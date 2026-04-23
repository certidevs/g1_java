**Creación de Entities**
- MOVIE()
- ROOM()
- SESSION()
- TICKET(
  id: Long → identificador único
  seatRow: String → fila del asiento ("A", "B", "C"...)
  seatNumber: Integer → número de butaca dentro de la fila (12)
  price: Double → lo que ha pagado el cliente (12.50)
  buyTime: LocalDateTime → fecha y hora de compra (2026-04-15T17:45)
  sessionTime: LocalDateTime → fecha y hora de la sesión a la que corresponde el ticket (2026-04-15T18:30))
- USER(En espera)