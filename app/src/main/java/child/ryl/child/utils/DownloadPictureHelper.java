package child.ryl.child.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import child.ryl.child.R;

/**
 * picasso加载图片
 */
public class DownloadPictureHelper {

    public static void downPic(Context context, String url, ImageView v) {
        if (TextUtils.isEmpty(url)) {
            v.setImageResource(R.drawable.conan);
            return;
        }
        Picasso.with(context).load(url).placeholder(R.drawable.conan).error(R.drawable.ic_launcher).into(v);
    }

    public static void downWelcomPic(Context context, String url, ImageView v) {
        if (TextUtils.isEmpty(url)) {
            v.setImageResource(R.drawable.conan);
            return;
        }
        Picasso.with(context).load(url).into(v);
    }
}
