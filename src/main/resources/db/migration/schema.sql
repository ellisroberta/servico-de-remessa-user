-- src/main/resources/db/migration/V1__schema.sql

-- Define o esquema para a tabela de usuários
CREATE TABLE tb_user (
    id UUID PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    document VARCHAR(14) UNIQUE NOT NULL,
    user_type VARCHAR(2) NOT NULL,
    wallet_id UUID
);