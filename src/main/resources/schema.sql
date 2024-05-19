CREATE TABLE tb_user (
    id UUID PRIMARY KEY,
    full_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    cpf VARCHAR(11) UNIQUE,
    cnpj VARCHAR(14) UNIQUE,
    user_type VARCHAR(2),
    wallet_id UUID,
    CONSTRAINT FK_wallet FOREIGN KEY (wallet_id) REFERENCES wallet(id)
);