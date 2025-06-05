package br.edu.cs.poo.ac.seguro.telas;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;

import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.mediators.ApoliceMediator;
import br.edu.cs.poo.ac.seguro.mediators.DadosVeiculo;
import br.edu.cs.poo.ac.seguro.mediators.RetornoInclusaoApolice;

public class TelaInclusaoApolice extends JFrame {

    private ApoliceMediator mediator;
    private JTextField txtCpfOuCnpj; // Mantido como JTextField pela complexidade da máscara condicional
    private JTextField txtPlaca;     // Placa pode ter formatos variados (Mercosul, antigo)
    private JFormattedTextField txtAno;
    private JFormattedTextField txtValorMaximoSegurado;
    private JComboBox<String> cmbCategoriaVeiculo;

    private JButton btnIncluir;
    private JButton btnLimpar;

    protected MaskFormatter createFormatter(String formatString) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(formatString);
            formatter.setPlaceholderCharacter('_');
        } catch (java.text.ParseException exc) {
            System.err.println("Erro na formatação da máscara: " + exc.getMessage());
            JOptionPane.showMessageDialog(this, "Erro ao criar máscara de campo: " + formatString, "Erro de Configuração", JOptionPane.ERROR_MESSAGE);
        }
        return formatter;
    }

    public TelaInclusaoApolice() {
        mediator = ApoliceMediator.getInstancia();
        setTitle("Inclusão de Apólice");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // setSize(450, 300); // pack() no final pode ser melhor
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // gbc.weightx = 1.0; // Removido para melhor ajuste com pack()

        // CPF ou CNPJ
        gbc.gridx = 0; gbc.gridy = 0; //gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("CPF/CNPJ do Segurado:"), gbc);
        gbc.gridx = 1; //gbc.anchor = GridBagConstraints.EAST;
        txtCpfOuCnpj = new JTextField(20);
        add(txtCpfOuCnpj, gbc);

        // Placa
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Placa do Veículo:"), gbc);
        gbc.gridx = 1;
        txtPlaca = new JTextField(20);
        add(txtPlaca, gbc);

        // Ano
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Ano do Veículo:"), gbc);
        gbc.gridx = 1;
        NumberFormat anoFormat = NumberFormat.getIntegerInstance();
        anoFormat.setGroupingUsed(false); // Sem separador de milhar
        txtAno = new JFormattedTextField(anoFormat);
        txtAno.setColumns(4);
        add(txtAno, gbc);

        // Valor Máximo Segurado
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Valor Máximo Segurado (R$):"), gbc);
        gbc.gridx = 1;
        NumberFormat valorFormat = NumberFormat.getNumberInstance(); // Ou getCurrencyInstance()
        valorFormat.setMinimumFractionDigits(2);
        valorFormat.setMaximumFractionDigits(2);
        txtValorMaximoSegurado = new JFormattedTextField(valorFormat);
        txtValorMaximoSegurado.setColumns(15);
        txtValorMaximoSegurado.setValue(0.00);
        add(txtValorMaximoSegurado, gbc);

        // Categoria do Veículo
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Categoria do Veículo:"), gbc);
        gbc.gridx = 1;
        cmbCategoriaVeiculo = new JComboBox<>();
        popularComboCategoriaVeiculo();
        add(cmbCategoriaVeiculo, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnIncluir = new JButton("Incluir");
        btnLimpar = new JButton("Limpar");
        buttonPanel.add(btnIncluir);
        buttonPanel.add(btnLimpar);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        btnIncluir.addActionListener(e -> {
            try {
                String cpfOuCnpj = txtCpfOuCnpj.getText().trim();
                String placa = txtPlaca.getText().trim();

                int ano;
                if (txtAno.getValue() instanceof Number) {
                    ano = ((Number) txtAno.getValue()).intValue();
                } else {
                    throw new NumberFormatException("Ano inválido.");
                }

                BigDecimal valorMaxSegurado;
                if (txtValorMaximoSegurado.getValue() instanceof Number) {
                    valorMaxSegurado = BigDecimal.valueOf(((Number) txtValorMaximoSegurado.getValue()).doubleValue());
                } else {
                    throw new NumberFormatException("Valor máximo segurado inválido.");
                }


                int selectedIndex = cmbCategoriaVeiculo.getSelectedIndex();
                if (selectedIndex < 0) {
                    JOptionPane.showMessageDialog(this, "Selecione uma categoria de veículo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String selectedCategoriaName = (String) cmbCategoriaVeiculo.getSelectedItem();
                CategoriaVeiculo selectedCategoria = null;
                for (CategoriaVeiculo cat : CategoriaVeiculo.values()) {
                    if (cat.getNome().equals(selectedCategoriaName)) {
                        selectedCategoria = cat;
                        break;
                    }
                }
                if (selectedCategoria == null) {
                    JOptionPane.showMessageDialog(this, "Categoria de veículo inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int codigoCategoria = selectedCategoria.getCodigo();

                DadosVeiculo dados = new DadosVeiculo(cpfOuCnpj, placa, ano, valorMaxSegurado, codigoCategoria);
                RetornoInclusaoApolice retorno = mediator.incluirApolice(dados);

                if (retorno.getMensagemErro() != null) {
                    JOptionPane.showMessageDialog(this, retorno.getMensagemErro(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Apólice incluída com sucesso! Anote o número da apólice: " + retorno.getNumeroApolice(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ano e Valor Máximo Segurado devem ser números válidos. " + ex.getMessage(), "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao incluir apólice: " + ex.getMessage(), "Erro Inesperado", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnLimpar.addActionListener(e -> limparCampos());

        // Configurar para que o botão Limpar responda ao Enter quando estiver com foco
        btnLimpar.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "limpar");
        btnLimpar.getActionMap().put("limpar", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                limparCampos();
            }
        });

        pack(); // Ajusta o tamanho da janela aos componentes
        setMinimumSize(new Dimension(450, getHeight())); // Garante largura mínima
        getRootPane().setDefaultButton(btnIncluir); // Enter para o botão Incluir
        setVisible(true);
    }

    private void configurarTeclasAtalho() {
        // Configurar Shift+Tab para o botão Limpar
        KeyStroke shiftTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.SHIFT_DOWN_MASK);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(shiftTab, "limpar");
        getRootPane().getActionMap().put("limpar", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                limparCampos();
            }
        });

        // Alternativa: usar F5 para limpar (mais comum)
        KeyStroke f5 = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(f5, "limpar_f5");
        getRootPane().getActionMap().put("limpar_f5", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                limparCampos();
            }
        });

        // Configurar Ctrl+L para limpar (outra opção comum)
        KeyStroke ctrlL = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ctrlL, "limpar_ctrl");
        getRootPane().getActionMap().put("limpar_ctrl", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                limparCampos();
            }
        });
    }

    private void popularComboCategoriaVeiculo() {
        CategoriaVeiculo[] categorias = CategoriaVeiculo.values();
        String[] nomesCategorias = new String[categorias.length];
        for (int i = 0; i < categorias.length; i++) {
            nomesCategorias[i] = categorias[i].getNome();
        }
        Arrays.sort(nomesCategorias, Comparator.naturalOrder());

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(nomesCategorias);
        cmbCategoriaVeiculo.setModel(model);

        if (cmbCategoriaVeiculo.getItemCount() > 0) {
            cmbCategoriaVeiculo.setSelectedIndex(0);
        }
    }

    private void limparCampos() {
        txtCpfOuCnpj.setText("");
        txtPlaca.setText("");
        txtAno.setValue(null); txtAno.setText("");// Limpa para JFormattedTextField
        txtValorMaximoSegurado.setValue(0.00); // Reseta para valor numérico padrão
        if (cmbCategoriaVeiculo.getItemCount() > 0) {
            cmbCategoriaVeiculo.setSelectedIndex(0);
        }
        txtCpfOuCnpj.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaInclusaoApolice::new);
    }
}