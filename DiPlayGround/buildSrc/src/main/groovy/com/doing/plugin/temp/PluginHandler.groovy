package com.doing.plugin.temp

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.ProjectEvaluationListener
import org.gradle.api.ProjectState
import org.gradle.api.Task
import org.gradle.api.XmlProvider
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ExternalDependency
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.publish.PublicationContainer
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenArtifact
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.PublishToMavenLocal
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository


class PluginHandler implements ProjectEvaluationListener {

    Project target

    @Override
    void beforeEvaluate(Project project) {
        println("${TempPublishPlugin.TAG} beforeEvaluate: ${project}")
    }

    @Override
    void afterEvaluate(Project project, ProjectState projectState) {
        target.apply plugin: 'maven-publish'


//        AndroidExtension androidExtension = target.extensions.android

//        println("${TempPublishPlugin.TAG} Configurations Compile: ${target.configurations.getByName("compile")}")
//        println("${TempPublishPlugin.TAG} Configurations API: ${target.configurations.getByName("api")}")
        println("${TempPublishPlugin.TAG} Configurations Implementation: ${target.configurations.getByName("implementation")}")
//        println("${TempPublishPlugin.TAG} Configurations runtimeOnly: ${target.configurations.getByName("runtimeOnly")}")
        println("${TempPublishPlugin.TAG} afterEvaluate: ${target}")
        println("${TempPublishPlugin.TAG} apply project: ${target.extensions.publishing.getClass()}")

        // 1. publishing 怎么获取到的？
        PublishingExtension pubExt = target.extensions.publishing
        pubExt.publications { PublicationContainer publications ->
            println("${TempPublishPlugin.TAG} publications: ${publications}")
            println("${TempPublishPlugin.TAG} this: ${this.getClass()}")
            println("${TempPublishPlugin.TAG} owner: ${owner.getClass()}")
            println("${TempPublishPlugin.TAG} delegate: ${delegate.getClass()}")

            // 2. maven(MavenPublication)这个代码怎么想到是下边这个，根据 task name (type: Class)
            publications.create("maven", MavenPublication.class) { MavenPublication publication ->
                // from(target.getComponents())
                publication.artifactId = 'TestPublish'
                publication.groupId = 'com.doing.plugin.test'
                publication.version = '0.0.1'
                publication.artifact(jarPath) { MavenArtifact artifact ->
                    artifact.setClassifier("interface")
                }
                // 3. project.extensions.getByType(d.class) 怎么就知道Android下有个Extension了
                publication.artifact("aarPath")
                // 4. publication 相关API哪里去查
                // CompileOnly runtimeOnly
                publication.pom.withXml { XmlProvider provider ->
                    Map map = [compile: "compile", api: "compile",
                            implementation: "runtime", runtimeOnly: "runtime"]
                    Node node = provider.asNode().appendNode("dependencies")
                    //  5. project.configurations 这里面存放的是什么
                    map.keySet().stream().map { it -> project.configurations.getByName(it)}
                        .flatMap { Configuration configuration ->
                            // 6. 为啥之上的配置属性都是从Extension里面找，唯独这个dependencies是configuration
                            configuration.dependencies.stream()
                                // 7. ExternalModuleDependency又是啥类型，哪来的？
                                .filter { it instanceof ExternalModuleDependency }
                                .map {new ConfigDependency(configuration.name, it) }
                        }.forEach { ConfigDependency config ->
                            ExternalModuleDependency externalModuleDependency = config.dependency
                            Node dependencyNode = node.appendNode("dependency")
                            dependencyNode.appendNode('groupId', externalModuleDependency.group)
                            dependencyNode.appendNode('artifactId', externalModuleDependency.name)
                            dependencyNode.appendNode('version', externalModuleDependency.version)
                            // 8. Scope对应关系哪儿来的， runtimeOnly 有啥用
                            dependencyNode.appendNode('scope', map[config.dependencyType])
                            if (externalModuleDependency.artifacts != null && externalModuleDependency.artifacts.size() > 0) {
                                String extension = externalModuleDependency.artifacts.first().getExtension()
                                // 9. type是啥 artifacts是啥
                                dependencyNode.appendNode('type', extension)
                            }

                            if (externalModuleDependency.excludeRules != null && externalModuleDependency.excludeRules.size() > 0) {
                                // 10. exclusions 语法又是哪来的 这就是全部Maven Pom语法了吗
                                def exclusionsNode = dependencyNode.appendNode('exclusions')
                                externalModuleDependency.excludeRules.each { rule ->
                                    def exclusionNode = exclusionsNode.appendNode('exclusion')
                                    exclusionNode.appendNode('groupId', rule.group)
                                    exclusionNode.appendNode('artifactId', rule.module)
                                }
                            }

                        }
                }

            }
        }
//
        project.tasks['publish'].mustRunAfter(project.tasks['clean'])
//        // 11. PublishToMavenRepository这个类哪来的
        project.tasks.withType(PublishToMavenRepository.class) { Task task ->
            task.setGroup('ikCompile')
            // 12. dependsOn 3参数用途是啥
            task.dependsOn('assembleRelease', TASK_MAKE_DOC, TASK_ASSEMBLE_JAR)
        }

        project.tasks['publishToMavenLocal'].mustRunAfter(project.tasks['clean'])
        // 13. PublishToMavenRepository这个类哪来的
        project.tasks.withType(PublishToMavenLocal.class) { Task task ->
            task.setGroup('ikCompile')
            task.dependsOn('assembleRelease', TASK_MAKE_DOC, TASK_ASSEMBLE_JAR)
        }

        // 14. Pom文件是因为AAR中没有子依赖项。
        // 15. Gradle task 后边跟的 -Pt -Pv，这个 P哪里来的？
        // 16. 发布Arr之前要混淆 ProguardKeepUtils.keep(project)
        // 17. Gradle的InkeLog.d封装了啥？
        // 18 import com.meelive.plugin.tasks.AssembleJarTask
        // 19. import com.meelive.plugin.tasks.ComponentDocTask
    }

    static final class ConfigDependency {

        final String dependencyType
        final ExternalModuleDependency dependency

        ConfigDependency(String dependencyType, ExternalModuleDependency dependency) {
            this.dependencyType = dependencyType
            this.dependency = dependency
        }
    }
}

//publishing {
//    publications {
//        maven(MavenPublication) {
//            artifactId = 'PublishPlugin'
//            groupId = 'com.doing.plugin'
//            version = '0.0.1'
//            from components.java
//        }
//    }
//
//    repositories {
//        maven {
////                url = layout.buildDirectory.dir('repo')
//            url "${projectDir}/repo"
//        }
//    }
//}