/**
 * Created by yechao on 2021/9/9.
 * Describe :
 */
plugins{
    `kotlin-dsl`
}

repositories{
    google()
    mavenCentral()
}

tasks.withType<JavaCompile>{
    options.isFork = true
    options.isIncremental = true
}