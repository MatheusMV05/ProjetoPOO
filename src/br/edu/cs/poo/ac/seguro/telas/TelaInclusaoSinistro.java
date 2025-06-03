package br.edu.cs.poo.ac.seguro.telas;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.excecoes.ExcecaoValidacaoDados;
import br.edu.cs.poo.ac.seguro.mediators.DadosSinistro;
import br.edu.cs.poo.ac.seguro.mediators.SinistroMediator;

public class TelaInclusaoSinistro extends JFrame {

    private SinistroMediator mediator;
    private JTextField txtPlaca;
    private JFormattedTextField txtDataHoraSinistro;
    private JTextField txtUsuarioRegistro;
    private JFormattedTextField txtValorSinistro;
    private JComboBox<String> cmbTipoSinistro;

    private JButton btnIncluir;
    private JButton btnLimpar;

    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

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

    public TelaInclusaoSinistro() {
        mediator = SinistroMediator.getInstancia();
        setTitle("Inclusão de Sinistro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // setSize(480, 280); // pack() no final pode ser melhor
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // gbc.weightx = 1.0;

        // Placa do Veículo
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Placa do Veículo:"), gbc);
        gbc.gridx = 1;
        txtPlaca = new JTextField(20);
        add(txtPlaca, gbc);

        // Data e Hora do Sinistro
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Data/Hora Sinistro (dd/MM/yyyy HH:mm):"), gbc);
        gbc.gridx = 1;
        txtDataHoraSinistro = new JFormattedTextField(createFormatter("##/##/#### ##:##"));
        txtDataHoraSinistro.setColumns(16);
        add(txtDataHoraSinistro, gbc);

        // Usuário do Registro
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Usuário do Registro:"), gbc);
        gbc.gridx = 1;
        txtUsuarioRegistro = new JTextField(20);
        add(txtUsuarioRegistro, gbc);

        // Valor do Sinistro
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Valor do Sinistro (R$):"), gbc);
        gbc.gridx = 1;
        NumberFormat valorFormat = NumberFormat.getNumberInstance(); // Ou getCurrencyInstance()
        valorFormat.setMinimumFractionDigits(2);
        valorFormat.setMaximumFractionDigits(2);
        txtValorSinistro = new JFormattedTextField(valorFormat);
        txtValorSinistro.setColumns(15);
        txtValorSinistro.setValue(0.00);
        add(txtValorSinistro, gbc);

        // Tipo de Sinistro
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Tipo de Sinistro:"), gbc);
        gbc.gridx = 1;
        cmbTipoSinistro = new JComboBox<>();
        popularComboTipoSinistro();
        add(cmbTipoSinistro, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnIncluir = new JButton("Incluir");
        btnLimpar = new JButton("Limpar");
        buttonPanel.add(btnIncluir);
        buttonPanel.add(btnLimpar);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        btnIncluir.addActionListener(e -> {
            try {
                String placa = txtPlaca.getText().trim();
                LocalDateTime dataHoraSinistroVal = LocalDateTime.parse(txtDataHoraSinistro.getText(), DATETIME_FORMATTER);
                String usuarioRegistro = txtUsuarioRegistro.getText().trim();

                double valorSinistroVal;
                if (txtValorSinistro.getValue() instanceof Number) {
                    valorSinistroVal = ((Number) txtValorSinistro.getValue()).doubleValue();
                } else {
                    throw new NumberFormatException("Valor do sinistro inválido.");
                }

                int selectedIndex = cmbTipoSinistro.getSelectedIndex();
                if (selectedIndex < 0) {
                    JOptionPane.showMessageDialog(this, "Selecione um tipo de sinistro.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String selectedTipoNome = (String) cmbTipoSinistro.getSelectedItem();
                TipoSinistro selectedTipo = null;
                for (TipoSinistro tipo : TipoSinistro.values()) {
                    if (tipo.getNome().equals(selectedTipoNome)) {
                        selectedTipo = tipo;
                        break;
                    }
                }
                if (selectedTipo == null) {
                    JOptionPane.showMessageDialog(this, "Tipo de sinistro inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int codigoTipoSinistro = selectedTipo.getCodigo();

                DadosSinistro dados = new DadosSinistro(placa, dataHoraSinistroVal, usuarioRegistro, valorSinistroVal, codigoTipoSinistro);
                String numeroSinistro = mediator.incluirSinistro(dados, LocalDateTime.now());

                JOptionPane.showMessageDialog(this, "Sinistro incluído com sucesso! Anote o número do sinistro: " + numeroSinistro, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();

            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data/Hora do Sinistro inválida. Use o formato dd/MM/yyyy HH:mm e preencha corretamente.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valor do Sinistro deve ser um número válido. " + ex.getMessage(), "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (ExcecaoValidacaoDados ex) {
                List<String> mensagensErro = ex.getMensagens();
                String errosConcatenados = mensagensErro.stream().collect(Collectors.joining("\n"));
                JOptionPane.showMessageDialog(this, "Erro ao incluir sinistro:\n" + errosConcatenados, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro inesperado ao incluir sinistro: " + ex.getMessage(), "Erro Inesperado", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnLimpar.addActionListener(e -> limparCampos());

        pack(); // Ajusta o tamanho da janela
        setMinimumSize(new Dimension(480, getHeight())); // Garante largura mínima
        setVisible(true);
    }

    private void popularComboTipoSinistro() {
        TipoSinistro[] tipos = TipoSinistro.values();
        String[] nomesTipos = new String[tipos.length];
        for (int i = 0; i < tipos.length; i++) {
            nomesTipos[i] = tipos[i].getNome();
        }
        Arrays.sort(nomesTipos, Comparator.naturalOrder());

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(nomesTipos);
        cmbTipoSinistro.setModel(model);

        if (cmbTipoSinistro.getItemCount() > 0) {
            cmbTipoSinistro.setSelectedIndex(0);
        }
    }

    private void limparCampos() {
        txtPlaca.setText("");
        txtDataHoraSinistro.setValue(null); txtDataHoraSinistro.setText("");
        txtUsuarioRegistro.setText("");
        txtValorSinistro.setValue(0.00);
        if (cmbTipoSinistro.getItemCount() > 0) {
            cmbTipoSinistro.setSelectedIndex(0);
        }
        txtPlaca.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaInclusaoSinistro::new);
    }
}