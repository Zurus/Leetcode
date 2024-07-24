package org.example.utils;

import org.apache.commons.lang3.time.StopWatch;

public class TimeUtils {

    public static void stopWatch() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // выполнение какой-то логики
        Thread.sleep(1000);
        stopWatch.stop();
        System.out.println("Прошло времени, мс: " + stopWatch.getTime());
    }


}
