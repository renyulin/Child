package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import child.ryl.child.R;
import child.ryl.child.flowlayout.FlowTagLayout;
import child.ryl.child.flowlayout.OnTagClickListener;
import child.ryl.child.flowlayout.OnTagSelectListener;
import child.ryl.child.flowlayout.TagAdapter;

/**
 * 可点击流布局
 */
public class FlowLayoutActivity extends Activity{
    private FlowTagLayout mColorFlowTagLayout;
    private FlowTagLayout mSizeFlowTagLayout;
    private FlowTagLayout mMobileFlowTagLayout;
    private TagAdapter<String> mSizeTagAdapter;
    private TagAdapter<String> mColorTagAdapter;
    private TagAdapter<String> mMobileTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowlayout);

        mColorFlowTagLayout = (FlowTagLayout) findViewById(R.id.color_flow_layout);
        mSizeFlowTagLayout = (FlowTagLayout) findViewById(R.id.size_flow_layout);
        mMobileFlowTagLayout = (FlowTagLayout) findViewById(R.id.mobile_flow_layout);

        //颜色
        mColorTagAdapter = new TagAdapter<>(this);
//        mColorFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_NONE);
        mColorFlowTagLayout.setAdapter(mColorTagAdapter);
        mColorFlowTagLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
//                Snackbar.make(view, "颜色:" + parent.getAdapter().getItem(position), Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Toast.makeText(FlowLayoutActivity.this, "颜色:" + parent.getAdapter().getItem(position), Toast.LENGTH_SHORT).show();
            }
        });

        //尺寸
        mSizeTagAdapter = new TagAdapter<>(this);
        mSizeFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        mSizeFlowTagLayout.setAdapter(mSizeTagAdapter);
        mSizeFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i : selectedList) {
                        sb.append(parent.getAdapter().getItem(i));
//                        sb.append("");
                    }
                    Toast.makeText(FlowLayoutActivity.this, "移动研发:" + sb.toString(), Toast.LENGTH_SHORT).show();
//                    Snackbar.make(parent, "移动研发:" + sb.toString(), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                } else {
                    Toast.makeText(FlowLayoutActivity.this, "没有选择标签", Toast.LENGTH_SHORT).show();
//                    Snackbar.make(parent, "没有选择标签", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                }
            }
        });

        //移动研发标签
        mMobileTagAdapter = new TagAdapter<>(this);
        mMobileFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        mMobileFlowTagLayout.setAdapter(mMobileTagAdapter);
        mMobileFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    StringBuilder sb = new StringBuilder();

                    for (int i : selectedList) {
                        sb.append(parent.getAdapter().getItem(i));
                        sb.append("");
                    }
                    Toast.makeText(FlowLayoutActivity.this, "移动研发:" + sb.toString(), Toast.LENGTH_SHORT).show();
//                    Snackbar.make(parent, "移动研发:" + sb.toString(), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                } else {
                    Toast.makeText(FlowLayoutActivity.this, "没有选择标签", Toast.LENGTH_SHORT).show();
//                    Snackbar.make(parent, "没有选择标签", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                }
            }
        });

        initColorData();

        initSizeData();

        initMobileData();
    }

    private void initMobileData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("android");
        dataSource.add("安卓");
        dataSource.add("SDK源码");
        dataSource.add("IOS");
        dataSource.add("iPhone");
        dataSource.add("游戏");
        dataSource.add("fragment");
        dataSource.add("viewcontroller");
        dataSource.add("cocoachina");
        dataSource.add("移动研发工程师");
        dataSource.add("移动互联网");
        dataSource.add("高薪+期权");
        mMobileTagAdapter.onlyAddAll(dataSource);
    }

    private void initColorData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("红色");
        dataSource.add("黑色");
        dataSource.add("花边色");
        dataSource.add("深蓝色");
        dataSource.add("白色");
        dataSource.add("玫瑰红色");
        dataSource.add("紫黑紫兰色");
        dataSource.add("葡萄红色");
        dataSource.add("黄色");
        dataSource.add("绿色");
        dataSource.add("彩虹色");
        dataSource.add("牡丹色");
        mColorTagAdapter.onlyAddAll(dataSource);
    }

    /**
     * 初始化数据
     */
    private void initSizeData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("28 (2.1尺)");
        dataSource.add("29 (2.2尺)");
        dataSource.add("30 (2.3尺)");
        dataSource.add("31 (2.4尺)");
        dataSource.add("32 (2.5尺)........");
        dataSource.add("33 (2.6尺)");
        dataSource.add("34 (2.7尺)");
        dataSource.add("35 (2.8尺)");
        dataSource.add("36 (2.9尺)");
        dataSource.add("37 (3.0尺)");
        dataSource.add("38 (3.1尺)");
        dataSource.add("39 (3.2尺)........");
        mSizeTagAdapter.onlyAddAll(dataSource);
    }
}
