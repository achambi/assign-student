# Bank-Application With TDD
#Requerimeintos: 
Las tecnologias usadas son las siguientes:

- <b>SpringBoot</b>
- <b>Hibernate</b>
- <b>JPA</b>
- <b>Swagger</b>
- <b>Mockito</b>
- <b>JUnit4</b>
- <b>Swagger-Test</b>
- <b>Intelij IDEA</b>


I have all documentation in swagger please use this link to view: 
http://localhost:9090/swagger-ui.html

I have service tests and controller tests
to run yo need to use intellij

You need to use a mysql database and configure the next options in application.properties file:

    server.port=9090
    server.error.whitelabel.enabled=false
    spring.datasource.url=jdbc:mysql://localhost:3306/assign-student?useSSL=false
    spring.datasource.username=root
    spring.datasource.password=control123!
    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=create
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect


To run the second exercise yo need to run the BoundingBoxTest.java is a unit test.

 