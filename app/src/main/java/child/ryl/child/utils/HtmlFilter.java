package child.ryl.child.utils;

import child.ryl.child.interface_util.MyInterface;

/**
 * 具体装饰角色，增加过滤掉HTML标签的功能
 */
public class HtmlFilter extends MessageBoardDecorator{
    public HtmlFilter(MyInterface myInterface) {
        super(myInterface);
    }

    @Override
    public String myLife(String str) {
        String temp = super.myLife(str);
        temp += "^^过滤掉HTML标签!^^";
        return temp;
    }
}
