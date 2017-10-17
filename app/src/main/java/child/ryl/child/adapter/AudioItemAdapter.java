package child.ryl.child.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import child.ryl.child.R;
import child.ryl.child.utils.Keys;
import child.ryl.child.utils.Utils;
import child.ryl.child.video.AudioPlayer;

/**
 * 简讯列表适配器
 * 任玉林
 */
public class AudioItemAdapter extends HeaderViewRecyclerAdapter {
    private Context context;
    private List<Map<String, Object>> mItems;
    private int mSelect = -1;
    private String status;
    private String classify = "a";
    private AudioPlayer player;

    public AudioItemAdapter(Context context, List<Map<String, Object>> items) {
        this.context = context;
        this.mItems = items;
        player = AudioPlayer.getHelper(context);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int getItemViewCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public int getHeaderViewCount() {
        return 0;
    }

    @Override
    public int getFooterViewCount() {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.audio_item, null);
        AudioItemAdapter.ItemViewHolder holder = new AudioItemAdapter.ItemViewHolder(view);
        return holder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final Map<String, Object> map = mItems.get(position);
        final ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        final String title = (String) map.get(Keys.AUDIO_TITLE);
        final String audioPath = (String) map.get(Keys.AUDIO_PATH);
        final String audioId = (String) map.get(Keys.AUDIO_ID);
        String updateTime = (String) map.get(Keys.AUDIO_UPDATE_TIME);
        updateTime = updateTime.replace(".", "/");
        String categoryName = (String) map.get("CategoryName");
        String audioLength = (String) map.get("AudioLength");
        int time = Utils.stringToInt(audioLength, 70);
        String audioTime = Utils.secondToMinutes(time);
        itemViewHolder.audio_item_label_title.setText(updateTime);
        itemViewHolder.audio_item_title.setText(title);
        itemViewHolder.audio_item_label.setText(categoryName);
        itemViewHolder.audio_item_time.setText(audioTime);
        if (!TextUtils.isEmpty(status)) {
            if (position == 0) {
                itemViewHolder.audio_item_label_title.setText(categoryName);
                itemViewHolder.audio_item_label_title.setVisibility(View.VISIBLE);
            } else {
                itemViewHolder.audio_item_label_title.setVisibility(View.GONE);
            }
        } else {
            if (position == 0) {
                itemViewHolder.audio_item_label_title.setText(updateTime);
                itemViewHolder.audio_item_label_title.setVisibility(View.VISIBLE);
                classify = updateTime;
            } else {
                if (!classify.equals(updateTime)) {
                    classify = updateTime;
                    itemViewHolder.audio_item_label_title.setText(updateTime);
                    itemViewHolder.audio_item_label_title.setVisibility(View.VISIBLE);
                } else {
                    itemViewHolder.audio_item_label_title.setVisibility(View.GONE);
                }
            }
        }
        if (player.getAudioID().equals(audioId)) {
            itemViewHolder.audio_item_title.setTextColor(Color.parseColor("#ff5200"));
            if (player.isPlaying()) {
                itemViewHolder.audio_item_img.setImageResource(R.drawable.page_xesf_btn_bf_list);
            } else {
                itemViewHolder.audio_item_img.setImageResource(R.drawable.page_xesf_btn_zt_list);
            }
        } else {
            itemViewHolder.audio_item_title.setTextColor(Color.parseColor("#000000"));
            itemViewHolder.audio_item_img.setImageResource(R.drawable.page_xesf_btn_zt_list);
        }
        itemViewHolder.audio_item_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setCategoryName(status);
                //播放音乐 id相同未播放->继续播放 id相同正在播放->停止播放 id不同未播放->播放
                player.playClick(audioId, audioPath, mItems, position);
            }
        });
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView audio_item_label_title;
        TextView audio_item_title;
        TextView audio_item_label;
        TextView audio_item_time;
        ImageView audio_item_img;
        LinearLayout audio_item_ll;

        public ItemViewHolder(View itemView) {
            super(itemView);
            audio_item_label_title = (TextView) itemView.findViewById(R.id.audio_item_label_title);
            audio_item_title = (TextView) itemView.findViewById(R.id.audio_item_title);
            audio_item_label = (TextView) itemView.findViewById(R.id.audio_item_label);
            audio_item_time = (TextView) itemView.findViewById(R.id.audio_item_time);
            audio_item_img = (ImageView) itemView.findViewById(R.id.audio_item_img);
            audio_item_ll = (LinearLayout) itemView.findViewById(R.id.audio_item_ll);
        }
    }
}