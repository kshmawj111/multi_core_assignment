package project1.problem2;
import project1.csv_writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class test2 {
     private static csv_writer writer = new csv_writer("result", "C:\\Users\\kshma\\IdeaProjects\\multi_core_assignment\\src\\project1\\problem2");

    public static void main(String[] args) throws FileNotFoundException {
        int[] threads = {1, 2, 4, 6, 8, 10, 12, 14, 16, 32};

        for(int t : threads){
            run_test(t);
        }

        writer.write_csv();
    }

    public static void run_test(int thread_no) throws FileNotFoundException {
        String path = MatmultD.class.getResource("mat1000.txt").getPath();
        Scanner sc = new Scanner(new File(path));

        int[][] a = MatmultD.readMatrix(sc);
        int[][] b= MatmultD.readMatrix(sc);

        long pure_cal_time = 0;
        long startTime = System.currentTimeMillis();

        Matrix ma = new Matrix(a, thread_no);
        Matrix mb = new Matrix(b, thread_no);
        int[][] c = ma.mult(mb);
        // printMatrix(c);
        long endTime = System.currentTimeMillis();

        VectorMult[] threads = ma.getThreads();
        for(int i=0; i<thread_no; i++){
            long temp = threads[i].getExecution_time();
            System.out.printf("[thread_no]:%2d , [Time]:%4d ms\n", i, temp);
            pure_cal_time += temp;
        }

        System.out.printf(" Avg Cal Time per thread: %f ms\n" , pure_cal_time/(double)thread_no);
        System.out.printf("Total Calculation Time: %d ms\n\n" , endTime-startTime);

        writer.add_content(thread_no, endTime-startTime);
    }

    public static void printMatrix(int[][] mat) {
        System.out.println("Matrix["+mat.length+"]["+mat[0].length+"]");
        int rows = mat.length;
        int columns = mat[0].length;
        int sum = 0;
        for (int[] ints : mat) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%4d ", ints[j]);
                sum += ints[j];
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Matrix Sum = " + sum + "\n");
    }
}
