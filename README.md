# Reservation Service

A simple backend service for reserving limited resources via a REST API.

## Technologies

- Java 21
- Spring Boot 4.x
- Spring Data JPA (H2 in-memory database)
- Lombok
- Jakarta Bean Validation

## Entities

### Resource
- `id` (UUID)
- `name` (String)
- `capacity` (int)
- `status` (ACTIVE / INACTIVE)

### Reservation
- `id` (UUID)
- `resource` (FK → Resource)
- `userId` (String)
- `startDate` / `endDate` (LocalDateTime)
- `status` (ACTIVE / CANCELED)
- `createdAt` (LocalDateTime)

## Features

- Create and manage resources
- Create, list, and cancel reservations
- DTO-based requests/responses with validation
- Concurrency handling with **pessimistic locks**
- Basic error handling with appropriate HTTP status codes
- Pagination support for reservation listing (limit + offset)

## Running the Project

1. Clone the repository:
```bash
git clone https://github.com/lndrj/reservation_service-springboot/
cd reservation_service
```

2. Build and run with Maven:
```bash
mvn clean spring-boot:run
```
The API will be available at http://localhost:8080

## Endpoints

- POST /resources – Create a new resource
- POST /reservations – Create a new reservation
- GET /reservations?resourceId=&from=&to=&limit=&offset= – List reservations
- DELETE /reservations/{id} – Cancel a reservation

## Notes


- Uses an in-memory H2 database, so data is lost on shutdown.
- Concurrency is handled at the database level using pessimistic locks.
- DTOs ensure input validation (@Valid) for requests.

## Example Requests

```bash
# Create a Resource
curl -X POST http://localhost:8080/resources \
-H "Content-Type: application/json" \
-d '{"name":"Conference Room A","capacity":5}'

# Create a Reservation
curl -X POST http://localhost:8080/reservations \
-H "Content-Type: application/json" \
-d '{"resourceId":"<resource-uuid>","userId":"user123","startDate":"2026-02-17T10:00","endDate":"2026-02-17T11:00"}'

# List Reservations
curl "http://localhost:8080/reservations?resourceId=<resource-uuid>&from=2026-02-17T00:00&to=2026-02-18T00:00&limit=10&offset=0"

# Cancel a Reservation
curl -X DELETE http://localhost:8080/reservations/<reservation-uuid>
```
