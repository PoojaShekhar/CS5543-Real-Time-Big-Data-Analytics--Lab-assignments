package org.openimaj.demos.video;

import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.Tag;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.typography.hershey.HersheyFont;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by poojashekhar on 11/23/16.
 */
public class VideoTagging {

    public static void main(String args[]) {

        try {

        ClarifaiClient clarifai = new ClarifaiClient("5DeJMCoHDetbRnwLuDQaefyQsOboCua6s7AEysVT",
                "LpVfPZs67_EApWkZa93R3rYQzP0cyLnK52KhrYGj");

        File[] keyFrames = new File("/home/poojashekhar/Downloads/RealTimeBigData/Lab3/output/mainframes/").listFiles();
        File path = new File("/home/poojashekhar/Downloads/RealTimeBigData/Lab3/output/frames/");
        File[] allFrames = path.listFiles();
        Arrays.sort(allFrames);

           

        List<RecognitionResult> results =
                clarifai.recognize(new RecognitionRequest(keyFrames));

            for (int i = 0; i < results.size(); i++) {
                System.out.println(results);
            }



        Vector<Tag> tags = new Vector<Tag>();
        for (int i = 0; i < results.size(); i++) {
            tags.add(results.get(i).getTags().get(2)); // get the second tag and store in tag vector
            System.out.println(tags.get(i));
        }

            //System.out.println(tags);

       /* Vector<Integer> transIndex = new Vector<Integer>();
        for(int i = 0; i < keyFrames.length; i++) {
            String fname = keyFrames[i].toString();
            String index = fname.split("_")[0].split("/")[1];
            transIndex.add(Integer.parseInt(index));
        }
        Collections.sort(transIndex);*/

        for(int i = 0; i < allFrames.length; i++) {
            /*if (!transIndex.isEmpty() && i == transIndex.get(0)) {
                transIndex.remove(0);
                tags.remove(0);
            }*/
            MBFImage image = ImageUtilities.readMBF(allFrames[i]);
            image.drawText(tags.get(i).getName(), 30, 100, HersheyFont.TIMES_BOLD, 20, RGBColour.CYAN);
            DisplayUtilities.displayName(image, "videoFrames");
            Thread.sleep(100);

        }
    }
    catch(Exception e) {
        e.printStackTrace();
    }
}

}

