package Help_classes;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Fransilvion on 23.09.2016.
 * helping class to put your sequence probabilities into text file
 * in format that is conveniet for parsing by python file.
 */
public class PutProbsToFile {

    private PutProbsToFile(){

    }

    //function for writing to the file
    public static void WritetoFile(HashMap<String, double[][]> probabilities) throws IOException {

        //create a directory (change after)
        String executionPath = System.getProperty("user.dir");
        File dir = new File(executionPath.replace("\\", "/") + "/csv_files");

        //if there is no a directory, than delete it!
        if (!dir.exists()) {
            dir.mkdir();
        }
        //if there is a directory - delete it with all files and create the directory
        else {
            //before deleting the folder you have to delete all files
            String[] entries = dir.list();
            for(String s: entries){
                File currentFile = new File(dir.getPath(),s);
                currentFile.delete();
            }
            dir.delete();
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
        //dir.mkdir();

        //essential stuff to write to file
        //change after
        File fileout;
        FileWriter fw;
        BufferedWriter bw;

        //parse the dictionary in order to make a csv file
        for (Map.Entry<String, double[][]> entry : probabilities.entrySet()){

            String id = entry.getKey();
            double[][] values = entry.getValue();

            //change the id (what is the best way?)
            //filename cannot contain such symbols:
            //*|\:"<>?/
            //without regexp
            id = id.replace(" ", "_");
            id = id.replace("*", "_");
            id = id.replace("|", "_");
            id = id.replace("\\", "_");
            id = id.replace(":", "_");
            id = id.replace("\"", "_");
            id = id.replace("<", "_");
            id = id.replace(">", "_");
            id = id.replace("?", "_");
            id = id.replace("/", "_");


            fileout = new File(executionPath.replace("\\", "/") + "/csv_files/" + id + ".csv");
            fw = new FileWriter(fileout);
            bw = new BufferedWriter(fw);

            //the header first
            bw.write("position,soluble,membrane\n");

            int i;
            for (i = 1; i <= values.length; i++){
                bw.write(i + "," + values[i-1][0] + "," + values[i-1][1] + "\n");
            }
            bw.close();
        }

    }
}
