package child.ryl.child.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import child.ryl.child.R;
import child.ryl.child.activity.AudioListActivity;
import child.ryl.child.adapter.EvaluationHouseFragmentAdapter;
import child.ryl.child.utils.Keys;
import child.ryl.child.utils.RequestHelper;
import child.ryl.child.video.AudioPlayer;

/**
 * 1.最低支持版本不同
 * Android.app.Fragment 兼容的最低版本是android:minSdkVersion="11" 即3.0版
 * android.support.v4.app.Fragment 兼容的最低版本是android:minSdkVersion="4" 即1.6版
 * 2.需要导jar包
 * fragment android.support.v4.app.Fragment 需要引入包android-support-v4.jar
 * 3.在Activity中取的方法不同
 * android.app.Fragment使用 (ListFragment)getFragmentManager().
 * findFragmentById(R.id.userList)  获得  ，继承Activity
 * <p>
 * android.support.v4.app.Fragment使用 (ListFragment)getSupportFragmentManager().
 * findFragmentById(R.id.userList) 获得 ，需要继承android.support.v4.app.FragmentActivity
 */
public class EvaluationHouseFragment extends Fragment implements AudioPlayer.AudioOnClick {
    RecyclerView list;
    private ImageView playBtn;
    private EvaluationHouseFragmentAdapter evaluationHouseFragmentAdapter;
    private List<Map<String, Object>> audiosList;
    private TextView seeMore;
    private AudioPlayer audioPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluation_house, container, false);
        list = (RecyclerView) view.findViewById(R.id.fragment_evaluation_house_list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setNestedScrollingEnabled(false);
        playBtn = (ImageView) view.findViewById(R.id.fragment_play_voice);
        seeMore = (TextView) view.findViewById(R.id.fragment_evaluation_see_more);
        playBtn.setOnClickListener(l);
        seeMore.setOnClickListener(l);
        if (audioPlayer == null) {
            audioPlayer = AudioPlayer.getHelper(getActivity());
            audioPlayer.setAudioOnClick(this);
        }
        return view;
    }

    public void getEvauationData() {
        Map<String, Object> param = new HashMap<>();
        param.put(Keys.PAGE, 1);
        param.put(Keys.PAGE_SIZE, 3);
        String url = "http://ltapi.fangxiaoer.com/apiv1/news/viewAudios";
        RequestHelper.readDataFromUrl(getActivity(), param, url, null,
                new RequestHelper.RequestCallback() {
                    @Override
                    public void doSuccess(Map<String, Object> jsonMap) {
                        audiosList = (List<Map<String, Object>>) jsonMap.get("content");
                        if (audiosList == null || audiosList.size() == 0) {
                            return;
                        }
                        getView().setVisibility(View.VISIBLE);
                        addAdapter();
                    }

                    @Override
                    public void doFail(int result) {
                    }
                }, false);
    }

    /**
     * 给adapter添加数据
     */
    private void addAdapter() {
        if (evaluationHouseFragmentAdapter == null) {
            evaluationHouseFragmentAdapter = new EvaluationHouseFragmentAdapter(getActivity(), audiosList);
            list.setAdapter(evaluationHouseFragmentAdapter);
        }
        evaluationHouseFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        audioPlayer.setAudioOnClick(this);
        if (audioPlayer != null && audioPlayer.isPlaying()) {
            playBtn.setImageResource(R.drawable.page_index_btn_bf);
        } else {
            playBtn.setImageResource(R.drawable.page_index_btn_zt);
        }
        getEvauationData();
    }

    private int j = 0;
    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fragment_play_voice:
                    if (TextUtils.isEmpty(audioPlayer.getAudioID())) {
                        audioPlayer.firstPlay(audiosList);
                        audioPlayer.setCategoryName("home");
                        playBtn.setImageResource(R.drawable.page_index_btn_bf);
                    } else if (audioPlayer.isPlaying()) {
                        audioPlayer.pause();
                        playBtn.setImageResource(R.drawable.page_index_btn_zt);
                    } else {
                        audioPlayer.play();
                        playBtn.setImageResource(R.drawable.page_index_btn_bf);
                    }
                    break;
                case R.id.fragment_evaluation_see_more:
                    Intent in = new Intent(getActivity(), AudioListActivity.class);
                    startActivity(in);
                    break;
            }
        }
    };

    @Override
    public void isStatus(int playState) {
        if (playState == AudioPlayer.NEXT_PLAY || playState == AudioPlayer.REFRESH_LIST) {
            evaluationHouseFragmentAdapter.notifyDataSetChanged();
        } else if (playState == AudioPlayer.PLAYING) {
            playBtn.setImageResource(R.drawable.page_index_btn_bf);
        } else if (playState == AudioPlayer.PAUSE) {
            playBtn.setImageResource(R.drawable.page_index_btn_zt);
        }
    }
}
