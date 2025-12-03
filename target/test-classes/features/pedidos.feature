# language: pt
@hamburgueria
Funcionalidade: Pedidos na hamburgueria Peppa Lanches
  Para realizar pedidos corretos
  Como cliente
  Eu quero saber se o item pode ser pedido, o valor total e o tempo estimado

  Contexto:
    Dado que o cardápio contém os itens:
      | item         | preco |
      | x-bacon      | 25.00 |
      | x-salada     | 22.00 |
      | batata frita | 12.00 |

  @feliz
  Cenário: Pedido simples de item existente
    Dado que quero pedir o item "x-bacon"
    E a quantidade informada é 2
    Quando calcular o total do pedido
    Então o valor total deve ser 50.00

  @inexistente
  Cenário: Pedido de item inexistente
    Dado que quero pedir o item "suco de laranja"
    E a quantidade informada é 1
    Quando calcular o total do pedido
    Então deve ocorrer o erro "Item indisponível no cardápio"

  @quantidade
  Cenário: Pedido com quantidade inválida
    Dado que quero pedir o item "x-salada"
    E a quantidade informada é 0
    Quando calcular o total do pedido
    Então deve ocorrer o erro "Quantidade inválida"

  @desconto
  Cenário: Pedido com desconto de 10 por cento
    Dado que quero pedir o item "batata frita"
    E a quantidade informada é 3
    Quando calcular o total com desconto de 10 por cento
    Então o valor total deve ser 32.40

  @sla
  Cenário: Calcular tempo estimado de preparo
    Dado que o número total de itens do pedido é 4
    Quando calcular o tempo estimado
    Então o tempo deve ser 16
