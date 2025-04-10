package com.usercard.login.DTO;

public class LoginResponseDTO {

    private String username;

    private String userPermission;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String username, String userPermission) {
        this.username = username;
        this.userPermission = userPermission;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(String userPermission) {
        this.userPermission = userPermission;
    }
}
