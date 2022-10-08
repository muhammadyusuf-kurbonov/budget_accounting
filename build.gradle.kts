import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.squareup.sqldelight").version("1.5.3")
}

group = "uz.qmgroup"
version = "1.0"
val exposedVersion: String by project
val composeDestinationsVersion: String by project

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("com.squareup.sqldelight:sqlite-driver:1.5.3")
                implementation("com.squareup.sqldelight:coroutines-extensions:1.5.3")
            }
        }
        val jvmTest by getting

        sqldelight {
            database("AppDatabase") {
                packageName = "uz.qmgroup.budget_accounting.database"
                sourceFolders = listOf("sqldelight")
                sourceSets {
                    add(jvmMain)
                    add(jvmTest)
                }
            }
        }


    }
}
compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Rpm)
            packageName = "budget_accounting"
            packageVersion = "1.0.0"
        }
    }
}