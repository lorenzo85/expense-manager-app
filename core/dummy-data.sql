-- Password is `aPassword`
INSERT INTO user (accountEnabled, accountExpired, accountLocked, credentialsExpired, password, username) VALUES (1, 0, 0, 0, '$2a$04$U8MQ3yty0R7x5zAnMriyn.XoveCOjehAzgZuzQE8c/AMJy1YNBGqS', 'mark');
INSERT INTO user (accountEnabled, accountExpired, accountLocked, credentialsExpired, password, username) VALUES (1, 0, 0, 0, '$2a$04$U8MQ3yty0R7x5zAnMriyn.XoveCOjehAzgZuzQE8c/AMJy1YNBGqS', 'aUsername');
INSERT INTO user_role(role, user_id) VALUES ('ROLE_ADMIN', (SELECT id FROM user WHERE username='mark'));
INSERT INTO user_role(role, user_id) VALUES ('ROLE_USER', (SELECT id FROM user WHERE username='aUsername'));


INSERT INTO yard (contract_total_amount, description, name) VALUES (43.23, 'Super duper description.', 'Palazzina Abitazione 1, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (543.23, 'Super duper description.', 'Palazzina Abitazione 2, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (643.23, 'Super duper description.', 'Palazzina Carnate 3, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (743.23, 'Super duper description.', 'Palazzina Carnate 4, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (1143.23, 'Super duper description.', 'Palazzina Carnate 5, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (2243.23, 'Super duper description.', 'Palazzina Carnate 6, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (3343.23, 'Super duper description.', 'Palazzina Carnate 7, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (46.23, 'Super duper description.', 'Palazzina Carnate 8, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (13.23, 'Super duper description.', 'Palazzina Carnate 9, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (73.23, 'Super duper description.', 'Palazzina Carnate 10, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (123.23, 'Super duper description.', 'Palazzina Carnate 11, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (6543.23, 'Super duper description.', 'Palazzina Carnate 12, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (873.23, 'Super duper description.', 'Palazzina Carnate 13, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (456.23, 'Super duper description.', 'Palazzina Carnate 14, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (454.23, 'Super duper description.', 'Palazzina Carnate 15, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (15443.23, 'Super duper description.', 'Palazzina Carnate 16, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (2343.23, 'Super duper description.', 'Palazzina Carnate 17, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (8743.23, 'Super duper description.', 'Palazzina Carnate 18, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (9943.23, 'Super duper description.', 'Palazzina Carnate 19, Monza e Brianza');
INSERT INTO yard (contract_total_amount, description, name) VALUES (8843.23, 'Super duper description.', 'Palazzina Carnate 20, Monza e Brianza');