package child.ryl.child.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;

import child.ryl.child.MainActivity;
import child.ryl.child.R;
import child.ryl.child.my_view.BadgeView;
import child.ryl.child.my_view.CircleImageView;
import child.ryl.child.service.DownloadService;
import child.ryl.child.test.TestActivity;

/**
 * 启动页
 */
public class StartupHomepageActivity extends Activity implements View.OnLongClickListener {
    private LinearLayout start_up;
    private CircleImageView circle_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_homepage);
        init();
        initDate();
        new Thread(networkTask).start();
    }

    private void initDate() {
        start_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartupHomepageActivity.this, TestActivity.class));
            }
        });
    }

    BadgeView mBadgeViewForChat;

    private void init() {
        circle_img = (CircleImageView) findViewById(R.id.circle_img);
        circle_img.setOnLongClickListener(this);
        start_up = (LinearLayout) findViewById(R.id.start_up);
        if (mBadgeViewForChat != null) {
            start_up.removeView(mBadgeViewForChat);
        }
        mBadgeViewForChat = new BadgeView(this);
//        mBadgeViewForChat.setBadgeGravity(Gravity.LEFT);
        mBadgeViewForChat.setText("a13");
        mBadgeViewForChat.setTargetView(start_up);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                StartupHomepageActivity.this.startActivity(new
                        Intent(StartupHomepageActivity.this, MainActivity.class));
                StartupHomepageActivity.this.finish();
            }
            super.handleMessage(msg);
        }
    };

    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0x123);
        }
    };

    private void update() {
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra(DownloadService.APP_NAME, DownloadService.TYPE_IM + "version");
        intent.putExtra(DownloadService.URL_KEY, "http://download.fangxiaoer.com/download/agencyim.apk");
        startService(intent);
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.circle_img:
                update();
                break;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }
    }

}
