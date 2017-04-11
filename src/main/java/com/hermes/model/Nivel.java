package com.hermes.model;

public enum Nivel {

	PRIORITARIA("Prioritária"),
	NORMAL("Normal");
	
	private String descricao;

	private Nivel(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
