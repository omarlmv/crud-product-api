# Usa la imagen oficial de OpenJDK 17
FROM openjdk:17-jdk-slim-buster

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR generado por el build
COPY target/*.jar app.jar

# Configura el puerto expuesto
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-Dserver.port=8080", "-jar", "app.jar"]
