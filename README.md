# 🧩 API de Gestión de Usuarios - BCI

Este proyecto es una API RESTful construida con **Spring Boot** para gestionar usuarios de forma segura. Se implementan las operaciones básicas de CRUD y se protege mediante JWT.

---

## ✅ Requisitos

- **Java:** 17+
- **Maven:** 3.8+
- **IDE recomendado:** IntelliJ IDEA o VS Code
- **Postman:** Para ejecutar la colección de pruebas
- **Swagger Editor:** Para visualizar el archivo `openapi.yaml`

---

## 🚀 Cómo levantar el proyecto

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu_usuario/gestor-usuarios-bci.git
   cd gestor-usuarios-bci
   ```

2. Asegúrate de tener Java 17:
   ```bash
   java -version
   ```

3. Ejecuta el proyecto:
   ```bash
   ./mvnw spring-boot:run
   ```

4. La API estará disponible en:
   ```
   http://localhost:8080
   ```

---
## 🧠 Flujo de Autenticación y Consumo de Servicios

Cuando se crea un nuevo usuario mediante `POST /user`, se ejecuta el siguiente flujo:

1. 📥 Se recibe un `request` con los datos del usuario y sus teléfonos embebidos.
2. 🛠️ Se valida que el correo no esté registrado y que la contraseña cumpla la expresión regular.
3. 🧱 Se crea el objeto `Usuario`, que contiene una lista de objetos `Telefono`.
4. 🔐 Se genera un **JWT** que incluye el `id` del usuario y su `correo`.
5. 📤 El `JWT` se devuelve en el `response`, junto con los datos del usuario creado.

---

### 🔑 Uso del Token JWT

Este token **JWT** debe ser utilizado en los siguientes endpoints:

- `GET /user` → Obtener el usuario autenticado.
- `PUT /user` → Actualizar el usuario autenticado.
- `PATCH /user/state` → Cambiar su estado.
- `DELETE /{id}` → Eliminar el usuario.

**📌 Formato de envío del token en el Header `Authorization`:**

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...
```
## 🧪 Cómo probar los endpoints

1. En la raíz del proyecto se encuentra el archivo:

   ```
   Prueba BCI.postman_collection.json
   ```

2. Importa este archivo en Postman para acceder a los 5 servicios:

   - `POST /user` → Crear nuevo usuario
   - `GET /user` → Obtener usuario autenticado (por JWT)
   - `PUT /user` → Actualizar usuario
   - `PATCH /user/state` → Cambiar estado del usuario
   - `DELETE /{id}` → Eliminar usuario por ID

3. Cada request incluye los headers necesarios y ejemplos de payload.

---

Validar si se elimino con el verbo get:
![crear-usuario.png](crear-usuario.png)
Validar si se creo con el enpoint con el verbo get:
![visualizar-usuario-creado.png](visualizar-usuario-creado.png)
Verbo patch para cambiar el estado del usuario:
![img.png](img.png)
Verbo Update para cambiar los datos del usuario:
![img_2.png](img_2.png)
Verbo Delete para eliminar al usuario:
![img_1.png](img_1.png)
Validar si se elimino con el verbo get:
![visualizar-usuario-eliminado.png](visualizar-usuario-eliminado.png)

## 📘 Documentación OpenAPI (Swagger)

En la raíz del proyecto se encuentra el archivo:

```
openapi.yaml
```

### Para visualizarlo en Swagger Editor:

1. Abre [Swagger Editor](https://editor.swagger.io/)
2. Elimina el contenido por defecto.
3. Copia y pega el contenido del archivo `openapi.yaml`
4. La interfaz Swagger mostrará los 5 endpoints definidos.

📌 *Este método es manual, no usa generación automática con Springdoc u OpenAPI Generator.*
![img_3.png](img_3.png)
---

## 🖼️ Diagrama de Solución

![img_4.png](img_4.png)

---

## 🔄 Diagrama de Flujo de los Endpoints
# Flujo de los 5 principales servicios de la API

```plaintext
Inicio
|
├─> ¿A qué endpoint accede el cliente?
|         ├────────────┬─────────────┬──────────────┬──────────────┬──────────────┐
|         ▼            ▼             ▼              ▼              ▼              ▼
|   POST /user   GET /user     PUT /user   PATCH /user/state   DELETE /{id}
|     Crear       Obtener        Actualizar       Cambiar Estado     Eliminar
|     Usuario     Usuario        Usuario          del Usuario         Usuario
|         │            │             │                  │                  │
|         ▼            ▼             ▼                  ▼                  ▼
|  Validar cuerpo    Extraer     Validar cuerpo     Validar cuerpo    Extraer
|  del request       Token       del request        del request       Token
|         │            │             │                  │                  │
|         ▼            ▼             ▼                  ▼                  ▼
|  Llamar a      Extraer ID     Extraer ID         Extraer ID        Extraer ID
|  userService   del token      del token          del token         del token
|  (createUser)  con JwtService con JwtService     con JwtService    con JwtService
|         │            │             │                  │                  │
|         ▼            ▼             ▼                  ▼                  ▼
|  Devolver      Llamar a     Llamar a           Llamar a          Llamar a
|  respuesta     userService  userService.update userService.patch userService.delete
|  con 201       (getUserById)                       │                  │
|         │            │             │               │                  │
|         ▼            ▼             ▼               ▼                  ▼
|     FIN         Devolver      Devolver          Devolver          Devolver
|                respuesta     204 No Content     204 No Content    204 No Content
|                con 200 OK
▼
FIN


```
---

## 👨‍💻 Autor

**Cristhian Ramiro Cerpa Gallegos**  
Desarrollador Backend - Java / Spring Boot