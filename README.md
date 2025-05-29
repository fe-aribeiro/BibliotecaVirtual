
# 📚 API de Biblioteca Virtual

Esta é uma API RESTful desenvolvida em **Java 17** com **Spring Boot 3.5.0** para gerenciar uma biblioteca virtual.  
A aplicação permite adicionar livros, autores e assuntos, fazer o vínculo entre eles e gerar relatórios agrupados por autor.

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.5.0
- Spring Data JPA
- Hibernate
- Lombok
- Bean Validation
- PostgreSQL (pode ser configurado com Docker)
- OpenAPI / Swagger para documentação da API

## 🛠 Funcionalidades

- 📚 Cadastrar, listar, editar e remover **livros**
- 👤 Cadastrar, listar, editar e remover **autores**
- 🏷️ Cadastrar, listar, editar e remover **assuntos**
- 🔗 Vincular livros a autores e assuntos
- 📊 Gerar **relatório de livros agrupado por autores**

## 📁 Estrutura do Projeto

```
biblioteca-virtual-api/
├── src/main/java/br/com/desafio/livraria/
│   ├── controller/        # Camada de controllers REST
│   ├── service/           # Lógica de negócio
│   ├── repository/        # Interfaces JPA
│   ├── model/             # Entidades JPA
│   ├── dto/               # Objetos de transferência de dados
│   └── config/            # Configurações da aplicação
├── collection-postman/    # Arquivo de collection utilizado para testar a api no Postman
├── db-docker/             # Arquivos para subir banco com Docker
├── pom.xml                # Dependências do projeto
```

## 🐳 Subindo o Banco com Docker

Dentro da pasta `db-docker`, você encontrará um arquivo `docker-compose.yml`.  
Dentro do `pom.xml` do projeto você encontra um script responsável por subir o banco usando o docker sempre que o projeto for buildado.  
(Caso o banco já esteja rodando o comando não é executado)

## 🌐 Acessando a Documentação da API

Após subir a aplicação, a documentação estará disponível em:

```
http://localhost:8080/swagger-ui.html
```

## 🧪 Testes

A estrutura está preparada para testes unitários com JUnit 5 e Mockito.

## 📬 Testes com Postman

Dentro da pasta `collection-postman`, você encontrará a collection `Biblioteca.postman_collection.json` contendo requisições organizadas para testar todos os endpoints da API.  
Essa collection pode ser importada diretamente no Postman para facilitar o uso e a validação dos recursos disponíveis.

## ✍️ Autor

Projeto desenvolvido como parte de um desafio técnico.
