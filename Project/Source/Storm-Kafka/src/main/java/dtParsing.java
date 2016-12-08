import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by poojashekhar on 12/7/16.
 */
public class dtParsing {
    public static void main(String args[]) throws IOException {
        String label = "0.0";
        String inputPath = "/home/poojashekhar/Downloads/RealTimeBigData/Tutorial9/Storm-Kafka/data/Model"; //Input path of decision tree model from Spark-ML-Lib
        File model = new File(inputPath);
        String outputPath = "/home/poojashekhar/Downloads/RealTimeBigData/Tutorial9/Storm-Kafka/data/ClassHome1";
        GeneratePathForClass(model, outputPath, label);


    }

    public dtParsing(File model, String outputPath, String label) {
        try {
            GeneratePathForClass(model, outputPath, label);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String addBrackets(String line) {
        int section = line.indexOf("feature") + 7;
        if (line.contains("<")) {
            int op = line.indexOf("<");
            String numsec = line.substring(section, op);
            String newnum = "[" + numsec.trim() + "] ";
            line = line.replaceFirst(numsec, newnum);
        } else if (line.contains(">")) {
            int op = line.indexOf(">");
            String numsec = line.substring(section, op);
            String newnum = "[" + numsec.trim() + "] ";
            line = line.replaceFirst(numsec, newnum);
        }
        return line;
    }

    public static void GeneratePathForClass(File model, String outputPath, String label) throws IOException {

        Boolean check = false;
        List<String> tree = new ArrayList<String>();
        String[] modelArray = Files.readLines(model, Charsets.UTF_8).toArray(new String[0]);
        System.out.println(modelArray.length);
        for (int i = 0; i < modelArray.length; i++) {
            if (modelArray[i].contains("If")) {
                modelArray[i] = addBrackets(modelArray[i]);
                String labelChange = modelArray[i].replaceAll("If", "if");
                System.out.println(labelChange);
                tree.add(labelChange);
                continue;
            }

            if (modelArray[i].contains("Else")) {
                modelArray[i] = addBrackets(modelArray[i]);
                String labelChange = modelArray[i].replaceAll("Else", "else if");
                System.out.println(labelChange);
                tree.add(labelChange);
                continue;
            }

            if (modelArray[i].contains(label)) {
                String labelChange = modelArray[i].replaceAll("Predict: " + label, "return true;");
                System.out.println(labelChange);
                tree.add(labelChange);
                continue;
            } else if (modelArray[i].contains("Predict")) {
                int ip = modelArray[i].indexOf("Predict");
                String labelChange = modelArray[i].replaceAll(modelArray[i].substring(ip,
                        modelArray[i].length()), "return false;");
                System.out.println(labelChange);
                tree.add(labelChange);
                continue;
            }





        }

        Joiner joiner = Joiner.on("\n").useForNull("null");
        String output = joiner.join(tree);

         /*
        Saving output to a File
         */
        FileWriter fw = new FileWriter(new File(outputPath));
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(output);
        bw.close();

    }

}
