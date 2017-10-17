package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;

import child.ryl.child.R;

/**
 * 播放gif图片
 */
public class PlayGifActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_gif);
    }
}
