package sergio.sastre.composable.preview.scanner

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

open class ComposablePreviewScannerExtension @Inject constructor(
    objectFactory: ObjectFactory,
) {

    val key: Property<String> = objectFactory.property<String>().also {
        it.convention("")
    }

    val name: Property<String> = objectFactory.property<String>().also {
        it.convention("")
    }

    val description: Property<String> = objectFactory.property<String>().also {
        it.convention("")
    }
}