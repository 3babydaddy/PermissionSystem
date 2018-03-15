package com.tf.base.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpSession;

import com.tf.base.common.service.LogService;
import com.tf.base.notification.domain.Notificationobject;
import com.tf.base.resource.domain.ResourceInfo;

/**
 * 
 * 负责推送资源更新通知，使用线程池，等待任务来临
 *
 */
public class NotificationCenter {

	/**
	 * 线程池_推送通知
	 */
	private static ExecutorService notificationExecutor = Executors.newFixedThreadPool(4);
	
	/**
	 * 增加推送任务
	 * @param command
	 */
	public static void addNotificationTask(Runnable command){
		notificationExecutor.execute(command);
	}
	
	/**
	 * 推送通知
	 * @param notificationobject
	 */
	public static void sendNotification(Notificationobject notificationobject,ResourceInfo resourceInfo,HttpSession session ,ResourceInfo info,LogService logService){
		NotificationTask task = new NotificationTask(notificationobject.getSystemid(), notificationobject.getSystemip(), 
				           notificationobject.getSystemport(), notificationobject.getNotificationUrl(),resourceInfo,session ,info,logService);
		addNotificationTask(task);
	}
	
}
