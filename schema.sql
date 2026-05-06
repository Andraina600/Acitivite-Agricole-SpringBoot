-- ============================================================
-- RESET COMPLET
-- ============================================================
DROP TABLE IF EXISTS collectivity_transaction CASCADE;
DROP TABLE IF EXISTS member_payment CASCADE;
DROP TABLE IF EXISTS membership_fee CASCADE;
DROP TABLE IF EXISTS mobile_money_account_detail CASCADE;
DROP TABLE IF EXISTS bank_account_detail CASCADE;
DROP TABLE IF EXISTS financial_account CASCADE;
DROP TABLE IF EXISTS member_referee CASCADE;
DROP TABLE IF EXISTS collectivity_structure CASCADE;
DROP TABLE IF EXISTS member CASCADE;
DROP TABLE IF EXISTS collectivity CASCADE;
DROP TABLE IF EXISTS collectivity_activity CASCADE;
DROP TABLE IF EXISTS activity_occupation_concerned CASCADE;
DROP TABLE IF EXISTS activity_attendance CASCADE;

-- ============================================================
-- RECREATION DES TABLES
-- ============================================================
CREATE TABLE collectivity (
                              id            VARCHAR(50)  PRIMARY KEY,
                              number        INTEGER      UNIQUE,
                              name          VARCHAR(255) UNIQUE,
                              location      VARCHAR(255) NOT NULL,
                              creation_date DATE         NOT NULL
);

CREATE TABLE member (
                        id              VARCHAR(50)  PRIMARY KEY,
                        first_name      VARCHAR(100) NOT NULL,
                        last_name       VARCHAR(100) NOT NULL,
                        birth_date      DATE         NOT NULL,
                        gender          VARCHAR(10),
                        address         VARCHAR(255),
                        profession      VARCHAR(100),
                        phone_number    BIGINT,
                        email           VARCHAR(100),
                        occupation      VARCHAR(50),
                        collectivity_id VARCHAR(50)  REFERENCES collectivity(id),
                        admission_date  DATE         NOT NULL
);

CREATE TABLE collectivity_structure (
                                        collectivity_id   VARCHAR(50) PRIMARY KEY REFERENCES collectivity(id),
                                        president_id      VARCHAR(50) REFERENCES member(id),
                                        vice_president_id VARCHAR(50) REFERENCES member(id),
                                        treasurer_id      VARCHAR(50) REFERENCES member(id),
                                        secretary_id      VARCHAR(50) REFERENCES member(id)
);

CREATE TABLE member_referee (
                                member_id  VARCHAR(50) REFERENCES member(id),
                                referee_id VARCHAR(50) REFERENCES member(id),
                                PRIMARY KEY (member_id, referee_id)
);

CREATE TABLE financial_account (
                                   id              VARCHAR(50)   PRIMARY KEY,
                                   collectivity_id VARCHAR(50)   NOT NULL REFERENCES collectivity(id),
                                   account_type    VARCHAR(20)   NOT NULL CHECK (account_type IN ('CASH', 'BANK', 'MOBILE')),
                                   balance         NUMERIC(15,2) NOT NULL DEFAULT 0
);

CREATE UNIQUE INDEX uq_cash_account
    ON financial_account (collectivity_id)
    WHERE account_type = 'CASH';

CREATE TABLE bank_account_detail (
                                     id                  VARCHAR(50)  PRIMARY KEY REFERENCES financial_account(id) ON DELETE CASCADE,
                                     holder_name         VARCHAR(255) NOT NULL,
                                     bank_name           VARCHAR(50)  NOT NULL,
                                     bank_code           INTEGER      NOT NULL,
                                     bank_branch_code    INTEGER      NOT NULL,
                                     bank_account_number BIGINT       NOT NULL,
                                     bank_account_key    INTEGER      NOT NULL
);

CREATE TABLE mobile_money_account_detail (
                                             id                     VARCHAR(50)  PRIMARY KEY REFERENCES financial_account(id) ON DELETE CASCADE,
                                             holder_name            VARCHAR(255) NOT NULL,
                                             mobile_banking_service VARCHAR(20)  NOT NULL,
                                             mobile_number          BIGINT       NOT NULL UNIQUE
);

CREATE TABLE membership_fee (
                                id              VARCHAR(50)   PRIMARY KEY,
                                collectivity_id VARCHAR(50)   NOT NULL REFERENCES collectivity(id),
                                label           VARCHAR(255)  NOT NULL,
                                amount          NUMERIC(15,2) NOT NULL CHECK (amount >= 0),
                                frequency       VARCHAR(20)   NOT NULL,
                                eligible_from   DATE          NOT NULL,
                                status          VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE member_payment (
                                id                  VARCHAR(50)   PRIMARY KEY,
                                member_id           VARCHAR(50)   NOT NULL REFERENCES member(id),
                                membership_fee_id   VARCHAR(50)   NOT NULL REFERENCES membership_fee(id),
                                account_credited_id VARCHAR(50)   NOT NULL REFERENCES financial_account(id),
                                amount              NUMERIC(15,2) NOT NULL CHECK (amount >= 0),
                                payment_mode        VARCHAR(20)   NOT NULL,
                                creation_date       DATE          NOT NULL
);

CREATE TABLE collectivity_transaction (
                                          id                  VARCHAR(50)   PRIMARY KEY,
                                          collectivity_id     VARCHAR(50)   NOT NULL REFERENCES collectivity(id),
                                          member_payment_id   VARCHAR(50)   REFERENCES member_payment(id),
                                          account_credited_id VARCHAR(50)   NOT NULL REFERENCES financial_account(id),
                                          member_debited_id   VARCHAR(50)   NOT NULL REFERENCES member(id),
                                          amount              NUMERIC(15,2) NOT NULL,
                                          payment_mode        VARCHAR(20)   NOT NULL,
                                          creation_date       DATE          NOT NULL
);

-- Activités d'une collectivité
CREATE TABLE collectivity_activity (
                                       id                       VARCHAR(50)  PRIMARY KEY,
                                       collectivity_id          VARCHAR(50)  NOT NULL REFERENCES collectivity(id),
                                       label                    VARCHAR(255) NOT NULL,
                                       activity_type            VARCHAR(20)  NOT NULL
                                           CHECK (activity_type IN ('MEETING', 'TRAINING', 'OTHER')),
                                       executive_date           DATE,
                                       recurrence_week_ordinal  INTEGER CHECK (recurrence_week_ordinal BETWEEN 1 AND 5),
                                       recurrence_day_of_week   VARCHAR(2)
                                           CHECK (recurrence_day_of_week IN ('MO','TU','WE','TH','FR','SA','SU'))
);

-- Occupations ciblées par une activité
CREATE TABLE activity_occupation_concerned (
                                               activity_id VARCHAR(50) NOT NULL REFERENCES collectivity_activity(id) ON DELETE CASCADE,
                                               occupation  VARCHAR(50) NOT NULL,
                                               PRIMARY KEY (activity_id, occupation)
);

-- Présences des membres à une activité
CREATE TABLE activity_attendance (
                                     id          VARCHAR(50) PRIMARY KEY,
                                     activity_id VARCHAR(50) NOT NULL REFERENCES collectivity_activity(id),
                                     member_id   VARCHAR(50) NOT NULL REFERENCES member(id),
                                     status      VARCHAR(20) NOT NULL DEFAULT 'UNDEFINED'
                                         CHECK (status IN ('MISSING', 'ATTENDED', 'UNDEFINED')),
                                     UNIQUE (activity_id, member_id)
);

