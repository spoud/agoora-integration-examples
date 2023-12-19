package io.spoud.agoora.example.graphql;

import io.quarkus.oidc.client.filter.OidcClientFilter;
import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import io.spoud.agoora.example.model.mutation.UserGroupChange;
import io.spoud.agoora.example.model.query.UserGroup;
import io.spoud.agoora.example.model.query.UserGroupsConnection;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.NonNull;

@GraphQLClientApi(configKey = "agoora")
@OidcClientFilter()
public interface UserGroupClient {

  UserGroupsConnection userGroups(@NonNull String organization, Integer first, String after);

  @Mutation
  UserGroup saveUserGroup(@NonNull UserGroupChange input);
}
