package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import child.ryl.child.R;
import child.ryl.child.my_view.DrawView;
import child.ryl.child.my_view.MyToggleButton;
import child.ryl.child.utils.AnimationUtil;
import child.ryl.child.utils.Utils;

/**
 * 实现onDraw自定义View的展示
 */
public class DrawViewActivity extends Activity implements View.OnClickListener {
    private MyToggleButton myToggleButton;
    private DrawView drawView;
    private TextView activity_draw_text2;
    private TextView activity_draw_text1;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        activity_draw_text1 = (TextView) findViewById(R.id.activity_draw_text1);
        activity_draw_text2 = (TextView) findViewById(R.id.activity_draw_text2);
        activity_draw_text1.setOnClickListener(this);
        initDate();
    }

    private void initDate() {
        myToggleButton = (MyToggleButton) findViewById(R.id.myToggleButton);
        myToggleButton.setTag(false);
        drawView = (DrawView) findViewById(R.id.drawView);
        Button button = (Button) findViewById(R.id.my_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.setIcon();

                boolean tag = (boolean) myToggleButton.getTag();
                myToggleButton.setTag(!tag);
                if (!tag)
                    myToggleButton.setToggleOn();
                else myToggleButton.setToggleOff();
            }
        });
        myToggleButton.setOnToggleChanged(new MyToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                myToggleButton.setTag(on);
                String msg = "";
                if (on)
                    msg = "true";
                else msg = "false";
                Utils.smallToast(DrawViewActivity.this, msg);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_draw_text1:
                if (i == 0) {
                    activity_draw_text1.setAnimation(AnimationUtil.moveToViewBottom());
                    AnimationUtil.makeToViewSmall(activity_draw_text2);
                    i = 1;
                } else {
                    activity_draw_text1.setAnimation(AnimationUtil.moveToViewLocation());
                    AnimationUtil.makeToViewBig(activity_draw_text2);
                    i = 0;
                }
                break;
        }
    }
}
