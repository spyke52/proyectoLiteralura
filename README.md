# 📚 LiterAlura - Buscador de Libros

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15%2B-informational)

Aplicación Java con Spring Boot para buscar y gestionar libros utilizando la API de Gutendex (Proyecto Gutenberg).

## 🚀 Características principales

- 🔍 Buscar libros por título
- 📂 Guardar libros en base de datos local
- 👨‍💻 Listar autores registrados
- 🎂 Buscar autores vivos en un año específico
- 🌍 Filtrar libros por idioma
- 🏆 Top 10 libros más descargados

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3.5.3**
  - Spring Data JPA
  - Spring Web
- **PostgreSQL** (Base de datos)
- **Gutendex API** (https://gutendex.com/)
- **Maven** (Gestión de dependencias)

## 📦 Instalación

### Requisitos previos
- Java 17 o superior
- PostgreSQL 15+
- Maven 3.8+

### Pasos para configurar

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/literalura.git
   cd literalura
   ```

2. Configura la base de datos:
   - Crea una base de datos PostgreSQL llamada `literalura_db`
   - Actualiza las credenciales en `src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
     spring.datasource.username=tu_usuario
     spring.datasource.password=tu_contraseña
     ```

3. Compila y ejecuta:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## 🖥️ Uso

Al iniciar la aplicación, se mostrará un menú interactivo con las siguientes opciones:

```
=== MENÚ LITERALURA ===
1 - Buscar libro por título
2 - Listar libros registrados
3 - Listar autores registrados
4 - Buscar autores vivos en un año
5 - Listar libros por idioma
6 - Top 10 libros más descargados
0 - Salir
```

### Ejemplo de flujo de trabajo

1. Buscar un libro por título (ej: "Don Quijote")
2. Ver los libros registrados
3. Explorar autores y sus obras
4. Filtrar por idioma (ej: "es" para español)

## 📊 Estructura del proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── alura/
│   │           └── literalura/
│   │               ├── model/       # Entidades y DTOs
│   │               ├── repository/  # Repositorios JPA
│   │               ├── service/     # Lógica de negocio
│   │               ├── principal/   # Interfaz de usuario
│   │               └── LiteraluraApplication.java
│   └── resources/
│       └── application.properties   # Configuración
pom.xml                             # Dependencias Maven
```

## 🌟 Contribuciones

¡Las contribuciones son bienvenidas! Por favor abre un issue o envía un pull request.

## 📄 Licencia

Este proyecto está bajo la licencia MIT.

---

Saludos
