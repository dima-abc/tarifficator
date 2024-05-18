package com.test2.auth.service;

import com.test2.auth.model.LoginRequestDto;
import com.test2.auth.model.LoginResponseDto;
import com.test2.auth.model.SignUpDto;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceDefault implements UserService {

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String MASTER_REALM = "master";
    private static final String ADMIN_CLI = "admin-cli";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final int CONNECTION_POOL_SIZE = 10;

    @Override
    public SignUpDto signUp(SignUpDto signUpDto) {
        log.info("Signing up user: {}", signUpDto);

        try (Keycloak keycloak = createKeycloakClient()) {
            keycloak.tokenManager().getAccessToken();

            UserRepresentation user = createUserRepresentation(signUpDto);

            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();

            Response response = usersResource.create(user);
            signUpDto.setStatusCode(response.getStatus());
            signUpDto.setStatusMessage(response.getStatusInfo().toString());

            if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                String userId = CreatedResponseUtil.getCreatedId(response);
                log.info("Created userId: {}", userId);

                updateUserCredentials(usersResource, userId, signUpDto.getPassword());
                assignUserRole(realmResource, usersResource, userId);
            }
        }
        return signUpDto;
    }

    private Keycloak createKeycloakClient() {
        return KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .grantType(OAuth2Constants.PASSWORD)
                .realm(MASTER_REALM)
                .clientId(ADMIN_CLI)
                .username(ADMIN_USERNAME)
                .password(ADMIN_PASSWORD)
                .resteasyClient(new ResteasyClientBuilderImpl().connectionPoolSize(CONNECTION_POOL_SIZE).build())
                .build();
    }

    private UserRepresentation createUserRepresentation(SignUpDto signUpDto) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(signUpDto.getUsername());
        user.setFirstName(signUpDto.getFirstname());
        user.setLastName(signUpDto.getLastname());
        user.setEmail(signUpDto.getEmail());
        return user;
    }

    private void updateUserCredentials(UsersResource usersResource, String userId, String password) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);

        UserResource userResource = usersResource.get(userId);
        userResource.resetPassword(passwordCred);
    }

    private void assignUserRole(RealmResource realmResource, UsersResource usersResource, String userId) {
        RoleRepresentation realmRoleUser = realmResource.roles().get(ROLE_ADMIN).toRepresentation();
        usersResource.get(userId).roles().realmLevel().add(Arrays.asList(realmRoleUser));
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        log.info("Logging in user: {}", loginRequest.getUsername());

        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", clientSecret);
        clientCredentials.put("provider", "secret");
        clientCredentials.put("grant_type", "password");

        Configuration configuration = new Configuration(authServerUrl, realm, clientId, clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);

        AccessTokenResponse response = authzClient.obtainAccessToken(loginRequest.getUsername(), loginRequest.getPassword());

        return createLoginResponse(response);
    }

    private LoginResponseDto createLoginResponse(AccessTokenResponse response) {
        LoginResponseDto loginResponse = new LoginResponseDto();
        loginResponse.setAccessToken(response.getToken());
        loginResponse.setRefreshToken(response.getRefreshToken());
        loginResponse.setScope(response.getScope());
        loginResponse.setExpiresIn(response.getExpiresIn());
        loginResponse.setError(response.getError());
        return loginResponse;
    }
}
