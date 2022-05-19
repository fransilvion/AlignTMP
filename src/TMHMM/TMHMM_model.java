package TMHMM;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fransilvion on 14.09.2016.
 * this code is an interpretaion of pyhton code version of Dan Søndergaard (https://bitbucket.org/dansondergaard/)
 */
public class TMHMM_model {
    //private class so no one will be able to crate it
    private TMHMM_model()
    {

    }


//////////////////////////////////////////////////////////////

    //delete all comments from the text
    private static String strip_comments(String text)
    {
        String[] parts = text.split("\n");
        List<String> split_text = new ArrayList<>(Arrays.asList(parts));
        for (int i = 0; i < parts.length; i++)
        {
            if (parts[i].startsWith("#")) {
                split_text.remove(parts[i]);
            }
        }

        String res = String.join("\r\n", split_text);
        return res;
    }

//////////////////////////////////////////////////////////////

    //split text to individual symbols and words (which will be easy to parse)
    private static ArrayDeque<String> tokenize(String text)
    {
        ArrayDeque<String> allMatches = new ArrayDeque<>();
        Matcher m = Pattern.compile("[A-Za-z0-9\\.\\-_]+|[:;\\{\\}]").matcher(text);
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches;
    }

//////////////////////////////////////////////////////////////

    //get a header of the TMHMM model
    private static HashMap<String, String> parse_header(ArrayDeque<String> tokens)
    {
        tokens.removeFirst(); //"header"
        tokens.removeFirst(); //"{"

        HashMap<String, String> header = new HashMap<>();

        String token;
        while (true)
        {
            token = tokens.removeFirst();

            if (token.equals("}"))
                break;
            header.put(token, tokens.removeFirst());
            tokens.removeFirst(); //";"
        }

        return header;
    }

//////////////////////////////////////////////////////////////

    //if we have information of list type, for example in ihelixm we have this line:
    //trans ihelix2 ihelix3 ihelix4 ihelix5 ihelix6 ihelix7 ihelix8 ihelix9 ihelix10 ihelix11 ihelix12 ihelix13 ihelix14 ihelix15 ihelix16 ihelix17 ihelix18 ihelix19 ihelix20 ihelix21 ihelixi1
    private static List<String> parse_list(ArrayDeque<String> tokens)
    {
        List<String> list = new ArrayList<>();
        while (true)
        {
            String token = tokens.removeFirst();

            if (token.equals(";"))
            {
                tokens.addFirst(token);
                return list;
            }

            list.add(token);
        }
    }

//////////////////////////////////////////////////////////////

    //so here we just put into dictionary appropriate information, for example this one
    //- trans probabilities from state ohelixm (or only)
    private static LinkedHashMap<String, Double> parse_map(ArrayDeque<String> tokens)
    {
        LinkedHashMap<String, Double> map = new LinkedHashMap<>();

        while (true)
        {
            String token = tokens.removeFirst();

            if (token.equals(";"))
            {
                tokens.addFirst(token);
                return map;
            }

            String next_token = tokens.removeFirst();

            //Fallback if the map was actually a list (for example state ihelixm)
            if (!next_token.equals(":"))
            {
                tokens.addFirst(next_token);
                tokens.addFirst(token);
                return null;
            }

            String value = tokens.removeFirst();
            //I am not sure, that double here is appropriate
            map.put(token, Double.parseDouble(value));
        }

    }

//////////////////////////////////////////////////////////////

    //parse states (second deque as the input for state_names)
    //we just parse different states in different ways.
    //possible objects are String, LinkedHashMap<String,Double>, List<String>, Integer
    private static HashMap<String, Object> parse_state(ArrayDeque<String> tokens, ArrayDeque<String> names) {
        String state_name = tokens.removeFirst();
        names.addLast(state_name);
        tokens.removeFirst(); //"{"

        HashMap<String, Object> parsed_state = new HashMap<>();

        Object value = null;

        while (true) {
            String token = tokens.removeFirst();

            if (token.equals("}"))
                return parsed_state;
            if (token.equals("trans") || token.equals("only")) {

                value = parse_map(tokens); //here value is LinkedHashMap<String, Double>
                if (value == null) {
                    value = parse_list(tokens); //here value is List<String>
                }
            }
            else if (token.equals("type") || token.equals("end")) {
                value = Integer.parseInt(tokens.removeFirst()); //here value is an Integer
            }
            else {
                value = tokens.removeFirst(); //here value is a String

            }

            parsed_state.put(token, value);
            tokens.removeFirst(); //";"
        }

    }

//////////////////////////////////////////////////////////////

    /**
     * """
     (after - in your own words!)
     The TMHMM file format allows parameters to be tied to the parameters of
     some other state. This basically means that a state inherits the parameters
     from another state.

     The normalization performed by this function consists of copying the
     specified parameters from the parent state to the inheriting state such
     that all states explicitly specify their transition and emission
     probabilities.
     """

     */
    //states - is a dictionary of a state name, and result of previous function
    private static void normalize_states(HashMap<String, HashMap<String, Object>> states)
    {
        for(Map.Entry<String, HashMap<String, Object>> entry : states.entrySet())
        {
            String name = entry.getKey();
            HashMap<String, Object> state = entry.getValue();

            //inherit parent's transition probabilities, but only for
            //the states specified for this state.
            if (state.containsKey("tied_trans")) {
                HashMap<String, Object> parent_state = states.get(state.get("tied_trans"));
                //there was some strange to_state variable

                HashMap<String, LinkedHashMap<String,Double>> true_parent_state = new HashMap<>();

                //check for debug!!
                //here we just put true map in the appropriate dictionary
                for (Map.Entry<String, Object> en : parent_state.entrySet()) {
                    if (en.getValue() instanceof LinkedHashMap) {
                        true_parent_state.put(en.getKey(), (LinkedHashMap<String, Double>) en.getValue());
                    }
                }

                //new dict (HashMap? LinkedHashMap?)
                HashMap<String, Double> final_dict = new HashMap<>();

                //for God sake!!!
                if (state.get("trans") instanceof List) {
                    List<String> true_list = (List) state.get("trans");

                    int count = 0;
                    for (Double st : true_parent_state.get("trans").values()) {
                        final_dict.put(true_list.get(count), st);
                        count++;

                    }

                    //here I am trying to put new value to old key - inherit probabilities!
                    states.get(name).put("trans", final_dict);
                }
                else {
                    System.out.println("Something is wrong");
                    System.exit(1);
                }

            }

            //inherit parent's emission probabilities
            if (state.containsKey("tied_letter")) {
                String parent_state = state.get("tied_letter").toString();

                LinkedHashMap<String, Double> final_dict;

                //because it must be a map!!!
                if (states.get(parent_state).get("only") instanceof LinkedHashMap)
                {
                    final_dict = (LinkedHashMap<String, Double>) states.get(parent_state).get("only");

                    //change containings of this state
                    states.get(name).put("only", final_dict);
                }
                else
                {
                    System.out.println("Something is wrong");
                    System.exit(1);
                }

            }

        }
    }

//////////////////////////////////////////////////////////////

    //Convert a model to matrix form; alphabet - is a sequence of amino acids
    private static Matrix_class to_matrix_form(String alphabet, HashMap<String, HashMap<String, Object>> states)
    {
        //pull out initial probabilities
        HashMap<String,Object> begin = states.get("begin");
        states.remove("begin");

        //must work!
        LinkedHashMap<String, Double> trans_map = (LinkedHashMap<String, Double>) begin.get("trans");


        //build state -> index mapping (what does it mean???)
        //(state_map = {v: k for k, v in enumerate(states)})
        HashMap<String, Integer> state_map = new HashMap<>();
        HashMap<Integer, String> name_map = new HashMap<>();

        int count = 0;
        for (Map.Entry<String, HashMap<String, Object>> en : states.entrySet())
        {
            String key = en.getKey();
            state_map.put(key, count);
            name_map.put(count,key);
            count++;
        }

        //build character -> index mapping
        //(char_map = {v: k for k, v in enumerate(alphabet)})
        char[] alph = alphabet.toCharArray();
        HashMap<Character, Integer> char_map = new HashMap<>();
        for (int i = 0; i < alph.length; i++)
        {
            char_map.put(alph[i],i);
        }

        int no_states = states.size();
        //I hope, double is appropriate here (maybe float?)
        double[] initial = new double[no_states];
        double[][] transitions = new double[no_states][no_states];
        double[][] emissions = new double[no_states][alphabet.length()];

        //initial probabilities
        for (Map.Entry<String, Double> en : trans_map.entrySet())
        {
            String state_name = en.getKey();
            Double trans_prob = en.getValue();
            int this_state_idx = state_map.get(state_name);
            initial[this_state_idx] = trans_prob;
        }

        HashMap<Integer, String> label_map = new HashMap<>();

        for(Map.Entry<String, HashMap<String, Object>> en : states.entrySet())
        {
            String state_name = en.getKey();
            HashMap<String, Object> state = en.getValue();
            int this_state_idx = state_map.get(state_name);

            //label map
            label_map.put(this_state_idx,(String) state.get("label"));


            //transition probabilities
            HashMap<String, Double> state_trans = (HashMap<String, Double>) state.get("trans");
            for(Map.Entry<String, Double> entry : state_trans.entrySet())
            {
                String other_state_name = entry.getKey();
                Double trans_prob = entry.getValue();

                int other_state_idx = state_map.get(other_state_name);
                transitions[this_state_idx][other_state_idx] = trans_prob;
            }

            //emission probabilities
            if (state.containsKey("only"))
            {
                HashMap<String, Double> state_only = (HashMap<String, Double>) (state.get("only"));
                for(Map.Entry<String, Double> entry : state_only.entrySet())
                {
                    String character = entry.getKey();
                    Double emission_prob = entry.getValue();

                    int this_character_idx = char_map.get(character.charAt(0));
                    emissions[this_state_idx][this_character_idx] = emission_prob;
                }
            }
        }

        Matrix_class result_map = new Matrix_class(initial,transitions,emissions,char_map,label_map,name_map);
        return result_map;
    }

//////////////////////////////////////////////////////////////

    /**
     * Parse a model in the TMHMM 2.0 format.
     (in your own words!!!)
     :param file_like: a file-like object to read and parse.
     :return: a model
     * @param filetext
     */
    public static Matrix_class parse(String filetext)
    {
        String contents = strip_comments(filetext);

        ArrayDeque<String> tokens = tokenize(contents);

        HashMap<String, String> header = parse_header(tokens);

        ArrayDeque<String> names = new ArrayDeque<>();
        HashMap<String, HashMap<String, Object>> states = new HashMap<>();
        while (tokens.size() != 0)
        {
            HashMap<String, Object> state = parse_state(tokens, names);
            states.put(names.removeFirst(), state);
        }

        if (!tokens.isEmpty())
        {
            //bug inside the programe
            System.out.println("Tokens didn't consume completely. Problem within TMHMM reading module");
            System.exit(1);
        }

        normalize_states(states);

        Matrix_class result = to_matrix_form(header.get("alphabet"),states);

        //this is idiotism!!! change it!!! incapsulation!!
        result.setHeader(header);

        return result;

    }
}
