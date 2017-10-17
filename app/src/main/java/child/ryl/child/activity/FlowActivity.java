package child.ryl.child.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import child.ryl.child.R;
import child.ryl.child.flowlayout.flow.CoustomFlowLayout;

/**
 * 第二个类型的流布局
 */
public class FlowActivity extends Activity {
    private String mNames[] = {"考研", "求职求职求职求职求职求职求职求职求职求职求职求职求职求职求职", "面试 ", "四六级", "国二", "驾照", "电视剧",
            "电影", "小说", "美食", "游戏"};
    private CoustomFlowLayout mFlowLayout = null;
    private Context mContext = null;
    private List<TextView> tvs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        init();
    }

    private void init() {
        initData();
        initChildViews();
//		initEvent();
    }

    private void initData() {
        mContext = this;
        tvs = new LinkedList<TextView>();
    }

    private void initChildViews() {
        mFlowLayout = (CoustomFlowLayout) findViewById(R.id.flowlayout);
        LayoutInflater mInflater = LayoutInflater.from(this);
        // MarginLayoutParams lp = new MarginLayoutParams(
        // LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // lp.leftMargin = 5;
        // lp.rightMargin = 5;
        // lp.topMargin = 5;
        // lp.bottomMargin = 5;
        Random random = new Random();
        for (int i = 0; i < mNames.length; i++) {
            Button tv = (Button) mInflater.inflate(R.layout.tv,
                    mFlowLayout, false);
            tv.setText(mNames[i]);
            tv.setTextSize(22);
//            tv.setGravity(0);
            tv.setBackgroundResource(R.drawable.round_rectangle_bg);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button tt = (Button) view;
                    tt.setSelected(true);
                    Toast.makeText(mContext, tt.getText(), Toast.LENGTH_SHORT).show();
                }
            });
            tvs.add(tv);

            mFlowLayout.addView(tv);
        }
    }

//	private void initEvent() {
//		tvs.get(0).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(mContext, "考研", Toast.LENGTH_SHORT).show();
//			}
//		});
//	}
}
