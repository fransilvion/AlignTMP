package Help_classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Fransilvion on 15.09.2016.
 * special class to hold single (aligned or not) sequence or any quantity of them.
 */
public class Profile {

    //dictionary with key - id of a sequence,
    //value - sequence itself as a char array
    private HashMap<String, String> profile = new HashMap<>();

    //char array with all sequences in profile
    private char[][] sequences;

    //string array with all ids in profile
    //the index of id in this array correspond to the index from sequence array for its seq
    private String[] ids;

    //dictionary with weights (id - double weight)
    private HashMap<String, Double> weight = new HashMap<>();

    //////////////////////////////////////////////////////////////////////////////////////
    //constructors

    //to copy another profile
    public Profile(Profile another) {
        this.profile = another.getProfile();
        this.weight = another.getAllWeights();
        getSeqAndIdArray();
    }

    //in case of one sequence
    public Profile(String id, String sequence) {
        this.profile.put(id, sequence);
        getSeqAndIdArray();
    }

    public Profile() {  }

    public Profile(HashMap<String, String> profile, HashMap<String, Double> weight) {
        this.profile = profile;
        getSeqAndIdArray();
        this.weight = weight;
    }

    //constructor for making a profile out of two arrays, they must be of equal size!!!
    public Profile(String[] id, String[] sequence) throws Exception {
        this.profile = new HashMap<>();
        //this two arrays must be of the same length, otherwise, it's a mistake
        if (id.length != sequence.length){
            throw new Exception("Problems with making a profile - not equal lengths of arrays");
        }
        else {
            for (int i = 0; i < id.length; i++){
                for (int j = 0; j < sequence.length; j++){
                    profile.put(id[i], sequence[i]);
                }
            }
        }
        getSeqAndIdArray();
    }

    //constructor for making a profile out of two arrays, they must be of equal size!!!
    public Profile(String[] id, String[] sequence, HashMap<String, Double> weight) throws Exception {
        this.profile = new HashMap<>();
        //this two arrays must be of the same length, otherwise, it's a mistake
        if (id.length != sequence.length){
            throw new Exception("Problems with making a profile - not equal lengths of arrays");
        }
        else {
            for (int i = 0; i < id.length; i++){
                for (int j = 0; j < sequence.length; j++){
                    profile.put(id[i], sequence[i]);
                }
            }
        }
        getSeqAndIdArray();
        this.weight = weight;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //methods

    //method for adding new sequences to profile
    public void putIntoProfile(String id, String sequence) { this.profile.put(id, sequence); }

    //method - to get a profile by an id
    public Profile getIdProfile(String id) { return new Profile(id, this.profile.get(id)); }

    public void setProfile(HashMap<String, String> profile) { this.profile = profile; }

    public HashMap<String, String> getProfile()
    {
        return this.profile;
    }

    public int getProfileLength() { return this.profile.size(); }

    //merge two profiles (with different sequences!!!) into one
    //getSeqAndIdArray - recalculate corresponding arrays: ids[] and sequences[][]
    public void mergeWithProfile(Profile profile2) {
        this.profile.putAll(profile2.getProfile());
        this.getSeqAndIdArray();
    }

    //get the length of the String in the first value - not conveniet because of dict;
    //because of the aligned sequences they all have the same length
    //if they are not aligned - profile has only one sequence.
    public int getStringSize() { return this.profile.values().iterator().next().length(); }

    //function to get the array of sequences from the corresponding profile
    //indexes will be the same, correspond to each other in different arrays
    private void getSeqAndIdArray(){
        char[][] seqArray = new char[this.profile.size()][];
        String[] idArray = new String[this.profile.size()];
        int i = 0;
        for (Map.Entry<String, String> prof : profile.entrySet()){
            seqArray[i] = prof.getValue().toCharArray();
            idArray[i] = prof.getKey();
            i++;
        }

        this.sequences = seqArray;
        this.ids = idArray;
    }


    //getter for the sequences array:
    public char[][] getSequences() { return this.sequences; }

    //getter for the ids array:
    public String[] getIds() { return this.ids; }

    //divide profile by list of ids
    public Pair<Profile, Profile> divideProfile(ArrayList<String> ids){
        HashMap<String, String> dictForProfile1 = new HashMap<>();
        HashMap<String, String> dictForProfile2 = new HashMap<>();

        HashMap<String, Double> weight1 = new HashMap<>();
        HashMap<String, Double> weight2 = new HashMap<>();

        for (Map.Entry<String, String> entry : this.profile.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            if (ids.contains(key)){
                dictForProfile1.put(key, value);
                weight1.put(key, this.weight.get(key));
            }
            else {
                dictForProfile2.put(key, value);
                weight2.put(key, this.weight.get(key));
            }
        }

        Profile profile1 = new Profile(dictForProfile1, weight1);
        Profile profile2 = new Profile(dictForProfile2, weight2);
        Pair<Profile, Profile> pair = new Pair<>(profile1, profile2);
        return pair;
    }
    //////////////////////////////////////////////////////////////////////////////////////
    //WEIGHTS
    //method for dictionary of weights initialization
    //only one sequence thus we have only one element in the array
    public void initWeights(){
        weight.put(ids[0], 0.0);
    }

    //calculate new weight (Gerstein & Chothia, 1994)
    //delta(wi) - change in weight
    //tn - length of new branch
    //wi - weight from the last step
    //sum(wk) - sum for all k - beyond the particular node
    //delta(wi) = tn * wi/sum(wk)
    public void calculateWeights(double branchHeight, double sum){
        //because weighting is going for only one sequence. thus its [0]
        if (this.getWeight() == 0.0) {
            weight.put(ids[0], branchHeight);
        }
        else {
            double delta_weight = branchHeight * this.getWeight()/sum;
            weight.put(ids[0], this.getWeight() + delta_weight);
        }
    }
    //to return all weights as the dictionary
    public HashMap<String, Double> getAllWeights() { return this.weight; }
    //to return one weight of one sequence
    public double getWeight() { return weight.get(ids[0]); }
    //set weights
    public void setWeight(HashMap<String, Double> weights) {
        this.weight = weights;
    }
}
