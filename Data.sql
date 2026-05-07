INSERT INTO collectivity (id, number, name, location, creation_date) VALUES
                                                                         ('col-1', 1, 'Mpanorina',       'Ambatondrazaka', '2026-01-01'),
                                                                         ('col-2', 2, 'Ambatondrazaka',  'Ambatondrazaka', '2026-01-01'),
                                                                         ('col-3', 3, 'Tantely mamy',    'Brickaville',    '2026-01-01');

INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession,
                    phone_number, email, occupation, collectivity_id, admission_date) VALUES
                                                                                          ('C1-M1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'MALE',   'Lot II V M Ambato.', 'Riziculteur',  341234567, 'member.1@fed-agri.mg', 'PRESIDENT',      'col-1', '2026-01-01'),
                                                                                          ('C1-M2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'MALE',   'Lot II F Ambato.',   'Agriculteur',  321234567, 'member.2@fed-agri.mg', 'VICE_PRESIDENT', 'col-1', '2026-01-01'),
                                                                                          ('C1-M3', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'MALE',   'Lot II J Ambato.',   'Collecteur',   331234567, 'member.3@fed-agri.mg', 'SECRETARY',      'col-1', '2026-01-01'),
                                                                                          ('C1-M4', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato.', 'Distributeur', 381234567, 'member.4@fed-agri.mg', 'TREASURER',      'col-1', '2026-01-01'),
                                                                                          ('C1-M5', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'MALE',   'Lot UV 80 Ambato.',  'Riziculteur',  373434567, 'member.5@fed-agri.mg', 'SENIOR',         'col-1', '2026-01-01'),
                                                                                          ('C1-M6', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato.',   'Riziculteur',  372234567, 'member.6@fed-agri.mg', 'SENIOR',         'col-1', '2026-01-01'),
                                                                                          ('C1-M7', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'MALE',   'Lot UV 7 Ambato.',   'Riziculteur',  374234567, 'member.7@fed-agri.mg', 'SENIOR',         'col-1', '2026-01-01'),
                                                                                          ('C1-M8', 'Prénom membre 8', 'Nom membre 6', '1975-08-20', 'MALE',   'Lot UV 8 Ambato.',   'Riziculteur',  370234567, 'member.8@fed-agri.mg', 'SENIOR',         'col-1', '2026-01-01');

INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession,
                    phone_number, email, occupation, collectivity_id, admission_date) VALUES
                                                                                          ('C2-M1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'MALE',   'Lot II V M Ambato.', 'Riziculteur',  341234567, 'member.1@fed-agri.mg', 'PRESIDENT',      'col-2', '2026-01-01'),
                                                                                          ('C2-M2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'MALE',   'Lot II F Ambato.',   'Agriculteur',  321234567, 'member.2@fed-agri.mg', 'VICE_PRESIDENT', 'col-2', '2026-01-01'),
                                                                                          ('C2-M3', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'MALE',   'Lot II J Ambato.',   'Collecteur',   331234567, 'member.3@fed-agri.mg', 'SECRETARY',      'col-2', '2026-01-01'),
                                                                                          ('C2-M4', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato.', 'Distributeur', 381234567, 'member.4@fed-agri.mg', 'TREASURER',      'col-2', '2026-01-01'),
                                                                                          ('C2-M5', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'MALE',   'Lot UV 80 Ambato.',  'Riziculteur',  373434567, 'member.5@fed-agri.mg', 'SENIOR',         'col-2', '2026-01-01'),
                                                                                          ('C2-M6', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato.',   'Riziculteur',  372234567, 'member.6@fed-agri.mg', 'SENIOR',         'col-2', '2026-01-01'),
                                                                                          ('C2-M7', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'MALE',   'Lot UV 7 Ambato.',   'Riziculteur',  374234567, 'member.7@fed-agri.mg', 'SENIOR',         'col-2', '2026-01-01'),
                                                                                          ('C2-M8', 'Prénom membre 8', 'Nom membre 6', '1975-08-20', 'MALE',   'Lot UV 8 Ambato.',   'Riziculteur',  370234567, 'member.8@fed-agri.mg', 'SENIOR',         'col-2', '2026-01-01');

INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession,
                    phone_number, email, occupation, collectivity_id, admission_date) VALUES
                                                                                          ('C3-M1', 'Prénom membre 9',  'Nom membre 9',  '1988-01-02', 'MALE',   'Lot 33 J Antsirabe',   'Apiculteur',  34034567,  'member.9@fed-agri.mg',  'PRESIDENT',      'col-3', '2026-01-01'),
                                                                                          ('C3-M2', 'Prénom membre 10', 'Nom membre 10', '1982-03-05', 'MALE',   'Lot 2 J Antsirabe',    'Agriculteur', 338634567, 'member.10@fed-agri.mg', 'VICE_PRESIDENT', 'col-3', '2026-01-01'),
                                                                                          ('C3-M3', 'Prénom membre 11', 'Nom membre 11', '1992-03-12', 'MALE',   'Lot 8 KM Antsirabe',   'Collecteur',  338234567, 'member.11@fed-agri.mg', 'SECRETARY',      'col-3', '2026-01-01'),
                                                                                          ('C3-M4', 'Prénom membre 12', 'Nom membre 12', '1988-05-10', 'FEMALE', 'Lot A K 50 Antsirabe', 'Distributeur',382334567, 'member.12@fed-agri.mg', 'TREASURER',      'col-3', '2026-01-01'),
                                                                                          ('C3-M5', 'Prénom membre 13', 'Nom membre 13', '1999-08-11', 'MALE',   'Lot UV 80 Antsirabe',  'Apiculteur',  373365567, 'member.13@fed-agri.mg', 'SENIOR',         'col-3', '2026-01-01'),
                                                                                          ('C3-M6', 'Prénom membre 14', 'Nom membre 14', '1998-08-09', 'FEMALE', 'Lot UV 6 Antsirabe',   'Apiculteur',  378234567, 'member.14@fed-agri.mg', 'SENIOR',         'col-3', '2026-01-01'),
                                                                                          ('C3-M7', 'Prénom membre 15', 'Nom membre 15', '1998-01-13', 'MALE',   'Lot UV 7 Antsirabe',   'Apiculteur',  374914567, 'member.15@fed-agri.mg', 'SENIOR',         'col-3', '2026-01-01'),
                                                                                          ('C3-M8', 'Prénom membre 16', 'Nom membre 16', '1975-08-02', 'MALE',   'Lot UV 8 Antsirabe',   'Apiculteur',  370634567, 'member.16@fed-agri.mg', 'SENIOR',         'col-3', '2026-01-01');



-- ------------------------------------------------------------
-- 8.2 Nouveaux membres - Collectivité 1 (col-1)
-- 4 juniors : 2 en avril, 1 en mai, 1 en juin
-- Parrains : C1-M1 et C1-M2
-- ------------------------------------------------------------

INSERT INTO member (id, first_name, last_name, birth_date, gender, address,
                    profession, phone_number, email, occupation,
                    collectivity_id, admission_date)
VALUES
    ('C1-NEW-1', 'Prénom new 1',  'Nom new 1',  '2000-01-01', 'MALE',   'Lot A1 Ambato.', 'Agriculteur', 340000001, 'new1.col1@fed-agri.mg',  'JUNIOR', 'col-1', '2026-04-01'),
    ('C1-NEW-2', 'Prénom new 2',  'Nom new 2',  '2001-02-15', 'FEMALE', 'Lot A2 Ambato.', 'Agriculteur', 340000002, 'new2.col1@fed-agri.mg',  'JUNIOR', 'col-1', '2026-04-01'),
    ('C1-NEW-3', 'Prénom new 3',  'Nom new 3',  '1999-03-20', 'MALE',   'Lot A3 Ambato.', 'Agriculteur', 340000003, 'new3.col1@fed-agri.mg',  'JUNIOR', 'col-1', '2026-05-01'),
    ('C1-NEW-4', 'Prénom new 4',  'Nom new 4',  '1998-06-10', 'FEMALE', 'Lot A4 Ambato.', 'Agriculteur', 340000004, 'new4.col1@fed-agri.mg',  'JUNIOR', 'col-1', '2026-06-01');

-- Parrainages col-1
INSERT INTO member_referee (member_id, referee_id) VALUES
                                                       ('C1-NEW-1', 'C1-M1'), ('C1-NEW-1', 'C1-M2'),
                                                       ('C1-NEW-2', 'C1-M1'), ('C1-NEW-2', 'C1-M2'),
                                                       ('C1-NEW-3', 'C1-M1'), ('C1-NEW-3', 'C1-M2'),
                                                       ('C1-NEW-4', 'C1-M1'), ('C1-NEW-4', 'C1-M2');

-- ------------------------------------------------------------
-- 8.3 Nouveaux membres - Collectivité 2 (col-2)
-- 3 juniors : tous en mars
-- Parrains : C1-M1 et C1-M2 (membres de col-2 aussi)
-- ------------------------------------------------------------

INSERT INTO member (id, first_name, last_name, birth_date, gender, address,
                    profession, phone_number, email, occupation,
                    collectivity_id, admission_date)
VALUES
    ('C2-NEW-1', 'Prénom new 1',  'Nom new 1',  '2000-04-01', 'MALE',   'Lot B1 Ambato.', 'Agriculteur', 340000011, 'new1.col2@fed-agri.mg',  'JUNIOR', 'col-2', '2026-03-01'),
    ('C2-NEW-2', 'Prénom new 2',  'Nom new 2',  '2001-05-15', 'FEMALE', 'Lot B2 Ambato.', 'Agriculteur', 340000012, 'new2.col2@fed-agri.mg',  'JUNIOR', 'col-2', '2026-03-01'),
    ('C2-NEW-3', 'Prénom new 3',  'Nom new 3',  '1999-06-20', 'MALE',   'Lot B3 Ambato.', 'Agriculteur', 340000013, 'new3.col2@fed-agri.mg',  'JUNIOR', 'col-2', '2026-03-01');

-- Parrainages col-2
INSERT INTO member_referee (member_id, referee_id) VALUES
                                                       ('C2-NEW-1', 'C1-M1'), ('C2-NEW-1', 'C1-M2'),
                                                       ('C2-NEW-2', 'C1-M1'), ('C2-NEW-2', 'C1-M2'),
                                                       ('C2-NEW-3', 'C1-M1'), ('C2-NEW-3', 'C1-M2');

-- ------------------------------------------------------------
-- 8.4 Nouveaux membres - Collectivité 3 (col-3)
-- 6 juniors : 1 en janvier, 2 en février, 3 en mars
-- Parrains : C3-M1 et C3-M2
-- ------------------------------------------------------------

INSERT INTO member (id, first_name, last_name, birth_date, gender, address,
                    profession, phone_number, email, occupation,
                    collectivity_id, admission_date)
VALUES
    ('C3-NEW-1', 'Prénom new 1',  'Nom new 1',  '2000-01-10', 'MALE',   'Lot C1 Antsirabe', 'Apiculteur', 340000021, 'new1.col3@fed-agri.mg',  'JUNIOR', 'col-3', '2026-01-01'),
    ('C3-NEW-2', 'Prénom new 2',  'Nom new 2',  '2001-02-20', 'FEMALE', 'Lot C2 Antsirabe', 'Apiculteur', 340000022, 'new2.col3@fed-agri.mg',  'JUNIOR', 'col-3', '2026-02-01'),
    ('C3-NEW-3', 'Prénom new 3',  'Nom new 3',  '1999-03-15', 'MALE',   'Lot C3 Antsirabe', 'Apiculteur', 340000023, 'new3.col3@fed-agri.mg',  'JUNIOR', 'col-3', '2026-02-01'),
    ('C3-NEW-4', 'Prénom new 4',  'Nom new 4',  '1998-04-25', 'FEMALE', 'Lot C4 Antsirabe', 'Apiculteur', 340000024, 'new4.col3@fed-agri.mg',  'JUNIOR', 'col-3', '2026-03-01'),
    ('C3-NEW-5', 'Prénom new 5',  'Nom new 5',  '2000-05-30', 'MALE',   'Lot C5 Antsirabe', 'Apiculteur', 340000025, 'new5.col3@fed-agri.mg',  'JUNIOR', 'col-3', '2026-03-01'),
    ('C3-NEW-6', 'Prénom new 6',  'Nom new 6',  '1997-06-05', 'FEMALE', 'Lot C6 Antsirabe', 'Apiculteur', 340000026, 'new6.col3@fed-agri.mg',  'JUNIOR', 'col-3', '2026-03-01');

-- Parrainages col-3
INSERT INTO member_referee (member_id, referee_id) VALUES
                                                       ('C3-NEW-1', 'C3-M1'), ('C3-NEW-1', 'C3-M2'),
                                                       ('C3-NEW-2', 'C3-M1'), ('C3-NEW-2', 'C3-M2'),
                                                       ('C3-NEW-3', 'C3-M1'), ('C3-NEW-3', 'C3-M2'),
                                                       ('C3-NEW-4', 'C3-M1'), ('C3-NEW-4', 'C3-M2'),
                                                       ('C3-NEW-5', 'C3-M1'), ('C3-NEW-5', 'C3-M2'),
                                                       ('C3-NEW-6', 'C3-M1'), ('C3-NEW-6', 'C3-M2');


INSERT INTO collectivity_structure
(collectivity_id, president_id, vice_president_id, treasurer_id, secretary_id) VALUES
                                                                                   ('col-1', 'C1-M1', 'C1-M2', 'C1-M4', 'C1-M3'),
                                                                                   ('col-2', 'C1-M5', 'C1-M6', 'C1-M8', 'C1-M7'),
                                                                                   ('col-3', 'C3-M1', 'C3-M2', 'C3-M4', 'C3-M3');

-- Comptes existants col-1
INSERT INTO financial_account (id, collectivity_id, account_type, balance) VALUES
                                                                               ('C1-A-CASH',     'col-1', 'CASH',   0),
                                                                               ('C1-A-MOBILE-1', 'col-1', 'MOBILE', 0);

INSERT INTO mobile_money_account_detail
(id, holder_name, mobile_banking_service, mobile_number) VALUES
    ('C1-A-MOBILE-1', 'Mpanorina', 'ORANGE_MONEY', 370489612);

-- Comptes existants col-2
INSERT INTO financial_account (id, collectivity_id, account_type, balance) VALUES
                                                                               ('C2-A-CASH',     'col-2', 'CASH',   0),
                                                                               ('C2-A-MOBILE-1', 'col-2', 'MOBILE', 0);

INSERT INTO mobile_money_account_detail
(id, holder_name, mobile_banking_service, mobile_number) VALUES
    ('C2-A-MOBILE-1', 'Dobo voalohany', 'ORANGE_MONEY', 320489612);

-- Comptes col-3 (caisse existante + 2 nouveaux BANK + 1 nouveau MOBILE)
INSERT INTO financial_account (id, collectivity_id, account_type, balance) VALUES
                                                                               ('C3-A-CASH',     'col-3', 'CASH',   0),
                                                                               ('C3-A-BANK-1',   'col-3', 'BANK',   0),
                                                                               ('C3-A-BANK-2',   'col-3', 'BANK',   0),
                                                                               ('C3-A-MOBILE-1', 'col-3', 'MOBILE', 0);

INSERT INTO bank_account_detail
(id, holder_name, bank_name, bank_code, bank_branch_code, bank_account_number, bank_account_key)
VALUES
    ('C3-A-BANK-1', 'Kolo', 'BMOI', 00004, 00001, 1234567890, 12),
    ('C3-A-BANK-2', 'Koto', 'BRED', 00008, 00003, 4567890123, 58);

INSERT INTO mobile_money_account_detail
(id, holder_name, mobile_banking_service, mobile_number) VALUES
    ('C3-A-MOBILE-1', 'Naivo', 'MVOLA', 341889612);

-- col-1
INSERT INTO membership_fee (id, collectivity_id, label, amount, frequency, eligible_from, status) VALUES
                                                                                                      ('cot-1', 'col-1', 'Cotisation annuelle', 200000, 'ANNUALLY',   '2026-01-01', 'ACTIVE'),
                                                                                                      ('cot-2', 'col-1', 'Famangiana',           20000, 'PUNCTUALLY', '2026-04-30', 'ACTIVE');

-- col-2
INSERT INTO membership_fee (id, collectivity_id, label, amount, frequency, eligible_from, status) VALUES
                                                                                                      ('cot-3', 'col-2', 'Cotisation annuelle', 200000, 'ANNUALLY', '2026-01-01', 'ACTIVE'),
                                                                                                      ('cot-4', 'col-2', 'Cotisation 2025',     100000, 'ANNUALLY', '2025-01-01', 'INACTIVE');

-- col-3
INSERT INTO membership_fee (id, collectivity_id, label, amount, frequency, eligible_from, status) VALUES
    ('cot-5', 'col-3', 'Cotisation mensuelle', 25000, 'MONTHLY', '2026-04-01', 'ACTIVE');

-- ============================================================
-- ETAPE 7 - PAIEMENTS ET TRANSACTIONS
-- Pour chaque paiement : 1 ligne member_payment + 1 ligne collectivity_transaction
-- ============================================================

-- ============================================================
-- COLLECTIVITE 1
-- ============================================================

-- member_payment
INSERT INTO member_payment (id, member_id, membership_fee_id, account_credited_id, amount, payment_mode, creation_date) VALUES
                                                                                                                            ('PAY-C1-M1', 'C1-M1', 'cot-1', 'C1-A-CASH',     200000, 'CASH',           '2026-01-01'),
                                                                                                                            ('PAY-C1-M2', 'C1-M2', 'cot-1', 'C1-A-CASH',     200000, 'CASH',           '2026-01-01'),
                                                                                                                            ('PAY-C1-M3', 'C1-M3', 'cot-1', 'C1-A-MOBILE-1', 200000, 'MOBILE_BANKING', '2026-01-01'),
                                                                                                                            ('PAY-C1-M4', 'C1-M4', 'cot-1', 'C1-A-MOBILE-1', 200000, 'MOBILE_BANKING', '2026-01-01'),
                                                                                                                            ('PAY-C1-M5', 'C1-M5', 'cot-1', 'C1-A-MOBILE-1', 150000, 'MOBILE_BANKING', '2026-01-01'),
                                                                                                                            ('PAY-C1-M6', 'C1-M6', 'cot-1', 'C1-A-CASH',     100000, 'CASH',           '2026-05-01'),
                                                                                                                            ('PAY-C1-M7', 'C1-M7', 'cot-1', 'C1-A-CASH',      60000, 'CASH',           '2026-05-01'),
                                                                                                                            ('PAY-C1-M8', 'C1-M8', 'cot-1', 'C1-A-CASH',      90000, 'CASH',           '2026-05-01');

-- collectivity_transaction
INSERT INTO collectivity_transaction (id, collectivity_id, member_payment_id, account_credited_id, member_debited_id, amount, payment_mode, creation_date) VALUES
                                                                                                                                                               ('TRX-C1-M1', 'col-1', 'PAY-C1-M1', 'C1-A-CASH',     'C1-M1', 200000, 'CASH',           '2026-01-01'),
                                                                                                                                                               ('TRX-C1-M2', 'col-1', 'PAY-C1-M2', 'C1-A-CASH',     'C1-M2', 200000, 'CASH',           '2026-01-01'),
                                                                                                                                                               ('TRX-C1-M3', 'col-1', 'PAY-C1-M3', 'C1-A-MOBILE-1', 'C1-M3', 200000, 'MOBILE_BANKING', '2026-01-01'),
                                                                                                                                                               ('TRX-C1-M4', 'col-1', 'PAY-C1-M4', 'C1-A-MOBILE-1', 'C1-M4', 200000, 'MOBILE_BANKING', '2026-01-01'),
                                                                                                                                                               ('TRX-C1-M5', 'col-1', 'PAY-C1-M5', 'C1-A-MOBILE-1', 'C1-M5', 150000, 'MOBILE_BANKING', '2026-01-01'),
                                                                                                                                                               ('TRX-C1-M6', 'col-1', 'PAY-C1-M6', 'C1-A-CASH',     'C1-M6', 100000, 'CASH',           '2026-05-01'),
                                                                                                                                                               ('TRX-C1-M7', 'col-1', 'PAY-C1-M7', 'C1-A-CASH',     'C1-M7',  60000, 'CASH',           '2026-05-01'),
                                                                                                                                                               ('TRX-C1-M8', 'col-1', 'PAY-C1-M8', 'C1-A-CASH',     'C1-M8',  90000, 'CASH',           '2026-05-01');

-- Mise à jour des soldes col-1
-- C1-A-CASH     : 200000+200000+100000+60000+90000 = 650000
-- C1-A-MOBILE-1 : 200000+200000+150000             = 550000
UPDATE financial_account SET balance = 650000 WHERE id = 'C1-A-CASH';
UPDATE financial_account SET balance = 550000 WHERE id = 'C1-A-MOBILE-1';

-- ============================================================
-- COLLECTIVITE 2
-- ============================================================

-- member_payment
INSERT INTO member_payment (id, member_id, membership_fee_id, account_credited_id, amount, payment_mode, creation_date) VALUES
                                                                                                                            ('PAY-C2-M1', 'C1-M1', 'cot-3', 'C2-A-CASH',     120000, 'CASH',           '2026-01-01'),
                                                                                                                            ('PAY-C2-M2', 'C1-M2', 'cot-3', 'C2-A-CASH',     180000, 'CASH',           '2026-01-01'),
                                                                                                                            ('PAY-C2-M3', 'C1-M3', 'cot-3', 'C2-A-CASH',     200000, 'CASH',           '2026-01-01'),
                                                                                                                            ('PAY-C2-M4', 'C1-M4', 'cot-3', 'C2-A-CASH',     200000, 'CASH',           '2026-01-01'),
                                                                                                                            ('PAY-C2-M5', 'C1-M5', 'cot-3', 'C2-A-CASH',     200000, 'CASH',           '2026-01-01'),
                                                                                                                            ('PAY-C2-M6', 'C1-M6', 'cot-3', 'C2-A-CASH',     200000, 'CASH',           '2026-01-01'),
                                                                                                                            ('PAY-C2-M7', 'C1-M7', 'cot-3', 'C2-A-MOBILE-1',  80000, 'MOBILE_BANKING', '2026-01-01'),
                                                                                                                            ('PAY-C2-M8', 'C1-M8', 'cot-3', 'C2-A-MOBILE-1', 120000, 'MOBILE_BANKING', '2026-01-01');

-- collectivity_transaction
INSERT INTO collectivity_transaction (id, collectivity_id, member_payment_id, account_credited_id, member_debited_id, amount, payment_mode, creation_date) VALUES
                                                                                                                                                               ('TRX-C2-M1', 'col-2', 'PAY-C2-M1', 'C2-A-CASH',     'C1-M1', 120000, 'CASH',           '2026-01-01'),
                                                                                                                                                               ('TRX-C2-M2', 'col-2', 'PAY-C2-M2', 'C2-A-CASH',     'C1-M2', 180000, 'CASH',           '2026-01-01'),
                                                                                                                                                               ('TRX-C2-M3', 'col-2', 'PAY-C2-M3', 'C2-A-CASH',     'C1-M3', 200000, 'CASH',           '2026-01-01'),
                                                                                                                                                               ('TRX-C2-M4', 'col-2', 'PAY-C2-M4', 'C2-A-CASH',     'C1-M4', 200000, 'CASH',           '2026-01-01'),
                                                                                                                                                               ('TRX-C2-M5', 'col-2', 'PAY-C2-M5', 'C2-A-CASH',     'C1-M5', 200000, 'CASH',           '2026-01-01'),
                                                                                                                                                               ('TRX-C2-M6', 'col-2', 'PAY-C2-M6', 'C2-A-CASH',     'C1-M6', 200000, 'CASH',           '2026-01-01'),
                                                                                                                                                               ('TRX-C2-M7', 'col-2', 'PAY-C2-M7', 'C2-A-MOBILE-1', 'C1-M7',  80000, 'MOBILE_BANKING', '2026-01-01'),
                                                                                                                                                               ('TRX-C2-M8', 'col-2', 'PAY-C2-M8', 'C2-A-MOBILE-1', 'C1-M8', 120000, 'MOBILE_BANKING', '2026-01-01');

-- Mise à jour des soldes col-2
-- C2-A-CASH     : 120000+180000+200000+200000+200000+200000 = 1100000
-- C2-A-MOBILE-1 : 80000+120000                              = 200000
UPDATE financial_account SET balance = 1100000 WHERE id = 'C2-A-CASH';
UPDATE financial_account SET balance = 200000  WHERE id = 'C2-A-MOBILE-1';

-- ============================================================
-- COLLECTIVITE 3
-- Attention : C3-M3 et C3-M4 en mai paient sur C3-A-MOBILE-1
-- mais le moyen de paiement dans le tableau dit "BANK" → on garde BANK_TRANSFER
-- car le compte est MOBILE mais le mode indiqué est BANK dans les données source
-- ============================================================

-- member_payment - avril 2026
INSERT INTO member_payment (id, member_id, membership_fee_id, account_credited_id, amount, payment_mode, creation_date) VALUES
                                                                                                                            ('PAY-C3-M1-APR', 'C3-M1', 'cot-5', 'C3-A-BANK-1', 25000, 'BANK_TRANSFER', '2026-04-01'),
                                                                                                                            ('PAY-C3-M2-APR', 'C3-M2', 'cot-5', 'C3-A-BANK-1', 25000, 'BANK_TRANSFER', '2026-04-01'),
                                                                                                                            ('PAY-C3-M3-APR', 'C3-M3', 'cot-5', 'C3-A-BANK-1', 25000, 'BANK_TRANSFER', '2026-04-01'),
                                                                                                                            ('PAY-C3-M4-APR', 'C3-M4', 'cot-5', 'C3-A-BANK-1', 25000, 'BANK_TRANSFER', '2026-04-01'),
                                                                                                                            ('PAY-C3-M5-APR', 'C3-M5', 'cot-5', 'C3-A-BANK-2', 25000, 'BANK_TRANSFER', '2026-04-01'),
                                                                                                                            ('PAY-C3-M6-APR', 'C3-M6', 'cot-5', 'C3-A-BANK-2', 25000, 'BANK_TRANSFER', '2026-04-01'),
                                                                                                                            ('PAY-C3-M7-APR', 'C3-M7', 'cot-5', 'C3-A-CASH',   25000, 'CASH',          '2026-04-01'),
                                                                                                                            ('PAY-C3-M8-APR', 'C3-M8', 'cot-5', 'C3-A-CASH',   25000, 'CASH',          '2026-04-01');

-- member_payment - mai 2026
INSERT INTO member_payment (id, member_id, membership_fee_id, account_credited_id, amount, payment_mode, creation_date) VALUES
                                                                                                                            ('PAY-C3-M1-MAY', 'C3-M1', 'cot-5', 'C3-A-BANK-1',   25000, 'BANK_TRANSFER', '2026-05-01'),
                                                                                                                            ('PAY-C3-M2-MAY', 'C3-M2', 'cot-5', 'C3-A-BANK-1',   25000, 'BANK_TRANSFER', '2026-05-01'),
                                                                                                                            ('PAY-C3-M3-MAY', 'C3-M3', 'cot-5', 'C3-A-MOBILE-1', 15000, 'BANK_TRANSFER', '2026-05-01'),
                                                                                                                            ('PAY-C3-M4-MAY', 'C3-M4', 'cot-5', 'C3-A-MOBILE-1', 15000, 'BANK_TRANSFER', '2026-05-01'),
                                                                                                                            ('PAY-C3-M5-MAY', 'C3-M5', 'cot-5', 'C3-A-BANK-2',   20000, 'BANK_TRANSFER', '2026-05-01'),
                                                                                                                            ('PAY-C3-M6-MAY', 'C3-M6', 'cot-5', 'C3-A-BANK-2',   25000, 'BANK_TRANSFER', '2026-05-01'),
                                                                                                                            ('PAY-C3-M7-MAY', 'C3-M7', 'cot-5', 'C3-A-CASH',      5000, 'CASH',          '2026-05-01'),
                                                                                                                            ('PAY-C3-M8-MAY', 'C3-M8', 'cot-5', 'C3-A-CASH',      5000, 'CASH',          '2026-05-01');

-- collectivity_transaction - avril 2026
INSERT INTO collectivity_transaction (id, collectivity_id, member_payment_id, account_credited_id, member_debited_id, amount, payment_mode, creation_date) VALUES
                                                                                                                                                               ('TRX-C3-M1-APR', 'col-3', 'PAY-C3-M1-APR', 'C3-A-BANK-1', 'C3-M1', 25000, 'BANK_TRANSFER', '2026-04-01'),
                                                                                                                                                               ('TRX-C3-M2-APR', 'col-3', 'PAY-C3-M2-APR', 'C3-A-BANK-1', 'C3-M2', 25000, 'BANK_TRANSFER', '2026-04-01'),
                                                                                                                                                               ('TRX-C3-M3-APR', 'col-3', 'PAY-C3-M3-APR', 'C3-A-BANK-1', 'C3-M3', 25000, 'BANK_TRANSFER', '2026-04-01'),
                                                                                                                                                               ('TRX-C3-M4-APR', 'col-3', 'PAY-C3-M4-APR', 'C3-A-BANK-1', 'C3-M4', 25000, 'BANK_TRANSFER', '2026-04-01'),
                                                                                                                                                               ('TRX-C3-M5-APR', 'col-3', 'PAY-C3-M5-APR', 'C3-A-BANK-2', 'C3-M5', 25000, 'BANK_TRANSFER', '2026-04-01'),
                                                                                                                                                               ('TRX-C3-M6-APR', 'col-3', 'PAY-C3-M6-APR', 'C3-A-BANK-2', 'C3-M6', 25000, 'BANK_TRANSFER', '2026-04-01'),
                                                                                                                                                               ('TRX-C3-M7-APR', 'col-3', 'PAY-C3-M7-APR', 'C3-A-CASH',   'C3-M7', 25000, 'CASH',          '2026-04-01'),
                                                                                                                                                               ('TRX-C3-M8-APR', 'col-3', 'PAY-C3-M8-APR', 'C3-A-CASH',   'C3-M8', 25000, 'CASH',          '2026-04-01');

-- collectivity_transaction - mai 2026
INSERT INTO collectivity_transaction (id, collectivity_id, member_payment_id, account_credited_id, member_debited_id, amount, payment_mode, creation_date) VALUES
                                                                                                                                                               ('TRX-C3-M1-MAY', 'col-3', 'PAY-C3-M1-MAY', 'C3-A-BANK-1',   'C3-M1', 25000, 'BANK_TRANSFER', '2026-05-01'),
                                                                                                                                                               ('TRX-C3-M2-MAY', 'col-3', 'PAY-C3-M2-MAY', 'C3-A-BANK-1',   'C3-M2', 25000, 'BANK_TRANSFER', '2026-05-01'),
                                                                                                                                                               ('TRX-C3-M3-MAY', 'col-3', 'PAY-C3-M3-MAY', 'C3-A-MOBILE-1', 'C3-M3', 15000, 'BANK_TRANSFER', '2026-05-01'),
                                                                                                                                                               ('TRX-C3-M4-MAY', 'col-3', 'PAY-C3-M4-MAY', 'C3-A-MOBILE-1', 'C3-M4', 15000, 'BANK_TRANSFER', '2026-05-01'),
                                                                                                                                                               ('TRX-C3-M5-MAY', 'col-3', 'PAY-C3-M5-MAY', 'C3-A-BANK-2',   'C3-M5', 20000, 'BANK_TRANSFER', '2026-05-01'),
                                                                                                                                                               ('TRX-C3-M6-MAY', 'col-3', 'PAY-C3-M6-MAY', 'C3-A-BANK-2',   'C3-M6', 25000, 'BANK_TRANSFER', '2026-05-01'),
                                                                                                                                                               ('TRX-C3-M7-MAY', 'col-3', 'PAY-C3-M7-MAY', 'C3-A-CASH',     'C3-M7',  5000, 'CASH',          '2026-05-01'),
                                                                                                                                                               ('TRX-C3-M8-MAY', 'col-3', 'PAY-C3-M8-MAY', 'C3-A-CASH',     'C3-M8',  5000, 'CASH',          '2026-05-01');

-- Mise à jour des soldes col-3
-- C3-A-BANK-1   : (25000*4 en avr) + (25000*2 en mai) = 100000+50000 = 150000
-- C3-A-BANK-2   : (25000*2 en avr) + (20000+25000 en mai) = 50000+45000 = 95000
-- C3-A-MOBILE-1 : 15000+15000 = 30000
-- C3-A-CASH     : (25000*2 en avr) + (5000*2 en mai) = 50000+10000 = 60000
UPDATE financial_account SET balance = 150000 WHERE id = 'C3-A-BANK-1';
UPDATE financial_account SET balance = 95000  WHERE id = 'C3-A-BANK-2';
UPDATE financial_account SET balance = 30000  WHERE id = 'C3-A-MOBILE-1';
UPDATE financial_account SET balance = 60000  WHERE id = 'C3-A-CASH';

-- Eto le bonus 1
DROP TABLE IF EXISTS activity_attendance CASCADE;
DROP TABLE IF EXISTS activity_occupation_concerned CASCADE;
DROP TABLE IF EXISTS collectivity_activity CASCADE;

CREATE TABLE collectivity_activity (
                                       id                      VARCHAR(50)  PRIMARY KEY,
                                       collectivity_id         VARCHAR(50)  NOT NULL REFERENCES collectivity(id),
                                       label                   VARCHAR(255) NOT NULL,
                                       activity_type           VARCHAR(20)  NOT NULL
                                           CHECK (activity_type IN ('MEETING', 'TRAINING', 'PUNCTUAL')),
                                       executive_date          DATE,
                                       recurrence_week_ordinal INTEGER CHECK (recurrence_week_ordinal BETWEEN 1 AND 5),
                                       recurrence_day_of_week  VARCHAR(2)
                                           CHECK (recurrence_day_of_week IN ('MO','TU','WE','TH','FR','SA','SU')),
                                       CONSTRAINT chk_recurrence_xor_date
                                           CHECK (
                                               (executive_date IS NOT NULL AND recurrence_week_ordinal IS NULL AND recurrence_day_of_week IS NULL)
                                                   OR
                                               (executive_date IS NULL AND recurrence_week_ordinal IS NOT NULL AND recurrence_day_of_week IS NOT NULL)
                                               )
);

CREATE TABLE activity_occupation_concerned (
                                               activity_id VARCHAR(50) NOT NULL REFERENCES collectivity_activity(id) ON DELETE CASCADE,
                                               occupation  VARCHAR(50) NOT NULL
                                                   CHECK (occupation IN ('JUNIOR','SENIOR','SECRETARY','TREASURER','VICE_PRESIDENT','PRESIDENT')),
                                               PRIMARY KEY (activity_id, occupation)
);

CREATE TABLE activity_attendance (
                                     id            VARCHAR(50) PRIMARY KEY,
                                     activity_id   VARCHAR(50) NOT NULL REFERENCES collectivity_activity(id),
                                     member_id     VARCHAR(50) NOT NULL REFERENCES member(id),
                                     status        VARCHAR(20) NOT NULL DEFAULT 'UNDEFINED'
                                         CHECK (status IN ('MISSING', 'ATTENDED', 'UNDEFINED')),
                                     activity_date DATE        NOT NULL,
                                     UNIQUE (activity_id, member_id, activity_date)
);

INSERT INTO collectivity_activity (id, collectivity_id, label, activity_type, executive_date, recurrence_week_ordinal, recurrence_day_of_week)
VALUES
    ('act-1', 'col-1', 'AG1',              'MEETING',  NULL,         1, 'SA'),
    ('act-2', 'col-1', 'Formation de base', 'TRAINING', NULL,         2, 'SU'),
    ('act-3', 'col-2', 'AG2',              'MEETING',  NULL,         1, 'SU'),
    ('act-4', 'col-2', 'Formation de base', 'TRAINING', NULL,         3, 'SU'),
    ('act-5', 'col-2', 'Perfectionnement',  'PUNCTUAL', '3036-04-30', NULL, NULL),
    ('act-6', 'col-3', 'AG3',              'MEETING',  NULL,         1, 'FR'),
    ('act-7', 'col-3', 'Formation de base', 'TRAINING', NULL,         4, 'WE');

INSERT INTO activity_occupation_concerned (activity_id, occupation) VALUES
                                                                        ('act-1','JUNIOR'),('act-1','SENIOR'),('act-1','SECRETARY'),
                                                                        ('act-1','TREASURER'),('act-1','VICE_PRESIDENT'),('act-1','PRESIDENT'),

                                                                        ('act-2','JUNIOR'),

                                                                        ('act-3','JUNIOR'),('act-3','SENIOR'),('act-3','SECRETARY'),
                                                                        ('act-3','TREASURER'),('act-3','VICE_PRESIDENT'),('act-3','PRESIDENT'),

                                                                        ('act-4','JUNIOR'),

                                                                        ('act-5','SENIOR'),

                                                                        ('act-6','JUNIOR'),('act-6','SENIOR'),('act-6','SECRETARY'),
                                                                        ('act-6','TREASURER'),('act-6','VICE_PRESIDENT'),('act-6','PRESIDENT'),

                                                                        ('act-7','JUNIOR');

-- Mars 2026 (1er samedi = 07/03/2026)
INSERT INTO activity_attendance (id, activity_id, member_id, status, activity_date) VALUES
                                                                                        ('ATT-act1-M1-mar', 'act-1', 'C1-M1', 'ATTENDED', '2026-03-07'),
                                                                                        ('ATT-act1-M2-mar', 'act-1', 'C1-M2', 'ATTENDED', '2026-03-07'),
                                                                                        ('ATT-act1-M3-mar', 'act-1', 'C1-M3', 'ATTENDED', '2026-03-07'),
                                                                                        ('ATT-act1-M4-mar', 'act-1', 'C1-M4', 'ATTENDED', '2026-03-07'),
                                                                                        ('ATT-act1-M5-mar', 'act-1', 'C1-M5', 'ATTENDED', '2026-03-07'),
                                                                                        ('ATT-act1-M6-mar', 'act-1', 'C1-M6', 'ATTENDED', '2026-03-07'),
                                                                                        ('ATT-act1-M7-mar', 'act-1', 'C1-M7', 'MISSING',  '2026-03-07'),
                                                                                        ('ATT-act1-M8-mar', 'act-1', 'C1-M8', 'MISSING',  '2026-03-07');

-- Avril 2026 (1er samedi = 04/04/2026)
INSERT INTO activity_attendance (id, activity_id, member_id, status, activity_date) VALUES
                                                                                        ('ATT-act1-M1-avr', 'act-1', 'C1-M1', 'ATTENDED', '2026-04-04'),
                                                                                        ('ATT-act1-M2-avr', 'act-1', 'C1-M2', 'ATTENDED', '2026-04-04'),
                                                                                        ('ATT-act1-M3-avr', 'act-1', 'C1-M3', 'MISSING',  '2026-04-04'),
                                                                                        ('ATT-act1-M4-avr', 'act-1', 'C1-M4', 'MISSING',  '2026-04-04'),
                                                                                        ('ATT-act1-M5-avr', 'act-1', 'C1-M5', 'ATTENDED', '2026-04-04'),
                                                                                        ('ATT-act1-M6-avr', 'act-1', 'C1-M6', 'ATTENDED', '2026-04-04'),
                                                                                        ('ATT-act1-M7-avr', 'act-1', 'C1-M7', 'ATTENDED', '2026-04-04'),
                                                                                        ('ATT-act1-M8-avr', 'act-1', 'C1-M8', 'ATTENDED', '2026-04-04');

-- Mars 2026 (1er dimanche = 08/03/2026)
INSERT INTO activity_attendance (id, activity_id, member_id, status, activity_date) VALUES
                                                                                        ('ATT-act3-M1-mar', 'act-3', 'C1-M1', 'ATTENDED', '2026-03-08'),
                                                                                        ('ATT-act3-M2-mar', 'act-3', 'C1-M2', 'ATTENDED', '2026-03-08'),
                                                                                        ('ATT-act3-M3-mar', 'act-3', 'C1-M3', 'MISSING',  '2026-03-08'),
                                                                                        ('ATT-act3-M4-mar', 'act-3', 'C1-M4', 'MISSING',  '2026-03-08'),
                                                                                        ('ATT-act3-M5-mar', 'act-3', 'C1-M5', 'ATTENDED', '2026-03-08'),
                                                                                        ('ATT-act3-M6-mar', 'act-3', 'C1-M6', 'ATTENDED', '2026-03-08'),
                                                                                        ('ATT-act3-M7-mar', 'act-3', 'C1-M7', 'ATTENDED', '2026-03-08'),
                                                                                        ('ATT-act3-M8-mar', 'act-3', 'C1-M8', 'ATTENDED', '2026-03-08');

-- Avril 2026 (1er dimanche = 05/04/2026)
INSERT INTO activity_attendance (id, activity_id, member_id, status, activity_date) VALUES
                                                                                        ('ATT-act3-M1-avr', 'act-3', 'C1-M1', 'ATTENDED', '2026-04-05'),
                                                                                        ('ATT-act3-M2-avr', 'act-3', 'C1-M2', 'ATTENDED', '2026-04-05'),
                                                                                        ('ATT-act3-M3-avr', 'act-3', 'C1-M3', 'MISSING',  '2026-04-05'),
                                                                                        ('ATT-act3-M4-avr', 'act-3', 'C1-M4', 'ATTENDED', '2026-04-05'),
                                                                                        ('ATT-act3-M5-avr', 'act-3', 'C1-M5', 'ATTENDED', '2026-04-05'),
                                                                                        ('ATT-act3-M6-avr', 'act-3', 'C1-M6', 'ATTENDED', '2026-04-05'),
                                                                                        ('ATT-act3-M7-avr', 'act-3', 'C1-M7', 'ATTENDED', '2026-04-05'),
                                                                                        ('ATT-act3-M8-avr', 'act-3', 'C1-M8', 'MISSING',  '2026-04-05');

INSERT INTO activity_attendance (id, activity_id, member_id, status, activity_date) VALUES
                                                                                        ('ATT-act5-M1', 'act-5', 'C1-M1', 'ATTENDED',  '2026-04-30'),
                                                                                        ('ATT-act5-M2', 'act-5', 'C1-M2', 'ATTENDED',  '2026-04-30'),
                                                                                        ('ATT-act5-M3', 'act-5', 'C1-M3', 'ATTENDED',  '2026-04-30'),
                                                                                        ('ATT-act5-M4', 'act-5', 'C1-M4', 'MISSING',   '2026-04-30'),
                                                                                        ('ATT-act5-M5', 'act-5', 'C1-M5', 'UNDEFINED', '2026-04-30'),
                                                                                        ('ATT-act5-M6', 'act-5', 'C1-M6', 'UNDEFINED', '2026-04-30'),
                                                                                        ('ATT-act5-M7', 'act-5', 'C1-M7', 'UNDEFINED', '2026-04-30'),
                                                                                        ('ATT-act5-M8', 'act-5', 'C1-M8', 'UNDEFINED', '2026-04-30');


-- Mars 2026 (1er vendredi = 06/03/2026)
INSERT INTO activity_attendance (id, activity_id, member_id, status, activity_date) VALUES
                                                                                        ('ATT-act6-M1-mar', 'act-6', 'C3-M1', 'ATTENDED', '2026-03-06'),
                                                                                        ('ATT-act6-M2-mar', 'act-6', 'C3-M2', 'ATTENDED', '2026-03-06'),
                                                                                        ('ATT-act6-M3-mar', 'act-6', 'C3-M3', 'ATTENDED', '2026-03-06'),
                                                                                        ('ATT-act6-M4-mar', 'act-6', 'C3-M4', 'ATTENDED', '2026-03-06'),
                                                                                        ('ATT-act6-M5-mar', 'act-6', 'C3-M5', 'ATTENDED', '2026-03-06'),
                                                                                        ('ATT-act6-M6-mar', 'act-6', 'C3-M6', 'ATTENDED', '2026-03-06'),
                                                                                        ('ATT-act6-M7-mar', 'act-6', 'C3-M7', 'MISSING',  '2026-03-06'),
                                                                                        ('ATT-act6-M8-mar', 'act-6', 'C3-M8', 'MISSING',  '2026-03-06');

-- Avril 2026 (1er vendredi = 03/04/2026)
INSERT INTO activity_attendance (id, activity_id, member_id, status, activity_date) VALUES
                                                                                        ('ATT-act6-M1-avr', 'act-6', 'C3-M1', 'ATTENDED', '2026-04-03'),
                                                                                        ('ATT-act6-M2-avr', 'act-6', 'C3-M2', 'ATTENDED', '2026-04-03'),
                                                                                        ('ATT-act6-M3-avr', 'act-6', 'C3-M3', 'MISSING',  '2026-04-03'),
                                                                                        ('ATT-act6-M4-avr', 'act-6', 'C3-M4', 'MISSING',  '2026-04-03'),
                                                                                        ('ATT-act6-M5-avr', 'act-6', 'C3-M5', 'ATTENDED', '2026-04-03'),
                                                                                        ('ATT-act6-M6-avr', 'act-6', 'C3-M6', 'ATTENDED', '2026-04-03'),
                                                                                        ('ATT-act6-M7-avr', 'act-6', 'C3-M7', 'MISSING',  '2026-04-03'),
                                                                                        ('ATT-act6-M8-avr', 'act-6', 'C3-M8', 'ATTENDED', '2026-04-03'),
                                                                                        -- C1-M1 membre externe présent à l'activité de col-3
                                                                                        ('ATT-act6-C1M1-avr', 'act-6', 'C1-M1', 'ATTENDED', '2026-04-03');