package com.zeroturnaround.rebellabs.oceanofmemories.article0.gc;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.zeroturnaround.rebellabs.oceanofmemories.common.util.MemoryUtil;
import com.zeroturnaround.rebellabs.oceanofmemories.common.util.Util;

/**
 * NOTE: This demo is only available at Java 7
 */
public class GarbageCollectionNotificationDemo {

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
	 * @link http://www.fasterj.com/articles/gcnotifs.shtml
	 * 
	 * NOTE: "GarbageCollectionNotificationInfo" is only available at Java 7
	 */
	private static void installGCMonitoring() {
		// Get all the GarbageCollectorMXBeans. 
		// There's one for each heap generation.
		// So probably there are two: the old generation and young generation
		List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
		
		// Install a notification handler for each bean
		for (GarbageCollectorMXBean gcBean : gcBeans) {
			NotificationEmitter emitter = (NotificationEmitter) gcBean;
			// Create notification listener to be notified for GC events
			NotificationListener listener = new NotificationListener() {
				// Keep a count of the total time spent in GCs
				long totalGcDuration = 0;

				// Implement the notifier callback handler
				@Override
				public void handleNotification(Notification notification, Object handback) {
					// We only handle GARBAGE_COLLECTION_NOTIFICATION notifications here
					if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
						// Get the information associated with this notification
						GarbageCollectionNotificationInfo info = 
								GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
						
						// Get all the info and pretty print it
						String gctype = info.getGcAction();
						if ("end of minor GC".equals(gctype)) {
							gctype = "Young Gen GC";
						} 
						else if ("end of major GC".equals(gctype)) {
							gctype = "Old Gen GC";
						}
						System.out.println();
						System.out.println(gctype + ":");
						System.out.println("\t" + "- " + "Id         : " + info.getGcInfo().getId());
						System.out.println("\t" + "- " + "Name       : " + info.getGcName());		
						System.out.println("\t" + "- " + "Cause      : " + info.getGcCause());
						System.out.println("\t" + "- " + "Duration   : " + (info.getGcInfo().getDuration() / 1000) + " milliseconds");
						System.out.println("\t" + "- " + "Start Time : " + (info.getGcInfo().getStartTime() / 1000) + " milliseconds since application started");	
						System.out.println("\t" + "- " + "End Time   : " + (info.getGcInfo().getEndTime() / 1000) + " milliseconds since application started");	
						
						//System.out.println("GcInfo CompositeType: " + info.getGcInfo().getCompositeType());
						//System.out.println("GcInfo MemoryUsageBeforeGc: " + info.getGcInfo().getMemoryUsageBeforeGc());
						//System.out.println("GcInfo MemoryUsageAfterGc: " + info.getGcInfo().getMemoryUsageAfterGc());

						// Get the information about each memory space, and pretty print it
						Map<String, MemoryUsage> memBefore = info.getGcInfo().getMemoryUsageBeforeGc();
						Map<String, MemoryUsage> mem = info.getGcInfo().getMemoryUsageAfterGc();
						
						for (Entry<String, MemoryUsage> entry : mem.entrySet()) {
							String name = entry.getKey();
							MemoryUsage memDetail = entry.getValue();
							long memInit = memDetail.getInit();
							long memCommitted = memDetail.getCommitted();
							long memMax = memDetail.getMax();
							long memUsed = memDetail.getUsed();
							MemoryUsage memBeforeDetail = memBefore.get(name);
							long beforeMemUsed = memBeforeDetail.getUsed();
							long beforeMemCommitted = memBeforeDetail.getCommitted();
							double initPercent = Util.percantegeOf(memInit, memCommitted);
							double beforePercent = Util.percantegeOf(beforeMemUsed, beforeMemCommitted);
							double percent = Util.percantegeOf(memUsed, memCommitted); //>100% when it gets expanded

							System.out.println("\t" + name + (memCommitted == memMax ? " (fully expanded):" : " (still expandable):"));
							System.out.println("\t\t" + "- " + "Init: " + initPercent + "%" + " " +
									"(" + MemoryUtil.byte2MB(memInit) + "MB" + " of " + MemoryUtil.byte2MB(memCommitted) + "MB" + ")");
							System.out.println("\t\t" + "- " + "Used: " + beforePercent + "%" + "->" + percent + "%" + " " +
									"(" + MemoryUtil.byte2MB(beforeMemUsed) + "MB" + "->"  + MemoryUtil.byte2MB(memUsed) + "MB" + 
									" of " + MemoryUtil.byte2MB(memCommitted) + "MB" + ")");
						}
						System.out.println();
						
						totalGcDuration += info.getGcInfo().getDuration();
						
						double percent = Util.percantegeOf(totalGcDuration, info.getGcInfo().getEndTime());
						
						System.out.println("GC cumulated overhead " + percent + "%");
						System.out.println();
					}
				}
			};

			// Add the listener
			emitter.addNotificationListener(listener, null, null);
	    }	
	}
	
}
