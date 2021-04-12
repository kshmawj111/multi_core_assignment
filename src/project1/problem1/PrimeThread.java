package project1.problem1;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class PrimeThread extends Thread{
    protected int num_of_primes = 0;
    protected long total_milliseconds = 0;
    protected final Queue<Integer> task_queue = new ArrayDeque<>();
    protected int target_num;

    PrimeThread(){}

    abstract public void run();

    public void insert_task(int num){task_queue.add(num);}
    public void setTarget_num(int x){target_num = x;}
    public long get_Total_milliseconds(){return total_milliseconds;}
    public int get_Num_of_primes(){return num_of_primes;}

}
