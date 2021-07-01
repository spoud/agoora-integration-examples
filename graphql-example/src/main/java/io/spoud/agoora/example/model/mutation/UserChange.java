package io.spoud.agoora.example.model.mutation;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserChange {
  private String id;
  private java.util.List<String> groups;
}
