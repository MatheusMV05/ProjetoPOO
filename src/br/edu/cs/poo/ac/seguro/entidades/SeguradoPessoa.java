package br.edu.cs.poo.ac.seguro.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SeguradoPessoa extends Segurado {

    private String cnpj;
    private Double renda;

    public SeguradoPessoa(String cnpj, Double renda, LocalDate dataNascimento, BigDecimal bonus) {
        super(nome, endereco, dataNascimento, bonus);
        this.cnpj = cnpj;
        this.renda = renda;
    }

    void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    String getCnpj() {
        this.cnpj = cnpj;
    }

    void setRenda(Double renda) {
        this.renda = renda;
    }

    Double getRenda() {
        this.renda = renda;
    }

    public LocalDate getDataNascimento() {
        return dataCriacao;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        setDataCriacao(dataNascimento);
    }





}
