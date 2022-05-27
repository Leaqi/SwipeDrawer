package cn.leaqi.drawerapp.Utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;

public class Cache {

    /**
     * 清除缓存目录
     */
    public static void clearCache() {
        try {
            delFolder(new File(getCachePath()));
            delFolder(AppData.getContext().getCacheDir());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除目录
     * @param dir 路径
     * @return 是否成功
     */
    private static boolean delFolder(File dir) {
        boolean isOk = true;
        try {
            if (dir != null) {
                if(dir.isDirectory()) {
                    String[] list = dir.list();
                    if (list != null) {
                        for (String item : list) {
                            if (!delFolder(new File(dir, item))) {
                                return false;
                            }
                        }
                    }
                }
                isOk = dir.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOk;
    }

    /**
     * 获取缓存路径
     * @return 返回路径
     */
    public static String getCachePath(){
        boolean isPath;
        File setPath = new File(AppData.getContext().getFilesDir(), "data");
        if (!setPath.exists()) {
            isPath = setPath.mkdirs();
        }else{
            isPath = true;
        }
        return (isPath ? setPath : AppData.getContext().getCacheDir()).toString();
    }

    /**
     * 获取缓存文件
     * @param name 文件名
     * @return 返回文件路径
     */
    public static File getCacheFile(String name){
        try {
            name = URLEncoder.encode(name, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new File(getCachePath() + "/" + name + ".Q");
    }

    /**
     * 创建文件
     * @param file 文件路径
     * @param bitmap 内容
     * @return 是否成功
     */
    public static boolean fileWrite(File file, Bitmap bitmap){
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
            stream.flush();
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 显示缓存图片
     * @param img Image
     * @param file 文件
     */
    public static boolean showImage(ImageView img, File file){
        try {
            if(file.exists()) {
                img.setImageURI(Uri.fromFile(file));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}

