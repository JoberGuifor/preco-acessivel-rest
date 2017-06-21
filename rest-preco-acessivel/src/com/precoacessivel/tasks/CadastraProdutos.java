package com.precoacessivel.tasks;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class CadastraProdutos {
	
	/**
     * @param args
     */
    public static void main(String[] args) {
    	Date data = new Date(System.currentTimeMillis());  
    	SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd"); 
    	System.out.print(formatarDate.format(data));
    }

}
