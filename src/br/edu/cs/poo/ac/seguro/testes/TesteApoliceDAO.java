package br.edu.cs.poo.ac.seguro.testes;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class TesteApoliceDAO extends TesteDAO {
    private ApoliceDAO dao = new ApoliceDAO();

    protected Class getClasse() {
        return Apolice.class;
    }

    @Test
    public void teste01() {
        String numero = "AP00000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE1", null, LocalDate.now(), BigDecimal.ZERO, "12345678901", 1000.0);
        Veiculo veiculo = new Veiculo("ABC1234", 2023, segurado, CategoriaVeiculo.BASICO);
        Apolice apolice = new Apolice(numero, veiculo, new BigDecimal("1800.00"),
                new BigDecimal("2340.00"), new BigDecimal("60000.00"), LocalDate.now());
        cadastro.incluir(apolice, numero);
        Apolice ap = dao.buscar(numero);
        Assertions.assertNotNull(ap);
    }

    @Test
    public void teste02() {
        String numero = "AP10000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE2", null, LocalDate.now(), BigDecimal.ZERO, "12345678902", 1001.0);
        Veiculo veiculo = new Veiculo("ABC1235", 2023, segurado, CategoriaVeiculo.BASICO);
        Apolice apolice = new Apolice(numero, veiculo, new BigDecimal("1801.00"),
                new BigDecimal("2341.00"), new BigDecimal("60001.00"), LocalDate.now());
        cadastro.incluir(apolice, numero);
        Apolice ap = dao.buscar("AP11000001");
        Assertions.assertNull(ap);
    }

    @Test
    public void teste03() {
        String numero = "AP22000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE3", null, LocalDate.now(), BigDecimal.ZERO, "12345678903", 1002.0);
        Veiculo veiculo = new Veiculo("ABC1236", 2023, segurado, CategoriaVeiculo.BASICO);
        Apolice apolice = new Apolice(numero, veiculo, new BigDecimal("1802.00"),
                new BigDecimal("2342.00"), new BigDecimal("60002.00"), LocalDate.now());
        cadastro.incluir(apolice, numero);
        boolean ret = dao.excluir(numero);
        Assertions.assertTrue(ret);
    }

    @Test
    public void teste04() {
        String numero = "AP33000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE4", null, LocalDate.now(), BigDecimal.ZERO, "12345678904", 1003.0);
        Veiculo veiculo = new Veiculo("ABC1237", 2023, segurado, CategoriaVeiculo.BASICO);
        Apolice apolice = new Apolice(numero, veiculo, new BigDecimal("1803.00"),
                new BigDecimal("2343.00"), new BigDecimal("60003.00"), LocalDate.now());
        cadastro.incluir(apolice, numero);
        boolean ret = dao.excluir("AP33100001");
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste05() {
        String numero = "AP44000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE5", null, LocalDate.now(), BigDecimal.ZERO, "12345678905", 1004.0);
        Veiculo veiculo = new Veiculo("ABC1238", 2023, segurado, CategoriaVeiculo.BASICO);
        Apolice apolice = new Apolice(numero, veiculo, new BigDecimal("1804.00"),
                new BigDecimal("2344.00"), new BigDecimal("60004.00"), LocalDate.now());
        boolean ret = dao.incluir(apolice);
        Assertions.assertTrue(ret);
        Apolice ap = dao.buscar(numero);
        Assertions.assertNotNull(ap);
    }

    @Test
    public void teste06() {
        String numero = "AP55000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE6", null, LocalDate.now(), BigDecimal.ZERO, "12345678906", 1005.0);
        Veiculo veiculo = new Veiculo("ABC1239", 2023, segurado, CategoriaVeiculo.BASICO);
        Apolice apolice = new Apolice(numero, veiculo, new BigDecimal("1805.00"),
                new BigDecimal("2345.00"), new BigDecimal("60005.00"), LocalDate.now());
        cadastro.incluir(apolice, numero);
        boolean ret = dao.incluir(apolice);
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste07() {
        String numero = "AP66000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE7", null, LocalDate.now(), BigDecimal.ZERO, "12345678907", 1006.0);
        Veiculo veiculo = new Veiculo("ABC1240", 2023, segurado, CategoriaVeiculo.BASICO);
        Apolice apolice = new Apolice(numero, veiculo, new BigDecimal("1806.00"),
                new BigDecimal("2346.00"), new BigDecimal("60006.00"), LocalDate.now());
        boolean ret = dao.alterar(apolice);
        Assertions.assertFalse(ret);
        Apolice ap = dao.buscar(numero);
        Assertions.assertNull(ap);
    }

    @Test
    public void teste08() {
        String numero = "AP77000001";
        SeguradoPessoa segurado = new SeguradoPessoa("TESTE8", null, LocalDate.now(), BigDecimal.ZERO, "12345678908", 1007.0);
        Veiculo veiculo = new Veiculo("ABC1241", 2023, segurado, CategoriaVeiculo.BASICO);
        Apolice apolice = new Apolice(numero, veiculo, new BigDecimal("1807.00"),
                new BigDecimal("2347.00"), new BigDecimal("60007.00"), LocalDate.now());
        cadastro.incluir(apolice, numero);

        // Criar uma nova apólice com mesmo número mas dados diferentes
        Apolice apoliceNova = new Apolice(numero, veiculo, new BigDecimal("1808.00"),
                new BigDecimal("2348.00"), new BigDecimal("60008.00"), LocalDate.now());
        boolean ret = dao.alterar(apoliceNova);
        Assertions.assertTrue(ret);
    }
}