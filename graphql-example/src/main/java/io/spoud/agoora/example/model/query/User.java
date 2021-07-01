package io.spoud.agoora.example.model.query;

import lombok.Data;

@Data
public class User {

  private String id;
  private String firstName;
  private String lastName;
  private String email;
  private java.util.List<String> organizations;
  private java.util.List<UserGroup> groups;
  private String avatarUrl;
}
