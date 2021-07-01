package io.spoud.agoora.example.model.query;

import lombok.Data;

import java.util.List;

@Data
public class UserConnection {

  private PageInfo pageInfo;
  private List<User> nodes;
}
