drop table if exists tb_pedido_item;
drop table if exists tb_produto;
drop table if exists tb_pedido;

create table tb_pedido (
    id uuid not null,
    cadastrado_em timestamp,
    cpf_cpnj_comprador varchar(255),
    desconto float8,
    situacao_pedido varchar(255),
    valor_produtos float8,
    valor_servicos float8,
    valor_total float8,
    primary key (id)
);

create table tb_pedido_item (
    id uuid not null,
    quantidade_solicitada float8,
    valor_cobrado_unidade float8,
    valor_total float8,
    codigo_pedido uuid,
    codigo_produto uuid,
    primary key (id)
);

create table tb_produto (
    id uuid not null,
    aliquota float8,
    ativo boolean,
    codigo_iss int4,
    fabricante_fornecedor varchar(255),
    nome_descricao varchar(255),
    quantidade_disponivel float8,
    tipo varchar(255),
    valor_compra float8 not null,
    valor_venda float8 not null,
    primary key (id)
);

alter table if exists tb_pedido_item add constraint fk_pedidoitem_pedido foreign key (codigo_pedido) references tb_pedido;
alter table if exists tb_pedido_item add constraint fk_produto_pedidoitem foreign key (codigo_produto) references tb_produto;

INSERT INTO tb_produto (id, ativo, nome_descricao, fabricante_fornecedor, valor_compra, valor_venda, quantidade_disponivel, tipo) VALUES
('a7ddd2f0-3ba1-11ed-a261-0242ac120002', true, 'Parafuso 5/16', 'Gerdau', 0.25, 1.00, 100,'PRODUTO'),
('a9bdc1f0-3ba1-11ed-a261-0242ac120002', true, 'Parafuso 3/8', 'Gerdau', 0.20, 1.00, 100,'PRODUTO'),
('49bfd36a-3ba2-11ed-a261-0242ac120002', true, 'Porca 5/16', 'Gerdau', 0.25, 1.25, 100,'PRODUTO'),
('5485aeb4-3ba2-11ed-a261-0242ac120002', true, 'Porca 3/8', 'Gerdau', 0.25, 1.25, 100,'PRODUTO'),
('5d41d244-3ba2-11ed-a261-0242ac120002', true, 'Tabua 150x30cm', 'Medeiros Madeira', 20.78, 37.20, 25,'PRODUTO'),
('66fb85c8-3ba2-11ed-a261-0242ac120002', true, 'Tabua 200x30cm', 'Medeiros Madeira', 25.90, 62.80, 25,'PRODUTO'),
('a7da2af0-3ba1-11ed-a661-0242ac120002', true, 'Tabua 300x30cm', 'Medeiros Madeira', 36.80, 85.00, 25,'PRODUTO'),
('ef959280-3ba1-11ed-a261-0242ac120002', true, 'Suporte de Prateleira', 'Juninho Utilitarios', 12.50, 30.50, 50,'PRODUTO'),
('e73b4bb6-3ba1-11ed-a261-0242ac120002', true, 'Parafusadeira', 'EletroShazam', 75.00, 150.00, 10, 'PRODUTO'),
('6ec271a4-3ba2-11ed-a261-0242ac120002', true, 'Furadeira', 'EletroShazam', 90.00, 250.00, 15, 'PRODUTO' ),
('a7dcaaf0-3ba1-11ed-a133-0242ac120002', true, 'Serrote', 'Serrano Ferramentas', 26.50, 60.50, 15, 'PRODUTO'),
('760f2af6-3ba2-11ed-a261-0242ac120002', true, 'Prego 100un', 'Serrano Ferramentas', 10.35, 25.00, 50, 'PRODUTO'),
('7cf34582-3ba2-11ed-a261-0242ac120002', true, 'Prego 200un', 'Serrano Ferramentas', 15.70, 45.00, 25, 'PRODUTO'),
('34df3f3e-3cde-11ed-a261-0242ac120002', true, 'Prego 500un', 'Serrano Ferramentas', 25.70, 65.00, 25, 'PRODUTO');

INSERT INTO tb_produto (id, ativo, nome_descricao, fabricante_fornecedor, valor_compra, valor_venda, quantidade_disponivel, tipo) VALUES
('0839d6e6-3c8a-11ed-a261-0242ac120002', true, 'Tornearia', 'Serralheria do Kleiton', 0.0, 50.00, 0.0,'SERVICO'),
('15298bd0-3c8a-11ed-a261-0242ac120002', true, 'Fresa', 'Serralheria do Kleiton', 0.0, 50.00, 0.0,'SERVICO'),
('1aa6fe9e-3c8a-11ed-a261-0242ac120002', true, 'Serralheria', 'Serralheria do Kleiton', 0.0, 50.00, 0.0,'SERVICO'),
('262f5ed2-3c8a-11ed-a261-0242ac120002', true, 'Manutenção', 'Serralheria do Kleiton', 0.0, 80.00, 0.0,'SERVICO'),
('2b527d68-3c8a-11ed-a261-0242ac120002', true, 'Projeto', 'Serralheria do Kleiton', 0.0, 100.00, 0.0,'SERVICO'),
('2f3f153a-3c8a-11ed-a261-0242ac120002', true, 'Montagem', 'Serralheria do Kleiton', 0.0, 70.00, 0.0,'SERVICO');

insert into tb_pedido (cadastrado_em, cpf_cpnj_comprador, desconto, situacao_pedido, valor_produtos, valor_servicos, valor_total, id) values
('NOW()', '216.545.750-54', 12.0, 'CONFIRMADO', 250.00, 0.0, 220.0, 'd02e1784-3d1b-11ed-b878-0242ac120002'),
('NOW()', '296.808.390-28', 10.0, 'EM_ABERTO', 200.00, 50.0, 230.0, 'bac6cf20-3d1c-11ed-b878-0242ac120002'),
('NOW()', '056.894.030-08', 0.0, 'CANCELADO', 100.00, 50.0, 150.0, 'c1ed96a8-3d1c-11ed-b878-0242ac120002'),
('NOW()', '123.894.330-08', 0.0, 'EM_ABERTO', 0.0, 100.0, 100.0, 'c1ed69a8-3d1c-11ed-b878-0242ac120002');

insert into tb_pedido_item (codigo_pedido, codigo_produto, quantidade_solicitada, valor_cobrado_unidade, valor_total, id) values
('bac6cf20-3d1c-11ed-b878-0242ac120002', '760f2af6-3ba2-11ed-a261-0242ac120002', 8.0, 25.00, 200.0, '5abbe604-3d1e-11ed-b878-0242ac120002'),
('d02e1784-3d1b-11ed-b878-0242ac120002', '6ec271a4-3ba2-11ed-a261-0242ac120002', 1.0, 250.0, 250.0, '6035710e-3d1e-11ed-b878-0242ac120002'),
('bac6cf20-3d1c-11ed-b878-0242ac120002', '1aa6fe9e-3c8a-11ed-a261-0242ac120002', 1.0, 50.0, 50.0, '670f36f4-3d1e-11ed-b878-0242ac120002'),
('c1ed96a8-3d1c-11ed-b878-0242ac120002', '5d41d244-3ba2-11ed-a261-0242ac120002', 1.0, 37.20, 37.20, '6bbde0e2-3d1e-11ed-b878-0242ac120002'),
('c1ed96a8-3d1c-11ed-b878-0242ac120002', '66fb85c8-3ba2-11ed-a261-0242ac120002', 1.0, 62.80, 62.80, '6f9dab16-3d1e-11ed-b878-0242ac120002'),
('c1ed69a8-3d1c-11ed-b878-0242ac120002', '1aa6fe9e-3c8a-11ed-a261-0242ac120002', 2.0, 50.0, 50.0, '73ef0b92-3d1e-11ed-b878-0242ac120002'),
('c1ed96a8-3d1c-11ed-b878-0242ac120002', '15298bd0-3c8a-11ed-a261-0242ac120002', 1.0, 50.0, 50.0, 'e6e14532-3d43-11ed-b878-0242ac120002');



