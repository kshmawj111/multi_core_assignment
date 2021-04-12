package project1.problem1;
import project1.csv_writer;

import java.util.ArrayDeque;
import java.util.Queue;

class PrimeThreadStatic extends PrimeThread {
    private int num_of_primes = 0;
    private long total_milliseconds = 0;
    private final Queue<Integer> task_queue = new ArrayDeque<>();

    public void insert_task(int num){task_queue.add(num);}

    public void run(){
        long startTime = System.currentTimeMillis();
        int i;

        while (!task_queue.isEmpty()) {
            int x = task_queue.poll();
            boolean is_prime = true;

            /*
            Algorithms to determine a number is prime or not
            for the purpose of making the most similar condition with dynamic way

             */

            if (x<=1){
                is_prime=false;
            }
            else {
                for (i = 2; i < x; i++) {
                    if (x % i == 0) {
                        is_prime = false;
                        break;
                    }
                }
            }

            if (is_prime) {
                num_of_primes++;
            }
        }
        total_milliseconds += (System.currentTimeMillis() - startTime); // record execution time
    }

    public long get_Total_milliseconds(){return total_milliseconds;}
    public int get_Num_of_primes(){return num_of_primes;}

}


public class Static extends PrimeAbstract {
    Static(int num_thread, csv_writer csv_writer) {
        super(num_thread, csv_writer);
    }

    @Override
    public void run_test() {
        long start_time = System.currentTimeMillis();
        initialize();
        allocate_task();

        for(PrimeThread x: threads){
            x.start();
        }

        for(PrimeThread x: threads){
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end_time = System.currentTimeMillis();
        print_result(end_time-start_time);
    }

    @Override
    protected void initialize() {
        threads = new PrimeThreadStatic[NUM_THREAD];

        for (int i=0; i<NUM_THREAD; i++){
            threads[i] = new PrimeThreadStatic();
        }
    }

    private void allocate_task() {
        for (int i=0; i<NUM_END; i++){
            threads[i%NUM_THREAD].insert_task(i+1);
        }
    }


}
