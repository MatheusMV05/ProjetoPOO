package br.edu.cs.poo.ac.seguro.telas;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// Ajuste os imports conforme a estrutura de pacotes do seu projeto final.
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.excecoes.ExcecaoValidacaoDados;
import br.edu.cs.poo.ac.seguro.mediators.DadosSinistro;
import br.edu.cs.poo.ac.seguro.mediators.SinistroMediator;

public class TelaInclusaoSinistro extends JFrame {

    private SinistroMediator mediator;

    private JTextField txtPlaca;
    private JTextField txtDataHoraSinistro; // YYYY-MM-DDTHH:MM:SS
    private JTextField txtUsuarioRegistro;
    private JTextField txtValorSinistro;
    private JComboBox<String> comboTipoSinistro;

    private JButton btnIncluir;
    private JButton btnLimpar;

    public TelaInclusaoSinistro() {
        mediator = SinistroMediator.getInstancia();
        setTitle("Inclusão de Sinistro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 300); // Ajustado
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Linha 0: Placa
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3; add(new JLabel("Placa do Veículo:"), gbc);
        txtPlaca = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7; add(txtPlaca, gbc);

        // Linha 1: Data/Hora Sinistro
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Data/Hora Sinistro (YYYY-MM-DDTHH:MM:SS):"), gbc);
        txtDataHoraSinistro = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 1; add(txtDataHoraSinistro, gbc);

        // Linha 2: Usuário Registro
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Usuário do Registro:"), gbc);
        txtUsuarioRegistro = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 2; add(txtUsuarioRegistro, gbc);

        // Linha 3: Valor Sinistro
        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Valor do Sinistro:"), gbc);
        txtValorSinistro = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = 3; add(txtValorSinistro, gbc);

        // Linha 4: Tipo Sinistro
        gbc.gridx = 0; gbc.gridy = 4; add(new JLabel("Tipo do Sinistro:"), gbc);
        comboTipoSinistro = new JComboBox<>();
        TipoSinistro[] tipos = TipoSinistro.values();
        Arrays.sort(tipos, Comparator.comparing(TipoSinistro::getNome)); // Ordena pelo nome
        for (TipoSinistro tipo : tipos) {
            comboTipoSinistro.addItem(tipo.getNome());
        }
        gbc.gridx = 1; gbc.gridy = 4; add(comboTipoSinistro, gbc);


        // Linha 5: Buttons Panel
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnIncluir = new JButton("Incluir Sinistro");
        btnLimpar = new JButton("Limpar Campos");

        panelBotoes.add(btnIncluir);
        panelBotoes.add(btnLimpar);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0; add(panelBotoes, gbc);

        addListeners();
        pack();
        setSize(getWidth() + 50, getHeight());
        setLocationRelativeTo(null);
    }

    private void clearFields() {
        txtPlaca.setText("");
        txtDataHoraSinistro.setText("");
        txtUsuarioRegistro.setText("");
        txtValorSinistro.setText("");
        if (comboTipoSinistro.getItemCount() > 0) {
            comboTipoSinistro.setSelectedIndex(0); //
        }
        txtPlaca.requestFocusInWindow();
    }

    private TipoSinistro getSelectedTipoSinistro() {
        String nomeSelecionado = (String) comboTipoSinistro.getSelectedItem();
        if (nomeSelecionado == null) return null;

        for (TipoSinistro tipo : TipoSinistro.values()) {
            if (tipo.getNome().equals(nomeSelecionado)) {
                return tipo;
            }
        }
        return null;
    }

    private void addListeners() {
        btnIncluir.addActionListener(e -> {
            String placa = txtPlaca.getText().trim();
            LocalDateTime dataHoraSinistro = null;
            String usuarioRegistro = txtUsuarioRegistro.getText().trim();
            double valorSinistro;
            TipoSinistro tipoSelecionado = getSelectedTipoSinistro();

            if (placa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Placa do veículo deve ser informada.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (txtDataHoraSinistro.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Data/Hora do sinistro deve ser informada.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (usuarioRegistro.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Usuário do registro deve ser informado.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (txtValorSinistro.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Valor do sinistro deve ser informado.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(tipoSelecionado == null){
                JOptionPane.showMessageDialog(this, "Selecione um tipo de sinistro válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int codigoTipoSinistro = tipoSelecionado.getCodigo();

            try {
                // Formato esperado: YYYY-MM-DDTHH:MM:SS
                // Se o usuário omitir os segundos, podemos tentar adicionar :00
                String dateTimeStr = txtDataHoraSinistro.getText().trim();
                if (dateTimeStr.length() == 16) { // YYYY-MM-DDTHH:MM
                    dateTimeStr += ":00";
                }
                dataHoraSinistro = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data/Hora do Sinistro deve estar no formato YYYY-MM-DDTHH:MM:SS.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                valorSinistro = Double.parseDouble(txtValorSinistro.getText().trim().replace(",","."));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valor do sinistro deve ser um número (ex: 1500.75).", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DadosSinistro dadosSinistro = new DadosSinistro(placa, dataHoraSinistro, usuarioRegistro, valorSinistro, codigoTipoSinistro);

            try {
                // A data/hora atual para registro do sinistro é pega dentro do mediator.
                String numeroSinistroGerado = mediator.incluirSinistro(dadosSinistro, LocalDateTime.now()); //
                JOptionPane.showMessageDialog(this, "Sinistro incluído com sucesso! Anote o número do sinistro: " + numeroSinistroGerado, "Sucesso", JOptionPane.INFORMATION_MESSAGE); //
                clearFields();
            } catch (ExcecaoValidacaoDados ex) {
                List<String> mensagens = ex.getMensagens(); //
                StringBuilder fullMessage = new StringBuilder("Erro(s) na inclusão do Sinistro:\n");
                for (String msg : mensagens) {
                    fullMessage.append("- ").append(msg).append("\n");
                }
                JOptionPane.showMessageDialog(this, fullMessage.toString(), "Erro de Validação", JOptionPane.ERROR_MESSAGE); //
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
            new TelaInclusaoSinistro().setVisible(true);
        });
    }
}