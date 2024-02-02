package saltsheep.lib.asm;

public class Test {

    public Test() {
    }

    public static void main(String[] args) {
        try {
            //System.out.println(DescriptorUtils.getMethodDescriptor("void","int[][]","[int","[java.lang.Object","java.lang.Object[][]"));
            /*
			ClassReader classReader = new ClassReader("saltsheep.lib.asm.Test");
			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM5,classWriter) {
				@Override
				public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
					if(name.equals("method1")){
						//System.out.println(1);
						return new MethodVisitor(Opcodes.ASM5,super.visitMethod(access,name,desc,signature,exceptions)) {
							@Override
							public void visitCode() {
								//System.out.println(2);
								this.visitVarInsn(Opcodes.ALOAD,0);
								this.visitVarInsn(Opcodes.ALOAD,1);
								this.visitVarInsn(Opcodes.ALOAD,2);
								this.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "saltsheep/lib/Test","method2","(Ljava/lang/String;Ljava/lang/String;)V",false);
								this.visitInsn(Opcodes.RETURN);
								this.visitEnd();
							}
						};
					}
					return super.visitMethod(access,name,desc,signature,exceptions);
				}
			};
			classReader.accept(classVisitor, ClassReader.SKIP_FRAMES);
			*/
            //ClassReader classReader = new ClassReader("saltsheep.lib.asm.Test");
            //ClassWriter classWriter = new ClassWriter(classReader,ClassWriter.COMPUTE_MAXS);
    	    //byte[] basic = classWriter.toByteArray();
			//Class<?> test = new MyClassLoader().defineClass("saltsheep.lib.asm.Test", ClassEditor.addImplement(basic,"net/minecraftforge/fml/relauncher/IFMLLoadingPlugin"));
			//System.out.println(test.newInstance() instanceof IFMLLoadingPlugin);
			//test.getDeclaredMethod("method1",String.class,String.class).invoke(test.newInstance(),"a","b");
            //new Test().method1("a", "b");
        } catch (Throwable error) {
            error.printStackTrace();
        }

    	System.out.println(DescriptorUtils.getMethodDescriptor("void","java.lang.String","[saltsheep.lib.asm.Test"));
    }

    public void method1(String arg1, String arg2) {
        System.out.println(arg1);
    }

    public void method2(String arg1, String arg2) {
        System.out.println(arg2);
    }

    public static void staticMethod1(Test obj, String arg1, String arg2) {
        ((TestMark)obj).method4(arg1,arg2);
    }

    public static void staticMethod2(Test obj, String arg1, String arg2) {
        System.out.println("Method invoke at start successful!");
    }

    public static void staticMethod3(Test obj, String arg1, String arg2) {
        System.out.println("Method invoke at return successful!");
    }

    public static class Test2 extends Test{}

}
