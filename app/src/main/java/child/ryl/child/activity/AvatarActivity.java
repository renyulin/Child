package child.ryl.child.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import child.ryl.child.R;
import child.ryl.child.dialog.SaveImageDialog;

/**
 * 头像
 */
public class AvatarActivity extends Activity implements View.OnClickListener, View.OnLongClickListener {
    private ImageView imageView;
    private RelativeLayout relativeLayout;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        imageView = (ImageView) findViewById(R.id.activity_avatar_image);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_avatar_relativeLayout);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        imageHttp(url);//第一种方法
        getIntent().putExtra("urlBack", url);
        setResult(123, getIntent());
        relativeLayout.setOnClickListener(this);
        relativeLayout.setOnLongClickListener(this);
    }

    Handler handlerAvatar = new Handler() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x122) {
                Toast.makeText(AvatarActivity.this, "ssss", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 0x125) {
                Intent i = new Intent(AvatarActivity.this, ImgActivity.class);
                AvatarActivity.this.startActivity(i, ActivityOptions.makeSceneTransitionAnimation(
                        AvatarActivity.this, imageView, "basic").toBundle());
            }
            super.handleMessage(msg);
        }
    };

    //运用imageLoader从网络获取图片
    private void imageHttp(String url) {
        ImageLoader imageLoader;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        imageLoader.displayImage(url, imageView);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public boolean onLongClick(View v) {
        SaveImageDialog dialog = new SaveImageDialog(this, R.style.MyDialog, url, handlerAvatar);
        dialog.show();
        return true;
    }
}
