@ECHO OFF
TITLE Server run
cd D:\Librerias\Trabajos\Programacion\TofuApp\api_tofu_app
call mvn clean install

call mvn spring-boot:run