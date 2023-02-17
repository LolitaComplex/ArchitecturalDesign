package com.doing.transform

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException

class TransformPlugin implements Plugin<Project> {

    public static final String TAG = "TransformPlugin >>>>>>>> "

    @Override
    void apply(Project project) {
        println("$TAG " + "apply : ${project}")

        if (!project.plugins.hasPlugin("com.android.application")) {
            throw new ProjectConfigurationException("不能应用于 Library 工程")
        }

        AppExtension android = project.extensions.findByName(AppExtension.class)
        android.registerTransform(new TinyTransform(project))
    }
}