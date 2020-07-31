package com.syh.plugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import org.gradle.api.Plugin
import org.gradle.api.Project

class Lifecycle extends Transform implements Plugin<Project> {

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

    @Override
    void apply(Project project) {
        System.out.println("========================");
        System.out.println("lifecycle gradle plugin!");
        System.out.println("========================");
    }
}