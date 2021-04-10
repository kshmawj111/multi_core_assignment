package project1.problem2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

// row_vec dot col_vec
class VectorMult extends Thread{
    private ArrayDeque<ArrayList<Object> > task_queue = new ArrayDeque<>();
    // Task queue with elements (row_vec, col_vec)
    private HashMap<ArrayList<Integer>, Integer> result = new HashMap<>();
    // HashMap ( location of element, the value)
    private long execution_time = 0;

    VectorMult(){}

    // insert new task to task queue
    public void insert_task(int[] _row, int[] _col, int _row_num, int _col_num){
        ArrayList<Object> temp = new ArrayList<>();
        temp.add(_row);
        temp.add(_col);
        temp.add(_row_num);
        temp.add(_col_num);
        task_queue.add(temp);
    }

    public void run() {
        long start = System.currentTimeMillis();
        while (!task_queue.isEmpty()) {
            ArrayList<Object> task = task_queue.poll();
            int[] _row = (int[]) task.get(0), _col = (int[]) task.get(1);
            int sum = IntStream.range(0, _row.length).map(i -> _row[i] * _col[i]).sum(); // sum product of two arrays

            ArrayList<Integer> loc = new ArrayList<>();
            loc.add((Integer) task.get(2)); // row
            loc.add((Integer) task.get(3)); // col
            result.put(loc, sum);
        }
        long end = System.currentTimeMillis();
        execution_time = end - start;
    }

    public HashMap<ArrayList<Integer>, Integer> getResult(){return result;}

    public long getExecution_time() {
        return execution_time;
    }
}

public class Matrix {
    private final int  row, col, num_threads;
    private int [][] elements;
    private long exe_time;
    public VectorMult[] threads;

    Matrix(int[][] e, int num_thread) {
        elements=e;
        exe_time = 0;
        row=e.length;
        col=e[0].length;
        num_threads = num_thread;
        threads = new VectorMult[num_thread];

        for(int i=0; i<num_thread; i++){
            threads[i] = new VectorMult();
        }
    }


    public int[][] mult(Matrix B){
        long start = System.currentTimeMillis();
        int[][] bt = B.transpose(); // Transposing Matrix B follows temporal locality of memory.
        allocate_vectors(elements, bt);

        for(int i=0; i<num_threads; i++){
            threads[i].start();
        }

        for(int i=0; i<num_threads; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int [][] result = new int[row][col];

        for(int i=0; i<num_threads; i++){
            HashMap<ArrayList<Integer>, Integer> thread_result = threads[i].getResult();
            for(ArrayList<Integer> key : thread_result.keySet()){
                int t_row =key.get(0), t_col = key.get(1);
                result[t_row][t_col] = thread_result.get(key);
            }
        }

        long end = System.currentTimeMillis();
        exe_time = end - start;

        return result;
    }


    public int[][] transpose(){
        int [][] transposed = new int[row][col];
        for(int i=0; i<row;i++){
            for(int j=0; j<col; j++){
                transposed[i][j] = elements[j][i];
            }
        }
        return transposed;

    }

    public VectorMult[] getThreads() {
        return threads;
    }

    private void allocate_vectors(int[][] _this, int[][] b){
        int t_idx = 0;
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                threads[t_idx].insert_task(_this[i], b[j], i, j);
                t_idx = (t_idx+1)%num_threads;
            }

        }
    }


}
