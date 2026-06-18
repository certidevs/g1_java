# Backlog de Ideas — Cine (Grupo 1)

Documento orientativo y didactico. El proyecto se entrega TAL CUAL: las ideas y correcciones
recogidas aqui NO se van a implementar dentro del curso. Su unico objetivo es mostrar como
continua el ciclo de vida del software despues de la primera entrega, como se detecta deuda
tecnica leyendo el codigo real y como un equipo prioriza el trabajo futuro. Todo lo que sigue
esta fundamentado en el codigo del repositorio (clases, controladores, repositorios y plantillas
concretas), no en recomendaciones genericas. Este backlog es complementario a las notas sueltas
de `src/main/resources/templates/docs/BACKLOG.md` y mucho mas estructurado.


## 1. Resumen del proyecto

- **Dominio:** Venta y gestion de entradas de cine ("FluxFilms"). Cartelera de peliculas,
  salas, sesiones (funciones), compra de entradas con seleccion de butaca y combo de comida,
  pago simulado, generacion de QR de acceso, reseñas y favoritos.

- **Stack (versiones reales del `pom.xml`):**
  - Java 25, Spring Boot 4.0.5 (parent `spring-boot-starter-parent`).
  - Spring MVC (`spring-boot-starter-webmvc`) + Thymeleaf + `thymeleaf-extras-springsecurity6`.
  - Spring Data JPA (`spring-boot-starter-data-jpa`) sobre **H2 en memoria** (`spring-boot-h2console`, driver `org.h2.Driver`).
  - Spring Security (`spring-boot-starter-security`) con BCrypt.
  - Spring Validation (Jakarta Bean Validation).
  - Lombok.
  - ZXing `core` + `javase` 3.5.2 para generar codigos QR.
  - springdoc-openapi `3.0.2` (Swagger UI) para la API REST.
  - Bootstrap 5.3.8 y Font Awesome 7.2.0 via WebJars.
  - Persistencia configurada en `src/main/resources/application.properties`:
    `jdbc:h2:mem:cine_db`, `spring.jpa.hibernate.ddl-auto=create-drop`, `show-sql=true`,
    consola H2 habilitada y `multipart.max-file-size=10MB`. **No hay perfiles** (`dev`/`test`/`prod`)
    ni configuracion de PostgreSQL, pese a lo que afirma el `README.md`.

- **Entidades principales y relaciones** (paquete `com.demo.model`):
  - `Movie` — pelicula. Tiene `Set<Genre>` (`@ElementCollection`), `MinAge`, `MovieStatus`,
    `Section`, duracion, trailer, imagen, sinopsis, `active` (borrado logico) y `@ManyToOne Director`.
    Campos `@Transient` `avgRating` y `countReviews` que se calculan en el controlador.
  - `Director` — solo `id` y `name` (unico).
  - `Room` — sala con `name`, `price`, `capacity`, `ScreenType`, `active` y `@OneToMany` a `Session`.
  - `Session` — funcion: `startTime`, `numAds`, `Language`, `active`, `@ManyToOne Room` (NOT NULL),
    `@ManyToOne Movie` (NOT NULL).
  - `Ticket` — entrada/butaca: `seatRow`, `seatNumber`, `price`, `priceCombo`, `purchaseTime`,
    `TicketStatus` (IN_PROGRESS por defecto), `active`, `qrScanned`, `maxUses`/`currentUses`,
    datos de tarjeta (`cardOwner`, `cardNumber`, `cardExpirationDate`, `cardCode`),
    `@ManyToOne Session` y `@ManyToOne User`.
  - `TicketLine` — `quantity` y `@ManyToOne Ticket` (entidad practicamente sin uso real todavia).
  - `User` — implementa `UserDetails`: `username`/`email` (unicos), `password`, `Role`, `active`, `imageUrl`.
  - `Review` — `title`, `description`, `rating` (1-5, con validaciones Bean Validation),
    `creationDate`, `@ManyToOne Movie`, `@ManyToOne User`.
  - `Favorite` — `createdAt`, `@ManyToOne User`, `@ManyToOne Movie` (relacion usuario-pelicula favorita).
  - Enums: `Genre`, `Language`, `MinAge`, `MovieStatus`, `ScreenType`, `Section`, `TicketStatus`, `Role`.

- **Que se puede hacer hoy (por roles):**
  - **Visitante (sin login):** ver cartelera con filtros (`/movies`), ficha de pelicula con
    reseñas y fechas de sesiones (`/movies/{id}`), listado y detalle de sesiones, listado de
    reseñas, y consultar la API REST publica de peliculas (`/api/v1/movies`, Swagger en `/swagger-ui`).
  - **Usuario (ROLE_USER):** ademas, comprar entradas (seleccionar butaca en `session-detail`,
    elegir combo, pagar con `PaymentForm` en `/tickets/{id}/finish`), ver sus tickets, crear y
    editar sus propias reseñas, marcar/desmarcar favoritos (`/favorites/toggle`), editar su perfil
    y avatar (`/profile`).
  - **Administrador (ROLE_ADMIN):** CRUD de peliculas (con subida de imagen y logica Flux),
    salas, sesiones (que autogeneran butacas), gestion de usuarios (`/admin/users`,
    alta/edicion/activar/desactivar), borrado de reseñas y gestion de tickets.
  - **Transversal:** generacion de QR por ticket (`QrService`), escaneo del QR para validar acceso
    (`/tickets/qr-scan/{id}`), datos de demo cargados al arrancar, paginas de error 403/404/500.


## 2. Features (nuevas funcionalidades)

Ideas de funcionalidad que la app NO tiene todavia y aportarian valor. Todas en linea con la
filosofia del curso (Spring server-side, Thymeleaf + Bootstrap, sin frameworks JS).

### F-01 · Confiteria como entidad real (Snack/Combo)
- Qué: Hoy la comida esta "hardcodeada" como tres botones en `ticket-form.html` con precios
  fijos (8, 10, 15) y solo se guarda el numero `priceCombo` en `Ticket`. Convertir el combo en
  una entidad de dominio gestionable por el admin.
- Por qué aporta: Permite al cine cambiar precios y productos sin tocar HTML, y deja datos para
  informes de ventas de confiteria. Es justo el `TODO` que ya aparece comentado en `TicketLine`.
- Qué habría que tocar: nueva entidad `Snack` (o `Combo`) + `SnackRepository`; relacionar
  `TicketLine` con `Snack` (descomentar el `@ManyToOne Food` previsto); `TicketController.saveTicket`
  para calcular precio desde BD; plantilla `tickets/ticket-form.html`; CRUD admin nuevo.
- Dificultad: Media

### F-02 · Promociones y descuentos (dia del espectador, tarifa joven)
- Qué: El campo `price` del `Ticket` ya menciona en su comentario "session.room.price + extra comida
  - descuento", pero el descuento no existe. Añadir promociones aplicables por dia/tipo.
- Por qué aporta: Es una funcionalidad nuclear de cualquier cine y aprovecha el calculo de precio
  ya centralizado en `saveTicket`.
- Qué habría que tocar: entidad `Promotion` (name, discountPercent, validFrom, validUntil);
  `@ManyToOne` opcional desde `Session`; logica en `TicketController.saveTicket`; mostrar en
  `session-detail.html` y `ticket-detail.html`.
- Dificultad: Media

### F-03 · Listado y detalle de directores
- Qué: `DirectorController` solo tiene `new`/`save`; el listado y detalle estan comentados
  (`directors/director-list`, `directors/director-detail`) y `saveDirector` redirige a `/directors`,
  ruta que devuelve 404 hoy.
- Por qué aporta: Completa el CRUD de una entidad que ya existe y arregla una redireccion rota.
  Permite ver la filmografia (`MovieRepository.findByDirector` ya esta implementado).
- Qué habría que tocar: `DirectorController` (descomentar/crear `list` y `detail`), plantillas
  `directors/director-list.html` y `directors/director-detail.html`, enlace en navbar.
- Dificultad: Baja

### F-04 · Cancelacion y reembolso de entradas por el usuario
- Qué: Existe `TicketStatus.CANCELLED` y el `README` habla de "cancelacion de reservas", pero no
  hay ningun endpoint de cancelacion para el usuario; solo `tickets/deactivate/{id}`.
- Por qué aporta: Cierra el ciclo de vida del ticket (IN_PROGRESS -> FINISHED -> CANCELLED) que el
  enum ya modela pero el codigo no usa.
- Qué habría que tocar: nuevo `POST /tickets/{id}/cancel` en `TicketController` con comprobacion de
  propiedad y de que la sesion no haya empezado; liberar la butaca; mostrar boton en `ticket-detail.html`.
- Dificultad: Media

### F-05 · Buscador y filtros de reseñas
- Qué: `ReviewController.reviews` carga todas las reseñas ordenadas por fecha, sin filtros.
  Permitir filtrar por pelicula, por puntuacion minima o por usuario.
- Por qué aporta: Mejora la usabilidad y reutiliza queries existentes
  (`findByMovie_IdOrderByCreationDateDesc`, `findByUser_Id`).
- Qué habría que tocar: parametros `@RequestParam` en `reviews()`, una query de filtrado en
  `ReviewRepository`, y controles de filtro en `reviews/review-list.html`.
- Dificultad: Baja

### F-06 · Una reseña por usuario y pelicula, y solo tras comprar entrada
- Qué: Hoy un mismo usuario puede dejar infinitas reseñas de la misma pelicula y de peliculas que
  no ha visto. El `README` afirma "valorar peliculas que hayan visualizado o reservado", pero no se
  comprueba.
- Por qué aporta: Da credibilidad a la media de puntuacion (`calculateAverageRatingByMovieId`) y
  evita spam.
- Qué habría que tocar: query `existsByUser_IdAndMovie_Id` en `ReviewRepository`; comprobacion en
  `ReviewController.saveReview` cruzando con `TicketRepository` (tickets FINISHED del usuario para
  esa pelicula); mensajes en la vista.
- Dificultad: Media

### F-07 · Mis favoritos como pantalla propia
- Qué: Existe el `toggle` de favoritos y se calculan en el perfil, pero no hay una pagina dedicada
  "Mis peliculas favoritas".
- Por qué aporta: Da visibilidad a una funcionalidad ya construida (`FavoriteService.findFavoriteMovies`)
  y mejora la experiencia del usuario registrado.
- Qué habría que tocar: nuevo `GET /favorites` en `FavoriteController`, plantilla nueva reutilizando
  `fragments/movie-card.html`, enlace en navbar.
- Dificultad: Baja

### F-08 · Aforo y "agotado" en la cartelera y la home
- Qué: `HomeController` ya calcula butacas vendidas (`countBySession_IdAndPurchaseTimeIsNotNull`) para
  la "sesion destacada", pero la cartelera y el listado de sesiones no muestran ocupacion ni avisan
  de sesiones agotadas.
- Por qué aporta: Informacion clave para el usuario; reutiliza la query de aforo ya existente.
- Qué habría que tocar: calculo de ocupacion en `SessionController.sessions`, badge en
  `sessions/session-list.html`; opcionalmente marcar la sesion como no comprable si esta llena.
- Dificultad: Media

### F-09 · Tarea programada para actualizar el estado de las peliculas
- Qué: `MovieService.updateStatusByDate` recalcula el `MovieStatus` segun la fecha, pero hoy se
  invoca en cada request (`MovieController.moviesList`/`movie`). Convertirlo en un proceso programado.
- Por qué aporta: Evita recalcular y persistir el estado en cada visita (ver B-03) y centraliza la
  logica temporal en un unico punto.
- Qué habría que tocar: `@EnableScheduling` + un `@Scheduled` que recorra peliculas activas una vez
  al dia; quitar la llamada por-request de los controladores.
- Dificultad: Media

### F-10 · Panel de estadisticas / dashboard para el administrador
- Qué: No existe panel agregado para el admin (recaudacion, entradas vendidas por pelicula, ocupacion
  por sala). Los datos ya estan en BD.
- Por qué aporta: Es el tipo de informe que un cine necesita; consolida queries como
  `calculateTotalMoneySpentByUserId`, `countBySession_Id...` o agregados nuevos.
- Qué habría que tocar: nuevo controlador `/admin/dashboard`, queries `@Query` de agregacion en
  `TicketRepository`, plantilla con tarjetas Bootstrap.
- Dificultad: Media

### F-11 · Completar la API REST (sesiones, salas, reseñas) y protegerla
- Qué: Solo existe `MovieRestController` (`/api/v1/movies`). Ofrecer la API tambien para otras
  entidades y aplicar seguridad (hoy `/api/v1/**` es `permitAll`).
- Por qué aporta: La API ya esta documentada con springdoc; ampliarla y asegurarla es el camino
  natural hacia integraciones externas.
- Qué habría que tocar: nuevos `@RestController` en `controller/api/`, DTOs de entrada/salida,
  reglas en `SecurityConfig` (p.ej. escritura solo ADMIN), y ampliar `ApiExceptionAdvice`.
- Dificultad: Media

### F-12 · Recuperacion de contrasena y cambio de contrasena seguro
- Qué: No hay flujo de "olvide mi contrasena" ni verificacion de contrasena actual al cambiarla en
  `/profile`.
- Por qué aporta: Funcionalidad basica de cualquier sistema con login; mejora la seguridad real.
- Qué habría que tocar: `UserService`/`UserController`, token temporal (entidad o tabla), plantillas
  nuevas; el envio de email queda fuera de alcance (ver seccion 4).
- Dificultad: Alta

### F-13 · Validacion de solapamiento de sesiones en la misma sala
- Qué: `SessionController.saveSession` guarda cualquier sesion sin comprobar que la sala este libre
  en esa franja (startTime + duracion de la pelicula).
- Por qué aporta: Evita el error de negocio de dos peliculas a la vez en la misma sala; aprovecha
  `findByRoom_Id`/`findByMovie_IdAndStartTimeBetween`.
- Qué habría que tocar: validacion en un nuevo metodo de `service` (p.ej. `SessionService`),
  mensaje de error en `sessions/session-form.html`.
- Dificultad: Media

### F-14 · Historial y exportacion de la entrada (PDF / pagina imprimible)
- Qué: El `README` menciona "entradas en PDF con QR" pero no existe. Como minimo, una vista
  imprimible del ticket FINISHED con su QR.
- Por qué aporta: Caso de uso muy comun; el QR ya se genera en `QrService`.
- Qué habría que tocar: nueva vista imprimible o endpoint que sirva la entrada; opcionalmente una
  libreria de PDF server-side (fuera del nucleo del curso).
- Dificultad: Media

### F-15 · Validacion de combos en servidor (no solo en el HTML)
- Qué: El precio del combo se fija con JavaScript en `ticket-form.html` y llega como `priceCombo`
  por un input oculto, sin validar en servidor. Un usuario podria enviar cualquier valor.
- Por qué aporta: Integridad de los importes cobrados; conecta directamente con F-01.
- Qué habría que tocar: validar `priceCombo` contra un catalogo en `TicketController.saveTicket`
  (idealmente la entidad `Snack` de F-01).
- Dificultad: Baja

### F-16 · Limpieza de reservas IN_PROGRESS abandonadas
- Qué: Cuando un usuario entra en `buy` y crea un ticket pero no paga, queda un `Ticket` con
  `purchaseTime == null` ocupando potencialmente esa butaca en la logica de la vista.
- Por qué aporta: Libera butacas "atascadas" y mantiene los datos limpios. Relacionado con B-02.
- Qué habría que tocar: `@Scheduled` que revierta o borre tickets IN_PROGRESS antiguos sin compra;
  query nueva en `TicketRepository`.
- Dificultad: Media


## 3. Fixes (correcciones y deuda tecnica)

Problemas, riesgos y deuda detectados en el codigo ACTUAL.

### B-01 · Logica de siembra de datos duplicada y no idempotente en `main()`
- Problema: `G1JavaApplication.main()` contiene ~400 lineas que insertan directores, peliculas,
  salas, sesiones, miles de tickets y reseñas, y ademas existe `DataInitializer` que inserta otro
  usuario, peliculas y reseñas. Ambos se ejecutan en cada arranque y no comprueban si ya hay datos.
- Dónde: `src/main/java/com/demo/G1JavaApplication.java` (metodo `main`) y
  `src/main/java/com/demo/config/DataInitializer.java`.
- Impacto: Con `create-drop` y H2 en memoria "funciona", pero crea peliculas/reseñas duplicadas
  (p.ej. "Super Mario Galaxy" y "Hokum" se crean en los dos sitios), es fragil y contradice tanto
  el `README` (que afirma "el main() solo arranca Spring" y siembra idempotente) como la convencion
  del propio curso de centralizar la siembra en `DataInitializer`.
- Propuesta: Mover toda la siembra a `DataInitializer`, añadir guarda `if (repository.count() > 0) return;`
  y eliminar la insercion del `main()`. Unificar el set de datos para no duplicar.
- Severidad: Alta

### B-02 · Condicion de carrera y doble reserva de la misma butaca
- Problema: Una butaca se considera "vendida" solo cuando `purchaseTime != null`. En
  `session-detail.html` se pinta el asiento como disponible si `purchaseTime == null`, pero el flujo
  `tickets/buy/{id}` -> `POST /tickets` (`saveTicket`) CREA UN TICKET NUEVO en lugar de reservar el
  ticket-butaca seleccionado, y no bloquea el asiento. Dos usuarios pueden comprar el mismo asiento
  a la vez. Ademas no hay `@Transactional` en ningun servicio ni control de concurrencia.
- Dónde: `TicketController.saveTicket` (`@PostMapping("tickets")`), `TicketController.buyTicket`,
  `session-detail.html` (lineas ~250-262), ausencia total de `@Transactional` en `service/`.
- Impacto: Sobreventa de asientos, datos inconsistentes y experiencia rota. Es el riesgo funcional
  mas serio de la aplicacion.
- Propuesta: Que `finish` (o `saveTicket`) reserve el ticket-butaca existente en vez de crear uno
  nuevo; marcar el asiento ocupado de forma transaccional; añadir bloqueo optimista
  (`@Version`) en `Ticket` o comprobacion de disponibilidad dentro de un metodo `@Transactional`.
- Severidad: Alta

### B-03 · Escritura en BD durante peticiones GET (efecto colateral en lecturas)
- Problema: `MovieController.moviesList` y `MovieController.movie` recorren las peliculas y llaman a
  `movieService.updateStatusByDate(...)`, que hace `movieRepository.save(...)`. Una simple visita de
  lectura escribe en la base de datos.
- Dónde: `MovieController.moviesList` (`movies.forEach(movieService::updateStatusByDate)`),
  `MovieService.updateStatusByDate` -> `updateStatus` -> `save`.
- Impacto: GET con efectos secundarios (mala practica REST/HTTP), escrituras innecesarias en cada
  request, posible coste y comportamiento inesperado bajo carga o con varias pestañas.
- Propuesta: Calcular el estado en memoria para mostrarlo sin persistir, o moverlo a la tarea
  programada de F-09. Separar "calcular" de "guardar".
- Severidad: Media

### B-04 · Consultas N+1 al calcular media y conteo de reseñas
- Problema: En `MovieController.moviesList`, por cada pelicula del listado se ejecutan dos queries
  (`calculateAverageRatingByMovieId` y `countByMovie_Id`) dentro de un bucle, mas la propia query del
  listado. Con muchas peliculas son decenas de consultas por pagina.
- Dónde: `MovieController.moviesList` (bucle `for (Movie movie : movies)`), `MovieController.movie`.
- Impacto: Rendimiento degradado a medida que crece la cartelera; patron N+1 clasico.
- Propuesta: Una unica query agregada que devuelva `movieId -> (avg, count)` (`GROUP BY`), o una
  proyeccion/DTO; tambien evitar el calculo en el listado si no se muestra.
- Severidad: Media

### B-05 · `TicketLineRepository.findByTicket_IdAndId` devuelve el tipo equivocado
- Problema: El metodo se declara `Optional<Ticket> findByTicket_IdAndId(Long ticketId, Long lineId)`
  pero esta en el repositorio de `TicketLine`; deberia devolver `Optional<TicketLine>`. Spring Data
  derivaria la query sobre `TicketLine`, no sobre `Ticket`.
- Dónde: `repository/TicketLineRepository.java`.
- Impacto: Codigo confuso y propenso a fallos si algun dia se usa; refleja que `TicketLine` esta a
  medio integrar (tambien tiene campos comentados y casi no se usa en el flujo real).
- Propuesta: Corregir el tipo de retorno a `Optional<TicketLine>` o eliminar el metodo si no se usa.
  Decidir si `TicketLine` se mantiene (ligado a F-01) o se retira.
- Severidad: Baja

### B-06 · API REST publica sin proteccion y con borrado fisico
- Problema: En `SecurityConfig`, `/api/v1/**` es `permitAll()` para todos los metodos. Cualquiera
  puede crear, modificar (`PUT`/`PATCH`) o BORRAR peliculas via `MovieRestController` sin autenticarse.
  El `delete` ademas hace `deleteById` (hard delete) con el try/catch de integridad comentado.
- Dónde: `SecurityConfig` (`.requestMatchers("/api/v1/**").permitAll()`),
  `MovieRestController.delete`/`create`/`update`/`updatePartial`.
- Impacto: Hueco de seguridad grave (escritura y borrado anonimos) e inconsistencia con el borrado
  logico (`active`) que usa el resto de la app.
- Propuesta: Restringir escritura de la API a ADMIN, dejar solo lectura publica; sustituir el hard
  delete por borrado logico (`active=false`) coherente con el MVC.
- Severidad: Alta

### B-07 · `ApiExceptionAdvice` aplana todos los errores a 400
- Problema: El `@ExceptionHandler(Exception.class)` devuelve SIEMPRE `400 BAD_REQUEST`, incluso para
  un `404` de `ResponseStatusException` lanzado en `findOne`/`update`. Se pierde el codigo real.
- Dónde: `controller/api/ApiExceptionAdvice.java`.
- Impacto: La API miente sobre el resultado (un "no encontrado" se reporta como "peticion incorrecta"),
  dificultando el consumo y el testing.
- Propuesta: Manejar `ResponseStatusException` respetando su `status`, y reservar el 400 para errores
  de validacion/integridad; añadir handler especifico para `MethodArgumentNotValidException`.
- Severidad: Media

### B-08 · Validaciones de dominio ausentes en `Movie`, `Room`, `Session` y `Ticket`
- Problema: Solo `Review` y `PaymentForm` tienen anotaciones Bean Validation. `Movie` (titulo,
  duracion), `Room` (capacidad, precio), `Session` (startTime) y `Ticket` no validan nada en servidor;
  los `@PostMapping` usan `@ModelAttribute` sin `@Valid`/`BindingResult`. El `required`/`min` del HTML
  es la unica barrera y se salta facilmente.
- Dónde: `model/Movie.java`, `model/Room.java`, `model/Session.java`, `model/Ticket.java`;
  `MovieController.saveMovie`, `RoomController.createRoom`, `SessionController.saveSession`.
- Impacto: Se pueden guardar peliculas sin titulo, salas con capacidad negativa o nula, sesiones sin
  fecha, etc. `SessionController.saveSession` hace `capacity / 10`: una capacidad null provoca
  `NullPointerException`.
- Propuesta: Añadir `@NotBlank`/`@NotNull`/`@Positive`/`@Future` en las entidades y `@Valid` +
  `BindingResult` en los controladores, devolviendo al formulario con errores.
- Severidad: Media

### B-09 · Manejo de errores debil ante IDs inexistentes (`orElseThrow` sin mensaje)
- Problema: Multiples controladores hacen `findById(id).orElseThrow()` sin tipo ni mensaje, lo que
  produce `NoSuchElementException` -> 500. Falta un `@ControllerAdvice` global para la parte MVC que
  traduzca a la pagina `error/404.html` existente.
- Dónde: `TicketController` (`ticketDetail`, `editTicket`, `buyTicket`, `scanQr`, `finish`),
  `MovieController.editMovie`, `RoomController.editRoom`, `SessionController.editSession`,
  `UserController.deactivateUser/activateUser`.
- Impacto: Un ID mal escrito en la URL muestra un error 500 generico en lugar de un 404 amable; mala
  experiencia y posible filtracion de trazas.
- Propuesta: Crear un `@ControllerAdvice` MVC (analogo al de la API) que capture
  `NoSuchElementException`/entidad-no-encontrada y renderice `error/404.html`. Existe la plantilla
  pero no el handler que la dispara explicitamente.
- Severidad: Media

### B-10 · `Movie.minAge` no se aplica en la compra (control parental inexistente)
- Problema: `Movie` tiene `MinAge` (`+7`, `+12`, `+18`...) y `User` no guarda edad ni fecha de
  nacimiento, por lo que la clasificacion por edad es puramente informativa: cualquiera compra
  cualquier entrada.
- Dónde: `model/Movie.java` (`minAge`), `model/User.java` (sin edad), `TicketController.saveTicket`.
- Impacto: La clasificacion por edades no tiene efecto real; el dato existe pero no protege nada.
- Propuesta: Añadir fecha de nacimiento a `User` (o al registro `RegisterForm`) y validar la edad
  minima al comprar; como minimo, avisar en la UI.
- Severidad: Baja

### B-11 · Filtro de tickets del admin con condicion redundante/incorrecta
- Problema: En `TicketRepository.filterTickets`, la clausula
  `AND (:sessionId IS NULL OR t.status = ...FINISHED)` ata el filtro de estado al parametro
  `sessionId`: si no se pasa `sessionId` no se filtra por estado, y si se pasa, exige FINISHED. La
  intencion (mostrar solo tickets comprados) queda mezclada con el filtro de sesion.
- Dónde: `repository/TicketRepository.java` (`filterTickets`).
- Impacto: Resultados inconsistentes segun se rellene o no el filtro de sesion; logica de negocio
  escondida en una condicion confusa.
- Propuesta: Separar las condiciones: filtrar siempre por `purchaseTime IS NOT NULL` (ya lo hace) y
  tratar el estado como filtro propio e independiente del `sessionId`.
- Severidad: Baja

### B-12 · Datos sensibles de tarjeta almacenados en claro en `Ticket`
- Problema: `Ticket` persiste `cardNumber`, `cardExpirationDate` y `cardCode` (CVV) tal cual llegan
  del `PaymentForm`. Aunque el pago es simulado, se guarda el numero completo y el CVV en la BD.
- Dónde: `model/Ticket.java` (campos de tarjeta), `TicketController.finish`.
- Impacto: Mala practica de seguridad/PCI incluso en demo; enseña un patron peligroso. El CVV no
  debe almacenarse nunca.
- Propuesta: No persistir el CVV; guardar como mucho los 4 ultimos digitos enmascarados; idealmente
  delegar el pago (ver seccion 4). Util como ejemplo didactico de "que no hacer".
- Severidad: Media

### B-13 · `README.md` describe funcionalidad e infraestructura que no existen
- Problema: El `README.md` afirma perfiles `dev`/`test`/`prod`, `application-dev.properties` y
  `application-prod.properties`, PostgreSQL 18 via `compose.yaml`, `DOCKER.md`, capturas en
  `docs/screenshots/`, siembra idempotente y "control de disponibilidad evitando reservas duplicadas".
  Nada de eso esta en el repositorio (solo hay `application.properties` con H2, sin perfiles, sin
  `compose.yaml`, y con la doble reserva descrita en B-02).
- Dónde: `README.md` frente a `src/main/resources/application.properties` y raiz del proyecto.
- Impacto: Documentacion que induce a error a quien clone el proyecto; comandos del README fallan.
- Propuesta: Alinear el README con la realidad (o implementar lo prometido). Buen ejemplo de por que
  la documentacion forma parte de la deuda tecnica.
- Severidad: Media

### B-14 · `System.out.println` y emojis en logs/codigo de produccion en lugar de logging
- Problema: Hay `System.out.println` en `HomeController.index`, `RoomController.createRoom` y
  `ApplicationStartupRunner` (este ultimo con emojis en los logs de arranque). El proyecto ya usa
  SLF4J (`@Slf4j`) en `UserController` y `FileService`.
- Dónde: `HomeController.index`, `RoomController.createRoom`, `config/ApplicationStartupRunner.java`.
- Impacto: Logs poco profesionales, sin niveles ni formato; ruido en consola; inconsistencia con el
  resto del codigo que si usa logging.
- Propuesta: Sustituir por `log.info/debug` con `@Slf4j` y eliminar los emojis de los mensajes de log.
- Severidad: Baja

### B-15 · Cobertura de tests practicamente nula
- Problema: El unico test es `G1JavaApplicationTests.contextLoads()`. No hay tests de repositorios,
  servicios ni controladores (MockMvc), pese a que el `pom.xml` incluye `webmvc-test`, `data-jpa-test`,
  `thymeleaf-test` y `security-test`.
- Dónde: `src/test/java/com/demo/G1JavaApplicationTests.java` (unico fichero de test).
- Impacto: Cualquier cambio puede romper la app sin que nadie se entere; la logica critica (precio,
  reserva, seguridad por roles) no esta cubierta.
- Propuesta: Añadir tests de `MovieService.updateStatusByDate` (logica pura, facil de testear),
  tests de repositorio con `@DataJpaTest` para las `@Query`, y tests MockMvc de seguridad por rol.
  Engancha directamente con la seccion 4.
- Severidad: Alta

### B-16 · `Director.name` unico pero el alta no controla duplicados ni usa la query existente
- Problema: `Director` tiene `@Column(unique = true)` y `DirectorRepository` expone
  `findByNameIgnoreCase`, pero `DirectorController.saveDirector` hace `save` directo. Crear un director
  repetido lanza una excepcion de integridad que acaba en 500.
- Dónde: `DirectorController.saveDirector`, `model/Director.java`, `repository/DirectorRepository.java`.
- Impacto: Error 500 en una accion de usuario normal (admin creando director ya existente); la query
  preparada para evitarlo no se usa.
- Propuesta: Comprobar con `findByNameIgnoreCase` antes de guardar y devolver un mensaje al formulario.
- Severidad: Baja

### B-17 · `GlobalModelAttributes` lanza una query de favoritos en cada vista (incluida la API y usuarios anonimos)
- Problema: El `@ControllerAdvice` `GlobalModelAttributes` ejecuta `findMovieIdsByUserId` para cada
  request de un `@Controller` autenticado, y `MovieController` ademas vuelve a añadir el mismo atributo.
- Dónde: `controller/GlobalModelAttributes.java`, `MovieController.moviesList`/`movie`.
- Impacto: Query repetida/duplicada por request; pequeño coste evitable y atributo redundante.
- Propuesta: Mantener el calculo solo en el advice (quitar el duplicado en `MovieController`) y, si se
  quiere, cachear por sesion. Menor, pero ilustra deuda de rendimiento difusa.
- Severidad: Baja


## 4. Como evolucionaria en un entorno real (ciclo de vida del software)

La primera entrega de FluxFilms es un punto de partida, no el final. En un equipo profesional, a
partir de aqui empezaria un ciclo continuo de mejora apoyado en practicas y herramientas concretas,
aterrizadas en este proyecto:

- **Control de versiones y ramas:** trabajar cada item de este backlog en su propia rama
  (`feature/promociones`, `fix/doble-reserva-butaca`) con Pull Requests y revision de codigo entre
  compañeros. El repositorio ya esta en Git; faltaria disciplina de ramas, mensajes de commit claros
  y revision antes de fusionar.

- **Tests automatizados y cobertura:** hoy solo hay `contextLoads()` (B-15). El camino real es
  añadir pruebas unitarias (`MovieService`), de repositorio (`@DataJpaTest` para las `@Query` de
  `MovieRepository`/`TicketRepository`) y de controlador con MockMvc verificando la seguridad por
  rol de `SecurityConfig`, midiendo cobertura con JaCoCo y exigiendo un minimo antes de fusionar.

- **CI/CD:** el workflow `.github/workflows/build.yml` solo compila (`mvn clean compile`) y unicamente
  de forma manual (`workflow_dispatch`). Evolucionaria a ejecutarse en cada push/PR, lanzar
  `mvn verify` con los tests, publicar el informe de cobertura y, mas adelante, construir y publicar
  el artefacto desplegable.

- **Contenedores y despliegue:** el `README` ya habla de PostgreSQL y Docker, pero no hay `compose.yaml`
  ni `Dockerfile` (B-13). El siguiente paso seria empaquetar la app en una imagen, definir perfiles
  reales (`dev` con H2, `prod` con PostgreSQL persistente) y desplegarla en un servidor o servicio
  gestionado, dejando atras el H2 en memoria con `create-drop`.

- **Observabilidad y logs:** sustituir los `System.out.println` por logging estructurado (B-14),
  añadir Spring Boot Actuator para health checks y metricas, y centralizar logs. Hoy no hay forma de
  saber que pasa en produccion mas alla de la consola.

- **Seguridad:** cerrar la API publica de escritura (B-06), no almacenar datos de tarjeta/CVV (B-12),
  delegar el pago en una pasarela real, añadir recuperacion de contrasena (F-12) y revisar el control
  por edad (B-10). La seguridad es un proceso continuo, no una casilla.

- **Feedback de usuarios y priorizacion continua:** medir que peliculas se ven y se compran
  (el dashboard de F-10), recoger incidencias reales y reordenar este backlog en cada iteracion segun
  valor y esfuerzo. El backlog es un documento vivo.

Estas direcciones (Docker, CI/CD completo, observabilidad, pasarela de pago, API segura) exceden el
alcance del curso, centrado en Spring server-side con Thymeleaf, pero conviene conocerlas como
horizonte hacia el que evolucionan las aplicaciones reales despues de la primera entrega.


## 5. Priorizacion orientativa

Valor y esfuerzo en escala Bajo/Medio/Alto. Horizonte: Corto (siguiente iteracion),
Medio (varias iteraciones), Largo (estrategico/fuera del alcance del curso).

| Idea | Tipo | Valor | Esfuerzo | Horizonte |
|------|------|-------|----------|-----------|
| B-02 Doble reserva de butaca / sin @Transactional | Fix | Alto | Alto | Corto |
| B-01 Siembra duplicada y no idempotente | Fix | Alto | Medio | Corto |
| B-06 API REST publica sin proteccion + hard delete | Fix | Alto | Bajo | Corto |
| B-15 Cobertura de tests casi nula | Fix | Alto | Medio | Corto |
| B-03 Escrituras en peticiones GET | Fix | Medio | Bajo | Corto |
| B-08 Validaciones de dominio ausentes | Fix | Medio | Medio | Corto |
| B-09 404 amable ante IDs inexistentes | Fix | Medio | Bajo | Corto |
| B-13 README desalineado con la realidad | Fix | Medio | Bajo | Corto |
| B-04 N+1 en media/conteo de reseñas | Fix | Medio | Medio | Medio |
| B-07 Errores de la API aplanados a 400 | Fix | Medio | Bajo | Medio |
| B-12 Datos de tarjeta/CVV en claro | Fix | Medio | Medio | Medio |
| F-01 Confiteria como entidad real | Feature | Alto | Medio | Medio |
| F-02 Promociones y descuentos | Feature | Alto | Medio | Medio |
| F-13 Solapamiento de sesiones en sala | Feature | Alto | Medio | Medio |
| F-08 Aforo y "agotado" en cartelera | Feature | Medio | Medio | Medio |
| F-09 Tarea programada de estados | Feature | Medio | Medio | Medio |
| F-03 Listado/detalle de directores | Feature | Bajo | Bajo | Corto |
| F-05 Filtros de reseñas | Feature | Medio | Bajo | Corto |
| F-06 Una reseña por usuario tras comprar | Feature | Medio | Medio | Medio |
| F-10 Dashboard de administrador | Feature | Alto | Medio | Medio |
| F-11 Completar y proteger la API REST | Feature | Medio | Medio | Largo |
| F-12 Recuperacion de contrasena | Feature | Medio | Alto | Largo |
| F-14 Entrada en PDF / imprimible | Feature | Medio | Medio | Largo |
| Docker + CI/CD + observabilidad + pasarela pago | Evolucion | Alto | Alto | Largo |
