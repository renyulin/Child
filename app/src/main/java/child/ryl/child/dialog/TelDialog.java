package child.ryl.child.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

import child.ryl.child.R;

/**
 * 拨打电话提示
 */
public class TelDialog extends Dialog implements View.OnClickListener {
    private TextView dialog_tel_sure;//确定
    private TextView dialog_tel_cancel;//取消
    private TextView dialog_tel_content;//内容
    private String content = "";
    private Context context;
    private int status;
    private String sure;//确定键
    private Handler handler;
    private String mSessionId;
    private Map<String, Object> map;

    public TelDialog(Context context) {
        super(context);
        this.context = context;
    }

    public TelDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    /**
     * 只设定内容
     *
     * @param content
     */
    public void setContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            this.content = content;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tel);
        initDate();
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        dialog_tel_sure = (TextView) findViewById(R.id.dialog_tel_sure);
        dialog_tel_cancel = (TextView) findViewById(R.id.dialog_tel_cancel);
        dialog_tel_content = (TextView) findViewById(R.id.dialog_tel_content);
        dialog_tel_sure.setOnClickListener(this);
        dialog_tel_cancel.setOnClickListener(this);
        dialog_tel_content.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_tel_sure:
                phone();
                dismiss();
                break;
            case R.id.dialog_tel_cancel:
                dismiss();
                break;
        }
    }

    /**
     * 拨打电话
     */
    private void phone() {
        String telNumber = content.replace(context.getString(R.string.transfer), ",");
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telNumber));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
            return;
        }
        context.startActivity(intent);
    }
}
