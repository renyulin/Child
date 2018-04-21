package child.ryl.child.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
 * 图片存取
 */
public class AccessActivity extends Activity implements View.OnClickListener {
    private Button storage;
    private Button extraction;
    private ImageView image;
    private Bitmap bitmap;
    private Bitmap pic;
    private File mFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);
        storage = (Button) findViewById(R.id.activity_access_storage);
        extraction = (Button) findViewById(R.id.activity_access_extraction);
        image = (ImageView) findViewById(R.id.activity_access_imageView);
        storage.setOnClickListener(this);
        extraction.setOnClickListener(this);
        Log.e("e","g:"+getClass().getSimpleName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_access_storage:
                new Thread(networkTask).start();
                break;
            case R.id.activity_access_extraction:
//                if (mFile == null) {
//                    Toast.makeText(this, "mFile==null", Toast.LENGTH_SHORT).show();
//                } else {
//                    pic = getLocalBitmap(mFile);
//                    image.setImageBitmap(pic);
//                }
                pic = getLocalBitmap(mFile);
                if (pic == null) {
                    Toast.makeText(this, "图片不存在", Toast.LENGTH_SHORT).show();
                } else {
                    image.setImageBitmap(pic);
                }
                break;
        }
    }

    /**
     * 加载本地图片
     *
     * @param file
     * @return
     */
    public static Bitmap getLocalBitmap(File file) {
        Log.i("aaaa", file + "");
        try {
            FileInputStream fis = new FileInputStream("/storage/emulated/0/Boohee/img.jpg");
//            FileInputStream fis = new FileInputStream(
// Environment.getExternalStorageDirectory()+"/Boohee/img.jpg");
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 从服务器取图片
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //从网上取图片
            if (msg.what == 0x123) {
//                MediaStore.Images.Media.insertImage(AccessActivity.this.getContentResolver(), bitmap, "title", "description");
//                AccessActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File("/sdcard/Boohee/image.jpg"))));

                mFile = saveImage(bitmap);

                Toast.makeText(AccessActivity.this, "图片保存成功", Toast.LENGTH_SHORT).show();
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                    System.gc();
                }
            }
            super.handleMessage(msg);
        }
    };

    public static File saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
//        String fileName = System.currentTimeMillis() + ".jpg";
        String fileName = "img.jpg";
        File file = new File(appDir, fileName);
        Log.i("file-->", file.toString());
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("printStackTrace", e.toString());
        }
        return file;
    }

    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // 在这里进行 http request.网络请求相关操作
            bitmap = getHttpBitmap("http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg");
            handler.sendEmptyMessage(0x123);
        }
    };
}
