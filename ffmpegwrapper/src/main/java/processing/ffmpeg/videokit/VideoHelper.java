package processing.ffmpeg.videokit;

import android.media.MediaMetadataRetriever;
import android.os.Environment;

import java.io.File;

/**
 * Created by radhakrishnan on 7/8/17.
 */

public class VideoHelper {
    private static VideoHelper videoHelper;
    public static VideoHelper getInstance()
    {
        if(videoHelper==null)
        {
            videoHelper=new VideoHelper();
        }
        return videoHelper;
    }
    private VideoHelper()
    {

    }
    public String getExpectedResolution(String inputFile, int width, int height) {
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        metaRetriever.setDataSource(inputFile);
        String heightOrginal = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        String widthOrginal = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        String rotation = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);

        int n1 = 0;
        if (heightOrginal != null && !heightOrginal.equals("")) {
            n1 = Integer.valueOf(heightOrginal);
        }
        int n2 = 0;
        if (widthOrginal != null && !widthOrginal.equals("")) {
            n2 = Integer.valueOf(widthOrginal);
        }
        int max = Math.max(n1, n2);
        int maxIs = (max == n2) ? 0 : 1;
        if (max < width) {
            return (maxIs == 0) ? width + "x" + height : height + "x" + width;
        } else {


            int min = 0;
            try {
                min = getGCD(max, width);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (min != 0) {
                int divident = max / min;
                int r1 = n1 / divident;
                int r2 = n2 / divident;

                return (maxIs == 0) ? r2 + "x" + r1 : r1 + "x" + r2;
            }
        }
        return "";

    }

    private int getGCD(int height, int width) throws Exception {
        int n1 = height, n2 = width;


        int r;

        while (n2 != 0) {
            r = n1 % n2;
            n1 = n2;
            n2 = r;
        }
        return n1;
    }

    public String getSizeVideo(String input) {
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        metaRetriever.setDataSource(input);
        String height = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        String width = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        String biteRate = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
        String frame = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE);
        int n1 = 0;
        if (height != null && !height.equals("")) {
            n1 = Integer.valueOf(height);
        }
        int n2 = 0;
        if (width != null && !width.equals("")) {
            n2 = Integer.valueOf(width);
        }
        int minSize = Math.min(n1, n2);

        int min = 0;

        try {
            min = getGCD(minSize, CommentBuilder.Default_Width);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (min != 0) {
            int divider = minSize / min;
            return (n1 / min) + "x" + (n2 / min);
        }
        return n1 + "x" + n2;
    }

    public String getTempVideoPath(String mediaPath) {
        if (mediaPath != null && !mediaPath.equals("")) {
            String name = mediaPath.substring(mediaPath.lastIndexOf("/"), mediaPath.length());
            return getAppDir() + name;
        }
        return getAppDir() + "temp.mp4";

    }

    /**
     * Get the device storage path for video
     *
     * @return String Device storage path
     */
    private String getSDPath() {
        File sdDir;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
            return sdDir.toString();
        } else {
            return null;
        }
    }

    /**
     * Get the device storage path for compressed video
     *
     * @return String Device storage path for compressed video
     */
    private String getAppDir() {
        String appDir = getSDPath();
        appDir += "/" + "xc";
        File file = new File(appDir);
        if (!file.exists()) {
            file.mkdir();
        }
        appDir += "/" + "videocompress";
        file = new File(appDir);
        if (!file.exists()) {
            file.mkdir();
        }
        return appDir;
    }
}
