package io.spoud.agoora.example.model.mutation;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserGroupChange {
  private String id;
  private String name;
  private String organization;
}
