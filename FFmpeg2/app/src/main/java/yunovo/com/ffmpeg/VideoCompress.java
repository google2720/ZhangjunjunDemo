package yunovo.com.ffmpeg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.mabeijianxi.smallvideorecord2.FileUtils;
import com.mabeijianxi.smallvideorecord2.JianXiCamera;
import com.mabeijianxi.smallvideorecord2.MediaRecorderBase;
import com.mabeijianxi.smallvideorecord2.StringUtils;
import com.mabeijianxi.smallvideorecord2.model.AutoVBRMode;
import com.mabeijianxi.smallvideorecord2.model.LocalMediaConfig;
import com.mabeijianxi.smallvideorecord2.model.MediaObject;
import com.mabeijianxi.smallvideorecord2.model.OnlyCompressOverBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangjunjun on 2018/6/6.
 */

public class VideoCompress {

    private static final String TAG = "VideoCompress";
    private ExecutorService mExecutor;
    VideoCompressListener mVideoCompressListener;
    private CompressTask compressTask;
    String mSourcePath;
    String mOutPath;
    private LocalMediaConfig config;
    private LocalMediaConfig.Buidler buidler;



    public void startCompress(String sourcePath,String outPath,VideoCompressListener videoCompressListener){

        if(compressTask==null){
            compressTask = new CompressTask();
        }
        if(mExecutor==null){
            mExecutor = Executors.newFixedThreadPool(1);
        }
        if(buidler==null){
            buidler = new LocalMediaConfig.Buidler();
        }

        mSourcePath = sourcePath;
        mOutPath = outPath;
        mVideoCompressListener = videoCompressListener;

        Log.d(TAG,"mSourcePath: "+mSourcePath);
        config = buidler
                .setVideoPath(mSourcePath)
                .captureThumbnailsTime(1)
                .doH264Compress(new AutoVBRMode(30))
                .setFramerate(7)
                .setScale(1.0f)
                .build();
        compressTask.setConfig(config);
        compressTask.setVideoCompressListener(mVideoCompressListener);




        String cachePath = mOutPath.substring(0,mOutPath.lastIndexOf("/")+1);
        Log.d(TAG,"cachePath: "+cachePath);
        JianXiCamera.setVideoCachePath(cachePath);


        int startIndex = mOutPath.lastIndexOf("/")+1;
        int endIndex = mOutPath.lastIndexOf(".");
        String targetName = mOutPath.substring(startIndex,endIndex);
        Log.d(TAG,"targetName: "+targetName);
        compressTask.setTargetName(targetName);

        mExecutor.submit(compressTask);

    }



public class CompressTask implements Runnable {

        String targetName;
        LocalMediaConfig config;
        VideoCompressListener videoCompressListener;
        Handler handler = new Handler();


        public void setTargetName(String targetName) {
            this.targetName = targetName;
        }

        public void setConfig(LocalMediaConfig config) {
            this.config = config;
        }

        public void setVideoCompressListener(VideoCompressListener videoCompressListener) {
            this.videoCompressListener = videoCompressListener;
        }

        @Override
        public void run() {

            if(!FileUtils.checkFile(config.getVideoPath())){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mVideoCompressListener.onFail("源文件不存在");
                    }
                });
                return;
            }else if("/sdcard/".equals(JianXiCamera.getVideoCachePath())
                    || "/sdcard".equals(JianXiCamera.getVideoCachePath())
                    || "sdcard/".equals(JianXiCamera.getVideoCachePath())
                    || "sdcard".equals(JianXiCamera.getVideoCachePath())){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mVideoCompressListener.onFail("输出目录不能为SD卡根目录");
                    }
                });
            }

            OnlyCompressOverBean onlyCompressOverBean = new MediaCompress(config).startCompress(targetName);
            Log.d(TAG,"CompressTask: "+onlyCompressOverBean.isSucceed());
            if(onlyCompressOverBean.isSucceed()){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        videoCompressListener.onSucceed();
                    }
                });

            }else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        videoCompressListener.onFail("转换失败");
                    }
                });
            }
        }

}



public interface VideoCompressListener {
    void onSucceed();
    void onFail(String msg);
}




public class MediaCompress extends MediaRecorderBase {

        private final String mNeedCompressVideo;
        private final OnlyCompressOverBean mOnlyCompressOverBean;
        private final LocalMediaConfig localMediaConfig;
        protected String scaleWH = "";

        @Override
        public MediaObject.MediaPart startRecord() {
            return null;
        }

        public MediaCompress(LocalMediaConfig localMediaConfig) {
            this.localMediaConfig = localMediaConfig;
            compressConfig = localMediaConfig.getCompressConfig();
            CAPTURE_THUMBNAILS_TIME = localMediaConfig.getCaptureThumbnailsTime();
            if (localMediaConfig.getFrameRate() > 0) {
                setTranscodingFrameRate(localMediaConfig.getFrameRate());
            }
            mNeedCompressVideo = localMediaConfig.getVideoPath();
            mOnlyCompressOverBean = new OnlyCompressOverBean();
            mOnlyCompressOverBean.setVideoPath(mNeedCompressVideo);

        }

        private String getScaleWH(String videoPath, float scale) {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(videoPath);
            String s = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
            String videoW = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            String videoH = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            int srcW = Integer.valueOf(videoW);
            int srcH = Integer.valueOf(videoH);
            int newsrcW = (int) (srcW / scale);
            int newsrcH = (int) (srcH / scale);
            if (newsrcH % 2 != 0) {
                newsrcH += 1;
            }
            if (newsrcW % 2 != 0) {
                newsrcW += 1;
            }
            if (s.equals("90") || s.equals("270")) {
                return String.format("%dx%d", newsrcH,newsrcW);

            } else if (s.equals("0") || s.equals("180") || s.equals("360")) {
                return String.format("%dx%d", newsrcW, newsrcH);
            }else {
                return "";
            }
        }

        private void correcAttribute(String videoPath, String picPath) {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(videoPath);
            String s = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
            String videoW = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            String videoH = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);

            if (s.equals("90") || s.equals("270")) {
                SMALL_VIDEO_WIDTH = Integer.valueOf(videoW);
                SMALL_VIDEO_HEIGHT = Integer.valueOf(videoH);
                String newPicPath = checkPicRotaing(Integer.valueOf(s), picPath);
                if (!TextUtils.isEmpty(newPicPath)) {
                    mOnlyCompressOverBean.setPicPath(newPicPath);
                }

            } else if (s.equals("0") || s.equals("180") || s.equals("360")) {
                SMALL_VIDEO_HEIGHT = Integer.valueOf(videoW);
                SMALL_VIDEO_WIDTH = Integer.valueOf(videoH);
            }

        }

        @Override
        public String getScaleWH() {
            return scaleWH;
        }

        private String checkPicRotaing(int angle, String picPath) {
            Bitmap bitmap = rotaingImageView(angle, BitmapFactory.decodeFile(picPath));
            return savePhoto(bitmap);
        }

        private Bitmap rotaingImageView(int angle, Bitmap bitmap) {

            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return resizedBitmap;
        }

        private String savePhoto(Bitmap bitmap) {

            FileOutputStream fileOutputStream = null;

            String fileName = UUID.randomUUID().toString() + ".jpg";
            File f = new File(mMediaObject.getOutputDirectory(), fileName);
            try {
                fileOutputStream = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
                return null;
            } finally {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return f.toString();
        }


        @Override
        public MediaObject setOutputDirectory(String key, String path) {
            if (StringUtils.isNotEmpty(path)) {
                File f = new File(path);
                if (f != null) {
                    if (f.exists()) {
                        //已经存在，删除
                        if (f.isDirectory()) {
                            FileUtils.deleteDir(f);
                        } else {
                            FileUtils.deleteFile(f);
                        }
                    }

                    f.mkdirs();
                }

                mMediaObject = new MediaObject(key, path, mVideoBitrate);
            }
            return mMediaObject;
        }

        public OnlyCompressOverBean startCompress(String outVideoName) {

            if (TextUtils.isEmpty(mNeedCompressVideo)) {
                return mOnlyCompressOverBean;
            }

            File f = new File(JianXiCamera.getVideoCachePath());
            if (!FileUtils.checkFile(f)) {
                f.mkdirs();
            }

            mMediaObject = setOutputDirectory(outVideoName, JianXiCamera.getVideoCachePath());
            mMediaObject.setOutputTempVideoPath(mNeedCompressVideo);
            float scale = localMediaConfig.getScale();
            if (scale > 1) {
                scaleWH = getScaleWH(mNeedCompressVideo, scale);
            }
            boolean b = doCompress(true);
            mOnlyCompressOverBean.setSucceed(b);

            if (b) {
                mOnlyCompressOverBean.setVideoPath(mMediaObject.getOutputTempTranscodingVideoPath());
                mOnlyCompressOverBean.setPicPath(mMediaObject.getOutputVideoThumbPath());
                correcAttribute(mMediaObject.getOutputTempTranscodingVideoPath(), mMediaObject.getOutputVideoThumbPath());
            }

            return mOnlyCompressOverBean;
        }


    }

}


