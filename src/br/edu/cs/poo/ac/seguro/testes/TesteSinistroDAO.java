package br.edu.cs.poo.ac.seguro.testes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class TesteSinistroDAO extends TesteDAO {
    private SinistroDAO dao = new SinistroDAO();

    protected Class getClasse() {
        return Sinistro.class;
    }

    @Test
    public void teste01() {
        String numero = "S00000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE1", null, null, BigDecimal.ZERO, "12345678901", 1000.0);
        Veiculo veiculo = new Veiculo("ABC1234", 2023, segurado, CategoriaVeiculo.BASICO);
        Sinistro sinistro = new Sinistro(numero, veiculo, LocalDateTime.now(),
                LocalDateTime.now(), "usuario1", new BigDecimal("5000.00"), TipoSinistro.COLISAO);
        cadastro.incluir(sinistro, numero);
        Sinistro sin = dao.buscar(numero);
        Assertions.assertNotNull(sin);
    }

    @Test
    public void teste02() {
        String numero = "S10000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE2", null, null, BigDecimal.ZERO, "12345678902", 1001.0);
        Veiculo veiculo = new Veiculo("ABC1235", 2023, segurado, CategoriaVeiculo.BASICO);
        Sinistro sinistro = new Sinistro(numero, veiculo, LocalDateTime.now(),
                LocalDateTime.now(), "usuario2", new BigDecimal("5001.00"), TipoSinistro.INCENDIO);
        cadastro.incluir(sinistro, numero);
        Sinistro sin = dao.buscar("S11000001");
        Assertions.assertNull(sin);
    }

    @Test
    public void teste03() {
        String numero = "S22000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE3", null, null, BigDecimal.ZERO, "12345678903", 1002.0);
        Veiculo veiculo = new Veiculo("ABC1236", 2023, segurado, CategoriaVeiculo.BASICO);
        Sinistro sinistro = new Sinistro(numero, veiculo, LocalDateTime.now(),
                LocalDateTime.now(), "usuario3", new BigDecimal("5002.00"), TipoSinistro.FURTO);
        cadastro.incluir(sinistro, numero);
        boolean ret = dao.excluir(numero);
        Assertions.assertTrue(ret);
    }

    @Test
    public void teste04() {
        String numero = "S33000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE4", null, null, BigDecimal.ZERO, "12345678904", 1003.0);
        Veiculo veiculo = new Veiculo("ABC1237", 2023, segurado, CategoriaVeiculo.BASICO);
        Sinistro sinistro = new Sinistro(numero, veiculo, LocalDateTime.now(),
                LocalDateTime.now(), "usuario4", new BigDecimal("5003.00"), TipoSinistro.ENCHENTE);
        cadastro.incluir(sinistro, numero);
        boolean ret = dao.excluir("S33100001");
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste05() {
        String numero = "S44000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE5", null, null, BigDecimal.ZERO, "12345678905", 1004.0);
        Veiculo veiculo = new Veiculo("ABC1238", 2023, segurado, CategoriaVeiculo.BASICO);
        Sinistro sinistro = new Sinistro(numero, veiculo, LocalDateTime.now(),
                LocalDateTime.now(), "usuario5", new BigDecimal("5004.00"), TipoSinistro.DEPREDACAO);
        boolean ret = dao.incluir(sinistro);
        Assertions.assertTrue(ret);
        Sinistro sin = dao.buscar(numero);
        Assertions.assertNotNull(sin);
    }

    @Test
    public void teste06() {
        String numero = "S55000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE6", null, null, BigDecimal.ZERO, "12345678906", 1005.0);
        Veiculo veiculo = new Veiculo("ABC1239", 2023, segurado, CategoriaVeiculo.BASICO);
        Sinistro sinistro = new Sinistro(numero, veiculo, LocalDateTime.now(),
                LocalDateTime.now(), "usuario6", new BigDecimal("5005.00"), TipoSinistro.COLISAO);
        cadastro.incluir(sinistro, numero);
        boolean ret = dao.incluir(sinistro);
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste07() {
        String numero = "S66000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE7", null, null, BigDecimal.ZERO, "12345678907", 1006.0);
        Veiculo veiculo = new Veiculo("ABC1240", 2023, segurado, CategoriaVeiculo.BASICO);
        Sinistro sinistro = new Sinistro(numero, veiculo, LocalDateTime.now(),
                LocalDateTime.now(), "usuario7", new BigDecimal("5006.00"), TipoSinistro.INCENDIO);
        boolean ret = dao.alterar(sinistro);
        Assertions.assertFalse(ret);
        Sinistro sin = dao.buscar(numero);
        Assertions.assertNull(sin);
    }

    @Test
    public void teste08() {
        String numero = "S77000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE8", null, null, BigDecimal.ZERO, "12345678908", 1007.0);
        Veiculo veiculo = new Veiculo("ABC1241", 2023, segurado, CategoriaVeiculo.BASICO);
        Sinistro sinistro = new Sinistro(numero, veiculo, LocalDateTime.now(),
                LocalDateTime.now(), "usuario8", new BigDecimal("5007.00"), TipoSinistro.FURTO);
        cadastro.incluir(sinistro, numero);

        // Criar um novo sinistro com mesmo número mas dados diferentes
        Sinistro sinistroNovo = new Sinistro(numero, veiculo, LocalDateTime.now(),
                LocalDateTime.now(), "usuario8_alterado", new BigDecimal("5008.00"), TipoSinistro.ENCHENTE);
        boolean ret = dao.alterar(sinistroNovo);
        Assertions.assertTrue(ret);
    }
}