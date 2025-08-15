
# Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
# Click nbfs://nbhost/SystemFileSystem/Templates/Other/Dockerfile to edit this template

# omega-cuentas/Dockerfile
FROM eclipse-temurin:22-jdk-alpine
WORKDIR /app
COPY target/omega-cuentas.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
