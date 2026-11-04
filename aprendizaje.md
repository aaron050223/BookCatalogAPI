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
  Creamos un método privado auxiliar `toResponseDTO(Book book)` dentro del controlador para no repetir la conversión de `Book` a `BookResponseDTO` en cada endpoint.

- **Decisiones sobre capas y contrato HTTP**:
  - **DTOs vs Modelo:** Separamos el modelo interno (`Book`) de la representación externa (`BookRequestDTO` para peticiones y `BookResponseDTO` para respuestas) para no exponer nuestra estructura interna directamente.
  - **Uso de colecciones seguras:** Usamos `HashMap` y `AtomicLong` para asegurarnos de que la aplicación no falle ni pise datos si varios usuarios intentan crear o consultar libros exactamente al mismo tiempo.
  - **Contrato HTTP temporal:** En esta fase, la creación (`POST`) responde estado `200 OK` con el JSON del nuevo libro.

- **Contribuciones, revisión y siguiente paso**:
  - Implementación en grupo de los DTOs, el modelo y la adaptación del controlador.
  - Revisión del código para comprender el paso de JSON a objetos Java (`@RequestBody`) y viceversa.
  - Siguiente paso: Activar la Entrega 3.