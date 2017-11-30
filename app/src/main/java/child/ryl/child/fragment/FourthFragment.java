package child.ryl.child.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import child.ryl.child.R;
import child.ryl.child.activity.AvatarActivity;
import child.ryl.child.my_view.CircleImageView;
import child.ryl.child.my_view.SpringProgressView;
import child.ryl.child.update_photo.manager.FileManager;
import child.ryl.child.utils.Utils;

import static child.ryl.child.update_photo.manager.FileManager.getCameraPath;

public class FourthFragment extends Fragment implements View.OnClickListener {
    private View view;
    private CircleImageView image;
    private SpringProgressView progressView;
    private Random random = new Random(System.currentTimeMillis());
    private String filePhotoPath = "";//实际图片路径
    private Uri imgUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_fourth, null);
        }
        image = (CircleImageView) view.findViewById(R.id.circle_image);
//            image.setImageResource(R.drawable.lena);
        image.setOnClickListener(this);
        Button clear = (Button) view.findViewById(R.id.clear);
        Button camera = (Button) view.findViewById(R.id.camera);
        camera.setOnClickListener(this);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.deleteFile(getActivity().getExternalFilesDir("child.ryl.child"));
                Utils.smallToast(getActivity(), "清理成功");
            }
        });
        initView(view);
        return view;
    }

    private void initView(View view) {
        progressView = (SpringProgressView) view.findViewById(R.id.springProgressView);
        progressView.setMaxCount(1000.0f);
        progressView.setCurrentCount(random.nextInt(1000));
        view.findViewById(R.id.progress_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressView.setCurrentCount(random.nextInt(1000));
                Toast.makeText(getActivity(), "最大值：" + progressView.getMaxCount() +
                        "   当前值：" + progressView.getCurrentCount(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:
                dialog();
                break;
            default:
                Intent intent = new Intent();
                intent.setClass(getActivity(), AvatarActivity.class);
                String url = "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg";
                intent.putExtra("url", url);
                startActivityForResult(intent, 2);
                break;
        }
    }

    /**
     * 对话框
     */
    private void dialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Camera");
        dialog.setMessage("please select camera of type");
        dialog.setPositiveButton("缩略", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                camera(0);
            }
        });
        dialog.setNegativeButton("实体", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                camera(1);
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 123:
                imageHttp(data.getStringExtra("urlBack"));
                break;
        }
        switch (requestCode) {
            case 1:
                Bundle bundle = data.getExtras();
                Bitmap bit = (Bitmap) bundle.get("data");
                image.setImageBitmap(bit);
                break;
            case 3:
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getActivity().
                            getContentResolver().openInputStream(imgUri));
                    image.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        if (!TextUtils.isEmpty(filePhotoPath)) {
            Bitmap bitmap = FileManager.getLocalBitmap(filePhotoPath);
            if (bitmap != null) {
//                String path = FileManager.saveImageFiles(getActivity(), bitmap);
                image.setImageBitmap(bitmap);
            }
            filePhotoPath = null;
        }
    }

    //从网络获取图片
    private void imageHttp(String url) {
        ImageLoader imageLoader;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        imageLoader.displayImage(url, image);
    }

    /**
     * 打开相机
     */
    private void camera(int key) {
        switch (key) {
            case 0:
                /**
                 * 获取缩略图
                 */
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, 1);

                /**
                 * 获取照片大小不确定
                 */
                File outputImg = new File(getActivity().getExternalCacheDir(), "output.jpg");
                try {
                    if (outputImg.exists()) {
                        outputImg.delete();
                    }
                    outputImg.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imgUri = FileProvider.getUriForFile(getActivity(), "child.ryl.child.fileprovider", outputImg);
                } else {
                    imgUri = Uri.fromFile(outputImg);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                startActivityForResult(intent, 3);
                break;
            case 1:
                /**
                 * 获取实图
                 */
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                ContentValues contentValues = new ContentValues(2);
                filePhotoPath = getCameraPath(getActivity());//要保存照片的绝对路径

                contentValues.put(MediaStore.Images.Media.DATA, filePhotoPath);
                //如果想拍完存在系统相机的默认目录,改为
                //contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "111111.jpg");

                contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri mPhotoUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
                startActivityForResult(intent2, 2);
                break;
        }
    }
}
