package br.com.matheusvieira.appwebservicecarros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lvCarros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lvCarros = (ListView) findViewById(R.id.lvCarros);

        lvCarros.setOnItemClickListener(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/herbie_noite/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CarroService service = retrofit.create(CarroService.class);

        Call<List<Carro>> carros = service.getCarros();

        carros.enqueue(new Callback<List<Carro>>() {
            @Override
            public void onResponse(Call<List<Carro>> call, Response<List<Carro>> response) {
                if(response.isSuccessful()){
                    List<Carro> listaCarros = response.body();

                    CarroAdapter adapter = new CarroAdapter(getApplicationContext(),
                            listaCarros, "http://10.0.2.2/herbie_noite/fotos/");

                    lvCarros.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<Carro>> call, Throwable t) {


            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Carro carro = (Carro)adapterView.getItemAtPosition(i);
        //Toast.makeText(this, "Modelo: " + carro.getModelo(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, PropostaActivity.class);
        intent.putExtra("modelo", carro.getModelo());
        intent.putExtra("marca", carro.getMarca());
        intent.putExtra("ano", carro.getAno());
        intent.putExtra("preco", carro.getPreco());
        intent.putExtra("carro_id", carro.getId());
        startActivity(intent);
    }
}
