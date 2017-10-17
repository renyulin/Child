package child.ryl.child.listener;

import android.text.style.ClickableSpan;
import android.view.View;

/**
 * 电话号的监听事件
 */
public class Clickable extends ClickableSpan implements View.OnClickListener {
    private final View.OnClickListener mListener;

    public Clickable(View.OnClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View view) {
        mListener.onClick(view);
    }
}