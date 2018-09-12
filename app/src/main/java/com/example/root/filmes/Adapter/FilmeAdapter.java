package com.example.root.filmes.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.root.filmes.FilmeBean;
import com.example.root.filmes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FilmeAdapter extends BaseAdapter {

    public List<FilmeBean> list;
    private LayoutInflater inflater;
    public ArrayList<FilmeBean> arrayList;

    public FilmeAdapter(Context c, List<FilmeBean> l){
        this.list = l;
        arrayList = new ArrayList<FilmeBean>();
        arrayList.addAll(l);
        Log.i("Adapter Filme", String.valueOf(l));
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
           public int getCount() {
               return list.size();
           }

           @Override
           public Object getItem(int position) {
               return list.get(position);
           }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if(view == null){
            view = inflater.inflate(R.layout.filme, null);
            holder = new ViewHolder();
            view.setTag(holder);

            holder.ivImg = (ImageView) view.findViewById(R.id.ivImg);
            holder.tvTitulo = (TextView) view.findViewById(R.id.tvTitulo);

        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        holder.tvTitulo.setText(list.get(position).getTitulo());
        holder.ivImg.setImageBitmap(null);
        holder.ivImg.setImageBitmap(list.get(position).getPoster());

        return view;
    }

    // INNER CLASS
    private static class ViewHolder{
        ImageView ivImg;
        TextView tvTitulo;

    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length()==0){
            list.addAll(arrayList);
            Log.i("Array List", String.valueOf(arrayList));
        }
        else {

            for (FilmeBean  model : arrayList){
                if (model.getTitulo().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    list.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }


}
