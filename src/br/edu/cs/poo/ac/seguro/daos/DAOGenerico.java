package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Registro;
import java.io.Serializable;

public abstract class DAOGenerico<T extends Registro> {
	private CadastroObjetos cadastro;

	public DAOGenerico() {
		this.cadastro = new CadastroObjetos(getClasseEntidade());
	}

	public abstract Class<T> getClasseEntidade();

	public T buscar(String id) {
		return (T) cadastro.buscar(id);
	}

	public boolean incluir(T entidade) {
		if (buscar(entidade.getIdUnico()) != null) {
			return false;
		} else {
			cadastro.incluir(entidade, entidade.getIdUnico());
			return true;
		}
	}

	public boolean alterar(T entidade) {
		if (buscar(entidade.getIdUnico()) == null) {
			return false;
		} else {
			cadastro.alterar(entidade, entidade.getIdUnico());
			return true;
		}
	}

	public boolean excluir(String id) {
		if (buscar(id) == null) {
			return false;
		} else {
			cadastro.excluir(id);
			return true;
		}
	}

	public Registro[] buscarTodos() {
		Serializable[] todos = cadastro.buscarTodos();
		if (todos == null) {
			return null;
		}
		Registro[] registros = new Registro[todos.length];
		for (int i = 0; i < todos.length; i++) {
			registros[i] = (Registro) todos[i];
		}
		return registros;
	}
}