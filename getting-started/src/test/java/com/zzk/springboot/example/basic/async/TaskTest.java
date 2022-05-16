package com.zzk.springboot.example.basic.async;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TaskTest {
    public static void main(String[] args) {

        int count = 10;

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TaskExecutorConfig.class);
        AsyncTask task = context.getBean(AsyncTask.class);
        for (int i = 0; i < count; i++) {
            task.executeAsyncTask(i);
            task.specialExecuteAsyncTask(i);
        }

        context.close();
    }
}
