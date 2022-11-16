val ktor_version = "2.1.3"
val kotlin_version = "1.7.21" // When updating also update kotlin plugins version
val psql_driver_version = "0.0.4"
val kotlin_coroutine = "1.6.4"
val kotlinx_serialization_version = "1.4.0"
val sqldelight_version = "1.5.3" // 2.0.0-alpha04
val sqldelight_group = "com.squareup.sqldelight" // app.cash.sqldelight
val logback_version =  "1.4.1"
val datetime_version =  "0.4.0"


plugins {
    kotlin("multiplatform") version "1.7.21"
    kotlin("plugin.serialization") version "1.7.21"
    application
    id("com.squareup.sqldelight") version "1.5.3"
}

group = "com.jonastm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sqldelight {
    database("DB") {
        //dialect("postgresql")
        packageName = "com.jonastm.orm.sqldelight"
        deriveSchemaFromMigrations = true
//        dialect("app.cash.sqldelight:dialect-postgresql:$sqldelight_version")
    }
//    linkSqlite = false
}

application {
    mainClass.set("com.jonastm.MainKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

kotlin {
    jvm {
//        withJava()
    }

    val hostOs = System.getProperty("os.name")
    val arm: Boolean = System.getProperty("os.arch") == "aarch64"
    val native = "native"
    val nativeTarget = when {
        hostOs == "Mac OS X" -> if (arm) macosArm64(native) else macosArm64(native)
        hostOs == "Linux" -> if (arm) linuxArm64(native) else linuxX64(native)
        else -> throw GradleException("Host OS is not supported in Kotlin/Native + Ktor.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "com.jonastm.starter"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            // SQLDelight ORM will be generated here
            dependencies {
                implementation("io.ktor:ktor-server-core:$ktor_version")

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetime_version")

                implementation("com.squareup.sqldelight:coroutines-extensions:1.5.3")

                implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutine")

                // Fix to sync kotlinx serialization version
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinx_serialization_version") {
                    version { strictly(kotlinx_serialization_version) }
                }
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinx_serialization_version") {
                    version { strictly(kotlinx_serialization_version) }
                }

                implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
                implementation("io.ktor:ktor-server-status-pages:$ktor_version")
                implementation("io.ktor:ktor-server-call-id:$ktor_version")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("io.ktor:ktor-server-test-host:$ktor_version")
                implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
                implementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-netty:$ktor_version")
                implementation("$sqldelight_group:sqlite-driver:$sqldelight_version")
                implementation("ch.qos.logback:logback-classic:$logback_version")
            }
        }
        val nativeMain by getting {
            dependencies {
                implementation("$sqldelight_group:native-driver:$sqldelight_version")
                implementation("io.ktor:ktor-server-cio:$ktor_version")
            }
        }
        val nativeTest by getting {
            dependencies {

            }
        }
    }
}
