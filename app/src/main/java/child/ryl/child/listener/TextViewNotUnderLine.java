package child.ryl.child.listener;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.UnderlineSpan;

/**
 * 无下划线的Span
 */
public class TextViewNotUnderLine extends UnderlineSpan {

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.parseColor("#FF5200"));
        ds.setUnderlineText(false);
    }
}
