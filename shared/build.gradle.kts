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
    androidLibrary {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }

        namespace = "io.github.mayachen350.mayascope.shared"
        compileSdk = 36
        minSdk = 26

        androidResources.enable = true
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
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