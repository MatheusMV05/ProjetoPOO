package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.entidades.Segurado;

public class SeguradoPessoaDAO extends SeguradoDAO {

	@Override
	public Class<Segurado> getClasseEntidade() {
		return Segurado.class;
	}

	public SeguradoPessoa buscar(String cpf) {
		Segurado seg = super.buscar(cpf);
		if (seg instanceof SeguradoPessoa) {
			return (SeguradoPessoa) seg;
		}
		return null;
	}
}