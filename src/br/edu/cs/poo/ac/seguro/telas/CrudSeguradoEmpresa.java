package br.edu.cs.poo.ac.seguro.telas;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Ajuste os imports conforme a estrutura de pacotes do seu projeto final.
import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoEmpresaMediator;

public class CrudSeguradoEmpresa extends JFrame {

    private SeguradoEmpresaMediator mediator;

    private JTextField txtCnpj;
    private JTextField txtNome;
    private JTextField txtFaturamento;
    private JCheckBox chkEhLocadora;
    private JTextField txtDataAbertura; // YYYY-MM-DD
    private JTextField txtBonus;

    // Endereco fields
    private JTextField txtLogradouro;
    private JTextField txtCep;
    private JTextField txtNumeroEndereco;
    private JTextField txtComplemento;
    private JTextField txtPais;
    private JTextField txtEstado;
    private JTextField txtCidade;

    private JButton btnNovo;
    private JButton btnBuscar;
    private JButton btnIncluirAlterar;
    private JButton btnExcluir;
    private JButton btnCancelar;
    private JButton btnLimpar;

    public CrudSeguradoEmpresa() {
        mediator = SeguradoEmpresaMediator.getInstancia();
        setTitle("CRUD de Segurado Empresa");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 530); // Ajustado
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Linha 0: CNPJ, Novo, Buscar
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0; add(new JLabel("CNPJ:"), gbc);
        txtCnpj = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7; gbc.gridwidth = 2; add(txtCnpj, gbc);

        btnNovo = new JButton("Novo");
        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 0.15; gbc.gridwidth = 1; add(btnNovo, gbc);

        btnBuscar = new JButton("Buscar");
        gbc.gridx = 4; gbc.gridy = 0; gbc.weightx = 0.15; add(btnBuscar, gbc);

        // Linha 1: Nome
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0; add(new JLabel("Nome Fantasia:"), gbc);
        txtNome = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; gbc.gridwidth = 4; add(txtNome, gbc);

        // Linha 2: Faturamento, Data Abertura
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0; gbc.gridwidth = 1; add(new JLabel("Faturamento Anual:"), gbc);
        txtFaturamento = new JTextField();
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.3; add(txtFaturamento, gbc);

        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0.0; add(new JLabel("Data Abertura (YYYY-MM-DD):"), gbc);
        txtDataAbertura = new JTextField();
        gbc.gridx = 3; gbc.gridy = 2; gbc.weightx = 0.7; gbc.gridwidth = 2; add(txtDataAbertura, gbc);

        // Linha 3: Bônus, É Locadora
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0; gbc.gridwidth = 1; add(new JLabel("Bônus:"), gbc);
        txtBonus = new JTextField();
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0.3; add(txtBonus, gbc);

        chkEhLocadora = new JCheckBox("É Locadora de Veículos?");
        gbc.gridx = 2; gbc.gridy = 3; gbc.weightx = 0.7; gbc.gridwidth = 3; add(chkEhLocadora, gbc);


        // Linha 4: Endereço Panel
        JPanel panelEndereco = new JPanel(new GridBagLayout());
        panelEndereco.setBorder(BorderFactory.createTitledBorder("Endereço"));
        GridBagConstraints gbcEndereco = new GridBagConstraints();
        gbcEndereco.insets = new Insets(3, 3, 3, 3);
        gbcEndereco.fill = GridBagConstraints.HORIZONTAL;
        gbcEndereco.weightx = 1.0;

        gbcEndereco.gridx = 0; gbcEndereco.gridy = 0; gbcEndereco.weightx = 0.0; panelEndereco.add(new JLabel("Logradouro:"), gbcEndereco);
        txtLogradouro = new JTextField();
        gbcEndereco.gridx = 1; gbcEndereco.gridy = 0; gbcEndereco.weightx = 1.0; gbcEndereco.gridwidth = 3; panelEndereco.add(txtLogradouro, gbcEndereco);

        gbcEndereco.gridx = 0; gbcEndereco.gridy = 1; gbcEndereco.weightx = 0.0; gbcEndereco.gridwidth = 1; panelEndereco.add(new JLabel("CEP:"), gbcEndereco);
        txtCep = new JTextField();
        gbcEndereco.gridx = 1; gbcEndereco.gridy = 1; gbcEndereco.weightx = 0.4; panelEndereco.add(txtCep, gbcEndereco);

        gbcEndereco.gridx = 2; gbcEndereco.gridy = 1; gbcEndereco.weightx = 0.0; panelEndereco.add(new JLabel("Número:"), gbcEndereco);
        txtNumeroEndereco = new JTextField();
        gbcEndereco.gridx = 3; gbcEndereco.gridy = 1; gbcEndereco.weightx = 0.6; panelEndereco.add(txtNumeroEndereco, gbcEndereco);

        gbcEndereco.gridx = 0; gbcEndereco.gridy = 2; gbcEndereco.weightx = 0.0; panelEndereco.add(new JLabel("Complemento:"), gbcEndereco);
        txtComplemento = new JTextField();
        gbcEndereco.gridx = 1; gbcEndereco.gridy = 2; gbcEndereco.weightx = 0.4; panelEndereco.add(txtComplemento, gbcEndereco);

        gbcEndereco.gridx = 2; gbcEndereco.gridy = 2; gbcEndereco.weightx = 0.0; panelEndereco.add(new JLabel("País:"), gbcEndereco);
        txtPais = new JTextField();
        gbcEndereco.gridx = 3; gbcEndereco.gridy = 2; gbcEndereco.weightx = 0.6; panelEndereco.add(txtPais, gbcEndereco);

        gbcEndereco.gridx = 0; gbcEndereco.gridy = 3; gbcEndereco.weightx = 0.0; panelEndereco.add(new JLabel("Estado (UF):"), gbcEndereco);
        txtEstado = new JTextField();
        gbcEndereco.gridx = 1; gbcEndereco.gridy = 3; gbcEndereco.weightx = 0.4; panelEndereco.add(txtEstado, gbcEndereco);

        gbcEndereco.gridx = 2; gbcEndereco.gridy = 3; gbcEndereco.weightx = 0.0; panelEndereco.add(new JLabel("Cidade:"), gbcEndereco);
        txtCidade = new JTextField();
        gbcEndereco.gridx = 3; gbcEndereco.gridy = 3; gbcEndereco.weightx = 0.6; panelEndereco.add(txtCidade, gbcEndereco);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 5; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH; add(panelEndereco, gbc);


        // Linha 5: Buttons Panel
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnIncluirAlterar = new JButton("Incluir");
        btnExcluir = new JButton("Excluir");
        btnCancelar = new JButton("Cancelar");
        btnLimpar = new JButton("Limpar");

        panelBotoes.add(btnIncluirAlterar);
        panelBotoes.add(btnExcluir);
        panelBotoes.add(btnCancelar);
        panelBotoes.add(btnLimpar);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 5; gbc.weighty = 0.0; gbc.fill = GridBagConstraints.HORIZONTAL; add(panelBotoes, gbc);

        setInitialState();
        addListeners();
        pack();
        setSize(getWidth() + 50, getHeight());
        setLocationRelativeTo(null);
    }

    private void setAllFieldsEnabled(boolean enabled) {
        txtNome.setEnabled(enabled);
        txtFaturamento.setEnabled(enabled);
        chkEhLocadora.setEnabled(enabled);
        txtDataAbertura.setEnabled(enabled);
        txtBonus.setEnabled(enabled);
        txtLogradouro.setEnabled(enabled);
        txtCep.setEnabled(enabled);
        txtNumeroEndereco.setEnabled(enabled);
        txtComplemento.setEnabled(enabled);
        txtPais.setEnabled(enabled);
        txtEstado.setEnabled(enabled);
        txtCidade.setEnabled(enabled);
    }

    private void clearFields() {
        if (txtCnpj.isEnabled()) {
            txtCnpj.setText("");
        }
        txtNome.setText("");
        txtFaturamento.setText("");
        chkEhLocadora.setSelected(false);
        txtDataAbertura.setText("");
        txtBonus.setText("");
        txtLogradouro.setText("");
        txtCep.setText("");
        txtNumeroEndereco.setText("");
        txtComplemento.setText("");
        txtPais.setText("");
        txtEstado.setText("");
        txtCidade.setText("");
    }

    private void setInitialState() {
        clearFields();
        txtCnpj.setEnabled(true);
        txtCnpj.setEditable(true);
        setAllFieldsEnabled(false);
        txtBonus.setEnabled(false);

        btnNovo.setEnabled(true);
        btnBuscar.setEnabled(true);

        btnIncluirAlterar.setEnabled(false);
        btnIncluirAlterar.setText("Incluir");
        btnExcluir.setEnabled(false);
        btnCancelar.setEnabled(false);
    }

    private void setStateNovo() {
        txtCnpj.setEnabled(false);
        txtCnpj.setEditable(false);

        setAllFieldsEnabled(true);
        txtBonus.setEnabled(false);
        txtBonus.setText("0.00");
        txtNome.requestFocusInWindow();

        txtNome.setText("");
        txtFaturamento.setText("");
        chkEhLocadora.setSelected(false);
        txtDataAbertura.setText("");
        // txtBonus já setado
        txtLogradouro.setText("");
        txtCep.setText("");
        txtNumeroEndereco.setText("");
        txtComplemento.setText("");
        txtPais.setText("");
        txtEstado.setText("");
        txtCidade.setText("");

        btnNovo.setEnabled(false);
        btnBuscar.setEnabled(false);

        btnIncluirAlterar.setEnabled(true);
        btnIncluirAlterar.setText("Incluir");
        btnExcluir.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    private void setStateBuscar(SeguradoEmpresa segurado) {
        txtCnpj.setEnabled(false);
        txtCnpj.setEditable(false);

        txtNome.setText(segurado.getNome());
        txtFaturamento.setText(String.valueOf(segurado.getFaturamento()));
        chkEhLocadora.setSelected(segurado.getEhLocadoraDeVeiculos());
        txtDataAbertura.setText(segurado.getDataAbertura() != null ? segurado.getDataAbertura().format(DateTimeFormatter.ISO_DATE) : "");
        txtBonus.setText(segurado.getBonus() != null ? segurado.getBonus().toPlainString() : "0.00");

        if (segurado.getEndereco() != null) {
            Endereco end = segurado.getEndereco();
            txtLogradouro.setText(end.getLogradouro() != null ? end.getLogradouro() : "");
            txtCep.setText(end.getCep() != null ? end.getCep() : "");
            txtNumeroEndereco.setText(end.getNumero() != null ? end.getNumero() : "");
            txtComplemento.setText(end.getComplemento() != null ? end.getComplemento() : "");
            txtPais.setText(end.getPais() != null ? end.getPais() : "");
            txtEstado.setText(end.getEstado() != null ? end.getEstado() : "");
            txtCidade.setText(end.getCidade() != null ? end.getCidade() : "");
        } else {
            txtLogradouro.setText("");
            txtCep.setText("");
            txtNumeroEndereco.setText("");
            txtComplemento.setText("");
            txtPais.setText("");
            txtEstado.setText("");
            txtCidade.setText("");
        }

        setAllFieldsEnabled(true);
        txtBonus.setEnabled(false);

        btnNovo.setEnabled(false);
        btnBuscar.setEnabled(false);

        btnIncluirAlterar.setEnabled(true);
        btnIncluirAlterar.setText("Alterar");
        btnExcluir.setEnabled(true);
        btnCancelar.setEnabled(true);
    }

    private SeguradoEmpresa getSeguradoFromFields() {
        String cnpj = txtCnpj.getText().trim();
        String nome = txtNome.getText().trim();
        double faturamento;
        boolean ehLocadora = chkEhLocadora.isSelected();
        LocalDate dataAbertura = null;
        BigDecimal bonus;

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome Fantasia deve ser preenchido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            faturamento = Double.parseDouble(txtFaturamento.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Faturamento deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            if (!txtDataAbertura.getText().trim().isEmpty()) {
                dataAbertura = LocalDate.parse(txtDataAbertura.getText().trim(), DateTimeFormatter.ISO_DATE);
            } else {
                JOptionPane.showMessageDialog(this, "Data de Abertura deve ser preenchida.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data de Abertura deve estar no formato YYYY-MM-DD.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            bonus = new BigDecimal(txtBonus.getText().trim().isEmpty() ? "0" : txtBonus.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Bônus deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Endereco endereco = new Endereco(
                txtLogradouro.getText().trim(),
                txtCep.getText().trim(),
                txtNumeroEndereco.getText().trim(),
                txtComplemento.getText().trim(),
                txtPais.getText().trim(),
                txtEstado.getText().trim(),
                txtCidade.getText().trim()
        );
        if (endereco.getLogradouro().isEmpty() || endereco.getCep().isEmpty() || endereco.getCidade().isEmpty() || endereco.getEstado().isEmpty() || endereco.getPais().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos de endereço obrigatórios (Logradouro, CEP, Cidade, Estado, País) devem ser preenchidos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return new SeguradoEmpresa(nome, endereco, dataAbertura, bonus, cnpj, faturamento, ehLocadora);
    }

    private void addListeners() {
        btnNovo.addActionListener(e -> {
            String cnpj = txtCnpj.getText().trim();
            if (cnpj.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CNPJ deve ser preenchido para 'Novo'.", "Aviso", JOptionPane.WARNING_MESSAGE);
                txtCnpj.requestFocusInWindow();
                return;
            }
            if (cnpj.length() != 14 || !cnpj.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "CNPJ deve conter 14 dígitos numéricos.", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }
            SeguradoEmpresa seg = mediator.buscarSeguradoEmpresa(cnpj);
            if (seg != null) {
                JOptionPane.showMessageDialog(this, "Segurado Empresa já existente com este CNPJ!", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                setStateNovo();
            }
        });

        btnBuscar.addActionListener(e -> {
            String cnpj = txtCnpj.getText().trim();
            if (cnpj.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CNPJ deve ser preenchido para 'Buscar'.", "Aviso", JOptionPane.WARNING_MESSAGE);
                txtCnpj.requestFocusInWindow();
                return;
            }
            if (cnpj.length() != 14 || !cnpj.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "CNPJ deve conter 14 dígitos numéricos.", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }
            SeguradoEmpresa seg = mediator.buscarSeguradoEmpresa(cnpj);
            if (seg == null) {
                JOptionPane.showMessageDialog(this, "Segurado Empresa não existente!", "Aviso", JOptionPane.WARNING_MESSAGE);
                clearFields();
                txtCnpj.setText(cnpj);
                txtCnpj.requestFocusInWindow();
            } else {
                setStateBuscar(seg);
            }
        });

        btnIncluirAlterar.addActionListener(e -> {
            SeguradoEmpresa segurado = getSeguradoFromFields();
            if (segurado == null) return;

            String msg;
            String msgOk;

            if (btnIncluirAlterar.getText().equals("Incluir")) {
                msg = mediator.incluirSeguradoEmpresa(segurado);
                msgOk = "Inclusão realizada com sucesso!";
            } else {
                msg = mediator.alterarSeguradoEmpresa(segurado);
                msgOk = "Alteração realizada com sucesso!";
            }

            if (msg != null) {
                JOptionPane.showMessageDialog(this, msg, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, msgOk, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                setInitialState();
            }
        });

        btnExcluir.addActionListener(e -> {
            String cnpj = txtCnpj.getText().trim();
            if (cnpj.isEmpty()){
                JOptionPane.showMessageDialog(this, "Nenhum segurado carregado para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int response = JOptionPane.showConfirmDialog(this, "Confirma a exclusão do segurado com CNPJ: " + cnpj + "?",
                    "Excluir Segurado Empresa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                String msg = mediator.excluirSeguradoEmpresa(cnpj);
                if (msg != null) {
                    JOptionPane.showMessageDialog(this, msg, "Erro na Exclusão", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Exclusão realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    setInitialState();
                }
            }
        });

        btnCancelar.addActionListener(e -> setInitialState());

        btnLimpar.addActionListener(e -> {
            if (txtCnpj.isEnabled()) {
                txtCnpj.setText("");
            }
            txtNome.setText("");
            txtFaturamento.setText("");
            chkEhLocadora.setSelected(false);
            txtDataAbertura.setText("");
            txtBonus.setText("");
            txtLogradouro.setText("");
            txtCep.setText("");
            txtNumeroEndereco.setText("");
            txtComplemento.setText("");
            txtPais.setText("");
            txtEstado.setText("");
            txtCidade.setText("");

            if (txtNome.isEnabled()) {
                txtNome.requestFocusInWindow();
            } else {
                txtCnpj.requestFocusInWindow();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CrudSeguradoEmpresa().setVisible(true);
        });
    }
}