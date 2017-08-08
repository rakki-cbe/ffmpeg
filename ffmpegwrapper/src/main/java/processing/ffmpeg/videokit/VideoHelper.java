package processing.ffmpeg.videokit;

import android.media.MediaMetadataRetriever;

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
    public String getRatioVideo(String inputFile)
    {
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        metaRetriever.setDataSource(inputFile);
        String height = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        String width = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);


        int n1=0;
        if(height!=null && !height.equals(""))
        {
            n1=Integer.valueOf(height);
        }
        int n2=0;
        if(width!=null && !width.equals(""))
        {
            n2=Integer.valueOf(width);
        }
        int min=0;
        try {
             min=getGCD(n1,n2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(min!=0)
        {
            int r1=n1/min;
            int r2=n2/min;
            return r1+":"+r2;
        }
        return "";

    }

    int getGCD(int height,int width)throws Exception
    {
        int n1=height,n2=width;


        int r;

        while(n2 != 0)
        {
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
        String biteRate=metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
        int n1=0;
        if(height!=null && !height.equals(""))
        {
            n1=Integer.valueOf(height);
        }
        int n2=0;
        if(width!=null && !width.equals(""))
        {
            n2=Integer.valueOf(width);
        }
        int minSize =Math.min(n1,n2);

        int min=0;

        try {
            min=getGCD(minSize,CommentBuilder.Default_Width);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(min!=0)
        {
            int divider=minSize/min;
            return (n1/min)+"x"+(n2/min);
        }
        return n1+"x"+n2;
    }
}
