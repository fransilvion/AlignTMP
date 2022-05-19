package UPGMA;

import Help_classes.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fransilvion on 22.10.2016.
 * based on code of this authors:
 * Peter Sestoft, sestoft@itu.dk 1999-12-07 version 0.3
 * Reference:  http://www.itu.dk/people/sestoft/bsa.html
 */
public class UPGMA_Cluster {
    // Cluster identifier
    //when you are creating new cluster it will have (-1+1 = 0) identifier,
    //so it would be easy to find in the array
    int lab;
    // The number of sequences in the cluster
    int card;
    // The height of the node (for weights)
    double height = 0;
    // Left and right children, or null (not sure if parent is needed)
    UPGMA_Cluster left, right, parent;
    // Distances to lower-numbered nodes, or null
    double[] dmat;
    //Just a profile of sequence(s)
    Profile profile;
    //list with indexes of clusters in current cluster
    ArrayList<Integer> profileIndexes = new ArrayList<>();
    //list with ids of profiles, which belong to this cluster
    ArrayList<String> profileIds = new ArrayList<>();

    // Leaves = single sequence
    public UPGMA_Cluster(int lab, Profile seq, double[] dmat) {
        this.lab = lab;
        this.card = 1;
        this.dmat = dmat;
        this.profile = seq;
        this.profileIndexes.add(this.lab);
        this.profileIds.add(seq.getIds()[0]);
        this.profile.initWeights();
    }

    // Cluster = with several sequences;
    public UPGMA_Cluster(int lab, UPGMA_Cluster left, UPGMA_Cluster right, double height,
                         double[] dmat) {
        this.lab = lab + 1;
        this.left = left;
        this.right = right;
        card = left.card + right.card;
        this.height = height;
        this.dmat = dmat;
        //added, but not sure
        this.left.setParent(this);
        this.right.setParent(this);
        //every cluster now contains information about id of its children and its own
        profileIndexes.addAll(left.getProfileIndexes());
        profileIndexes.addAll(right.getProfileIndexes());

        profileIds.addAll(left.getProfileIds());
        profileIds.addAll(right.getProfileIds());
    }

    //Cluster with the profile, add parent (?)
    public UPGMA_Cluster(int lab, Profile profile, int card)
    {
        //clarify!
        this.lab = lab;
        this.profile = profile;
        this.card = card;
    }

    // if distance matrix is null, node is dead.
    public boolean live()
    { return dmat != null; }

    public void kill()
    { dmat = null; }

    public int getLab()
    {
        return this.lab;
    }

    public double getHeight() { return this.height; }

    public void setHeight(double height) { this.height = height; }

    public int getCard() {return this.card; }

    public ArrayList<Integer> getProfileIndexes() { return this.profileIndexes; }

    public ArrayList<String> getProfileIds() { return this.profileIds; }

    public void setCard(int card)
    {
        this.card = card;
    }

    public UPGMA_Cluster getParent()
    {
        return this.parent;
    }

    public void setParent(UPGMA_Cluster parent)
    {
        this.parent = parent;
    }

    //for debugging
    public UPGMA_Cluster getLeft()
    {
        return this.left;
    }

    public void setLeft(UPGMA_Cluster left)
    {
        this.left = left;
    }

    public UPGMA_Cluster getRight()
    {
        return this.right;
    }

    public void setRight(UPGMA_Cluster right)
    {
        this.right = right;
    }

    //for what???
    public Profile getProfile()
    {
        if (left == null && right == null)
        {
            return this.profile;

        }
        else
            return this.right.getProfile();
    }
}
