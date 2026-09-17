package com.analytics.reporting_engine.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analytics.reporting_engine.dto.ActivityLogResponseDTO;
import com.analytics.reporting_engine.dto.AnalyticsReport;
import com.analytics.reporting_engine.entity.ActivityLog;
import com.analytics.reporting_engine.entity.Geolocation;
import com.analytics.reporting_engine.entity.User;
import com.analytics.reporting_engine.entity.UserAgent;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class ReportingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;


    // Dynamic Activity Report
    public List<ActivityLogResponseDTO> getReport(

            LocalDateTime fromDate,
            LocalDateTime toDate,
            String actionType,
            Long userId,
            String gender,
            Integer age,
            String country,
            String city,
            int page,
            int size,
            String sortBy) {

        CriteriaBuilder cb =
                entityManager.getCriteriaBuilder();

        CriteriaQuery<ActivityLog> query =
                cb.createQuery(ActivityLog.class);

        Root<ActivityLog> activityLog =
                query.from(ActivityLog.class);

        List<Predicate> predicates =
                new ArrayList<>();


        // Date From
        if (fromDate != null) {

            predicates.add(
                cb.greaterThanOrEqualTo(
                    activityLog.get("activityTime"),
                    fromDate
                )
            );
        }


        // Date To
        if (toDate != null) {

            predicates.add(
                cb.lessThanOrEqualTo(
                    activityLog.get("activityTime"),
                    toDate
                )
            );
        }


        // Action Type
        if (actionType != null &&
            !actionType.isBlank()) {

            predicates.add(
                cb.equal(
                    activityLog.get("actionType"),
                    actionType
                )
            );
        }


        // User ID
        if (userId != null) {

            predicates.add(
                cb.equal(
                    activityLog.get("user").get("id"),
                    userId
                )
            );
        }


        // Demographic Filters
        if (gender != null ||
            age != null ||
            country != null ||
            city != null) {

            Join<ActivityLog, User> userJoin =
                    activityLog.join(
                        "user",
                        JoinType.INNER
                    );


            // Gender
            if (gender != null &&
                !gender.isBlank()) {

                predicates.add(
                    cb.equal(
                        userJoin.get("gender"),
                        gender
                    )
                );
            }


            // Age
            if (age != null) {

                predicates.add(
                    cb.equal(
                        userJoin.get("age"),
                        age
                    )
                );
            }


            // Country
            if (country != null &&
                !country.isBlank()) {

                predicates.add(
                    cb.equal(
                        userJoin.get("country"),
                        country
                    )
                );
            }


            // City
            if (city != null &&
                !city.isBlank()) {

                predicates.add(
                    cb.equal(
                        userJoin.get("city"),
                        city
                    )
                );
            }
        }


        query.where(
            predicates.toArray(
                new Predicate[0]
            )
        );


        // Sorting
        if ("id".equalsIgnoreCase(sortBy)) {

            query.orderBy(
                cb.desc(
                    activityLog.get("id")
                )
            );

        } else if ("actionType".equalsIgnoreCase(sortBy)) {

            query.orderBy(
                cb.asc(
                    activityLog.get("actionType")
                )
            );

        } else {

            // Default sorting
            query.orderBy(
                cb.desc(
                    activityLog.get("activityTime")
                )
            );
        }


        Query finalQuery =
                entityManager.createQuery(query);


        // Pagination
        if (page < 0) {
            page = 0;
        }

        if (size <= 0) {
            size = 20;
        }

        finalQuery.setFirstResult(
            page * size
        );

        finalQuery.setMaxResults(
            size
        );


        // Named Entity Graph
        finalQuery.setHint(
            "jakarta.persistence.fetchgraph",
            entityManager.getEntityGraph(
                "ActivityLog.details"
            )
        );


        // Hibernate Query Cache
        finalQuery.setHint(
            "org.hibernate.cacheable",
            true
        );


        @SuppressWarnings("unchecked")
        List<ActivityLog> activityLogs =
                finalQuery.getResultList();


        // Convert Entity -> DTO
        List<ActivityLogResponseDTO> response =
                new ArrayList<>();


        for (ActivityLog activityLogData :
                activityLogs) {

            ActivityLogResponseDTO dto =
                    new ActivityLogResponseDTO();


            dto.setId(
                activityLogData.getId()
            );

            dto.setActionType(
                activityLogData.getActionType()
            );

            dto.setActivityTime(
                activityLogData.getActivityTime()
            );

            dto.setPageUrl(
                activityLogData.getPageUrl()
            );

            dto.setIpAddress(
                activityLogData.getIpAddress()
            );


            // User Details
            User user =
                    activityLogData.getUser();

            if (user != null) {

                dto.setUserName(
                    user.getName()
                );

                dto.setUserEmail(
                    user.getEmail()
                );

                dto.setGender(
                    user.getGender()
                );

                dto.setAge(
                    user.getAge()
                );

                dto.setCountry(
                    user.getCountry()
                );

                dto.setCity(
                    user.getCity()
                );
            }


            // UserAgent Details
            UserAgent userAgent =
                    activityLogData.getUserAgent();

            if (userAgent != null) {

                dto.setBrowser(
                    userAgent.getBrowser()
                );

                dto.setBrowserVersion(
                    userAgent.getBrowserVersion()
                );

                dto.setOperatingSystem(
                    userAgent.getOperatingSystem()
                );

                dto.setDeviceType(
                    userAgent.getDeviceType()
                );
            }


            // Geolocation Details
            Geolocation geolocation =
                    activityLogData.getGeolocation();

            if (geolocation != null) {

                dto.setGeoCountry(
                    geolocation.getCountry()
                );

                dto.setGeoCity(
                    geolocation.getCity()
                );

                dto.setRegion(
                    geolocation.getRegion()
                );

                dto.setLatitude(
                    geolocation.getLatitude()
                );

                dto.setLongitude(
                    geolocation.getLongitude()
                );
            }


            response.add(dto);
        }


        return response;
    }


    // Analytics Summary Report
    public AnalyticsReport getAnalyticsReport() {

        // Total Activities
        Query totalQuery =
                entityManager.createQuery(
                    "SELECT COUNT(a) " +
                    "FROM ActivityLog a"
                );

        totalQuery.setHint(
            "org.hibernate.cacheable",
            true
        );

        Long totalActivities =
                (Long) totalQuery.getSingleResult();


        // LOGIN Count
        Query loginQuery =
                entityManager.createQuery(
                    "SELECT COUNT(a) " +
                    "FROM ActivityLog a " +
                    "WHERE a.actionType = 'LOGIN'"
                );

        loginQuery.setHint(
            "org.hibernate.cacheable",
            true
        );

        Long loginCount =
                (Long) loginQuery.getSingleResult();


        // LOGOUT Count
        Query logoutQuery =
                entityManager.createQuery(
                    "SELECT COUNT(a) " +
                    "FROM ActivityLog a " +
                    "WHERE a.actionType = 'LOGOUT'"
                );

        logoutQuery.setHint(
            "org.hibernate.cacheable",
            true
        );

        Long logoutCount =
                (Long) logoutQuery.getSingleResult();


        // Other Activities
        Long otherActivities =
                totalActivities
                - loginCount
                - logoutCount;


        // Query Cache Statistics
        Statistics statistics =
                sessionFactory.getStatistics();

        System.out.println(
            "Query Cache Hit Count: "
            + statistics.getQueryCacheHitCount()
        );

        System.out.println(
            "Query Cache Miss Count: "
            + statistics.getQueryCacheMissCount()
        );


        return new AnalyticsReport(
            totalActivities,
            loginCount,
            logoutCount,
            otherActivities
        );
    }
}