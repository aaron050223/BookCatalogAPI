# Registro de aprendizaje del grupo

## Entrega N

- **Comportamiento que añadimos o contrato que evolucionamos**:
- **Test y rojo observado (distinguir compilación/configuración de comportamiento)**:
- **Cambio mínimo y resultado verde**:
- **Refactorización**:
- **Decisiones sobre capas y contrato HTTP**:
- **Contribuciones, revisión y siguiente paso**:

---

## Entrega 1

- **Comportamiento que añadimos o contrato que evolucionamos**: Comprobamos el esqueleto base. El proyecto arranca con 
una API en /books que responde texto plano a los 5 métodos HTTP (GET, POST, PUT, DELETE).


- **Test y rojo observado (distinguir compilación/configuración de comportamiento)**: Ninguno. La prueba BookCheckpoint01Test
ya venía programada y pasa directamente en verde para enseñarnos la estructura básica.


- **Cambio mínimo y resultado verde**: No hizo falta tocar código Java. Ejecutamos ./mvnw test y confirmamos que todo 
arrancaba bien.


- **Refactorización**: Ninguna.


- **Decisiones sobre capas y contrato HTTP**: 
  - Ruta base: `/books`.
  - Rutas y verbos: 

      - `GET /books`: Lista de libros.
      - `GET /books/{id}`: Buscar por id.
      - `POST /books`: Crear libros.
      - `PUT /books/{id}`: Actualizar libro.
      - `DELETE /books/{id}`: Borrar libro.

  - Toda la lógica está dentro del `CatalogController`

- **Contribuciones, revisión y siguiente paso**:
  - Revisión conjunta de la estructura y redacción de la entrega.
  - Siguiente paso: Activar la entrega 2, donde creamos  la clase `Book`, los DTOs y cambiaremos el texto plano por 
respuestas JSON.

---

## Entrega 2

- **Comportamiento que añadimos o contrato que evolucionamos**:
  Cambiamos el texto plano inicial por respuestas en formato JSON. Introducimos el modelo interno `Book`, los objetos de transferencia de datos (`BookRequestDTO` y `BookResponseDTO`) y el almacenamiento de libros en memoria mediante una colección.


- **Test y rojo observado (distinguir compilación/configuración de comportamiento)**:
  - *Compilación/Estructura:* Al activar la Entrega 2 (`java classroom/ActivarEntrega.java 2`), los tests no compilaban inicialmente porque faltaban los récords/clases de los DTOs y el modelo `Book`.
  - *Comportamiento:* Tras crear las estructuras mínimas, el test `BookCheckpoint02Test` falló (rojo) porque el controlador devolvía cadenas de texto (`text/plain`) en lugar de objetos y listas en JSON (`application/json`).


- **Cambio mínimo y resultado verde**:
  - Creamos los récords `BookRequestDTO` (entrada sin `id`) y `BookResponseDTO` (salida con `id`).
  - Quitamos `produces = MediaType.TEXT_PLAIN_VALUE` de `@RequestMapping` en `BookController`.
  - Guardamos los libros en memoria usando un `HashMap<Long, Book>` y generamos los IDs de forma segura con `AtomicLong`.
  - Actualizamos los endpoints para que reciban `@RequestBody BookRequestDTO` y devuelvan DTOs convertidos a JSON.
  - Ejecutamos `./mvnw test` y conseguimos que los tests pasaran a verde.


- **Refactorización**:
  Creamos un método privado auxiliar `toResponseDTO(Book book)` dentro del controlador para no repetir la conversión de 
`Book` a `BookResponseDTO` en cada endpoint.


- **Decisiones sobre capas y contrato HTTP**:
  - **DTOs vs Modelo:** Separamos el modelo interno (`Book`) de la representación externa (`BookRequestDTO` para peticiones
y `BookResponseDTO` para respuestas) para no exponer nuestra estructura interna directamente.
  - **Uso de colecciones seguras:** Usamos `HashMap` y `AtomicLong` para asegurarnos de que la aplicación no falle ni pise datos si varios usuarios intentan crear o consultar libros exactamente al mismo tiempo.
  - **Contrato HTTP temporal:** En esta fase, la creación (`POST`) responde estado `200 OK` con el JSON del nuevo libro.


- **Contribuciones, revisión y siguiente paso**:
  - Implementación en grupo de los DTOs, el modelo y la adaptación del controlador.
  - Revisión del código para comprender el paso de JSON a objetos Java (`@RequestBody`) y viceversa.
  - Siguiente paso: Activar la Entrega 3.

---

## Entrega 3

- **Comportamiento que añadimos o contrato que evolucionamos**:
  Mejoramos los códigos de respuesta HTTP usando `ResponseEntity`. Ahora, al crear un libro (`POST`), la API
responde con un `201 Created` y añade la cabecera `Location: /books/{id}` con la URL del libro recién creado.
Al borrar (`DELETE`), responde con un `204 No Content` sin enviar ningún cuerpo. Las consultas (`GET`) y 
modificaciones (`PUT`) siguen devolviendo `200 OK`.


- **Test y rojo observado (distinguir compilación/configuración de comportamiento)**:
  - *Compilación:* El proyecto compilaba bien sin errores de código ni de imports.
  - *Comportamiento:* Al activar la entrega (`java classroom/ActivarEntrega.java 3`), el test `BookCheckpoint03Test` falló
  porque el `POST` devolvía un `200 OK` en vez de un `201` y no traía la cabecera `Location`, y el `DELETE` devolvía
  un `200` en lugar de un `204`.

- **Cambio mínimo y resultado verde**:
  - Cambiamos el tipo de retorno de los métodos de `BookController.java` para que todos devuelvan un `ResponseEntity`.
  - En el `POST /books`, pusimos `ResponseEntity.created(URI.create("/books/" + newId)).body(response)`.
  - En el `DELETE /books/{id}`, usamos `ResponseEntity.noContent().build()`.
  - Volvimos a lanzar `./mvnw test` y la prueba pasó a verde.


- **Refactorización**:
  Dejamos todos los endpoints del controlador adaptados para que devuelvan siempre sus respuestas envueltas en un `ResponseEntity`
de forma limpia.


- **Decisiones sobre capas y contrato HTTP**:
- **Uso de ResponseEntity:** Nos sirve para controlar exactamente qué código de estado (`201`, `204`, `200`, `404`) y qué
cabeceras devolvemos al cliente en cada caso.
- **Cabecera Location:** Sirve para informarle a quien use la API de la ruta exacta donde se puede consultar el nuevo libro
creado.


- **Contribuciones, revisión y siguiente paso**:
  - **Contribuciones:** Modificación del controlador `BookController.java`, comprobación de los tests y redacción de este
  apartado de aprendizaje.
  - **Siguiente paso:** Activar la **Entrega 4** (`java classroom/ActivarEntrega.java 4`) para sacar la lógica y la lista
  en memoria del controlador y llevarlas a una clase de servicio (`BookService`).

---

## Entrega 4

- **Comportamiento que añadimos o contrato que evolucionamos**:
  Separamos la lógica de nuestra aplicación en capas. Sacamos del controlador el mapa donde guardábamos los libros en memoria y el contador de IDs, y los llevamos a una nueva clase de servicio (`BookService`).

- **Test y rojo observado (distinguir compilación/configuración de comportamiento)**:
  - *Compilación/Estructura:* Al activar la Entrega 4 (`java classroom/ActivarEntrega.java 4`), el proyecto no compilaba porque los tests buscaban la clase/interfaz `BookService` que aún no habíamos creado.
  - *Comportamiento:* Tras crear la estructura básica de `BookService`, los tests verificaron que el controlador realmente llamaba a los métodos del servicio en lugar de procesar los datos él mismo.

- **Cambio mínimo y resultado verde**:
  - Creamos la clase `BookService` con `@Service` para gestionar la colección en memoria y la conversión de los libros a DTOs.
  - Modificamos `BookController` para recibir el servicio a través de su constructor y delegarle todas las operaciones (`list`, `get`, `create`, `update`, `delete`).
  - Al ejecutar `./mvnw test`, confirmamos que todos los métodos devolvían el resultado esperado a través del servicio y pasaban a verde.

- **Refactorización**:
  Movimos el método auxiliar de conversión `toResponseDTO` del controlador hacia la clase `BookService`, haciendo que el controlador dependa únicamente del servicio para obtener los datos listos.

- **Decisiones sobre capas y contrato HTTP**:
  - **Separación limpia:** El servicio solo maneja datos en Java y desconoce completamente el protocolo HTTP (no usa anotaciones de Spring Web ni respuestas `ResponseEntity`).
  - **Controlador delgado:** El controlador se limita a recibir la petición HTTP, pedir los datos al servicio y devolver la respuesta con el código HTTP adecuado (`200`, `201` o `204`).

- **Contribuciones, revisión y siguiente paso**:
  - Creación de la capa de servicio y refactorización conjunta del controlador para aplicar la inyección de dependencias por constructor.
  - Revisión del código para entender cómo se delegan las responsabilidades entre Controller y Service.
  - Siguiente paso: Activar la Entrega 5.