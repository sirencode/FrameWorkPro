package com.syh.plugin;

import com.syh.plugin.annotation.Cost;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by shenyonghe on 2020/8/26.
 */
class CostClassVisitor extends ClassVisitor {
    public CostClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        mv = new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
            private boolean inject = false;
            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                if (Type.getDescriptor(Cost.class).equals(desc)) {
                    inject = true;
                }
                return super.visitAnnotation(desc, visible);
            }
            @Override
            protected void onMethodEnter() {
                super.onMethodEnter();
                if (inject) {
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                    mv.visitVarInsn(LSTORE, 2);
                    mv.visitLdcInsn("ASM-TAG");
                    mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                    mv.visitInsn(DUP);
                    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                    mv.visitLdcInsn("=================start::");
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    mv.visitVarInsn(LLOAD, 2);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
                    mv.visitLdcInsn("ms==========");
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                    mv.visitMethodInsn(INVOKESTATIC, "com/syh/framework/util/LogUtil", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
                    mv.visitInsn(POP);
                }
            }
            @Override
            protected void onMethodExit(int opcode) {
                super.onMethodExit(opcode);
                if (inject) {
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                    mv.visitVarInsn(LSTORE, 4);
                    mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                    mv.visitInsn(DUP);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getSimpleName", "()Ljava/lang/String;", false);
                    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
                    mv.visitLdcInsn("--->");
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    mv.visitLdcInsn(name);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                    mv.visitVarInsn(ASTORE, 6);
                    mv.visitLdcInsn("ASM-TAG");
                    mv.visitVarInsn(ALOAD, 6);
                    mv.visitMethodInsn(INVOKESTATIC, "com/syh/framework/util/LogUtil", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
                    mv.visitInsn(POP);
                    mv.visitLdcInsn("ASM-TAG");
                    mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                    mv.visitInsn(DUP);
                    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                    mv.visitLdcInsn("----> execute cost::");
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    mv.visitVarInsn(LLOAD, 4);
                    mv.visitVarInsn(LLOAD, 2);
                    mv.visitInsn(LSUB);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
                    mv.visitLdcInsn("ms");
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                    mv.visitMethodInsn(INVOKESTATIC, "com/syh/framework/util/LogUtil", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
                    mv.visitInsn(POP);
                    mv.visitLdcInsn("ASM-TAG");
                    mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                    mv.visitInsn(DUP);
                    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                    mv.visitLdcInsn("=================end::");
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    mv.visitVarInsn(LLOAD, 4);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
                    mv.visitLdcInsn("ms==========");
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                    mv.visitMethodInsn(INVOKESTATIC, "com/syh/framework/util/LogUtil", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
                    mv.visitInsn(POP);
                }
            }
        };
        return mv;
    }
}
