package TMHMM;

import Help_classes.Counter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fransilvion on 14.09.2016.
 *This class implements the scaled Forward algorithm.

 :param sequence str: a string over the alphabet specified by the model.
 :rtype: tuple(matrix, constants)
 :return: the scaled dynamic programming table and the constants used to
 normalize it.
 */
public class Hmm {
    //static final String[] GROUP_NAMES = new String[]{"i", "m", "o"};

    private static final Map<Integer, String> GROUP_NAMES;
    static {
        GROUP_NAMES = new HashMap<Integer, String>();
        GROUP_NAMES.put(0, "s");
        GROUP_NAMES.put(1, "m");
    }

    //private class so no one will be able to crate it
    private Hmm() {

    }

    public static double[][] parse(String sequence){
        String s = TMHMM.TMHMM;

        Matrix_class res = TMHMM_model.parse(s);

        //double[] constants = new double[sequence.length()+1];
        double[] constants = new double[sequence.length()];
        double[][] forward_table = forward(sequence, res.getInitial(), res.getTransitions(),
                res.getEmissions(), res.getCharMap(), constants);

        double[][] backward_table = backward(sequence, res.getInitial(), res.getTransitions(),
                res.getEmissions(), res.getCharMap(), constants);

        double[][] posterior = new double[forward_table.length][forward_table[0].length];

        //here we just multiple probabilities without dividing on P(x) - ask Spirin
        for (int i = 0; i < forward_table.length; i++)
        {
            for (int j = 0; j < forward_table[i].length; j++)
                posterior[i][j] = forward_table[i][j] * backward_table[i][j];
        }

        int observations = sequence.length();
        int states = res.getNamemap().size();

        //just counts how many states there are per label
        Counter<String> group_counts = new Counter<>();

        for (String label : res.getLabelMap().values())
        {
            group_counts.add(label);
        }

        //final table - for every observation (letter in sequence)
        //get probabilities of being soluble (i or o) or transmembrane (m)
        double[][] table = new double[observations][2];

        for (int i = 0; i < observations; i++) {
            //dictionary for counting probabilities of (i, o, m)
            HashMap<String, Double> group_probs = new HashMap<>();
            double sum = 0;

            for (int j = 0; j < states; j++) {
                String group = res.getLabelMap().get(j).toLowerCase();

                //if label stands for "in" or "out" - it means this is soluble ("s") part
                if (group.equals("i") || group.equals("o")) {
                    group = "s";
                }

                //group_probs[group] += posterior[i, j]
                group_probs.put(group, group_probs.containsKey(group) ?
                        group_probs.get(group) + posterior[i][j] : posterior[i][j]);
            }

            for (Map.Entry<Integer, String> en : GROUP_NAMES.entrySet()) {
                table[i][en.getKey()] = group_probs.get(en.getValue());
                sum += group_probs.get(en.getValue());
            }

            //for normalization
            for (int x = 0; x < table[i].length; x++) {
                table[i][x] = table[i][x] / sum;
            }
        }

        return table;

    }

//////////////////////////////////////////////////////////////

    //This function implements the scaled Forward algorithm.
    public static double[][] forward(String sequence, double[] initial, double[][] transitions,
                                     double[][] emissions, HashMap<Character,Integer> char_map,
                                     double[] constants){
        String upSequence = sequence.toUpperCase();

        int no_observations = sequence.length();
        int no_states = initial.length;

        //matrix with forward probabilities
        double[][] M = new double[no_observations][no_states];

        int i, j, k, observation;
        double state_sum;

        observation = char_map.get(upSequence.charAt(0));

        for (i = 0; i < no_states; i++) {
            M[0][i] = initial[i] * emissions[i][observation];
        }
        constants[0] = sumDouble(M[0]);

        //iteration (any better ideas?)
        //carry out scaling procedure (through dividing all probabilities by their total sum
        for (int d = 0; d < M[0].length; d++) {
            M[0][d] = M[0][d] / constants[0];
        }

        for (i = 1; i < no_observations; i++) {
            observation = char_map.get(upSequence.charAt(i));
            for (j = 0; j < no_states; j++) {
                state_sum = 0.0d;
                for (k = 0; k < no_states; k++) {
                    state_sum += M[i - 1][k] * transitions[k][j];
                }
                M[i][j] = state_sum * emissions[j][observation];
            }
            constants[i] = sumDouble(M[i]);

            //iteration (any better ideas?)
            //same scaling, at the end all these probabilities will give 1 in sum
            for (int d = 0; d < M[i].length; d++) {
                M[i][d] = M[i][d] / constants[i];
            }

        }
        return M;

    }

//////////////////////////////////////////////////////////////

    //This function implements the scaled backward algorithm.
    public static double[][] backward(String sequence, double[] initial, double[][] transitions,
                                      double[][] emissions, HashMap<Character,Integer> char_map,
                                      double[] constants)
    {
        String upSequence = sequence.toUpperCase();

        int no_observations = sequence.length();
        int no_states = initial.length;

        //matrix with backward probabilities
        double[][] M = new double[no_observations][no_states];

        int i, j, k, observation;
        double prob, state_sum;

        //scalable initialization (true?)
        //iteration (any better ideas?)
        for (int d = 0; d < M[0].length; d++)
        {
            M[no_observations - 1][d] = 1 / constants[no_observations - 1];
        }

        for (i = no_observations-2; i >= 0; i--)
        {
            observation = char_map.get(upSequence.charAt(i+1));
            for (j = 0; j < no_states; j++)
            {
                state_sum = 0.0d;
                for (k = 0; k < no_states; k++)
                {
                    state_sum += M[i + 1][k] * transitions[j][k] * emissions[k][observation];
                }
                M[i][j] = state_sum / constants[i];
            }
        }

        return M;
    }

//////////////////////////////////////////////////////////////

    //helping fucntion to sum the array containings
    private static double sumDouble(double[] array)
    {
        double res = 0.0d;
        for (double d : array)
        {
            res += d;
        }

        return res;
    }
}
