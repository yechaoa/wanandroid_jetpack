/**
 * Created by yechao on 2021/9/9.
 * Describe :
 */

plugins{
//    groovy
    `kotlin-dsl`
}

repositories{
    google()
    mavenCentral()
}

dependencies{
//    implementation(gradleApi())
//    implementation(localGroovy())
//    implementation("com.android.tools.build:gradle:4.0.2")
}

tasks.withType<JavaCompile>{
    options.isFork = true
    options.isIncremental = true
}