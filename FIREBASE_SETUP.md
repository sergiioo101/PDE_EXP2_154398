# Configuración de Firebase

Este proyecto utiliza Firebase Authentication para la autenticación de usuarios.

## Pasos para configurar Firebase

### 1. Crear proyecto en Firebase Console

1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Haz clic en "Agregar proyecto" o selecciona un proyecto existente
3. Sigue las instrucciones para crear/configurar el proyecto

### 2. Agregar aplicación Android

1. En el panel de Firebase, haz clic en el ícono de Android
2. Ingresa el **Package name**: `com.example.pde_exp2_154398`
3. (Opcional) Ingresa un **App nickname** y **Debug signing certificate SHA-1**
4. Haz clic en "Registrar app"

### 3. Descargar google-services.json

1. Descarga el archivo `google-services.json`
2. **IMPORTANTE**: Coloca el archivo en la carpeta `app/` (no en `app/src/main/`)
   - La ruta correcta es: `app/google-services.json`

### 4. Configurar política de contraseña en Firebase

1. En Firebase Console, ve a **Authentication** → **Settings** → **Password policy**
2. Configura la política de contraseña:
   - **Mínimo de caracteres**: 8
   - **Requisitos**: 
     - ✅ Mayúsculas
     - ✅ Minúsculas
     - ✅ Números
   - (Opcional) Símbolos

### 5. Habilitar Authentication con Email/Password

1. En Firebase Console, ve a **Authentication** → **Sign-in method**
2. Haz clic en **Email/Password**
3. Habilita el primer toggle (Email/Password)
4. Haz clic en **Guardar**

### 6. Sincronizar proyecto

1. En Android Studio, haz clic en **File** → **Sync Project with Gradle Files**
2. Espera a que termine la sincronización

## Verificación

Una vez configurado, deberías poder:
- Compilar el proyecto sin errores
- Registrar nuevos usuarios con email/contraseña
- Iniciar sesión con usuarios registrados

## Notas importantes

- El archivo `google-services.json` contiene información sensible y está en `.gitignore`
- No compartas este archivo públicamente
- Cada desarrollador debe descargar su propio `google-services.json` desde Firebase Console

## Estructura esperada

```
app/
├── google-services.json  ← Coloca el archivo aquí
├── build.gradle.kts
└── src/
    └── main/
        └── ...
```

## Solución de problemas

### Error: "File google-services.json is missing"
- Verifica que el archivo esté en `app/google-services.json` (no en `app/src/main/`)
- Sincroniza el proyecto con Gradle

### Error: "Package name mismatch"
- Verifica que el package name en `google-services.json` coincida con `com.example.pde_exp2_154398`
- Verifica que el `applicationId` en `app/build.gradle.kts` sea correcto

### Error de autenticación
- Verifica que Email/Password esté habilitado en Firebase Console
- Verifica que la política de contraseña esté configurada correctamente

