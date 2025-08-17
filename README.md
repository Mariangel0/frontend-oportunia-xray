# OportunIA – Frontend (Android)

Aplicación móvil Android para apoyar a estudiantes de informática en su preparación laboral: simulación de entrevistas con IA, revisión de currículums y recomendaciones de mejora. Este repo contiene el frontend nativo en Kotlin con Jetpack Compose y arquitectura limpia.

Backend: Kotlin/Spring Boot (API REST).
Infra: AWS (Elastic Beanstalk / S3 / Textract, etc.).

## Tecnologías

-Kotlin · Jetpack Compose · Material 3
-Clean Architecture (data / domain / presentation)
-Hilt para DI
-Retrofit + OkHttp (API REST)
-Kotlinx Coroutines/Flow
-DataStore Preferences (persistencia de token/user)
-Navigation Compose
-Gradle Kotlin DSL

## Características principales

-Autenticación JWT (login y persistencia de sesión con DataStore).
-Perfiles de usuario (Student): edición de datos, foto de perfil.
-Currículum: subida y análisis (modo IA y modo nube).
-Entrevistas simuladas con IA: preguntas y feedback.
-Catálogo de compañías y reviews.
-Rachas (streak) y notificaciones in-app.

##  Arquitectura (Clean)

-domain: entidades puras, use cases, contratos de repositorio.
-data: implementaciones de repositorio (Retrofit/DB), mapeo DTO ⇄ dominio.
-presentation: ViewModel + State (UI State) + pantallas Compose.

##  Requisitos

-Android Studio
-JDK 17
-AGP y Kotlin según build.gradle.kts
-Dispositivo/emulador API 24+ (recomendado 28+)
