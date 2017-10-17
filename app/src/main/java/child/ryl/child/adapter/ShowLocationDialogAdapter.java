package child.ryl.child.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import child.ryl.child.R;


public class ShowLocationDialogAdapter extends RecyclerView.Adapter<ShowLocationDialogAdapter.MyViewHolder> {
    private LayoutInflater mInflater;
    private boolean flag = false;//false到期 true
    private int[] imagesVip = {R.drawable.page_sindex_list_btn_fbcc, R.drawable.page_me_list_btn_fbesf,
            R.drawable.page_sindex_list_btn_fbsp, R.drawable.page_sindex_list_btn_czkc,
            R.drawable.page_sindex_list_btn_esfkc, R.drawable.page_sindex_list_btn_spkc,
            R.drawable.page_sindex_list_btn_czts, R.drawable.page_sindex_list_btn_esfts,
            R.drawable.page_sindex_list_btn_spts, R.drawable.page_sindex_list_btn_zd,
            R.drawable.page_sindex_list_btn_zfdd, R.drawable.page_sindex_list_btn_spqb};
    private int[] images = {R.drawable.page_sindex_list_btn_qxfbcc, R.drawable.page_me_list_btn_qxfbesf,
            R.drawable.page_sindex_list_btn_qxfbsp, R.drawable.page_sindex_list_btn_qxczkc,
            R.drawable.page_sindex_list_btn_qxesfkc, R.drawable.page_sindex_list_btn_qxspkc,
            R.drawable.page_sindex_list_btn_qxczts, R.drawable.page_sindex_list_btn_qxesfts,
            R.drawable.page_sindex_list_btn_qxspts, R.drawable.page_sindex_list_btn_qxzd,
            R.drawable.page_sindex_list_btn_qxzfdd, R.drawable.page_sindex_list_btn_qxspqb};

    public interface OnItemClickListener {
        void onItemClick(View view, int height, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickListener = mOnItemClickLitener;
    }


    public ShowLocationDialogAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(
                R.layout.adapter_home_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (!flag) {
            holder.imageView.setImageResource(images[position]);
        } else {
            holder.imageView.setImageResource(imagesVip[position]);
        }
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, holder.imageView.getHeight(), pos);
                }
            });

            holder.itemView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 12;
    }

//    public void removeData(int position) {
//        mDatas.remove(position);
//        notifyItemRemoved(position);
//    }

    class MyViewHolder extends ViewHolder {
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.adapter_home_image);
        }
    }
}