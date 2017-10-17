package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import child.ryl.child.R;
import child.ryl.child.my_view.SwipeView;

/**
 * listView侧拉
 */
public class SidePullActivity extends Activity {

    private ListView mListView;

    private ArrayList<String> mData;

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_pull);
        mListView = (ListView) findViewById(R.id.side_pull_listView);
        initData();

        adapter = new MyAdapter();
        mListView.setAdapter(adapter);

    }

    /**
     * 初始化假数据
     */
    private void initData() {
        mData = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            mData.add(i + "");
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(SidePullActivity.this).inflate(R.layout.swipeview, null);
                holder.content = (LinearLayout) convertView.findViewById(R.id.content);
                holder.content_text = (TextView) convertView.findViewById(R.id.content_text);
                holder.swipe = (LinearLayout) convertView.findViewById(R.id.menu);

                holder.swipeDelete = (TextView) convertView.findViewById(R.id.menu_delete);
                holder.swipeDelete.setOnClickListener(mOnClick);

                holder.favorite = (TextView) convertView.findViewById(R.id.menu_favorite);
                holder.favorite.setOnClickListener(listener);

                holder.swipeView = (SwipeView) convertView;
                // 设置左滑删掉的点击监听
                holder.swipeView.setOnSwipeChangeListener(mOnSwipe);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.content_text.setText(mData.get(position));

            holder.swipeDelete.setText("删除");
            //保存下标
            holder.swipeDelete.setTag(position);

            holder.favorite.setText("收藏");
            holder.favorite.setTag(position);
            return convertView;
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();
            Log.e("aaa", position + "aaa");
            adapter.notifyDataSetChanged();
            Toast.makeText(SidePullActivity.this, "已成功将第" + (position + 1) + "加入收藏夹", Toast.LENGTH_SHORT).show();

        }
    };
    /**
     * 左滑删掉
     */
    private SwipeView openedSwipeView;
    /**
     * 左滑删除监听
     */
    private SwipeView.OnSwipeChangeListener mOnSwipe = new SwipeView.OnSwipeChangeListener() {

        @Override
        public void onOpen(SwipeView swipeView) {
            // 保存swipView
            openedSwipeView = swipeView;
        }

        @Override
        public void onDown(SwipeView swipeView) {
            if (openedSwipeView != null && openedSwipeView != swipeView) {
                openedSwipeView.close();
            }
        }

        @Override
        public void onClose(SwipeView swipeView) {
            if (openedSwipeView != null && openedSwipeView == swipeView) {
                openedSwipeView = null;
            }
        }
    };
    /**
     * 删除点击监听
     */
    private View.OnClickListener mOnClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //取出保存的下标
            int position = (Integer) v.getTag();
            Log.e("aaa", position + "");
            mData.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(SidePullActivity.this, "删除数据", Toast.LENGTH_SHORT).show();
        }
    };

    static class ViewHolder {
        LinearLayout content;
        TextView content_text;
        TextView favorite;
        LinearLayout swipe;
        TextView swipeDelete;
        SwipeView swipeView;
    }
}

