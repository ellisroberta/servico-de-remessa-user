# User Management Service
O serviço User Management é responsável por gerenciar os usuários dentro de um sistema de carteira digital.

## Descrição
O User Management Service é um componente central que oferece funcionalidades de cadastro, autenticação e gestão de usuários.

## Arquitetura
O serviço User Management é projetado como um microserviço independente e segue os princípios de arquitetura de microsserviços. 
Ele é desenvolvido usando tecnologias como Spring Boot, Java rabbitMq, e integrações com banco de dados H2 para armazenamento dos dados dos usuários.

## Integrações
O User Management Service se integra com os seguintes serviços:

- [Wallet Management Service] (https://github.com/ellisroberta/servico-de-remessa-wallet): responsável pelo gerenciamento das carteiras digitais dos usuários.

## Requisitos do Sistema
Para executar o User Management Service, verifique se você possui os seguintes requisitos instalados:

- Java 17: [Instalar Java 17](https://www.oracle.com/java/technologies/downloads/)
- Maven: [Instalar Maven](https://maven.apache.org/install.html)
- Docker: [Instalar Docker](https://docs.docker.com/get-docker/)

Certifique-se de que o Java 17 e o Maven estejam configurados corretamente em seu ambiente.

## Compilação e Execução

### 1. Compilar o Projeto:
Navegue até a raiz do projeto e execute o seguinte comando para compilar o projeto utilizando Maven:

```bash
Copiar código
mvn clean package
```

### 2. Construir Imagem Docker:
Antes de iniciar o ambiente com docker-compose, construa a imagem Docker do Wallet Management Service:

```bash
Copiar código
docker build -t wallet-management .
```

Observação: Certifique-se de ter construído as imagens das dependências antes de executar o docker-compose, como o Authentication Service e o Profile Service.

### 3. Executar com Docker Compose:
Inicie o ambiente utilizando docker-compose, garantindo que o comando seja executado no diretório principal do projeto:

```bash
Copiar código
docker-compose up -d
```

Isso garantirá que todos os serviços necessários sejam iniciados corretamente.

## Documentação do Swagger
O User Management Service possui uma documentação do Swagger que descreve os endpoints disponíveis e fornece informações detalhadas sobre como consumir a API.

Para acessar a documentação do Swagger, siga as etapas abaixo:

Verifique se o docker-compose foi corretamente executado.
Abra o navegador e vá para a URL: http://localhost/user/swagger-ui.html.
Isso abrirá a interface do Swagger, onde você poderá explorar os endpoints, enviar solicitações e visualizar as respostas.

Certifique-se de que o serviço esteja em execução para acessar a documentação do Swagger.

## Funcionalidades Principais
- Listar todos os usuários registrados.
- Obter informações detalhadas de um usuário baseado no ID.
- Registrar um novo usuário com base nos dados fornecidos.
- Criar uma solicitação de transação via RabbitMQ.
- Remover um usuário do sistema com base no ID fornecido.

## Exemplos de Uso (Curl)
Aqui estão alguns exemplos de como usar as funcionalidades do User Management Service com curl:

- Criar um novo usuário:

```bash
Copiar código
curl -X POST "http://localhost/user/api/users" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"fullName\": \"Nome Completo\", \"email\": \"email@exemplo.com\", \"password\": \"senha123\", \"cpf\": \"01234567890\"}"
```

- Consultar informações do usuário (use o ID do usuário obtido na criação):

```bash
Copiar código
curl -X GET "http://localhost/user/api/users/{userID}" -H "accept: */*"
```

- Atualizar informações do usuário:

```bash
Copiar código
curl -X PUT "http://localhost/user/api/users/{userId}" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"fullName\": \"Novo Nome\", \"email\": \"novoemail@exemplo.com\", \"password\": \"novaSenha123\", \"cpf\": \"01234567890\"}"
```

- Criar uma transação:

```bash
Copiar código
curl -X POST "http://localhost/user/api/users/transacao" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"userId\": \"{userId}\", \"amountBrl\": 100.0 }"
```

- Deletar um usuário:

```bash
Copiar código
curl -X DELETE "http://localhost/user/api/users/{userId}" -H "accept: */*"
```