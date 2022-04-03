package com.hsjnb.yd_ecommerce_platform_api.utils;

import cn.hutool.core.date.format.FastDateFormat;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 *
 * @author : Hsj1730
 * @version : 1.0
 * @date : Created in 2022/04/02 16:18
 * @description : 订单编号和商品编号生成策略
 */

public abstract class BarCodeGenerator {

    private static final FastDateFormat pattern = FastDateFormat.getInstance("yyyyMMddHHmmss");
    private static final AtomicInteger atomicInteger = new AtomicInteger(1);
    private static final ThreadLocal<StringBuilder> threadLocal = new ThreadLocal<>();

    /**
     * 长码生成策略
     * @param lock lock
     * @return String
     */
    public static String getC(String lock) {
        // 取系统当前时间作为订单号前半部分
        StringBuilder builder = new StringBuilder(pattern.format(Instant.now().toEpochMilli()));
        // HASH-CODE
        builder.append(((String.valueOf(Math.abs(lock.hashCode())))), 0, 2);
        // 自增顺序
        DecimalFormat df=new DecimalFormat("0000");
        builder.append(df.format(atomicInteger.getAndIncrement()));
        threadLocal.set(builder);
        return threadLocal.get().toString();
    }

    /**
     * 短码生成策略
     * @param lock lock
     * @return String
     */
    public static String getD(String lock) {
        // 随机数
        StringBuilder builder = new StringBuilder(ThreadLocalRandom.current().nextInt(0,999));
        // HASH-CODE
        builder.append(((String.valueOf(Math.abs(lock.hashCode())))), 0, 6);
        // 自增顺序
        DecimalFormat df=new DecimalFormat("0000");
        builder.append(df.format(atomicInteger.getAndIncrement()));
        threadLocal.set(builder);
        return threadLocal.get().toString();
    }

    //public static void main(String[] args) throws ExecutionException, InterruptedException {
    //    Set<String> set = new HashSet<>();
    //    FutureTask<String> task;
    //    long startTime = System.currentTimeMillis();
    //    for (int i = 0; i < 1000; i++) {
    //        Callable<String> callable = () -> {
    //             System.out.println("当前线程:>>>>> ".concat(Thread.currentThread().getName()));
    //             //return getC(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
    //            return getD(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
    //        };
    //        task = new FutureTask<>(callable);
    //        new Thread(task).start();
    //        System.out.println(task.get());
    //        set.add(task.get());
    //    }
    //    System.out.println("总共耗时:" + ((System.currentTimeMillis() - startTime)) + "ms");
    //    System.out.println("*************** " + set.size());
    //}

}
