package com.dwarfeng.dutil.demo.basic;

import com.dwarfeng.dutil.basic.cls.ClassUtil;
import com.dwarfeng.dutil.basic.io.CT;

import java.util.List;
import java.util.concurrent.RunnableScheduledFuture;

public class ClassUtilDemo {

    public static void main(String[] args) {
        CT.trace(" C's super classes are:");
        for (Class<?> clas : ClassUtil.getSuperClasses(C.class)) {
            CT.trace("\t" + clas);
        }
        CT.trace("C's implement interfaces are:");
        for (Class<?> clas : ClassUtil.getImplInterfaces(C.class)) {
            CT.trace("\t" + clas);
        }
    }

    private static abstract class A {
    }

    private static abstract class B extends A implements RunnableScheduledFuture<Object> {
    }

    private static abstract class C extends B implements List<Object> {
    }
}
