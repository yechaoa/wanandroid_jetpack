/**
 * Created by yechao on 2021/9/9.
 * Describe : 版本管理、依赖管理 （也可以继续拆分）
 */

object Configs {
    const val applicationId = "com.yechaoa.wanandroid_jetpack"
    const val compile = 30
    const val minSdk = 23
    const val target = 30
    const val versionCode = 2
    const val versionName = "1.1"
}

object Versions {
    const val gradle_version = "7.0.2"
    const val kotlin_version = "1.5.31"

    const val core_version = "1.3.2"
    const val compat_version = "1.2.0"
    const val material_version = "1.2.1"
    const val constraint_version = "2.0.4"
    const val navigationFragment_version = "2.3.2"
    const val navigation_version = "2.3.2"
    const val lifecycle_version = "2.2.0"
    const val room_version = "2.2.5"

    const val junit_version = "4.13"
    const val extJunit_version = "1.1.2"
    const val espresso_version = "3.3.0"

    const val okhttp_version = "4.9.0"
    const val retrofit_version = "2.9.0"
    const val gson_version = "2.9.0"
    const val logging_version = "3.12.0"

    const val banner_version = "2.1.0"
    const val swiperefresh_version = "1.1.0"
    const val immersionbar_version = "3.0.0"
    const val agentweb_version = "4.1.4"
    const val yutilskt_version = "3.2.0"
    const val brvha_version = "3.0.6"
    const val glide_version = "4.11.0"
    const val flowLayout_version = "1.1.2"
    const val vertical_version = "1.2.9"
}

object Deps {
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"

    const val coreKtx = "androidx.core:core-ktx:${Versions.core_version}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.compat_version}"
    const val material = "com.google.android.material:material:${Versions.material_version}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_version}"
    const val navigationFragment = "androidx.navigation:navigation-fragment:${Versions.navigationFragment_version}"
    const val navigationUi = "androidx.navigation:navigation-ui:${Versions.navigation_version}"
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle_version}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragment_version}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation_version}"

    const val junit = "junit:junit:${Versions.junit_version}"
    const val junitExt = "androidx.test.ext:junit:${Versions.extJunit_version}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso_version}"

    /*okhttp、retrofit*/
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp_version}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.gson_version}"
    const val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.logging_version}"

    /*banner*/
    const val banner = "com.youth.banner:banner:${Versions.banner_version}"

    /*swiperefreshlayout*/
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefresh_version}"

    /*immersionbar*/
    const val immersionbar = "com.gyf.immersionbar:immersionbar:${Versions.immersionbar_version}"

    /*agentWeb*/
    const val agentweb = "com.just.agentweb:agentweb-androidx:${Versions.agentweb_version}"

    /*YUtils*/
    const val yutilskt = "com.github.yechaoa.YUtils:yutilskt:${Versions.yutilskt_version}"

    /*BRVAH*/
    const val brvha = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.brvha_version}"

    /*glide*/
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide_version}"

    /*FlowLayout*/
    const val flowlayout = "com.hyman:flowlayout-lib:${Versions.flowLayout_version}"

    /*VerticalTabLayout*/
    const val VerticalTabLayout = "q.rorbin:VerticalTabLayout:${Versions.vertical_version}"

    /*room*/
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room_version}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room_version}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room_version}"
    const val roomTesting = "androidx.room:room-testing:${Versions.room_version}"
}
