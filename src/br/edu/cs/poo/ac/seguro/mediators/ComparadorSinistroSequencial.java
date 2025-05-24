package br.edu.cs.poo.ac.seguro.mediators;

import java.util.Comparator;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;

public class ComparadorSinistroSequencial implements Comparator<Sinistro> {

    @Override
    public int compare(Sinistro s1, Sinistro s2) {
        // Extrai o sequencial dos números dos sinistros
        int seq1 = extrairSequencial(s1.getNumero());
        int seq2 = extrairSequencial(s2.getNumero());

        return Integer.compare(seq1, seq2);
    }

    private int extrairSequencial(String numeroSinistro) {
        if (numeroSinistro == null || numeroSinistro.length() < 3) {
            return 0;
        }

        // O sequencial são os últimos 3 caracteres
        String sequencialStr = numeroSinistro.substring(numeroSinistro.length() - 3);
        try {
            return Integer.parseInt(sequencialStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}