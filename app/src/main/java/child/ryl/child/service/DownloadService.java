package child.ryl.child.service;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class DownloadService extends Service {
    public final static String URL_KEY = "url";
    public final static String APP_NAME = "appName";
    public final static String TYPE_IM = "IM";
    private DownloadManager downloadManager;
    private BroadcastReceiver receiver;

    Map<String, Long> downloadMap = new HashMap<>();

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return 0;
        }
        final String url = intent.getStringExtra(URL_KEY);
        if (!TextUtils.isEmpty(url) && url.startsWith("http")) {

        } else {
            Toast.makeText(this, "找不到下载项", Toast.LENGTH_SHORT).show();
            stopSelf();
            return 0;
        }
        if (downloadMap.get(url) != null) {
            Toast.makeText(this, "正在下载", Toast.LENGTH_SHORT).show();
            return 0;
        }
        String filename = intent.getStringExtra(APP_NAME);//IMversion
        if (!filename.endsWith(".apk")) {
            filename = filename + ".apk";
        }
        final String filePath = getFullFilePath(filename);
        final File file = new File(filePath);///storage/emulated/0/Android/data/child.ryl.child/files/Download/IMversion.apk
        if (file.exists()) {
//            file.delete();
//            return 0;
//            open(file);
            open(filePath, DownloadService.this);
            stopSelf();//停止服务
            return 0;
        }
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);
        request.setMimeType("application/vnd.android.package-archive");
        request.setTitle("卖房通");
        request.setDescription("正在下载更新文件");
        request.setDestinationUri(Uri.fromFile(file));
        final long id = downloadManager.enqueue(request);
        downloadMap.put(url, id);
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        Toast.makeText(this, "正在下载", Toast.LENGTH_SHORT).show();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (id == reference) {
                    Toast.makeText(DownloadService.this, "下载完成", Toast.LENGTH_SHORT).show();
                    downloadMap.remove(url);
//                    open(filePath);
                    open(filePath, DownloadService.this);
                }
            }
        };
        registerReceiver(receiver, filter);
        return super.onStartCommand(intent, flags, startId);
    }

    public void open(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        this.startActivity(intent);
        stopSelf();
    }

    String getFullFilePath(String file) {
//        String sdcard = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath();
        String sdcard = getRootPath();
        return sdcard + "/" + file;
    }

    public static String getRootPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath(); // filePath:  /sdcard/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data"; // filePath:  /data/data/
        }
    }

    public void open(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
        this.startActivity(intent);//getUriForFile(this,file)
        stopSelf();
    }

    public void open(String file, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setDataAndType(getUriForFile(this, file), "application/vnd.android.package-archive");
        context.startActivity(intent);
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    public static void startActionFile(
            Context context, String file, String contentType) throws ActivityNotFoundException {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//增加读写权限
        intent.setDataAndType(getUriForFile(context, file), contentType);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

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
}
