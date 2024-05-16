plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("com.google.gms.google-services")
    id ("dagger.hilt.android.plugin")
    id("com.google.firebase.crashlytics")
}


android {
    namespace = "cal.calor.caloriecounter"
    compileSdk = 34

    defaultConfig {
        applicationId = "cal.calor.caloriecounter"
        minSdk = 26
        targetSdk = 34
        versionCode = 46
        versionName = "2.43"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        kapt {
            arguments{
                arg("room.schemaLocation","$projectDir/schemas")
            }}

    }




    buildTypes {
        release {
            manifestPlaceholders["crashlyticsCollectionEnabled"] = "true"
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            manifestPlaceholders["crashlyticsCollectionEnabled"] = "false"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_9
        targetCompatibility = JavaVersion.VERSION_1_9
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose  = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation ("co.yml:ycharts:2.1.0")
    implementation ("org.codehaus.groovy:groovy-all:2.4.12")
    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.activity:activity-compose:1.7.1")
    //noinspection GradleDependency
    implementation ("androidx.compose.ui:ui:1.5.4")
    //noinspection GradleDependency
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.4")
    implementation ("androidx.compose.material:material:1.4.3")
//    implementation ("com.google.firebase:firebase-auth-ktx:22.0.0")

    implementation ("com.google.firebase:firebase-database:20.2.2")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("com.google.mlkit:translate:17.0.1")
    implementation("com.google.firebase:firebase-analytics:21.0.0")
    implementation("androidx.compose.material3:material3-android:1.2.0")
    implementation("com.google.firebase:firebase-crashlytics:19.0.0")

    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    //noinspection GradleDependency
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.5.4")
    //noinspection GradleDependency
    debugImplementation ("androidx.compose.ui:ui-tooling:1.5.4")
    //noinspection GradleDependency
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.5.4")
    implementation ("androidx.navigation:navigation-compose:2.7.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation ("androidx.navigation:navigation-compose:2.7.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))

    implementation ("androidx.compose.runtime:runtime-livedata:1.6.0-alpha06")
    //ROOM
    implementation ("androidx.room:room-runtime:2.5.2")
    kapt ("androidx.room:room-compiler:2.5.1")
    implementation ("androidx.room:room-ktx:2.5.1")
    implementation ("androidx.room:room-paging:2.5.1")

// Paging 3.0
    implementation ("androidx.paging:paging-compose:1.0.0-alpha19")


    implementation("io.coil-kt:coil-compose:2.3.0")
    implementation ("com.github.bumptech.glide:compose:1.0.0-alpha.1")

    implementation ("com.google.dagger:hilt-android:2.44")
    kapt ("com.google.dagger:hilt-android-compiler:2.44")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation ("com.google.zxing:core:3.4.1")

    implementation ("com.google.android.play:app-update:2.1.0")
    implementation ("com.google.android.play:app-update-ktx:2.1.0")
    implementation ("com.exyte:animated-navigation-bar:1.0.0")
    implementation ("com.google.accompanist:accompanist-navigation-animation:0.20.0")
    implementation("androidx.compose.material:material-icons-extended-android:1.5.4")
    implementation("androidx.compose.ui:ui-text-google-fonts:1.5.4")


    //noinspection MobileAdsSdkOutdatedVersion
    implementation("com.yandex.android:mobileads:7.0.0")

    implementation("io.github.vanpra.compose-material-dialogs:datetime:0.8.1-rc")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    implementation("com.google.accompanist:accompanist-swiperefresh:0.27.0")



}