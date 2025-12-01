// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val agpVersion = "8.9.1"
    val kotlinVersion = "2.2.21"
    val kspVersion = "2.2.21-2.0.4" // The correct KSP version

    id("com.android.application") version agpVersion apply false
    id("com.google.devtools.ksp") version kspVersion apply false
    id("org.jetbrains.kotlin.android") version kotlinVersion apply false

}