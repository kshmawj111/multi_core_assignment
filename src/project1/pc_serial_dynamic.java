package project1;


import java.util.ArrayDeque;
import java.util.Queue;


class isPrimeDynamic implements Runnable{
        int num_of_primes = 0;
        long total_milliseconds = 0;
        int x;

        isPrimeDynamic(int x){this.x=x;}

        public void run(){
            long startTime = System.currentTimeMillis();
            int i;

            boolean is_prime = true;
            
            if (x<=1){
                is_prime=false;
            }

            else {
                for (i = 2; i < x/2; i++) {
                    if (x % i == 0) {
                        is_prime = false;
                        break;
                    }
                }
            }
            long endTime = System.currentTimeMillis();
            total_milliseconds = (endTime - startTime); // record execution time

            if (is_prime) {
                num_of_primes++;
            }

        }

        public long get_Total_milliseconds(){return total_milliseconds;}
        public int get_Num_of_primes(){return num_of_primes;}

    }



public class pc_serial_dynamic {
    private static final int NUM_END = 200000;
    private static int NUM_THREAD;
    private static csv_writer writer;
    private static Queue<Integer> tasks;
    private static Long[] exe_times;
    private static int num_primes;
    private static int counter;

    pc_serial_dynamic(int num_thread, csv_writer csv_writer){
        NUM_THREAD = num_thread;
        writer = csv_writer;
    }

    public void run_test(){
        initialize();

        while(!tasks.isEmpty()){
            int temp = fetch_task();
            isPrimeDynamic det = new isPrimeDynamic(temp);
            det.run();
            end_task(det);

        }

        print_result();
    }

    private static void initialize(){
        tasks = new ArrayDeque<>();
        exe_times = new Long[NUM_THREAD];
        counter = NUM_THREAD;

        for (int i=0; i<NUM_END;i++){
            tasks.add(i+1);
        }

        for(int i = 0; i<NUM_THREAD; i++){
            exe_times[i] = (long)0;
        }

    }

    public void change_num_threads(int num) {NUM_THREAD = num;}

    private synchronized int fetch_task(){
        while (counter ==0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        counter--;
        return tasks.poll();
    }

    private synchronized void end_task(isPrimeDynamic obj){
        num_primes += obj.get_Num_of_primes();
        exe_times[(int)Thread.currentThread().getId()] += obj.get_Total_milliseconds();
        counter++;
        notify();
    }

    private static void print_result(){
        // writer.add_content(NUM_THREAD, total_time);
        // System.out.println("Total " + num_primes + " prime numbers between 1 and "+ NUM_END+"\n\n");

        for(int i=0; i<NUM_THREAD; i++){
            System.out.println("Thread "+ i + ": " + exe_times[i]);
        }

        System.out.println("total: "+num_primes);
    }

}
