package org.openimaj.demos.video;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.video.Video;
import org.openimaj.video.xuggle.XuggleVideo;

import java.io.File;
import java.io.IOException;

/**
 * Created by poojashekhar on 11/23/16.
 */
public class VideoReconstructWithMaainFrames {

    public static void main(String[] args) throws IOException, InterruptedException {

        File path = new File("output/mainframes/");

        File[] files = path.listFiles();

        for (int i = 0; i < files.length - 1; i++) {
            int f1 = Integer.parseInt(files[i].getName().split("_")[0]);
            int f2 = Integer.parseInt(files[i + 1].getName().split("_")[0]);
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
