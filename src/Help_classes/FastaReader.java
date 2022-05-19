package Help_classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Fransilvion on 15.09.2016.
 * Speciall class which is dedicated to read sequence from input fasta.file
 * (add another formats?)
 */
public class FastaReader {

    //amino acid compound for testing if you put not appropriate sequence
    private static final String COMPOUND = "acdefghiklmnpqrstvwyACDEFGHIKLMNPQRSTVWY";

    private FastaReader() {

    }

    //read sequences from the fasta file
    public static Profile readSequences(File file) throws IOException {

        //simplest way to go through file in java
        //should find some more effective way!
        BufferedReader br = new BufferedReader(new FileReader(file));

        return process(br);
    }

    private static Profile process(BufferedReader br) throws IOException {

        String sCurrentLine;
        StringBuilder sb = new StringBuilder();
        Profile sequences = new Profile();

        String id = "";
        do {
            sCurrentLine = br.readLine();
            if (sCurrentLine == null) {
                if (id.equals("") || sb.length() == 0) {
                    System.out.println("Wrong format of the last sequence");
                    System.exit(1);
                }
                else {
                    if (sequences.getProfile().containsKey(id)) {
                        System.out.println("You had repetitions in your sequence: id " + id);
                        System.out.println("It will be deleted");
                    }
                    sequences.putIntoProfile(id, sb.toString());
                    break;
                }
            }
            else if (sCurrentLine.startsWith(">")) {
                if (!id.equals("") && sb.length() > 0) {
                    if (sequences.getProfile().containsKey(id)) {
                        System.out.println("You had repetitions in your sequence: id " + id);
                        System.out.println("It will be deleted");
                    }
                    sequences.putIntoProfile(id, sb.toString());

                }
                sb.setLength(0); //this is faster than allocating new buffers, better memory utilization (same buffer)
                id = sCurrentLine.trim(); //not sure if trim() is necessary
            }
            else {
                for (int i = 0; i < sCurrentLine.length(); i++) {
                    //if any sequence contains wrong symbols, we should not create
                    //such sequence
                    if (COMPOUND.indexOf(sCurrentLine.charAt(i)) < 0) {
                        try {
                            throw new WrongCompoundException("You have bad characters in the sequences or bad format of the file");
                        } catch (WrongCompoundException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }

                    }
                }
                //we are adding sequences
                sb.append(sCurrentLine.trim());
            }

        } while (sCurrentLine != null); //I don't know, why is it true all the time

        br.close();

        //in order to make all relevant arrays through getSeqAndIdArray()
        Profile profile = new Profile(sequences);
        return profile;

    }


}
