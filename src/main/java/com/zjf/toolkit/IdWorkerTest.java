package com.zjf.toolkit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class IdWorkerTest {
	public static void main(String []args){ 
        IdWorkerTest test = new IdWorkerTest(); 
        List<String> list = test.test2(); 
        List<String> duplicateElements = getDuplicateElements(list);
        System.out.println("list 中重复的元素：" + duplicateElements);

    } 

    public List<String> test2(){ 
        final MyIdWorker w = new MyIdWorker(); 
        final CyclicBarrier cdl = new CyclicBarrier(100); 
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 100; i++){ 
            new Thread(new Runnable() { 
                @Override 
                public void run() { 
                try { 
                    cdl.await(); 
                } catch (InterruptedException e) { 
                    e.printStackTrace(); 
                } catch (BrokenBarrierException e) { 
                    e.printStackTrace(); 
                }
                System.out.println(w.nextId(304));
                //list.add(w.nextId());
                } 
             }).start(); 
        } 
        try { 
            TimeUnit.SECONDS.sleep(5); 
        } catch (InterruptedException e) { 
           e.printStackTrace(); 
        }
		return list; 

    } 
    
    public static <E> List<E> getDuplicateElements(List<E> list) {
        return list.stream() // list 对应的 Stream
                .collect(Collectors.toMap(e -> e, e -> 1, (a, b) -> a + b)) // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
                .entrySet().stream() // 所有 entry 对应的 Stream
                .filter(entry -> entry.getValue() > 1) // 过滤出元素出现次数大于 1 的 entry
                .map(entry -> entry.getKey()) // 获得 entry 的键（重复元素）对应的 Stream
                .collect(Collectors.toList());  // 转化为 List
    }

}
