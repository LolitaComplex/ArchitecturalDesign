package com.doing.pluginpublish


import org.gradle.api.Plugin
import org.gradle.api.Project

class PublishPlugin implements Plugin<Project> {

    public static final String TAG = "PublishPlugin >>>>>>> "

    @Override
    void apply(Project target) {
//        target.gradle.addProjectEvaluationListener(new PublishHandler(target: target))


        println("$TAG apply ${target.name}")
    }
}