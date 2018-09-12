package com.example.root.filmes;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class FilmeBean extends FilmesList implements Parcelable {

    private String titulo;
    private String ano;
    private String imdbid;
    private String tipo;
    private String pathImg;
    private Bitmap poster;

    public FilmeBean(){
        super();
    }

    protected FilmeBean(Parcel in) {
        titulo = in.readString();
        ano = in.readString();
        imdbid = in.readString();
        tipo = in.readString();
        pathImg = in.readString();
        poster = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public String getPathImg() {
        return pathImg;
    }

    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

    public static final Creator<FilmeBean> CREATOR = new Creator<FilmeBean>() {
        @Override
        public FilmeBean createFromParcel(Parcel in) {
            return new FilmeBean(in);
        }

        @Override
        public FilmeBean[] newArray(int size) {
            return new FilmeBean[size];
        }
    };

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getImdbid() {
        return imdbid;
    }

    public void setImdbid(String imdbid) {
        this.imdbid = imdbid;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Bitmap getPoster() {
        return this.poster;
    }

    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }

    @Override
    public String toString(){
        String aux = "<b>Titulo:<b> "+getTitulo()+"<br>";
        aux += "<b>Ano:<b> "+getAno()+"<br>";
        aux += "<b>ImdBID:<b> "+getImdbid()+"<br>";
        aux += "<b>Tipo:<b> "+getTipo()+"<br>";
        return(aux);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(titulo);
        parcel.writeString(ano);
        parcel.writeString(imdbid);
        parcel.writeString(tipo);
        parcel.writeString(pathImg);
    }


}
