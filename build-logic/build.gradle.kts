plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("LocalMavenPublish") {
            id = "sergio.sastre.composable.preview.scanner.local.publish"
            implementationClass = "sergio.sastre.composable.preview.scanner.ComposablePreviewPublishPlugin"
        }
    }
}

dependencies {
    compileOnly(libs.shadow.plugin)
}