package io.spoud.agoora.example.graphql;

import io.quarkus.oidc.client.filter.OidcClientFilter;
import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import io.spoud.agoora.example.model.mutation.InviteUserToOrganization;
import io.spoud.agoora.example.model.mutation.UserChange;
import io.spoud.agoora.example.model.query.User;
import io.spoud.agoora.example.model.query.UserConnection;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.NonNull;

@GraphQLClientApi(configKey = "agoora")
@OidcClientFilter()
public interface UserClient {

  UserConnection users(@NonNull String organization, Integer first, String after);

  /**
   * Invite someone to an organization. You will need the ADMIN permission on the organization to do so
   */
  @Mutation
  String inviteUserToOrganization(@NonNull InviteUserToOrganization input);

  @Mutation
  User saveUser(@NonNull UserChange input);
}
