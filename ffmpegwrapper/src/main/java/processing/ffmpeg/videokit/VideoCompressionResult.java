package processing.ffmpeg.videokit;

/**
 * Created by radhakrishnan on 7/8/17.
 */

public interface VideoCompressionResult {
    void onSucess(String outPath);
    void onFailure(String outPath,int Code);
}
