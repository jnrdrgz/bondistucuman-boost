# Bondis Tucumán Boost
La premisa es simple, la [app que permite ver donde están los colectivos en Tucumán](http://www.tucuman.miredbus.com.ar/) carece de las siguientes funcionalidades básicas:
- Mostrar donde están todos bondis de una misma línea (la app solo permite ver un ramal determinado en el mapa).
- Marcar dos puntos en el mapa y mostrar todos los bondis que me lleven del punto A al punto B.
- Elegir arbitrariamente que líneas quiero ver en el mapa.


Estas son las features que decidí implementar en esta app. En el menu, la primera con el nombre de Por Línea, la segunda se llama Punto a Punto, y la tercera y todavía no implementada, Personalizado.
Desgraciadamente, las llamadas a la API de  [RedBus](http://www.tucuman.miredbus.com.ar/), no están disponibles para el uso fuera de la misma página de RedBus (como debería ser, ya que la brindan la ubicación de un servicio público), así que esta app funciona con mi ["API"](https://github.com/jnrdrgz/my-apis) , la cual no creo aguante un ambiente de producción.
La app continúa todavía en desarrollo, por lo tanto, se pueden encontrar varios errores.