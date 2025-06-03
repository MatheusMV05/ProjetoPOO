package br.edu.cs.poo.ac.seguro.telas;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoEmpresaMediator;

public class TelaCrudSeguradoEmpresa extends JFrame {

    private SeguradoEmpresaMediator mediator = SeguradoEmpresaMediator.getInstancia();
    private JFormattedTextField txtCnpj;
    private JTextField txtNome;
    private JFormattedTextField txtDataAbertura;
    private JFormattedTextField txtFaturamento;
    private JFormattedTextField txtBonus;
    private JCheckBox chkEhLocadora;

    private JTextField txtLogradouro;
    private JFormattedTextField txtCep;
    private JTextField txtNumeroEndereco;
    private JTextField txtComplemento;
    private JTextField txtCidade;
    private JTextField txtEstado;
    private JTextField txtPais;

    private JButton btnNovo;
    private JButton btnBuscar;
    private JButton btnIncluirAlterar;
    private JButton btnExcluir;
    private JButton btnCancelar;
    private JButton btnLimpar;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

    public TelaCrudSeguradoEmpresa() {
        setTitle("CRUD de Segurado Empresa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 550);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // CNPJ
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("CNPJ:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtCnpj = new JFormattedTextField(createFormatter("##.###.###/####-##"));
        txtCnpj.setColumns(18);
        add(txtCnpj, gbc);
        gbc.weightx = 0.0;

        gbc.gridx = 2;
        btnNovo = new JButton("Novo");
        add(btnNovo, gbc);

        gbc.gridx = 3;
        btnBuscar = new JButton("Buscar");
        add(btnBuscar, gbc);

        // Nome
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtNome = new JTextField(20);
        add(txtNome, gbc);
        gbc.gridwidth = 1;

        // Data de Abertura
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Data Abertura (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtDataAbertura = new JFormattedTextField(createFormatter("##/##/####"));
        txtDataAbertura.setColumns(10);
        add(txtDataAbertura, gbc);
        gbc.gridwidth = 1;

        // Faturamento
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Faturamento:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        NumberFormat faturamentoFormat = NumberFormat.getNumberInstance(); // Ou getCurrencyInstance()
        faturamentoFormat.setMinimumFractionDigits(2);
        faturamentoFormat.setMaximumFractionDigits(2);
        txtFaturamento = new JFormattedTextField(faturamentoFormat);
        txtFaturamento.setColumns(15);
        txtFaturamento.setValue(0.00);
        add(txtFaturamento, gbc);
        gbc.gridwidth = 1;

        // Bonus
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Bônus:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        NumberFormat bonusFormat = NumberFormat.getNumberInstance();
        bonusFormat.setMinimumFractionDigits(2);
        bonusFormat.setMaximumFractionDigits(2);
        txtBonus = new JFormattedTextField(bonusFormat);
        txtBonus.setColumns(15);
        txtBonus.setValue(0.00);
        add(txtBonus, gbc);
        gbc.gridwidth = 1;

        // Eh Locadora
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("É Locadora de Veículos?"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        chkEhLocadora = new JCheckBox();
        add(chkEhLocadora, gbc);
        gbc.gridwidth = 1;

        // Endereço Fields
        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("Logradouro:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtLogradouro = new JTextField(20);
        add(txtLogradouro, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 7;
        add(new JLabel("CEP:"), gbc);
        gbc.gridx = 1;
        txtCep = new JFormattedTextField(createFormatter("#####-###"));
        txtCep.setColumns(9);
        add(txtCep, gbc);

        gbc.gridx = 2;
        add(new JLabel("Número:"), gbc);
        gbc.gridx = 3;
        txtNumeroEndereco = new JTextField(5);
        add(txtNumeroEndereco, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        add(new JLabel("Complemento:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtComplemento = new JTextField(20);
        add(txtComplemento, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 9;
        add(new JLabel("Cidade:"), gbc);
        gbc.gridx = 1;
        txtCidade = new JTextField(15);
        add(txtCidade, gbc);

        gbc.gridx = 2;
        add(new JLabel("Estado (UF):"), gbc);
        gbc.gridx = 3;
        txtEstado = new JTextField(2);
        add(txtEstado, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        add(new JLabel("País:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtPais = new JTextField(15);
        add(txtPais, gbc);
        gbc.gridwidth = 1;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnIncluirAlterar = new JButton("Incluir");
        btnExcluir = new JButton("Excluir");
        btnCancelar = new JButton("Cancelar");
        btnLimpar = new JButton("Limpar");

        buttonPanel.add(btnIncluirAlterar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnLimpar);

        gbc.gridx = 0; gbc.gridy = 11; gbc.gridwidth = 4; gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        btnNovo.addActionListener(e -> {
            String cnpjInput = txtCnpj.getText().replaceAll("[^0-9]", "");
            if (cnpjInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CNPJ deve ser informado para verificar existência.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SeguradoEmpresa seg = mediator.buscarSeguradoEmpresa(cnpjInput);
            if (seg != null) {
                JOptionPane.showMessageDialog(this, "Segurado Empresa já existente!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                habilitarCamposEdicao(true);
                btnIncluirAlterar.setText("Incluir");
                btnNovo.setEnabled(false);
                btnBuscar.setEnabled(false);
                txtCnpj.setEditable(false);
            }
        });

        btnBuscar.addActionListener(e -> {
            String cnpjInput = txtCnpj.getText().replaceAll("[^0-9]", "");
            if (cnpjInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CNPJ deve ser informado para busca.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SeguradoEmpresa seg = mediator.buscarSeguradoEmpresa(cnpjInput);
            if (seg == null) {
                JOptionPane.showMessageDialog(this, "Segurado Empresa não existente!", "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                preencherCamposComDados(seg);
                habilitarCamposEdicao(true);
                btnIncluirAlterar.setText("Alterar");
                btnExcluir.setEnabled(true);
                btnNovo.setEnabled(false);
                btnBuscar.setEnabled(false);
                txtCnpj.setEditable(false);
            }
        });

        btnIncluirAlterar.addActionListener(e -> {
            try {
                String cnpj = txtCnpj.getText().replaceAll("[^0-9]", "");
                String nome = txtNome.getText().trim();
                LocalDate dataAbertura = LocalDate.parse(txtDataAbertura.getText(), DATE_FORMATTER);

                double faturamento;
                if (txtFaturamento.getValue() instanceof Number) {
                    faturamento = ((Number) txtFaturamento.getValue()).doubleValue();
                } else {
                    throw new NumberFormatException("Faturamento inválido.");
                }

                BigDecimal bonus;
                if (txtBonus.getValue() instanceof Number) {
                    bonus = BigDecimal.valueOf(((Number) txtBonus.getValue()).doubleValue());
                } else {
                    throw new NumberFormatException("Bônus inválido.");
                }
                boolean ehLocadora = chkEhLocadora.isSelected();

                Endereco endereco = new Endereco(
                        txtLogradouro.getText().trim(),
                        txtCep.getText().replaceAll("[^0-9]", ""),
                        txtNumeroEndereco.getText().trim(),
                        txtComplemento.getText().trim(),
                        txtPais.getText().trim(),
                        txtEstado.getText().trim(),
                        txtCidade.getText().trim()
                );

                SeguradoEmpresa seg = new SeguradoEmpresa(nome, endereco, dataAbertura, bonus, cnpj, faturamento, ehLocadora);
                String msg;
                String msgOk;

                if (btnIncluirAlterar.getText().equals("Incluir")) {
                    msg = mediator.incluirSeguradoEmpresa(seg);
                    msgOk = "Inclusão realizada com sucesso!";
                } else {
                    msg = mediator.alterarSeguradoEmpresa(seg);
                    msgOk = "Alteração realizada com sucesso!";
                }

                if (msg != null) {
                    JOptionPane.showMessageDialog(this, msg, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, msgOk, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    resetarTelaParaEstadoInicial();
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data de Abertura inválida. Use o formato dd/MM/yyyy e preencha corretamente.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Faturamento e/ou Bônus devem ser números válidos. " + ex.getMessage(), "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro Inesperado", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnExcluir.addActionListener(e -> {
            String cnpj = txtCnpj.getText().replaceAll("[^0-9]", "");
            if (cnpj.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CNPJ não carregado para exclusão.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Confirma a exclusão do segurado empresa?", "Excluir Segurado Empresa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String msg = mediator.excluirSeguradoEmpresa(cnpj);
                if (msg != null) {
                    JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Exclusão realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    resetarTelaParaEstadoInicial();
                }
            }
        });

        btnCancelar.addActionListener(e -> resetarTelaParaEstadoInicial());

        btnLimpar.addActionListener(e -> {
            limparCamposTextoParcial();
            if (txtCnpj.isEditable()) {
                txtCnpj.setValue(null);
                txtCnpj.setText("");
            }
        });

        resetarTelaParaEstadoInicial();
        setVisible(true);
    }

    private void habilitarCamposEdicao(boolean habilitar) {
        txtNome.setEditable(habilitar);
        txtDataAbertura.setEditable(habilitar);
        txtFaturamento.setEditable(habilitar);
        txtBonus.setEditable(habilitar);
        chkEhLocadora.setEnabled(habilitar); // Checkbox usa setEnabled
        txtLogradouro.setEditable(habilitar);
        txtCep.setEditable(habilitar);
        txtNumeroEndereco.setEditable(habilitar);
        txtComplemento.setEditable(habilitar);
        txtCidade.setEditable(habilitar);
        txtEstado.setEditable(habilitar);
        txtPais.setEditable(habilitar);

        btnIncluirAlterar.setEnabled(habilitar);
        btnCancelar.setEnabled(habilitar);
    }

    private void limparCamposTextoParcial() {
        if (txtNome.isEditable()) txtNome.setText("");
        if (txtDataAbertura.isEditable()) {txtDataAbertura.setValue(null); txtDataAbertura.setText("");}
        if (txtFaturamento.isEditable()) txtFaturamento.setValue(0.00);
        if (txtBonus.isEditable()) txtBonus.setValue(0.00);
        if (chkEhLocadora.isEnabled()) chkEhLocadora.setSelected(false);
        if (txtLogradouro.isEditable()) txtLogradouro.setText("");
        if (txtCep.isEditable()) {txtCep.setValue(null); txtCep.setText("");}
        if (txtNumeroEndereco.isEditable()) txtNumeroEndereco.setText("");
        if (txtComplemento.isEditable()) txtComplemento.setText("");
        if (txtCidade.isEditable()) txtCidade.setText("");
        if (txtEstado.isEditable()) txtEstado.setText("");
        if (txtPais.isEditable()) txtPais.setText("");
    }

    private void limparCamposTextoCompleto() {
        txtNome.setText("");
        txtDataAbertura.setValue(null); txtDataAbertura.setText("");
        txtFaturamento.setValue(0.00);
        txtBonus.setValue(0.00);
        chkEhLocadora.setSelected(false);
        txtLogradouro.setText("");
        txtCep.setValue(null); txtCep.setText("");
        txtNumeroEndereco.setText("");
        txtComplemento.setText("");
        txtCidade.setText("");
        txtEstado.setText("");
        txtPais.setText("");
    }

    private void preencherCamposComDados(SeguradoEmpresa seg) {
        txtCnpj.setText(seg.getCnpj());
        txtNome.setText(seg.getNome());
        txtDataAbertura.setText(seg.getDataAbertura() != null ? seg.getDataAbertura().format(DATE_FORMATTER) : null);
        txtFaturamento.setValue(seg.getFaturamento());
        txtBonus.setValue(seg.getBonus() != null ? seg.getBonus() : BigDecimal.ZERO);
        chkEhLocadora.setSelected(seg.getEhLocadoraDeVeiculos());

        if (seg.getEndereco() != null) {
            Endereco end = seg.getEndereco();
            txtLogradouro.setText(end.getLogradouro() != null ? end.getLogradouro() : "");
            txtCep.setText(end.getCep() != null ? end.getCep() : null);
            txtNumeroEndereco.setText(end.getNumero() != null ? end.getNumero() : "");
            txtComplemento.setText(end.getComplemento() != null ? end.getComplemento() : "");
            txtCidade.setText(end.getCidade() != null ? end.getCidade() : "");
            txtEstado.setText(end.getEstado() != null ? end.getEstado() : "");
            txtPais.setText(end.getPais() != null ? end.getPais() : "");
        } else {
            limparCamposEndereco();
        }
    }

    private void limparCamposEndereco() {
        txtLogradouro.setText("");
        txtCep.setValue(null); txtCep.setText("");
        txtNumeroEndereco.setText("");
        txtComplemento.setText("");
        txtCidade.setText("");
        txtEstado.setText("");
        txtPais.setText("");
    }

    private void resetarTelaParaEstadoInicial() {
        limparCamposTextoCompleto();
        txtCnpj.setValue(null); txtCnpj.setText("");
        txtCnpj.setEditable(true);
        habilitarCamposEdicao(false);
        btnNovo.setEnabled(true);
        btnBuscar.setEnabled(true);
        btnExcluir.setEnabled(false);
        btnIncluirAlterar.setText("Incluir");
        txtCnpj.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaCrudSeguradoEmpresa::new);
    }
}