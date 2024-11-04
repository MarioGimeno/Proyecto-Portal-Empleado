# Usar una imagen base oficial de JDK que incluya herramientas de compilación
FROM maven:3.8.4-openjdk-17 as build

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar los archivos de configuración de Maven y el código fuente en el contenedor
COPY pom.xml .
COPY src ./src

# Construir la aplicación usando Maven
RUN mvn clean package

# Usar una imagen base más ligera para el contenedor de ejecución
FROM openjdk:17-slim  # Cambiado a una versión disponible

WORKDIR /app

# Copiar el JAR compilado desde la etapa de construcción a la etapa de ejecución
COPY --from=build /app/target/your-jar-file.jar app.jar  # Cambia `your-jar-file.jar` por el nombre de tu JAR

# Exponer el puerto que utiliza tu aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
