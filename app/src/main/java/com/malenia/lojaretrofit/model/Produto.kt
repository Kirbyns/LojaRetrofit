package com.malenia.lojaretrofit.model

// é necessario criar uma data Class sempre que for o modelo de um recebido do Json
// nesse caso é produto
//é uma classe de negocio.

data class Produto(
    //é necessario tipar cada tipo da variavel
 var precProduto:   Float,
 var ativoProduto:  Boolean,
 var qtdMinEstoque: Int,
 var nomeProduto:   String,
 var idProduto:     Int,
 var idCategoria:   Int,
 var descProduto: String,
 var descontoPromocao: Float

)

