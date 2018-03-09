package child.ryl.child.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import child.ryl.child.R;

/**
 * 圆图对话框
 */
public class SaveImageDialog extends Dialog implements View.OnClickListener {
    private TextView saveImage;
    private TextView uploadImage;
    private TextView dialog_animation;
    private String url;
    private Bitmap bitmap;
    private Context context;
    private Handler handlerAvatar;

    public SaveImageDialog(Context context, int themeResId, String url, Handler handlerAvatar) {
        super(context, themeResId);
        this.url = url;
        this.context = context;
        this.handlerAvatar = handlerAvatar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_save_image);
        saveImage = (TextView) findViewById(R.id.dialog_save_image_textView);
        saveImage.setText(R.string.save_image);
        uploadImage = (TextView) findViewById(R.id.dialog_upload_image_textView);
        uploadImage.setText(R.string.upload_image);
        dialog_animation = (TextView) findViewById(R.id.dialog_animation);
        dialog_animation.setText(R.string.jump_animation);
        saveImage.setOnClickListener(this);
        uploadImage.setOnClickListener(this);
        dialog_animation.setOnClickListener(this);
        //Bitmap bitmap = getLocalBitmap("/aa/aa.jpg"); //从本地取图片
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_save_image_textView:
                new Thread(networkTask).start();
                break;
            case R.id.dialog_upload_image_textView:
                handlerAvatar.sendEmptyMessage(0x122);
                dismiss();
                break;
            case R.id.dialog_animation:
                handlerAvatar.sendEmptyMessage(0x125);
                dismiss();
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //从网上取图片
            if (msg.what == 0x123) {
                dismiss();
                MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "title", "description");
                //Uri.fromFile(new File("/sdcard/Boohee/image.jpg"))
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        getUriForFile(context, "/sdcard/Boohee/image.jpg")));
                Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show();
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                    System.gc();
                }
            }
            super.handleMessage(msg);
        }
    };
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // 在这里进行 http request.网络请求相关操作
            bitmap = getHttpBitmap("http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg");
            handler.sendEmptyMessage(0x123);
//            Bitmap bm =((BitmapDrawable) ((ImageView) image).getDrawable()).getBitmap();
        }
    };

    public static Uri getUriForFile(Context context, String filePath) {
        Uri uri;
        String spack = context.getPackageName();
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context,
                    spack + ".fileprovider", new File(filePath));
        } else {
            uri = Uri.fromFile(new File(filePath));
        }
        return uri;
    }
//    下面就是保存的方法，传入参数就可以了：

//    public  void saveFile(Bitmap bm, String fileName, String path) throws IOException {
//        String subForder = SAVE_REAL_PATH + path;
//        File foder = new File(subForder);
//        if (!foder.exists()) {
//            foder.mkdirs();
//        }
//        File myCaptureFile = new File(subForder, fileName);
//        if (!myCaptureFile.exists()) {
//            myCaptureFile.createNewFile();
//        }
//        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
//        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
//        bos.flush();
//        bos.close();
//
//        //    这样就保存好了，可是有的时候明明保存下来了，为什么进入相册时查看不到呢？
//        // 反正我是遇到这样的问题的，原来我们在保存成功后，还要发一个系统广播通知手机有图片更新，广播如下：
//
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(myCaptureFile);
//        intent.setData(uri);
//        context.sendBroadcast(intent);//这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
//        Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show();
//    }

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        String path = String.valueOf(Environment.getExternalStorageDirectory());
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));

    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLocalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 从服务器取图片
     * http://bbs.3gstdy.com
     *
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

//    /**
//     * 判断sd卡是否存在
//     *
//     * @return
//     */
//    private boolean existSDCard() {
//        if (android.os.Environment.getExternalStorageState().equals(
//                android.os.Environment.MEDIA_MOUNTED)) {
//            return true;
//        } else
//            return false;
//    }


//    public static Bitmap getBitmapFromServer(String imagePath) {
//
//        HttpGet get = new HttpGet(imagePath);
//        HttpClient client = new DefaultHttpClient();
//        Bitmap pic = null;
//        try {
//            HttpResponse response = client.execute(get);
//            HttpEntity entity = response.getEntity();
//            InputStream is = entity.getContent();
//
//            pic = BitmapFactory.decodeStream(is);   // 关键是这句代码
//
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return pic;
//    }
//
//    public static Bitmap getBitmapFromServer(String imagePath) {
//
//        HttpGet get = new HttpGet(imagePath);
//        HttpClient client = new DefaultHttpClient();
//        Bitmap pic = null;
//        try {
//            HttpResponse response = client.execute(get);
//            HttpEntity entity = response.getEntity();
//            InputStream is = entity.getContent();
//
//            pic = BitmapFactory.decodeStream(is);   // 关键是这句代码
//
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return pic;
//    }

}

