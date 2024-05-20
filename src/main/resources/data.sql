-- Criando usuários

-- Usuário 1 (Pessoa Física)
INSERT INTO tb_user (id, full_name, email, password, cpf, user_type)
VALUES ('11111111-1111-1111-1111-111111111111', 'Alice Silva', 'alice.silva@example.com', 'password1', '12345678901', 'PF');

-- Usuário 2 (Pessoa Jurídica)
INSERT INTO tb_user (id, full_name, email, password, cnpj, user_type)
VALUES ('33333333-3333-3333-3333-333333333333', 'Empresa XYZ', 'contato@xyz.com', 'password2', '12345678000199', 'PJ');
