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
    private SeguradoEmpresa proprietarioEmpresa;
    private SeguradoPessoa proprietarioPessoa;
    private CategoriaVeiculo categoria;

    // Construtor vazio para compatibilidade
    public Veiculo() {
    }

    // Construtor principal - mantém compatibilidade com testes
    public Veiculo(String placa, int ano, Segurado proprietario, CategoriaVeiculo categoria) {
        this.placa = placa;
        this.ano = ano;
        this.categoria = categoria;
        setProprietario(proprietario);
    }

    // Construtor para SeguradoPessoa (usado nos testes)
    public Veiculo(String placa, int ano, SeguradoPessoa proprietarioPessoa,
                   CategoriaVeiculo categoria) {
        this.placa = placa;
        this.ano = ano;
        this.proprietarioPessoa = proprietarioPessoa;
        this.proprietarioEmpresa = null;
        this.categoria = categoria;
    }

    // Construtor para SeguradoEmpresa
    public Veiculo(String placa, int ano, SeguradoEmpresa proprietarioEmpresa,
                   CategoriaVeiculo categoria) {
        this.placa = placa;
        this.ano = ano;
        this.proprietarioEmpresa = proprietarioEmpresa;
        this.proprietarioPessoa = null;
        this.categoria = categoria;
    }

    // Construtor com ambos proprietários (compatibilidade)
    public Veiculo(String placa, int ano, SeguradoEmpresa proprietarioEmpresa,
                   SeguradoPessoa proprietarioPessoa, CategoriaVeiculo categoria) {
        this.placa = placa;
        this.ano = ano;
        this.proprietarioEmpresa = proprietarioEmpresa;
        this.proprietarioPessoa = proprietarioPessoa;
        this.categoria = categoria;
    }

    // Método para obter o proprietário (polimórfico)
    public Segurado getProprietario() {
        if (proprietarioEmpresa != null) {
            return proprietarioEmpresa;
        }
        return proprietarioPessoa;
    }

    // Método para definir o proprietário (polimórfico)
    public void setProprietario(Segurado proprietario) {
        if (proprietario instanceof SeguradoEmpresa) {
            this.proprietarioEmpresa = (SeguradoEmpresa) proprietario;
            this.proprietarioPessoa = null;
        } else if (proprietario instanceof SeguradoPessoa) {
            this.proprietarioPessoa = (SeguradoPessoa) proprietario;
            this.proprietarioEmpresa = null;
        }
    }

    @Override
    public String getIdUnico() {
        return placa;
    }
}