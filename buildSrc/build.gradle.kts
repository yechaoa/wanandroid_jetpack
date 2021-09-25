/**
 * Created by yechao on 2021/9/9.
 * Describe :
 */

plugins {
//    groovy
    `kotlin-dsl`
}

repositories {
    maven(url = "https://maven.aliyun.com/repository/public")
    google()
    mavenCentral()
}

dependencies {
//    implementation(gradleApi())
//    implementation(localGroovy())
//    implementation("com.android.tools.build:gradle:4.0.2")
}

tasks.withType<JavaCompile> {
    options.isFork = true
    options.isIncremental = true
}