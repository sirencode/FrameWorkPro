package com.syh.framework.util;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

public class VideoUtil {

    public static Bitmap getVideoThumbnail(String path) {
        Bitmap b = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
            b = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    public static Bitmap getNetVideoThumbnail(String url) {
        return null;
    }

}
