package com.hermes.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Message {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty
	private Long id;

	private String message;
	private String usuario;
	
	@Enumerated(EnumType.STRING)
	private Nivel nivel;

	private boolean lida;
	
	@Convert(converter=LocalDateTimeConverter.class)
	private LocalDateTime horarioCriacao;
	
	@Convert(converter=LocalDateTimeConverter.class)
	private LocalDateTime horarioLida;
	
	@JsonIgnore
	@Transient
	private final DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd:MM:yyyy hh:mm:ss");
	
	public Message() 
	{	
		this.horarioCriacao = LocalDateTime.now();
	}

	public void read() 
	{
		this.lida = true;
		this.horarioLida = LocalDateTime.now();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Nivel getNivel() {
		return nivel;
	}
	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
	
	public boolean isLida() {
		return lida;
	}
	public void setLida(boolean lida) {
		this.lida = lida;
	}
	
	@JsonProperty
	public String horarioCriacaoFormatada()
	{
		return this.getHorarioCriacao().format(sdf); 
	}
	
	@JsonProperty
	public String horarioLidaFormatada()
	{
		if (null == this.horarioLida)
			return "";
		
		return this.getHorarioLida().format(sdf); 
	}
	
	public LocalDateTime getHorarioCriacao() {
		return horarioCriacao;
	}
	public void setHorarioCriacao(LocalDateTime horarioCriacao) {
		this.horarioCriacao = horarioCriacao;
	}

	public LocalDateTime getHorarioLida() {
		return horarioLida;
	}
	public void setHorarioLida(LocalDateTime horarioLida) {
		this.horarioLida = horarioLida;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + ", usuario=" + usuario + ", nivel=" + nivel + ", lida=" + lida + " ]";
	}
	
}
