drop table if exists tb_pedido;
drop table if exists tb_pedido_item;
drop table if exists tb_produto;
drop table if exists tb_servico

create table tb_pedido(
id uuid not null,
desconto float8,
situacao_pedido varchar(255),
primary key (id));

create table tb_pedido_item (
id uuid not null,
quantidade_solicitada float8,
valor_cobrado_unidade float8,
valor_total float8,
codigo_pedido uuid,
codigo_produto uuid,
primary key (id));


create table tb_produto
(id uuid not null,
ativo boolean,
fabricante_fornecedor varchar(255),
nome_descricao varchar(255),
quantidade_disponivel float8,
tipo varchar(255),
valor_compra float8 not null,
valor_venda float8 not null,
primary key (id));

create table tb_servico
(id uuid not null,
ativo boolean,
fabricante_fornecedor varchar(255),
nome_descricao varchar(255),
quantidade_disponivel float8,
tipo varchar(255),
valor_compra float8 not null,
valor_venda float8 not null,
aliquota float8,
codigo_iss int4,
primary key (id));

alter table if exists tb_pedido_item add constraint fk_item_pedido foreign key (codigo_pedido) references tb_pedido;
alter table if exists tb_pedido_item add constraint fk_produto_item_pedido foreign key (codigo_produto) references tb_pedido;