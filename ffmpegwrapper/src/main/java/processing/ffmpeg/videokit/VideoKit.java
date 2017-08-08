package processing.ffmpeg.videokit;

import android.os.AsyncTask;

import java.io.File;

/**
 * Created by Ilja Kosynkin on 06.07.2016.
 * Copyright by inFullMobile
 */
public class VideoKit {
    private final int ERROR_CODE_INPUTFILE_INVALID=1;
    final int ERROR_CODE_INPUTFILE_NAME_INVALID=2;
    private final int ERROR_CODE_OUTFILE_INVALID=3;
    final int ERROR_CODE_OUTPUTFILE_NAME_INVALID=4;
    final int ERROR_CODE_UNEXPECTED_ERROR=5;


    static {
        try {
            System.loadLibrary("avutil");
            System.loadLibrary("swresample");
            System.loadLibrary("avcodec");
            System.loadLibrary("avformat");
            System.loadLibrary("swscale");
            System.loadLibrary("avfilter");
            System.loadLibrary("avdevice");
            System.loadLibrary("videokit");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    private LogLevel logLevel = LogLevel.FULL;

    public void setLogLevel(LogLevel level) {
        logLevel = level;
    }

    public void getVideoInfo(String inputFile,VideoCompressionResult  compressionResult)
    {
        new compressVide(compressionResult,inputFile,inputFile).execute(new String[]{"ffmpeg","-i",inputFile,"-hide_banner"}) ;
    }
    public void process(CommentBuilder comment,VideoCompressionResult  compressionResult) {

        new compressVide(compressionResult,comment.getOutput(),comment.getInput()).execute(comment.build()) ;
    }

    private native int run(int loglevel, String[] args);


    private class compressVide extends AsyncTask<String[],Integer,Integer>
    {
        VideoCompressionResult  compressionResult;
        String outPath;
        String inPath;

        compressVide(VideoCompressionResult compressionResult, String outPath, String inPath) {
            this.compressionResult = compressionResult;
            this.outPath = outPath;
            this.inPath = inPath;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Integer integer) {
            if(0==integer)
            {
                compressionResult.onSucess(this.outPath);
            }
            else
            {
                compressionResult.onFailure(this.outPath,integer);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {


        }

        @Override
        protected Integer doInBackground(String[]... params) {
            if(inPath!=null && outPath !=null)
            {
                //TO check the input file exist /Not
                File f=new File(inPath);
                if(!f.exists())
                {

                    return ERROR_CODE_INPUTFILE_INVALID;
                }
                //Checking the out put directry is resent ot not
                 f =new File(outPath.substring(0,outPath.lastIndexOf("/")));
                if(!f.exists() || !f.isDirectory())
                {

                    return ERROR_CODE_OUTFILE_INVALID;
                }

            }
            return run(2, params[0]);
        }
    }
}
