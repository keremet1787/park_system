# Parking System

Backend-система парковки: автомобили, парковочные места, въезд и выезд с расчётом стоимости.

## Стек
Java 21, Spring Boot 4, Spring Data JPA, PostgreSQL, Swagger (springdoc-openapi), Lombok, Maven.

## Запуск

1. Установить JDK 17+ и PostgreSQL.
2. Создать базу данных (или использовать стандартную `postgres`):
```sql
   CREATE DATABASE parking_db;
```
3. Задать настройки подключения через переменные окружения. Если переменные не заданы, используются значения по умолчанию.

   | Переменная | По умолчанию |
      |---|---|
   | `DB_HOST` | `localhost` |
   | `DB_PORT` | `5432` |
   | `DB_NAME` | `postgres` |
   | `DB_USERNAME` | `postgres` |
   | `DB_PASSWORD` | `postgres` |

   Пример для Windows PowerShell:
```powershell
   $env:DB_NAME="parking_db"
   $env:DB_PASSWORD="ваш_пароль"
```
4. Запустить приложение:
```bash
   ./mvnw spring-boot:run
```
(Windows: `mvnw.cmd spring-boot:run`)

Таблицы создаются автоматически при первом запуске (`ddl-auto: update`).

## Swagger
После запуска: http://localhost:8080/swagger-ui/index.html

## API

| Метод | Адрес | Описание |
|---|---|---|
| POST | `/api/vehicles` | создать автомобиль |
| GET | `/api/vehicles` | список автомобилей |
| GET | `/api/vehicles/{id}` | автомобиль по ID |
| PUT | `/api/vehicles/{id}` | изменить |
| DELETE | `/api/vehicles/{id}` | удалить |
| POST | `/api/parking-spots` | создать место |
| GET | `/api/parking-spots` | список мест |
| GET | `/api/parking-spots/free` | свободные места |
| GET | `/api/parking-spots/{id}` | место по ID |
| PUT | `/api/parking-spots/{id}` | изменить |
| DELETE | `/api/parking-spots/{id}` | удалить |
| POST | `/api/sessions/entry` | въезд автомобиля |
| POST | `/api/sessions/{id}/exit` | выезд, расчёт стоимости |
| GET | `/api/sessions`, `/api/sessions/{id}` | сессии |

## Правила
- Типы: `CAR`, `MOTORCYCLE`, `TRUCK`.
- Тариф: CAR 100, MOTORCYCLE 50, TRUCK 150 сом/час.
- Стоимость округляется вверх до полного часа, минимум 1 час.
- Тип автомобиля должен совпадать с типом парковочного места.
- Один автомобиль не может иметь две активные сессии; занятое место занять нельзя.

## Коды ответов
201 создано, 200 успешно, 204 удалено, 400 неверные данные или несовместимый тип, 404 не найдено, 409 конфликт (место занято, дубликат номера).