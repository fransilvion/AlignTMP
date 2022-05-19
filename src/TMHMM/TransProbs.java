package TMHMM;

import Help_classes.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fransilvion on 14.09.2016.
 */
public class TransProbs {
    private TransProbs() {

    }

    //method for calculating probs for all sequences in the profile
    public static HashMap<String, double[][]> calculateProbs(Profile profile) {
        HashMap<String, double[][]> probabilities = new HashMap<>();

        System.out.println("CALCULATING PROBABILITIES");
        ExecutorService exec = Executors.newFixedThreadPool(profile.getProfileLength());
        ArrayList<ProbabilityThread> probList = new ArrayList<>();
        //Profile is a dictionary, so we have to get its items in order to calculate probs
        for (Map.Entry<String, String> entry : profile.getProfile().entrySet())
        {
            ProbabilityThread probThread = new ProbabilityThread(entry.getKey(), entry.getValue());
            probList.add(probThread);
            exec.execute(probThread);
        }
        exec.shutdown();
        try {
            exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (ProbabilityThread i : probList){
            probabilities.put(i.getId(), i.getTable());
        }

        return probabilities;
    }
}
