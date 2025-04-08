# <h1 align=center>Uso del Contenedor Docker 🐳 </h1>

## Requisitos Previos
Antes de empezar a usar asegurate de tener instalado **DOCKER** en tu máquina local. 
### ¿Cómo instalar DOCKER 🐋?
• [Windows](https://docs.docker.com/desktop/install/windows-install/) • [Linux](https://docs.docker.com/desktop/install/linux-install/) • [MacOs](https://docs.docker.com/desktop/install/mac-install/) •

## Pasos para el despliegue local
### 1. Navegar al directorio del proyecto
Abre una terminal y dirigite a la carpeta de tu proyecto, donde se encuentre el archivo docker-compose.yml.

### 2. Construir y Levantar los Contenedores
   Ejecutá el siguiente comando para construir y levantar los contenedores:
   ```
   docker compose up
   ```
  Esto iniciará el proceso de construcción de la imagen de la aplicación Spring Boot y arrancará los contenedores de la aplicación y la base de datos (MariaDB en este caso).

### 3. Acceder a la API
  Una vez que los contenedores estén en funcionamiento, es posible acceder a la API en la siguiente dirección `http://localhost:8080` y sus debidas rutas.
  [Mirar la documentacion de la API](https://github.com/disilab-frba-utn-edu-ar/t1-s2-socios-back/tree/main#uso-de-la-aplici%C3%B3n)

### 4. Detener y Limpiar los Contenedores
Para detener los contenedores Docker y eliminarlos cuando hayas terminado de usarlos, simplemente en la misma terminalen la carpeta de tu proyecto, ejecutá:
```
docker-compose down
```

### Uso de la Aplición
[COMPLETAR BACKEND]
