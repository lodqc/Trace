// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val agpVersion = "8.1.0"
    val kotlinVersion = "1.8.0"
    val pluginPublishVersion = "1.2.0"
    val tracePluginVersion = "0.0.2"
    id("com.android.application").version(agpVersion).apply(false)
    id("com.android.library").version(agpVersion).apply(false)
    id("org.jetbrains.kotlin.android").version(kotlinVersion).apply(false)
    id("org.jetbrains.kotlin.jvm").version(kotlinVersion).apply(false)
    id("com.gradle.plugin-publish").version(pluginPublishVersion).apply(false)
    id("io.github.leavesczy.trace").version(tracePluginVersion).apply(false)
}