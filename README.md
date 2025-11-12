# 🛍️ API REST - Gestión de Productos (Spring Boot)

Proyecto integrador para **Programación III** – UTN. Implementa una API REST completa con arquitectura en capas, validaciones, manejo de errores, JPA/H2 y documentación con Swagger.

## ⚙️ Tecnologías
- Java 17, Spring Boot 3.3.x
- Spring Web, Spring Data JPA, Validation
- H2 Database (in-memory)
- Lombok
- springdoc-openapi (Swagger UI)
- Maven

## 🚀 Cómo ejecutar en IntelliJ
1. **Archivo → New → Project from Existing Sources…** y seleccioná este directorio (`productos-api`).  
2. Asegurate que el **Project SDK** sea **Java 17**.  
3. En Maven, hacé *Reload All Maven Projects*.  
4. Ejecutá la clase `com.utn.productos.ProductosApiApplication`.  
5. Endpoints útiles:
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - Consola H2: `http://localhost:8080/h2-console`  
     - JDBC URL: `jdbc:h2:mem:productosdb` — usuario `sa` — sin password

## 📐 Arquitectura de paquetes
```
com.utn.productos
 ├─ model
 ├─ dto
 ├─ repository
 ├─ service
 ├─ controller
 └─ exception
```

## 🌐 Endpoints
| Método | Ruta                         | Descripción                         |
|-------:|------------------------------|-------------------------------------|
| GET    | `/api/productos`             | Lista todos los productos           |
| GET    | `/api/productos/{id}`        | Obtiene un producto por ID          |
| GET    | `/api/productos/categoria/{categoria}` | Lista por categoría       |
| POST   | `/api/productos`             | Crea un producto                    |
| PUT    | `/api/productos/{id}`        | Actualiza producto completo         |
| PATCH  | `/api/productos/{id}/stock`  | Actualiza solo el stock             |
| DELETE | `/api/productos/{id}`        | Elimina un producto                 |

## ✅ Validaciones
- `nombre`: **@NotBlank**, 3–100 caracteres  
- `descripcion`: máx. 500  
- `precio`: **@NotNull**, mínimo 0.01  
- `stock`: **@NotNull**, mínimo 0  
- `categoria`: **@NotNull** (`ELECTRONICA`, `ROPA`, `ALIMENTOS`, `HOGAR`, `DEPORTES`)

## ⚠️ Errores y manejo de excepciones
- `ProductoNotFoundException` → **404**  
- `MethodArgumentNotValidException` → **400** con detalle campo → error  
- Errores genéricos → **500**  
Las respuestas incluyen `timestamp`, `status`, `error` y `path`.

## 🧪 Pruebas manuales sugeridas (Swagger UI)
1. **POST**: crear 5 productos de distintas categorías.  
2. **GET**: listar todos y filtrar por categoría.  
3. **GET /{id}**: usar un ID inválido → debe devolver 404.  
4. **PUT / PATCH**: actualizar producto y solo stock.  
5. **DELETE**: eliminar un producto y verificar 204 y 404 luego.

---

> **Autor/a:** (tu nombre y legajo)  
> **Cátedra:** Programación III — Tecn. Univ. en Programación (a distancia)
