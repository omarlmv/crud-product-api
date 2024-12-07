# Product API

API for managing products, categories, and statistics.

## Prerequisites

- Docker

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/omarlmv/crud-product-api.git
    cd crud-product-api
    ```

## Running the Project

1. Build and run the Docker container:
    ```sh
    docker build -t crud-product-api .
    docker run -p 8080:8080 crud-product-api
    ```

## Testing

1. Run the tests:
    ```sh
    mvn test
    ```

## Deployment

1. Build the Docker image:
    ```sh
    docker build -t crud-product-api .
    ```
2. Tag the Docker image:
    ```sh
    docker tag crud-product-api registry.heroku.com/api-product-app/web
    ```

3. Log in to the Heroku container registry:
    ```sh
    heroku container:login
    ```

4. Push the Docker image to the Heroku container registry:
    ```sh
    docker push registry.heroku.com/api-product-app/web
    ```

5. Release the Docker image on Heroku:
    ```sh
    heroku container:release web --app api-product-app
    ```

6. Run the Docker container:
    ```sh
    docker run -p 8080:8080 crud-product-api
    ```

## API Documentation

API documentation is available at `/swagger-ui.html` when the application is running.

## Postman Collection

A Postman collection for testing the API is available in the `api-product.postman_collection.json` file. To import it into Postman:

1. Open Postman.
2. Click on `Import` in the top left corner.
3. Select the `api-product.postman_collection.json` file from the root of this repository.
4. Click `Open` to import the collection.