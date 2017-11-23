package child.ryl.child.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import child.ryl.child.R;
import child.ryl.child.activity.ActActivity;
import child.ryl.child.activity.AddPointActivity;
import child.ryl.child.activity.AddressBookActivity;
import child.ryl.child.alibaba.RootActivity;
import child.ryl.child.broadcast.BroadcastActivity;
import child.ryl.child.dialog.BottomSheetDialogView;
import child.ryl.child.my_view.DragImageView;
import child.ryl.child.test.A;
import child.ryl.child.test.Genericity;
import child.ryl.child.utils.Utils;
import child.ryl.child.viewpage.ADInfo;
import child.ryl.child.viewpage.ImageCycleView;

/**
 * firstFragment
 */
public class FirstFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    private View view;
    private DragImageView dragImageView;
    private ImageCycleView mAdView;
    private ImageLoader imageLoader;
    private ArrayList<ADInfo> infos = new ArrayList<ADInfo>();
    private ArrayList<ADInfo> infos2 = new ArrayList<ADInfo>();
    private RelativeLayout relative_layout;
    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
    private int currentColor = 0;
    final int[] colors = new int[]{
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6
    };
    final int names[] = new int[]{
            R.id.frame1,
            R.id.frame2,
            R.id.frame3,
            R.id.frame4,
            R.id.frame5,
            R.id.frame6,
    };
    TextView[] textViews = new TextView[names.length];
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                for (int i = 0; i < names.length; i++) {
                    textViews[i].setBackgroundResource(colors[(i + currentColor) % names.length]);
                }
                currentColor++;
            }
            super.handleMessage(msg);

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_ad_cycle, null);
            init(view);
        }
        return view;
    }

    int mDayNightMode;//对话框模式

    public void suspendImg() {
        if (dragImageView != null) {
            dragImageView.dettachView();
            dragImageView.removeAllViews();
        }
        View suspendBtn = LayoutInflater.from(getActivity()).inflate(R.layout.suspend_image, null);
        dragImageView = new DragImageView(getActivity());
        dragImageView.addView(suspendBtn);
        dragImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int mDayNightMode = AppCompatDelegate.MODE_NIGHT_AUTO;
                if (mDayNightMode == AppCompatDelegate.MODE_NIGHT_NO) {
                    mDayNightMode = AppCompatDelegate.MODE_NIGHT_YES;
                } else {
                    mDayNightMode = AppCompatDelegate.MODE_NIGHT_NO;
                }
                BottomSheetDialogView.show(getActivity(), mDayNightMode);
            }
        });
        dragImageView.attachView(relative_layout);
    }

    private void init(View view) {
        relative_layout = (RelativeLayout) view.findViewById(R.id.relative_layout);
        suspendImg();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        /*mImageUrl = new ArrayList<String>();
        mImageUrl2 = new ArrayList<String>();*/
        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("top-->" + i);
            infos.add(info);
        }

        mAdView = (ImageCycleView) view.findViewById(R.id.ad_view);

        for (int i = 0; i < names.length; i++) {
            textViews[i] = (TextView) view.findViewById(names[i]);
        }
        textViews[5].setOnClickListener(this);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x123);
            }
        }, 0, 200);
        mAdView.setImageResources(infos, mAdCycleViewListener);
        textViews[5].setOnLongClickListener(this);
    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (position == 0) {
                startActivity(new Intent(getActivity(), AddressBookActivity.class));
            } else if (position == 1) {
                startActivity(new Intent(getActivity(), RootActivity.class));
            } else if (position == 2) {
                startActivity(new Intent(getActivity(), ActActivity.class));
            } else if (position == 3) {
                startActivity(new Intent(getActivity(), BroadcastActivity.class));
            } else {
                Toast.makeText(getActivity(), "content->" + info.getContent() +
                        ":" + position, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            imageLoader.displayImage(imageURL, imageView);// 使用ImageLoader对图片进行加装！
        }
    };

    /**
     * dime应用dp
     */
//    private void dimen() {
//        ImageView point = new ImageView(getActivity());
//        point.setImageResource(R.drawable.point);
//        point.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        int width = (int) getResources().getDimension(R.dimen.base_padding);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, width);
//        lp.setMargins(5, 0, 5, 0);
//        point.setLayoutParams(lp);
//    }
    @Override
    public void onResume() {
        super.onResume();
        mAdView.startImageCycle();
        A a = new A();
        a.way();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdView.pushImageCycle();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdView.pushImageCycle();
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), AddPointActivity.class));
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.frame6:
                if (textViews[0].getVisibility()==View.VISIBLE){
                    for (int i=0;i<textViews.length-1;i++){
                        textViews[i].setVisibility(View.GONE);
                    }
                }else {
                    for (int i=0;i<textViews.length-1;i++){
                        textViews[i].setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
        return true;
    }
}
