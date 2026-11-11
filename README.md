UCanScan
Escanea, registra y controla.
Descripción
UCanScan es una aplicación móvil para Android orientada al control de inventario en múltiples tiendas y bodegas. Permite consultar existencias por sucursal, identificar productos mediante códigos de barras, registrar movimientos y revisar alertas y reportes de stock.

El proyecto fue desarrollado en Kotlin con Jetpack Compose como parte de la asignatura Desarrollo de Aplicaciones Móviles (DSY1105). Actualmente es un prototipo funcional con persistencia local mediante Room; algunas funciones, como el inicio de sesión, utilizan datos de demostración.
Funcionalidades implementadas
- Inicio de sesión de demostración: formulario con validaciones básicas. No utiliza autenticación contra un servidor.
- Perfiles y navegación por roles: OPERADOR, ENCARGADO_BODEGA y GERENTE, con restricciones de acceso a determinadas pantallas. La selección de roles es de demostración.
- Inventario por sucursal: consulta, búsqueda de productos y visualización de unidades disponibles y stock mínimo.
- Escáner de códigos de barras: lectura mediante la cámara y Google Code Scanner, con alternativa de ingreso manual.
- Movimientos de inventario: registro de entradas, salidas y conteos físicos, con validaciones de cantidad y stock disponible.
- Persistencia local: almacenamiento de existencias y movimientos con Room sobre SQLite. Las actualizaciones de stock y el registro de movimientos se realizan mediante transacciones.
- Historial de movimientos: consulta de movimientos registrados y de los cambios de stock asociados.
- Alertas y control: identificación de productos con stock bajo o agotado y diferencias detectadas en conteos.
- Reportes: resúmenes de inventario y existencias por sucursal.
- Evidencia fotográfica de conteos: selección de una imagen mediante Android Photo Picker y vista previa en pantalla.
- Animaciones: transiciones de aparición y desaparición con AnimatedVisibility y animaciones de elementos en listas.
Tecnologías utilizadas
Tecnología	Uso en UCanScan
Kotlin	Lenguaje de programación
Jetpack Compose	Construcción de interfaces declarativas
Material Design 3	Componentes y estilos de interfaz
Navigation Compose	Navegación entre pantallas
MVVM	Separación de interfaz, estado y lógica
ViewModel y Flow/StateFlow	Gestión de estado y actualización de la interfaz
Room / SQLite	Persistencia local de stock y movimientos
Coroutines	Operaciones asíncronas
Google Code Scanner	Lectura de códigos de barras con la cámara
Android Photo Picker	Selección de fotografías del dispositivo
Git y GitHub	Control de versiones e integración del trabajo


Estructura general del proyecto
app/src/main/java/com/ucanscan/app/
├── data/
│   └── local/         # Base de datos Room, entidades, DAO y migraciones
├── model/             # Modelos y tipos de datos
├── navigation/        # Rutas y permisos según rol
├── repository/        # Acceso y coordinación de datos
├── ui/
│   ├── components/    # Componentes Compose reutilizables
│   ├── screens/       # Pantallas de la aplicación
│   └── theme/         # Estilos y tema Material 3
└── viewmodel/         # Estado y lógica para la interfaz
El flujo general de datos utiliza Screen → ViewModel → Repository → DAO → Room. Para las consultas observables, Room puede emitir cambios mediante Flow, que se reflejan en la interfaz Compose.
Cómo ejecutar el proyecto
1. Instala Android Studio y configura el SDK de Android.
2. Clona la rama principal (main):
   git clone https://github.com/SuperBarman/UCanScan.git
3. Abre la carpeta UCanScan en Android Studio.
4. Ejecuta Sync Project with Gradle Files y espera a que se descarguen las dependencias.
5. Selecciona un emulador Android o un teléfono físico compatible.
6. Ejecuta la configuración app con el botón Run.
Para probar el escáner, se recomienda un dispositivo o emulador compatible con los servicios de Google Play. La galería puede probarse seleccionando una foto ya disponible en el dispositivo.

Datos y limitaciones actuales
- El inicio de sesión es de demostración, no un sistema de autenticación real.
- El catálogo inicial contiene productos y sucursales de ejemplo; no existe todavía un CRUD completo de administración de productos y tiendas.
- Los roles y permisos corresponden al prototipo y no están validados por un servidor.
- El stock y los movimientos sí se almacenan localmente con Room.
- La fotografía de evidencia solo se selecciona y visualiza: aún no se guarda permanentemente junto al movimiento.
- Spring Boot y una API REST no están integrados en la versión actual. La sincronización con un backend queda como mejora futura.
Pruebas
El proyecto incluye una prueba instrumentada de persistencia de stock con Room. También se han probado los flujos principales de inventario, movimientos, escaneo y navegación durante el desarrollo.
Equipo
Proyecto desarrollado por tres integrantes para la asignatura DSY1105:
- Integrante 1: MARIO CONTRERAS
- Integrante 2: ANGELO MORA
- Integrante 3: LUKAS RIQUELME

Estado del proyecto
Prototipo Android funcional, integrado en la rama principal main.

La versión principal del proyecto se encuentra en main. La integración de funcionalidades desde integration/ucanscan se realizó mediante el commit de merge 57a3e4f.
Organización y control de versiones
El desarrollo se organizó mediante ramas de Git por funcionalidad y una rama de integración, con commits descriptivos para registrar los cambios. Las tareas desarrolladas en paralelo se incorporaron a integration/ucanscan para probar el conjunto de la aplicación. Posteriormente, la rama integration/ucanscan se fusionó con main, que contiene la versión integrada. La rama de integración se conserva para mantener el historial del trabajo.
