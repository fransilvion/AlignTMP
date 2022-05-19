package Help_classes;

/**
 * Created by Fransilvion on 02.02.2017.
 */
public class AverageMatrixMismatch {
    public static float calculateAverage(short[][] matrix){
        if (matrix.length != 20 || matrix[0].length != 20){
            System.out.println("Given matrix has not correct size, error on gap calculating stage");
            System.exit(1);
        }

        int i,j;
        int sum = 0;
        int num_elements = 0;
        for (i = 1; i < matrix.length; i++){
            for (j = 0; j < i; j++){
                sum += matrix[i][j];
                num_elements++;
            }
        }
        float result = (float) sum / (float) num_elements;
        return (-1)* result;
    }

}
