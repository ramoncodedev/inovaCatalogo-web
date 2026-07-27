CREATE TABLE leads (
       id    BIGSERIAL PRIMARY KEY,
       name_client  VARCHAR(255) NOT NULL,
       company_name  VARCHAR(255) NOT NULL,
       email         VARCHAR(255) NOT NULL UNIQUE,
       phone       VARCHAR(20),
       segment     VARCHAR(50)  NOT NULL,
       created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);