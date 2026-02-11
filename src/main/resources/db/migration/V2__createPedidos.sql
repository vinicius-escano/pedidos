-- Initial schema and seed data converted from data.sql

create table if not exists tb_pedido (
    id uuid not null,
    codigo int4,
    cadastrado_em timestamp,
    cpf_cpnj_comprador varchar(255),
    desconto float8,
    situacao_pedido varchar(255),
    valor_produtos float8,
    valor_servicos float8,
    valor_total float8,
    primary key (id)
);

create table if not exists tb_produto (
    id uuid not null,
    codigo int4,
    aliquota float8,
    ativo boolean,
    codigo_iss int4,
    fabricante_fornecedor varchar(255),
    nome_descricao varchar(255),
    quantidade_disponivel float8,
    unidade_medida varchar(255),
    tipo varchar(255),
    valor_compra float8 not null,
    valor_venda float8 not null,
    primary key (id)
);

create table if not exists tb_pedido_item (
    id uuid not null,
    codigo int4,
    quantidade_solicitada float8,
    valor_cobrado_unidade float8,
    valor_total float8,
    codigo_pedido uuid,
    codigo_produto uuid,
    primary key (id)
);

alter table if exists tb_pedido_item add constraint fk_pedidoitem_pedido foreign key (codigo_pedido) references tb_pedido;
alter table if exists tb_pedido_item add constraint fk_produto_pedidoitem foreign key (codigo_produto) references tb_produto;

-- seed produtos (added codigo values sequentially)
INSERT INTO tb_produto (id, codigo, ativo, nome_descricao, fabricante_fornecedor, valor_compra, valor_venda, quantidade_disponivel, unidade_medida, tipo) VALUES
('a7ddd2f0-3ba1-11ed-a261-0242ac120002', 1, true, 'Parafuso 5/16', 'Gerdau', 0.25, 1.00, 100,'UNIDADE','PRODUTO'),
('a9bdc1f0-3ba1-11ed-a261-0242ac120002', 2, true, 'Parafuso 3/8', 'Gerdau', 0.20, 1.00, 100,'UNIDADE','PRODUTO'),
('49bfd36a-3ba2-11ed-a261-0242ac120002', 3, true, 'Porca 5/16', 'Gerdau', 0.25, 1.25, 100,'UNIDADE','PRODUTO'),
('5485aeb4-3ba2-11ed-a261-0242ac120002', 4, true, 'Porca 3/8', 'Gerdau', 0.25, 1.25, 100,'UNIDADE','PRODUTO'),
('5d41d244-3ba2-11ed-a261-0242ac120002', 5, true, 'Tabua 150x30cm', 'Medeiros Madeira', 20.78, 37.20, 25,'UNIDADE','PRODUTO'),
('66fb85c8-3ba2-11ed-a261-0242ac120002', 6, true, 'Tabua 200x30cm', 'Medeiros Madeira', 25.90, 62.80, 25,'UNIDADE','PRODUTO'),
('a7da2af0-3ba1-11ed-a661-0242ac120002', 7, true, 'Tabua 300x30cm', 'Medeiros Madeira', 36.80, 85.00, 25,'UNIDADE','PRODUTO'),
('ef959280-3ba1-11ed-a261-0242ac120002', 8, true, 'Suporte de Prateleira', 'Juninho Utilitarios', 12.50, 30.50, 50,'UNIDADE','PRODUTO'),
('e73b4bb6-3ba1-11ed-a261-0242ac120002', 9, true, 'Parafusadeira', 'EletroShazam', 75.00, 150.00, 10, 'UNIDADE','PRODUTO'),
('6ec271a4-3ba2-11ed-a261-0242ac120002', 10, true, 'Furadeira', 'EletroShazam', 90.00, 250.00, 15, 'UNIDADE','PRODUTO' ),
('a7dcaaf0-3ba1-11ed-a133-0242ac120002', 11, true, 'Serrote', 'Serrano Ferramentas', 26.50, 60.50, 15, 'UNIDADE','PRODUTO'),
('760f2af6-3ba2-11ed-a261-0242ac120002', 12, true, 'Prego 100un', 'Serrano Ferramentas', 10.35, 25.00, 50, 'PACOTE','PRODUTO'),
('7cf34582-3ba2-11ed-a261-0242ac120002', 13, true, 'Prego 200un', 'Serrano Ferramentas', 15.70, 45.00, 25, 'PACOTE','PRODUTO'),
('34df3f3e-3cde-11ed-a261-0242ac120002', 14, true, 'Prego 500un', 'Serrano Ferramentas', 25.70, 65.00, 25, 'PACOTE','PRODUTO');

-- seed servicos (added codigo values continuing sequence)
INSERT INTO tb_produto (id, codigo, ativo, nome_descricao, fabricante_fornecedor, valor_compra, valor_venda, quantidade_disponivel, unidade_medida, tipo) VALUES
('0839d6e6-3c8a-11ed-a261-0242ac120002', 101, true, 'Tornearia', 'Serralheria do Kleiton', 0.0, 50.00, 0.0,'UNIDADE','SERVICO'),
('15298bd0-3c8a-11ed-a261-0242ac120002', 102, true, 'Fresa', 'Serralheria do Kleiton', 0.0, 50.00, 0.0,'UNIDADE','SERVICO'),
('1aa6fe9e-3c8a-11ed-a261-0242ac120002', 103, true, 'Serralheria', 'Serralheria do Kleiton', 0.0, 50.00, 0.0,'UNIDADE','SERVICO'),
('262f5ed2-3c8a-11ed-a261-0242ac120002', 104, true, 'Manutenção', 'Serralheria do Kleiton', 0.0, 80.00, 0.0,'UNIDADE','SERVICO'),
('2b527d68-3c8a-11ed-a261-0242ac120002', 105, true, 'Projeto', 'Serralheria do Kleiton', 0.0, 100.00, 0.0,'UNIDADE','SERVICO'),
('2f3f153a-3c8a-11ed-a261-0242ac120002', 106, true, 'Montagem', 'Serralheria do Kleiton', 0.0, 70.00, 0.0,'UNIDADE','SERVICO');

-- seed pedidos (added codigo values)
insert into tb_pedido (id, codigo, cadastrado_em, cpf_cpnj_comprador, desconto, situacao_pedido, valor_produtos, valor_servicos, valor_total) values
('d02e1784-3d1b-11ed-b878-0242ac120002', 1001, NOW(), '216.545.750-54', 12.0, 'CONFIRMADO', 250.00, 0.0, 220.0),
('bac6cf20-3d1c-11ed-b878-0242ac120002', 1002, NOW(), '296.808.390-28', 10.0, 'EM_ABERTO', 200.00, 50.0, 230.0),
('c1ed96a8-3d1c-11ed-b878-0242ac120002', 1003, NOW(), '056.894.030-08', 0.0, 'CANCELADO', 100.00, 50.0, 150.0),
('c1ed69a8-3d1c-11ed-b878-0242ac120002', 1004, NOW(), '123.894.330-08', 0.0, 'EM_ABERTO', 0.0, 100.0, 100.0);

-- seed pedido items (added codigo values)
insert into tb_pedido_item (id, codigo, codigo_pedido, codigo_produto, quantidade_solicitada, valor_cobrado_unidade, valor_total) values
('5abbe604-3d1e-11ed-b878-0242ac120002', 5001, 'bac6cf20-3d1c-11ed-b878-0242ac120002', '760f2af6-3ba2-11ed-a261-0242ac120002', 8.0, 25.00, 200.0),
('6035710e-3d1e-11ed-b878-0242ac120002', 5002, 'd02e1784-3d1b-11ed-b878-0242ac120002', '6ec271a4-3ba2-11ed-a261-0242ac120002', 1.0, 250.0, 250.0),
('670f36f4-3d1e-11ed-b878-0242ac120002', 5003, 'bac6cf20-3d1c-11ed-b878-0242ac120002', '1aa6fe9e-3c8a-11ed-a261-0242ac120002', 1.0, 50.0, 50.0),
('6bbde0e2-3d1e-11ed-b878-0242ac120002', 5004, 'c1ed96a8-3d1c-11ed-b878-0242ac120002', '5d41d244-3ba2-11ed-a261-0242ac120002', 1.0, 37.20, 37.20),
('6f9dab16-3d1e-11ed-b878-0242ac120002', 5005, 'c1ed96a8-3d1c-11ed-b878-0242ac120002', '66fb85c8-3ba2-11ed-a261-0242ac120002', 1.0, 62.80, 62.80),
('73ef0b92-3d1e-11ed-b878-0242ac120002', 5006, 'c1ed69a8-3d1c-11ed-b878-0242ac120002', '1aa6fe9e-3c8a-11ed-a261-0242ac120002', 2.0, 50.0, 50.0),
('e6e14532-3d43-11ed-b878-0242ac120002', 5007, 'c1ed96a8-3d1c-11ed-b878-0242ac120002', '15298bd0-3c8a-11ed-a261-0242ac120002', 1.0, 50.0, 50.0);