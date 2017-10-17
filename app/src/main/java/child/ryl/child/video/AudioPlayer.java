package child.ryl.child.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import child.ryl.child.utils.Keys;

/**
 * 封装的全局播放器
 * 任玉林
 */
public class AudioPlayer implements MediaPlayer.OnCompletionListener {
    private static AudioPlayer instance;
    private MediaPlayer mediaPlayer;
    private static Context mContext;
    public final static int PLAYING = 0;//播放中
    public final static int COMPLETION = 1;//播放完成
    public final static int PAUSE = 2;//播放暂停
    public final static int NEXT_PLAY = 3;//下一曲
    public final static int REFRESH_LIST = 4;//更新ui
    private String audioID = "";
    public AudioOnClick audioOnClick;
    private String categoryName = "";
    private List<Map<String, Object>> audiosList;
    private int position;

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized AudioPlayer getHelper(Context context) {
        mContext = context.getApplicationContext();
        if (instance == null) {
            synchronized (AudioPlayer.class) {
                if (instance == null)
                    instance = new AudioPlayer(mContext);
            }
        }
        return instance;
    }

    public AudioOnClick getAudioOnClick() {
        return audioOnClick;
    }

    public void setAudioOnClick(AudioOnClick audioOnClick) {
        this.audioOnClick = audioOnClick;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Map<String, Object>> getAudiosList() {
        return audiosList;
    }

    public void setAudiosList(List<Map<String, Object>> audiosList) {
        this.audiosList = audiosList;
    }

    public String getAudioID() {
        return audioID;
    }

    public void setAudioID(String audioID) {
        this.audioID = audioID;
    }

    /**
     * 适配器播放按钮监听事件
     * 播放音乐 id相同未播放->继续播放 id相同正在播放->停止播放 id不同未播放->播放
     *
     * @param audioId   唯一标识
     * @param audioPath 路径
     * @param mItems    数据列表
     * @param position  播放条目
     */
    public void playClick(String audioId, String audioPath,
                          List<Map<String, Object>> mItems, int position) {
        if (this.audioID.equals(audioId) && !isPlaying()) {
            try {
                play();
            } catch (Exception e) {
                startPlay(audioPath);
            }
        } else if (this.audioID.equals(audioId) && isPlaying()) {
            pause();
        } else if (!getAudioID().equals(audioId) || !isPlaying()) {
            setAudioID(audioId);
            audioOnClick.isStatus(REFRESH_LIST);
            startPlay(audioPath);
        }
        setAudiosList(mItems);
        setPosition(position);
    }

    /**
     * 第一次播放
     */
    public void firstPlay(List<Map<String, Object>> mItems) {
        if (mItems == null || mItems.size() == 0) {
            return;
        }
        String path = (String) mItems.get(0).get(Keys.AUDIO_PATH);
        String id = (String) mItems.get(0).get(Keys.AUDIO_ID);
        playClick(id, path, mItems, 0);
    }

    private AudioPlayer(Context context) {
    }

    public void startPlay(String url) {
        init();
        addAudioData(url);
        play();
    }

    /**
     * 播放
     */
    public void play() {
        mediaPlayer.start();
        audioOnClick.isStatus(PLAYING);
    }

    /**
     * 停止播放
     */
    public void pause() {
        mediaPlayer.pause();
        audioOnClick.isStatus(PAUSE);
    }

    /**
     * 播放状态
     *
     * @return
     */
    public boolean isPlaying() {
        if (mediaPlayer == null) {
            return false;
        }
        return mediaPlayer.isPlaying();
    }

    /**
     * 初始化mediaPlay
     */
    private void init() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(this);
        }
    }

    /**
     * 停止mediaPlayer
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * 给mediaPlay注入数据
     *
     * @param url
     */
    private void addAudioData(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(mContext, Uri.parse(url));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mediaPlayer.isLooping()) {
            audioOnClick.isStatus(COMPLETION);
            return;
        }
        String nextPath = nextAudioPath();
        audioOnClick.isStatus(NEXT_PLAY);
        startPlay(nextPath);
    }

    /**
     * 下一曲
     */
    private String nextAudioPath() {
        position++;
        if (position >= audiosList.size()) {
            position = 0;
        }
        Map<String, Object> map = audiosList.get(position);
        String audioPath = (String) map.get(Keys.AUDIO_PATH);
        audioID = (String) map.get(Keys.AUDIO_ID);
        return audioPath;
    }

    /**
     * is looping play ?
     *
     * @param looping
     */
    public void setLooping(boolean looping) {
        mediaPlayer.setLooping(looping);
    }

    public interface AudioOnClick {
        void isStatus(int playState);
    }
}
