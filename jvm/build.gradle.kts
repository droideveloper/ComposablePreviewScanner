plugins {
    alias(libs.plugins.plugin.publish)
    kotlin("jvm")
    id("maven-publish")
    alias(libs.plugins.maven.local.publish)
}

java {
    withJavadocJar()
    withSourcesJar()
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
}

libraryContext {
    key = "preview-scanner.jvm"
    name = "jvm"
    description = "Composable Preview Scanner jvm library"
}

dependencies {
    api(project(":core"))
    implementation(libs.classgraph)
    implementation(libs.kotlin.reflect)
}
