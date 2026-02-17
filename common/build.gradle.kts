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
    key = "preview-scanner.common"
    name = "common"
    description = "Composable Preview Scanner common library"
}

dependencies {
    api(project(":core"))
    implementation(libs.classgraph)
    implementation(libs.kotlin.reflect)
}
