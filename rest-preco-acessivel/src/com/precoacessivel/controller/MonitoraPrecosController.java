package com.precoacessivel.controller;

import java.sql.Date;
import java.util.ArrayList;

import com.precoacessivel.dao.EstabelecimentoDAO;
import com.precoacessivel.entidade.Estabelecimento;
import com.precoacessivel.entidade.Produto;

public class MonitoraPrecosController {
	
	private EstabelecimentoDAO estDAO;
	private ProdutoController prodContr;
	
	public MonitoraPrecosController() {
		estDAO = new EstabelecimentoDAO();
		prodContr = new ProdutoController();
	}
	
	public void atualizaPrecos(){
		
		ArrayList<Estabelecimento> ests = null;
		
		do{
			try {
				ests = estDAO.buscarEstabelecimentos(5);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			if(ests.size() > 0){
				
				ArrayList<Produto> prods = null;
				
				for (int i = 0; i < ests.size(); i++) {
					
					Estabelecimento estabelecimento = ests.get(i);
					prods = prodContr.buscaTodosProdsEstabelecimento(estabelecimento);
					
					if(prods != null){
						
						for (int j = 0; j < prods.size(); j++) {
							System.out.print("j = "+j+" ( ");
							Produto prodIndividual = prods.get(j);
							String termo;
							ArrayList<Produto> prodsNP = null;
							if(prodIndividual.getCodbar() != 0){
								termo = prodIndividual.getCodbar() + "";
							}else{
								termo = prodIndividual.getDescricao();
							}
							
							//aguarda 2s pra nao sobrecarregar o serv do Nota paraná
							try {
							   Thread.sleep(2000);
							} catch (Exception e) {
							   e.printStackTrace();
							}
							prodsNP = prodContr.buscaProdutos(termo, estabelecimento.getLocal(), 1);
							
							if(prodsNP != null){
								for (int k = 0; k < prodsNP.size(); k++) {
									Produto prodNP = prodsNP.get(k);
									if(prodNP.getEstab().getCodigo().equals(estabelecimento.getCodigo())){
										prodNP.setIdproduto(prodIndividual.getIdproduto());
										Date dtProdBanco = Date.valueOf(prodIndividual.getDatamov());
										Date dtProdNP = Date.valueOf(prodNP.getDatamov());
										//verifica se teve outras vendas após a ultima data!
										if(dtProdNP.after(dtProdBanco)){
											//Verifica se o valor mudou
											if(prodNP.getValor() != prodIndividual.getValor()){
												Produto p = prodContr.buscarProdutoEstabelecimento(prodNP.getIdproduto(), prodNP.getEstab().getLocal(), prodNP.getDatamov());
												long idprodestnovo;
												if(p==null){
													idprodestnovo = prodContr.addProdutoEstabelecimento(prodNP);
												}else{
													idprodestnovo = p.getCodprodEst();
												}
												
												if(idprodestnovo == 0){
													System.out.println("Falha na inserção do relacionamento Produto-Estabelecimento");
													continue;
												}
												prodNP.setCodprodEst(idprodestnovo);
												
												if(prodNP.getValor() < prodIndividual.getValor()){
													prodContr.addProdutoPromocao(prodIndividual, prodNP);
												}else{
													//Remove a promocao se o preco aumentou
													long idpromo = prodContr.buscarProdutoPromocao(prodIndividual.getCodprodEst());
													prodContr.removerProdutoPromocao(idpromo);
												}
											}
										}
									}
									
								}
							}
							
						}
					}
					
					try {
						estDAO.addEstabelecimentoMonitorado(estabelecimento);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
		}while(ests.size() > 0);
		
	}
	

}
