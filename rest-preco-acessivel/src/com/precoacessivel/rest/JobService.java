package com.precoacessivel.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.precoacessivel.entidade.Usuario;
import com.precoacessivel.tasks.CadastraProdutosJob;
import com.precoacessivel.tasks.MonitoraPrecosJob;

@Path("job")
public class JobService {
	
	private static String OK = "OK";
	private static String FALHA = "Falha";
	
	@GET
	@Path("/startCadastro")
	@Produces(MediaType.APPLICATION_JSON )
	public String iniciaCadastros() {
		
		SchedulerFactory shedFact = new StdSchedulerFactory();
		
        try {
               Scheduler scheduler = shedFact.getScheduler();
               scheduler.start();
               JobDetail job = JobBuilder.newJob(CadastraProdutosJob.class)
                             .withIdentity("cadastraProdutosJOB", "grupo01")
                             .build();
               Trigger trigger = TriggerBuilder.newTrigger()
                             .withIdentity("cadastraProdutosTRIGGER","grupo01")
                             .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 * * * ?"))
                             .build();
               scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
               e.printStackTrace();
               return FALHA;
        }
		
		return OK;
	}
	
	@GET
	@Path("/startPrecos/{tempo}")
	@Produces(MediaType.APPLICATION_JSON )
	public String iniciaMonitoramentoPrecos(@PathParam("tempo") int tempo) {
		
		SchedulerFactory shedFact = new StdSchedulerFactory();
		
        try {
               Scheduler scheduler = shedFact.getScheduler();
               scheduler.start();
               JobDetail job = JobBuilder.newJob(MonitoraPrecosJob.class)
                             .withIdentity("monitoramentoPrecosJOB", "grupo01")
                             .build();
               Trigger trigger = TriggerBuilder.newTrigger()
                             .withIdentity("monitoramentoPrecosTRIGGER","grupo01")
                             .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0/"+tempo+" * * ?"))
                             .build();
               scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
               e.printStackTrace();
               return FALHA;
        }
		
		return OK;
	}
	
	
}
