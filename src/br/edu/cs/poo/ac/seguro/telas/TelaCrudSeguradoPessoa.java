package br.edu.cs.poo.ac.seguro.telas;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoPessoaMediator;

public class TelaCrudSeguradoPessoa extends JFrame {

    private SeguradoPessoaMediator mediator = SeguradoPessoaMediator.getInstancia();
    // Campos alterados para JFormattedTextField
    private JFormattedTextField txtCpf;
    private JTextField txtNome; // Nome geralmente não precisa de máscara rígida
    private JFormattedTextField txtDataNascimento;
    private JFormattedTextField txtRenda;
    private JFormattedTextField txtBonus;

    // Endereco fields
    private JTextField txtLogradouro;
    private JFormattedTextField txtCep;
    private JTextField txtNumeroEndereco;
    private JTextField txtComplemento;
    private JTextField txtCidade;
    private JTextField txtEstado; // Geralmente 2 letras, pode não precisar de máscara rígida
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

    public TelaCrudSeguradoPessoa() {
        setTitle("CRUD de Segurado Pessoa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // CPF
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtCpf = new JFormattedTextField(createFormatter("###.###.###-##"));
        txtCpf.setColumns(14);
        add(txtCpf, gbc);
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

        // Data de Nascimento
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Data Nasc. (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtDataNascimento = new JFormattedTextField(createFormatter("##/##/####"));
        txtDataNascimento.setColumns(10);
        add(txtDataNascimento, gbc);
        gbc.gridwidth = 1;

        // Renda
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Renda:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        NumberFormat rendaFormat = NumberFormat.getNumberInstance();
        rendaFormat.setMinimumFractionDigits(2);
        rendaFormat.setMaximumFractionDigits(2);
        txtRenda = new JFormattedTextField(rendaFormat);
        txtRenda.setColumns(15);
        txtRenda.setValue(0.00);
        add(txtRenda, gbc);
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

        // Endereço Fields
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Logradouro:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtLogradouro = new JTextField(20);
        add(txtLogradouro, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 6;
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

        gbc.gridx = 0; gbc.gridy = 7;
        add(new JLabel("Complemento:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtComplemento = new JTextField(20);
        add(txtComplemento, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 8;
        add(new JLabel("Cidade:"), gbc);
        gbc.gridx = 1;
        txtCidade = new JTextField(15);
        add(txtCidade, gbc);

        gbc.gridx = 2;
        add(new JLabel("Estado (UF):"), gbc);
        gbc.gridx = 3;
        txtEstado = new JTextField(2);
        add(txtEstado, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
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

        gbc.gridx = 0; gbc.gridy = 10; gbc.gridwidth = 4; gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        btnNovo.addActionListener(e -> {
            String cpfInput = txtCpf.getText().replaceAll("[^0-9]", "");
            if (cpfInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CPF deve ser informado para verificar existência.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SeguradoPessoa seg = mediator.buscarSeguradoPessoa(cpfInput);
            if (seg != null) {
                JOptionPane.showMessageDialog(this, "Segurado já existente!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                habilitarCamposEdicao(true);
                btnIncluirAlterar.setText("Incluir");
                btnNovo.setEnabled(false);
                btnNovo.setFocusable(false);
                btnBuscar.setEnabled(false);
                btnBuscar.setFocusable(false);
                txtCpf.setEditable(false);
                txtCpf.setFocusable(false);

                // Botão excluir não disponível para novo registro
                btnExcluir.setEnabled(false);
                btnExcluir.setFocusable(false);

                // Colocar foco no primeiro campo editável
                txtNome.requestFocusInWindow();
            }
        });

        btnBuscar.addActionListener(e -> {
            String cpfInput = txtCpf.getText().replaceAll("[^0-9]", "");
            if (cpfInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CPF deve ser informado para busca.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SeguradoPessoa seg = mediator.buscarSeguradoPessoa(cpfInput);
            if (seg == null) {
                JOptionPane.showMessageDialog(this, "Segurado não existente!", "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                preencherCamposComDados(seg);
                habilitarCamposEdicao(true);
                btnIncluirAlterar.setText("Alterar");
                btnNovo.setEnabled(false);
                btnNovo.setFocusable(false);
                btnBuscar.setEnabled(false);
                btnBuscar.setFocusable(false);
                txtCpf.setEditable(false);
                txtCpf.setFocusable(false);

                // Botão excluir disponível para registro existente
                btnExcluir.setEnabled(true);
                btnExcluir.setFocusable(true);

                // Colocar foco no primeiro campo editável
                txtNome.requestFocusInWindow();
            }
        });

        btnIncluirAlterar.addActionListener(e -> {
            try {
                String cpf = txtCpf.getText().replaceAll("[^0-9]", "");
                String nome = txtNome.getText().trim();
                LocalDate dataNascimento = LocalDate.parse(txtDataNascimento.getText(), DATE_FORMATTER);

                double renda;
                if (txtRenda.getValue() instanceof Number) {
                    renda = ((Number) txtRenda.getValue()).doubleValue();
                } else {
                    throw new NumberFormatException("Renda inválida.");
                }

                BigDecimal bonus;
                if (txtBonus.getValue() instanceof Number) {
                    bonus = BigDecimal.valueOf(((Number) txtBonus.getValue()).doubleValue());
                } else {
                    throw new NumberFormatException("Bônus inválido.");
                }

                Endereco endereco = new Endereco(
                        txtLogradouro.getText().trim(),
                        txtCep.getText().replaceAll("[^0-9]", ""),
                        txtNumeroEndereco.getText().trim(),
                        txtComplemento.getText().trim(),
                        txtPais.getText().trim(),
                        txtEstado.getText().trim(),
                        txtCidade.getText().trim()
                );

                SeguradoPessoa seg = new SeguradoPessoa(nome, endereco, dataNascimento, bonus, cpf, renda);
                String msg;
                String msgOk;

                if (btnIncluirAlterar.getText().equals("Incluir")) {
                    msg = mediator.incluirSeguradoPessoa(seg);
                    msgOk = "Inclusão realizada com sucesso!";
                } else {
                    msg = mediator.alterarSeguradoPessoa(seg);
                    msgOk = "Alteração realizada com sucesso!";
                }

                if (msg != null) {
                    JOptionPane.showMessageDialog(this, msg, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, msgOk, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    resetarTelaParaEstadoInicial();
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data de Nascimento inválida. Use o formato dd/MM/yyyy e preencha corretamente.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Renda e/ou Bônus devem ser números válidos. " + ex.getMessage(), "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro Inesperado", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnExcluir.addActionListener(e -> {
            String cpf = txtCpf.getText().replaceAll("[^0-9]", "");
            if (cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CPF não carregado para exclusão.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Criar diálogo customizado para controle total da navegação por TAB
            JDialog dialog = new JDialog(this, "Excluir Segurado", true);
            dialog.setLayout(new BorderLayout());

            // Mensagem
            JLabel mensagem = new JLabel("Confirma a exclusão do segurado?", JLabel.CENTER);
            mensagem.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
            dialog.add(mensagem, BorderLayout.CENTER);

            // Painel de botões
            JPanel painelBotoes = new JPanel(new FlowLayout());
            JButton btnSim = new JButton("Sim");
            JButton btnNao = new JButton("Não");

            // Configurar tamanhos iguais
            Dimension tamanhoBotao = new Dimension(80, 30);
            btnSim.setPreferredSize(tamanhoBotao);
            btnNao.setPreferredSize(tamanhoBotao);

            painelBotoes.add(btnSim);
            painelBotoes.add(btnNao);
            dialog.add(painelBotoes, BorderLayout.SOUTH);

            // Variável para armazenar a resposta
            final boolean[] confirmado = {false};

            // Listeners dos botões
            btnSim.addActionListener(evt -> {
                confirmado[0] = true;
                dialog.dispose();
            });

            btnNao.addActionListener(evt -> {
                confirmado[0] = false;
                dialog.dispose();
            });

            // Configurar navegação por TAB
            btnNao.setFocusable(true);
            btnSim.setFocusable(true);

            // IMPORTANTE: Não definir botão padrão - força uso correto do foco
            // dialog.getRootPane().setDefaultButton(null);

            // Configurar teclas
            btnSim.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "confirmar");
            btnSim.getActionMap().put("confirmar", new AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    confirmado[0] = true;
                    dialog.dispose();
                }
            });

            btnNao.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "cancelar");
            btnNao.getActionMap().put("cancelar", new AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    confirmado[0] = false;
                    dialog.dispose();
                }
            });

            // ESC para cancelar
            dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
            dialog.getRootPane().getActionMap().put("escape", new AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    confirmado[0] = false;
                    dialog.dispose();
                }
            });

            // Configurar diálogo
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setResizable(false);

            // Foco inicial no "Não" (mais seguro)
            SwingUtilities.invokeLater(() -> btnNao.requestFocusInWindow());

            // Mostrar diálogo (modal)
            dialog.setVisible(true);

            // Verificar resposta após o diálogo ser fechado
            if (confirmado[0]) {
                System.out.println("Usuário confirmou exclusão");
                String msg = mediator.excluirSeguradoPessoa(cpf);
                if (msg != null) {
                    JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Exclusão realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    resetarTelaParaEstadoInicial();
                }
            } else {
                System.out.println("Usuário cancelou exclusão");
            }
        });

        btnCancelar.addActionListener(e -> resetarTelaParaEstadoInicial());

        btnLimpar.addActionListener(e -> {
            limparCamposTextoParcial(); // Limpa campos editáveis
            if (txtCpf.isEditable()) {
                txtCpf.setValue(null); // Limpa CPF se estiver editável
                txtCpf.setText("");
            }
        });

        // Configurar atalhos de teclado
        configurarTeclasAtalho();

        // ESC para fechar a interface
        configurarEscParaFechar();

        resetarTelaParaEstadoInicial();
        pack(); // Ajusta o tamanho da janela
        setMinimumSize(new Dimension(480, getHeight())); // Garante largura mínima
        getRootPane().setDefaultButton(btnIncluirAlterar); // Enter para o botão Incluir
        setVisible(true);
    }

    private void configurarTeclasAtalho() {
        // Configurar Enter para todos os botões quando têm foco
        btnLimpar.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "limpar");
        btnLimpar.getActionMap().put("limpar", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                limparCamposTextoParcial();
                limparCamposTextoCompleto();
                limparCamposEndereco();
            }
        });

        btnNovo.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "novo");
        btnNovo.getActionMap().put("novo", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnNovo.doClick();
            }
        });

        btnBuscar.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "buscar");
        btnBuscar.getActionMap().put("buscar", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnBuscar.doClick();
            }
        });

        btnExcluir.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "excluir");
        btnExcluir.getActionMap().put("excluir", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnExcluir.doClick();
            }
        });

        btnCancelar.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "cancelar");
        btnCancelar.getActionMap().put("cancelar", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                btnCancelar.doClick();
            }
        });
    }

    private void configurarEscParaFechar() {
        // ESC para fechar a interface
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "fechar_interface");
        getRootPane().getActionMap().put("fechar_interface", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Fechar a janela
                setVisible(false);
                dispose();
            }
        });
    }

    private void habilitarCamposEdicao(boolean habilitar) {
        // Controlar tanto edição quanto foco para melhor navegação por TAB
        txtNome.setEditable(habilitar);
        txtNome.setFocusable(habilitar);

        txtDataNascimento.setEditable(habilitar);
        txtDataNascimento.setFocusable(habilitar);

        txtRenda.setEditable(habilitar);
        txtRenda.setFocusable(habilitar);

        txtBonus.setEditable(habilitar);
        txtBonus.setFocusable(habilitar);

        txtLogradouro.setEditable(habilitar);
        txtLogradouro.setFocusable(habilitar);

        txtCep.setEditable(habilitar);
        txtCep.setFocusable(habilitar);

        txtNumeroEndereco.setEditable(habilitar);
        txtNumeroEndereco.setFocusable(habilitar);

        txtComplemento.setEditable(habilitar);
        txtComplemento.setFocusable(habilitar);

        txtCidade.setEditable(habilitar);
        txtCidade.setFocusable(habilitar);

        txtEstado.setEditable(habilitar);
        txtEstado.setFocusable(habilitar);

        txtPais.setEditable(habilitar);
        txtPais.setFocusable(habilitar);

        btnIncluirAlterar.setEnabled(habilitar);
        btnIncluirAlterar.setFocusable(habilitar);

        btnCancelar.setEnabled(habilitar);
        btnCancelar.setFocusable(habilitar);

        // Nota: btnExcluir é controlado separadamente nos listeners dos botões
    }

    private void limparCamposTextoParcial() {
        if (txtNome.isEditable()) txtNome.setText("");
        if (txtDataNascimento.isEditable()) {txtDataNascimento.setValue(null); txtDataNascimento.setText("");}
        if (txtRenda.isEditable()) txtRenda.setValue(0.00);
        if (txtBonus.isEditable()) txtBonus.setValue(0.00);
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
        txtDataNascimento.setValue(null); txtDataNascimento.setText("");
        txtRenda.setValue(0.00);
        txtBonus.setValue(0.00);
        txtLogradouro.setText("");
        txtCep.setValue(null); txtCep.setText("");
        txtNumeroEndereco.setText("");
        txtComplemento.setText("");
        txtCidade.setText("");
        txtEstado.setText("");
        txtPais.setText("");
    }

    private void preencherCamposComDados(SeguradoPessoa seg) {
        txtCpf.setText(seg.getCpf()); // JFormattedTextField tentará formatar
        txtNome.setText(seg.getNome());
        txtDataNascimento.setText(seg.getDataNascimento() != null ? seg.getDataNascimento().format(DATE_FORMATTER) : null);
        txtRenda.setValue(seg.getRenda());
        txtBonus.setValue(seg.getBonus() != null ? seg.getBonus() : BigDecimal.ZERO);

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
        txtCpf.setValue(null); txtCpf.setText("");
        txtCpf.setEditable(true);
        txtCpf.setFocusable(true);

        habilitarCamposEdicao(false);

        btnNovo.setEnabled(true);
        btnNovo.setFocusable(true);

        btnBuscar.setEnabled(true);
        btnBuscar.setFocusable(true);

        btnExcluir.setEnabled(false);
        btnExcluir.setFocusable(false);

        btnLimpar.setEnabled(true);
        btnLimpar.setFocusable(true);

        btnIncluirAlterar.setText("Incluir");

        txtCpf.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaCrudSeguradoPessoa::new);
    }
}