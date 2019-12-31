package com.zjf.toolkit;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class ThreadTest {
	public static void main(String[] args) {
		Runner1 runner1 = new Runner1();
		Runner2 runner2 = new Runner2();
		// Thread(Runnable target) 分配新的 Thread 对象。
		Thread thread1 = new Thread(runner1);
		Thread thread2 = new Thread(runner2);
		 thread1.start();
		 thread2.start();
		//thread1.run();
		//thread2.run();
	}
}

class Runner1 implements Runnable { // 实现了Runnable接口，jdk就知道这个类是一个线程
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println("进入Runner1运行状态——————————" + i);
		}
	}
}

class Runner2 implements Runnable { // 实现了Runnable接口，jdk就知道这个类是一个线程
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println("进入Runner2运行状态==========" + i);
		}
	}
}


class SingleThread {

    public static long[] numbers;

    public static void main(String[] args) {
        numbers = LongStream.rangeClosed(1, 10_000_000).toArray();
        long sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
        }
        System.out.println("sum  = " + sum);
    }

}


class ThreadTest1 {

    public static final int THRESHOLD = 10_000;
    public static long[] numbers;
    private static long allSum;

    public static void main(String[] args) throws Exception {
        numbers = LongStream.rangeClosed(1, 10_000_000).toArray();
        int taskSize = (int) (numbers.length / THRESHOLD);
        for (int i = 1; i <= taskSize; i++) {
            final int key = i;
            new Thread(new Runnable() {
                public void run() {
                    sumAll(sum((key - 1) * THRESHOLD, key * THRESHOLD));
                }
            }).start();
        }
        Thread.sleep(100);
        System.out.println("allSum = " + getAllSum());
    }

    private static synchronized long sumAll(long threadSum) {
        return allSum += threadSum;
    }

    public static synchronized long getAllSum() {
        return allSum;
    }

    private static long sum(int start, int end) {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}


class ExecutorServiceTest {

    public static final int THRESHOLD = 10_000;
    public static long[] numbers;

    public static void main(String[] args) throws Exception {
        numbers = LongStream.rangeClosed(1, 10_000_000).toArray();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        CompletionService<Long> completionService = new ExecutorCompletionService<Long>(executor);
        int taskSize = (int) (numbers.length / THRESHOLD);
        for (int i = 1; i <= taskSize; i++) {
            final int key = i;
            completionService.submit(new Callable<Long>() {

                @Override
                public Long call() throws Exception {
                    return sum((key - 1) * THRESHOLD, key * THRESHOLD);
                }
            });
        }
        long sumValue = 0;
        for (int i = 0; i < taskSize; i++) {
            sumValue += completionService.take().get();
        }
        // 所有任务已经完成,关闭线程池
        System.out.println("sumValue = " + sumValue);
        executor.shutdown();
    }

    private static long sum(int start, int end) {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}

class ForkJoinTest extends java.util.concurrent.RecursiveTask<Long> {

    private static final long serialVersionUID = 1L;
    private final long[] numbers;
    private final int start;
    private final int end;
    public static final long THRESHOLD = 10_000;

    public ForkJoinTest(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinTest(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        ForkJoinTest leftTask = new ForkJoinTest(numbers, start, start + length / 2);
        leftTask.fork();
        ForkJoinTest rightTask = new ForkJoinTest(numbers, start + length / 2, end);
        Long rightResult = rightTask.compute();
        // 注：join方法会阻塞，因此有必要在两个子任务的计算都开始之后才执行join方法
        Long leftResult = leftTask.join();
        return leftResult + rightResult;
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(forkJoinSum(10_000_000));
    }

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinTest(numbers);
        return new ForkJoinPool().invoke(task);
    }
}


class StreamTest {

    public static void main(String[] args) {
        System.out.println("sum = " + parallelRangedSum(10_000_000));
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }
}