Book API — Spring Boot + OpenAPI (Swagger)
=========================================

Small demo project that exposes a REST API to manage books. It uses Spring Boot, Spring Data JPA (H2 in-memory), and Springdoc OpenAPI (Swagger UI) for interactive documentation.

Quick facts
- Java: 17
- Spring Boot parent: 3.2.0
- OpenAPI/Swagger: springdoc-openapi-starter-webmvc-ui
- In-memory DB: H2 (default, configured in application.properties)

Run the app
----------

1) Run with Maven (development):

```bash
cd /home/zeiny/Documents/jee/demo
mvn spring-boot:run
# or using the wrapper if available:
./mvnw spring-boot:run
```

2) Build and run the fat jar:

```bash
mvn -DskipTests package
java -jar target/book-api-1.0.0.jar
```

Change the server port
----------------------

```bash
# JVM arg
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=9090"
# or when running the jar
java -jar target/book-api-1.0.0.jar --server.port=9090
```

Interactive API docs (Swagger)
-----------------------------

- Swagger UI (interactive): http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml

H2 console
----------

- H2 web console: http://localhost:8080/h2-console
  - JDBC URL: jdbc:h2:mem:bookdb
  - User: sa
  - Password: (empty)

API endpoints (summary)
-----------------------

Base path: /api/books

- GET /api/books — list all books
- GET /api/books/{id} — get book by id
- POST /api/books — create book (send JSON body, do not include id)
- PUT /api/books/{id} — update book
- DELETE /api/books/{id} — delete book
- GET /api/books/search?author=NAME — search by author
- GET /api/books?category=NAME — (if exposed) list by category

Sample JSON (create):

```json
{
  "title": "Dune",
  "author": "Frank Herbert",
  "isbn": "9780441013593",
  "price": 9.99
}
```

cURL examples
-------------

```bash
# Create
curl -s -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Dune","author":"Frank Herbert","isbn":"9780441013593","price":9.99}' | jq

# List
curl -s http://localhost:8080/api/books | jq

# Get
curl -s http://localhost:8080/api/books/1 | jq

# Update
curl -s -X PUT http://localhost:8080/api/books/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Dune (Updated)","author":"F. Herbert","isbn":"9780441013593","price":10.5}' | jq

# Delete
curl -s -X DELETE http://localhost:8080/api/books/1 -w "\nHTTP:%{http_code}\n"
```

ID generation
-------------

The `Book` entity currently uses a database sequence named `book_seq` (configured with allocationSize=1). This means the database will auto-generate numeric IDs starting at 1 and incrementing by 1. When creating a book via POST, do not provide an `id` — JPA will set it.

If you prefer the simpler identity strategy (GenerationType.IDENTITY) for auto-increment columns, update the `@GeneratedValue` annotation in `src/main/java/com/example/demo/model/Book.java`.

Testing & build
---------------

- Run unit/integration tests:

```bash
mvn -DskipTests=false test
```

- Build the jar (skip tests if you want faster builds):

```bash
mvn -DskipTests package
```

Troubleshooting
---------------

- 404 on /api/books: ensure the application started successfully and that the controller exists at `/api/books`. Check logs in the terminal where you started the app.
- Swagger UI 404: verify `springdoc-openapi-starter-webmvc-ui` is in `pom.xml` and that the app started without bean definition errors. Default UI path is `/swagger-ui/index.html`.
- If you see "No Jakarta Validation provider found" warnings, add `org.hibernate.validator:hibernate-validator` if you use bean validation annotations.

Next steps (optional)
---------------------
- Add persistence to a file-based DB or PostgreSQL for data that survives restarts.
- Add DTOs and validation with `@Valid` and `hibernate-validator`.
- Add integration tests for the controller (MockMvc or TestRestTemplate) and tests that assert the OpenAPI JSON contains expected operations.

Contact / help
--------------
If you want, I can:
- Start the app and show the OpenAPI JSON here.
- Change ID strategy to IDENTITY.
- Add example responses and request examples in the OpenAPI annotations.

Enjoy!
