package com.jvm.Chapter02;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**VM args:-XX:PermSize=10M -XX:MaxPermSize=10
 * 方法区存储Class的相关信息,如类名、访问修饰符、常量池、字段描述、方法描述
 * 方法区内存溢出测试
 * Created by dongweizhao on 17/7/5.
 */
public class JavaMethodAreaOOM {
    public static void main(String[] args) {
        while (true){
            Enhancer enhancer=new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o,objects);
                }
            });
            enhancer.create();

        }
    }
    static  class OOMObject{}
}
