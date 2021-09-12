package com.yechaoa.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

/**
 * Created by yechao on 2021/9/11.
 * Describe :
 */

class DependenciesPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("------com.yechaoa.plugin.DependenciesPlugin.groovy-------")

        project.afterEvaluate {
            project.android.applicationVariants.all { variant ->
                project.tasks.create(name: "showDependencies${variant.name.capitalize()}", description: "展示并区分所有依赖") {
                    doLast {
                        Configuration configuration
                        try {
                            configuration = project.configurations."${variant.name}CompileClasspath"
                        } catch (Exception e) {
                            println(e.message)
                            configuration = project.configurations."_${variant.name}Compile"
                        }

                        List<String> androidLibs = new ArrayList<>()
                        List<String> otherLibs = new ArrayList<>()

                        configuration.resolvedConfiguration.lenientConfiguration.allModuleDependencies.each {
                            def identifier = it.module.id
                            if (identifier.group.contains("androidx") || identifier.group.contains("com.google")) {
                                androidLibs.add("${identifier.group}:${identifier.name}:${identifier.version}")
                            } else {
                                otherLibs.add("${identifier.group}:${identifier.name}:${identifier.version}")
                            }
                        }

                        println("--------------官方库 start--------------")
                        androidLibs.each {
                            println(it)
                        }
                        println("--------------官方库 end--------------")

                        println("--------------三方库 start--------------")
                        otherLibs.each {
                            println(it)
                        }
                        println("--------------三方库 end--------------")
                    }
                }
            }
        }
    }

}