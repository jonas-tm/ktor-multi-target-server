# ktor-multi-target-server

This repository contains a _proof-of-concept_ for developing 
a Ktor server in with Kotlin Multiplatform targeting **Kotlin/Native** and **Kotlin/JVM**.

The core server logic including routes and plugins lays in `commonMain` module.
The platform specific _Ktor Engine_, _Configuration_ and _SQLDelight Drivers_ are implemented 
in the respective target modules `commonNative` and `commonMain`.

#### Features
- Ktor Plugins
- Different engines per target (Netty -> JVM, CIO -> Native)
- SQLDelight SQLite Persistence 

### Run/Debug
- Config path to SQLite database in `com.jonastm.EnvironmentImpl.kt` per target module
- Kotlin/JVM `gradle :run` 
  - (not working for some unknown reason -> start via IntelliJ main func)
- Kotlin/Native
  - Debug: `gradle :runDebugExecutableNative`
  - Run: `gradle :runReleaseExecutableNative`

## Tests
Tests are implemented in _commonTest_ and can be run for both platforms.
- Kotlin/JVM via `gradle :jvmTest`
- Kotlin/Native via `gradle :nativeTest`
- All targets via `gradle :allTests`
