package project1.problem1;

import project1.csv_writer;



public abstract class PrimeAbstract {
    protected static final int NUM_END = 200000;
    protected static int NUM_THREAD;
    protected static csv_writer writer;
    protected PrimeThread[] threads;

    PrimeAbstract(int num_thread, csv_writer csv_writer){
        NUM_THREAD = num_thread;
        writer = csv_writer;
    }

    abstract public void run_test();
    abstract protected void initialize();
    abstract protected void allocate_task();

    protected void print_result(){
        int total_primes = 0;
        long total_time = 0;

        for(int i=0; i<NUM_THREAD; i++){
            System.out.println("Thread "+ i + ": " + threads[i].get_Total_milliseconds() + "msec");
            total_primes += threads[i].get_Num_of_primes();
            total_time += threads[i].get_Total_milliseconds();
        }

        writer.add_content(NUM_THREAD, total_time);
        System.out.println("total primes : "+ total_primes + " Avg time per thread : " + total_time/(double)NUM_THREAD + "msec\n\n");

    }
}
