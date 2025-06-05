## JWT Authentication Workflow in Spring Boot

This document explains the step-by-step JWT authentication process as visualized in the provided diagram.

---

### ✅ Step-by-Step Workflow

---

### 🔹 1. Client Sends HTTP Request
- The client (e.g., Postman, frontend app) sends an HTTP request to the server.
- The request includes a JWT in the `Authorization` header:
  ```
  Authorization: Bearer <token>
  ```

---

### 🔹 2. JwtAuthFilter Intercepts Request
- `JwtAuthFilter` intercepts the HTTP request.
- It checks for the presence of a JWT in the header.

---

### 🔹 3. Validate JWT Token
- `JwtAuthFilter` calls `JwtService` to:
  - Decode the token
  - Verify the signature
  - Check for expiration

---

### 🔹 4. Invalid or Missing JWT
If the token is:
- ❌ Invalid
- ❌ Expired
- ❌ Missing

The filter stops the request and responds with:
- `HTTP 403 Forbidden`
- With an error message like:
  - "Invalid JWT"
  - "Missing JWT"
  - "User does not exist"

---

### 🔹 5. Valid JWT → Extract User Info
- If valid:
  - `JwtService` extracts the `username` or `userId` from the token.

---

### 🔹 6. Load User from Database
- `UserDetailsService` loads user details from the **PostgreSQL** database (running in Docker).

---

### 🔹 7. Update Security Context
- If the user exists and is valid:
  - Create an `Authentication` object.
  - Set it into the `SecurityContextHolder`.
  - The user is now authenticated.

---

### 🔹 8. Forward to DispatcherServlet
- Request is passed to Spring's `DispatcherServlet`.
- Now it's an authenticated request.

---

### 🔹 9. Access Controller Logic
- `DispatcherServlet` forwards the request to the appropriate `@RestController`.
- Business logic is executed.

---

### 🔹 10. Return JSON Response
- Controller returns a JSON response.
- Server responds with:
  - `HTTP 200 OK`
  - JSON payload (data, message, etc.)

---

### 🔹 11. PostgreSQL + Docker
- Any DB-related logic (e.g., retrieving user info) hits the **PostgreSQL database**.
- Database runs inside a **Docker container**.

---

### Summary Diagram Mapping
| Visual Item              | Meaning                          |
|--------------------------|----------------------------------|
| 🔴 Postman Icon           | HTTP request from client         |
| 🔲 JwtAuthFilter         | Checks and validates JWT         |
| 🛡️ Security Block         | SecurityContextHolder update     |
| ⚙️ JwtService/UserDetails | JWT decode & DB user validation  |
| 🟢 DispatcherServlet       | Passes to controller             |
| 📄 Controller Icon         | Handles request logic/response   |
| 🐳 Docker + Elephant       | PostgreSQL database container    |