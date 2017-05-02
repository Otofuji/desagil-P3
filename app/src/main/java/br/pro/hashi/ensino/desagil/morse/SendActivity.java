package br.pro.hashi.ensino.desagil.morse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static br.pro.hashi.ensino.desagil.morse.R.id.touchView;


public class SendActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    private EditText numberEdit;
    private EditText messageEdit;
    private String string;
    private Button touchView;
    private Button finalPalavra;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        String mensagemDaLista;
        Intent intent = getIntent();
        numberEdit = (EditText) findViewById(R.id.numberEdit);
        messageEdit = (EditText) findViewById(R.id.messageEdit);
        Intent myIntent = getIntent();
        Bundle extras = getIntent().getExtras();
        mensagemDaLista= extras.getString("mensagemSelecionada");
        if (mensagemDaLista != null) {
            messageEdit.append(mensagemDaLista);
        };

        touchView = (Button) findViewById(R.id.touchView);
        touchView.setOnClickListener(this);
        touchView.setOnLongClickListener(this);

        String string = "";


    }

    @Override
    public void onClick(View v) {
        if (v == touchView){
            string = string + '.';
        }
        else{
            MorseTree arvore = MorseTree.getInstancia();
            char letra =  arvore.translate(string);
            String letter = Character.toString(letra);
            messageEdit.append(letter);

        }

    }
    @Override
    public boolean onLongClick(View v) {
        // TODO Auto-generated method stub
        string = string + '_';
        return true;
    }




    public void sendMessage(View view) {
        SmsManager manager = SmsManager.getDefault();

        String number = numberEdit.getText().toString();
        String message = messageEdit.getText().toString();

        try {
            manager.sendTextMessage(number, null, message, null, null);

            Toast toast = Toast.makeText(this, "Message sent to number!", Toast.LENGTH_SHORT);
            toast.show();
        }
        catch(IllegalArgumentException exception) {
            Log.e("SendActivity", "number or message empty");
        }
    }
}
