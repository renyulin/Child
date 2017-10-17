package child.ryl.child.dialog;

import android.app.Activity;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import child.ryl.child.utils.Utils;


/**
 * 用于设定dialog展示的位置
 * 任玉林
 */
public class DialogShow {
    public static final int EXPIRE_PROMPT = 0;//到期提示按钮
    public static final int MAIN_PROMPT = 1;//recycleView到期的提示按钮

//    /**
//     * 更多的对话框
//     *
//     * @param location
//     * @param width
//     * @param height
//     * @param resTopID    top of View
//     * @param resBottomID bottom of View
//     * @param content
//     * @param activity
//     * @param onlyDialog
//     */
//    public static void OnlyShowAtLocation(int[] location, int width, int height, int resTopID, int resBottomID,
//                                          String content, Activity activity, OnlyDialog onlyDialog) {
//        Rect frame = new Rect();
//        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        int statusBarHeight = frame.top;
//        // 预先设置Dialog的一些属性
//        Window dialogWindow = onlyDialog.getWindow();
//        WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        //获取屏幕的高度和宽度
//        WindowManager m = activity.getWindow().getWindowManager();
//        Display d = m.getDefaultDisplay();
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        d.getMetrics(outMetrics);
//        params.gravity = Gravity.LEFT;
//        params.alpha = 0.8f;
////        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), resID);
////        int dialogWHalf = bitmap.getWidth() / 2;
//        int dialogWHalf = onlyDialog.widthLL / 2;
//        params.x = location[0] + (width / 2) - dialogWHalf;
//        int screenHeight = CommonUtil.getScreenHeight(activity);
//        //dialog图片切换,dialog上下位置判断
//        if (location[1] + height > screenHeight * 6 / 7) {
//            params.gravity |= Gravity.BOTTOM;
//            onlyDialog.dialog_only_text.setBackgroundResource(resTopID);
//            onlyDialog.dialog_only_text.setPadding(0, 0, 0, (int) (5 * outMetrics.density));
//            params.y = screenHeight - location[1];
//        } else {
//            params.gravity |= Gravity.TOP;
//            onlyDialog.dialog_only_text.setBackgroundResource(resBottomID);
//            onlyDialog.dialog_only_text.setPadding(0, (int) (5 * outMetrics.density), 0, 0);
//            params.y = location[1] + height - statusBarHeight + (int) (5 * outMetrics.density);
//        }
//        if (!TextUtils.isEmpty(content)) {
//            onlyDialog.dialog_only_text.setText(content);
//        }
//        dialogWindow.setAttributes(params);
//    }

    /**
     * 首页到期提示
     * recycleView点击的事件
     * 到期提示dialog控制
     *
     * @param location
     * @param width
     * @param height
     * @param position
     * @param code
     * @param activity
     * @param promptDialog
     */
    public static void expirePromptShowAtLocation(int[] location, int width, int height, int position,
                                                  int code, Activity activity, PromptDialog promptDialog) {
        promptDialog.show();
        // 预先设置Dialog的一些属性
        Window dialogWindow = promptDialog.getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        params.alpha = 0.8f;
        //获取屏幕的高度和宽度
        WindowManager m = activity.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        d.getMetrics(outMetrics);
        if (code == MAIN_PROMPT) {//根据code判断是recycleView的还是到期提示按钮的
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            promptDialog.iKnow.setVisibility(View.VISIBLE);
            params.width = (int) (d.getWidth() * 0.7);
            //dialog的位置判断：左中右
            if (location[0] < (d.getWidth() / 3)) {
                promptDialog.dialog_prompt_all.setGravity(Gravity.NO_GRAVITY);
                params.x = (int) (15 * outMetrics.density);
                params.gravity = Gravity.LEFT;
                // 通过自定义坐标来放置你的控件
                if (9 == position) {
                    promptDialog.dialog_prompt_top.setPadding(
                            d.getWidth() * 1 / 6, 0, 0, 0);
                    promptDialog.dialog_prompt_bottom.setPadding(
                            d.getWidth() * 1 / 6, 0, 0, 0);
                } else {
                    promptDialog.dialog_prompt_top.setPadding(
                            d.getWidth() * 1 / 6 + (int) (8 * outMetrics.density), 0, 0, 0);
                    promptDialog.dialog_prompt_bottom.setPadding(
                            d.getWidth() * 1 / 6 + (int) (8 * outMetrics.density), 0, 0, 0);
                }
            } else if (location[0] < (d.getWidth() * 2 / 3)) {
                promptDialog.dialog_prompt_all.setGravity(Gravity.CENTER);
                params.x = (int) (0 * outMetrics.density);
                params.gravity = Gravity.CENTER;
                promptDialog.dialog_prompt_top.setPadding(
                        d.getWidth() / 6, 0, 0, 0);
                promptDialog.dialog_prompt_bottom.setPadding(
                        d.getWidth() / 6, 0, 0, 0);
            } else {
                promptDialog.dialog_prompt_all.setGravity(Gravity.RIGHT);
                params.x = (int) (15 * outMetrics.density);
                params.gravity = Gravity.RIGHT;
                promptDialog.dialog_prompt_top.setPadding(0, 0, (int) (10 * outMetrics.density), 0);
                promptDialog.dialog_prompt_bottom.setPadding(0, 0, (int) (10 * outMetrics.density), 0);
                promptDialog.dialog_prompt_ll.setGravity(Gravity.RIGHT);
            }
            int screenHeight = Utils.getScreenHeight(activity);
            //dialog图片的显示隐藏,dialog上下位置判断
            int imgHeight;
            if (location[1] + height > screenHeight * 2 / 3) {
                params.gravity |= Gravity.BOTTOM;
                promptDialog.dialog_prompt_top.setVisibility(View.GONE);
                promptDialog.dialog_prompt_bottom.setVisibility(View.VISIBLE);
                imgHeight = promptDialog.bottomHeight;
                int paramsY = screenHeight - location[1] - height + imgHeight;
                params.y = paramsY - (int) (8 * outMetrics.density);
            } else {
                params.gravity |= Gravity.TOP;
                promptDialog.dialog_prompt_top.setVisibility(View.VISIBLE);
                promptDialog.dialog_prompt_bottom.setVisibility(View.GONE);
                imgHeight = promptDialog.topHeight;
                params.y = location[1] + height - statusBarHeight - imgHeight;
            }
        } else if (code == EXPIRE_PROMPT) {
            promptDialog.iKnow.setVisibility(View.GONE);
            promptDialog.dialog_prompt_top.setVisibility(View.GONE);
            promptDialog.dialog_prompt_bottom.setVisibility(View.VISIBLE);
            promptDialog.dialog_prompt_all.setGravity(Gravity.RIGHT);
            int imgPaddingRight = d.getWidth() - location[0] - width / 2 - (int) (20 * outMetrics.density);
            promptDialog.dialog_prompt_bottom.setPadding(0, 0, imgPaddingRight, 0);
            params.width = (int) (d.getWidth() * 0.7);
            params.x = (int) (15 * outMetrics.density);
            params.y = d.getHeight() - location[1] + 10;
            params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        }
        dialogWindow.setAttributes(params);
    }
}
