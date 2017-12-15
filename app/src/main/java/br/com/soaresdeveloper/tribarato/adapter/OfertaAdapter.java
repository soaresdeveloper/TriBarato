package br.com.soaresdeveloper.tribarato.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.com.soaresdeveloper.tribarato.R;
import br.com.soaresdeveloper.tribarato.entidades.Oferta;

/**
 * Created by Soares on 14/12/2017.
 */

public class OfertaAdapter extends ArrayAdapter<Oferta> {

    public static final String ENVIADO_POR = "Enviado por: ";
    public static final String ANONIMO = "Usuário Anônimo";

    public OfertaAdapter(Context context, int resource, List<Oferta> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_oferta, parent, false);
        }

        TextView titulo = (TextView) convertView.findViewById(R.id.titulo);
        TextView autor = (TextView) convertView.findViewById(R.id.autor);
        TextView descricao = (TextView) convertView.findViewById(R.id.descricao);
        TextView preco = (TextView) convertView.findViewById(R.id.preco);
        TextView dataHora = (TextView) convertView.findViewById(R.id.dataHora);
        Button btnQueroIsto = (Button) convertView.findViewById(R.id.btnQueroIsto);

        Oferta oferta = getItem(position);

//      TODO Implementar envio de fotos nas ofertas
//        boolean isPhoto = message.getPhotoUrl() != null;
//        if (isPhoto) {
//            messageTextView.setVisibility(View.GONE);
//            photoImageView.setVisibility(View.VISIBLE);
//            Glide.with(photoImageView.getContext())
//                    .load(message.getPhotoUrl())
//                    .into(photoImageView);
//        }

        titulo.setText(oferta.getTitulo());
        if(oferta.getAutor() != null){
        autor.setText(ENVIADO_POR.concat(oferta.getAutor()));
        }else{
            autor.setText(ENVIADO_POR.concat(ANONIMO));
        }
        descricao.setText(oferta.getDescricao());
        preco.setText(oferta.getPreco());
        dataHora.setText(oferta.getData());
        btnQueroIsto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Implementar funcionalidade de redirecionamento
                // para sites ou serviço de Google Maps caso o seja endereço de loja fisica
            }
        });

        return convertView;
    }
}
