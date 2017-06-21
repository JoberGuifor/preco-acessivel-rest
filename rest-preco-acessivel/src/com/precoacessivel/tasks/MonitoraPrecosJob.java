package com.precoacessivel.tasks;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.precoacessivel.controller.EstabelecimentoController;
import com.precoacessivel.controller.MonitoraPrecosController;

public class MonitoraPrecosJob implements Job{
	
	private MonitoraPrecosController mpc;
	private EstabelecimentoController ec;
	private boolean executando;
	
	public MonitoraPrecosJob() {
		mpc = new MonitoraPrecosController();
		ec = new EstabelecimentoController();
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		long totEstabs[] = null;
		
		totEstabs = ec.buscarTotalEstabelecimentos();
		
		if(totEstabs!=null){
			if(totEstabs[0] == totEstabs[1]){
				ec.removerTodosEstabelecimentosMonitorados();
			}else if(totEstabs[1] != 0){
				return;
			}
		}
		
		System.out.println("Inciou novo processo de verificação de PRECOS em: "+new Date());
        
		mpc.atualizaPrecos();
		
		System.out.println("Terminou processo de verificação de PRECOS em: "+new Date());
	
	}

}
