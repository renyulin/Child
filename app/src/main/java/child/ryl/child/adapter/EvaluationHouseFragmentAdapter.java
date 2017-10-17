package child.ryl.child.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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
import child.ryl.child.video.AudioPlayer;

public class EvaluationHouseFragmentAdapter extends HeaderViewRecyclerAdapter {
    private Context context;
    private List<Map<String, Object>> mItems;
    private AudioPlayer player;

    public EvaluationHouseFragmentAdapter(Context context, List<Map<String, Object>> items) {
        this.context = context;
        this.mItems = items;
        if (player == null) {
            player = AudioPlayer.getHelper(context);
        }
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
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_evaluation_item, null);
        EvaluationListHolder holder = new EvaluationListHolder(view);
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
        final Map<String, Object> json = mItems.get(position);
        final EvaluationListHolder holder = (EvaluationListHolder) viewHolder;
        holder.title.setText((String) json.get(Keys.AUDIO_TITLE));
        final String audioPath = (String) json.get(Keys.AUDIO_PATH);
        final String audioId = (String) json.get(Keys.AUDIO_ID);
        if (player.getAudioID().equals(audioId)) {
            holder.title.setTextColor(Color.parseColor("#FF5200"));
            holder.img.setImageResource(R.drawable.page_index_img_celect);
        } else {
            holder.title.setTextColor(Color.parseColor("#000000"));
            holder.img.setImageResource(R.drawable.page_index_img_ncelect);
        }
        holder.playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.playClick(audioId, audioPath, mItems, position);
            }
        });
    }

    public class EvaluationListHolder extends RecyclerView.ViewHolder {
        TextView title;
        LinearLayout playAudio;
        ImageView img;

        public EvaluationListHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.fragment_evaluation_title);
            playAudio = (LinearLayout) itemView.findViewById(R.id.fragment_evaluation_play_audio);
            img = (ImageView) itemView.findViewById(R.id.fragment_evaluation_item_img);
        }
    }
}
