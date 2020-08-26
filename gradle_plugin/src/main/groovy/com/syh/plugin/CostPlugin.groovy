package com.syh.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES

class CostPlugin extends Transform implements Plugin<Project> {

    @Override
    String getName() {
        return "MethodTimePlugin"
    }

    // 输入文件类型
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 指Transform要操作内容的范围，官方文档Scope有7种类型：
     * EXTERNAL_LIBRARIES        只有外部库
     * PROJECT                   只有项目内容
     * PROJECT_LOCAL_DEPS        只有项目的本地依赖(本地jar)
     * PROVIDED_ONLY             只提供本地或远程依赖项
     * SUB_PROJECTS              只有子项目
     * SUB_PROJECTS_LOCAL_DEPS   只有子项目的本地依赖项(本地jar)
     * TESTED_CODE               由当前变量(包括依赖项)测试的代码
     * SCOPE_FULL_PROJECT        整个项目
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    // 指明当前Transform是否支持增量编译
    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void apply(Project project) {
        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(this)
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        println '//===============asm visit start===============//'
        def startTime = System.currentTimeMillis()
        inputs.each { TransformInput input ->
            input.directoryInputs.each { DirectoryInput directoryInput ->
                if (directoryInput.file.isDirectory()) {
                    directoryInput.file.eachFileRecurse { File file ->
                        def name = file.name
                        if (name.endsWith(".class") && !name.startsWith("R\$") &&
                                !"R.class".equals(name) && !"BuildConfig.class".equals(name)) {
                            println name + ' is changing...'
                            ClassReader cr = new ClassReader(file.bytes)
                            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
                            ClassVisitor cv = new CostClassVisitor(cw)
                            cr.accept(cv, EXPAND_FRAMES)
                            byte[] code = cw.toByteArray()
                            FileOutputStream fos = new FileOutputStream(
                                    file.parentFile.absolutePath + File.separator + name)
                            fos.write(code)
                            fos.close()
                        }
                    }
                }
                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
            input.jarInputs.each { JarInput jarInput ->
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                def dest = outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)

                FileUtils.copyFile(jarInput.file, dest)
            }
        }
        def cost = (System.currentTimeMillis() - startTime) / 1000
        println "plugin cost $cost secs"
        println '//===============asm visit end===============//'
    }
}