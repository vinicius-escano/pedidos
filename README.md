# Pedidos_API

API para gestão de Pedido para teste back-end java nivel III.

### Requisitos

- Java 11 ou superior
- Lombok
- pgAdmin
- Criar uma base de dados chamada 'db_pedidos' na porta padrão do postgres (5432) ou alterar o nome do servidor na property `spring.datasource.url` no arquivo application.properties
- Dentro da pasta do projeto rodar o comando `mvn package clean install -DskipTests` para criar as 'QObject' da queryDSL
- Para rodar os testes das controllers importar o script "Insomnia Tests" dentro do Insomnia

Executar o programa dentro da IDE normalmente ou pelo comando `java -jar arquivo.jar` na pasta target

### Testes
> A estrutura de classes segue a hierarquia: Pedido -> PedidoItem -> Produto(Serviço)

#### Teste de Controllers
- Rodar os scripts do Insomnia na ordem (Produto, Pedido, PedidoItem)
- Ao inserir PedidoItem, deve existir um Pedido e um Produto, a referência pode ser feita pegando um id dos posts dos mesmos.

#### Teste de Repositório
- Para os testes de repositório rodar as classes na pasta test/repositorytest do projeto

#### Interface
- Existe uma interface ao usuário, basta acessar no navegador o localhost:8080/
