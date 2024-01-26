<center>
  <p align="center">
    <img src="readmefiles/logoclean.png" width="150">
    <img src="https://icon-library.com/images/java-icon-png/java-icon-png-15.jpg"  width="150" />
  </p>  
  <h1 align="center">MS SnackHub Order com Clean Architecture</h1>
  <br align="center">
    Este projeto tem a finalidade educacional de um Microserviço responsável por montar um pedido de um estabelecimento de lanchonete, como parte do Tech Challenge do Curso de Arquitetura de Software.
</center>

# Conceito

## O que é Clean Architecture?

Clean Architecture, também conhecida como Arquitetura Limpa, é uma abordagem de desenvolvimento de software proposta por Robert C. Martin, um renomado engenheiro de software, autor e consultor. A principal ideia por trás da Clean Architecture é criar sistemas que sejam independentes de frameworks, banco de dados e detalhes de interface do usuário, enfatizando a separação de preocupações e a clareza na organização do código.

O objetivo da Clean Architecture é desenvolver sistemas altamente sustentáveis, testáveis e escaláveis, facilitando a manutenção contínua ao longo do tempo, permitindo a troca de componentes sem alterar a lógica central e tornando o código mais compreensível para novos desenvolvedores que trabalham no projeto.

Essa arquitetura promove a separação de preocupações e permite que cada camada se concentre em sua responsabilidade específica. A ideia é que as dependências fluam de dentro para fora, ou seja, as camadas internas não devem depender das camadas externas, tornando o sistema mais modular e independente.


![img.png](readmefiles/cleanmodel.png)

## Decisão Arquitetural do Desafio

O projeto SnackHub possui três principais módulos:
* Domain
* Application
* Infrastructure

## Domain

Nesse módulo estão presente as nossas classes de dominio, projetadas com a utilização de práticas do DDD.
No cenário do Clean Architecture representam as Entities.

As classes presente nesse módulo não possuem nenhuma dependência externa ou de framework.

## Application

A camada de Use Case é responsável por implementar os casos de uso específicos do negócio da aplicação.
A caracteristica desse módulo é abstrair de regras de negócio: A camada de Use Case contém a lógica do negócio da aplicação, mas sem detalhes de implementação relacionados a infraestrutura ou apresentação.

## Infrastructure

A camada de infraestrutura é uma das camadas principais do Clean Architecture (Arquitetura Limpa) proposta por Robert C. Martin.
Essa camada é responsável por lidar com os detalhes técnicos, como o acesso a bancos de dados, serviços externos, sistemas de arquivos e outras tecnologias que não são específicas do domínio da aplicação. Sua principal função é permitir a comunicação entre a aplicação e o mundo externo, mantendo a lógica de negócio isolada e independente de detalhes de implementação.

# Persistência
No microserviço estamos utilizando um banco de dados relacional (SQL) MySQL.

# Cobertura e Qualidade

Nossa pipeline no Github Actions é realizada todas as etapas de build, analise e deploy da aplicação.

Na Step Build and analyze:

![img.png](readmefiles/stepbuild.png)

- Build do microserviço
- Execução de todos os tipos de testes
- Geração de relatórios de testes e cobertura
- Envio das informações do projeto ao SonarCloud

https://sonarcloud.io/summary/overall?id=grupo60-fiap2023_snackhub-order

![img.png](readmefiles/sonarcloud.png)
* os 3 code smell serão customizados na regra do Sonar, pois foram analisados pela equipe e será uma prática.

No SonarCloud é avaliado:
- Prováveis Bugs
- Qualidade do fonte
- Linhas duplicadas
- Conbertura do fonte por testes

Como pode ser visto na imagem e no link a Cobertura hoje do serviço está em 97,5%.

O nosso serviço também tem uma configuração no qual a pipeline irá falhar caso um novo fonte acabe baixando a cobertura do fonte inferior a 85%:

![img.png](readmefiles/configcoverage.png)


## Tipos de Testes Implementados
## Teste de unidade
Todos módulos implementados no serviço possuem testes de unidade implementados com o JUnit.

![img.png](readmefiles/junit.png)

*ver nos módulos package de teste

## Integração

Nos testes de integração foi utilizado o SpringBootTest. Em testes que necessitavam simular a base de dados real foi utilizado o Banco em memória H2.

![img.png](readmefiles/inetgracao.png)

Testes do módulo infrastructure

![img.png](readmefiles/infratest.png)

*ver no módulo infrastructure testes com o final IT

## BDD
Utilizado o Cumcumber nos BDD.

![cucumber.png](readmefiles%2Fcucumber.png)

![img.png](readmefiles/cumcuber2.png)

## Teste de Carga
Foi analisado o serviço com um teste de carga K6.

![k6LoadTest.png](readmefiles%2Fk6LoadTest.png)

## Execução dos testes por módulo

A execução dos testes poderá ser visto pelo Sonar, mas segue a execução por módulo.

![img.png](readmefiles/infrateste.png)

![img.png](readmefiles/apptestes.png)

![img.png](readmefiles/domaintestes.png)

![coverage.png](readmefiles%2Fcoverage.png)

## Como executar só com Docker?

**1. Subir a aplicação e o banco de dados MySQL com Docker:**
```shell
docker-compose up -d
```

**2. URL de acesso:**

http://localhost:8080/swagger-ui/index.html#

# Step Deploy
O último step da pipeline realiza de forma automatizada o deploy para a AWS.

# Proteção Repositório
![img.png](readmefiles/github1.png)

![img_1.png](readmefiles/github2.png)