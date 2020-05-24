# Easy Weather
Aplicación Android nativa escrita en Java, la cual se encarga de utilizar el servicio de <https://openweathermap.org/> para 
consultar el clima de diferentes ciudades insertadas por el usuario.

Usa un RecyclerView para mostrar las ciudades que han sido cargadas por el usuario, dentro del cual cada elemento es un CardView
que incluye diferentes parámetros como la temperatura, presión, una imagen ilustrativa, entre otros..

Las consultas se realizan a través de una subclase de AsyncTask, la cual se encarga de recibir una serie de archivos JSON (uno por 
cada ciudad) a través de URLs armadas con el nombre de las ciudades, idioma (obtenido desde el sistema android sobre el cual está
corriendo la aplicación), sistema métrico, y otros parámetros.

El parseo de archivos JSON se hace a través de objetos JSONObject y JSONArray según la información que se necesite extraer para
luego almacenarla en un objeto "Weather", el cual encapsula el clima de una ciudad. Posteriormente se ingresa en una colección
de estos objetos la cual es una instancia única del DataSet de la lista principal, la cual actualiza las vistas al mínimo cambio.

El agregado de ciudades por parte del usuario se hace a través de una segunda actividad la cual se encarga de recibir datos a
través de teclado para luego agregarla a una base de datos que se encarga de persistir las ciudades ingresadas, para luego
consultar el clima y actualizar el DataSet.

El APK de la aplicación se encuentra en:
<https://github.com/Brahim006/easy-weather/tree/master/app/build/outputs/apk/debug>
