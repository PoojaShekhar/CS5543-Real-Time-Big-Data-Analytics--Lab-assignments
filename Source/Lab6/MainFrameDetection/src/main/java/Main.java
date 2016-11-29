import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by poojashekhar on 11/26/16.
 */
public class Main {
    public static void main(String args[]) throws IOException {
        String path = args[0]; // mercedes-benz.mkv
        MainframeDetection mFD = new MainframeDetection(path);
        mFD.Frames();

        mFD.sethasRun(false);
        mFD.MainFrames();

        VideoReconstruct vr = new VideoReconstruct();
        long startTime = System.currentTimeMillis();

        File originalFile = new File("output/frames");

        vr.createOriginal(originalFile);

        long endTime = System.currentTimeMillis();
        long startTime2 = System.currentTimeMillis();



        File reconFile = new File("output/mainframes");
        vr.createVideo(reconFile);

        long endTime2 = System.currentTimeMillis();

        int original = mFD.getNumberImages();
        int after = mFD.getAfterImages();

        System.out.println("The original image had " + original + " frames and took " + (endTime-startTime)
                + " milli seconds and our new video had " + after + " frames and took " + (endTime2-startTime2) + " milli seconds");

        PrintWriter writer = new PrintWriter("output/metadata.txt", "UTF-8");
        writer.println("The original image had " + original + " frames and took " + (endTime-startTime)
                + " milli seconds and our new video had " + after + " frames and took " + (endTime2-startTime2) + " milli seconds");

        writer.close();

        ObjectFeatureExtraction extracter = new ObjectFeatureExtraction(args[1]);
        extracter.extractFeatures();




    }

}
