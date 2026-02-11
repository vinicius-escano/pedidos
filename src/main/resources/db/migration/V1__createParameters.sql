-- V3: Create Empresa and Parametro tables and initial seed data

CREATE TABLE IF NOT EXISTS tb_parametro (
    id uuid NOT NULL,
    codigo int4,
    empresa uuid,
    tipo_moeda varchar(50),
    tipo_medida varchar(50),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_empresa (
    id uuid NOT NULL,
    codigo int4,
    nome varchar(255),
    nome_fantasia varchar(255),
    cnpj varchar(50),
    endereco uuid,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_endereco (
    id uuid NOT NULL,
    codigo int4,
    numero varchar(50),
    bairro varchar(255),
    cidade varchar(255),
    estado varchar(100),
    cep varchar(20),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_cliente (
    id uuid NOT NULL,
    codigo int4,
    nome varchar(255),
    nome_fantasia varchar(255),
    cpf_cnpj varchar(50),
    email varchar(255),
    telefone varchar(50),
    endereco uuid,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS tb_cliente
    ADD CONSTRAINT fk_cliente_endereco FOREIGN KEY (endereco) REFERENCES tb_endereco(id);

ALTER TABLE IF EXISTS tb_empresa
    ADD CONSTRAINT fk_empresa_endereco FOREIGN KEY (endereco) REFERENCES tb_endereco(id);

ALTER TABLE IF EXISTS tb_parametro
    ADD CONSTRAINT fk_parametro_empresa FOREIGN KEY (empresa) REFERENCES tb_empresa(id);

-- seed endereco (added codigo = 1)
INSERT INTO tb_endereco (id, codigo, numero, bairro, cidade, estado, cep) VALUES
('11111111-1111-1111-1111-111111111111', 1, '123', 'Centro', 'São Paulo', 'SP', '01000-000');

-- seed empresa (added codigo = 1)
INSERT INTO tb_empresa (id, codigo, nome, nome_fantasia, cnpj, endereco) VALUES
('33333333-3333-3333-3333-333333333333', 1, 'Empresa Exemplo LTDA', 'EmpresaEx', '12.345.678/0001-99', '11111111-1111-1111-1111-111111111111');

-- seed parametro (added codigo = 1)
INSERT INTO tb_parametro (id, codigo, empresa, tipo_moeda, tipo_medida) VALUES
('44444444-4444-4444-4444-444444444444', 1, '33333333-3333-3333-3333-333333333333', 'REAL', 'METRICO');

-- seed cliente (added codigo = 1)
INSERT INTO tb_cliente (id, codigo, nome, nome_fantasia, cpf_cnpj, email, telefone, endereco) VALUES
('22222222-2222-2222-2222-222222222222', 1, 'João da Silva', 'João Construções', '123.456.789-00', 'joao@example.com', '+55 11 99999-0000', '11111111-1111-1111-1111-111111111111');