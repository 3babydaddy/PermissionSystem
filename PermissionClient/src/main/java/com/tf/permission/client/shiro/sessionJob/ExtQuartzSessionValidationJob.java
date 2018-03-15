/**
 * 
 */
package com.tf.permission.client.shiro.sessionJob;

import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoxinchun 2015年9月25日 上午10:25:28
 *
 */
public class ExtQuartzSessionValidationJob extends QuartzSessionValidationJob {

	 /**
     * Key used to store the session manager in the job data map for this job.
     */
    static final String SESSION_MANAGER_KEY = "sessionManager";
    
    private static final Logger log = LoggerFactory.getLogger(ExtQuartzSessionValidationJob.class);
	
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	log.info("=======================>>>>SessionValidationJob begin run");
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        ValidatingSessionManager sessionManager = (ValidatingSessionManager) jobDataMap.get(SESSION_MANAGER_KEY);
        sessionManager.validateSessions();
        log.info("=======================>>>>SessionValidationJob end run");
    }
}
