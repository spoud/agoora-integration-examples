package io.spoud.agoora.example.interceptor;

import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;

/**
 * Uses the keycloak client to simplify the authentication. Any OpenId Connect client would have
 * done the trick.
 *
 * <p>This classes only fetch the token and get the bearer token
 */
@Slf4j
@Singleton
public class AuthorizationHeaderService {
  public static final String REALM_NAME = "spoud";
  public static final String INTEGRATION_CLIENT_ID = "spoud-sdm-integration";

  @ConfigProperty(name = "agoora.auth.url")
  String url;

  @ConfigProperty(name = "agoora.auth.username")
  String username;

  @ConfigProperty(name = "agoora.auth.password")
  String password;

  private Keycloak keycloakClient;

  void onStart(@Observes StartupEvent startupEvent) {

    this.keycloakClient =
        KeycloakBuilder.builder()
            .serverUrl(url)
            .realm(REALM_NAME)
            .clientId(INTEGRATION_CLIENT_ID)
            .grantType(OAuth2Constants.PASSWORD)
            .username(username)
            .password(password)
            .build();
  }

  public String getAuthorizationHeader() {
    try {
      String accessToken = keycloakClient.tokenManager().getAccessTokenString();
      if (accessToken == null) {
        throw new IllegalStateException(
            "JWT token is null, Keycloak agent is not able to retrieve the token");
      }
      return "Bearer " + accessToken;
    } catch (Exception e) {
      throw new IllegalStateException("Unable to get auth token", e);
    }
  }
}
