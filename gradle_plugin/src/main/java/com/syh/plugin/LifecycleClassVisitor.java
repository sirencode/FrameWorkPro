package com.syh.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenyonghe on 2020/7/31.
 */
class LifecycleClassVisitor extends ClassVisitor implements Opcodes {

    public static List<String> lifeRecords = new ArrayList<>();

    private String mClassName;
    private String superName;

    public LifecycleClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature,
                      String superName, String[] interfaces) {
        System.out.println("LifecycleClassVisitor : visit -----> started ：" + name);
        this.mClassName = name;
        this.superName = superName;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
        System.out.println("LifecycleClassVisitor : visitMethod : " + name);
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        //匹配FragmentActivity
        if (mClassName.equals("androidx/fragment/app/FragmentActivity")) {
            if ("onCreate".equals(name)) {
                //处理onCreate
                return new LifecycleOnCreateMethodVisitor(mv);
            } else if ("onDestroy".equals(name)) {
                //处理onDestroy
                return new LifecycleOnDestroyMethodVisitor(mv);
            } else if ("onStart".equals(name)) {
                //处理onDestroy
                return new LifecycleOnStartMethodVisitor(mv);
            } else if ("onStop".equals(name)) {
                //处理onDestroy
                return new LifecycleOnStopMethodVisitor(mv);
            }
        } else if (mClassName.equals("com/syh/asm/BaseLazyFragment")) {
            if ("notifyFragmentVisibleListeners".equals(name)) {
                //处理onCreate
                return new LifecycleFragmentVisibleMethodVisitor(mv, access, name, desc);
            }
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        System.out.println("LifecycleClassVisitor : visit -----> end");
        super.visitEnd();
    }
}
