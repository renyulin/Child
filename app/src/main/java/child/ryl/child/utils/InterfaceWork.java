package child.ryl.child.utils;

import child.ryl.child.interface_util.MyInterface;

/**
 * 接口的工作类
 */
public class InterfaceWork implements MyInterface {
    @Override
    public String myLife(String str) {
        return "我们的生活是"+str;
    }
}
