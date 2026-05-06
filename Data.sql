
-- ============================================================
-- TABLEAU 1 : COLLECTIVITES
-- ============================================================
INSERT INTO collectivity (id, number, name, location, creation_date) VALUES
                                                                         ('col-1', 1, 'Mpanorina',      'Ambatondrazaka', '2025-01-01'),
                                                                         ('col-2', 2, 'Dobo voalohany', 'Ambatondrazaka', '2025-01-01'),
                                                                         ('col-3', 3, 'Tantely mamy',   'Brickaville',    '2025-01-01');

-- ============================================================
-- TABLEAU 2 : MEMBRES DE LA COLLECTIVITE 1
-- collectivity_id = col-1 (collectivité principale du membre)
-- ============================================================
INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession,
                    phone_number, email, occupation, collectivity_id, admission_date) VALUES
                                                                                          ('C1-M1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'MALE',   'Lot II V M Ambato.', 'Riziculteur',  341234567, 'member.1@fed-agri.mg', 'PRESIDENT',      'col-1', '2024-01-01'),
                                                                                          ('C1-M2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'MALE',   'Lot II F Ambato.',   'Agriculteur',  321234567, 'member.2@fed-agri.mg', 'VICE_PRESIDENT', 'col-1', '2024-01-01'),
                                                                                          ('C1-M3', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'MALE',   'Lot II J Ambato.',   'Collecteur',   331234567, 'member.3@fed-agri.mg', 'SECRETARY',      'col-1', '2024-01-01'),
                                                                                          ('C1-M4', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato.', 'Distributeur', 381234567, 'member.4@fed-agri.mg', 'TREASURER',      'col-1', '2024-01-01'),
                                                                                          ('C1-M5', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'MALE',   'Lot UV 80 Ambato.',  'Riziculteur',  373434567, 'member.5@fed-agri.mg', 'SENIOR',         'col-1', '2024-01-01'),
                                                                                          ('C1-M6', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato.',   'Riziculteur',  372234567, 'member.6@fed-agri.mg', 'SENIOR',         'col-1', '2024-01-01'),
                                                                                          ('C1-M7', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'MALE',   'Lot UV 7 Ambato.',   'Riziculteur',  374234567, 'member.7@fed-agri.mg', 'SENIOR',         'col-1', '2024-01-01'),
                                                                                          ('C1-M8', 'Prénom membre 8', 'Nom membre 6', '1975-08-20', 'MALE',   'Lot UV 8 Ambato.',   'Riziculteur',  370234567, 'member.8@fed-agri.mg', 'SENIOR',         'col-1', '2024-01-01');

INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession,
                    phone_number, email, occupation, collectivity_id, admission_date) VALUES
                                                                                          ('C2-M1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'MALE',   'Lot II V M Ambato.', 'Riziculteur',  341234567, 'member.1@fed-agri.mg', 'PRESIDENT',      'col-2', '2024-01-01'),
                                                                                          ('C2-M2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'MALE',   'Lot II F Ambato.',   'Agriculteur',  321234567, 'member.2@fed-agri.mg', 'VICE_PRESIDENT', 'col-2', '2024-01-01'),
                                                                                          ('C2-M3', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'MALE',   'Lot II J Ambato.',   'Collecteur',   331234567, 'member.3@fed-agri.mg', 'SECRETARY',      'col-2', '2024-01-01'),
                                                                                          ('C2-M4', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato.', 'Distributeur', 381234567, 'member.4@fed-agri.mg', 'TREASURER',      'col-2', '2024-01-01'),
                                                                                          ('C2-M5', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'MALE',   'Lot UV 80 Ambato.',  'Riziculteur',  373434567, 'member.5@fed-agri.mg', 'SENIOR',         'col-2', '2024-01-01'),
                                                                                          ('C2-M6', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato.',   'Riziculteur',  372234567, 'member.6@fed-agri.mg', 'SENIOR',         'col-2', '2024-01-01'),
                                                                                          ('C2-M7', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'MALE',   'Lot UV 7 Ambato.',   'Riziculteur',  374234567, 'member.7@fed-agri.mg', 'SENIOR',         'col-2', '2024-01-01'),
                                                                                          ('C2-M8', 'Prénom membre 8', 'Nom membre 6', '1975-08-20', 'MALE',   'Lot UV 8 Ambato.',   'Riziculteur',  370234567, 'member.8@fed-agri.mg', 'SENIOR',         'col-2', '2024-01-01');

-- ============================================================
-- TABLEAU 4 : MEMBRES DE LA COLLECTIVITE 3
-- ============================================================
INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession,
                    phone_number, email, occupation, collectivity_id, admission_date) VALUES
                                                                                          ('C3-M1', 'Prénom membre 9',  'Nom membre 9',  '1988-01-02', 'MALE',   'Lot 33 J Antsirabe',   'Apiculteur',  34034567,  'member.9@fed-agri.mg',  'PRESIDENT',      'col-3', '2024-01-01'),
                                                                                          ('C3-M2', 'Prénom membre 10', 'Nom membre 10', '1982-03-05', 'MALE',   'Lot 2 J Antsirabe',    'Agriculteur', 338634567, 'member.10@fed-agri.mg', 'VICE_PRESIDENT', 'col-3', '2024-01-01'),
                                                                                          ('C3-M3', 'Prénom membre 11', 'Nom membre 11', '1992-03-12', 'MALE',   'Lot 8 KM Antsirabe',   'Collecteur',  338234567, 'member.11@fed-agri.mg', 'SECRETARY',      'col-3', '2024-01-01'),
                                                                                          ('C3-M4', 'Prénom membre 12', 'Nom membre 12', '1988-05-10', 'FEMALE', 'Lot A K 50 Antsirabe', 'Distributeur',382334567, 'member.12@fed-agri.mg', 'TREASURER',      'col-3', '2024-01-01'),
                                                                                          ('C3-M5', 'Prénom membre 13', 'Nom membre 13', '1999-08-11', 'MALE',   'Lot UV 80 Antsirabe',  'Apiculteur',  373365567, 'member.13@fed-agri.mg', 'SENIOR',         'col-3', '2024-01-01'),
                                                                                          ('C3-M6', 'Prénom membre 14', 'Nom membre 14', '1998-08-09', 'FEMALE', 'Lot UV 6 Antsirabe',   'Apiculteur',  378234567, 'member.14@fed-agri.mg', 'SENIOR',         'col-3', '2024-01-01'),
                                                                                          ('C3-M7', 'Prénom membre 15', 'Nom membre 15', '1998-01-13', 'MALE',   'Lot UV 7 Antsirabe',   'Apiculteur',  374914567, 'member.15@fed-agri.mg', 'SENIOR',         'col-3', '2024-01-01'),
                                                                                          ('C3-M8', 'Prénom membre 16', 'Nom membre 16', '1975-08-02', 'MALE',   'Lot UV 8 Antsirabe',   'Apiculteur',  370634567, 'member.16@fed-agri.mg', 'SENIOR',         'col-3', '2024-01-01');

-- ============================================================
-- TABLEAU 2 & 3 : LIAISON MEMBRE <-> COLLECTIVITE
-- Chaque membre avec son occupation dans chaque collectivité
--
-- col-1 (Tableau 2) :
--   C1-M1 -> PRESIDENT, C1-M2 -> VICE_PRESIDENT,
--   C1-M3 -> SECRETARY, C1-M4 -> TREASURER,
--   C1-M5 -> SENIOR, C1-M6 -> SENIOR, C1-M7 -> SENIOR, C1-M8 -> SENIOR
--
-- col-2 (Tableau 3) :
--   C1-M1 -> SENIOR (Confirmé), C1-M2 -> SENIOR (Confirmé),
--   C1-M3 -> SENIOR (Confirmé), C1-M4 -> SENIOR (Confirmé),
--   C1-M5 -> PRESIDENT, C1-M6 -> VICE_PRESIDENT,
--   C1-M7 -> SECRETARY, C1-M8 -> TREASURER
--
-- col-3 (Tableau 4) :
--   C3-M1 -> PRESIDENT, C3-M2 -> VICE_PRESIDENT,
--   C3-M3 -> SECRETARY, C3-M4 -> TREASURER,
--   C3-M5 -> SENIOR, C3-M6 -> SENIOR, C3-M7 -> SENIOR, C3-M8 -> SENIOR
-- ============================================================
--=======================
-- STRUCTURES DES COLLECTIVITES
-- col-1 : président=C1-M1, VP=C1-M2, trésorier=C1-M4, secrétaire=C1-M3
-- col-2 : président=C1-M5, VP=C1-M6, trésorier=C1-M8, secrétaire=C1-M7
-- col-3 : président=C3-M1, VP=C3-M2, trésorier=C3-M4, secrétaire=C3-M3
-- ============================================================
INSERT INTO collectivity_structure (collectivity_id, president_id, vice_president_id, treasurer_id, secretary_id) VALUES
                                                                                                                      ('col-1', 'C1-M1', 'C1-M2', 'C1-M4', 'C1-M3'),
                                                                                                                      ('col-2', 'C1-M5', 'C1-M6', 'C1-M8', 'C1-M7'),
                                                                                                                      ('col-3', 'C3-M1', 'C3-M2', 'C3-M4', 'C3-M3');

-- ============================================================
-- PARRAINAGES (member_referee)
-- Tableau 2 - col-1 :
--   C1-M1 : Aucun parrain
--   C1-M2 : Aucun parrain
--   C1-M3 : C1-M1 ; C1-M2
--   C1-M4 : C1-M1 ; C1-M2
--   C1-M5 : C1-M1 ; C1-M2
--   C1-M6 : C1-M1 ; C1-M2
--   C1-M7 : C1-M1 ; C1-M2
--   C1-M8 : C1-M6 ; C1-M7
-- Tableau 3 - col-2 (même membres, même parrainages déjà insérés) :
--   C1-M1 (=C2-M1) : Aucun parrain
--   C1-M2 (=C2-M2) : Aucun parrain
--   C1-M3 (=C2-M3) : C1-M1 ; C1-M2  -> déjà inséré
--   C1-M4 (=C2-M4) : C1-M1 ; C1-M2  -> déjà inséré
--   C1-M5 (=C2-M5) : C1-M1 ; C1-M2  -> déjà inséré
--   C1-M6 (=C2-M6) : C1-M1 ; C1-M2  -> déjà inséré
--   C1-M7 (=C2-M7) : C1-M1 ; C1-M2  -> déjà inséré
--   C1-M8 (=C2-M8) : C1-M6 ; C1-M7  -> déjà inséré
-- Tableau 4 - col-3 :
--   C3-M1 : C1-M1 ; C1-M2
--   C3-M2 : C1-M1 ; C1-M2
--   C3-M3 : C3-M1 ; C3-M2
--   C3-M4 : C3-M1 ; C3-M2
--   C3-M5 : C3-M1 ; C3-M2
--   C3-M6 : C3-M1 ; C3-M2
--   C3-M7 : C3-M1 ; C3-M2
--   C3-M8 : C3-M1 ; C3-M2
-- ============================================================
INSERT INTO member_referee (member_id, referee_id) VALUES
                                                       -- col-1 parrainages
                                                       ('C1-M3', 'C1-M1'),
                                                       ('C1-M3', 'C1-M2'),
                                                       ('C1-M4', 'C1-M1'),
                                                       ('C1-M4', 'C1-M2'),
                                                       ('C1-M5', 'C1-M1'),
                                                       ('C1-M5', 'C1-M2'),
                                                       ('C1-M6', 'C1-M1'),
                                                       ('C1-M6', 'C1-M2'),
                                                       ('C1-M7', 'C1-M1'),
                                                       ('C1-M7', 'C1-M2'),
                                                       ('C1-M8', 'C1-M6'),
                                                       ('C1-M8', 'C1-M7'),
                                                       -- col-3 parrainages
                                                       ('C3-M1', 'C1-M1'),
                                                       ('C3-M1', 'C1-M2'),
                                                       ('C3-M2', 'C1-M1'),
                                                       ('C3-M2', 'C1-M2'),
                                                       ('C3-M3', 'C3-M1'),
                                                       ('C3-M3', 'C3-M2'),
                                                       ('C3-M4', 'C3-M1'),
                                                       ('C3-M4', 'C3-M2'),
                                                       ('C3-M5', 'C3-M1'),
                                                       ('C3-M5', 'C3-M2'),
                                                       ('C3-M6', 'C3-M1'),
                                                       ('C3-M6', 'C3-M2'),
                                                       ('C3-M7', 'C3-M1'),
                                                       ('C3-M7', 'C3-M2'),
                                                       ('C3-M8', 'C3-M1'),
                                                       ('C3-M8', 'C3-M2');

-- ============================================================
-- COMPTES FINANCIERS
-- col-1 : C1-A-CASH (CASH, 0), C1-A-MOBILE-1 (ORANGE_MONEY, Mpanorina, 0370489612, 0)
-- col-2 : C2-A-CASH (CASH, 0), C2-A-MOBILE-1 (ORANGE_MONEY, Dobo voalohany, 0320489612, 0)
-- col-3 : C3-A-CASH (CASH, 0)
-- ============================================================
INSERT INTO financial_account (id, collectivity_id, account_type, balance) VALUES
                                                                               ('C1-A-CASH',     'col-1', 'CASH',   0),
                                                                               ('C1-A-MOBILE-1', 'col-1', 'MOBILE', 0),
                                                                               ('C2-A-CASH',     'col-2', 'CASH',   0),
                                                                               ('C2-A-MOBILE-1', 'col-2', 'MOBILE', 0),
                                                                               ('C3-A-CASH',     'col-3', 'CASH',   0);

INSERT INTO mobile_money_account_detail (id, holder_name, mobile_banking_service, mobile_number) VALUES
                                                                                                     ('C1-A-MOBILE-1', 'Mpanorina',      'ORANGE_MONEY', 370489612),
                                                                                                     ('C2-A-MOBILE-1', 'Dobo voalohany', 'ORANGE_MONEY', 320489612);

-- ============================================================
-- COTISATIONS
-- Tableau 5 : cot-1 | col-1 | Cotisation annuelle | ANNUALLY | 01/01/2026 | 100 000
-- Tableau 6 : cot-2 | col-2 | Cotisation annuelle | ANNUALLY | 01/01/2026 | 100 000
-- Tableau 7 : cot-3 | col-3 | Cotisation annuelle | ANNUALLY | 01/01/2026 |  50 000
-- ============================================================
INSERT INTO membership_fee (id, collectivity_id, label, amount, frequency, eligible_from, status) VALUES
                                                                                                      ('cot-1', 'col-1', 'Cotisation annuelle', 100000, 'ANNUALLY', '2026-01-01', 'ACTIVE'),
                                                                                                      ('cot-2', 'col-2', 'Cotisation annuelle', 100000, 'ANNUALLY', '2026-01-01', 'ACTIVE'),
                                                                                                      ('cot-3', 'col-3', 'Cotisation annuelle',  50000, 'ANNUALLY', '2026-01-01', 'ACTIVE');

-- ============================================================
-- TABLEAU 8 : PAIEMENTS COLLECTIVITE 1
-- C1-M1 | 100 000 | C1-A-CASH | CASH | 01/01/2026
-- C1-M2 | 100 000 | C1-A-CASH | CASH | 01/01/2026
-- C1-M3 | 100 000 | C1-A-CASH | CASH | 01/01/2026
-- C1-M4 | 100 000 | C1-A-CASH | CASH | 01/01/2026
-- C1-M5 | 100 000 | C1-A-CASH | CASH | 01/01/2026
-- C1-M6 | 100 000 | C1-A-CASH | CASH | 01/01/2026
-- C1-M7 |  60 000 | C1-A-CASH | CASH | 01/01/2026
-- C1-M8 |  90 000 | C1-A-CASH | CASH | 01/01/2026
-- ============================================================
INSERT INTO member_payment (id, member_id, membership_fee_id, account_credited_id, amount, payment_mode, creation_date) VALUES
                                                                                                                            ('pay-c1-m1', 'C1-M1', 'cot-1', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                            ('pay-c1-m2', 'C1-M2', 'cot-1', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                            ('pay-c1-m3', 'C1-M3', 'cot-1', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                            ('pay-c1-m4', 'C1-M4', 'cot-1', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                            ('pay-c1-m5', 'C1-M5', 'cot-1', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                            ('pay-c1-m6', 'C1-M6', 'cot-1', 'C1-A-CASH', 100000, 'CASH', '2026-01-01'),
                                                                                                                            ('pay-c1-m7', 'C1-M7', 'cot-1', 'C1-A-CASH',  60000, 'CASH', '2026-01-01'),
                                                                                                                            ('pay-c1-m8', 'C1-M8', 'cot-1', 'C1-A-CASH',  90000, 'CASH', '2026-01-01');

-- ============================================================
-- TABLEAU 9 : TRANSACTIONS COLLECTIVITE 1
-- ============================================================
INSERT INTO collectivity_transaction (id, collectivity_id, member_payment_id, account_credited_id, member_debited_id, amount, payment_mode, creation_date) VALUES
                                                                                                                                                               ('tx-c1-m1', 'col-1', 'pay-c1-m1', 'C1-A-CASH', 'C1-M1', 100000, 'CASH', '2026-01-01'),
                                                                                                                                                               ('tx-c1-m2', 'col-1', 'pay-c1-m2', 'C1-A-CASH', 'C1-M2', 100000, 'CASH', '2026-01-01'),
                                                                                                                                                               ('tx-c1-m3', 'col-1', 'pay-c1-m3', 'C1-A-CASH', 'C1-M3', 100000, 'CASH', '2026-01-01'),
                                                                                                                                                               ('tx-c1-m4', 'col-1', 'pay-c1-m4', 'C1-A-CASH', 'C1-M4', 100000, 'CASH', '2026-01-01'),
                                                                                                                                                               ('tx-c1-m5', 'col-1', 'pay-c1-m5', 'C1-A-CASH', 'C1-M5', 100000, 'CASH', '2026-01-01'),
                                                                                                                                                               ('tx-c1-m6', 'col-1', 'pay-c1-m6', 'C1-A-CASH', 'C1-M6', 100000, 'CASH', '2026-01-01'),
                                                                                                                                                               ('tx-c1-m7', 'col-1', 'pay-c1-m7', 'C1-A-CASH', 'C1-M7',  60000, 'CASH', '2026-01-01'),
                                                                                                                                                               ('tx-c1-m8', 'col-1', 'pay-c1-m8', 'C1-A-CASH', 'C1-M8',  90000, 'CASH', '2026-01-01');

-- Solde C1-A-CASH : 100000*6 + 60000 + 90000 = 750 000
UPDATE financial_account SET balance = 750000 WHERE id = 'C1-A-CASH';

-- ============================================================
-- TABLEAU 10 : PAIEMENTS COLLECTIVITE 2
-- C1-M1 (=C2-M1) |  60 000 | C2-A-CASH     | CASH         | 01/01/2026
-- C1-M2 (=C2-M2) |  90 000 | C2-A-CASH     | CASH         | 01/01/2026
-- C1-M3 (=C2-M3) | 100 000 | C2-A-CASH     | CASH         | 01/01/2026
-- C1-M4 (=C2-M4) | 100 000 | C2-A-CASH     | CASH         | 01/01/2026
-- C1-M5 (=C2-M5) | 100 000 | C2-A-CASH     | CASH         | 01/01/2026
-- C1-M6 (=C2-M6) | 100 000 | C2-A-CASH     | CASH         | 01/01/2026
-- C1-M7 (=C2-M7) |  40 000 | C2-A-MOBILE-1 | MOBILE MONEY | 01/01/2026
-- C1-M8 (=C2-M8) |  60 000 | C2-A-MOBILE-1 | MOBILE MONEY | 01/01/2026
-- ============================================================
INSERT INTO member_payment (id, member_id, membership_fee_id, account_credited_id, amount, payment_mode, creation_date) VALUES
                                                                                                                            ('pay-c2-m1', 'C1-M1', 'cot-2', 'C2-A-CASH',      60000, 'CASH',          '2026-01-01'),
                                                                                                                            ('pay-c2-m2', 'C1-M2', 'cot-2', 'C2-A-CASH',      90000, 'CASH',          '2026-01-01'),
                                                                                                                            ('pay-c2-m3', 'C1-M3', 'cot-2', 'C2-A-CASH',     100000, 'CASH',          '2026-01-01'),
                                                                                                                            ('pay-c2-m4', 'C1-M4', 'cot-2', 'C2-A-CASH',     100000, 'CASH',          '2026-01-01'),
                                                                                                                            ('pay-c2-m5', 'C1-M5', 'cot-2', 'C2-A-CASH',     100000, 'CASH',          '2026-01-01'),
                                                                                                                            ('pay-c2-m6', 'C1-M6', 'cot-2', 'C2-A-CASH',     100000, 'CASH',          '2026-01-01'),
                                                                                                                            ('pay-c2-m7', 'C1-M7', 'cot-2', 'C2-A-MOBILE-1',  40000, 'MOBILE_BANKING','2026-01-01'),
                                                                                                                            ('pay-c2-m8', 'C1-M8', 'cot-2', 'C2-A-MOBILE-1',  60000, 'MOBILE_BANKING','2026-01-01');

-- ============================================================
-- TABLEAU 11 : TRANSACTIONS COLLECTIVITE 2
-- ============================================================
INSERT INTO collectivity_transaction (id, collectivity_id, member_payment_id, account_credited_id, member_debited_id, amount, payment_mode, creation_date) VALUES
                                                                                                                                                               ('tx-c2-m1', 'col-2', 'pay-c2-m1', 'C2-A-CASH',     'C1-M1',  60000, 'CASH',          '2026-01-01'),
                                                                                                                                                               ('tx-c2-m2', 'col-2', 'pay-c2-m2', 'C2-A-CASH',     'C1-M2',  90000, 'CASH',          '2026-01-01'),
                                                                                                                                                               ('tx-c2-m3', 'col-2', 'pay-c2-m3', 'C2-A-CASH',     'C1-M3', 100000, 'CASH',          '2026-01-01'),
                                                                                                                                                               ('tx-c2-m4', 'col-2', 'pay-c2-m4', 'C2-A-CASH',     'C1-M4', 100000, 'CASH',          '2026-01-01'),
                                                                                                                                                               ('tx-c2-m5', 'col-2', 'pay-c2-m5', 'C2-A-CASH',     'C1-M5', 100000, 'CASH',          '2026-01-01'),
                                                                                                                                                               ('tx-c2-m6', 'col-2', 'pay-c2-m6', 'C2-A-CASH',     'C1-M6', 100000, 'CASH',          '2026-01-01'),
                                                                                                                                                               ('tx-c2-m7', 'col-2', 'pay-c2-m7', 'C2-A-MOBILE-1', 'C1-M7',  40000, 'MOBILE_BANKING','2026-01-01'),
                                                                                                                                                               ('tx-c2-m8', 'col-2', 'pay-c2-m8', 'C2-A-MOBILE-1', 'C1-M8',  60000, 'MOBILE_BANKING','2026-01-01');

-- Solde C2-A-CASH     : 60000+90000+100000+100000+100000+100000 = 550 000
UPDATE financial_account SET balance = 550000 WHERE id = 'C2-A-CASH';
-- Solde C2-A-MOBILE-1 : 40000+60000 = 100 000
UPDATE financial_account SET balance = 100000 WHERE id = 'C2-A-MOBILE-1';
