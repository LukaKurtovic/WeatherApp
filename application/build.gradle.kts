buildscript {
    repositories {
        google()
        mavenCentral()
    }
}
plugins {
    id("com.android.application") version "7.3.1" apply false
    id("com.android.library") version "7.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}
