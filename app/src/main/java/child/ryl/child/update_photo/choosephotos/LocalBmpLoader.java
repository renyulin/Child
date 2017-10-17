package child.ryl.child.update_photo.choosephotos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.SoftReference;
import java.util.HashMap;

public class LocalBmpLoader {

    public Handler h = new Handler();
    public final String TAG = getClass().getSimpleName();
    private HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();

    private static LocalBmpLoader instance;

    public static LocalBmpLoader getInstance() {
        if (instance == null) {
            instance = new LocalBmpLoader();
        }
        return instance;
    }

    private LocalBmpLoader() {
    }

    public void put(String path, Bitmap bmp) {
        if (!TextUtils.isEmpty(path) && bmp != null) {
            imageCache.put(path, new SoftReference<Bitmap>(bmp));
        }
    }

    private ImageCallback callback = new ImageCallback() {
        @Override
        public void imageLoad(ImageView imageView, Bitmap bitmap, Object... params) {
            if (imageView != null && bitmap != null) {
                String url = (String) params[0];
                if (url != null && url.equals(imageView.getTag())) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    Log.e(TAG, "callback, bmp not match");
                }
            } else {
                Log.e(TAG, "callback, bmp null");
            }
        }
    };

    public void bindBmp(final ImageView iv, final String thumbPath, final String sourcePath) {
        if (TextUtils.isEmpty(thumbPath) && TextUtils.isEmpty(sourcePath)) {
            Log.e(TAG, "no paths pass in");
            return;
        }

        final String path;
        final boolean isThumbPath;
        if (!TextUtils.isEmpty(thumbPath)) {
            path = thumbPath;
            isThumbPath = true;
        } else if (!TextUtils.isEmpty(sourcePath)) {
            path = sourcePath;
            isThumbPath = false;
        } else {
            return;
        }

        if (imageCache.containsKey(path)) {
            SoftReference<Bitmap> reference = imageCache.get(path);
            Bitmap bmp = reference.get();
            if (bmp != null) {
                if (callback != null) {
                    callback.imageLoad(iv, bmp, sourcePath);
                }
                iv.setImageBitmap(bmp);
                Log.d(TAG, "hit cache");
                return;
            }
        }
        iv.setImageBitmap(null);

        new Thread() {
            Bitmap thumb;

            public void run() {
                try {
                    if (isThumbPath) {
                        thumb = BitmapFactory.decodeFile(thumbPath);
                        if (thumb == null) {
                            thumb = BitmapTools.revitionImageSize(sourcePath);
                        }
                    } else {
                        thumb = BitmapTools.revitionImageSize(sourcePath);
                    }
                } catch (Exception e) {
                }
                if (thumb == null) {
                    thumb = ChoosePhotosActivity.bimap;
                }
                Log.e(TAG, "-------thumb------" + thumb);
                put(path, thumb);

                if (callback != null) {
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.imageLoad(iv, thumb, sourcePath);
                        }
                    });
                }
            }
        }.start();

    }

    public interface ImageCallback {
        void imageLoad(ImageView imageView, Bitmap bitmap, Object... params);
    }
}
