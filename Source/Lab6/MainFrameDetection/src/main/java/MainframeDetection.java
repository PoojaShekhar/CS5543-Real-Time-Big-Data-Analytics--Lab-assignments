import org.apache.commons.io.FileUtils;
import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.feature.local.matcher.FastBasicKeypointMatcher;
import org.openimaj.feature.local.matcher.LocalFeatureMatcher;
import org.openimaj.feature.local.matcher.consistent.ConsistentLocalFeatureMatcher2d;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.feature.local.engine.DoGSIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.math.geometry.transforms.estimation.RobustAffineTransformEstimator;
import org.openimaj.math.model.fit.RANSAC;
import org.openimaj.video.Video;
import org.openimaj.video.xuggle.XuggleVideo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by poojashekhar on 11/26/16.
 */
public class MainframeDetection {

    private Video<MBFImage> video;
    //    VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(video);
    private List<MBFImage> imageList = new ArrayList<MBFImage>();
    private List<Long> timeStamp = new ArrayList<Long>();
    private List<Double> mainPoints = new ArrayList<Double>();
    private String path;
    private int numberImages = 0;
    private int afterImages = 0;
    private boolean hasRun = false;


    public MainframeDetection(String path){
        this.path = path;
    }

    public void Frames(){
        System.out.println("Xuggle call");

        video = new XuggleVideo(path);
        System.out.println("made xuggle video");

//        VideoDisplay<MBFImage> display = VideoDisplay.createVideoDisplay(video);
        int j=0;
        System.out.println("Initializing frame getting");
        for (MBFImage mbfImage : video) {
            BufferedImage bufferedFrame = ImageUtilities.createBufferedImageForDisplay(mbfImage);
            j++;
            System.out.println("On image " + j);
            String name = "output/frames/new" + j + ".jpg";
            File outputFile = new File(name);
            try {

                ImageIO.write(bufferedFrame, "jpg", outputFile);

            } catch (IOException e) {
                e.printStackTrace();
            }
            MBFImage b = mbfImage.clone();
            imageList.add(b);
            timeStamp.add(video.getTimeStamp());
            numberImages++;
        }

        System.out.println("The total frames are:" + j);
    }

    //this decides main frames
    public void MainFrames(){
        System.out.println("ImageList.size = " + imageList.size());

        //parallelize this plz
        for (int i=0; i<imageList.size() - 1; i++)
        {
            System.out.println("ImageList we on = " + i);

            MBFImage image1 = imageList.get(i);
            MBFImage image2 = imageList.get(i+1);
            DoGSIFTEngine engine = new DoGSIFTEngine();
            LocalFeatureList<Keypoint> queryKeypoints = engine.findFeatures(image1.flatten());
            LocalFeatureList<Keypoint> targetKeypoints = engine.findFeatures(image2.flatten());
            RobustAffineTransformEstimator modelFitter = new RobustAffineTransformEstimator(5.0, 1500,
                    new RANSAC.PercentageInliersStoppingCondition(0.5));
            LocalFeatureMatcher<Keypoint> matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(
                    new FastBasicKeypointMatcher<Keypoint>(8), modelFitter);
            matcher.setModelFeatures(queryKeypoints);
            matcher.findMatches(targetKeypoints);
            double size = matcher.getMatches().size();
            mainPoints.add(size);
            System.out.println("Main points added " + size);
        }
        Double max = Collections.max(mainPoints);

        for(int i=0; i<mainPoints.size(); i++){
            //let's see what happens if we increase the .01 value
            //I think we get more points
            if(((mainPoints.get(i))/max < 0.50) || i==0){
                Double name1 = mainPoints.get(i)/max;
                BufferedImage bufferedFrame = ImageUtilities.createBufferedImageForDisplay(imageList.get(i+1));
                String name = "output/mainframes/" + i + "_" + name1.toString() + ".jpg";
                File outputFile = new File(name);
                try {
                    ImageIO.write(bufferedFrame, "jpg", outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public void sethasRun(Boolean t){
        hasRun = t;
    }

    public int getNumberImages(){
        return numberImages;
    }

    public int getAfterImages(){
        return afterImages;
    }

}
