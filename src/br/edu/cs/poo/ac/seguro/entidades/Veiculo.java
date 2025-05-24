package br.edu.cs.poo.ac.seguro.entidades;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = "placa")
public class Veiculo implements Registro {
    private String placa;
    private int ano;
    private Segurado proprietario;
    private CategoriaVeiculo categoria;

    // Construtor atualizado
    public Veiculo(String placa, int ano, Segurado proprietario, CategoriaVeiculo categoria) {
        this.placa = placa;
        this.ano = ano;
        this.proprietario = proprietario;
        this.categoria = categoria;
    }

    // Construtor de compatibilidade - DEPRECATED
    @Deprecated
    public Veiculo(String placa, int ano, SeguradoEmpresa proprietarioEmpresa,
                   SeguradoPessoa proprietarioPessoa, CategoriaVeiculo categoria) {
        this.placa = placa;
        this.ano = ano;
        this.categoria = categoria;
        // Define o proprietário baseado em qual parâmetro não é null
        if (proprietarioEmpresa != null) {
            this.proprietario = proprietarioEmpresa;
        } else if (proprietarioPessoa != null) {
            this.proprietario = proprietarioPessoa;
        }
    }

    // Métodos de compatibilidade - DEPRECATED
    @Deprecated
    public SeguradoEmpresa getProprietarioEmpresa() {
        if (proprietario instanceof SeguradoEmpresa) {
            return (SeguradoEmpresa) proprietario;
        }
        return null;
    }

    @Deprecated
    public void setProprietarioEmpresa(SeguradoEmpresa proprietarioEmpresa) {
        this.proprietario = proprietarioEmpresa;
    }

    @Deprecated
    public SeguradoPessoa getProprietarioPessoa() {
        if (proprietario instanceof SeguradoPessoa) {
            return (SeguradoPessoa) proprietario;
        }
        return null;
    }

    @Deprecated
    public void setProprietarioPessoa(SeguradoPessoa proprietarioPessoa) {
        this.proprietario = proprietarioPessoa;
    }

    @Override
    public String getIdUnico() {
        return placa;
    }
}