package child.ryl.child.update_photo.choosephotos;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import child.ryl.child.R;
import child.ryl.child.utils.DownloadPictureHelper;

public class ImageGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<ImageItem> dataList;

    private ImageItemKeeper keeper;
    private boolean isSelfList = false;//false已选择图片的页面 true相册跳转页面

    public ImageGridAdapter(Context context, List<ImageItem> list) {
        mContext = context;
        keeper = ImageItemKeeper.getInstance();
        if (list == null) {
            isSelfList = true;
            dataList = keeper.getWorkingList();
        } else {
            dataList = list;
        }
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
    public Object getItem(int position) {
        if (dataList != null) {
            return dataList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;

        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(mContext, R.layout.item_image_grid, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.image);
            holder.selected = (ImageView) convertView.findViewById(R.id.isselected);
            holder.textFrame = (TextView) convertView.findViewById(R.id.item_image_grid_text);
            holder.textIndex = (TextView) convertView.findViewById(R.id.item_image_index_text);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final ImageItem item = dataList.get(position);

        holder.iv.setTag(item.imagePath);
        if (item.isNetFile()) {
            DownloadPictureHelper.downPic(mContext, item.imagePath, holder.iv);
        } else {
            LocalBmpLoader.getInstance().bindBmp(holder.iv, item.thumbnailPath, item.imagePath);
        }

        if (isSelfList) {
            holder.selected.setImageResource(R.drawable.page_photo_btn_delete);
            if (item.isIndex) {
                holder.textIndex.setVisibility(View.VISIBLE);
                // previousHolder = holder;
            } else {
                holder.textIndex.setVisibility(View.GONE);
            }
            holder.selected.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    keeper.remove(position);
                    notifyDataSetChanged();
                }
            });
        } else {
            updateItemState(holder, item);
        }

        holder.iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isSelfList) {
                    keeper.setIndex(position);
                    holder.textIndex.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                } else {
                    if (!keeper.contains(item)) {
                        if (!keeper.add(item)) {
                            Toast.makeText(mContext, mContext.getString(R.string.only_selected) + keeper.size()
                                    + mContext.getString(R.string.pic_num), Toast.LENGTH_SHORT);
                        }
                    } else {
                        keeper.remove(item);
                        notifyDataSetChanged();
                    }
                    updateItemState(holder, item);
                }
            }

        });
        return convertView;
    }

    private void updateItemState(Holder holder, ImageItem item) {
        if (keeper.contains(item)) {
            holder.selected.setImageResource(R.drawable.icon_data_select);
            holder.textFrame.setBackgroundResource(R.drawable.bgd_relatly_line);
        } else {
            holder.selected.setImageResource(-1);
            holder.textFrame.setBackgroundColor(0x00000000);
        }
    }

    private class Holder {
        private ImageView iv;
        private ImageView selected;
        private TextView textFrame;
        private TextView textIndex;
    }
}
