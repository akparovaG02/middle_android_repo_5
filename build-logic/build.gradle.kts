plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.android.tools.build:gradle:8.5.0")
    implementation(kotlin("gradle-plugin", "2.0.0"))
}

gradlePlugin {
    plugins {
        register("featureAndroidLibrary") {
            id = "feature.android.library"
            implementationClass = "FeatureAndroidLibraryConventionPlugin"
        }
    }
}