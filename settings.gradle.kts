pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        // 阿里云仓库
        maven("https://maven.aliyun.com/repository/public/")
        mavenLocal()
        flatDir {
            dirs("libs")
        }
    }
}

include(":app")
rootProject.name = "wanandroid_jetpack"