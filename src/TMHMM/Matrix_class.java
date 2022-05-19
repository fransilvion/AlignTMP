package TMHMM;

import java.util.HashMap;

/**
 * Created by Fransilvion on 14.09.2016.
 */
public class Matrix_class {
    private double[] initial;
    private double[][] transitions;
    private double[][] emissions;
    private HashMap<Character, Integer> char_map;
    private HashMap<Integer, String> label_map;
    private HashMap<Integer, String> name_map;
    //this field is only added during parse method.
    private HashMap<String, String> header;

    public Matrix_class(double[] initial, double[][] transitions, double[][] emissions,
                        HashMap<Character, Integer> char_map, HashMap<Integer, String> label_map,
                        HashMap<Integer, String> name_map)
    {
        this.initial = initial;
        this.transitions = transitions;
        this.emissions = emissions;
        this.char_map = char_map;
        this.label_map = label_map;
        this.name_map = name_map;
    }

    public double[] getInitial()
    {
        return this.initial;
    }

    public double[][] getTransitions()
    {
        return this.transitions;
    }

    public double[][] getEmissions()
    {
        return this.emissions;
    }

    public HashMap<Character,Integer> getCharMap()
    {
        return this.char_map;
    }

    public HashMap<Integer,String> getLabelMap()
    {
        return this.label_map;
    }

    public HashMap<Integer, String> getNamemap()
    {
        return this.name_map;
    }

    public HashMap<String, String> getHeader()
    {
        return this.header;
    }

    //this is strange, but
    void setHeader(HashMap<String,String> header)
    {
        this.header = header;
    }
}
