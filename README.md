# omega_project

Este proyecto corresponde a una práctica de SpringBoot; simula la gestión bancaria de clientes, cuentas y movimientos de cuenta


#(1) ESTRUCTURA DEL PROYECTO
1. Carpeta reto_técnico 		-> Indicaciones técnicas para el proyecto
2. Carpeta postman_colecciones 	-> Archivos postman con las colecciones para la validación de los endpoints
3. Carpeta omega 				-> Carpeta principal del proyecto completo incluye DB, omega-clientes y omega-cuentas


#(2) EJECUCIÓN DEL PROYECTO
1. Limpiar, Construir y Generar omega-clientes.jar
2. Limpiar, Construir y Generar omega-cuentas.jar
3. En la carpeta del proyecto 'GitHub\omega_project\omega', ejecutar la funcionalidad de docker-compose mediante:
	-> docker-compose build 
	-> docker-compose up -d
4. La base de datos y los proyectos se cargarán automáticamente sin intervención del tester
5. Verificar en el navegador, el correcto funcionamiento de los endpoints:
	-> http://localhost:8080/api/clientes
	-> http://localhost:8081/api/cuentas
	-> http://localhost:8081/api/movimientos
	-> http://localhost:8081/reportes/
6. Importar los '4' archivos json de Postman, probar la funcionalidad de los endPoints
	

