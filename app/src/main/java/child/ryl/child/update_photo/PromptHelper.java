package child.ryl.child.update_photo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import child.ryl.child.R;

/**
 * Created by Administrator on 2017/3/22 0022.
 */
public class PromptHelper {
    public interface OnDialogClick {
        public void dialogInnerSure();

        public void dialogInnerBack();
    }

    private static CheckBox checkBox;

    /**
     * We can have only one loading dialog in the whole view So we must add
     * logic in future
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View content = inflater.inflate(R.layout.progress_dialog, null);
        TextView tipTextView = (TextView) content.findViewById(R.id.tipTextView);
        tipTextView.setText(msg);
        final Dialog loadingDlg = new Dialog(context, R.style.loading_dialog);
        loadingDlg.setCancelable(false);
        loadingDlg.setContentView(content, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return loadingDlg;
    }

    public static void showFailDialog(final Context context, final String tip, final String content, final String sureButton,
                                      final OnDialogClick callback) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_fail_textview, null);
        TextView tv_fail_content = (TextView) view.findViewById(R.id.tv_fail_update_content);
        tv_fail_content.setText(content);
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle(
                context.getString(R.string.SystemPrompt)).setMessage(tip)
                .setIcon(R.drawable.ic_maifangtong)
                .setPositiveButton(sureButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callback.dialogInnerSure();
                    }
                }).setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callback.dialogInnerBack();
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public static void showCompletionDialog(final Context context, final String tip, final String content, final String sureButton,
                                            final OnDialogClick callback) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_fail_textview, null);
        TextView tv_fail_content = (TextView) view.findViewById(R.id.tv_fail_update_content);
        tv_fail_content.setText(content);
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle(context.getString(R.string.SystemPrompt)).setMessage(tip).setIcon(R.drawable.ic_maifangtong)
                .setPositiveButton(sureButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callback.dialogInnerSure();
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public static void showSkipDialog(final Context context, final String tip, final String content, final OnDialogClick callback) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_update_checkbox, null);
        TextView tv_skip_content = (TextView) view.findViewById(R.id.tv_skip_update_content);
        tv_skip_content.setText(content);
        checkBox = (CheckBox) view.findViewById(R.id.btn_checkbox);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.SystemPrompt)).setMessage(tip).setIcon(R.drawable.ic_maifangtong).setView(view)
                .setPositiveButton(R.string.to_update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callback.dialogInnerSure();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkBox.isChecked()) {
                            SharedPreferences sharedPreferences = context.getSharedPreferences("prompt", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("key", false);
                            editor.putBoolean("version", false);
                            editor.commit();
                        }

                        dialog.dismiss();
                        callback.dialogInnerBack();
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showPhotoWay(final Context context, final String tip, final OnDialogClick callback) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.SystemPrompt)).setMessage(tip).setIcon(R.drawable.ic_maifangtong)
                .setPositiveButton(context.getString(R.string.album),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                callback.dialogInnerSure();
                            }
                        }).setNegativeButton(context.getString(R.string.camera),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                callback.dialogInnerBack();
                            }
                        }).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public static void showReturnDialog(final Activity activity, final String tip, final OnDialogClick callback) {
        final Dialog dialog = new android.app.Dialog(activity, R.style.Translucent_NoTitle);
        View v = activity.getLayoutInflater().inflate(R.layout.dialog_layout, null);
        final TextView tx = (TextView) v.findViewById(R.id.content);
        tx.setText(tip);
        Button finish = (Button) v.findViewById(R.id.finish_button);
        Button ok = (Button) v.findViewById(R.id.ok_button);
        finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callback.dialogInnerBack();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callback.dialogInnerSure();
            }
        });
        dialog.setContentView(v);
        dialog.show();
    }


    public static void showCountDownDialog(final Activity activity, final String tip, final OnDialogClick callback) {
        final Dialog dialog = new Dialog(activity, R.style.activity_dialog_style);
        View v = activity.getLayoutInflater().inflate(R.layout.connt_down, null);
        final TextView tx = (TextView) v.findViewById(R.id.content);
        Button button = (Button) v.findViewById(R.id.finish_button);
        final CountDownTimer timer = new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (tx != null) {
                    tx.setText(millisUntilFinished / 1000 + activity.getString(R.string.after_second) + tip);
                }
            }

            @Override
            public void onFinish() {
                dialog.dismiss();
                callback.dialogInnerSure();
            }
        }.start();
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timer.cancel();
                dialog.dismiss();
            }
        });
        dialog.setTitle(R.string.kindly_reminder);
        dialog.setContentView(v);
        dialog.show();
    }


    /**
     * Display a toast with long time
     *
     * @param context
     * @param message
     */
    public static void displayMessage(Context context, String message) {
        displayMessage(context, message, Toast.LENGTH_SHORT);
    }

    public static void displayMessage(Context context, int stringId) {
        String msg = context.getResources().getString(stringId);
        displayMessage(context, msg, Toast.LENGTH_SHORT);
    }

    /**
     * Display a toast with long time
     *
     * @param context
     * @param message
     * @param time
     */
    public static void displayMessage(Context context, String message, int time) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//        Toast toast = new Toast(context);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.custom_toast_layout, null);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
//                LayoutParams.WRAP_CONTENT);
//        view.setLayoutParams(lp);
//        TextView tv = (TextView) view.findViewById(R.id.toast_content);
//        toast.setView(view);
//        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 80);
//        toast.setDuration(time);
//        tv.setText(message);
//        toast.show();
    }
}
