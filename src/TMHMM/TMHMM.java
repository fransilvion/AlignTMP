package TMHMM;

/**
 * Created by Fransilvion on 14.09.2016.
 */
public class TMHMM {
    private TMHMM()
    {

    }

    public static final String TMHMM =
            "# TMHMM2 model\n" +
                    "\n" +
                    "\n" +
                    "########## HEADER ##################################################\n" +
                    "header {\n" +
                    "\talphabet ACDEFGHIKLMNPQRSTVWY;\n" +
                    "\twildcards BXZ;\n" +
                    "}\n" +
                    "########## STATE begin #############################################\n" +
                    "begin {\ttrans\n" +
                    "\t\tin10: 0.549251\n" +
                    "\t\toutglob10: 0.207469\n" +
                    "\t\tout10: 0.24328;\n" +
                    "\ttype 0;\n" +
                    "\tend 0;\n" +
                    "\tletter NULL;\n" +
                    "\t}\n" +
                    "########## STATE in10 ##############################################\n" +
                    "in10 {\ttrans\n" +
                    "\t\tin11: 0.995851\n" +
                    "\t\tin29: 0.00111283\n" +
                    "\t\tohelixi1: 0.00303586;\n" +
                    "\tlabel i;\n" +
                    "\tonly\t A:0.0713632 C:0.0188491 D:0.043014 E:0.0471663 F:0.0298789\n" +
                    "\t\t G:0.0564853 H:0.0233906 I:0.038904 K:0.0894525 L:0.0551519\n" +
                    "\t\t M:0.0427067 N:0.0544149 P:0.0370019 Q:0.0524006 R:0.114758\n" +
                    "\t\t S:0.0661936 T:0.0689907 V:0.0416332 W:0.0146085 Y:0.0336358;\n" +
                    "\t}\n" +
                    "########## STATE in11 ##############################################\n" +
                    "in11 {\ttrans\n" +
                    "\t\tin12: 0.976066\n" +
                    "\t\tin28: 0.0239339\n" +
                    "\t\tin29: 1.08323e-09;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in12 ##############################################\n" +
                    "in12 {\ttrans\n" +
                    "\t\tin13: 0.895077\n" +
                    "\t\tin27: 0.104922\n" +
                    "\t\tin28: 1.76891e-06;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in13 ##############################################\n" +
                    "in13 {\ttrans\n" +
                    "\t\tin14: 0.979527\n" +
                    "\t\tin26: 0.0204673\n" +
                    "\t\tin27: 5.81809e-06;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in14 ##############################################\n" +
                    "in14 {\ttrans\n" +
                    "\t\tin15: 0.993341\n" +
                    "\t\tin25: 0.00660812\n" +
                    "\t\tin26: 5.08664e-05;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in15 ##############################################\n" +
                    "in15 {\ttrans\n" +
                    "\t\tin16: 0.738969\n" +
                    "\t\tin24: 0.159734\n" +
                    "\t\tin25: 0.101297;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in16 ##############################################\n" +
                    "in16 {\ttrans\n" +
                    "\t\tin17: 0.999938\n" +
                    "\t\tin23: 1.4427e-06\n" +
                    "\t\tin24: 6.0424e-05;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in17 ##############################################\n" +
                    "in17 {\ttrans\n" +
                    "\t\tin18: 0.973203\n" +
                    "\t\tin22: 0.0168132\n" +
                    "\t\tin23: 0.00998417;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in18 ##############################################\n" +
                    "in18 {\ttrans\n" +
                    "\t\tin19: 0.977498\n" +
                    "\t\tin21: 0.0217216\n" +
                    "\t\tin22: 0.000780768;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in19 ##############################################\n" +
                    "in19 {\ttrans\n" +
                    "\t\tin20: 0.16223\n" +
                    "\t\tin21: 0.10126\n" +
                    "\t\tinglob1: 0.73651;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in20 ##############################################\n" +
                    "in20 {\ttrans\n" +
                    "\t\tin21: 1;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in21 ##############################################\n" +
                    "in21 {\ttrans\n" +
                    "\t\tin22: 1;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in22 ##############################################\n" +
                    "in22 {\ttrans\n" +
                    "\t\tin23: 1;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in23 ##############################################\n" +
                    "in23 {\ttrans\n" +
                    "\t\tin24: 1;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in24 ##############################################\n" +
                    "in24 {\ttrans\n" +
                    "\t\tin25: 1;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in25 ##############################################\n" +
                    "in25 {\ttrans\n" +
                    "\t\tin26: 1;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in26 ##############################################\n" +
                    "in26 {\ttrans\n" +
                    "\t\tin27: 1;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in27 ##############################################\n" +
                    "in27 {\ttrans\n" +
                    "\t\tin28: 1;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in28 ##############################################\n" +
                    "in28 {\ttrans\n" +
                    "\t\tin29: 1;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE in29 ##############################################\n" +
                    "in29 {\ttrans\n" +
                    "\t\tohelixi1: 1;\n" +
                    "\tlabel i;\n" +
                    "\ttied_letter in10;\n" +
                    "\t}\n" +
                    "########## STATE inglob1 ###########################################\n" +
                    "inglob1 {\ttrans\n" +
                    "\t\tin20: 0.0132918\n" +
                    "\t\tinglob1: 0.986708;\n" +
                    "\tlabel i;\n" +
                    "\tonly\t A:0.0773341 C:0.0212026 D:0.0556231 E:0.0789783 F:0.0291466\n" +
                    "\t\t G:0.0821038 H:0.02529 I:0.0392883 K:0.0466567 L:0.0718204\n" +
                    "\t\t M:0.0191835 N:0.0490524 P:0.0671432 Q:0.0472671 R:0.0492684\n" +
                    "\t\t S:0.0852997 T:0.0610192 V:0.0528717 W:0.0166592 Y:0.0247916;\n" +
                    "\t}\n" +
                    "########## STATE outglob10 #########################################\n" +
                    "outglob10 {\ttrans\n" +
                    "\t\toutglob11: 1\n" +
                    "\t\toutglob29: 0\n" +
                    "\t\tihelixo1: 0;\n" +
                    "\tlabel O;\n" +
                    "\tonly\t A:0.0693743 C:0.0149605 D:0.0406956 E:0.0538397 F:0.0531778\n" +
                    "\t\t G:0.0792746 H:0.0221055 I:0.0440866 K:0.0565779 L:0.0988165\n" +
                    "\t\t M:0.0432829 N:0.0414346 P:0.0615562 Q:0.0412212 R:0.0677628\n" +
                    "\t\t S:0.0732544 T:0.0524824 V:0.0445653 W:0.0140309 Y:0.0275;\n" +
                    "\t}\n" +
                    "########## STATE outglob11 #########################################\n" +
                    "outglob11 {\ttrans\n" +
                    "\t\toutglob12: 1\n" +
                    "\t\toutglob28: 0\n" +
                    "\t\toutglob29: 0;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob12 #########################################\n" +
                    "outglob12 {\ttrans\n" +
                    "\t\toutglob13: 1\n" +
                    "\t\toutglob27: 0\n" +
                    "\t\toutglob28: 0;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob13 #########################################\n" +
                    "outglob13 {\ttrans\n" +
                    "\t\toutglob14: 1\n" +
                    "\t\toutglob26: 0\n" +
                    "\t\toutglob27: 0;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob14 #########################################\n" +
                    "outglob14 {\ttrans\n" +
                    "\t\toutglob15: 1\n" +
                    "\t\toutglob25: 0\n" +
                    "\t\toutglob26: 0;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob15 #########################################\n" +
                    "outglob15 {\ttrans\n" +
                    "\t\toutglob16: 1\n" +
                    "\t\toutglob24: 0\n" +
                    "\t\toutglob25: 0;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob16 #########################################\n" +
                    "outglob16 {\ttrans\n" +
                    "\t\toutglob17: 1\n" +
                    "\t\toutglob23: 0\n" +
                    "\t\toutglob24: 0;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob17 #########################################\n" +
                    "outglob17 {\ttrans\n" +
                    "\t\toutglob18: 1\n" +
                    "\t\toutglob22: 0\n" +
                    "\t\toutglob23: 0;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob18 #########################################\n" +
                    "outglob18 {\ttrans\n" +
                    "\t\toutglob19: 1\n" +
                    "\t\toutglob21: 0\n" +
                    "\t\toutglob22: 0;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob19 #########################################\n" +
                    "outglob19 {\ttrans\n" +
                    "\t\toutglobLong: 1\n" +
                    "\t\toutglob20: 0\n" +
                    "\t\toutglob21: 0;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglobLong #######################################\n" +
                    "outglobLong {\ttrans\n" +
                    "\t\toutglobLong: 0.999093\n" +
                    "\t\toutglob20: 0.000906913;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter inglob1;\n" +
                    "\t}\n" +
                    "########## STATE outglob20 #########################################\n" +
                    "outglob20 {\ttrans\n" +
                    "\t\toutglob21: 1;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob21 #########################################\n" +
                    "outglob21 {\ttrans\n" +
                    "\t\toutglob22: 1;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob22 #########################################\n" +
                    "outglob22 {\ttrans\n" +
                    "\t\toutglob23: 1;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob23 #########################################\n" +
                    "outglob23 {\ttrans\n" +
                    "\t\toutglob24: 1;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob24 #########################################\n" +
                    "outglob24 {\ttrans\n" +
                    "\t\toutglob25: 1;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob25 #########################################\n" +
                    "outglob25 {\ttrans\n" +
                    "\t\toutglob26: 1;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob26 #########################################\n" +
                    "outglob26 {\ttrans\n" +
                    "\t\toutglob27: 1;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob27 #########################################\n" +
                    "outglob27 {\ttrans\n" +
                    "\t\toutglob28: 1;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob28 #########################################\n" +
                    "outglob28 {\ttrans\n" +
                    "\t\toutglob29: 1;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE outglob29 #########################################\n" +
                    "outglob29 {\ttrans\n" +
                    "\t\tihelixo1: 1;\n" +
                    "\tlabel O;\n" +
                    "\ttied_letter outglob10;\n" +
                    "\t}\n" +
                    "########## STATE out10 #############################################\n" +
                    "out10 {\ttrans\n" +
                    "\t\tout11: 1\n" +
                    "\t\tout29: 3.54571e-14\n" +
                    "\t\tihelixo1: 0;\n" +
                    "\tlabel o;\n" +
                    "\tonly\t A:0.0690346 C:0.0129673 D:0.0627756 E:0.0541851 F:0.0425402\n" +
                    "\t\t G:0.0835626 H:0.0271581 I:0.0292546 K:0.0530401 L:0.0822093\n" +
                    "\t\t M:0.0334625 N:0.0506017 P:0.0693889 Q:0.0389539 R:0.0432109\n" +
                    "\t\t S:0.0863749 T:0.0587278 V:0.0480586 W:0.0198473 Y:0.0346461;\n" +
                    "\t}\n" +
                    "########## STATE out11 #############################################\n" +
                    "out11 {\ttrans\n" +
                    "\t\tout12: 0.910217\n" +
                    "\t\tout28: 0.0495866\n" +
                    "\t\tout29: 0.0401965;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out12 #############################################\n" +
                    "out12 {\ttrans\n" +
                    "\t\tout13: 0.984498\n" +
                    "\t\tout27: 0.00440955\n" +
                    "\t\tout28: 0.0110921;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out13 #############################################\n" +
                    "out13 {\ttrans\n" +
                    "\t\tout14: 0.997286\n" +
                    "\t\tout26: 0.00258189\n" +
                    "\t\tout27: 0.000132519;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out14 #############################################\n" +
                    "out14 {\ttrans\n" +
                    "\t\tout15: 0.812906\n" +
                    "\t\tout25: 0.0130127\n" +
                    "\t\tout26: 0.174082;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out15 #############################################\n" +
                    "out15 {\ttrans\n" +
                    "\t\tout16: 0.951951\n" +
                    "\t\tout24: 0.0400992\n" +
                    "\t\tout25: 0.00795001;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out16 #############################################\n" +
                    "out16 {\ttrans\n" +
                    "\t\tout17: 0.660205\n" +
                    "\t\tout23: 0.33979\n" +
                    "\t\tout24: 4.76867e-06;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out17 #############################################\n" +
                    "out17 {\ttrans\n" +
                    "\t\tout18: 0.992733\n" +
                    "\t\tout22: 0.0072618\n" +
                    "\t\tout23: 5.34599e-06;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out18 #############################################\n" +
                    "out18 {\ttrans\n" +
                    "\t\tout19: 0.971165\n" +
                    "\t\tout21: 0.0119931\n" +
                    "\t\tout22: 0.0168416;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out19 #############################################\n" +
                    "out19 {\ttrans\n" +
                    "\t\toutglobShort: 0.770716\n" +
                    "\t\tout20: 0.00133523\n" +
                    "\t\tout21: 0.227948;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE outglobShort ######################################\n" +
                    "outglobShort {\ttrans\n" +
                    "\t\toutglobShort: 0.960334\n" +
                    "\t\tout20: 0.0396664;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter inglob1;\n" +
                    "\t}\n" +
                    "########## STATE out20 #############################################\n" +
                    "out20 {\ttrans\n" +
                    "\t\tout21: 1;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out21 #############################################\n" +
                    "out21 {\ttrans\n" +
                    "\t\tout22: 1;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out22 #############################################\n" +
                    "out22 {\ttrans\n" +
                    "\t\tout23: 1;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out23 #############################################\n" +
                    "out23 {\ttrans\n" +
                    "\t\tout24: 1;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out24 #############################################\n" +
                    "out24 {\ttrans\n" +
                    "\t\tout25: 1;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out25 #############################################\n" +
                    "out25 {\ttrans\n" +
                    "\t\tout26: 1;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out26 #############################################\n" +
                    "out26 {\ttrans\n" +
                    "\t\tout27: 1;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out27 #############################################\n" +
                    "out27 {\ttrans\n" +
                    "\t\tout28: 1;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out28 #############################################\n" +
                    "out28 {\ttrans\n" +
                    "\t\tout29: 1;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE out29 #############################################\n" +
                    "out29 {\ttrans\n" +
                    "\t\tihelixo1: 1;\n" +
                    "\tlabel o;\n" +
                    "\ttied_letter out10;\n" +
                    "\t}\n" +
                    "########## STATE ohelixi1 ##########################################\n" +
                    "ohelixi1 {\ttrans\n" +
                    "\t\tohelixi2: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\tonly\t A:0.0890049 C:0.0170933 D:0.00159548 E:0.00489647 F:0.108081\n" +
                    "\t\t G:0.0692068 H:0.00923517 I:0.113471 K:0.00466208 L:0.19417\n" +
                    "\t\t M:0.0239901 N:0.0233656 P:0.0145168 Q:0.00487025 R:0.0127643\n" +
                    "\t\t S:0.0455139 T:0.0292949 V:0.128208 W:0.0399529 Y:0.0661077;\n" +
                    "\t}\n" +
                    "########## STATE ohelixi2 ##########################################\n" +
                    "ohelixi2 {\ttrans\n" +
                    "\t\tohelixi3: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixi1;\n" +
                    "\t}\n" +
                    "########## STATE ohelixi3 ##########################################\n" +
                    "ohelixi3 {\ttrans\n" +
                    "\t\tohelixi4: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixi1;\n" +
                    "\t}\n" +
                    "########## STATE ohelixi4 ##########################################\n" +
                    "ohelixi4 {\ttrans\n" +
                    "\t\tohelixi5: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixi1;\n" +
                    "\t}\n" +
                    "########## STATE ohelixi5 ##########################################\n" +
                    "ohelixi5 {\ttrans\n" +
                    "\t\tohelixi6: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixi1;\n" +
                    "\t}\n" +
                    "########## STATE ohelixi6 ##########################################\n" +
                    "ohelixi6 {\ttrans\n" +
                    "\t\tohelixi7: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelixi7 ##########################################\n" +
                    "ohelixi7 {\ttrans\n" +
                    "\t\tohelixm: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelixm ###########################################\n" +
                    "ohelixm {\ttrans\n" +
                    "\t\tohelix2: 0.000534023\n" +
                    "\t\tohelix3: 0.000567583\n" +
                    "\t\tohelix4: 0.00064457\n" +
                    "\t\tohelix5: 0.00159544\n" +
                    "\t\tohelix6: 0.000669433\n" +
                    "\t\tohelix7: 0.00161008\n" +
                    "\t\tohelix8: 0.000762887\n" +
                    "\t\tohelix9: 0.000789084\n" +
                    "\t\tohelix10: 0.000868204\n" +
                    "\t\tohelix11: 0.00519996\n" +
                    "\t\tohelix12: 0.00774891\n" +
                    "\t\tohelix13: 0.0108947\n" +
                    "\t\tohelix14: 0.697722\n" +
                    "\t\tohelix15: 0.0444777\n" +
                    "\t\tohelix16: 0.0328707\n" +
                    "\t\tohelix17: 0.111827\n" +
                    "\t\tohelix18: 0.0324248\n" +
                    "\t\tohelix19: 0.0448694\n" +
                    "\t\tohelix20: 0.00212234\n" +
                    "\t\tohelix21: 0.00107586\n" +
                    "\t\tohelixo1: 0.00072618;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\tonly\t A:0.110896 C:0.0254931 D:0.00235724 E:0.00383965 F:0.0942003\n" +
                    "\t\t G:0.0818095 H:0.00408389 I:0.144377 K:0.00236432 L:0.182902\n" +
                    "\t\t M:0.0385107 N:0.00978815 P:0.0201094 Q:0.00437833 R:0.00115335\n" +
                    "\t\t S:0.0421756 T:0.0514071 V:0.132167 W:0.0158643 Y:0.0321232;\n" +
                    "\t}\n" +
                    "########## STATE ohelix2 ###########################################\n" +
                    "ohelix2 {\ttrans\n" +
                    "\t\tohelix3: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix3 ###########################################\n" +
                    "ohelix3 {\ttrans\n" +
                    "\t\tohelix4: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix4 ###########################################\n" +
                    "ohelix4 {\ttrans\n" +
                    "\t\tohelix5: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix5 ###########################################\n" +
                    "ohelix5 {\ttrans\n" +
                    "\t\tohelix6: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix6 ###########################################\n" +
                    "ohelix6 {\ttrans\n" +
                    "\t\tohelix7: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix7 ###########################################\n" +
                    "ohelix7 {\ttrans\n" +
                    "\t\tohelix8: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix8 ###########################################\n" +
                    "ohelix8 {\ttrans\n" +
                    "\t\tohelix9: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix9 ###########################################\n" +
                    "ohelix9 {\ttrans\n" +
                    "\t\tohelix10: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix10 ##########################################\n" +
                    "ohelix10 {\ttrans\n" +
                    "\t\tohelix11: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix11 ##########################################\n" +
                    "ohelix11 {\ttrans\n" +
                    "\t\tohelix12: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix12 ##########################################\n" +
                    "ohelix12 {\ttrans\n" +
                    "\t\tohelix13: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix13 ##########################################\n" +
                    "ohelix13 {\ttrans\n" +
                    "\t\tohelix14: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix14 ##########################################\n" +
                    "ohelix14 {\ttrans\n" +
                    "\t\tohelix15: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix15 ##########################################\n" +
                    "ohelix15 {\ttrans\n" +
                    "\t\tohelix16: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix16 ##########################################\n" +
                    "ohelix16 {\ttrans\n" +
                    "\t\tohelix17: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix17 ##########################################\n" +
                    "ohelix17 {\ttrans\n" +
                    "\t\tohelix18: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix18 ##########################################\n" +
                    "ohelix18 {\ttrans\n" +
                    "\t\tohelix19: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix19 ##########################################\n" +
                    "ohelix19 {\ttrans\n" +
                    "\t\tohelix20: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix20 ##########################################\n" +
                    "ohelix20 {\ttrans\n" +
                    "\t\tohelix21: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelix21 ##########################################\n" +
                    "ohelix21 {\ttrans\n" +
                    "\t\tohelixo1: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelixo1 ##########################################\n" +
                    "ohelixo1 {\ttrans\n" +
                    "\t\tohelixo2: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelixo2 ##########################################\n" +
                    "ohelixo2 {\ttrans\n" +
                    "\t\tohelixo3: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ohelixo3 ##########################################\n" +
                    "ohelixo3 {\ttrans\n" +
                    "\t\tohelixo4: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixo7;\n" +
                    "\t}\n" +
                    "########## STATE ohelixo4 ##########################################\n" +
                    "ohelixo4 {\ttrans\n" +
                    "\t\tohelixo5: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixo7;\n" +
                    "\t}\n" +
                    "########## STATE ohelixo5 ##########################################\n" +
                    "ohelixo5 {\ttrans\n" +
                    "\t\tohelixo6: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixo7;\n" +
                    "\t}\n" +
                    "########## STATE ohelixo6 ##########################################\n" +
                    "ohelixo6 {\ttrans\n" +
                    "\t\tohelixo7: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixo7;\n" +
                    "\t}\n" +
                    "########## STATE ohelixo7 ##########################################\n" +
                    "ohelixo7 {\ttrans\n" +
                    "\t\toutglob10: 0.0757404\n" +
                    "\t\tout10: 0.92426;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\tonly\t A:0.110353 C:0.00498206 D:0.00469973 E:0.00649427 F:0.0973043\n" +
                    "\t\t G:0.0737631 H:0.0119931 I:0.12167 K:0.00180854 L:0.157124\n" +
                    "\t\t M:0.044721 N:0.0107496 P:0.0283821 Q:0.0143416 R:0.00857182\n" +
                    "\t\t S:0.0402204 T:0.0501039 V:0.107462 W:0.0501891 Y:0.0550665;\n" +
                    "\t}\n" +
                    "########## STATE ihelixo1 ##########################################\n" +
                    "ihelixo1 {\ttrans\n" +
                    "\t\tihelixo2: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixo7;\n" +
                    "\t}\n" +
                    "########## STATE ihelixo2 ##########################################\n" +
                    "ihelixo2 {\ttrans\n" +
                    "\t\tihelixo3: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixo7;\n" +
                    "\t}\n" +
                    "########## STATE ihelixo3 ##########################################\n" +
                    "ihelixo3 {\ttrans\n" +
                    "\t\tihelixo4: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixo7;\n" +
                    "\t}\n" +
                    "########## STATE ihelixo4 ##########################################\n" +
                    "ihelixo4 {\ttrans\n" +
                    "\t\tihelixo5: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixo7;\n" +
                    "\t}\n" +
                    "########## STATE ihelixo5 ##########################################\n" +
                    "ihelixo5 {\ttrans\n" +
                    "\t\tihelixo6: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixo7;\n" +
                    "\t}\n" +
                    "########## STATE ihelixo6 ##########################################\n" +
                    "ihelixo6 {\ttrans\n" +
                    "\t\tihelixo7: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelixo7 ##########################################\n" +
                    "ihelixo7 {\ttrans\n" +
                    "\t\tihelixm: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelixm ###########################################\n" +
                    "ihelixm {\ttrans ihelix2 ihelix3 ihelix4 ihelix5 ihelix6 ihelix7 ihelix8 ihelix9 ihelix10 ihelix11 ihelix12 ihelix13 ihelix14 ihelix15 ihelix16 ihelix17 ihelix18 ihelix19 ihelix20 ihelix21 ihelixi1;\n" +
                    "\ttied_trans ohelixm;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix2 ###########################################\n" +
                    "ihelix2 {\ttrans\n" +
                    "\t\tihelix3: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix3 ###########################################\n" +
                    "ihelix3 {\ttrans\n" +
                    "\t\tihelix4: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix4 ###########################################\n" +
                    "ihelix4 {\ttrans\n" +
                    "\t\tihelix5: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix5 ###########################################\n" +
                    "ihelix5 {\ttrans\n" +
                    "\t\tihelix6: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix6 ###########################################\n" +
                    "ihelix6 {\ttrans\n" +
                    "\t\tihelix7: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix7 ###########################################\n" +
                    "ihelix7 {\ttrans\n" +
                    "\t\tihelix8: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix8 ###########################################\n" +
                    "ihelix8 {\ttrans\n" +
                    "\t\tihelix9: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix9 ###########################################\n" +
                    "ihelix9 {\ttrans\n" +
                    "\t\tihelix10: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix10 ##########################################\n" +
                    "ihelix10 {\ttrans\n" +
                    "\t\tihelix11: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix11 ##########################################\n" +
                    "ihelix11 {\ttrans\n" +
                    "\t\tihelix12: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix12 ##########################################\n" +
                    "ihelix12 {\ttrans\n" +
                    "\t\tihelix13: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix13 ##########################################\n" +
                    "ihelix13 {\ttrans\n" +
                    "\t\tihelix14: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix14 ##########################################\n" +
                    "ihelix14 {\ttrans\n" +
                    "\t\tihelix15: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix15 ##########################################\n" +
                    "ihelix15 {\ttrans\n" +
                    "\t\tihelix16: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix16 ##########################################\n" +
                    "ihelix16 {\ttrans\n" +
                    "\t\tihelix17: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix17 ##########################################\n" +
                    "ihelix17 {\ttrans\n" +
                    "\t\tihelix18: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix18 ##########################################\n" +
                    "ihelix18 {\ttrans\n" +
                    "\t\tihelix19: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix19 ##########################################\n" +
                    "ihelix19 {\ttrans\n" +
                    "\t\tihelix20: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix20 ##########################################\n" +
                    "ihelix20 {\ttrans\n" +
                    "\t\tihelix21: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelix21 ##########################################\n" +
                    "ihelix21 {\ttrans\n" +
                    "\t\tihelixi1: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelixi1 ##########################################\n" +
                    "ihelixi1 {\ttrans\n" +
                    "\t\tihelixi2: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelixi2 ##########################################\n" +
                    "ihelixi2 {\ttrans\n" +
                    "\t\tihelixi3: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixm;\n" +
                    "\t}\n" +
                    "########## STATE ihelixi3 ##########################################\n" +
                    "ihelixi3 {\ttrans\n" +
                    "\t\tihelixi4: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixi1;\n" +
                    "\t}\n" +
                    "########## STATE ihelixi4 ##########################################\n" +
                    "ihelixi4 {\ttrans\n" +
                    "\t\tihelixi5: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixi1;\n" +
                    "\t}\n" +
                    "########## STATE ihelixi5 ##########################################\n" +
                    "ihelixi5 {\ttrans\n" +
                    "\t\tihelixi6: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixi1;\n" +
                    "\t}\n" +
                    "########## STATE ihelixi6 ##########################################\n" +
                    "ihelixi6 {\ttrans\n" +
                    "\t\tihelixi7: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixi1;\n" +
                    "\t}\n" +
                    "########## STATE ihelixi7 ##########################################\n" +
                    "ihelixi7 {\ttrans\n" +
                    "\t\tin10: 1;\n" +
                    "\tlabel M;\n" +
                    "\tend 0;\n" +
                    "\ttied_letter ohelixi1;\n" +
                    "\t}";

}
