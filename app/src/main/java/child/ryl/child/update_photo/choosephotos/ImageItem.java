package child.ryl.child.update_photo.choosephotos;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 一个图片对象
 *
 * @author Administrator
 */
public class ImageItem implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -4377648880701820348L;
    public String imageId;
    public String thumbnailPath;
    public String imagePath;
    public String uploadPath;
    public boolean isIndex = false;

    public boolean isNetFile() {
        return !TextUtils.isEmpty(imagePath) && imagePath.startsWith("http");
    }
}
