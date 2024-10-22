# Usar una imagen base que incluya el Android SDK y las herramientas de construcción necesarias
FROM thyrlian/android-sdk:latest

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el código fuente y los archivos de gradle en el contenedor
COPY . /app

# Aceptar las licencias de Android SDK (necesario para la construcción)
RUN yes | sdkmanager --licenses

# Construir la aplicación usando Gradle
RUN ./gradlew assembleDebug

# Aquí se termina la construcción. No se puede ejecutar la aplicación Android en el contenedor.
# Deberías usar esta imagen para ejecutar pruebas automatizadas o generar el APK.
