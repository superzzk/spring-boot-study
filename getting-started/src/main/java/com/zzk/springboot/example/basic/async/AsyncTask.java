package com.zzk.springboot.example.basic.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask {
    /** 表示异步执行 */
    @Async
    public void executeAsyncTask(Integer i){
        System.out.println("执行异步任务: " + i);
    }

    /** 表示异步执行 */
    @Async
    public void specialExecuteAsyncTask(Integer i){
        System.out.println("执行特殊异步任务: " + i);
    }
}
