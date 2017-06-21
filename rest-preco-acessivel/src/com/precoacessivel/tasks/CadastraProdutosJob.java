package com.precoacessivel.tasks;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.precoacessivel.controller.BuscaController;
import com.precoacessivel.controller.ProdutoController;
import com.precoacessivel.dao.UsuarioDAO;
import com.precoacessivel.entidade.Buscas;
import com.precoacessivel.entidade.Produto;

public class CadastraProdutosJob implements Job{
	
	private BuscaController bc;
	private ProdutoController pc;
	
	public CadastraProdutosJob() {
		bc = new BuscaController();
		pc = new ProdutoController();
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		System.out.println("Inciou novo processo de verificação de buscas em: "+new Date());
        
		ArrayList<Buscas> buscas = null;
		
		buscas = bc.listarBuscasProcessadas();
		
		if(buscas!=null){
			if((buscas.size() < 2) && (buscas.size() != 0) ){
				System.out.println("Processo de verificação de buscas não iniciado qtd buscas processadas: "+buscas.size());
				return;
			}else{
				for(int i = 0;i<buscas.size();i++){
					bc.removerBusca(buscas.get(i).getIdbusca());
				}
				buscas = null;
				
			}
		}
		
		buscas = bc.listarBuscas(2);
        
		String retBusca;
		ArrayList<Produto> prods = null;
		
		for (int i = 0; i < buscas.size(); i++) {
			Buscas busc = buscas.get(i);
			retBusca = busc.getResultado();
			prods = bc.formataProdutosJson(retBusca);
			
			for(int x = 0;x<prods.size();x++){
				Produto p = prods.get(x);
				boolean statusCadastro = pc.addProduto(p);
				if (!statusCadastro){
					System.out.println("Falha ao cadastrar o produto : " + p.getCodbar() + " - "+p.getDescricao());
				}
			}
			
			
			busc.setProcessado("T");
			bc.editarBusca(busc);
			
		}
		
        System.out.println("Terminou verificação em: "+new Date());
        
	}

}
