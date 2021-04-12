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

    protected void print_result(long total_time){
        int total_primes = 0;
        long avg_time=0;

        for(int i=0; i<NUM_THREAD; i++){
            PrimeThread t = threads[i];
            avg_time += t.get_Total_milliseconds();
            System.out.println("Thread "+ i + ": " + t.get_Total_milliseconds() + "msec");
            total_primes += t.get_Num_of_primes();
        }

        String avg =  String.format("%.4f", avg_time/(double)NUM_THREAD);
        writer.add_content(NUM_THREAD, total_time, avg);
        System.out.println("total primes : "+ total_primes + " Avg time per thread : " + avg + "msec\n\n");

    }
}
