package com.paymentApp.module;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer customerId;
	
	@NotBlank
	private String name;
	
	@Size(min=10,max=10,message = "{moblie.invalid}")
	private String mobileNo;
	
	//@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,100}$",message = "{password.invalid}")
	private String password;
	
	@Email(message = "{email.invalid}")
	@NotBlank
	private String email;

	@NotBlank
	private String role;

	@JsonIgnore
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	private Wallet wallet;

	@Override
	public String toString() {
		return "Customer{" +
				"customerId=" + customerId +
				", name='" + name + '\'' +
				", mobileNo='" + mobileNo + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", role='" + role + '\'' +
				", wallet=" + wallet +
				'}';
	}
}
