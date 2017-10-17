package child.ryl.child.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import child.ryl.child.R;
import child.ryl.child.activity.AccessActivity;
import child.ryl.child.activity.ByteTest;
import child.ryl.child.activity.CalendarActivity;
import child.ryl.child.activity.CarouselActivity;
import child.ryl.child.activity.DrawViewActivity;
import child.ryl.child.activity.FileActivity;
import child.ryl.child.activity.FlowAllActivity;
import child.ryl.child.activity.FlowLayoutActivity;
import child.ryl.child.activity.HorizontalListViewActivity;
import child.ryl.child.activity.IOCActivity;
import child.ryl.child.activity.InterfaceActivity;
import child.ryl.child.activity.ListViewButtonActivity;
import child.ryl.child.activity.MultiLayoutListViewActivity;
import child.ryl.child.activity.ExpandableActivity;
import child.ryl.child.activity.PictureActivity;
import child.ryl.child.activity.PlayGifActivity;
import child.ryl.child.activity.PrimarySqlActivity;
import child.ryl.child.activity.ShowLocationDialogActivity;
import child.ryl.child.activity.SidePullActivity;
import child.ryl.child.activity.TitleTestActivity;
import child.ryl.child.activity.ViewAndValueActivity;
import child.ryl.child.activity.XListViewActivity;
import child.ryl.child.activity.XmlSaveActivity;
import child.ryl.child.adapter.ThirdFragmentAdapter;
import child.ryl.child.update_photo.UpdateInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * ThirdFragment
 */
public class ThirdFragment extends Fragment implements AdapterView.OnItemClickListener {
    private View view;
    private ListView listView;
    private List<String> list;
    private ThirdFragmentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_third, null);
            listView = (ListView) view.findViewById(R.id.fragment_third_listView);
            init();
            adapter = new ThirdFragmentAdapter(getActivity(), list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
        return view;
    }

    private void init() {
        list = new ArrayList<>();
        list.add("listView侧拉0");
        list.add("下拉+上拉1");
        list.add("多布局listView2");
        list.add("横向滑动3");
        list.add("listView_button冲突4");
        list.add("存取图片相册不显示5");
        list.add("文件存储6");
        list.add("单张读取相册图片7");
        list.add("标题测试类8");
        list.add("轮播9");
        list.add("可点击流布局10");
        list.add("日历11");
        list.add("数据存储xml.12");//13
        list.add("自定义显示隐藏布局13");//14
        list.add("接口调用14");//15
        list.add("ioc测试15");//16
        list.add("onDraw自定义实现16");//17
        list.add("dialog位置展示17");//18
        list.add("播放本地gif图片18");//19
        list.add("上传头像到服务器19");//20
        list.add("数据库ormlite20");//21
        list.add("valueAnimation和view应用");//22
        list.add("bundle数据传递和转换");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), SidePullActivity.class));
                break;
            case 1:
                startActivity(new Intent(getActivity(), XListViewActivity.class));
                break;
            case 2:
                startActivity(new Intent(getActivity(), MultiLayoutListViewActivity.class));
                break;
            case 3:
                startActivity(new Intent(getActivity(), HorizontalListViewActivity.class));
                break;
            case 4:
                startActivity(new Intent(getActivity(), ListViewButtonActivity.class));
                break;
            case 5:
                startActivity(new Intent(getActivity(), AccessActivity.class));
                break;
            case 6:
                startActivity(new Intent(getActivity(), FileActivity.class));
                break;
            case 7:
                startActivity(new Intent(getActivity(), PictureActivity.class));
                break;
            case 8:
                startActivity(new Intent(getActivity(), TitleTestActivity.class));
                break;
            case 9:
                startActivity(new Intent(getActivity(), CarouselActivity.class));
                break;
            case 10:
                startActivity(new Intent(getActivity(), FlowAllActivity.class));
                break;
            case 11:
                startActivity(new Intent(getActivity(), CalendarActivity.class));
                break;
            case 12:
                startActivity(new Intent(getActivity(), XmlSaveActivity.class));
                break;
            case 13:
                startActivity(new Intent(getActivity(), ExpandableActivity.class));
                break;
            case 14:
                startActivity(new Intent(getActivity(), InterfaceActivity.class));
                break;
            case 15:
                startActivity(new Intent(getActivity(), IOCActivity.class));
                break;
            case 16:
                startActivity(new Intent(getActivity(), DrawViewActivity.class));
                break;
            case 17:
                startActivity(new Intent(getActivity(), ShowLocationDialogActivity.class));
                break;
            case 18:
                startActivity(new Intent(getActivity(), PlayGifActivity.class));
                break;
            case 19:
                startActivity(new Intent(getActivity(), UpdateInfoActivity.class));
                break;
            case 20:
                startActivity(new Intent(getActivity(), PrimarySqlActivity.class));
                break;
            case 21:
                startActivity(new Intent(getActivity(), ViewAndValueActivity.class));
                break;
            case 22:
                startActivity(new Intent(getActivity(), ByteTest.class));
                break;
        }
    }
}
