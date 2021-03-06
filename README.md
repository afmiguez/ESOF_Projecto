[![GitHub release (latest by date including pre-releases)](https://img.shields.io/github/v/release/RetlavSource/ESOF_Projecto?color=green&include_prereleases&label=%C3%BAltima%20vers%C3%A3o&style=plastic)](https://github.com/RetlavSource/ESOF_Project/releases)

# Projeto Prático de Engenharia de Software

Este é um projeto prático para a disciplina de  ***Engenharia de Software*** na ***Universidade Fernando Pessoa***.

## Endpoints utilizados
*   **GET**
    *   /aluno -- *`lista todos os alunos`*
    *   /aluno/{id} -- *`lista o aluno com o referido id passado por url`*
    *   /atendimento -- *`lista todos os atendimentos`*
    *   /cadeira -- *`lista todas as cadeiras`*
    *   /curso -- *`lista todos os cursos`*
    *   /explicador -- *`lista todos os cursos ou procura-os por diversos parâmetros (dia, cadeira, idioma, inicio e fim)`*
    *   /explicador/{nome} -- *`lista o explicador com o referido nome passado por url`*
    *   /faculdade -- *`lista todas as faculdades`*
    *   /faculdade/{id} -- *`lista a faculdade com o referido id passado por url`*
    *   /horario -- *`lista todos os horarios`*
    *   /idioma -- *`lista todos os idiomas`*

*   **POST**
    *   /aluno -- *`cria um aluno`*
    *   /atendimento -- *`cria um atendimento`*
    *   /cadeira/{curso} -- *`cria uma cadeira no curso com o nome do curso passado por url`*
    *   /curso/{faculdade} -- *`cria um curso na faculdade com o nome da faculdade passado por url`*
    *   /explicador -- *`cria um explicador`*
    *   /faculdade -- *`cria uma faculdade`*

*   **PUT**
    *   /explicador -- *`modifica o explicador passado no payload`*
    *   /explicador/{cadeira} -- *`associa uma cadeira (nome no url) a um explicador (passado no payload)`*
    
*   **DELETE**
    *   /curso/1 -- *`remove o curso com o id=1`*

## Documentação de referência
Documentação utilizada na realização do projeto:
*   [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
*   [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/maven-plugin/)
*   [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
*   [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
*   [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/reference/htmlsingle/#using-boot-devtools)
*   [Thymeleaf](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-spring-mvc-template-engines)

## Guias de utilização
Guias de utilização de algumas ferramentas utilizadas:

*   [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
*   [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
*   [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
*   [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
*   [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
*   [TODOS os guias](https://spring.io/guides/)