package io.spoud.agoora.example.model.mutation;

import io.spoud.agoora.example.model.types.PrivilegeType;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class InviteUserToOrganization {
  private String organizationName;
  private String email;
  private PrivilegeType privilege;
}
