# ⚔️Guilda de Aventureiros

![wallpaper](https://hackmd.io/_uploads/B1Pg50wwWl.png)

Projeto desenvolvido com **Spring Boot** utilizando **PostgreSQL** como banco de dados containerizado via Docker.

## 💻Tecnologias Utilizadas

- Java 25
- Maven 3.9.11
- Spring Boot 4.0.3
- Spring Data JPA 4.0.3
- PostgreSQL
- Docker

## 📂Estrutura do Projeto

```shell
src/
├───main
│   ├───java
│   │   └───br
│   │       └───com
│   │           └───infnet
│   │               └───guildaaventureiro
│   │                   ├───advice
│   │                   ├───controller
│   │                   ├───domain
│   │                   │   ├───audit
│   │                   │   │   └───enums
│   │                   │   └───aventura
│   │                   │       └───enums
│   │                   ├───dto
│   │                   │   ├───aventureiro
│   │                   │   ├───companheiro
│   │                   │   ├───missao
│   │                   │   │   └───enums
│   │                   │   └───relatorio
│   │                   ├───exception
│   │                   │   └───aventura
│   │                   ├───mapper
│   │                   ├───repository
│   │                   │   ├───audit
│   │                   │   └───aventura
│   │                   └───service
│   └───resources
└───test
    └───java
        └───br
            └───com
                └───infnet
                    └───guildaaventureiro
                        └───repository
                            ├───audit
                            └───aventura
```

## 📦Dependências

- Spring Boot Web
- Spring Data JPA
- Spring Boot Validation
- Spring Boot Test
- Lombok
- PostgreSQL Driver
- Jackson Databind
- Hypersistence Utils

## ▶️Como executar o projeto

1. Clonar o repositório

```shell
git clone https://github.com/rodrigo-cloureiro/guildaaventureiro-tp2.git
cd guildaaventureiro-tp2
```

2. Subir o banco com Docker
    1. Baixar a imagem
    ```shell
    # Para Windows
    docker pull leogloriainfnet/postgres-tp2-spring:1.0
    # Para macOS
    docker pull leogloriainfnet/postgres-tp2-spring:1.0-mac
    ```

    2. Credenciais do Banco
    ```shell
    POSTGRES_USER: postgres
    POSTGRES_PASSWORD: apppass
    ```

    3. Criar o container
    ```shell 
      docker run -d --name <NOME_CONTAINER> -p 5432:5432 -e POSTGRES_PASSWORD=<POSTGRES_PASSWORD> leogloriainfnet/postgres-tp2-spring:<TAG>
    ```

3. Executar a aplicação

```shell
mvn spring-boot:run
# Ou
./mvnw spring-boot:run
```

4. Acessar aplicação

```shell
http://localhost:8080
```

### 🧪 Testes

Para executar os testes:

```shell
mvn test
```

## Autor

- [rodrigo-cloureiro](https://github.com/rodrigo-cloureiro)