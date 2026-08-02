package com.SpringBoot.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id")
	private long a_id;

	@Column(name = "admin_name", nullable = false)
	private String a_name;

	@Column(name = "admin_email", unique = true, nullable = false)
	private String AEmail;

	@Column(name = "admin_password", nullable = false)
	private String a_password;

	public Admin() {
	}

	public long getA_id() {
		return a_id;
	}

	public void setA_id(long a_id) {
		this.a_id = a_id;
	}

	@JsonProperty("a_name")
	public String getA_name() {
		return a_name;
	}

	public void setA_name(String a_name) {
		this.a_name = a_name;
	}

	@JsonProperty("a_email")
	public String getA_email() {
		return AEmail;
	}

	public void setA_email(String a_email) {
		this.AEmail = a_email;
	}

	@JsonProperty("a_password")
	public String getA_password() {
		return a_password;
	}

	public void setA_password(String a_password) {
		this.a_password = a_password;
	}
}
