package com.doing.plugin.temp

import org.gradle.api.Plugin
import org.gradle.api.Project

class TempPublishPlugin implements Plugin<Project> {

    public static final String TAG = "TempPublishPlugin >>>>>>> "

    @Override
    void apply(Project target) {
        println("${TAG} apply project: ${target}")
        target.gradle.addProjectEvaluationListener(new PluginHandler(target: target))
    }
}