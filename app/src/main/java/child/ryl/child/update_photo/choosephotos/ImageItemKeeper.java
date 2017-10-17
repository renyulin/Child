package child.ryl.child.update_photo.choosephotos;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import child.ryl.child.update_photo.manager.FileManager;

public class ImageItemKeeper {

    public final static int MAX_IAMGE_SELECTED = 8;
    private static ImageItemKeeper instance;
    public final static int INDEX_ALL = 1;
    public final static int INDEX_ROOM = 2;
    public final static int INDEX_BED = 3;
    public final static int INDEX_SELL = 4;
    public final static int INDEX_REVIEWS = 5;
    public int listType = 0;

    private ImageItemKeeper() {
    }

    public static ImageItemKeeper getInstance() {
        if (instance == null) {
            instance = new ImageItemKeeper();
        }
        return instance;
    }

    private List<ImageItem> allSelectList = new ArrayList<ImageItem>();
    private List<ImageItem> roomSelectList = new ArrayList<ImageItem>();
    private List<ImageItem> bedSelectList = new ArrayList<ImageItem>();
    private List<ImageItem> selSelectList = new ArrayList<ImageItem>();
    private List<ImageItem> reviewsSelectList = new ArrayList<ImageItem>();

    private List<ImageItem> currentList;

    private List<ImageItem> getSelectedImage(int index) {
        switch (index) {
            case INDEX_ALL:
                return allSelectList;
            case INDEX_ROOM:
                return roomSelectList;
            case INDEX_BED:
                return bedSelectList;
            case INDEX_SELL:
                return selSelectList;
            case INDEX_REVIEWS:
                return reviewsSelectList;
        }
        return null;
    }

    public void switchWorkList(int index) {
        currentList = getSelectedImage(index);
        listType = index;
    }

    public List<ImageItem> getWorkingList() {
        return currentList;
    }

    public boolean contains(ImageItem item) {
        return currentList != null && currentList.contains(item);
    }

    public boolean remove(ImageItem item) {
        boolean result = false;
        if (currentList != null) {
            result = currentList.remove(item);
            setDefaultIndex();
        }
        return result;
    }

    public boolean add(ImageItem item) {
        if (currentList != null) {
            if (currentList.size() == 0) {
                item.isIndex = true;
                currentList.add(item);
                return true;
            }
            if (currentList.size() >= MAX_IAMGE_SELECTED) {
                return false;
            }
            if (currentList.size() >= 1 && listType == INDEX_REVIEWS) {
                return false;
            }
            if (item.isIndex && !currentList.contains(item)) {
                for (ImageItem i : currentList) {
                    i.isIndex = false;
                }
                currentList.add(item);
                return true;
            }
            if (!currentList.contains(item)) {
                currentList.add(item);
            }
            setDefaultIndex();
            return true;
        }
        return false;
    }

    public int size() {
        return currentList == null ? 0 : currentList.size();
    }

    public void remove(int index) {
        if (currentList != null && index < currentList.size()) {
            currentList.remove(index);
            setDefaultIndex();
        }
    }

    public void setIndex(int position) {
        if (currentList != null && position < currentList.size()) {
            for (int i = 0; i < currentList.size(); i++) {
                ImageItem item = currentList.get(i);
                item.isIndex = position == i ? true : false;
            }
        }
    }

    public int getIndexPosition() {
        int position = 0;
        if (currentList != null) {
            for (ImageItem i : currentList) {
                if (i.isIndex) {
                    return position;
                }
                position++;
            }
        }
        return position;
    }

    public ImageItem getIndexItem() {
        if (currentList != null) {
            for (ImageItem i : currentList) {
                if (i.isIndex) {
                    return i;
                }
            }
            if (currentList.size() > 0) {
                return currentList.get(0);
            }
        }
        return null;
    }

    public void setDefaultIndex() {
        int total = 0;
        int lastIndex = 0;
        if (currentList != null) {
            for (int i = 0; i < currentList.size(); i++) {
                ImageItem item = currentList.get(i);
                if (item.isIndex) {
                    total++;
                    lastIndex = i;
                }
            }
        }
        if (currentList.size() > 0 && total == 0) {
            currentList.get(lastIndex).isIndex = true;
        }
        if (total > 1) {
            currentList.get(lastIndex).isIndex = false;
        }
        if (total > 2) {
            setDefaultIndex();
        }
    }

    private int getLocalCount() {
        int count = 0;
        for (int i = 0; i < currentList.size(); i++) {
            if (!currentList.get(i).isNetFile()) {
                count++;
            }
        }
        return count;
    }

    public String regenerateUploadedString(String uploadedString, String sign) {
        if (currentList != null && currentList.size() > 0) {
            StringBuilder sBuilder = new StringBuilder();
            int index = 0;
            if (uploadedString == null) {
                uploadedString = "";
            }
            String[] uploadedStrings = uploadedString.split(";");
            for (int i = 0; i < currentList.size(); i++) {
                ImageItem item = currentList.get(i);
                if (item.isNetFile()) {
                    sBuilder.append(item.imagePath.replace("http://images.fangxiaoer.com/sy/" + sign + "/middle/", ""));
                } else {
                    sBuilder.append(uploadedStrings[index++]);
                }
                sBuilder.append(";");
            }
            sBuilder.deleteCharAt(sBuilder.length() - 1);
            return sBuilder.toString();
        } else {
            return "";
        }
    }

    public File[] generateCompressedFileArray(Context context) {
        if (currentList != null && currentList.size() > 0) {
            File[] files = new File[getLocalCount()];
            int count = 0;
            for (int i = 0; i < currentList.size(); i++) {
                ImageItem item = currentList.get(i);
                if (item.isNetFile())
                    continue;
                if (TextUtils.isEmpty(item.uploadPath)) {
                    File file = new File(item.imagePath);
                    Bitmap bmp = BitmapTools.compressImageSize(file, 200);
                    String filePath = FileManager.saveImageFiles(context, bmp);
                    item.uploadPath = filePath;
                    files[count++] = new File(filePath);
                } else {
                    files[count++] = new File(item.uploadPath);
                }
            }
            return files;
        }
        return null;
    }

    public void clearAll() {
        allSelectList.clear();
        roomSelectList.clear();
        bedSelectList.clear();
        selSelectList.clear();
        reviewsSelectList.clear();
    }
}
