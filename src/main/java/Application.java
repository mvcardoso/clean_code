import br.com.acme.enums.TipoAssinatura;
import br.com.acme.model.AssinaturaModel;
import br.com.acme.model.ClienteModel;
import br.com.acme.model.PagamentoModel;
import br.com.acme.model.ProdutoModel;
import br.com.acme.service.AssinaturaService;
import br.com.acme.service.PagamentoService;
import br.com.acme.service.ProdutoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.Month.*;

public class Application {

    public static void main(String[] args) {

        // 1 Crie uma Classe com um método main para criar alguns produtos, clientes e pagamentos. [OK]
        // Crie Pagamentos com:  a data de hoje, ontem e um do mês passado.
        ClienteModel breno = new ClienteModel("Breno");
        ClienteModel joao = new ClienteModel("Joao");
        ClienteModel maria = new ClienteModel("Maria");

        ProdutoModel prod1 = new ProdutoModel("Apologize", null, new BigDecimal("98.90"));
        ProdutoModel prod2 = new ProdutoModel("Te levar daqui", null, new BigDecimal("50.00"));
        ProdutoModel prod3 = new ProdutoModel("Ana Julia", null, new BigDecimal("25.30"));

        PagamentoModel pag1 = new PagamentoModel(List.of(prod1, prod2), LocalDate.now(), breno);
        PagamentoModel pag2 = new PagamentoModel(List.of(prod1, prod2, prod3), LocalDate.now().minusDays(1), joao);
        PagamentoModel pag3 = new PagamentoModel(List.of(prod1), LocalDate.now().minusMonths(1), maria);

        List<PagamentoModel> pagamentos = new ArrayList<>(List.of(pag1, pag2, pag3));

        // 2 - Ordene e imprima os pagamentos pela data de compra. [OK]
        System.out.println("--------------------------#2--------------------------");
        PagamentoService pagamentoService = new PagamentoService();
        pagamentoService.ordernarPelaDataDaCompra(pagamentos);

        // 3 - Calcule e Imprima a soma dos valores de um pagamento com optional e recebendo um Double diretamente. [OK]
        System.out.println("--------------------------#3--------------------------");
        pagamentoService.calcularSomaPagamento(pag2);


        // 4 -  Calcule o Valor de todos os pagamentos da Lista de pagamentos. [OK]
        System.out.println("--------------------------#4--------------------------");
        pagamentoService.calcularPagamentos(pagamentos);

        // 5 - Imprima a quantidade de cada Produto vendido [OK]
        System.out.println("--------------------------#5--------------------------");
        ProdutoService produtoService = new ProdutoService();
        produtoService.getQuantidadeProdutosVendidos(pagamentos);

        // 6 - Crie um Mapa de <Cliente, List<Produto> , onde Cliente pode ser o nome do cliente. [OK]
        pagamentoService.criarMapaClienteProduto(pagamentos);


        // 7 - Qual cliente gastou mais? [OK]
        System.out.println("--------------------------#7--------------------------");
        pagamentoService.calcularMaiorPagamentoPorCliente(pagamentos);


        // 8 - Quanto foi faturado em um determinado mês? [OK]
        System.out.println("--------------------------#8--------------------------");
        pagamentoService.calcularFaturamentoPorMes(pagamentos);

        // 9 - Crie 3 assinaturas com assinaturas de 99.98 reais, sendo 2 deles com assinaturas encerradas. [OK]
        AssinaturaModel assinaturaEmAberto = new AssinaturaModel(new BigDecimal("99.98"), LocalDate.of(2023, JANUARY, 10), breno, TipoAssinatura.ANUAL);
        AssinaturaModel assinaturaEncerrada1 = new AssinaturaModel(new BigDecimal("99.98"), LocalDate.of(2022, APRIL, 23), LocalDate.of(2022, DECEMBER, 30), joao, TipoAssinatura.TRIMESTRAL);
        AssinaturaModel assinaturaEncerrada2 = new AssinaturaModel(new BigDecimal("99.98"), LocalDate.of(2020, OCTOBER, 15), LocalDate.of(2023, MAY, 5), maria, TipoAssinatura.SEMESTRAL);

        // 10 - Imprima o tempo em meses de alguma assinatura ainda ativa. [OK]
        System.out.println("--------------------------#10--------------------------");
        AssinaturaService assinaturaService = new AssinaturaService();
        assinaturaService.calcularMesesDeAssinatura(assinaturaEmAberto);

        System.out.println("--------------------------#11--------------------------");
        List<AssinaturaModel> assinaturas = new ArrayList<>(List.of(assinaturaEmAberto, assinaturaEncerrada1, assinaturaEncerrada2));
        assinaturaService.calcularPeriodoAssinaturas(assinaturas);


        // 12 - Calcule o valor pago em cada assinatura até o momento. [OK]
        System.out.println("--------------------------#12--------------------------");
        assinaturaService.calcularValorPagoPorAssinatura(assinaturas);

        // Crie um método para calcular uma taxa para cada assinatura.
        System.out.println("----------------------------------------------------");
        System.out.println("Valor em R$: " + assinaturaService.calcularTaxaAssinatura(assinaturaEmAberto));

    }
}
