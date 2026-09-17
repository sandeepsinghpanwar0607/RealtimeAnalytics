package com.analytics.reporting_engine.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	@NotBlank
	private String name ;
	@Email
	@NotBlank
	private String email;
	private String password ;
	private String gender;
	private Integer age ;
	private String country;
	private String city ;
	@NotNull
	private String Role;
	
	
	

}
