package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.entidades.Segurado;

public class SeguradoEmpresaDAO extends SeguradoDAO {

    @Override
    public Class<Segurado> getClasseEntidade() {
        return Segurado.class;
    }

    public SeguradoEmpresa buscar(String cnpj) {
        Segurado seg = super.buscar(cnpj);
        if (seg instanceof SeguradoEmpresa) {
            return (SeguradoEmpresa) seg;
        }
        return null;
    }
}