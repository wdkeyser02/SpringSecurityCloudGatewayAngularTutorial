-- Users
create table if not exists users
(
	username varchar(200) not null primary key,
	password varchar(500) not null,
	enabled boolean not null
);

create table if not exists authorities
(
	username varchar(200) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key (username) references users (username),
	constraint username_authority UNIQUE (username, authority)
);

-- Insert new Users & Authorities
-- INSERT users VALUES("user", "{bcrypt}$2a$14$tEnq90/CcR320dWQ.NdQLuj326PmgLzMGmFkUUOHQrbjPWplKK67i", true); --Password = 'password'
-- INSERT users VALUES("admin", "{bcrypt}$2a$14$tJANh4xMR7qNjwwftmoZjezhp6rP.RVUtIFXFBF6maQvqGXwvM4JS", true); --Password = 'password'
-- INSERT authorities VALUES("user", "ROLE_USER");
-- INSERT authorities VALUES("admin", "ROLE_USER");
-- INSERT authorities VALUES("admin", "ROLE_ADMIN");

-- Registered Client Repository

CREATE TABLE if not exists oauth2_registered_client
(
    id                            varchar(100)                            NOT NULL,
    client_id                     varchar(100)                            NOT NULL,
    client_id_issued_at           timestamp NULL,
    client_secret                 varchar(200)  DEFAULT NULL,
    client_secret_expires_at      timestamp NULL,
    client_name                   varchar(200)                            NOT NULL,
    client_authentication_methods varchar(1000)                           NOT NULL,
    authorization_grant_types     varchar(1000)                           NOT NULL,
    redirect_uris                 varchar(1000) DEFAULT NULL,
    post_logout_redirect_uris     varchar(1000) DEFAULT NULL,
    scopes                        varchar(1000)                           NOT NULL,
    client_settings               varchar(2000)                           NOT NULL,
    token_settings                varchar(2000)                           NOT NULL,
    PRIMARY KEY (id)
);

-- Insert new Oauth2 Registered Clients
-- INSERT oauth2_registered_client VALUES("b9b14387-25d0-46c8-8406-2c11deff05f0", "client",  now(), "{bcrypt}$2a$14$k4M/IICUdwmeTk0/nByDqee/dZ3YRPK6KlHHqEcIKUfVZR3R8.AX6",
--  now()  + INTERVAL 1 DAY, "b9b14387-25d0-46c8-8406-2c11deff05f0", "client_secret_basic", "refresh_token,authorization_code", "http://127.0.0.1:8090/login/oauth2/code/gateway", 
--  "http://127.0.0.1:8090/logged-out", "openid,profile", 
--  "{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}", 
--  "{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,
-- \"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],
-- \"settings.token.access-token-time-to-live\":[\"java.time.Duration\",300.000000000],
-- \"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat\",\"value\":\"self-contained\"},
-- \"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",3600.000000000],
-- \"settings.token.authorization-code-time-to-live\":[\"java.time.Duration\",300.000000000],
-- \"settings.token.device-code-time-to-live\":[\"java.time.Duration\",300.000000000]}"); --client_secret = 'secret'

-- sessions
CREATE TABLE if not exists SPRING_SESSION (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	EXPIRY_TIME BIGINT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

CREATE UNIQUE INDEX if not exists SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX if not exists SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX if not exists SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

-- session attributes
CREATE TABLE if not exists SPRING_SESSION_ATTRIBUTES (
	SESSION_PRIMARY_ID CHAR(36) NOT NULL,
	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
	ATTRIBUTE_BYTES BLOB NOT NULL,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

-- Authorization
CREATE TABLE if not exists oauth2_authorization (
    id varchar(100) NOT NULL,
    registered_client_id varchar(100) NOT NULL,
    principal_name varchar(200) NOT NULL,
    authorization_grant_type varchar(100) NOT NULL,
    authorized_scopes varchar(1000) DEFAULT NULL,
    attributes text DEFAULT NULL,
    state varchar(500) DEFAULT NULL,
    authorization_code_value text DEFAULT NULL,
    authorization_code_issued_at timestamp NULL,
    authorization_code_expires_at timestamp NULL,
    authorization_code_metadata text DEFAULT NULL,
    access_token_value text DEFAULT NULL,
    access_token_issued_at timestamp NULL,
    access_token_expires_at timestamp NULL,
    access_token_metadata text DEFAULT NULL,
    access_token_type varchar(100) DEFAULT NULL,
    access_token_scopes varchar(1000) DEFAULT NULL,
    oidc_id_token_value text DEFAULT NULL,
    oidc_id_token_issued_at timestamp NULL,
    oidc_id_token_expires_at timestamp NULL,
    oidc_id_token_metadata text DEFAULT NULL,
    refresh_token_value text DEFAULT NULL,
    refresh_token_issued_at timestamp NULL,
    refresh_token_expires_at timestamp NULL,
    refresh_token_metadata text DEFAULT NULL,
    user_code_value text DEFAULT NULL,
    user_code_issued_at timestamp NULL,
    user_code_expires_at timestamp NULL,
    user_code_metadata text DEFAULT NULL,
    device_code_value text DEFAULT NULL,
    device_code_issued_at timestamp NULL,
    device_code_expires_at timestamp NULL,
    device_code_metadata text DEFAULT NULL,
    PRIMARY KEY (id)
);
-- Authorization consent
CREATE TABLE if not exists oauth2_authorization_consent (
    registered_client_id varchar(100) NOT NULL,
    principal_name varchar(200) NOT NULL,
    authorities varchar(1000) NOT NULL,
    PRIMARY KEY (registered_client_id, principal_name)
);

-- rsa_key_pairs
create table if not exists rsa_key_pairs
(
    id          varchar(1000) not null primary key,
    private_key text          not null,
    public_key  text          not null,
    created     date          not null,
    constraint id_created UNIQUE (id, created)
);
