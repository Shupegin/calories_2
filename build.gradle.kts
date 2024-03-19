buildscript {

    dependencies {
        classpath ("com.google.gms:google-services:4.4.1")
    }
    repositories {
        mavenCentral()
        maven {
            url = uri("<https://jitpack.io>")
        }
    }
}
plugins {
    id ("com.android.application") version "8.1.1" apply false
    id ("com.android.library") version "8.1.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id ("com.google.dagger.hilt.android") version "2.44" apply false
}