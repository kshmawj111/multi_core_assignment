package project1;

public class pc_serial {
    private static final int NUM_END = 100;
    private static final int NUM_THREAD = 4;

    public static void main(String[] args) {
        int counter=0, i=0;

        long start = System.currentTimeMillis();
        for(i=0;i<NUM_END; i++) {
            if (isPrime(i)) counter++;
        }
        System.out.println(counter);

    }

    private static boolean isPrime(int x){
        int i;
        if (x<=1)return false;
        for(i=2;i<x;i++){
            if(x%i == 0) return false;
        }
        return true;
    }


}
