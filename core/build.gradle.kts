plugins {
    alias(libs.plugins.plugin.publish)
    kotlin("jvm")
    alias(libs.plugins.jetbrains.kotlin.plugin.compose)
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
    key = "preview-scanner.core"
    name = "core"
    description = "Composable Preview Scanner core library"
}

dependencies {
    compileOnly(libs.androidx.compose.runtime)
    implementation(libs.kotlin.reflect)
    implementation(libs.classgraph)
}
