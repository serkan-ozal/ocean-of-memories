package com.zeroturnaround.rebellabs.oceanofmemories.article0.gc;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryNotificationInfo;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

import com.zeroturnaround.rebellabs.oceanofmemories.common.util.MemoryUtil;
import com.zeroturnaround.rebellabs.oceanofmemories.common.util.Util;


public class GarbageCollectionThresholdDemo {

	private static long MEMORY_USAGE_THRESHOLD = 1000000;
	
	public static void main(String[] args) {
		final int INTEGER_COUNT = 1000000;
		final Integer[] integerArray = new Integer[INTEGER_COUNT];
		
		MemoryUtil.runGC();
		
		installGCMonitoring();

		for (int i = 0; i < INTEGER_COUNT; i++) {
			integerArray[i] = new Integer(i);
		}
		
		Util.waitFor(5000);

		MemoryUtil.runGC();
		
		Util.waitFor(5000);
	}

	/**
	 * @link http://docs.oracle.com/javase/7/docs/api/java/lang/management/MemoryPoolMXBean.html
	 */
	private static void installGCMonitoring() {
		// Get the MemoryMXBean 
		MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
		NotificationEmitter emitter = (NotificationEmitter) memBean;
		
		NotificationListener listener = new NotificationListener() {
			// Implement the notifier callback handler
			@Override
			public void handleNotification(Notification notification, Object handback) {
				// We only handle MEMORY_THRESHOLD_EXCEEDED notifications here
				if (notification.getType().equals(MemoryNotificationInfo.MEMORY_THRESHOLD_EXCEEDED)) {
					// Get the information associated with this notification
					MemoryNotificationInfo info = 
							MemoryNotificationInfo.from((CompositeData) notification.getUserData());
					
					MemoryUsage memDetail = info.getUsage();
					long memInit = memDetail.getInit();
					long memCommitted = memDetail.getCommitted();
					long memMax = memDetail.getMax();
					long memUsed = memDetail.getUsed();
					
					double committedPercent = Util.percantegeOf(memCommitted, memMax);
					double initPercent = Util.percantegeOf(memInit, memCommitted);
					double percent = Util.percantegeOf(memUsed, memCommitted); //>100% when it gets expanded

					System.out.println();
					System.out.println("MEMORY THRESHOLD EXCEEDED:");
					System.out.println("\t" + "- " + "Pool Name       : " + info.getPoolName());
					System.out.println("\t" + "- " + "Usage Threshold : " + MemoryUtil.byte2MB(MEMORY_USAGE_THRESHOLD) + "MB");
					System.out.println("\t" + "- " + "Exceeded Count  : " + info.getCount());
					System.out.println("\t" + "- " + "Memory Usage    : ");
					System.out.println("\t\t" + "- " + "Committed of Max : " + committedPercent + "%" + ", " +
							MemoryUtil.byte2MB(memCommitted) + "MB" + " of " + MemoryUtil.byte2MB(memMax) + "MB" + " " + 
							"(" + (memCommitted == memMax ? "fully expanded" : "still expandable") + ")");
					System.out.println("\t\t" + "- " + "Init             : " + initPercent + "%" + ", " +
							MemoryUtil.byte2MB(memInit) + "MB" + " of " + MemoryUtil.byte2MB(memCommitted) + "MB");
					System.out.println("\t\t" + "- " + "Used             : " + percent + "%" + ", " +
							MemoryUtil.byte2MB(memUsed) + "MB" + " of " + MemoryUtil.byte2MB(memCommitted) + "MB");
					System.out.println();
				}
			}
		};	
		
		// Add the listener
		emitter.addNotificationListener(listener, null, null);
		
		// ************************************************************************************************
		// NOTE: Setting usage threshold to all memory pools must be done after 
		//       notification listener for MEMORY_THRESHOLD_EXCEEDED has been registered to MemoryMXBean.
		// ************************************************************************************************
		
		// Get all the MemoryPoolMXBeans. 
		// There's one for each pools.
		// So probably there are five: "Code Cache", "PS Eden Space", "PS Survivor Space", "PS Old Gen" and "PS Perm Gen"
		List<MemoryPoolMXBean> memPoolBeans = ManagementFactory.getMemoryPoolMXBeans();
				
		// Set memory usage threshold for each bean
		for (MemoryPoolMXBean memPoolBean : memPoolBeans) {
			System.out.println("Memory Pool Name: " + memPoolBean.getName());
			
			/*
			 * You use the "isUsageThresholdSupported()" method to determine whether a memory pool supports a usage threshold, 
			 * since a usage threshold is not appropriate for some memory pools. 
			 * For example, in a generational garbage collector (such as the one in the HotSpot VM), 
			 * most of the objects are allocated in the young generation, from the "Eden memory pool". 
			 * "The Eden pool" is designed to be filled up. 
			 * Garbage collecting the "Eden memory pool" will free most of its memory space, 
			 * since it is expected to contain mostly short-lived objects that are unreachable at garbage collection time. 
			 * So, it is not appropriate for the "Eden memory pool" to support a usage threshold.
			 * Also "Survior Space" doesn't support memory usage threshold.
			 */
			if (memPoolBean.isUsageThresholdSupported()) {
				// If usage threshold is supported by this memory pool bean, set usage threshold value
				memPoolBean.setUsageThreshold(MEMORY_USAGE_THRESHOLD);
				System.out.println("Usage Threshold has been set to " + MEMORY_USAGE_THRESHOLD + 
						" for pool " + memPoolBean.getName());
			}	
			else {
				System.out.println("Usage Threshold is not supported by pool " + memPoolBean.getName());
			}
			
			System.out.println();
		}
	}
	
}
