package sergio.sastre.composable.preview.scanner

import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.attributes
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType

open class ComposablePreviewPublishPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(ShadowPlugin::class)
            apply(MavenLocalPublishingPlugin::class)
        }

        val libraryContext = extensions.create<ComposablePreviewScannerExtension>("libraryContext")

        val projectGroupId = "sergio.sastre.composable.preview.scanner"
        project.version = "0.8.2"

        extensions.getByType<LocalPublicationExtension>().apply {
            artifactKey.set(libraryContext.key)
            metadata {
                name.set(libraryContext.name)
                groupId.set(projectGroupId)
                description.set(libraryContext.description)
                url.set("https://github.com/sergio-sastre/ComposablePreviewScanner")
            }
            license {
                name.set("The Apache Software License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("repo")
            }
            developer {
                id.set("https://github.com/sergio-sastre")
                name.set("sergio-sastre")
                organization.set("sergio-sastre")
                organizationUrl.set("https://github.com/sergio-sastre/ComposablePreviewScanner")
            }
            scm {
                connection.set("scm:git:git@github.com:sergio-sastre/ComposablePreviewScanner.git")
                developerConnection.set("scm:git:git@github.com:sergio-sastre/ComposablePreviewScanner.git")
                url.set("https://github.com/sergio-sastre/ComposablePreviewScanner")
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
            add("runtimeElements", shadowJarTask)
            add("apiElements", shadowJarTask)
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

        dependencies {}
    }
}