package child.ryl.child.alibaba;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import child.ryl.child.R;
import child.ryl.child.adapter.HeaderViewRecyclerAdapter;

/**
 * 简讯列表适配器
 * 任玉林
 */
public class FilterAdapter extends HeaderViewRecyclerAdapter {
    private Context context;
    List<Map<String, Object>> mapList;
    private ItemClick itemClick;

    public ItemClick getItemClick() {
        return itemClick;
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public FilterAdapter(Context context, List<Map<String, Object>> mapList) {
        this.context = context;
        this.mapList = mapList;
    }

    @Override
    public int getItemViewCount() {
        return mapList == null ? 0 : mapList.size();
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
        View view = LayoutInflater.from(context).inflate(R.layout.ali_adapter_item, null);
        ItemViewHolder holder = new ItemViewHolder(view);
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
        final Map<String, Object> mapItem = mapList.get(position);
        final ItemViewHolder holder = (ItemViewHolder) viewHolder;

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 300);
//                        layoutParams.height = (int) (200 + (position % 15) * 10);

        holder.itemView.setLayoutParams(layoutParams);
        if (position == 0) {
            StaggeredGridLayoutManager.LayoutParams lp = new StaggeredGridLayoutManager.
                    LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setFullSpan(true);
            holder.itemView.setLayoutParams(lp);
            holder.itemView.setEnabled(false);
        } else if (position == 8) {
            StaggeredGridLayoutManager.LayoutParams lp = new StaggeredGridLayoutManager.
                    LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setFullSpan(true);
            holder.itemView.setLayoutParams(lp);
            holder.itemView.setEnabled(false);
        } else if (position == 35) {
            StaggeredGridLayoutManager.LayoutParams lp = new StaggeredGridLayoutManager.
                    LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setFullSpan(true);
            holder.itemView.setEnabled(false);
            holder.itemView.setLayoutParams(lp);
        } else {
            holder.itemView.setEnabled(true);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(false);
            }
        }
        holder.title.setText(Integer.toString(position));
        if (("1:" + position).equals(mapItem.get("po"))) {
            holder.title.setBackgroundColor(Color.parseColor("#000fff"));
        } else {
            holder.title.setBackgroundColor(Color.parseColor("#ff5200"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (("1:" + position).equals(mapItem.get("po"))) {
                    holder.title.setBackgroundColor(Color.parseColor("#ff5200"));
                    mapItem.put("po", "0:" + position);
                } else {
                    holder.title.setBackgroundColor(Color.parseColor("#000fff"));
                    mapItem.put("po", "1:" + position);
                }
                if (position < 8) {
                    itemClick.callData((String) mapItem.get("id"), position, 1);
                } else if (position < 35) {
                    itemClick.callData((String) mapItem.get("id"), position, 2);
                } else {
                    itemClick.callData((String) mapItem.get("id"), position, 3);
                }
            }
        });
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    interface ItemClick {
        void callData(String data, int position, int type);
    }
}