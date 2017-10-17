package child.ryl.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import child.ryl.child.R;
import child.ryl.child.adapter.AudioItemAdapter;
import child.ryl.child.my_view.refresh.BaseSwipeRefreshLayout;
import child.ryl.child.update_photo.PromptHelper;
import child.ryl.child.utils.Keys;
import child.ryl.child.utils.RequestHelper;
import child.ryl.child.utils.Utils;
import child.ryl.child.video.AudioPlayer;

/**
 * 简讯列表页
 * 任玉林
 */
public class AudioListActivity extends FragmentActivity implements AudioPlayer.AudioOnClick {
    private BaseSwipeRefreshLayout act_audio_list_refreshLayout;
    private RecyclerView act_audio_list_recyclerView;
    private List<Map<String, Object>> audioList = new ArrayList<>();
    private AudioItemAdapter audioItemAdapter;
    private int page = 1;
    private String status;
    private AudioPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);
        if (player == null) {
            player = AudioPlayer.getHelper(this);
            player.setAudioOnClick(this);
        }
        act_audio_list_refreshLayout = (BaseSwipeRefreshLayout) findViewById(R.id.act_audio_list_refreshLayout);
        act_audio_list_recyclerView = (RecyclerView) findViewById(R.id.act_audio_list_recyclerView);
        act_audio_list_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        act_audio_list_refreshLayout.setOnFreshOrMoreListener(new BaseSwipeRefreshLayout.OnFreshOrMoreListener() {
            @Override
            public void OnFresh() {
                refreshList();
                act_audio_list_refreshLayout.setRefreshing(false);
            }

            @Override
            public void OnMore() {
                ++page;
                generateAudioData(page, 12);
                act_audio_list_refreshLayout.setLoadMore(false);
            }
        });
        refreshList();
    }

    public void refreshList() {
        page = 1;
        audioList.clear();
        generateAudioData(page, 12);
    }

    Map<String, Object> mParam;

    private void generateAudioData(int page, int pageNum) {
        if (mParam == null) {
            mParam = new HashMap<>();
        }
        mParam.put(Keys.PAGE, page);
        mParam.put(Keys.PAGE_SIZE, pageNum);
        String url = "http://ltapi.fangxiaoer.com/apiv1/news/viewAudios";
        RequestHelper.readDataFromUrl(this, mParam, url, new RequestHelper.RequestCallback() {
            @Override
            public void doSuccess(Map<String, Object> jsonMap) {
                List<Map<String, Object>> listTemp = (List<Map<String, Object>>) jsonMap.get("content");
                if (listTemp == null || listTemp.size() == 0) {
                    if (audioItemAdapter == null) {
                        Utils.smallToast(AudioListActivity.this, getString(R.string.no_info));
                    } else {
                        if (audioList == null || audioList.size() == 0) {
                            PromptHelper.displayMessage(AudioListActivity.this, getString(R.string.no_info));
                        } else {
                            PromptHelper.displayMessage(AudioListActivity.this, getString(R.string.no_more));
                        }
                        audioItemAdapter.notifyDataSetChanged();
                    }
                } else {
                    if (audioList.size() == 0) {
                        if (audioItemAdapter == null) {
                            audioItemAdapter = new AudioItemAdapter(AudioListActivity.this, audioList);
                        }
                        act_audio_list_recyclerView.setAdapter(audioItemAdapter);
                    }
                    audioList.addAll(listTemp);
                    player.setAudiosList(audioList);
                    audioItemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void doFail(int result) {
            }
        });
    }

    @Override
    public void isStatus(int playState) {
        if (playState == AudioPlayer.NEXT_PLAY || playState == AudioPlayer.PLAYING ||
                playState == AudioPlayer.REFRESH_LIST || playState == AudioPlayer.PAUSE) {
            audioItemAdapter.notifyDataSetChanged();
        }
    }
}
