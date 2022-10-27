package com.malenia.lojaretrofit.service

import com.malenia.lojaretrofit.model.Produto
import retrofit2.Call
import retrofit2.http.GET


//para os serviços sempre usar a class de tipo Interface

interface ProdutoService {

    //método de listagem

    @GET("/android/rest/produto") //anotação do  metodo + endpoint
    fun listar():Call<List<Produto>>  // Sempre selecionar o o call do retrofit2 para array colocar <list>
}