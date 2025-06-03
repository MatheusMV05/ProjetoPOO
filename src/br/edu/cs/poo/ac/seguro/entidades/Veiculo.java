package br.edu.cs.poo.ac.seguro.entidades;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;



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


    @Override
    public String getIdUnico() {
        return placa;
    }
}