import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    alias(libs.plugins.plugin.publish)
    kotlin("jvm")
    id("maven-publish")
    alias(libs.plugins.shadow)
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

val projectGroupId = "sergio.sastre.composable.preview.scanner"
project.version = "0.8.2"

dependencies {
    api(project(":core"))
    implementation(libs.kotlin.reflect)
    implementation (libs.classgraph)
}

localPublication {
    artifactKey = "preview-scanner.android"
    metadata {
        name = "android"
        groupId = projectGroupId
        description = "Composable Preview Scanner android library"
        url = "https://github.com/sergio-sastre/ComposablePreviewScanner"
    }
    license {
        name = "The Apache Software License, Version 2.0"
        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
        distribution = "repo"
    }
    developer {
        id = "https://github.com/sergio-sastre"
        name = "sergio-sastre"
        organization = "sergio-sastre"
        organizationUrl = "https://github.com/sergio-sastre/ComposablePreviewScanner"
    }
    scm {
        connection = "scm:git:git@github.com:sergio-sastre/ComposablePreviewScanner.git"
        developerConnection = "scm:git:git@github.com:sergio-sastre/ComposablePreviewScanner.git"
        url = "https://github.com/sergio-sastre/ComposablePreviewScanner"
    }
}

val shadowJarTask = tasks.named<ShadowJar>("shadowJar") {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Implementation-Vendor" to project.group,
            "Implementation-Vendor-Id" to project.group
        )
    }
    relocate("io.github.classgraph", "$projectGroupId.shadow.io.github.classgraph")
    relocate("kotlin.reflect", "kotlin.reflect")
    archiveClassifier.set(null as String?)
}

artifacts {
    runtimeElements(shadowJarTask)
    apiElements(shadowJarTask)
}

tasks.withType<Test> {
    dependsOn("publishToMavenLocal")
}

tasks.whenTaskAdded {
    if (name == "publishPluginJar" || name == "generateMetadataFileForPluginMavenPublication") {
        dependsOn(tasks.named("shadowJar"))
    }
}

tasks.named("check") {
    dependsOn(tasks.named("assemble"))
    dependsOn(tasks.named("collectRepository"))
}
