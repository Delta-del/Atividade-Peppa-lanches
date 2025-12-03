package steps;

import io.cucumber.java.pt.*;
import org.junit.jupiter.api.Assertions;
import peppa.hamburgueria.CardapioService;
import peppa.hamburgueria.PedidoService;

public class PedidoSteps {

    private CardapioService cardapio;
    private PedidoService pedidoService;

    private String item;
    private int quantidade;
    private double totalCalculado;
    private Exception erro;
    private int tempoEstimado;

    @Dado("que o cardápio contém os itens:")
    public void que_o_cardapio_contem_os_itens(io.cucumber.datatable.DataTable dataTable) {
        cardapio = new CardapioService();
        dataTable.asMaps(String.class, String.class)
                .forEach(linha -> cardapio.cadastrarItem(
                        linha.get("item"),
                        Double.parseDouble(linha.get("preco"))
                ));
        pedidoService = new PedidoService(cardapio);
    }

    @Dado("que quero pedir o item {string}")
    public void que_quero_pedir_o_item(String item) {
        this.item = item;
    }

    @Dado("a quantidade informada é {int}")
    public void a_quantidade_informada_e(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Quando("calcular o total do pedido")
    public void calcular_o_total_do_pedido() {
        try {
            totalCalculado = pedidoService.calcularTotal(item, quantidade);
        } catch (Exception e) {
            erro = e;
        }
    }

    @Então("o valor total deve ser {double}")
    public void o_valor_total_deve_ser(Double esperado) {
        Assertions.assertEquals(esperado, totalCalculado);
    }

    @Então("deve ocorrer o erro {string}")
    public void deve_ocorrer_o_erro(String mensagem) {
        Assertions.assertNotNull(erro);
        Assertions.assertEquals(mensagem, erro.getMessage());
    }

    @Quando("calcular o total com desconto de {int} por cento")
    public void calcular_total_com_desconto(Integer desconto) {
        try {
            double total = pedidoService.calcularTotal(item, quantidade);
            double totalComDesconto = total - (total * (desconto / 100.0));
            totalCalculado = Math.round(totalComDesconto * 100.0) / 100.0;
        } catch (Exception e) {
            erro = e;
        }
    }

    @Dado("que o número total de itens do pedido é {int}")
    public void que_o_numero_total_de_itens_e(Integer total) {
        this.quantidade = total;
    }

    @Quando("calcular o tempo estimado")
    public void calcular_tempo_estimado() {
        tempoEstimado = pedidoService.calcularTempoEstimado(quantidade);
    }

    @Então("o tempo deve ser {int}")
    public void o_tempo_deve_ser(Integer esperado) {
        Assertions.assertEquals(esperado, tempoEstimado);
    }
}
