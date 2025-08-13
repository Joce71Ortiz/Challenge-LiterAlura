# Challenge-LiterAlura
Desafio de LiterAlura

### Descripción del proyecto

`LiteraturAlura` es una aplicación de consola desarrollada en **Java** con **Spring Boot** que actúa como una biblioteca digital. El proyecto fue creado para consolidar conocimientos en el uso de **Spring Data JPA** para la persistencia de datos, la creación de consultas personalizadas y la integración con APIs externas. La aplicación se conecta a la API de **Gutendex** para buscar y registrar libros, y permite al usuario realizar diversas consultas sobre los datos almacenados en una base de datos **PostgreSQL**.

---

### Funcionalidades implementadas

La aplicación ofrece un menú interactivo con las siguientes opciones:

* **Buscar libro por título**: Busca un libro en la API, lo guarda en la base de datos si no existe y muestra sus datos.
* **Mostrar libros registrados**: Lista todos los libros almacenados en la base de datos.
* **Mostrar autores registrados**: Muestra una lista de todos los autores de los libros guardados.
* **Mostrar autores vivos en un determinado año**: Filtra y lista autores que estuvieron vivos en un año específico.
* **Mostrar libros por idioma**: Permite buscar y listar libros según el idioma.
* **Top 10 libros más descargados**: Muestra los 10 libros con el mayor número de descargas de la base de datos.
* **Generar estadísticas de descargas**: Muestra estadísticas como el promedio, máximo y mínimo de descargas.
* **Buscar libros por autor**: Permite buscar y listar todos los libros asociados a un autor específico.
* **Evitar duplicados**: La aplicación valida si un libro ya existe en la base de datos antes de registrarlo, optimizando el rendimiento y la integridad de los datos.

---

### Tecnologías utilizadas

* **Java 22**: Lenguaje de programación.
* **Spring Boot**: Framework principal.
* **Spring Data JPA**: Capa de persistencia.
* **PostgreSQL**: Base de datos relacional.
* **Gutendex API**: Fuente de datos de libros.
* **Maven**: Gestor de dependencias.

---

### Requisitos y ejecución

Para ejecutar la aplicación, necesitas tener instalado:

* **JDK 22** o superior.
* **Maven**.
* **PostgreSQL** con una base de datos configurada.

**Pasos para ejecutar el proyecto:**

1.  **Clona este repositorio**.
2.  **Configura la base de datos** en el archivo `src/main/resources/application.properties` con tus credenciales de PostgreSQL.
3.  **Ejecuta la aplicación** desde tu IDE o con el comando `mvn spring-boot:run` en la terminal.

---

### Desafíos resueltos y próximos pasos

Durante el desarrollo, se resolvieron los siguientes desafíos clave:

* **Implementación de consultas personalizadas JPQL** para filtrar datos complejos.
* **Manejo de la lógica de negocio** para evitar la duplicación de registros de libros.
* **Integración y consumo de una API externa** para enriquecer los datos de la aplicación.
