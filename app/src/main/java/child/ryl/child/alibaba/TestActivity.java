/*
 * MIT License
 *
 * Copyright (c) 2016 Alibaba Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package child.ryl.child.alibaba;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import child.ryl.child.R;

/**
 * alibaba
 */
public class TestActivity extends Activity implements FilterAdapter.ItemClick {
    FilterAdapter filterAdapter;
    List<Map<String, Object>> mapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_view);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

//        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText position = (EditText) findViewById(R.id.position);
//                if (!TextUtils.isEmpty(position.getText())) {
//                    try {
//                        int pos = Integer.parseInt(position.getText().toString());
//                        recyclerView.scrollToPosition(pos);
//                    } catch (Exception e) {
//                        Log.e("VlayoutActivity", e.getMessage(), e);
//                    }
//                }
//            }
//        });


//        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                outRect.set(4, 4, 4, 4);
//            }
//        });
        recyclerView.setItemAnimator(null);
        mapList = new ArrayList<>();
        String[] names = new String[]{"特色", "抢优惠", "地铁沿线", "优质学府", "低首付", "现房",
                "精装房", "品牌房企", "类型", "普宅", "洋房", "商业", "公寓", "别墅"};
        String[] ids = new String[]{"", "isDicount", "isSubWay", "isSchoolArea",
                "isPayment", "existinfo", "isDecorate", "isBrandHouse", "", "1", "2", "3", "4", "5"};

        for (int i = 0; i < names.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", names[i]);
            map.put("id", ids[i]);
            mapList.add(map);
        }
        filterAdapter = new FilterAdapter(this, mapList);
        recyclerView.setAdapter(filterAdapter);
        filterAdapter.setItemClick(this);
//        recyclerView.setAdapter(
//                new RecyclerView.Adapter() {
//                    @Override
//                    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////                        TextView view = (TextView) LayoutInflater.from(TestActivity.this).inflate(R.layout.ali_adapter_item, parent, false);
////                        FrameLayout frameLayout = new FrameLayout(TestActivity.this);
//                        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(TestActivity.this).inflate(R.layout.ali_adapter_item, parent, false);
////                        frameLayout.addView(view);
//                        return new MainViewHolder(frameLayout);
//                    }
//
//                    @Override
//                    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//                                ViewGroup.LayoutParams.MATCH_PARENT, 300);
////                        layoutParams.height = (int) (200 + (position % 15) * 10);
//
//                        holder.itemView.findViewById(R.id.title).setLayoutParams(layoutParams);
//                        if (position == 39) {
//                            StaggeredGridLayoutManager.LayoutParams lp = new StaggeredGridLayoutManager.
//                                    LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                                    ViewGroup.LayoutParams.WRAP_CONTENT);
//                            lp.setFullSpan(true);
//                            holder.itemView.setLayoutParams(lp);
//                        } else {
//                            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
//                            if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
//                                ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(false);
//                            }
//                        }
//                        ((TextView) holder.itemView.findViewById(R.id.title)).
//                                setText(Integer.toString(position));
//                        holder.itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                holder.itemView.setBackgroundColor(Color.parseColor("#000fff"));
//                            }
//                        });
//                    }
//
//                    @Override
//                    public int getItemCount() {
//                        return 30;
//                    }
//                });
        maps.add(type1);
        maps.add(type2);
        maps.add(type3);
    }

    private Map<Integer, String> type1 = new HashMap<>();
    private Map<Integer, String> type2 = new HashMap<>();
    private Map<Integer, String> type3 = new HashMap<>();
    private List<Map<Integer, String>> maps = new ArrayList<>();
    Map<String, Object> moreMapBack = new HashMap<>();

    @Override
    public void callData(String data, int position, int type) {
        if (type == 1) {
            if (!"1".equals(moreMapBack.get(data))) {
                moreMapBack.put(data, "1");
            } else {
                moreMapBack.put(data, "");
            }
        } else if (type == 2) {
            if (data.equals(moreMapBack.get("projectType"))) {
                moreMapBack.put("projectType","");
            }else {
                moreMapBack.put("projectType",data);
            }
        }
        if (maps.get(type - 1).containsKey(position)) {
            maps.get(type - 1).remove(position);
        } else {
            if (type == 2) {
                Map<Integer, String> map = maps.get(type - 1);
                for (Integer key : map.keySet()) {//单选，移除状态
                    maps.get(type - 1).remove(key);
                    Map<String, Object> item = mapList.get(key);
                    item.put("po", "0:" + key);
                }
                filterAdapter.notifyDataSetChanged();
            }
            maps.get(type - 1).put(position, data);
        }
        Log.e("dddddddddddddd",moreMapBack.toString());
    }

//    class MainViewHolder extends RecyclerView.ViewHolder {
//
//        public MainViewHolder(View itemView) {
//            super(itemView);
//        }
//    }

}
