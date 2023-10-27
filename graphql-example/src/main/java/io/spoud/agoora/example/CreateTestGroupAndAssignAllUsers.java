package io.spoud.agoora.example;

import io.quarkus.runtime.StartupEvent;
import io.spoud.agoora.example.graphql.UserClient;
import io.spoud.agoora.example.graphql.UserGroupClient;
import io.spoud.agoora.example.model.mutation.UserChange;
import io.spoud.agoora.example.model.mutation.UserGroupChange;
import io.spoud.agoora.example.model.query.User;
import io.spoud.agoora.example.model.query.UserGroup;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@ApplicationScoped
public class CreateTestGroupAndAssignAllUsers {

  @Inject UserClient userClient;
  @Inject UserGroupClient userGroupClient;

  @ConfigProperty(name = "agoora.organization")
  String organization;

  ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

  void onStart(@Observes StartupEvent startupEvent) {
    scheduledExecutorService.schedule(this::execute, 1, TimeUnit.SECONDS);
  }

  void execute() {
    try {
      final UserGroup group = createGroupIfNotExists("test-group");

      // get users
      final List<User> users = userClient.users(organization, 100, null).getNodes();
      log.info("Users: {}", users);

      // assign all users to this group
      users.forEach(
          user -> {
            // edit user to add the test group
            final UserChange input =
                UserChange.builder()
                    .id(user.getId())
                    .groups(
                        Stream.concat(
                                Stream.of(group.getId()),
                                user.getGroups().stream().map(UserGroup::getId))
                            .distinct()
                            .collect(Collectors.toList()))
                    .build();
            final User saved = userClient.saveUser(input);
            log.info("Saved user: {}", saved);
          });

    } catch (Exception ex) {
      log.error("Error while processing", ex);
    }
  }

  UserGroup createGroupIfNotExists(String name) {
    // get user groups
    final List<UserGroup> groups = userGroupClient.userGroups(organization, 100, null).getNodes();
    log.info("User groups: {}", groups);

    return groups.stream()
        .filter(group -> group.getName().equals(name))
        .findAny()
        .orElseGet(
            () -> {
              // create group
              final UserGroup userGroup =
                  userGroupClient.saveUserGroup(
                      UserGroupChange.builder()
                          .name("test-group")
                          .organization(organization)
                          .build());
              log.info("created user group: {}", userGroup);
              return userGroup;
            });
  }
}
