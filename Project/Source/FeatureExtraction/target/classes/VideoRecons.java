import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import java.io.File;
import java.io.IOException;

/**
 * Created by poojashekhar on 12/7/16.
 */
public class VideoRecons {

    public static void main(String[] args) throws IOException, InterruptedException {

        File path = new File("/home/poojashekhar/Downloads/RealTimeBigData/Lab5/FeatureExtraction/output/modeldetectedframes/");

        File[] files = path.listFiles();

        for (int i = 0; i < files.length - 1; i++) {
            int f1 = Integer.parseInt(files[i].getName().substring(files[i].getName().indexOf("_")+1,files[i].getName().indexOf(".")));
            int f2 = Integer.parseInt(files[i + 1].getName().substring(files[i+1].getName().indexOf("_")+1,files[i+1].getName().indexOf(".")));
            if (f2 < f1) {
                File temp = files[i];
                files[i] = files[i + 1];
                files[i + 1] = temp;
                i = 0;
            }
        }

        //Video<MBFImage> video = new XuggleVideo();

        for (int i = 0; i < files.length; i++){

            MBFImage image = ImageUtilities.readMBF(files[i]);



            //image.drawText("Wild Life", 90, 150, HersheyFont.ASTROLOGY, 20, RGBColour.BLACK);

            DisplayUtilities.displayName(image, "videoFrames");
        }
    }
}
