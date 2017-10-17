package child.ryl.child.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import child.ryl.child.R;
import child.ryl.child.utils.Utils;


/**
 * 到期提示dialog
 */
public class PromptDialog extends Dialog implements View.OnClickListener {
    private TextView content;
    public TextView iKnow;
    public ImageView dialog_prompt_top;
    public ImageView dialog_prompt_bottom;
    private Context context;
    public LinearLayout dialog_prompt_ll;
    public LinearLayout dialog_prompt_all;
    //    public int contentHeight;
    public int bottomHeight;
    public int topHeight;

    public PromptDialog(Context context) {
        super(context);
        this.context = context;
    }

    public PromptDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_prompt);
        initView();
    }

    private void initView() {
        dialog_prompt_all = (LinearLayout) findViewById(R.id.dialog_prompt_all);
        dialog_prompt_ll = (LinearLayout) findViewById(R.id.dialog_prompt_ll);
        dialog_prompt_top = (ImageView) findViewById(R.id.dialog_prompt_top);
        dialog_prompt_bottom = (ImageView) findViewById(R.id.dialog_prompt_bottom);
        content = (TextView) findViewById(R.id.dialog_prompt_content);
        iKnow = (TextView) findViewById(R.id.dialog_prompt_know);
        iKnow.setVisibility(View.GONE);
        iKnow.setOnClickListener(this);
        content.setMovementMethod(LinkMovementMethod.getInstance());
        String str = "ddddddddddd";
        String tel400 = "400-632-2002转10010";
        str += "eeeeeeeeeeee";
        int startNumber = str.length();
        int endNumber = tel400.length() + startNumber;
        str += tel400;
        content.setText(Utils.getSpan(context, str, startNumber, endNumber, tel400));
        content.setHighlightColor(context.getResources().getColor(android.R.color.transparent));

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        dialog_prompt_top.measure(w, h);
        dialog_prompt_bottom.measure(w, h);
        topHeight = dialog_prompt_top.getMeasuredHeight();
        bottomHeight = dialog_prompt_bottom.getMeasuredHeight();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_prompt_know:
                dismiss();
                break;
        }
    }
}
