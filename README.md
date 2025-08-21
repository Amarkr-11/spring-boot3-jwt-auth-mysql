# spring-boot3-jwt-auth-mysql
Secure authentication and role-based authorization implemented using Spring Boot 3, Spring Security, JWT, and MySQL database.

**🚀 Overview**

    auth-demo is a Spring Boot 3 authentication & authorization project with:
    
    JWT-based authentication
    
    Role-based access control (USER, ADMIN)
    
    MySQL database persistence
    
    REST APIs for registration, login, and user management
    
    Exception handling & validation
    
    This project provides a ready-to-use login module that can be integrated into any backend system.


**⚙️ Tech Stack**
  
    Java 17
    
    Spring Boot 3.3.2
    
    Spring Web
    
    Spring Security
    
    Spring Data JPA
    
    Spring Validation
    
    JWT (jjwt 0.11.5) – secure token handling
    
    MySQL – relational database
    
    Lombok – boilerplate reduction (optional)
    
    Maven – dependency management


**🛡️ Security Workflow**

    User registers or logs in
    
    Password is hashed with BCrypt
    
    JWT token is issued
    
    JWT Authentication
    
    Client sends Authorization: Bearer <token> header
    
    JwtAuthenticationFilter validates token
    
    Security context stores authenticated user
    
    Role-based access
    
    @PreAuthorize("hasRole('ADMIN')") used for admin-only endpoints
    
    Regular users (USER) can access their own resources


**📑 Entities**
    User
    
    id, username, email, password
    
    Role (enum: USER, ADMIN)
    
    createdAt, updatedAt
    
    Implements UserDetails (for Spring Security)



**📦 DTOs**

    RegisterRequest → User registration (username, email, password)
    
    AdminRegisterRequest → Admin registering another user (with role)
    
    RegisterResponse → Registration success (id, username, email, role, token)
    
    AuthRequest → Login (username, password)
    
    AuthResponse → Login success (token)


**⚙️ Configuration**
    application.yml
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/auth_demo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
        username: root
        password: root
      jpa:
        hibernate:
          ddl-auto: update
        show-sql: true
    
    server:
      port: 8080
    
    app:
      jwt:
        secret: "change-me-super-secret-key-512-bits-min"
        expiration-ms: 86400000 # 24h
    
    
    👉 Replace username and password with your MySQL credentials.
    👉 Change the JWT secret to a strong 512-bit key.

**🏃 Getting Started**

    1. Clone & Build
    git clone https://github.com/your-repo/auth-demo.git
    cd auth-demo
    mvn clean install
    
    2. Configure Database
    Create MySQL DB:
    CREATE DATABASE auth_demo;
    Update application.yml with DB username/password.
    
    3. Run
    mvn spring-boot:run
    Server will start at: http://localhost:8080


**🔑 API Endpoints**
    1. Register (User)
    
    POST /api/auth/register
    
    Request
    
    {
      "username": "john",
      "email": "john@example.com",
      "password": "secret123"
    }
    
    
    Response
    
    {
      "id": 1,
      "username": "john",
      "email": "john@example.com",
      "role": "USER",
      "token": "eyJhbGciOiJIUzI1..."
    }
    
    2. Register (By Admin)
    
    POST /api/auth/admin/register
    
    Requires Authorization: Bearer <admin-token>
    
    Request
    
    {
      "username": "alice",
      "email": "alice@example.com",
      "password": "password",
      "role": "ADMIN"
    }
    
    3. Login
    
    POST /api/auth/login
    
    Request
    
    {
      "username": "john",
      "password": "secret123"
    }
    
    
    Response
    
    {
      "token": "eyJhbGciOiJIUzI1..."
    }
    
    4. Get Current User
    
    GET /api/users/me
    Header: Authorization: Bearer <token>
    
    Response
    
    {
      "id": 1,
      "username": "john",
      "email": "john@example.com",
      "role": "USER"
    }
    
    5. User Management (Admin only)
    
    GET /api/users → List all users
    
    GET /api/users/{id} → Get user by ID
    
    POST /api/users → Create user
    
    PUT /api/users/{id} → Update user
    
    DELETE /api/users/{id} → Delete user

⚠️ Error Handling

    Validation errors → 400 Bad Request (field → error message)
    
    Invalid login → 401 Unauthorized
    
    Other errors → 500 Internal Server Error

✅ Features Checklist

    ✔️ JWT authentication
    ✔️ BCrypt password hashing
    ✔️ Role-based authorization
    ✔️ Global exception handler
    ✔️ REST API with DTOs
    ✔️ Validation on inputs
    ✔️ Admin-controlled user registration

📝 Notes

    First user must be registered manually (will be USER role).

    To create an ADMIN, change DB entry or use /admin/register from an existing admin.
    
    JWT token must be attached to every secured API request.
    
    Extend easily by adding more roles & permissions.
