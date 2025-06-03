package br.edu.cs.poo.ac.seguro.telas;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;

// Ajuste os imports conforme a estrutura de pacotes do seu projeto final.
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.mediators.ApoliceMediator;
import br.edu.cs.poo.ac.seguro.mediators.DadosVeiculo;
import br.edu.cs.poo.ac.seguro.mediators.RetornoInclusaoApolice;

public class TelaInclusaoApolice extends JFrame {

    private ApoliceMediator mediator;

    private JTextField txtCpfCnpj;
    private JTextField txtPlaca;
    private JTextField txtAno;
    private JTextField txtValorMaximoSegurado;
    private JComboBox<String> comboCategoriaVeiculo;

    private JButton btnIncluir;
    private JButton btnLimpar;

    public TelaInclusaoApolice() {
        mediator = ApoliceMediator.getInstancia();
        setTitle("Inclusão de Apólice");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 300); // Ajustado
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Linha 0: CPF/CNPJ
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3; add(new JLabel("CPF/CNPJ do Segurado:"), gbc);
        txtCpfCnpj = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7; add(txtCpfCnpj, gbc);

        // Linha 1: Placa
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Placa do Veículo:"), gbc);
        txtPlaca = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = 1; add(txtPlaca, gbc);

        // Linha 2: Ano
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Ano do Veículo (2020-2025):"), gbc);
        txtAno = new JTextField(5);
        gbc.gridx = 1; gbc.gridy = 2; add(txtAno, gbc);

        // Linha 3: Valor Máximo Segurado
        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Valor Máx. Segurado:"), gbc);
        txtValorMaximoSegurado = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = 3; add(txtValorMaximoSegurado, gbc);

        // Linha 4: Categoria Veículo
        gbc.gridx = 0; gbc.gridy = 4; add(new JLabel("Categoria do Veículo:"), gbc);
        comboCategoriaVeiculo = new JComboBox<>();
        CategoriaVeiculo[] categorias = CategoriaVeiculo.values();
        Arrays.sort(categorias, Comparator.comparing(CategoriaVeiculo::getNome)); // Ordena pelo nome
        for (CategoriaVeiculo cat : categorias) {
            comboCategoriaVeiculo.addItem(cat.getNome());
        }
        gbc.gridx = 1; gbc.gridy = 4; add(comboCategoriaVeiculo, gbc);


        // Linha 5: Buttons Panel
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnIncluir = new JButton("Incluir Apólice");
        btnLimpar = new JButton("Limpar Campos");

        panelBotoes.add(btnIncluir);
        panelBotoes.add(btnLimpar);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; add(panelBotoes, gbc);

        addListeners();
        pack();
        setSize(getWidth() + 50, getHeight());
        setLocationRelativeTo(null);
    }

    private void clearFields() {
        txtCpfCnpj.setText("");
        txtPlaca.setText("");
        txtAno.setText("");
        txtValorMaximoSegurado.setText("");
        if (comboCategoriaVeiculo.getItemCount() > 0) {
            comboCategoriaVeiculo.setSelectedIndex(0); //
        }
        txtCpfCnpj.requestFocusInWindow();
    }

    private CategoriaVeiculo getSelectedCategoria() {
        String nomeSelecionado = (String) comboCategoriaVeiculo.getSelectedItem();
        if (nomeSelecionado == null) return null;

        for (CategoriaVeiculo cat : CategoriaVeiculo.values()) {
            if (cat.getNome().equals(nomeSelecionado)) {
                return cat;
            }
        }
        return null;
    }

    private void addListeners() {
        btnIncluir.addActionListener(e -> {
            String cpfOuCnpj = txtCpfCnpj.getText().trim();
            String placa = txtPlaca.getText().trim();
            int ano;
            BigDecimal valorMaxSegurado;
            CategoriaVeiculo categoriaSelecionada = getSelectedCategoria();

            if (cpfOuCnpj.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CPF ou CNPJ deve ser informado.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (placa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Placa do veículo deve ser informada.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (txtAno.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ano do veículo deve ser informado.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (txtValorMaximoSegurado.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Valor máximo segurado deve ser informado.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (categoriaSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma categoria válida.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int codigoCategoria = categoriaSelecionada.getCodigo();


            try {
                ano = Integer.parseInt(txtAno.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ano do veículo deve ser um número inteiro.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                valorMaxSegurado = new BigDecimal(txtValorMaximoSegurado.getText().trim().replace(",", "."));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valor máximo segurado deve ser um número (ex: 15000.00).", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DadosVeiculo dadosVeiculo = new DadosVeiculo(cpfOuCnpj, placa, ano, valorMaxSegurado, codigoCategoria);
            RetornoInclusaoApolice retorno = mediator.incluirApolice(dadosVeiculo); //

            if (retorno.getMensagemErro() != null) {
                JOptionPane.showMessageDialog(this, retorno.getMensagemErro(), "Erro na Inclusão da Apólice", JOptionPane.ERROR_MESSAGE); //
            } else {
                JOptionPane.showMessageDialog(this, "Apólice incluída com sucesso! Anote o número da apólice: " + retorno.getNumeroApolice(), "Sucesso", JOptionPane.INFORMATION_MESSAGE); //
                clearFields();
            }
        });

        btnLimpar.addActionListener(e -> clearFields());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new TelaInclusaoApolice().setVisible(true);
        });
    }
}