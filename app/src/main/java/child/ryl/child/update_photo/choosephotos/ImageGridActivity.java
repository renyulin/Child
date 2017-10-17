package child.ryl.child.update_photo.choosephotos;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

import java.util.List;

import child.ryl.child.R;

public class ImageGridActivity extends Activity {
    public static final String EXTRA_IMAGE_LIST = "imagelist";
    public static final String INTENT_KEY_TITLE = "title";

    private List<ImageItem> dataList;
    private GridView gridView;
    private ImageGridAdapter adapter;
    private AlbumHelper helper;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_image_grid);

        int position = getIntent().getIntExtra(EXTRA_IMAGE_LIST, -1);
        if (position != -1) {
            helper = AlbumHelper.getHelper();
            helper.init(getApplicationContext());
            dataList = helper.getImagesBucketList(false).get(position).imageList;
        }

        initView();
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new ImageGridAdapter(ImageGridActivity.this, dataList);
        gridView.setAdapter(adapter);
    }
}
