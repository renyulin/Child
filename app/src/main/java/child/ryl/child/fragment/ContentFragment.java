package child.ryl.child.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import child.ryl.child.R;

/**
 * contentProvider用法
 */
public class ContentFragment extends Fragment {
    private Button fragment_content_add;
    private Button fragment_content_delete;
    private Button fragment_content_update;
    private Button fragment_content_find;
    private String url = "content://child.ryl.child.provider/table1";
    private Uri uri = Uri.parse(url);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, null);
//        initView(view);
        return view;
    }

}
