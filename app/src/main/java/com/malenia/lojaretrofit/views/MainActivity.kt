package com.malenia.lojaretrofit.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.malenia.lojaretrofit.R
import com.malenia.lojaretrofit.databinding.ActivityMainBinding
import com.malenia.lojaretrofit.databinding.ItemProdutoBinding
import com.malenia.lojaretrofit.model.Produto
import com.malenia.lojaretrofit.service.ProdutoService
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var  binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chamarApiListProduto()

    }

    fun chamarApiListProduto(){ //metodo para chamar o List Produto

        //1 - Criar uma instância do RetroFit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://oficinacordova.azurewebsites.net/")//isso aqui é o link inicial do endpoint(urlbase)
            .addConverterFactory(GsonConverterFactory.create()) //conversor de json do google
            .build()

        //2 - Criar uma instância do Serviço
        val service = retrofit.create(ProdutoService::class.java) //instancia do serviço ProdutoService

        //3 - Criar uma chamada
        val chamada = service.listar() // a chamada foi criada para uso futuro

        //4 - Definir um callback de retorno(parte mais dificil)
        val callback = object : Callback<List<Produto>>{ //implementação de uma classe anonima
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
             //esse é ativado quando teve uma resposta do Endpoint
               if(response.isSuccessful){
                val listaProduto = response.body()// agora o body do json está no listaProduto

                //val nomeProduto = listaProduto?.first()?.nomeProduto    //esse aqui pega o nome do primeiro produto

                  // alert("sucesso", "Nome do Primeiro Produto: $nomeProduto")
                   mostrarProdutos(listaProduto)
               }

                else{
                    alert("Erro", response.code().toString()) //manda o codigo para saber qual o erro
               }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
            //esse é ativado quando não teve resposta do Endpoint, então deve ter falha no codigo/internet
                alert("erro", t.message.toString())
            }

        }

        //5 - Executar a chamada
        chamada.enqueue(callback) //colocar na fila

    }


    //Função dos elementos dinamicos
    fun mostrarProdutos(listaProdutos: List<Produto>?){

        // 0 - Percorrer por cada produto

        listaProdutos?.forEach{
            //1 -  Inflar o Layout do item da lista

            val itemProduto = ItemProdutoBinding.inflate(layoutInflater)


            // 2 - Configurar as Views(componentes) com os dados do backend
            itemProduto.textNome.text = it.nomeProduto

            itemProduto.textPreco.text = it.precProduto.toString()

            // 2.5 - obter imagem do item
            Picasso.get().load("https://oficinacordova.azurewebsites.net/android/rest/produto/image/${it.idProduto}").into(itemProduto.imageView);

            //3 - adicionar o layout no container

            binding.container.addView(itemProduto.root)
        }


    }
    fun alert(titulo: String, msg:String){ //alert na tela
        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(msg)
            .setPositiveButton("ok",null)
            .create()
            .show()
    }
}