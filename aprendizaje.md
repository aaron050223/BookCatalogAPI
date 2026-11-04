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