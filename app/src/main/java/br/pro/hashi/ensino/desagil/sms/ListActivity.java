package br.pro.hashi.ensino.desagil.sms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import br.pro.hashi.ensino.desagil.morse.R;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Pega o objeto que representa a listView
        ListView listaMensagem = (ListView) findViewById(R.id.lista_mensagem);
        //Cria o objeto com as strings
        MessageList a = new MessageList();
        //Adaptador para a listview(como vai ser exibida)
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, a.getListaMensagem());
        listaMensagem.setAdapter(adapter);
    }
}