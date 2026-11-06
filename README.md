#Title
# 🏦 Sistema Bancário - JDBC & DAO

Este projeto foi desenvolvido com base nos conceitos da **Aula 22 de Programação Orientada a Objetos**. Ele implementa um sistema de gerenciamento de contas bancárias via linha de comando (CLI), utilizando **Java**, **JDBC** para acesso ao banco de dados e o padrão de projeto **DAO (Data Access Object)**.

## 🛠️ Tecnologias e Conceitos Aplicados

*   **Java 17**: Linguagem base do projeto.
*   **Maven**: Gerenciamento de dependências e build.
*   **PostgreSQL 16**: Banco de dados relacional (rodando via Docker).
*   **JDBC (Java Database Connectivity)**: API padrão para executar comandos SQL.
*   **Padrão DAO**: Separação da lógica de acesso a dados (SQL) da lógica de negócios.
*   **GitHub Codespaces / Dev Containers**: Ambiente de desenvolvimento isolado e pronto para uso.

## 🏗️ Arquitetura do Projeto (Padrão DAO)

Seguindo os conceitos da aula, o projeto está dividido nas seguintes responsabilidades:

1.  **`Conta` (Modelo de Domínio)**: Representa a entidade do mundo real. Utiliza `BigDecimal` para o saldo, pois dinheiro não deve ser representado com `double` (evita erros de arredondamento).
2.  **`IContaDao` (Interface/Contrato)**: Define *o que* deve ser feito (criar, ler, atualizar, apagar), mas não *como*. Garante o baixo acoplamento.
3.  **`ContaDao` (Implementação)**: Contém o código SQL real (usando `PreparedStatement` para evitar SQL Injection). É aqui que o JDBC atua.
4.  **`ConnectionFactory`**: Centraliza a criação de conexões com o banco. Evita repetir a URL, usuário e senha em vários lugares.
5.  **`GerenciadorContasApp` (Main)**: A interface com o usuário (menu no terminal). Ela solicita os dados e chama os métodos do DAO.

---

## 🚀 Como Rodar o Projeto

### 1. Preparar o Ambiente
Se você acabou de abrir este projeto no GitHub Codespaces, aguarde a inicialização. O arquivo `.devcontainer` configurará automaticamente:
*   A máquina virtual com Java e Maven.
*   O container do PostgreSQL com o banco `meubanco`.

### 2. Executar a Aplicação
Abra o terminal do VS Code (certifique-se de que está na pasta do projeto) e execute:

```bash
# Compilar o projeto
mvn compile

# Executar a classe principal
mvn exec:java