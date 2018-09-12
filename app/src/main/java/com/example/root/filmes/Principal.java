package com.example.root.filmes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.SearchView;

import com.example.root.filmes.Adapter.FilmeAdapter;

import java.util.ArrayList;


public class Principal extends AppCompatActivity {

    private ListView listView;
    private FilmeAdapter adapter;
    private ProgressDialog load;
    boolean loadingMore = false;
    ArrayList<FilmeBean> myListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Busca");

        GetJson download = new GetJson();


        listView = (ListView) findViewById(R.id.listView);

        download.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    adapter.filter("");
                    listView.clearTextFilter();
                }
                else {
                    adapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.action_settings){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class GetJson extends AsyncTask<Void, Void, FilmesList>  {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(Principal.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected FilmesList doInBackground(Void... params) {
            Utils util = new Utils();

            return util.getInformacao("http://www.omdbapi.com/?i=tt3896198&apikey=bd52e9d4&s=Batman&page=2");
        }

        protected void onPostExecute(final FilmesList filme){

            if(adapter == null){
                adapter = new FilmeAdapter(Principal.this, filme.getFilmes());
                listView.setAdapter(adapter);

            }
            else{
                adapter.notifyDataSetChanged();
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent it = new Intent(Principal.this, DetailsActivity.class);
                    it.putExtra("filme", filme.getFilmes().get(i));
                    startActivity(it);
                }
            });

            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView absListView, int i) {

                }

                @Override
                public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                    int lastInScreen = firstVisibleItem + visibleItemCount;

                    if ((lastInScreen == totalItemCount) && !(loadingMore)) {
                        Thread thread = new Thread(null, loadMoreListItems);
                        thread.start();

                    }
                }

                Runnable loadMoreListItems = new Runnable() {
                    @Override
                    public void run() {
                        loadingMore = true;
                        myListItems = new ArrayList<FilmeBean>();
                        try {

                            Thread.sleep(1000);

                        } catch (InterruptedException e) {
                        }

                        for (int i = 0; i < filme.getFilmes().size(); i++) {
                            myListItems.add(filme.getFilmes().get(i));
                        }
                        runOnUiThread(returnRes);
                    }

                };

                private Runnable returnRes = new Runnable() {
                    @Override
                    public void run() {

                        if (myListItems != null && myListItems.size() > 0) {

                                adapter = new FilmeAdapter(Principal.this, myListItems);
                                listView.setAdapter(adapter);

                                adapter.notifyDataSetChanged();

                        }
                        Log.i("Runnable", String.valueOf(myListItems));
                        setTitle("Never ending List with " + String.valueOf(adapter.getCount()) + " items");
                        adapter.notifyDataSetChanged();
                        loadingMore = false;
                    }
                };
            });
            load.dismiss();
        }
    }
}
