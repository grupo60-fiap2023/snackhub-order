# language: pt
Funcionalidade: Order API

  @smoke
  Cenário: Criar um novo pedido
    Quando submeter uma requisição de pedido
    Então o pedido é registrado com sucesso

  Cenário: Listar pedidos existente
    Dado que um pedido já foi registrado
    Quando requisitar a lista de pedidos
    Então os pedidos são exibidas com sucesso

  Cenário: Alterar um pedido existente
    Dado que um pedido já foi registrado
    Quando requisitar a alteração do pedido
    Então o pedido é atualizada com sucesso
