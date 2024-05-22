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

Antes de construir a imagem Docker do User Management Service, é necessário executar o Maven para compilar o projeto. Navegue até a raiz do projeto e execute o seguinte comando:

```bash
Copiar código
mvn clean package
```

Antes de iniciar o ambiente com o docker-compose, é necessário construir a imagem Docker do User Management Service executando o seguinte comando na raiz do projeto:

```bash
Copiar código
docker build -t user-management .
```

Observação: Certifique-se de ter construído as imagens das dependências antes de executar o docker-compose, como o Authentication Service e o Profile Service.
[Wallet Management Service] (https://github.com/ellisroberta/servico-de-remessa-wallet)

```bash
Copiar código
docker-compose up -d
```

Observação: Certifique-se de estar executando o comando docker-compose no diretório principal do servico-de-remessa-user. 
Isso garantirá que o Docker Compose encontre e utilize o arquivo correto para iniciar todos os serviços necessários.

## Documentação do Swagger
O User Management Service possui uma documentação do Swagger que descreve os endpoints disponíveis e fornece informações detalhadas sobre como consumir a API.

Para acessar a documentação do Swagger, siga as etapas abaixo:

Verifique se o docker-compose foi corretamente executado.
Abra o navegador e vá para a URL: http://localhost/user/swagger-ui.html.
Isso abrirá a interface do Swagger, onde você poderá explorar os endpoints, enviar solicitações e visualizar as respostas.

Certifique-se de que o serviço esteja em execução para acessar a documentação do Swagger.

## Funcionalidades Principais
- Cadastro de novos usuários com informações básicas.
- Atualização de dados de usuários existentes.
- Autenticação e gestão de sessões de usuários.
- Gerenciamento de perfis de usuário e informações adicionais.
- Consulta de informações de usuários.
- Exclusão de usuários.

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
curl -X PUT "http://localhost/user/api/users/{userID}" -H "accept: */*" -H "Content-Type: a
```