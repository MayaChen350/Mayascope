import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.net.URL

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    idea
}

val generatePoems by tasks.registering {
    val outputDir = layout.buildDirectory.dir("generated/static/kotlin")
    outputs.dir(outputDir) // Mark as task output for caching

    fun parseOnePoem(poem: String): List<String> = poem.lines()
        .map { it.trim() }
        .filter {
            it != "" && !it.lowercase().startsWith("title") && !it.lowercase().startsWith("date")
        }
        .map { it.trim('"').trim() }

    doLast {
        val outputFile = outputDir.get().file("poems.kt").asFile
        outputFile.parentFile.mkdirs()
        outputFile.writeText(
            """
            package io.github.mayachen350.mayascope.data
            
            val poemsArrays = arrayOf(${
                run {
                    @Suppress("DEPRECATION") val poemFileContent =
                        URL(
                            "https://raw.githubusercontent.com/MayaChen350/MayaChen350/" +
                                    "refs/heads/main/.extras/poems.txt"
                        ).readText()
                            .split("///")
                    poemFileContent.joinToString { poem ->
                        "arrayOf(${parseOnePoem(poem).joinToString { "\"\"\"$it\"\"\"" }})"
                    }
                }
            })
        """.trimIndent()
        )
    }
}

kotlin {
    android {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }

        namespace = "io.github.mayachen350.mayascope.shared"
        compileSdk = 37
        minSdk = 26

        androidResources.enable = true
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    // For iOS targets, this is also where you should
    // configure native binary output. For more information, see:
    // https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

    // A step-by-step guide on how to include this library in an XCode
    // project can be found here:
    // https://developer.android.com/kotlin/multiplatform/migrate
    val xcfName = "sharedKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    sourceSets {
//        androidUnitTest.dependencies {
//            testImplementation(libs.junit)
//            androidTestImplementation(libs.androidx.junit)
//            androidTestImplementation(libs.androidx.espresso.core)
//            androidTestImplementation(platform(libs.androidx.compose.bom))
//            androidTestImplementation(libs.androidx.ui.test.junit4)
//        }
        androidMain.dependencies {
            implementation(libs.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.ui)
            implementation(libs.androidx.datastore.preferences)
        }
        commonMain {
            kotlin.srcDir(tasks.named(generatePoems.name))

            dependencies {
                implementation(libs.runtime)
                implementation(libs.foundation)
                implementation(libs.material3)
                implementation(libs.ui)
                implementation(libs.components.resources)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
                implementation(libs.kotlinx.datetime)


            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain {
            dependencies {
                implementation("androidx.datastore:datastore:1.2.1")
                implementation("androidx.datastore:datastore-preferences:1.2.1")
                // Add iOS-specific dependencies here. This a source set created by Kotlin Gradle
                // Plugin (KGP) that each specific iOS target (e.g., iosX64) depends on as
                // part of KMP’s default source set hierarchy. Note that this source set depends
                // on common by default and will correctly pull the iOS artifacts of any
                // KMP dependencies declared in commonMain.
            }
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.androidx.uiTooling)
}

idea {
    module {
        // Marks the directory as "Generated" in the IDE project structure
        generatedSourceDirs.add(
            layout.buildDirectory.dir("generated/static/kotlin").get().asFile
        )
    }
}