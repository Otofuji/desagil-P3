package br.pro.hashi.ensino.desagil.morse;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ListActivity extends AppCompatActivity {

    private ListView listaMensagem;
    private ListAdapter adapter;
    private MessageList a;
    private AdapterView.OnItemClickListener setOnItemClickListener;
    private String mensagem;
    private TextView mensagemView;
    private CountDownTimer countdown;
    private View touchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Pega o objeto que representa a listView
        final ListView listaMensagem = (ListView) findViewById(R.id.lista_mensagem);
        final TextView mensagemView = (TextView) findViewById(R.id.cada_mensagem);
        //Cria o objeto com as strings
        MessageList a = new MessageList();
        //Adaptador para a listview(como vai ser exibida)
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, a.getListaMensagem());
        listaMensagem.setAdapter(adapter);

        listaMensagem.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mensagem = listaMensagem.getItemAtPosition(position).toString();
                mensagemView.setText(mensagem);
                Intent myIntent = new Intent(ListActivity.this, SendActivity.class);
                myIntent.putExtra("mensagemSelecionada", mensagem);
                startActivity(myIntent);
            }
        });
        countdown = new CountDownTimer(600,100){
            public void onTick(long millisUntilFinished) {}
            public void onFinish() {}



        };
    }

    /*public boolean onTouch(View touchView, MotionEvent event) {
        countdown.start();

    }*/

}
