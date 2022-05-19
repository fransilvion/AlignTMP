package Help_classes;


import java.io.IOException;

/**
 * Created by Fransilvion on 21.09.2016.
 * Function for building graphs of probabilities from TMHMM
 * using python scrip with matplotlib through Jython
 */
public class ProbGraph {

    private ProbGraph(){

    }

    //method for launching the Python script, which builds graphs for all sequences
    //using matplotlib library
    //should be launched after PutProbsToFile.WriteToFile(...)
    public static void BuildGraphs(){
        //code to execute the python code for building the graphs of probabilities
        try {
            String executionPath = System.getProperty("user.dir");
            //ProcessBuilder pb = new ProcessBuilder("C:/Users/PC/Anaconda3/python.exe", executionPath.replace("\\", "/") +"/plot_csv.py");
            ProcessBuilder pb = new ProcessBuilder("python", executionPath.replace("\\", "/") +"/plot_csv.py");
            Process p = pb.start();
            //p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Didn't build graphs");
        }
    }

    //public static void main(String[] args){
        //ProbGraph.BuildGraphs();
        //System.out.println("Done");
    //}

}
