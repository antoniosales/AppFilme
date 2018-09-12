package com.example.root.filmes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class Utils {

    public FilmesList getInformacao(String end){

        String json;
        FilmesList retorno;
        json = NetworkUtils.getJSONFromAPI(end);
        retorno = parseJson(json);

        return retorno;
    }

    private FilmesList parseJson(String json){
            FilmesList filme = new FilmesList();
        try {

            JSONObject jo = new JSONObject(json);
            JSONArray ja;
            filme.setFilmes(new ArrayList<FilmeBean>());

            ja = jo.getJSONArray("Search");
            for(int i = 0, tam = ja.length(); i < tam; i++){

                FilmeBean f = new FilmeBean();
                f.setTitulo(ja.getJSONObject(i).getString("Title"));
                f.setAno(ja.getJSONObject(i).getString("Year"));
                f.setImdbid(ja.getJSONObject(i).getString("imdbID"));
                f.setTipo(ja.getJSONObject(i).getString("Type"));
                f.setPathImg(ja.getJSONObject(i).getString("Poster"));
                f.setPoster(baixarImagem(ja.getJSONObject(i).getString("Poster")));
                filme.getFilmes().add(f);

            }

            return filme;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    Bitmap baixarImagem(String url) {
        try{
            URL endereco;
            InputStream inputStream;
            Bitmap imagem; endereco = new URL(url);
            inputStream = endereco.openStream();
            imagem = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return imagem;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
