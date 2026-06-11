# JobHorizon Backend - Agent Instructions

This file contains high-signal, repo-specific context to help agents work effectively in this codebase. 

## Domain & Naming Conventions
- **Spanish Domain Language**: Domain models, entities, database tables, and packages use **Spanish** (e.g., `Usuario`, `OfertaTrabajo`, `Empresa`). **DO NOT** translate these to English when writing code, JPA entities, or API responses.
- **PascalCase Database Objects**: The database schema uses **PascalCase** for both tables and columns (e.g., `IdUsuario`, `PasswordHash`). 
- **JPA Mappings**: When creating JPA entities, you must explicitly declare the exact PascalCase name in annotations (`@Table(name = "Usuario")`, `@Column(name = "Correo")`). Do not rely on default snake_case physical naming strategies.

## Database & Migrations
- **Engine**: MSSQL (SQL Server). Use SQL Server syntax (e.g., `IDENTITY(1,1)`, `DATETIME2`). Note the use of `GO` as a batch separator in SQL scripts.
- **Flyway**: `spring.jpa.hibernate.ddl-auto` is set to `none`. **All** schema changes must be done via Flyway migration scripts in `src/main/resources/db/migration/`. Format: `V<version>__<description>.sql`.

## Tech Stack & Code Style
- **Stack**: Java 21, Spring Boot 4.x, Maven.
- **Lombok**: Heavily used. Avoid writing boilerplate getters, setters, or constructors. Use `@Entity`, `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, and `@Builder`.
- **Integrations**: Email services are implemented using the **Resend Java SDK** (`com.resend:resend-java`).

## Commands & Environment
- **Environment Setup**: The application requires environment variables defined in `.env` to start (e.g., `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `RESEND_API_KEY`).
- **Compile**: `./mvnw clean compile`
- **Test**: `./mvnw test`
- **Run**: `./mvnw spring-boot:run` (Ensure `.env` values are loaded or passed).
