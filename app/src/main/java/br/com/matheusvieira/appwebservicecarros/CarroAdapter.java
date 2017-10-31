package br.com.matheusvieira.appwebservicecarros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

/**
 * Created by Matheus Vieira on 09/10/2017.
 */

public class CarroAdapter extends BaseAdapter {

    private Context ctx;
    private List<Carro> carros;
    private String pathFotos;

    public CarroAdapter(Context ctx, List<Carro> carros, String pathFotos) {
        this.ctx = ctx;
        this.carros = carros;
        this.pathFotos = pathFotos;
    }

    @Override
    public int getCount() {
        return carros.size();
    }

    @Override
    public Object getItem(int i) {
        return carros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Carro carro = carros.get(i);

        View linha = LayoutInflater.from(ctx).inflate(R.layout.item_carro, null);

        ImageView imgFoto = (ImageView) linha.findViewById(R.id.imgFoto);
        TextView txtModelo = (TextView) linha.findViewById(R.id.txtModelo);
        TextView txtMarcaAno = (TextView) linha.findViewById(R.id.txtMarcaAno);
        TextView txtPreco = (TextView) linha.findViewById(R.id.txtPreco);

        Picasso.with(ctx).load(pathFotos+carro.getId()+".jpg").into(imgFoto);
        txtModelo.setText("Modelo: "+carro.getModelo());
        txtMarcaAno.setText("Marca: "+carro.getMarca() + " - Ano: "+carro.getAno());
        NumberFormat nfbr = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
        txtPreco.setText("Preço: " + nfbr.format(carro.getPreco()));

        return linha;
    }
}
