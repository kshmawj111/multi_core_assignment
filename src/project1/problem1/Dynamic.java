package project1.problem1;

import project1.csv_writer;

import java.util.ArrayDeque;

class TaskQueue {
    private ArrayDeque<Integer> task = new ArrayDeque<>();
    private int size = 0;

    public synchronized void add(int num){
        task.add(num);
        size += 1;
    }

    public synchronized int poll(){
        if (!isEmpty()) {
            size-= 1;
            return task.poll();
        }
        return -1;
    }

    public synchronized boolean isEmpty(){
        if(size<=0){
            return true;
        }
        else return false;
    }
}



class PrimeThreadDynamic extends Thread{
    int id;
    private int num_of_primes;
    private long total_milliseconds;
    private int target_num;
    private TaskQueue tq;

    PrimeThreadDynamic(int i, TaskQueue q){id=i; tq=q;}

    public void run(){

        long startTime = System.currentTimeMillis();
        while (!tq.isEmpty()) {
            fetch();
            // System.out.println(id + ": running on " + target_num);

            int i;
            boolean is_prime = true;

            if (target_num <= 1) {
                is_prime = false;
            } else {
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

        }

        long endTime = System.currentTimeMillis();
        total_milliseconds += (endTime - startTime); // record execution time
    }

    public long get_Total_milliseconds(){return total_milliseconds;}
    public int get_Num_of_primes(){return num_of_primes;}


    public synchronized boolean fetch(){
        if (!tq.isEmpty()) {
            target_num = tq.poll();
            return true;
        }
        else{
            return false;
        }
    }

}

public class Dynamic extends PrimeAbstract{
    private final TaskQueue task_queue = new TaskQueue();
    private static PrimeThreadDynamic[] th;

    Dynamic(int num_thread, csv_writer writer){
        super(num_thread, writer);
    }

    public void run_test(){
        long start_time = System.currentTimeMillis();

        initialize();

        for(PrimeThreadDynamic t: th){
            t.start();
        }

        for(PrimeThreadDynamic t: th){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end_time = System.currentTimeMillis();
        print_result(end_time-start_time);

    }

    protected void initialize(){
        th = new PrimeThreadDynamic[NUM_THREAD];

        for(int i=0; i<NUM_END; i++){
            task_queue.add(i+1);
        }

        for(int i=0; i<NUM_THREAD; i++){
            PrimeThreadDynamic r = new PrimeThreadDynamic(i, task_queue);
            th[i] = r;
        }

    }

    protected void print_result(long total_time){
        int total_primes = 0;
        long avg_time=0;

        for(int i=0; i<NUM_THREAD; i++){
            PrimeThreadDynamic t = th[i];
            avg_time += t.get_Total_milliseconds();
            System.out.println("Thread "+ i + ": " + t.get_Total_milliseconds() + "msec");
            total_primes += t.get_Num_of_primes();
        }

        String avg = String.format("%.4f", avg_time/(double)NUM_THREAD);
        writer.add_content(NUM_THREAD, total_time, avg);
        System.out.println("total primes : "+ total_primes + " Avg time per thread : " + avg + "msec\n\n");

    }
}
