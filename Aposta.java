package br.com.loteria.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;


@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"idUsuario", "numeroGerado"})) 
@Data
@Entity
@NoArgsConstructor
public class Aposta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAposta;
	
	@ManyToOne
	@JoinColumn(name = "idUsuario")
	@JsonIgnore
	private Usuario usuario;
	
	@Column(nullable = false)
	private String numeroGerado;

	public Aposta(String numeroGerado) {
		super();
		this.numeroGerado = numeroGerado;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aposta other = (Aposta) obj;
		if (numeroGerado == null) {
			if (other.numeroGerado != null)
				return false;
		} else if (!numeroGerado.equals(other.numeroGerado))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroGerado == null) ? 0 : numeroGerado.hashCode());
		return result;
	}


}
