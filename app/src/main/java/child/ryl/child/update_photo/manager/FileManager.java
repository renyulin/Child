package child.ryl.child.update_photo.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FileManager {

    //	public static void createExternalStoragePrivateFile(Context context, InputStream inputStream, String folder,
//			String fileName) {
//
//		// Create a path where we will place our private file on external
//		// storage.
//		File file = new File(context.getExternalFilesDir(folder), fileName);
//
//		try {
//			// Very simple code to copy a picture from the application's
//			// resource into the external file. Note that this code does
//			// no error checking, and assumes the picture is small (does not
//			// try to copy it in chunks). Note that if external storage is
//			// not currently mounted this will silently fail.
//			OutputStream outputStream = null;
//
//			BufferedInputStream bin = null;
//
//			bin = new BufferedInputStream(inputStream, 1024);
//			outputStream = new BufferedOutputStream(new FileOutputStream(file), 1024);
//			byte buffer[] = new byte[1];
//			while (bin.read(buffer) != -1) {
//				outputStream.write(buffer);
//			}
//			bin.close();
//			inputStream.close();
//			outputStream.close();
//		} catch (Exception e) {
//			// Unable to create file, likely because external storage is
//			// not currently mounted.
//			// Log.w("ExternalStorage", "Error writing.......... " + file, e);
//		}
//	}
//
    public static String getImageFolder() {
        return "agency_im" + File.separator + "upload";
    }

    public static String saveImageFiles(Context context, Bitmap bitmap) {
        String rootPath = getImageFolder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmssSSS", Locale.CHINA);
        String name = sdf.format(Calendar.getInstance(Locale.CHINA).getTime()) + ".jpg";
        return FileManager.createExternalStoragePrivateFile(context, bitmap, rootPath, name);
    }

    public static String createExternalStoragePrivateFile(Context context, Bitmap bitmap, String folder, String fileName) {
        // Create a path where we will place our private file on external
        // storage.
        File file = new File(context.getExternalFilesDir(folder), fileName);
        String path = file.getPath();
        FileOutputStream fops = null;
        try {
            fops = new FileOutputStream(file);
            // 把数据写入文件
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fops);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fops.flush();
                fops.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bitmap = null;
        return path;
    }

    public static void createExternalStoragePrivateFileFromString(Context context, String folder, String fileName, String data) {
        // Create a path where we will place our private file on external
        // storage.

        // Log.d(TAG, "Write data to sd card.....");
        // write the contents on mySettings to the file
        try {
            OutputStream outputStream = null;

            File file = new File(context.getExternalFilesDir(folder), fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            // open file for writing
            OutputStreamWriter out = new OutputStreamWriter(outputStream);
            out.write(data);
            // close the file
            out.close();
        } catch (IOException e) {
            // Don't save data to SDCard when no SDcard!!!!
            e.printStackTrace();
        }
    }

//	public static File getFileDirectory(Context context, String folder, String name) {
//		File file = new File(context.getExternalFilesDir(folder), name);
//		String pathString = file.getPath().substring(0, file.getPath().lastIndexOf('/'));
//		// Log.d(TAG, "file.getName()....." + file.getName());
//		File f = new File(pathString);
//		return f;
//	}
//
//	public static void deleteExternalStoragePrivateFile(Context context, String folder, String name) {
//		// Get path for the file on external storage. If external
//		// storage is not currently mounted this will fail.
//		// Log.d(TAG, "name....." + name);
//		File file = new File(context.getExternalFilesDir(folder), name);
//		if (file != null) {
//			// Log.d(TAG, "file name....." + name);
//			file.delete();
//		}
//
//	}
//
//	public static void deleteAllExternalStoragePrivateFile(Context context, String folder) {
//		// Get path for the file on external storage. If external
//		// storage is not currently mounted this will fail.
//
//		File file = context.getExternalFilesDir(folder);
//		/*
//		 * if (file != null) { Log.d(TAG, "file name....." + name);
//		 * file.delete(); }
//		 */
//		if (file.isDirectory()) {
//			File[] files = file.listFiles();
//			for (File file2 : files) {
//				if (file2 != null) {
//					file2.delete();
//					System.out.println("删除了一个");
//					// Log.d(TAG, "Deleted file name....." + file2.getName());
//				}
//			}
//		}
//
//	}
//
//	public static boolean hasExternalStoragePrivateFile(Context context, String folder, String name) {
//		// Get path for the file on external storage. If external
//		// storage is not currently mounted this will fail.
//		File file = new File(context.getExternalFilesDir(folder), name);
//
//		return file.length() > 0 ? true : false;
//
//	}
//
//	public static File getExternalStoragePrivateFile(Context context, String folder, String name) {
//		// Get path for the file on external storage. If external
//		// storage is not currently mounted this will fail.
//		File file = new File(context.getExternalFilesDir(folder), name);
//
//		return file;
//	}
//
//	public static String getExternalStoragePrivateFilePath(Context context, String folder, String name) {
//		File file = new File(context.getExternalFilesDir(folder), name);
//		String path = file.getPath();
//		// Log.d("ExternalStorage path", path);
//		return path;
//	}

    public static void deleteFile(File oldPath) {
        if (oldPath.isDirectory()) {
            File[] files = oldPath.listFiles();
            for (File file : files) {
                deleteFile(file);
            }
        } else {
            oldPath.delete();
        }
    }

    public static String getCameraPath(Context context) {
        String rootPath = getImageFolder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmssSSS", Locale.CHINA);
        String name = sdf.format(Calendar.getInstance(Locale.CHINA).getTime()) + ".jpg";
        File file = new File(context.getExternalFilesDir(rootPath), name);
        String path = file.getPath();
        return path;
    }

    public static Bitmap getLocalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
