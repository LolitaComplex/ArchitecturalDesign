package com.doing.pluginpublish

import org.gradle.api.Project
import org.gradle.api.ProjectEvaluationListener
import org.gradle.api.ProjectState

class PublishHandler implements ProjectEvaluationListener{

    private Project target

    @Override
    void beforeEvaluate(Project project) {
        println("${PublishPlugin.TAG} beforeEvaluate ${project.name}")

        target.apply([plugin: 'maven-publish'])
    }

    //afterEvaluate {
//    publishing {
//        publications {
//            maven(MavenPublication) {
//                artifactId = 'PublishPlugin'
//                groupId = 'com.doing.plugin'
//                version = '0.0.1'
//                from components.java
//            }
//        }
//
//        repositories {
//            maven {
////                url = layout.buildDirectory.dir('repo')
//                url "${projectDir}/repo"
//            }
//        }
//    }
//}

    @Override
    void afterEvaluate(Project project, ProjectState projectState) {
        println("${PublishPlugin.TAG} afterEvaluate ${project.name}")
    }
}

