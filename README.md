
<h1 align="center">
  <p align="center">REST-API-Spring-Boot-Aplication-with-Docker</p>
</h1

![rest](https://user-images.githubusercontent.com/104053775/198865741-d76b7df2-613c-4fbb-9d0e-63d4deff540a.jpg)

## Meu primeiro projeto de uma API-REST, é uma aplicação para controle de **orçamento familiar.** A aplicação permite que uma pessoa cadastre suas receitas e despesas do mês, bem como gerar um relatório mensal.
  
## Endpoints configurados na minha aplicação:

- ``/register (Cadastrar usuário para começar a usar a api)``
- ``/auth (Fazer a autentificação do usuário e receber um web token para poder fazer as próximas requisições http)``
- ``/receitas      /receitar/{id}        /receitas?descricao={valor}       /receitas/{ano}/{mes}``
- ``/despesas      /despesas/{id}        /despesas?descricao={valor}       /despesas/{ano}/{mes}  ``
- ``/resumo/{ano}/{mes}``

## A api está com o Swagger2 implementado, para ver a **documentação completa**, rode a aplicação e entre no endereço: http://localhost:8080/swagger-ui.html
  
 ## ✔️ Técnicas e tecnologias utilizadas 

- ``Modelo REST``
- ``SPRING BOOT VERSION 2.7.5``
- ``Bean validation``  
- ``SPRING SECURITY 5.7`` (WebSecurityConfigurerAdapter foi deprecated, https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)
- ``SPRING DATA-JPA-HIBERNATE``  
- ``MYSQL 8.0.30``    
- ``Java 17``
- ``ECLIPSE IDE``
- ``Maven``
- ``JUnit4``
- ``Mockito``
- ``Swagger2``
- ``Json Web Token (Jwt)``
- ``Docker``  
 
## Repositório DockerHub para utilizar a imagem do meu projeto
  [![Docker Hub Repo](https://img.shields.io/docker/pulls/rafaelmbessa/rafaelbessaapi.svg)](https://hub.docker.com/repository/docker/rafaelmbessa/rafaelbessaapi)
  
## Outros

- ``Me adicione no LinkedIn`` - https://www.linkedin.com/in/rafaelmbessa/
