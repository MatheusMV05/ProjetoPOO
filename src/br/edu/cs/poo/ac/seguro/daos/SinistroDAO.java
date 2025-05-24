package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.Registro;

public class SinistroDAO extends DAOGenerico<Sinistro> {

    @Override
    public Class<Sinistro> getClasseEntidade() {
        return Sinistro.class;
    }

    public Sinistro[] buscarTodos() {
        Registro[] registros = super.buscarTodos();
        if (registros == null) {
            return null;
        }

        Sinistro[] sinistros = new Sinistro[registros.length];
        for (int i = 0; i < registros.length; i++) {
            sinistros[i] = (Sinistro) registros[i];
        }
        return sinistros;
    }
}