package Alignment;

import Help_classes.Gap;
import Help_classes.Profile;
import UPGMA.UPGMA_Cluster;

import java.util.*;

/**
 * Created by Fransilvion on 03.11.2016.
 * class for step by step multiple alignment by guide tree (UPGMA)
 * (for Probs)
 */
public class AlignmentByGuideTreeForProbs {

    private Gap gapPenalty;
    private UPGMA_Cluster root;
    private int treeSize;
    private ProbMultipleSequenceAlignment multipleAlignment;

    //////////////////////////////////////////////////////////////////////////////////////
    //constructor of class
    public AlignmentByGuideTreeForProbs(UPGMA_Cluster root, HashMap<String, double[][]> probs,
                                        Gap gapPenalty, boolean isSum) {
        this.gapPenalty = gapPenalty;
        this.root = root;
        this.treeSize = root.getCard();
        doProgressiveAlignment(probs, isSum);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //progressive alignment - through all branches of guide tree,
    //considering both directions - left and right
    private void doProgressiveAlignment(HashMap<String, double[][]> probs, boolean isSum) {
        //go through all the tree
        System.out.println("PERFORMING ALIGNMENT BY GUIDE TREE");
        while (treeSize > 2) {
            if (root.getLeft().getCard() > 1){
                left(root.getLeft(), probs, isSum);
            }
            if (root.getRight().getCard() > 1){
                right(root.getRight(), probs, isSum);
            }
        }
        this.multipleAlignment = alignProfileOneCluster(root, probs, isSum);
        //for debugging
        System.out.print("Aligned ");
        for (String id : multipleAlignment.getProfile().getIds()) {
            System.out.print(id + " ");
        }
        System.out.println();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //when a node has two profiles - align them together through MSA
    private ProbMultipleSequenceAlignment alignProfileOneCluster(UPGMA_Cluster cluster, HashMap<String, double[][]> probs,
                                                                 boolean isSum) {
        Profile prof1 = new Profile();
        Profile prof2 = new Profile();
        try {
            prof1 = cluster.getLeft().getProfile();
            prof2 = cluster.getRight().getProfile();
        } catch (Exception e){
            System.out.println("Problem with your file. Perhaps, it's only one sequence");
            System.exit(1);
        }
        return new ProbMultipleSequenceAlignment(prof1, prof2, gapPenalty, probs, isSum);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //path through left part of the tree
    private void left(UPGMA_Cluster cluster, HashMap<String, double[][]> probs, boolean isSum) {
        ProbMultipleSequenceAlignment profileAlignment;
        Profile profile;
        //when a node contains 3 profiles (first special case)
        if (cluster.getCard() == 3) {
            //if on left side we have just a leave, we'd better go another way.
            if (cluster.getLeft().getLeft() == null) {
                left(cluster.getRight(), probs, isSum);
            }
            else {
                left(cluster.getLeft(), probs, isSum);
            }
        }
        //when a node contains 2 profiles (second special case)
        else if (cluster.getCard() == 2) {
            profileAlignment = alignProfileOneCluster(cluster, probs, isSum);
            profile = profileAlignment.getProfile();
            UPGMA_Cluster parent = cluster.getParent();
            fixParent(cluster);
            UPGMA_Cluster new_cluster = new UPGMA_Cluster(cluster.getLab(), profile, 1);
            reChild(parent, cluster, new_cluster);
            //for debugging
            System.out.print("Aligned ");
            for (String id : profile.getIds())
            {
                System.out.print(id + " ");
            }
            System.out.println();
            treeSize--;
        }
        //when a node contains more than 3 sequences (you go deeper)
        else if (cluster.getLeft() != null)
        {
            left(cluster.getLeft(), probs, isSum);
        }
        //when a node contains only one profile (third special case)
        //go back to parent, choose right branch and explore it to the left
        else
        {
            left(cluster.getParent().getRight(), probs, isSum);
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////
    //path through right part of the tree
    public void right(UPGMA_Cluster cluster, HashMap<String, double[][]> probs, boolean isSum) {
        ProbMultipleSequenceAlignment profileAlignment;
        Profile profile;
        //when a node contains 3 profiles (first special case)
        if (cluster.getCard() == 3) {
            //if on left side we have just a leave, we'd better go another way.
            if (cluster.getRight().getRight() == null) {
                right(cluster.getLeft(), probs, isSum);
            }
            else {
                right(cluster.getRight(), probs, isSum);
            }
        }
        //when a node contains 2 profiles (second special case)
        else if (cluster.getCard() == 2) {
            profileAlignment = alignProfileOneCluster(cluster, probs, isSum);
            profile = profileAlignment.getProfile();
            UPGMA_Cluster parent = cluster.getParent();
            fixParent(cluster);
            UPGMA_Cluster new_cluster = new UPGMA_Cluster(cluster.getLab(), profile, 1);
            reChild(parent, cluster, new_cluster);
            //for debugging
            System.out.print("Aligned ");
            for (String id : profile.getIds()) {
                System.out.print(id + " ");
            }
            System.out.println();
            treeSize--;
        }
        //when a node contains more than 3 sequences (you go deeper)
        else if (cluster.getRight() != null) {
            right(cluster.getRight(), probs, isSum);
        }
        //when a node contains only one profile (third special case)
        //go back to parent, choose right branch and explore it to the left
        else {
            right(cluster.getParent().getLeft(), probs, isSum);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //rechange amount of sequences for all corresponding parents
    private void fixParent(UPGMA_Cluster cluster) {
        UPGMA_Cluster parent = cluster.getParent();
        int x;
        while(parent != null) {
            x = parent.getCard();
            parent.setCard(x-1);
            parent = parent.getParent();
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////
    //rechild our parent and delete old child
    private void reChild(UPGMA_Cluster parent, UPGMA_Cluster cluster, UPGMA_Cluster new_cluster) {
        if (parent.getLeft().equals(cluster)) {
            parent.setLeft(new_cluster);
            new_cluster.setParent(parent);
        }
        else {
            parent.setRight(new_cluster);
            new_cluster.setParent(parent);
        }
        //delete child
        cluster = null;
    }
    //////////////////////////////////////////////////////////////////////////////////////
    //get the multipleAlignment
    public ProbMultipleSequenceAlignment getMultipleAlignment() { return multipleAlignment; }
}
