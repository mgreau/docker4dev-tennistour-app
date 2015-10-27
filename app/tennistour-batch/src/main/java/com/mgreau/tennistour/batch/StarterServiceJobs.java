package com.mgreau.tennistour.batch;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TimerService;

@Startup
@Singleton
public class StarterServiceJobs {
    /* Use the container's timer service */
    @Resource TimerService tservice;
    
    JobOperator jo;
    
    private static final Logger logger = Logger.getLogger("StarterServiceJobs");
    
    @PostConstruct
    public void init() {
        logger.log(Level.INFO, "Initializing Batch APP.");
        jo = BatchRuntime.getJobOperator();
    }
    
    @Schedule(second="*/30", minute="*",hour="*", persistent=false)
    public void loadHistoryDatas() {
    	 //long jid = jo.start("loadHistoryJob", new Properties());
    	 
    	 //JobExecution je = jo.getJobExecution(jid);
    	 //logger.log(Level.INFO, "Job created on: " + je.getCreateTime() + "<br>");
    	 //logger.log(Level.INFO, "Job started on: " + je.getStartTime() + "<br>");
    }
    
}
