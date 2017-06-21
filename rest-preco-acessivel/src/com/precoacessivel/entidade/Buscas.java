package com.precoacessivel.entidade;

public class Buscas {
	private long idbusca;
	private String resultado;
	private String processado;
	
	public String getProcessado() {
		return processado;
	}
	public void setProcessado(String string) {
		this.processado = string;
	}
	public long getIdbusca() {
		return idbusca;
	}
	public void setIdbusca(long idbusca) {
		this.idbusca = idbusca;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
}
