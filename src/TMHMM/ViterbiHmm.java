package TMHMM;

import java.util.HashMap;

/**
 * Created by Fransilvion on 01.02.2017.
 */
public class ViterbiHmm {

    private static final String[] GROUP_NAMES = new String[]{"s", "m"};

    //private class so no one will be able to crate it
    private ViterbiHmm() {

    }

    public static char[] runViterbi(String sequence){

        String s = TMHMM.TMHMM;

        Matrix_class res = TMHMM_model.parse(s);

        double[] initial = res.getInitial();
        double[][] transitions = res.getTransitions();
        double[][] emissions = res.getEmissions();
        HashMap<Character,Integer> char_map = res.getCharMap();
        HashMap<Integer, String> label_map = res.getLabelMap();

        String upSequence = sequence.toUpperCase();
        int no_observations = sequence.length();
        int no_states = initial.length;
        int[][] pointers = new int[no_observations][no_states];

        //matrix with log scaled probabilities
        double[][] M = new double[no_observations][no_states];

        int i, j, k, observation, pointer, end_pointer;
        double max_value, new_max_value, intermediate;

        observation = char_map.get(upSequence.charAt(0));

        //#1 - initialization
        for (i = 0; i < no_states; i++) {
            M[0][i] = Math.log(initial[i]) + Math.log(emissions[i][observation]);
        }

        //initialization of pointer's matrix (we came to all states at first from the begining state)
        for (i = 0; i < no_states; i++) {
            pointers[0][i] = -1;
        }

        //#2 - recursion
        for (i = 1; i < no_observations; i++) {
            observation = char_map.get(upSequence.charAt(i));
            for (j = 0; j < no_states; j++) {
                max_value = Double.NEGATIVE_INFINITY;
                pointer = 0;
                for (k = 0; k < no_states; k++) {
                    intermediate = M[i - 1][k] + Math.log(transitions[k][j]);
                    new_max_value = Math.max(intermediate, max_value);

                    //to put proper pointer, from what state we came to this state
                    if (new_max_value != max_value){
                        pointer = k;
                        max_value = new_max_value;
                    }

                }
                M[i][j] = max_value + Math.log(emissions[j][observation]);
                pointers[i][j] = pointer;
            }
        }

        //there are no probabilities of transfer to end state; thus, they are picked randomly
        //and equally for all states (0.01)
        //#3 - termination
        max_value = Double.NEGATIVE_INFINITY;
        end_pointer = 0;
        for (i = 0; i < no_states; i++) {
            intermediate = M[no_observations-1][i] + Math.log(0.01);
            new_max_value = Math.max(intermediate, max_value);

            //to put proper pointer, from what state we came to this state
            if (new_max_value != max_value){
                end_pointer = i;
                max_value = new_max_value;
            }
        }

        //procedure of bactrack in order to get the most probable path of states.
        //#4 - traceback
        StringBuilder path = new StringBuilder();

        //for end_pointer
        String group = label_map.get(end_pointer).toLowerCase();
        if (group.equals("i") || group.equals("o")) {
            group = "s";
        }
        path.append(group);

        int next_pointer = end_pointer;
        for (i = no_observations-1; i > 0; i--){
            next_pointer = pointers[i][next_pointer];
            group = label_map.get(next_pointer).toLowerCase();
            if (group.equals("i") || group.equals("o")) {
                group = "s";
            }
            path.append(group);
        }
        char[] final_path = new String(path.reverse()).toCharArray();
        return final_path;
    }

}
