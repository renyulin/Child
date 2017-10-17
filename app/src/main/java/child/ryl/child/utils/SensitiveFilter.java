package child.ryl.child.utils;

import child.ryl.child.interface_util.MyInterface;

/**
 * 具体装饰角色，增加过滤掉政治敏感字眼的功能
 */
public class SensitiveFilter extends MessageBoardDecorator{
    public SensitiveFilter(MyInterface myInterface) {
        super(myInterface);
    }

    @Override
    public String myLife(String str) {
        String temp = super.myLife(str);
        temp += "^^过滤掉政治敏感的字眼!^^";
        return temp;
    }
}
