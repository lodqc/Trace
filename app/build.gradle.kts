@file:Suppress("UnstableApiUsage")

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("io.github.leavesczy.trace")
}

clickTrace {
    view {
        onClickClass = "github.leavesczy.trace.click.view.ViewClickMonitor"
        onClickMethodName = "isEnabled"
        uncheckViewOnClickAnnotation = "github.leavesczy.trace.click.view.UncheckViewOnClick"
        include = listOf()
        exclude = listOf()
    }
    compose {
        onClickClass = "github.leavesczy.trace.click.compose.ComposeOnClick"
        onClickWhiteList = "notCheck"
    }
}

replaceClassTrace {
    originClass = "android.widget.ImageView"
    targetClass = "github.leavesczy.trace.replace.MonitorImageView"
    include = listOf()
    exclude = listOf(".*\\.IgnoreImageView\$")
}

toastTrace {
    toasterClass = "github.leavesczy.trace.toast.Toaster"
    showToastMethodName = "showToast"
}

android {
    namespace = "github.leavesczy.trace"
    compileSdk = 33
    defaultConfig {
        applicationId = "github.leavesczy.trace"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        create("release") {
            storeFile =
                File(rootDir.absolutePath + File.separator + "doc" + File.separator + "key.jks")
            keyAlias = "leavesCZY"
            keyPassword = "123456"
            storePassword = "123456"
            enableV1Signing = true
            enableV2Signing = true
        }
    }
    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    applicationVariants.all {
        val variant = this
        outputs.all {
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                val simpleDateFormat = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
                simpleDateFormat.timeZone = TimeZone.getTimeZone("Asia/Shanghai")
                val time = simpleDateFormat.format(Calendar.getInstance().time)
                this.outputFileName =
                    "trace_${variant.name}_versionCode_${variant.versionCode}_versionName_${variant.versionName}_${time}.apk"
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    packagingOptions {
        resources {
            excludes += setOf(
                "**/*.md",
                "**/*.version",
                "**/*.properties",
                "**/**/*.properties",
                "META-INF/{AL2.0,LGPL2.1}",
                "META-INF/CHANGES",
                "DebugProbesKt.bin",
                "kotlin-tooling-metadata.json"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    val composeBom = platform("androidx.compose:compose-bom:2023.06.01")
    implementation(composeBom)
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.7.2")
}