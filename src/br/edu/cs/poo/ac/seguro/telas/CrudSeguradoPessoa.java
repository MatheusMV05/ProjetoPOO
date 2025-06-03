package br.edu.cs.poo.ac.seguro.telas;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Ajuste os imports conforme a estrutura de pacotes do seu projeto final.
// Exemplo: import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoPessoaMediator;


public class CrudSeguradoPessoa extends JFrame {

    private SeguradoPessoaMediator mediator;

    private JTextField txtCpf;
    private JTextField txtNome;
    private JTextField txtRenda;
    private JTextField txtDataNascimento; // YYYY-MM-DD
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

    public CrudSeguradoPessoa() {
        mediator = SeguradoPessoaMediator.getInstancia();
        setTitle("CRUD de Segurado Pessoa");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 500); // Ajustado para melhor visualização dos campos
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;


        // Linha 0: CPF, Novo, Buscar
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0; add(new JLabel("CPF:"), gbc);
        txtCpf = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7; gbc.gridwidth = 2; add(txtCpf, gbc);

        btnNovo = new JButton("Novo");
        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 0.15; gbc.gridwidth = 1; add(btnNovo, gbc);

        btnBuscar = new JButton("Buscar");
        gbc.gridx = 4; gbc.gridy = 0; gbc.weightx = 0.15; add(btnBuscar, gbc);

        // Linha 1: Nome
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0; add(new JLabel("Nome:"), gbc);
        txtNome = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; gbc.gridwidth = 4; add(txtNome, gbc);

        // Linha 2: Renda, Data Nascimento
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0; gbc.gridwidth = 1; add(new JLabel("Renda:"), gbc);
        txtRenda = new JTextField();
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.3; add(txtRenda, gbc);

        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0.0; add(new JLabel("Data Nasc. (YYYY-MM-DD):"), gbc);
        txtDataNascimento = new JTextField();
        gbc.gridx = 3; gbc.gridy = 2; gbc.weightx = 0.7; gbc.gridwidth = 2; add(txtDataNascimento, gbc);

        // Linha 3: Bônus
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0; gbc.gridwidth = 1; add(new JLabel("Bônus:"), gbc);
        txtBonus = new JTextField();
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0.3; add(txtBonus, gbc);


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
        pack(); // Ajusta o tamanho da janela aos componentes
        setSize(getWidth() + 50, getHeight()); // Adiciona um pouco de largura extra
        setLocationRelativeTo(null);
    }

    private void setAllFieldsEditable(boolean editable) {
        txtNome.setEditable(editable);
        txtRenda.setEditable(editable);
        txtDataNascimento.setEditable(editable);
        // txtBonus.setEditable(editable); // Bônus geralmente não é diretamente editável
        txtLogradouro.setEditable(editable);
        txtCep.setEditable(editable);
        txtNumeroEndereco.setEditable(editable);
        txtComplemento.setEditable(editable);
        txtPais.setEditable(editable);
        txtEstado.setEditable(editable);
        txtCidade.setEditable(editable);
    }

    private void setAllFieldsEnabled(boolean enabled) {
        txtNome.setEnabled(enabled);
        txtRenda.setEnabled(enabled);
        txtDataNascimento.setEnabled(enabled);
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
        // Não limpa CPF se estiver desabilitado (em modo Novo/Busca)
        if (txtCpf.isEnabled()) {
            txtCpf.setText("");
        }
        txtNome.setText("");
        txtRenda.setText("");
        txtDataNascimento.setText("");
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
        clearFields(); // Limpa os campos primeiro
        txtCpf.setEnabled(true);
        txtCpf.setEditable(true);
        setAllFieldsEnabled(false); // Desabilita todos os campos de dados
        txtBonus.setEnabled(false); // Bônus sempre desabilitado para edição direta

        btnNovo.setEnabled(true);
        btnBuscar.setEnabled(true);

        btnIncluirAlterar.setEnabled(false);
        btnIncluirAlterar.setText("Incluir");
        btnExcluir.setEnabled(false);
        btnCancelar.setEnabled(false);
        // Limpar é sempre habilitado
    }

    private void setStateNovo() {
        // Não limpa o CPF aqui, pois ele foi usado para verificar a não existência
        txtCpf.setEnabled(false); // Desabilita CPF pois está em modo "Novo" com este CPF
        txtCpf.setEditable(false);

        setAllFieldsEnabled(true); // Habilita campos para entrada de dados
        txtBonus.setEnabled(false); // Bônus não é editável na inclusão
        txtBonus.setText("0.00"); // Default para novo
        txtNome.requestFocusInWindow(); // Foco no nome

        // Limpa os campos de dados (exceto CPF que já está preenchido e bloqueado)
        txtNome.setText("");
        txtRenda.setText("");
        txtDataNascimento.setText("");
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

    private void setStateBuscar(SeguradoPessoa segurado) {
        txtCpf.setEnabled(false); // CPF não é alterável após busca
        txtCpf.setEditable(false);

        txtNome.setText(segurado.getNome());
        txtRenda.setText(String.valueOf(segurado.getRenda()));
        txtDataNascimento.setText(segurado.getDataNascimento() != null ? segurado.getDataNascimento().format(DateTimeFormatter.ISO_DATE) : "");
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

        setAllFieldsEnabled(true); // Habilita campos para edição
        txtBonus.setEnabled(false); // Bônus não é diretamente editável

        btnNovo.setEnabled(false);
        btnBuscar.setEnabled(false);

        btnIncluirAlterar.setEnabled(true);
        btnIncluirAlterar.setText("Alterar");
        btnExcluir.setEnabled(true);
        btnCancelar.setEnabled(true);
    }

    private SeguradoPessoa getSeguradoFromFields() {
        String cpf = txtCpf.getText().trim(); // CPF é pego do campo, que estará desabilitado mas com valor
        String nome = txtNome.getText().trim();
        double renda;
        LocalDate dataNascimento = null;
        BigDecimal bonus;

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome deve ser preenchido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            renda = Double.parseDouble(txtRenda.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Renda deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            if (!txtDataNascimento.getText().trim().isEmpty()) {
                dataNascimento = LocalDate.parse(txtDataNascimento.getText().trim(), DateTimeFormatter.ISO_DATE);
            } else {
                JOptionPane.showMessageDialog(this, "Data de Nascimento deve ser preenchida.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data de Nascimento deve estar no formato YYYY-MM-DD.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            // Bonus é pego do campo, mas ele deve estar desabilitado para edição direta.
            // Em um cenário real, o bônus seria recalculado ou gerenciado pelo sistema.
            // Para este CRUD, vamos assumir que o valor no campo (seja default ou carregado) é usado.
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
        // Adicionar validações para endereço se necessário, conforme SeguradoMediator
        if (endereco.getLogradouro().isEmpty() || endereco.getCep().isEmpty() || endereco.getCidade().isEmpty() || endereco.getEstado().isEmpty() || endereco.getPais().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos de endereço obrigatórios (Logradouro, CEP, Cidade, Estado, País) devem ser preenchidos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return null;
        }


        return new SeguradoPessoa(nome, endereco, dataNascimento, bonus, cpf, renda);
    }


    private void addListeners() {
        btnNovo.addActionListener(e -> {
            String cpf = txtCpf.getText().trim();
            if (cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CPF deve ser preenchido para 'Novo'.", "Aviso", JOptionPane.WARNING_MESSAGE);
                txtCpf.requestFocusInWindow();
                return;
            }
            // Validação básica do formato do CPF antes de chamar o mediator
            if (cpf.length() != 11 || !cpf.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "CPF deve conter 11 dígitos numéricos.", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SeguradoPessoa seg = mediator.buscarSeguradoPessoa(cpf);
            if (seg != null) {
                JOptionPane.showMessageDialog(this, "Segurado Pessoa já existente com este CPF!", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                setStateNovo(); // Prepara a tela para nova entrada, mantendo o CPF digitado
            }
        });

        btnBuscar.addActionListener(e -> {
            String cpf = txtCpf.getText().trim();
            if (cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CPF deve ser preenchido para 'Buscar'.", "Aviso", JOptionPane.WARNING_MESSAGE);
                txtCpf.requestFocusInWindow();
                return;
            }
            if (cpf.length() != 11 || !cpf.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "CPF deve conter 11 dígitos numéricos.", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }
            SeguradoPessoa seg = mediator.buscarSeguradoPessoa(cpf);
            if (seg == null) {
                JOptionPane.showMessageDialog(this, "Segurado Pessoa não existente!", "Aviso", JOptionPane.WARNING_MESSAGE);
                // Opcional: Limpar campos de dados se a busca falhar e o usuário quiser tentar um novo CPF
                clearFields(); // Limpa tudo
                txtCpf.setText(cpf); // Restaura o CPF que foi buscado e falhou
                txtCpf.requestFocusInWindow();
            } else {
                setStateBuscar(seg);
            }
        });

        btnIncluirAlterar.addActionListener(e -> {
            SeguradoPessoa segurado = getSeguradoFromFields();
            if (segurado == null) return;

            String msg;
            String msgOk;

            if (btnIncluirAlterar.getText().equals("Incluir")) {
                msg = mediator.incluirSeguradoPessoa(segurado);
                msgOk = "Inclusão realizada com sucesso!";
            } else {
                msg = mediator.alterarSeguradoPessoa(segurado);
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
            String cpf = txtCpf.getText().trim(); // CPF estará desabilitado, mas com valor
            if (cpf.isEmpty()){
                JOptionPane.showMessageDialog(this, "Nenhum segurado carregado para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int response = JOptionPane.showConfirmDialog(this, "Confirma a exclusão do segurado com CPF: " + cpf + "?",
                    "Excluir Segurado Pessoa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                String msg = mediator.excluirSeguradoPessoa(cpf);
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
            // Limpa campos de dados; se CPF estiver habilitado (estado inicial), limpa também
            if (txtCpf.isEnabled()) {
                txtCpf.setText("");
            }
            txtNome.setText("");
            txtRenda.setText("");
            txtDataNascimento.setText("");
            txtBonus.setText(""); // Bonus é setado para 0.00 no estado Novo ou carregado na Busca
            txtLogradouro.setText("");
            txtCep.setText("");
            txtNumeroEndereco.setText("");
            txtComplemento.setText("");
            txtPais.setText("");
            txtEstado.setText("");
            txtCidade.setText("");

            // Se não estiver no estado inicial, o Limpar deve apenas limpar os campos de dados,
            // não necessariamente resetar o estado dos botões ou do campo CPF.
            // A lógica de "limpar" do exemplo original parece limpar os campos de dados.
            // Se estivermos em modo "Novo" ou "Alterar", o CPF estará bloqueado.
            // O botão Cancelar é quem realmente volta ao estado inicial.
            if (txtNome.isEnabled()) { // Se os campos de dados estão habilitados
                txtNome.requestFocusInWindow();
            } else {
                txtCpf.requestFocusInWindow();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Define o Look and Feel para o padrão do sistema operacional, se desejado
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CrudSeguradoPessoa().setVisible(true);
        });
    }
}