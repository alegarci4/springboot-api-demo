# Workshop Spring Boot 3 - Progra 3

Una API REST básica construida con Spring Boot 3 y Java 17.

## ¿Qué hace?

Es una API que maneja saludos y un simulador de préstamos. Fui construyéndola paso a paso durante el taller, habilitando endpoints,
agregando validaciones y manejo de errores.

## Endpoints

- `GET /api/v1` - Health check
- `GET /api/v1/saludos?nombre=Ana` - Saludo personalizado
- `POST /api/v1/saludos` - Saludo por body
- `POST /api/v1/simulaciones/prestamo` - Simulador de préstamo


## Cómo correrlo

```bash
mvn spring-boot:run
```

## Tests

```bash
mvn test
```

