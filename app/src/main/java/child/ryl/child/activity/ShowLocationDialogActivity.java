package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import child.ryl.child.R;
import child.ryl.child.adapter.ShowLocationDialogAdapter;
import child.ryl.child.dialog.DialogShow;
import child.ryl.child.dialog.PromptDialog;
import child.ryl.child.utils.Utils;

/**
 * recycleView配合对话框进行位置展示
 */
public class ShowLocationDialogActivity extends Activity implements View.OnClickListener {
    private ImageView fragment_home_expire;
    private RecyclerView recyclerView;
    private ShowLocationDialogAdapter adapter;
    private boolean flag = false;
    private PromptDialog promptDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location);
        fragment_home_expire = (ImageView) findViewById(R.id.fragment_home_expire);
        fragment_home_expire.setOnClickListener(this);
        promptDialog = new PromptDialog(this, R.style.MyDialog);
        recyclerView = (RecyclerView) findViewById(R.id.fragment_home_recycle);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        adapter = new ShowLocationDialogAdapter(this);
        recyclerView.setAdapter(adapter);
        // 设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new ShowLocationDialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int height, final int position) {
                if (flag) {
                    Utils.smallToast(ShowLocationDialogActivity.this, "" + position);
                } else {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    DialogShow.expirePromptShowAtLocation(location, 0, view.getHeight(),
                            position, DialogShow.MAIN_PROMPT,
                            ShowLocationDialogActivity.this, promptDialog);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_home_expire:
                if (flag) {
                    flag = false;
                } else {
                    flag = true;
                }
                adapter.setFlag(flag);
                adapter.notifyDataSetChanged();
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                DialogShow.expirePromptShowAtLocation(location, view.getWidth(), 0, 0,
                        DialogShow.EXPIRE_PROMPT, this, promptDialog);
                break;
        }
    }
}
