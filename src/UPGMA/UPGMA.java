package UPGMA;

import Help_classes.Profile;

import java.util.HashMap;

/**
 * Created by Fransilvion on 22.10.2016.
 * based on code of this authors:
 * Peter Sestoft, sestoft@itu.dk 1999-12-07 version 0.3
 * Reference:  http://www.itu.dk/people/sestoft/bsa.html
 */
public class UPGMA {
    // The number of clusters created so far
    private int K;
    // The nodes (clusters) of the resulting tree
    private UPGMA_Cluster[] cluster;
    //helpful variables to calculate the length of branch and corresponding weight
    private double nodeHeight = 0;

    //public UPGMA(double[][] ds)
    //char[][] sequences used to be HashMap<Integer, Sequence> orient
    public UPGMA(double[][] ds, String[] ids, char[][] sequences) {

        // amount of sequences
        int N = ds.length;

        // total number of clusters for this number of sequences
        cluster = new UPGMA_Cluster[2*N-1];

        //create item for every cluster
        for (int i=0; i<N; i++){
            cluster[i] = new UPGMA_Cluster(i, new Profile(ids[i], new String(sequences[i])), ds[i]);
        }
        K = N;
        while (K < 2*N-1)
            findAndJoin();
    }

    public UPGMA_Cluster getRoot()
    { return cluster[K-1]; }

    //function to get distance
    private double distance(int i, int j) {
        // at the begining
        //try {
            return cluster[Math.max(i,j)].dmat[Math.min(i,j)];
        //}
        //catch (Exception e) {
            //System.out.println("problem with " + i + " " + j);
            //System.exit(1);
            //return 0;
        //}
    } // maybe you should switch here max and min

    // Find closest two live clusters and join them
    private void findAndJoin() {
        int mini = -1, minj = -1;
        double mind = Double.POSITIVE_INFINITY;
        for (int i=0; i<K; i++){
            if (cluster[i].live()){
                for (int j=0; j<i; j++){
                    if (cluster[j].live()) {
                        double d = distance(i, j);
                        if (Double.isNaN(d)){
                            System.out.println("Problem with building guide tree. Try to change negative constant value");
                            System.exit(1);
                        }
                        if (d < mind) {
                            mind = d;
                            mini = i;
                            minj = j;
                        }
                    }
                }
            }
        }
        join(mini, minj);
        //for debugging
        //System.out.println("Joined sequences " + mini + " with " + minj);
    }

    // recalculate distances from new cluster to others
    private void join(int i, int j) {
        // Join i and j to form node K
        // System.out.println("Joining " + (i+1) + " and " + (j+1) + " to form "
        //	       + (K+1) + " at height " + (int)(d(i, j) * 50)/100.0);
        double[] dmat = new double[K];
        for (int m=0; m<K; m++){
            if (cluster[m].live() && m != i && m != j){
                dmat[m] = (distance(i, m) * cluster[i].card + distance(j, m) * cluster[j].card)
                        / (cluster[i].card + cluster[j].card);
            }
        }
        nodeHeight = distance(i, j) / 2;
        cluster[K] = new UPGMA_Cluster(K, cluster[i], cluster[j], nodeHeight, dmat);

        calculateWeightsForCluster(cluster[i]);
        calculateWeightsForCluster(cluster[j]);

        cluster[i].kill();
        cluster[j].kill();
        K++;
    }
    //calculate weights for profiles, lying beyond the node in the cluster
    private void calculateWeightsForCluster(UPGMA_Cluster cl){
        //calculating the sum of distances beyond the new node
        double sum = 0;
        for (int index : cl.getProfileIndexes()){
            if (cluster[index].getProfile().getWeight() == 0.0){
                sum += nodeHeight;
            } else {
                sum += cluster[index].getProfile().getWeight();
            }
        }
        double branchHeight;
        //weighting of all sequences, that correspond to particular node:
        for (int index : cl.getProfileIndexes()){
            branchHeight = nodeHeight - cluster[index].getHeight();
            cluster[index].setHeight(nodeHeight);
            cluster[index].getProfile().calculateWeights(branchHeight, sum);
        }
    }

    public UPGMA_Cluster[] getCluster(){ return cluster; }
    public int getNumberOfClusters() { return K; }

}
