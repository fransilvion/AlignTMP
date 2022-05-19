package Alignment;

import Help_classes.Gap;
import Help_classes.Profile;

import java.util.HashMap;

/**
 * Created by Fransilvion on 03.02.2017.
 */
public abstract class Alignment {

    //Gap - class for gap penalties
    Gap gapPenalty;

    //for creating aligned sequence, check for thread safety
    StringBuilder[] mAlignmentSeqA, mAlignmentSeqB;

    //score of the alignment;
    double score;

    //two sequence (or profiles) for alignment;
    //target - A, query - B
    char[][] targetA, queryB;
    String[] targetAids, queryBids;

    //will contain the result of the alignment
    String[] resultProfileA, resultProfileB;

    //contains the final alignment of sequences
    Profile itogProfile;

    //three matrices for affine gap penalties;
    //H - matrix for an alignment, which ends (a/-)
    //mD - matrix for an alignment, which ends (a/b)
    //V - matrix for an alignment, which ends (-/b)
    double[][] mD, V, H;

    //three matrices for pointers (for backtrack procedure)
    char[][] pointD, pointV, pointH;

    //length of the alignment
    int profileALength, profileBLength;

    //number of sequences
    int profileASize, profileBSize;

    //////////////////////////////////////////////////////////////////////////////////////
    //constructor of the abstract class
    public Alignment(Profile targetA, Profile queryB, Gap gapPenalty){

        //getting gap penalty
        this.gapPenalty = gapPenalty;

        //extracting info from profiles
        this.targetA = targetA.getSequences();
        this.queryB = queryB.getSequences();
        this.targetAids = targetA.getIds();
        this.queryBids = queryB.getIds();

        this.profileALength = targetA.getStringSize();
        this.profileBLength = queryB.getStringSize();
        this.profileASize = targetA.getProfile().size();
        this.profileBSize = queryB.getProfile().size();
        mAlignmentSeqA = new StringBuilder[profileASize];
        mAlignmentSeqB = new StringBuilder[profileBSize];

        resultProfileA = new String[profileASize];
        resultProfileB = new String[profileBSize];

        //initialize arrays of StringBuilders
        for (int x = 0; x < profileASize; x++) {
            mAlignmentSeqA[x] = new StringBuilder();
        }
        for (int x = 0; x < profileBSize; x++) {
            mAlignmentSeqB[x] = new StringBuilder();
        }

    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //to initialize the matrices
    protected void init() {
        mD = new double[profileBLength + 1][profileALength + 1];
        V = new double[profileBLength + 1][profileALength + 1];
        H = new double[profileBLength + 1][profileALength + 1];

        pointD = new char[profileBLength + 1][profileALength + 1];
        pointV = new char[profileBLength + 1][profileALength + 1];
        pointH = new char[profileBLength + 1][profileALength + 1];

        for (int i = 0; i <= profileBLength; i++) {
            for (int j = 0; j <= profileALength; j++) {
                if (i == 0) {
                    if (j == 0) {
                        V[i][j] = 0;
                        H[i][j] = 0;
                        mD[i][j] = 0;
                    }
                    else {
                        H[i][j] = Double.NEGATIVE_INFINITY;
                        mD[i][j] = Double.NEGATIVE_INFINITY;
                        //mD[i][j] = gOP;
                        //V[i][j] = gOP + gExt * j;
                        V[i][j] = 0;
                    }


                } else if (j == 0) {
                    mD[i][j] = Double.NEGATIVE_INFINITY;
                    //mD[i][j] = gOP;
                    //H[i][j] = gOP + gExt * i;
                    H[i][j] = 0;
                    V[i][j] = Double.NEGATIVE_INFINITY;
                } else {
                    mD[i][j] = 0;
                    H[i][j] = 0;
                    V[i][j] = 0;
                }

                pointD[i][j] = 'D';
                pointH[i][j] = 'H';
                pointV[i][j] = 'V';
            }
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //process of the matrix with appropriate numbers
    //because it has different implementations in NW and MSA, it should be abstract
    //abstract method cannot be private so its protected
    protected abstract void process();

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //find resulting alignment through the backtrack procedure (using pointers);
    protected void backtrack() {
        //length of two sequences;
        //int i = mProfB[0].length;
        //int j = mProfA[0].length;
        int bLength = profileBLength;
        int aLength = profileALength;

        //helping variable for checking our current state
        char currentState;

        //finding the maximal number in the right corner
        if (mD[bLength][aLength] > V[bLength][aLength] &&
                mD[bLength][aLength] > H[bLength][aLength]) {
            score = mD[bLength][aLength];
            currentState = 'D';
        }
        else if (V[bLength][aLength] > mD[bLength][aLength] &&
                V[bLength][aLength] > H[bLength][aLength]) {
            score = V[bLength][aLength];
            currentState = 'V';
        }
        else {
            score = H[bLength][aLength];
            currentState = 'H';
        }

        this.mD = null;
        this.H = null;
        this.V = null;

        //performing backtrack procedure
        while (bLength > 0 || aLength > 0) {
            switch (currentState) {
                case 'D':

                    int y, x;
                    //x = this.mProfA.length - 1;
                    x = this.profileASize - 1;
                    for (y = x; y >= 0; y--) {
                        mAlignmentSeqA[y].append(targetA[y][aLength - 1]);
                    }

                    x = profileBSize - 1;
                    for (y = x; y >= 0; y--) {
                        mAlignmentSeqB[y].append(queryB[y][bLength - 1]);
                    }

                    //checking our next direction
                    switch (pointD[bLength][aLength]) {
                        case 'D':
                            currentState = 'D';
                            break;
                        case 'V':
                            currentState = 'V';
                            break;
                        case 'H':
                            currentState = 'H';
                            break;
                    }
                    bLength--;
                    aLength--;
                    break;
                case 'V':

                    x = profileBSize - 1;
                    for (y = x; y >= 0; y--) {
                        mAlignmentSeqB[y].append('-');
                    }

                    x = profileASize - 1;
                    for (y = x; y >= 0; y--) {
                        mAlignmentSeqA[y].append(targetA[y][aLength - 1]);
                    }
                    //checking our next direction
                    switch (pointV[bLength][aLength]) {
                        case 'D':
                            currentState = 'D';
                            break;
                        case 'V':
                            currentState = 'V';
                            break;
                        case 'H':
                            currentState = 'H';
                            break;
                    }
                    aLength--;
                    break;
                case 'H':

                    //x = this.mProfB.length - 1;
                    x = profileBSize - 1;
                    for (y = x; y >= 0; y--) {
                        mAlignmentSeqB[y].append(queryB[y][bLength - 1]);
                    }

                    //x = this.mProfA.length - 1;
                    x = profileASize - 1;
                    for (y = x; y >= 0; y--) {
                        mAlignmentSeqA[y].append('-');
                    }
                    //going in the H pointer matrix
                    switch (pointH[bLength][aLength]) {
                        case 'D':
                            currentState = 'D';
                            break;
                        case 'V':
                            currentState = 'V';
                            break;
                        case 'H':
                            currentState = 'H';
                            break;
                    }
                    bLength--;
                    break;
            }
        }

        //building an array of final sequence alignment;
        for (int x = 0; x < mAlignmentSeqA.length; x++) {
            resultProfileA[x] = new StringBuffer(mAlignmentSeqA[x]).reverse().toString();
        }

        for (int x = 0; x < mAlignmentSeqB.length; x++) {
            resultProfileB[x] = new StringBuffer(mAlignmentSeqB[x]).reverse().toString();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //perform the whole alignment procedure
    public void align(){

        //#1 - first step;
        init();

        //#2 - second step;
        process();

        //#3 - third step;
        backtrack();

        //#4 - fourth step; making of the final profile;
        try {
            Profile profileA = new Profile(targetAids, resultProfileA);
            Profile profileB = new Profile(queryBids, resultProfileB);
            profileA.mergeWithProfile(profileB);

            this.itogProfile = profileA;
            profileA = null;
            profileB = null;

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        //#5 - fifth step; deleting information from matrices to free memory
        this.pointH = null;
        this.pointD = null;
        this.pointV = null;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //get the final Profile with the alignment
    public Profile getProfile()
    {
        return itogProfile;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //get the final score of the alignment
    public double getScore() { return score; }

}
