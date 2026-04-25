import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class FeatureAndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.kotlin.android")

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        val compileSdkVersion = libs.findVersion("compileSdk").get().toString().toInt()
        val minSdkVersion = libs.findVersion("minSdk").get().toString().toInt()

        extensions.configure<LibraryExtension> {
            compileSdk = compileSdkVersion

            defaultConfig {
                minSdk = minSdkVersion
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
            }

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }

        dependencies {
            add("implementation", "androidx.core:core-ktx:1.13.1")
            add("implementation", "androidx.appcompat:appcompat:1.7.0")
            add("implementation", "com.google.android.material:material:1.12.0")

            add("testImplementation", "junit:junit:4.13.2")
            add("androidTestImplementation", "androidx.test.ext:junit:1.2.1")
            add("androidTestImplementation", "androidx.test.espresso:espresso-core:3.6.1")
        }
    }
}