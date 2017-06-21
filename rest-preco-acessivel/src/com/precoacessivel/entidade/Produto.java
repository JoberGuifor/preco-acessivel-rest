package com.precoacessivel.entidade;

public class Produto {
	private long idproduto;
	private long codbar;
	private String descricao;
	private long ncm;
	private double valor;
	private String datamov;
	private Estabelecimento estab;
	private long codprodEst; // codigo do relacionamento Produto-Estabelecimento
	private double valorpromo;
	
	public double getValorpromo() {
		return valorpromo;
	}
	public void setValorpromo(double valorpromo) {
		this.valorpromo = valorpromo;
	}
	public long getCodprodEst() {
		return codprodEst;
	}
	public void setCodprodEst(long codprodEst) {
		this.codprodEst = codprodEst;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getDatamov() {
		return datamov;
	}
	public void setDatamov(String datamov) {
		this.datamov = datamov;
	}
	public Estabelecimento getEstab() {
		return estab;
	}
	public void setEstab(Estabelecimento estab) {
		this.estab = estab;
	}
	public long getNcm() {
		return ncm;
	}
	public void setNcm(long ncm) {
		this.ncm = ncm;
	}
	public long getIdproduto() {
		return idproduto;
	}
	public void setIdproduto(long idproduto) {
		this.idproduto = idproduto;
	}
	public long getCodbar() {
		return codbar;
	}
	public void setCodbar(long codbar) {
		this.codbar = codbar;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
