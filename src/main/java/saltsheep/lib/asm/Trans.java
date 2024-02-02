package saltsheep.lib.asm;

import jdk.internal.org.objectweb.asm.Opcodes;
import net.minecraft.launchwrapper.IClassTransformer;

public class Trans implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        try {
            if (transformedName.equals("saltsheep.lib.asm.Test")) {
                basicClass = ClassEditor.createMethod(basicClass, Opcodes.ACC_PUBLIC,"method3", DescriptorUtils.getMethodDescriptor("void","java.lang.String"));
                basicClass = ClassEditor.createField(basicClass,Opcodes.ACC_PUBLIC, "fieldTest",DescriptorUtils.getFieldDescriptor("saltsheep.lib.asm.Test"),null);
                basicClass = ClassEditor.renameMethod(basicClass,"method2","method4",DescriptorUtils.getMethodDescriptor("void","java.lang.String","java.lang.String"));
                basicClass = ClassEditor.renameField(basicClass,"fieldTest","fieldTest2");
                basicClass = ClassEditor.addImplement(basicClass,"saltsheep.lib.asm.TestMark");
                basicClass = MethodEditor.replaceMethod(basicClass,"method1",DescriptorUtils.getMethodDescriptor("void","java.lang.String","java.lang.String"),"saltsheep.lib.asm.Test","staticMethod1",DescriptorUtils.getMethodDescriptor("void","saltsheep.lib.asm.Test","java.lang.String","java.lang.String"));
                basicClass = MethodEditor.invokeAtMethodFirst(basicClass,"method1",DescriptorUtils.getMethodDescriptor("void","java.lang.String","java.lang.String"),"saltsheep.lib.asm.Test","staticMethod2",DescriptorUtils.getMethodDescriptor("void","saltsheep.lib.asm.Test","java.lang.String","java.lang.String"));
                basicClass = MethodEditor.invokeAtMethodReturn(basicClass,"method1",DescriptorUtils.getMethodDescriptor("void","java.lang.String","java.lang.String"),"saltsheep.lib.asm.Test","staticMethod3",DescriptorUtils.getMethodDescriptor("void","saltsheep.lib.asm.Test","java.lang.String","java.lang.String"));
                /*
                ClassReader classReader = new ClassReader("saltsheep.lib.asm.Test");
                ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM5, classWriter) {
                    @Override
                    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                        if (name.equals("method1")) {
                            //System.out.println(1);
                            return new MethodVisitor(Opcodes.ASM5, super.visitMethod(access, name, desc, signature, exceptions)) {
                                @Override
                                public void visitCode() {
                                    //System.out.println(2);
                                    this.visitVarInsn(Opcodes.ALOAD, 0);
                                    this.visitVarInsn(Opcodes.ALOAD, 1);
                                    this.visitVarInsn(Opcodes.ALOAD, 2);
                                    this.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "saltsheep/lib/Test", "method2", "(Ljava/lang/String;Ljava/lang/String;)V", false);
                                    this.visitEnd();
                                    //super.visitCode();
                                }
                            };
                        }
                        return super.visitMethod(access, name, desc, signature, exceptions);
                    }
                };
                classReader.accept(classVisitor, ClassReader.SKIP_FRAMES);
                return classWriter.toByteArray();
                */
            }
        } catch (Throwable error) {
            error.printStackTrace();
        }
        return basicClass;
    }
}
