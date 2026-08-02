package com.SpringBoot.Dto;

public class AuthResponse {
    private boolean success;
    private String message;
    private String role; // "STUDENT" or "ADMIN"
    private Long id;
    private String name;
    private String email;

    public AuthResponse() {
    }

    public AuthResponse(boolean success, String message, String role, Long id, String name, String email) {
        this.success = success;
        this.message = message;
        this.role = role;
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
