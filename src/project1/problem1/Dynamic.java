package project1.problem1;
import project1.csv_writer;

import java.util.ArrayDeque;
import java.util.concurrent.Semaphore;


class PrimeThreadDynamic implements Runnable{
    int thread_number;
    private long total_milliseconds;
    private int num_of_primes;

    PrimeThreadDynamic(int x){thread_number=x;}

    public void run(){
        System.out.println(thread_number+" running\n");
        /*long startTime = System.currentTimeMillis();
        int i;
        boolean is_prime = true;

        if (target_num <=1){
            is_prime=false;
        }
        else {
            for (i = 2; i < target_num; i++) {
                if (target_num % i == 0) {
                    is_prime = false;
                    break;
                }
            }
        }

        if (is_prime) {
            num_of_primes++;
        }

        long endTime = System.currentTimeMillis();
        total_milliseconds += (endTime - startTime); // record execution time*/

    }
    public long get_Total_milliseconds(){return total_milliseconds;}
    public int get_Num_of_primes(){return num_of_primes;}

    public PrimeThreadDynamic get_currentThread(){return this;}

}



public class Dynamic extends PrimeAbstract{
    private static int current_num;
    private static ArrayDeque<PrimeThreadDynamic> thread_queue = new ArrayDeque<>();
    PrimeThreadDynamic threads_list[];
    private static Semaphore sem;

    Dynamic(int num_thread, csv_writer writer){
        super(num_thread, writer);
    }

    public void run_test(){
        long start_time = System.currentTimeMillis();

        initialize();
        int i;

        for(i=0; i<NUM_THREAD; i++){
            threads_list[i].start();
        }

        while(current_num < NUM_END){
            try {
                fetch_and_run_task(thread_queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            end_tast();
        }

        for(i=0; i<NUM_THREAD; i++){
            try {
                threads_list[i].join(); // 마지막 숫자까지 진행했다면 join으로 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end_time = System.currentTimeMillis();
        print_result(end_time-start_time);
    }

    protected void initialize(){
        threads_list = new PrimeThreadDynamic[NUM_THREAD];
        current_num = 0;
        sem = new Semaphore(NUM_THREAD);

        for(int i=0; i<NUM_THREAD; i++){
            PrimeThreadDynamic t = new PrimeThreadDynamic();
            thread_queue.add(t);
            threads_list[i] = t;
        }

    }

    private void fetch_and_run_task(PrimeThreadDynamic t) throws InterruptedException {
        sem.acquire(NUM_THREAD);
        t.setTarget_num(current_num++);
        t.run();

    }

    private synchronized void end_tast(){
        sem.release();

    }
}
