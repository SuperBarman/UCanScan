# UCanScan — Guía de trabajo en equipo

## Estado de la aplicación (base compartida)

- Android Studio, Kotlin, Jetpack Compose y Material 3.
- Navegación de siete destinos y pantallas provisionales.
- Tema azul/amarillo y componentes `AppButton`, `AppTextField`, `AppTopBar`.
- Login de demostración con `LoginViewModel` y validación de campos; **NO** autentica contra base de datos.
- Los modelos y `DatosEjemplo` de esta entrega no contienen persistencia ni API.

## Contrato común de datos (NO cambiar sin avisar al integrador)

| Modelo | Campos principales | Uso |
|---|---|---|
| `Producto` | `id`, `codigoBarras`, `nombre`, `descripcion`, `categoria` | Catálogo único de productos |
| `Sucursal` | `id`, `nombre`, `direccion` | Tiendas o bodegas |
| `StockSucursal` | `productoId`, `sucursalId`, `cantidad`, `stockMinimo` | Existencia por producto **y sucursal** |
| `Movimiento` | `id`, `productoId`, `sucursalId`, `usuarioId`, `tipo`, `cantidad`, `fechaMillis`, `observacion` | Historial de movimientos |
| `Usuario` | `id`, `nombre`, `correo`, `rol`, `sucursalId` | Usuarios y perfil de acceso |
| `RolUsuario` | `OPERADOR`, `ENCARGADO_BODEGA`, `GERENTE` | Tipos de usuario |
| `TipoMovimiento` | `ENTRADA`, `SALIDA`, `CONTEO` | Tipos de operación |

Reglas simples:

1. `Producto` representa el catálogo; **no** guardar `stock` dentro de `Producto`, porque cada sucursal tiene cantidades distintas.
2. `StockSucursal` relaciona un producto con una sucursal. Una combinación producto/sucursal debe tener un solo registro.
3. Cantidades de stock y mínimos nunca deben ser negativas; la validación se implementará en la lógica, no en estas clases.
4. En `Movimiento`, `cantidad` representa una magnitud positiva. `ENTRADA` suma y `SALIDA` resta (si hay stock suficiente). `CONTEO` guarda la cantidad contada para comparar, **no** modifica el stock automáticamente.
5. `fechaMillis` expresa milisegundos desde época Unix; sirve para ordenar eventos. `usuarioId` identifica quién hizo el movimiento.
6. `Usuario` no guarda contraseñas. El inicio de sesión actual no es autenticación real.
7. `DatosEjemplo` es temporal. Los códigos `DEMO-001`, `DEMO-002` y `DEMO-003` no son códigos de barras comerciales reales.
8. `id`, `productoId` y `sucursalId` son `String` en todas las clases; no crear duplicados con `Int`.

## Reparto inicial

### Mario — Integración / login / roles / supervisión
- Mantiene `MainActivity.kt`, `navigation/AppNavHost.kt`, `ui/theme/*`, `ui/components/App*`, Gradle y los modelos comunes.
- Integra las nuevas pantallas y los cambios mediante pull requests.
- Sigue con roles, control/alertas, reportes y, más adelante, persistencia/REST.

### Compañero 1 — Inventario
- Trabajar en rama `feature/inventario`.
- Crear `ui/screens/InventoryScreen.kt`.
- Crear `ui/components/ProductCard.kt` y `viewmodel/InventoryViewModel.kt`.
- Primera entrega: listar `DatosEjemplo.productos`, seleccionar sucursal, mostrar stock de esa sucursal y buscar por nombre/código; indicar stock bajo cuando `cantidad <= stockMinimo`.
- Para simplificar, el composable principal puede tener firma `@Composable fun InventoryScreen(onBack: () -> Unit)`.

### Compañero 2 — Escáner y movimientos
- Trabajar en rama `feature/escaner-movimientos`.
- Crear `ui/screens/ScannerScreen.kt`, `ui/screens/MovementsScreen.kt` y `viewmodel/MovementsViewModel.kt`.
- Primera entrega: pantalla de escáner **simulada** con ingreso manual de código (ej. `DEMO-001`), seleccionar un producto por código, mostrar un formulario de entrada/salida/conteo y validar cantidad positiva. No implementar todavía scanner real sin coordinar dependencias.
- Para simplificar, usar firmas `@Composable fun ScannerScreen(onBack: () -> Unit)` y `@Composable fun MovementsScreen(onBack: () -> Unit)`.
- **Aún no hay persistencia**: una prueba con datos locales sirve como primera etapa. La integración final de movimientos entre pantallas se coordinará después.

## Reglas de colaboración

- Cada persona crea una rama desde la última versión de `main`. Nadie trabaja directamente en `main` salvo el integrador.
- No editar `MainActivity.kt`, `AppNavHost.kt`, `build.gradle.kts`, `libs.versions.toml`, `ui/theme/*`, ni `model/*` sin coordinar con Mario.
- No modificar `SectionScreen.kt`: es un reemplazo temporal. Las nuevas pantallas deben vivir en archivos propios.
- Cada función nueva debe ser comprensible, tener una responsabilidad y pasar `:app:assembleDebug`.
- Antes de entregar, hacer **push de la rama** y abrir un **Pull Request** hacia `main`. Mario revisará e integrará.
- No poner tokens, contraseñas reales ni archivos `local.properties` en GitHub.
- Las pantallas nuevas no aparecerán en el emulador hasta que Mario las conecte en `AppNavHost.kt`. Eso es esperado.

## Pasos para cada compañero (primera vez)

1. Aceptar la invitación de GitHub y clonar `https://github.com/SuperBarman/UCanScan` en Android Studio.
2. En terminal PowerShell del proyecto:

   ```powershell
   git switch main
   git pull origin main
   git switch -c feature/inventario
   ```

   Para el otro compañero, reemplazar `feature/inventario` por `feature/escaner-movimientos`.
3. Implementar solo los archivos asignados.
4. Compilar:

   ```powershell
   .\gradlew.bat :app:assembleDebug
   ```

5. Guardar y subir la rama:

   ```powershell
   git add .
   git commit -m "feat: implementar primera version de mi modulo"
   git push -u origin feature/inventario
   ```

   Cambiar el nombre de rama en el último comando si corresponde.
6. En GitHub, abrir **Compare & pull request** hacia `main`. No fusionarlo sin revisión.

## Próxima etapa de integración

- Conectar `InventoryScreen`, `ScannerScreen` y `MovementsScreen` en `AppNavHost.kt` reemplazando las rutas que hoy muestran `SectionScreen`.
- Implementar repositorio compartido / Room para que un movimiento afecte el stock consultado por las demás pantallas.
- Integrar lectura real con la cámara y la API REST de Spring Boot cuando la UI y los modelos estén estables.
- Incorporar permisos por rol y autenticación real cuando exista la capa de datos.
