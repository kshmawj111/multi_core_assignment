package project1.problem1;

import project1.csv_writer;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Random;

class ThreadQueue {
    private final LinkedList<PrimeThreadDynamic> queue  = new LinkedList<>();

    public void insert_thread(PrimeThreadDynamic t){
        change_queue_order();
        queue.add(t);
    }

    private void change_queue_order(){
        for(int i=0; i<queue.size(); i++){
            if(queue.get(i).isAlive()){
                PrimeThreadDynamic t = queue.remove(i);
                queue.add(t);

            }
        }
    }

    private PrimeThreadDynamic poll(){
        return queue.remove(0);
    }
}


class PrimeThreadDynamic extends Thread{
    int id;
    private int num_of_primes;
    private long total_milliseconds;
    private int target_num;
    private ArrayDeque<PrimeThreadDynamic> wq;

    PrimeThreadDynamic(int i, ArrayDeque<PrimeThreadDynamic> wait_q){id=i; wq=wait_q;}

    public void run(){
        System.out.println(id+": running on " + target_num + "\n");
        Random r = new Random();
        try {
            if(id==0) {
                Thread.sleep(r.nextInt(5000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long startTime = System.currentTimeMillis();

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
        total_milliseconds += (endTime - startTime); // record execution time
        wq.add(this); // 작업 끝나면 q 맨 뒤로 이동
    }

    public long get_Total_milliseconds(){return total_milliseconds;}
    public int get_Num_of_primes(){return num_of_primes;}
    public synchronized void fetch_and_run(int num){
        target_num= num;
        this.run();
    }

}

public class Dynamic extends PrimeAbstract{
    private static final ArrayDeque<PrimeThreadDynamic> wait_queue = new ArrayDeque<>();
    private int current_num=1;

    Dynamic(int num_thread, csv_writer writer){
        super(num_thread, writer);
    }

    public void run_test(){
        long start_time = System.currentTimeMillis();

        initialize();

        while (current_num<NUM_END) {

            wait_queue.poll().fetch_and_run(current_num++); // synchronized method call
            current_num++;
        }

        for(PrimeThreadDynamic t: wait_queue){

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

        for(int i=0; i<NUM_THREAD; i++){
            PrimeThreadDynamic r = new PrimeThreadDynamic(i, wait_queue);
            wait_queue.add(r);
        }

    }

    protected void print_result(long total_time){
        int total_primes = 0;
        long avg_time=0;

        for(int i=0; i<NUM_THREAD; i++){
            PrimeThreadDynamic t = wait_queue.poll();
            avg_time += t.get_Total_milliseconds();
            System.out.println("Thread "+ i + ": " + t.get_Total_milliseconds() + "msec");
            total_primes += t.get_Num_of_primes();
        }

        String avg = String.format("%.4f", avg_time/(double)NUM_THREAD);
        writer.add_content(NUM_THREAD, total_time, avg);
        System.out.println("total primes : "+ total_primes + " Avg time per thread : " + avg + "msec\n\n");

    }
}
