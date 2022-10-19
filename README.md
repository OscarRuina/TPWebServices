# TPWebServices

Modulo Administrador:
 * Puerto 8081.
 * URL de Swagger: http://localhost:8081/swagger-ui/index.html

Para hacer login:
* Usuario: admin 
* Contraseña: foo1234

Modulo Estudiante:
 * Puerto 8082.
 * Al correr el proyecto a partir del archivo xsd se generan clases de Java que se encuentran en la carpeta target/generated-sources/jaxb.
 * Estas clases se actualizan cada vez que se modifica el xsd y se corre el proyecto.
 * Para ver en el browser el xml escribir la url: http://localhost:8082/soapWS/estudiantes.wsdl
 * Para correr varios proyectos a la vez, abrir una terminal dentro de la carpeta del proyecto y usar el comando mvn spring-boot:run

Modulo Docente:
 * Puerto 8083.
 Requiere los mismos pasos que el modulo de Estudiante
 * Al correr el proyecto a partir del archivo xsd se generan clases de Java que se encuentran en la carpeta target/generated-sources/jaxb.
 * Estas clases se actualizan cada vez que se modifica el xsd y se corre el proyecto.
 * Para ver en el browser el xml escribir la url: http://localhost:8083/soapWS/docentes.wsdl
 * Para correr varios proyectos a la vez, abrir una terminal dentro de la carpeta del proyecto y usar el comando mvn spring-boot:run

