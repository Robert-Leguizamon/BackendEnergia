# Guía definitiva de cómo construir una relación entre dos tablas en Spring Boot (JPA), explicada paso a paso:
1. Definir la Cardinalidad (¿Quién pertenece a quién?)
Antes de programar, debes decidir la relación. En tu caso fue:
Un Usuario puede tener muchas Auditorías.
Muchas Auditorías pertenecen a un solo Usuario.
Esto en base de datos se llama 1:N (Uno a Muchos). En JPA se traduce como @ManyToOne (desde el hijo) y @OneToMany (desde el padre).
2. El Lado "Muchos" (El hijo que lleva la FK)
Es la tabla que tiene la columna física de la Llave Foránea. En tu ejemplo es Audit_log.
Pasos en el código:
Cambiar el ID simple por el Objeto: No uses Long user_id, usa private User user.
Anotación @ManyToOne: Le dice a JPA: "Muchas filas de aquí van a una sola fila de allá".
Anotación @JoinColumn: Define cómo se llama la columna en la base de datos (SQL).
code
Java
@ManyToOne(fetch = FetchType.LAZY) // LAZY: No traigas al usuario si no lo pido
@JoinColumn(name = "user_id")      // Nombre de la columna física en la DB
private User user;
3. El Lado "Uno" (El padre - Opcional)
Es la tabla principal (User). No es obligatorio poner la relación aquí, pero se hace si quieres poder decir usuario.getLogs() y ver su historial.
Pasos en el código:
Crear una Lista: private List<Audit_log> logs.
Anotación @OneToMany:
mappedBy = "user": Crucial. Le dice a JPA que la relación ya está mandada por el campo "user" en la otra clase. Evita que JPA cree una tercera tabla innecesaria.
code
Java
@OneToMany(mappedBy = "user")
private List<Audit_log> auditLogs;
4. Romper el Bucle Infinito (El problema que tuviste)
Como el Padre conoce al Hijo y el Hijo al Padre, cuando Spring intenta generar un JSON, entra en un círculo sin fin.
Cómo evitarlo:
Usa @JsonIgnore en el lado que no quieras mostrar cuando consultes el otro.
Regla de oro: Siempre pon @JsonIgnore en la lista del Padre y en los campos sensibles como password.
5. Lógica de Negocio (Servicios)
En SQL normal harías: INSERT INTO audit_log (action, user_id) VALUES ('LOGIN', 5).
En JPA (Java) trabajas con instancias:
Obtienes el objeto Padre (User) de la DB.
Creas el objeto Hijo (Audit_log).
Asignas el objeto completo: log.setUser(usuarioEncontrado).
Guardas el hijo.
Resumen de Anotaciones Clave
Anotación	Para qué sirve
@Entity	Le dice a Java: "Esta clase es una tabla de DB".
@ManyToOne	Define que esta tabla es la "hija" y tiene una FK.
@JoinColumn	Le da nombre a la columna física (ej. user_id).
@OneToMany	(Opcional) Permite al padre ver a sus hijos en una lista.
mappedBy	Indica quién es el "dueño" de la relación (evita duplicados).
@JsonIgnore	Evita bucles infinitos y protege datos sensibles (passwords).
En resumen, el flujo de trabajo es:
Modelo: Crear las clases y conectarlas con @ManyToOne.
Repository: Crear interfaces que hereden de JpaRepository.
Service: Crear métodos que reciban objetos, no solo IDs, para guardar las relaciones.
Controller: Asegurarse de que el JSON resultante sea limpio y seguro.