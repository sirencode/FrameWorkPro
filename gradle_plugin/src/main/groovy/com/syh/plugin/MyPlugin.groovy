package com.syh.plugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin extends Transform implements Plugin<Project> {

    void apply(Project project) {
        System.out.println("========================");
        System.out.println("My gradle plugin!");
        System.out.println("========================");
    }

    @Override
    String getName() {
        return null
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return null
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return null
    }

    @Override
    boolean isIncremental() {
        return false
    }
}