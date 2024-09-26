# Usa una imagen base oficial de OpenJDK 17
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de la aplicación desde el host al contenedor
COPY target/donbosco-0.0.1-SNAPSHOT.jar.original /app/donbosco.jar

# Establece una variable de entorno para el nombre de la aplicación
ENV SPRING_APPLICATION_NAME=donbosco

# Expone el puerto en el que correrá la aplicación (debe coincidir con tu configuración)
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/donbosco.jar"]