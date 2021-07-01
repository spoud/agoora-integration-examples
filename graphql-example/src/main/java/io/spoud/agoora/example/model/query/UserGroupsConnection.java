package io.spoud.agoora.example.model.query;

import lombok.Data;

import java.util.List;

@Data
public class UserGroupsConnection {

  private PageInfo pageInfo;
  private List<UserGroup> nodes;
}
