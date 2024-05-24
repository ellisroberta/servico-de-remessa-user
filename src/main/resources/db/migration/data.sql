-- src/main/resources/db/migration/V2__data.sql

-- Criando usuários

-- Usuário 1 (Pessoa Física)
INSERT INTO tb_user (id, full_name, email, password, document, user_type, wallet_id)
VALUES ('11111111-1111-1111-1111-111111111111', 'Alice Silva', 'alice.silva@example.com', 'password1', '12345678901', 'PF', NULL);

-- Usuário 2 (Pessoa Jurídica)
INSERT INTO tb_user (id, full_name, email, password, document, user_type, wallet_id)
VALUES ('33333333-3333-3333-3333-333333333333', 'Empresa XYZ', 'contato@xyz.com', 'password2', '12345678000199', 'PJ', NULL);