package com.openclassrooms.starterjwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponseDTO {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String username;
  private String firstName;
  private String lastName;

  private Boolean admin;

  public JwtResponseDTO(String accessToken, Long id, String username, String firstName, String lastName, Boolean admin) {
    this.token = accessToken;
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.admin = admin;
  }
}
