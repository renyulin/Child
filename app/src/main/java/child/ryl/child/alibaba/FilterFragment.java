package child.ryl.child.alibaba;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.LinkedList;
import java.util.List;

import child.ryl.child.R;

/**
 * 筛选项：更多
 */
public class FilterFragment extends Fragment {
    private RecyclerView  filter_recycler;
    private Runnable trigger;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, null);
         filter_recycler = (RecyclerView) view.findViewById(R.id.fragment_filter_recycler);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(getActivity());
        filter_recycler.setLayoutManager(layoutManager);
        final DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);

        filter_recycler.setAdapter(delegateAdapter);
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        adapters.add(new VLayoutActivity.SubAdapter(getActivity(), new GridLayoutHelper(4), 4));

        GridLayoutHelper helper = new GridLayoutHelper(4);
//        helper.setAspectRatio(4f);
        //helper.setColWeights(new float[]{40, 20, 30, 30});
        helper.setMargin(10, 10, 10, 10);
//        helper.setGap(10);
        adapters.add(new VLayoutActivity.SubAdapter(getActivity(), new LinearLayoutHelper(), 10));
        adapters.add(new VLayoutActivity.SubAdapter(getActivity(), helper, 14)
//        {
//            @Override
//            public void onBindViewHolder(VLayoutActivity.MainViewHolder holder, int position) {
//                super.onBindViewHolder(holder, position);
//                VirtualLayoutManager.LayoutParams lp = (VirtualLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
//                // lp.bottomMargin = 1;
//                // lp.rightMargin = 1;
//            }
//        }
        );
        adapters.add(new VLayoutActivity.SubAdapter(getActivity(), new LinearLayoutHelper(), 10));
        delegateAdapter.setAdapters(adapters);

        final Handler mainHandler = new Handler(Looper.getMainLooper());

        trigger = new Runnable() {
            @Override
            public void run() {
                // recyclerView.scrollToPosition(22);
                // recyclerView.getAdapter().notifyDataSetChanged();
                filter_recycler.requestLayout();
                // mainHandler.postDelayed(trigger, 1000);
            }
        };

        mainHandler.postDelayed(trigger, 1000);
    }

    static class SubAdapter extends DelegateAdapter.Adapter<FilterFragment.FilterHolder> {

        private Context mContext;

        private LayoutHelper mLayoutHelper;

        private VirtualLayoutManager.LayoutParams mLayoutParams;
        private int mCount = 0;

        public SubAdapter(Context context, LayoutHelper layoutHelper, int count) {
            this(context, layoutHelper, count, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        }

        public SubAdapter(Context context, LayoutHelper layoutHelper,
                          int count, @NonNull VirtualLayoutManager.LayoutParams layoutParams) {
            this.mContext = context;
            this.mLayoutHelper = layoutHelper;
            this.mCount = count;
            this.mLayoutParams = layoutParams;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return mLayoutHelper;
        }

        @Override
        public FilterFragment.FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FilterFragment.FilterHolder(LayoutInflater.from(mContext).inflate(R.layout.ali_adapter_item, parent, false));
        }

        @Override
        public void onBindViewHolder(FilterFragment.FilterHolder holder, int position) {
            // only vertical
            holder.itemView.setLayoutParams(new VirtualLayoutManager.LayoutParams(mLayoutParams));
        }


        @Override
        protected void onBindViewHolderWithOffset(FilterFragment.FilterHolder holder, int position, int offsetTotal) {
            ((TextView) holder.itemView.findViewById(R.id.title)).setText(Integer.toString(offsetTotal));
        }

        @Override
        public int getItemCount() {
            return mCount;
        }
    }


    static class FilterHolder extends RecyclerView.ViewHolder {

        public static volatile int existing = 0;
        public static int createdTimes = 0;

        public FilterHolder(View itemView) {
            super(itemView);
            createdTimes++;
            existing++;
        }

        @Override
        protected void finalize() throws Throwable {
            existing--;
            super.finalize();
        }
    }
}
