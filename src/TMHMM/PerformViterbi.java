package TMHMM;

import Help_classes.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fransilvion on 02.02.2017.
 */
public class PerformViterbi {
    private PerformViterbi(){

    }

    //method for calculating Viterbi paths for all sequences in the profile
    public static HashMap<String, char[]> findPaths(Profile profile) {
        HashMap<String, char[]> paths = new HashMap<>();

        System.out.println("CALCULATING VITERBI PATHS");
        ExecutorService exec = Executors.newFixedThreadPool(profile.getProfileLength());
        ArrayList<ViterbiThread> probList = new ArrayList<>();
        //Profile is a dictionary, so we have to get its items in order to calculate probs
        for (Map.Entry<String, String> entry : profile.getProfile().entrySet()) {
            ViterbiThread viterbiThread = new ViterbiThread(entry.getKey(), entry.getValue());
            probList.add(viterbiThread);
            exec.execute(viterbiThread);
        }
        exec.shutdown();
        try {
            exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (ViterbiThread i : probList){
            paths.put(i.getId(), i.getPath());
        }

        return paths;
    }
}
