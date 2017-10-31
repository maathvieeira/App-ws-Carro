package br.com.matheusvieira.appwebservicecarros;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by android on 09/10/2017.
 */

public interface CarroService {

    //define  assinatura do método que retorna uma lista de dados
    @GET("lista_carros.php")
    Call<List<Carro>> getCarros();

    //define assinatura do método que envia  os dados para ws
    @FormUrlEncoded
    @POST("insere_proposta.php")
    Call<Proposta> enviaProposta(@Field("cliente") String cliente,
                                 @Field("email") String email,
                                 @Field("proposta") String proposta,
                                 @Field("carro_id") int carro_id);
}
