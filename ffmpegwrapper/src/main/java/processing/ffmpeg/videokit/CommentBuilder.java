package processing.ffmpeg.videokit;

/**
 * Created by radhakrishnan on 7/8/17.
 */

public class CommentBuilder {

    private String header="ffmpeg";
    private String input="";
    private String output="";
    private String videoCodec="";
    private String audioCodec="";
    private String audioBiteforfram="";
    private String biteForFram="";
    private String size="";
    private String ratio="";
    private String framePerSecond="";
    public static int Default_Width=640;
    public int Default_Height=480;
    public CommentBuilder(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public void setVideoCodec(String videoCodec) {
        this.videoCodec = videoCodec;
    }

    public void setAudioCodec(String audioCodec) {
        this.audioCodec = audioCodec;
    }

    public void setBiteForFram(String biteForFram) {
        this.biteForFram = biteForFram;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public void setAudioBiteforfram(String audioBiteforfram) {
        this.audioBiteforfram = audioBiteforfram;
    }

    public void setFramePerSecond(String framePerSecond) {
        this.framePerSecond = framePerSecond;
    }

    String getInput() {
        return input;
    }

    String getOutput() {
        return output;
    }

    String[] build()
    {
          /* "ffmpeg","-y","-i","/storage/emulated/0/testvideo/small.mp4",
            "-strict","-2","-vcodec","mpeg4","-acodec","aac","-ar","44100","-ac",
            "1","-b:a","72k","-s","480x480","-aspect","1:1","-r","24",
            "/storage/emulated/0/testvideo/abababab_new_1.mp4"*/
       /* return new String[]{header,"-y","-i",input,"-strict","-2",
                "-vcodec",(videoCodec.equals(""))?"mpeg4":videoCodec,
                "-acodec",(audioCodec.equals(""))?"aac":audioCodec,
                "-ar",(audioBiteforfram.equals(""))?"44100":audioBiteforfram,
                "-ac","1","-b:a",(biteForFram.equals(""))?"72k":biteForFram,
                *//*"-s",(size.equals(""))?Default_Height+"x"+Default_Width:size,
                "-aspect",(ratio.equals(""))?"1;1":ratio,*//*
                "-r",(framePerSecond.equals(""))?"24":framePerSecond,output
        };*/

        return new String[]{header, "-y", "-i", input, "-strict", "-2",
                "-vcodec", (videoCodec.equals("")) ? "libx264" : videoCodec,
                 /* "-ab","32000",
                "-ar", (audioBiteforfram.equals("")) ? "16000" : audioBiteforfram,
                "-ac", "1",
               */
                "-r", (framePerSecond.equals("")) ? "24" : framePerSecond,
                "-b:a", (biteForFram.equals("")) ? "72k" : biteForFram,
                "-s", (size.equals("")) ? Default_Height + "x" + Default_Width : size,

                "-acodec", (audioCodec.equals("")) ? "copy" : audioCodec,
               /* "-aspect",(ratio.equals(""))?"1;1":ratio,*/
                //
                output
        };
    }

}
