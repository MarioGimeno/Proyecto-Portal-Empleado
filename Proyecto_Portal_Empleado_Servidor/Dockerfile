# Usar una imagen base oficial de Maven con OpenJDK 17 para la compilación
FROM maven:3.8.4-openjdk-17 as build

# Establecer el directorio de trabajo en el contenedor de compilación
WORKDIR /app

# Copiar el archivo de configuración de Maven y las dependencias
COPY pom.xml .

# Descargar dependencias de Maven para evitar reconstrucciones innecesarias
RUN mvn dependency:go-offline -B

# Copiar el resto del código fuente
COPY src ./src

# Compilar la aplicación y generar el archivo JAR
RUN mvn clean package -DskipTests

# Usar una imagen base de OpenJDK más ligera para la ejecución
FROM openjdk:17-slim

# Establecer el directorio de trabajo en el contenedor de ejecución
WORKDIR /app

# Copiar el archivo JAR desde el contenedor de compilación
COPY --from=build /app/target/Proyecto_Portal_Empleado-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto que utiliza tu aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
