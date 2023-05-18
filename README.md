# TestActin
Proyecto de demostración con la API Movie DB (https://developer.themoviedb.org/reference/intro/getting-started), basado en una arquitectura CLEAN con MVVM.

## Funciones de la aplicación

- Los usuarios pueden ver la lista de las últimas películas.
- Los usuarios pueden hacer clic en cualquier película para ver los detalles de la misma.

## Arquitectura de la aplicación
Basado en la arquitectura Clean y el patrón de repositorio.

## La aplicación incluye los siguientes componentes principales:
- Un servicio de API web.
- Un repositorio que trabaja con el servicio api, proporcionando una interfaz de datos unificada.
- Un ViewModel que proporciona datos específicos para la interfaz de usuario.
- La interfaz de usuario, que muestra una representación visual de los datos en ViewModel.

## Paquetes de aplicaciones
- constants.
- data.
- di.
- ui.
- utils.

## Especificaciones de la aplicación
- SDK mínimo 26
- Java (en la rama maestra) y Kotlin (en la rama kotlin_support)
- Arquitectura MVVM
- Componentes de la arquitectura de Android (LiveData, ViewModel, componente de navegación, Material Design).
- **Retrofit 2** para integración API.
- **Gson** para serialización.
- **Glide** para cargar imágenes.
- **ViewModel** para pasar datos del modelo a las vistas
- **LiveData**
- **Hilt** para inyección de dependencias.
- **Corutinas**
- **Room** para persistencia de datos
- **Navigation**
