package br.com.soaresdeveloper.tribarato.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
    public static final String MAPS = "http://www.maps.google.com.br/maps?q=";

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

        final Oferta oferta = getItem(position);

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
        if (oferta.getUsuario() != null) {
            String mUsuario = oferta.getUsuario().getNome().concat(" ").concat(oferta.getUsuario().getSobrenome());
            autor.setText(ENVIADO_POR.concat(mUsuario));
        } else {
            autor.setText(ENVIADO_POR.concat(ANONIMO));
        }
        descricao.setText(oferta.getDescricao());
        preco.setText(oferta.getPreco());
        dataHora.setText(oferta.getData());
        btnQueroIsto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oferta.getSite() != null && !oferta.getSite().trim().equals("")) {
                    // se for loja virtual
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(oferta.getSite()));
                    ContextCompat.startActivity(getContext(), intent, null);
                }else if(oferta.getLocal() != null && !oferta.getLocal().trim().equals("")){
                    // Se for loja fisica
                    StringBuilder url = new StringBuilder(MAPS)
                            .append(oferta.getLocal()).append("+")
                            .append(oferta.getEstado().replace(" ","+")).append("+")
                            .append(oferta.getCidade().replace(" ","+"));

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url.toString()));
                    ContextCompat.startActivity(getContext(), intent, null);
                }
            }
        });

        return convertView;
    }
}
