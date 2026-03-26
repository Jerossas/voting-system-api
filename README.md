# Voting System API

REST API para sistema de votaciones electrónica. Construida con Java 21 y Spring Boot 4.0.3, siguiendo arquitectura limpia y autenticación JWT con roles.

---

## Tabla de contenidos

- [Arquitectura](#arquitectura)
- [Requisitos previos](#requisitos-previos)
- [Variables de entorno](#variables-de-entorno)
- [Pasos para ejecutar localmente](#pasos-para-ejecutar-localmente)
- [Documentación con Swagger](#documentacion-con-swagger)
- [Ejemplos en Postman](#ejemplos-en-postman)
- [Captura de estadísticas](#captura-de-estadisticas)

---

## Arquitectura

El proyecto es un **monorepo Gradle multi-módulo** dividido en cuatro capas:

```
voting-system-api/
|-- domain/          # Modelos, repositorios (interfaces), excepciones de dominio
|-- application/     # Casos de uso (interfaces + implementaciones), comandos
|-- infrastructure/  # Adaptadores JPA, Spring Data, encoder de contraseñas
|-- web/             # Controladores REST, DTOs, filtros JWT, configuración de seguridad
```

Las dependencias solo fluyen hacia adentro: `web -> application -> domain <- infrastructure`

---

## Requisitos previos

| Herramienta | Versión Mínima |
|-------------|----------------|
| Java (JDK)  | 21             |
| Gradle      | 9.3.0          |
| PostgreSQL  | 14+            |

---

## Variables de entorno

La aplicación requiere las siguientes variables de entorno. Se pueden definir en el sistema operativo o si se va a utilizar Intellij IDE, se pueden configurar en el IDE. Los valores de la tabla se pueden cambiar a gusto.

| Variable         | Descripción                                      | Ejemplo                                              |
|------------------|--------------------------------------------------|------------------------------------------------------|
| `DB_URL`         | JDBC URL de la base de datos PostgreSQL          | `jdbc:postgresql://localhost:5432/voting_db`         |
| `DB_USERNAME`    | Usuario de la base de datos                      | `postgres`                                           |
| `DB_PASSWORD`    | Contraseña de la base de datos                   | `secret`                                             |
| `JWT_SECRET`     | Clave secreta Base64 para firmar tokens JWT (≥32 bytes) | `OlNPJ5u2WtH1IdBa4sci2SfVZSU4CxX1TIB5TIE84sI`   |
| `JWT_EXPIRATION` | Tiempo de expiración del token en milisegundos   | `86400000` *(24 horas)*                              |
| `ADMIN_EMAIL`    | Email del administrador inicial (seed)           | `admin@voting.com`                                   |
| `ADMIN_PASSWORD` | Contraseña del administrador inicial             | `Admin1234!`                                         |

---

## Pasos para ejecutar localmente

### 1. Clonar e instalar dependencias

```bash
git clone https://github.com/Jerossas/voting-system-api.git
cd voting-system-api
gradlew build
```

**Nota:** el comando `gradlew build` se debe ejecutar en la raiz del proyecto.

### 2. Crear la base de datos en PostgreSQL

```sql
CREATE DATABASE voting_db;
```

### 3. Crear variables de entorno

#### Crear variables de entorno en Linux

```bash
export DB_URL=jdbc:postgresql://localhost:5432/voting_db
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
export JWT_SECRET=OlNPJ5u2WtH1IdBa4sci2SfVZSU4CxX1TIB5TIE84sI
export JWT_EXPIRATION=86400000
export ADMIN_EMAIL=admin@voting.com
export ADMIN_PASSWORD=Admin1234!
```

**Nota:** de la anterior forma solo se mantienen en la sesión actual. Si se reinicia el ordenador, se deben crear nuevamente.

#### Crear variables de entorno en Windows cmd

```bash
set DB_URL=jdbc:postgresql://localhost:5432/voting_db
set DB_USERNAME=postgres
set DB_PASSWORD=postgres
set JWT_SECRET=OlNPJ5u2WtH1IdBa4sci2SfVZSU4CxX1TIB5TIE84sI
set JWT_EXPIRATION=86400000
set ADMIN_EMAIL=admin@voting.com
set ADMIN_PASSWORD=Admin1234!
```

**Nota:** de la anterior forma solo se mantienen en la sesión actual. Si se reinicia el ordenador, se deben crear nuevamente.

### 4. Compilar y ejecutar

```bash
gradlew :web:bootRun
```

**Nota:** el comando `gradlew :web:bootRun` se debe ejecutar en la raiz del proyecto.

---

## Documentación con Swagger

Luego de correr la aplicación, se puede acceder a la documentación interactiva en:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

El JSON de la especificación OpenAPI está disponible en:

[http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Autenticarse en la UI

1. Ejecuta **POST /api/auth/sign-in** desde Swagger con las credenciales del admin
2. Copia el `token` de la respuesta
3. Haz clic en el botón **Authorize 🔒** (arriba a la derecha)
4. Escribe `Bearer <tu_token>` y haz clic en **Authorize**
5. Ya todos los endpoints quedan autenticados para la sesión actual

---

## Ejemplos en Postman

### Autenticación con usuario administrador

<img width="1436" height="609" alt="image" src="https://github.com/user-attachments/assets/f0d65602-a279-4727-a7f6-a695129d3e12" />

### Registrar candidato

<img width="1436" height="671" alt="image" src="https://github.com/user-attachments/assets/76ee16a1-446a-4cb1-97a9-2d85e4f00541" />

### Registrar votante

<img width="1438" height="676" alt="image" src="https://github.com/user-attachments/assets/b453bc66-6826-49a0-b1f5-7e2c18eed249" />

### Ver votantes

<img width="1438" height="891" alt="image" src="https://github.com/user-attachments/assets/360d9e02-229e-4239-8799-41157338cc21" />

### Ver candidatos

<img width="1440" height="943" alt="image" src="https://github.com/user-attachments/assets/9bc861c7-ac0b-4707-bb9e-19dbfbca5b62" />

### Votar

<img width="1433" height="674" alt="image" src="https://github.com/user-attachments/assets/ee8f727c-fda5-4e37-a98d-f33002323b30" />

---

## Captura de estadísticas

<img width="1438" height="890" alt="image" src="https://github.com/user-attachments/assets/081f6197-adc8-480a-bafa-674fb4e2bd19" />
