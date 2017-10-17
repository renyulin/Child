package child.ryl.child.update_photo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import child.ryl.child.R;
import child.ryl.child.my_view.CircleImageView;
import child.ryl.child.update_photo.choosephotos.ChoosePhotosActivity;
import child.ryl.child.update_photo.choosephotos.ImageItem;
import child.ryl.child.update_photo.choosephotos.ImageItemKeeper;
import child.ryl.child.update_photo.choosephotos.LocalBmpLoader;
import child.ryl.child.update_photo.manager.FileManager;
import child.ryl.child.utils.RequestHelper;

/**
 * 个人资料
 */
public class UpdateInfoActivity extends Activity implements View.OnClickListener {
    protected final static int USER_PHOTOTS = 4;
    protected final static int USER_PICTURE = 5;
    private CircleImageView iv_user_head;
    String sessionId;
    private String mUploadedImageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_date);
        sessionId = "d9c469c0e2ebb640af81d48d2f0a8666";
        iv_user_head = (CircleImageView) findViewById(R.id.iv_user_head);
        findViewById(R.id.btn_personal_head).setOnClickListener(this);
    }

    protected void switchImageKeeper(int type) {
        ImageItemKeeper.getInstance().switchWorkList(type);
    }

    @Override
    public void onClick(View v) {
        Intent in;
        switch (v.getId()) {
            case R.id.btn_personal_head:
                PromptHelper.showPhotoWay(UpdateInfoActivity.this,
                        getString(R.string.please_select_way), new PromptHelper.OnDialogClick() {
                            @Override
                            public void dialogInnerSure() {
                                switchImageKeeper(ImageItemKeeper.INDEX_REVIEWS);
                                Intent i = new Intent(UpdateInfoActivity.this, ChoosePhotosActivity.class);
                                startActivityForResult(i, USER_PHOTOTS);
                            }

                            @Override
                            public void dialogInnerBack() {
                                switchImageKeeper(ImageItemKeeper.INDEX_REVIEWS);
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, USER_PICTURE);
                            }
                        });
                break;
        }
    }

    protected void prepareFormFiles(Context context) {
        if (ImageItemKeeper.getInstance().size() > 0 && TextUtils.isEmpty(mUploadedImageUrls)) {
            final Dialog dialog = PromptHelper.createLoadingDialog(this, "");
            dialog.show();
            File[] files = ImageItemKeeper.getInstance().generateCompressedFileArray(this);
            if (files.length > 0) {
                RequestHelper.postImages(this, files, "qt", sessionId, new RequestHelper.RequestCallback() {

                    @Override
                    public void doSuccess(Map<String, Object> jsonMap) {
                        dialog.dismiss();
                        mUploadedImageUrls = (String) jsonMap.get("result");
                        mUploadedImageUrls = ImageItemKeeper.getInstance().
                                regenerateUploadedString(mUploadedImageUrls, "qt");
                        covertAndSendUploadedUrls(mUploadedImageUrls);
                    }

                    @Override
                    public void doFail(int result) {
                        dialog.dismiss();
                    }
                });
            } else {
                mUploadedImageUrls = ImageItemKeeper.getInstance().regenerateUploadedString(mUploadedImageUrls,
                        "qt");
                covertAndSendUploadedUrls(mUploadedImageUrls);
            }
        } else if (!TextUtils.isEmpty(mUploadedImageUrls)) {
            covertAndSendUploadedUrls(mUploadedImageUrls);
        } else {

        }
    }

    protected void covertAndSendUploadedUrls(String uploadedImageUrl) {
        if (TextUtils.isEmpty(uploadedImageUrl)) {
            return;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pic", mUploadedImageUrls);
        try {
            String defaultPic = mUploadedImageUrls.split(";")[ImageItemKeeper.getInstance().getIndexPosition()];
            map.put("defaultPic", defaultPic);
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Override
    protected void onDestroy() {
        ImageItemKeeper.getInstance().clearAll();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case USER_PICTURE:
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    iv_user_head.setImageBitmap(bitmap);
                    ImageItem item = new ImageItem();
                    item.imagePath = FileManager.saveImageFiles(this, bitmap);
                    if (ImageItemKeeper.getInstance() != null) {
                        ImageItemKeeper.getInstance().clearAll();
                    }
                    ImageItemKeeper.getInstance().add(item);
                    break;
                default:
            }
            prepareFormFiles(this);
        }

        if (requestCode == USER_PHOTOTS) {
            int mun = ImageItemKeeper.getInstance().size();
            if (mun > 0) {
                ImageItem indexImage = ImageItemKeeper.getInstance().getIndexItem();
                LocalBmpLoader.getInstance().bindBmp(iv_user_head,
                        indexImage.thumbnailPath, indexImage.imagePath);
                prepareFormFiles(this);
            }
        }
    }
}
