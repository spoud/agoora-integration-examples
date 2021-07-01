package io.spoud.agoora.example.interceptor;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;

@Slf4j
@Provider
public class AuthorizationInterceptor implements WriterInterceptor {
  @Inject AuthorizationHeaderService authorizationHeaderService;

  @Override
  public void aroundWriteTo(WriterInterceptorContext context)
      throws IOException, WebApplicationException {

    try {
      if (!context.getMediaType().equals(MediaType.APPLICATION_FORM_URLENCODED_TYPE)) {
        // avoid to intercept keycloak calls
        context
            .getHeaders()
            .add("Authorization", authorizationHeaderService.getAuthorizationHeader());
        log.trace("Authorization header injected " + context.getHeaders());
      }
    } catch (Exception ex) {
      log.error("Unable to inject authorization token", ex);
    } finally {
      context.proceed();
    }
  }
}
