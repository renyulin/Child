package child.ryl.child.danli;

/**
 * 单例模式
 */
public class TestSingleton {
    String name = null;

    private TestSingleton() {
    }

    private static volatile TestSingleton instance = null;

    public static TestSingleton getInstance() {
        if (instance == null) {
            synchronized (TestSingleton.class) {
                if (instance == null) {
                    instance = new TestSingleton();
                }
            }
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printInfo() {
        System.out.println("the name is " + name);
    }
}
/*
1、在getInstance方法上加同步

public static synchronized Singleton getInstance() {
         if (single == null) {
             single = new Singleton();
         }
        return single;
}

2、双重检查锁定

public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
               if (singleton == null) {
                  singleton = new Singleton();
               }
            }
        }
        return singleton;
    }
3、静态内部类

public class Singleton {
    private static class LazyHolder {
       private static final Singleton INSTANCE = new Singleton();
    }
    private Singleton (){}
    public static final Singleton getInstance() {
       return LazyHolder.INSTANCE;
    }
}
这种比上面1、2都好一些，既实现了线程安全，又避免了同步带来的性能影响。


二、饿汉式单例

//饿汉式单例类.在类初始化时，已经自行实例化
public class Singleton1 {
    private Singleton1() {}
    private static final Singleton1 single = new Singleton1();
    //静态工厂方法
    public static Singleton1 getInstance() {
        return single;
    }
}
 */

