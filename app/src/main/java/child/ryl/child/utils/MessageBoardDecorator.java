package child.ryl.child.utils;

import child.ryl.child.interface_util.MyInterface;

/**
 * 装饰角色
 */
public class MessageBoardDecorator implements MyInterface {
    private MyInterface myInterface;

    public MessageBoardDecorator(MyInterface myInterface) {
        super();
        this.myInterface = myInterface;
    }

    @Override
    public String myLife(String str) {
        return myInterface.myLife(str);
    }
}
