package com.paymentApp.module;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CustomerDTO {

	@Size(min=10,max=10,message = "{moblie.invalid}")
	private String mobileNo;
	
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,12}$",message = "{password.invalid}") 
	private String password;
}
