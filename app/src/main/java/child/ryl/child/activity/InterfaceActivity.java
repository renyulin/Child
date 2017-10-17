package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import child.ryl.child.R;
import child.ryl.child.interface_util.MyInterface;
import child.ryl.child.utils.HtmlFilter;
import child.ryl.child.utils.InterfaceWork;
import child.ryl.child.utils.SensitiveFilter;
//
//import com.example.administrator.myapplication.R;
//import com.example.administrator.myapplication.interface_util.MyInterface;
//import com.example.administrator.myapplication.utils.HtmlFilter;
//import com.example.administrator.myapplication.utils.InterfaceWork;
//import com.example.administrator.myapplication.utils.MessageBoardDecorator;
//import com.example.administrator.myapplication.utils.SensitiveFilter;

/**
 * 接口调用
 * http://blog.csdn.net/lenotang/article/details/2587265
 *
 * 1)        抽象构件角色（Component）：定义一个抽象接口，以规范准备接收附加责任的对象。
 * 2)        具体构件角色(Concrete Component)：这是被装饰者，定义一个将要被装饰增加功能的类。
 * 3)        装饰角色(Decorator)：持有一个构件对象的实例，并定义了抽象构件定义的接口。
 * 4)        具体装饰角色(Concrete Decorator)：负责给构件添加增加的功能。
 *
 * 装饰着模式初步理解：本质为系统接口与继承的应用，Aclass extends Dclass,Bclass extends Dclass,
 * Dclass implements Eclass,Eclass为接口，Eclass e=new Aclass(new Bclass(new Dclass)) 实现构造
 * e.接口方法 我的理解是：继承同一个接口实现类的类可以相互new通过构造实现接口方法 简而言之：系统自带
 *
 */
public class InterfaceActivity extends Activity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);
        textView = (TextView) findViewById(R.id.activity_interface_text);
        MyInterface myInterface1 = new InterfaceWork();
        String life = myInterface1.myLife("美好幸福的");
        textView.setText(life);
        MyInterface myInterface = new InterfaceWork();
        String content = myInterface.myLife("一定要学好装饰模式！");
        System.out.println(content);
        Log.i("interface1", content);
        myInterface = new HtmlFilter(new SensitiveFilter(new InterfaceWork()));
        content = myInterface.myLife("一定要学好装饰模式！");
        System.out.println(content);
        Log.i("interface2", content);
    }
}
