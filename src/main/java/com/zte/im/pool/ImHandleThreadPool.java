//package com.zte.im.pool;
//
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//public class ImHandleThreadPool {
//
//	private final static int CORE_POOL_SIZE = 5;
//	private final static int MAX_POOL_SIZE = 5;
//	private final static long KEEP_ALIVE_TIME = 0L;
////	private final static int WORK_QUEUE_SIZE = 500000;
//	
//	private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
//			TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
//			new ThreadPoolExecutor.CallerRunsPolicy()); //自动重试
//	
//	public static void execute(Runnable r){
//		threadPool.execute(r);
//	}
//	
//	public static int getThreadSize() {
//		return threadPool.getPoolSize();
//	}
//
//	public static void shutdown() {
//		threadPool.shutdown();
//	}
//
// }
