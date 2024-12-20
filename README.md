# CRUD Product API with System Statistics

## Descripción

Este proyecto proporciona una API RESTful desarrollada con **Spring Boot** y **WebFlux** para gestionar productos y categorías, además de almacenar estadísticas de uso del sistema de forma asíncrona. Entre las funcionalidades principales se incluyen:

- Gestión de productos y categorías.
- Generación de estadísticas avanzadas del sistema, como:
    - Cantidad de productos por categoría.
    - Promedio de precio por categoría.
    - Productos más populares.
    - Última actualización de estadísticas.
- Autenticación basada en **JWT**.

## Requisitos Previos

Asegúrate de tener instaladas las siguientes herramientas:

- **Java** 17
- **Maven** 3.6+
- **Docker** y **Docker Compose**

## Configuración del Proyecto

### Clonar el Repositorio

```bash
$ git clone https://github.com/omarlmv/crud-product-api.git
$ cd crud-product-api
```

### Configuración de Variables de Entorno

Crea un archivo `.env` en la raíz del proyecto con las siguientes variables:

```env
JWT_SECRET=your-very-secure-secret
```

Si estás utilizando una base de datos distinta a H2, asegúrate de configurar las credenciales necesarias.

### Construir la Aplicación

Usa Maven para compilar el proyecto:

```bash
$ mvn clean install
```

### Ejecución Local con Docker

Ejecuta los siguientes comandos para construir y ejecutar la aplicación localmente usando Docker:

```bash
$ docker build -t crud-product-api .
$ docker run -p 8080:8080 crud-product-api
```

Esto iniciará la aplicación API REST en `http://localhost:8080`.

Si necesitas probar la API, asegúrate de que las rutas estén configuradas correctamente y utiliza las colecciones de Postman proporcionadas.

### Ejecución en Producción

El despliegue en producción está configurado para ejecutarse automáticamente cada vez que se realiza un push al repositorio principal. Esto asegura que la versión más reciente esté siempre disponible.

## Endpoints Clave

### Gestión de Productos

- **Crear Producto**: `/api/products` (POST)
- **Obtener Producto**: `/api/products/{id}` (GET)
- **Actualizar Producto**: `/api/products/{id}` (PUT)
- **Eliminar Producto**: `/api/products/{id}` (DELETE)

### Estadísticas del Sistema

- **Consultar Estadísticas**: `/api/statistics` (GET)

### Autenticación

- **Login**: `/api/auth/login` (POST)

## Colecciones de Postman

Para facilitar las pruebas, se han preparado dos colecciones de Postman:

1. **Colección Local**: Configurada para apuntar a la API en ejecución local (`http://localhost:8080`).

    - Archivo: `api-crud-product-local.postman_collection.json`

2. **Colección Producción**: Configurada para apuntar a la URL pública de la API desplegada.

    - Archivo: `api-crud-product.postman_collection.json`

Importa estas colecciones en Postman y prueba los diferentes endpoints.
