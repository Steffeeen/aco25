plugins {
    kotlin("jvm") version "2.2.21"
}

group = "de.steffeeen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    compilerOptions {
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3)
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}

dependencies {
    // core
    implementation("io.ksmt:ksmt-core:0.5.23")
    // z3 solver
    implementation("io.ksmt:ksmt-z3:0.5.23")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}