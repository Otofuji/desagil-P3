package br.pro.hashi.ensino.desagil.morse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Isabella on 28/04/2017.
 */

public class DictActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);
        // Pega o objeto que representa a listView
        ListView listaDicionario = (ListView) findViewById(R.id.lista_dicionario);
        //Cria o objeto com as strings
        MorseTree tree = MorseTree.getInstancia();
        //Adaptador para a listview(como vai ser exibida)
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tree.getDicionario());
        listaDicionario.setAdapter(adapter);
    }
}
