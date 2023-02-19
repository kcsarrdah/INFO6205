package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * TODO tidy it up a bit.
 */
public class Main {

    public static void main(String[] args) {
        int thread = 2, size = 20000;
        processArgs(args);
        System.out.println("Size of Array is:"+size);

        while(thread<128) {
            ForkJoinPool Pool = new ForkJoinPool(thread);
            System.out.println("Degree of parallelism: " + Pool.getParallelism());
            Random random = new Random();
            int[] array = new int[size];
            ArrayList<Long> timeList = new ArrayList<>();
            for (int j = 0; j < 50; j++) {
                ParSort.cutoff = 10000 * (j + 1);
                long time;
                long sTime = System.currentTimeMillis();
                for (int t = 0; t < 10; t++) {
                    for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                    ParSort.sort(array, 0, array.length,Pool);
                }
                long eTime = System.currentTimeMillis();
                time = (eTime - sTime);
                timeList.add(time);


                System.out.println("cutoff：" + (ParSort.cutoff) + "\t\t10-times Time:" + time + "ms");
                //System.out.println(time);

            }
            try {
                FileOutputStream fis = new FileOutputStream("./src/result.csv");
                OutputStreamWriter isr = new OutputStreamWriter(fis);
                BufferedWriter bw = new BufferedWriter(isr);
                int j = 0;
                for (long i : timeList) {
                    String content = (double) 10000 * (j + 1) / 2000000 + "," + (double) i / 10 + "\n";
                    j++;
                    bw.write(content);
                    bw.flush();
                }
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            thread+=thread;
        }
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}