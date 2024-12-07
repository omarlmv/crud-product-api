# Usa la imagen oficial de OpenJDK 17
FROM openjdk:17-jdk-slim-buster

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia los archivos jar generados por el build
COPY target/*.jar app.jar

# Exponer el puerto en el que la aplicación se ejecuta
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
