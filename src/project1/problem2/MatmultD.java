package project1.problem2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// command-line execution example) java project1.problem2.MatmultD 6 < mat500.txt
// 6 means the number of threads to use
// < mat500.txt means the file that contains two matrices is given as standard input
//
// In eclipse, set the argument value and file input by using the menu [Run]->[Run Configurations]->{[Arguments], [Common->Input File]}.

// Original JAVA source code: http://stackoverflow.com/questions/21547462/how-to-multiply-2-dimensional-arrays-matrix-multiplication
public class MatmultD
{
  private static Scanner sc;
  private static final Scanner ksc = new Scanner(System.in);

  public static void main(String [] args) throws FileNotFoundException {
    int thread_no;
    if (args.length==1) thread_no = Integer.parseInt(args[0]);
    else thread_no = 1;

    String file_name = ksc.next();

    String path = MatmultD.class.getResource(file_name).getPath();
    sc = new Scanner(new File(path));

    int[][] a =readMatrix();
    int[][] b =readMatrix();

    long pure_cal_time = 0;
    long startTime = System.currentTimeMillis();

    Matrix ma = new Matrix(a, thread_no);
    Matrix mb = new Matrix(b, thread_no);
    int[][] c = ma.mult(mb);
    printMatrix(c);

    VectorMult[] threads = ma.getThreads();
    for(int i=0; i<thread_no; i++){
      long temp = threads[i].getExecution_time();
      System.out.printf("[thread_no]:%2d , [Time]:%4d ms\n", i, temp);
      pure_cal_time += temp;
    }
    long endTime = System.currentTimeMillis();
    System.out.printf(" Avg Cal Time per thread: %f ms\n" , pure_cal_time/(double)thread_no);
    System.out.printf("  Total Calculation Time: %d ms\n" , endTime-startTime);

    //printMatrix(a);
    //printMatrix(b);

    //System.out.printf("thread_no: %d\n" , thread_no);

  }

   public static int[][] readMatrix() {
       int rows = sc.nextInt();
       int cols = sc.nextInt();
       int[][] result = new int[rows][cols];
       for (int i = 0; i < rows; i++) {
           for (int j = 0; j < cols; j++) {
              result[i][j] = sc.nextInt();
           }
       }
       return result;
   }

  public static int[][] readMatrix(Scanner sc){
    int rows = sc.nextInt();
    int cols = sc.nextInt();
    int[][] result = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        result[i][j] = sc.nextInt();
      }
    }
    return result;
  }


  public static void printMatrix(int[][] mat) {
  System.out.println("Matrix["+mat.length+"]["+mat[0].length+"]");
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

  public static int[][] multMatrix(int[][] a, int[][] b){//a[m][n], b[n][p]
    if(a.length == 0) return new int[0][0];
    if(a[0].length != b.length) return null; //invalid dims

    int n = a[0].length;
    int m = a.length;
    int p = b[0].length;
    int[][] ans = new int[m][p];

    for(int i = 0;i < m;i++){
      for(int j = 0;j < p;j++){
        for(int k = 0;k < n;k++){
          ans[i][j] += a[i][k] * b[k][j];
        }
      }
    }
    return ans;
  }
}
