package child.ryl.child.update_photo.choosephotos;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import child.ryl.child.R;

public class ImageBucketAdapter extends BaseAdapter {
    final String TAG = getClass().getSimpleName();

    Activity act;
    /**
     * 图片集列表
     */
    List<ImageBucket> dataList;

    public ImageBucketAdapter(Activity act, List<ImageBucket> list) {
        this.act = act;
        dataList = list;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (dataList != null) {
            count = dataList.size();
        }
        return count;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    class Holder {
        private ImageView iv;
        private ImageView selected;
        private TextView name;
        private TextView count;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        Holder holder;
        if (arg1 == null) {
            holder = new Holder();
            arg1 = View.inflate(act, R.layout.item_image_bucket, null);
            holder.iv = (ImageView) arg1.findViewById(R.id.image);
            holder.selected = (ImageView) arg1.findViewById(R.id.isselected);
            holder.name = (TextView) arg1.findViewById(R.id.name);
            holder.count = (TextView) arg1.findViewById(R.id.count);
            arg1.setTag(holder);
        } else {
            holder = (Holder) arg1.getTag();
        }
        ImageBucket item = dataList.get(arg0);
        holder.count.setText("" + item.count);
        holder.name.setText(item.bucketName);
        holder.selected.setVisibility(View.GONE);
        if (item.imageList != null && item.imageList.size() > 0) {
            String thumbPath = item.imageList.get(0).thumbnailPath;
            String sourcePath = item.imageList.get(0).imagePath;
            holder.iv.setTag(sourcePath);
            LocalBmpLoader.getInstance().bindBmp(holder.iv, thumbPath, sourcePath);
        } else {
            holder.iv.setImageBitmap(null);
            Log.e(TAG, "no images in bucket " + item.bucketName);
        }
        return arg1;
    }

}
