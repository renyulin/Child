package child.ryl.child.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Random;

import child.ryl.child.R;
import child.ryl.child.activity.AvatarActivity;
import child.ryl.child.my_view.CircleImageView;
import child.ryl.child.my_view.SpringProgressView;
import child.ryl.child.utils.Utils;

public class FourthFragment extends Fragment implements View.OnClickListener {
    private View view;
    private CircleImageView image;
    private SpringProgressView progressView;
    private Random random = new Random(System.currentTimeMillis());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_fourth, null);
        }
        image = (CircleImageView) view.findViewById(R.id.circle_image);
//            image.setImageResource(R.drawable.lena);
        image.setOnClickListener(this);
        Button clear = (Button) view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.deleteFile(getActivity().getExternalFilesDir("child.ryl.child"));
                Utils.smallToast(getActivity(), "清理成功");
            }
        });
        initView(view);
        return view;
    }

    private void initView(View view) {
        progressView = (SpringProgressView) view.findViewById(R.id.springProgressView);
        progressView.setMaxCount(1000.0f);
        progressView.setCurrentCount(random.nextInt(1000));
        view.findViewById(R.id.progress_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressView.setCurrentCount(random.nextInt(1000));
                Toast.makeText(getActivity(), "最大值：" + progressView.getMaxCount() +
                        "   当前值：" + progressView.getCurrentCount(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), AvatarActivity.class);
        String url = "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg";
        intent.putExtra("url", url);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 123:
                imageHttp(data.getStringExtra("urlBack"));
                break;
        }
    }

    //从网络获取图片
    private void imageHttp(String url) {
        ImageLoader imageLoader;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        imageLoader.displayImage(url, image);
    }
}
