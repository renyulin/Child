package child.ryl.child.my_view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class DragImageView extends RelativeLayout implements OnTouchListener {

    private RelativeLayout.LayoutParams wmParams;
    private OnClickListener mListener;
    private AnimatorSet mSet;
    private RelativeLayout mParentLayout;
    private int screenWidth;
    private int screenHeight;
    private int measuredWidth;
    private int measuredHeight;
    private static int sMarginLeft = -1;
    private static int sMarginTop = -1;

    public DragImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public DragImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragImageView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        wmParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        setOnTouchListener(this);
    }

    public void attachView(RelativeLayout parent) {
        mParentLayout = parent;
        mParentLayout.addView(this, wmParams);
        this.setVisibility(INVISIBLE);
        try {
            this.measure(0, 0);
            measuredWidth = getMeasuredWidth();
            measuredHeight = getMeasuredHeight();
        } catch (NullPointerException e) {
            measuredWidth = 140;
            measuredHeight = 168;
        }
        if (sMarginLeft == -1 || sMarginTop == -1) {
            sMarginLeft = screenWidth - measuredWidth;
            sMarginTop = (screenHeight - measuredHeight) / 2;
        }
        wmParams.leftMargin = sMarginLeft;
        wmParams.topMargin = sMarginTop;
        mParentLayout.updateViewLayout(this, wmParams);
        this.setVisibility(VISIBLE);
        startAnimation();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void startAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (mSet == null) {
                // this.setPivotY(0);
                ObjectAnimator rotatestart = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 0.8f).setDuration(400);
                ObjectAnimator rotateLeft = ObjectAnimator.ofFloat(this, "scaleX", 0.8f, 1.0f).setDuration(100);
                ObjectAnimator rotateRight = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 0.9f).setDuration(200);
                ObjectAnimator rotateBack = ObjectAnimator.ofFloat(this, "scaleX", 0.9f, 1.0f).setDuration(100);
                mSet = new AnimatorSet();
                mSet.playSequentially(rotatestart, rotateLeft, rotateRight, rotateBack);
                mSet.setStartDelay(2000);
                mSet.addListener(new AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (mSet != null) {
                            mSet.start();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }
                });
                mSet.start();
            }
        }
    }

    public void dettachView() {
        stopAnimation();
        mParentLayout.removeView(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void stopAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (mSet != null) {
                mSet.cancel();
                mSet = null;
            }
        }
    }

    private float downX = 0;
    private float downY = 0;
    private float deltaX = 0;
    private float deltaY = 0;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                deltaX = downX;
                deltaY = downY;
                return true;
            case MotionEvent.ACTION_MOVE:
                sMarginLeft -= (int) (deltaX - event.getRawX());
                sMarginTop -= (int) (deltaY - event.getRawY());
                if (sMarginLeft < 0) {
                    sMarginLeft = 0;
                }
                if (sMarginTop < 0) {
                    sMarginTop = 0;
                }
                if (sMarginLeft > screenWidth - measuredWidth) {
                    sMarginLeft = screenWidth - measuredWidth;
                }
                if (sMarginTop > mParentLayout.getHeight() - measuredHeight) {
                    sMarginTop = mParentLayout.getHeight() - measuredHeight;
                }
                wmParams.leftMargin = sMarginLeft;
                wmParams.topMargin = sMarginTop;
                deltaX = event.getRawX();
                deltaY = event.getRawY();
                mParentLayout.updateViewLayout(DragImageView.this, wmParams);
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(downX - event.getRawX()) < 3 && Math.abs(downY - event.getRawY()) < 3) {
                    mListener.onClick(v);
                }
                break;
            default:
                v.performClick();
                break;
        }
        return false;
    }

    @Override
    public boolean performClick() {
        return mListener == null && super.performClick();
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mListener = l;
    }
}
