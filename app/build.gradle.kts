import java.util.Properties
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    id("kotlin-android")
    id("com.google.devtools.ksp")
}

val keystorePropertiesFile = rootProject.file("./jks/keystore.properties")
val keystoreProperties = Properties().apply {
    load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "com.yechaoa.wanandroid_jetpack"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.yechaoa.wanandroid_jetpack"
        minSdk = 23
        targetSdk = 35
        versionCode = 4
        versionName = "1.3"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        //room数据库 schema文件生成路径
//        javaCompileOptions {
//            annotationProcessorOptions {
//                arguments += [
//                        "room.schemaLocation"  : "$projectDir/schemas".toString(),
//                        "room.incremental"     : "true",
//                        "room.expandProjection": "true"]
//            }
//        }

        // resConfigs("en", "zh", "zh-rCN")
        androidResources {
            // 声明应用支持的语言区域（BCP 47 格式）
            localeFilters.addAll(listOf("en", "zh", "zh-CN"))
        }

        ndk {
            //选择要添加的对应 cpu 类型的 .so 库。
            abiFilters.addAll(mutableSetOf("arm64-v8a", "armeabi-v7a", "x86", "x86_64"))
        }
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    applicationVariants.all {
        outputs.forEach { output ->
            if (output is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                val date = SimpleDateFormat("yyyyMMdd").format(Date())
                output.outputFileName = "app_${name}_v${versionName}_${versionCode}_${date}.apk"
            }
        }
    }

    signingConfigs {
        create("release") {
            if (keystorePropertiesFile.exists()) {
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
            }
        }
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "AUTHOR", "\"yechaoa\"")
            isMinifyEnabled = false
        }
        release {
            buildConfigField("String", "AUTHOR", "\"yechaoa\"")
            signingConfig = signingConfigs.getByName("release")
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    // ========== Kotlin 标准库 ==========
    implementation(libs.kotlin.stdlib)

    // ========== AndroidX 核心库 ==========
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.swiperefreshlayout)

    // ========== 导航组件 ==========
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.lifecycle.extensions)

    // ========== 网络库 ==========
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // ========== 图片加载 ==========
    implementation(libs.glide)

    // ========== 数据库 ==========
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // ========== 测试依赖 ==========
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // ========== 第三方组件 ==========
    implementation(libs.banner)
    implementation(libs.immersionbar)
    implementation(libs.agentweb)
    implementation(libs.yutilskt)
    implementation(libs.brvha)
    implementation(libs.flowlayout)
    implementation(libs.vertical.tablayout)

}