package br.com.matheusvieira.appwebservicecarros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PropostaActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgCarro;
    private TextView txtCarro;
    private TextView txtPreco;
    private EditText edtCliente;
    private EditText edtEmail;
    private EditText edtProposta;
    private Button btnEnviar;
    private int carro_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposta);

        imgCarro = (ImageView) findViewById(R.id.imgCarro);
        txtCarro = (TextView) findViewById(R.id.txtCarro);
        txtPreco = (TextView) findViewById(R.id.txtPreco);
        edtCliente = (EditText) findViewById(R.id.edtCliente);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtProposta = (EditText) findViewById(R.id.edtProposta);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        Intent intent = getIntent();
        String modelo = intent.getStringExtra("modelo");
        String marca = intent.getStringExtra("marca");
        int ano = intent.getIntExtra("ano", -1);
        double preco = intent.getDoubleExtra("preco", -1);
        carro_id = intent.getIntExtra("carro_id", -1);

        Picasso.with(this).load("http://10.0.2.2/herbie_noite/fotos/"+carro_id+".jpg").into(imgCarro);
        txtCarro.setText(marca+" "+modelo+" "+ano);

        NumberFormat nfBr = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        txtPreco.setText("Preço: " + nfBr.format(preco));

        btnEnviar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String cliente = edtCliente.getText().toString();
        String email = edtEmail.getText().toString();
        String proposta = edtProposta.getText().toString();

        if(cliente.trim().isEmpty() || email.trim().isEmpty() || proposta.trim().isEmpty()){
            Toast.makeText(this, "informe todos os dados da proposta", Toast.LENGTH_SHORT).show();
            edtCliente.requestFocus();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/herbie_noite/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CarroService service = retrofit.create(CarroService.class);

        Call<Proposta> propostaCall = service.enviaProposta(cliente, email, proposta, carro_id);

        propostaCall.enqueue(new Callback<Proposta>() {
            @Override
            public void onResponse(Call<Proposta> call, Response<Proposta> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Ok! Proposta Salva com Id: "+response.body().getId(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Proposta> call, Throwable t) {

            }
        });
    }
}
