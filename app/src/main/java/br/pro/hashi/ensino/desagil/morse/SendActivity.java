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

public class SendActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    private EditText numberEdit;
    private EditText messageEdit;
    private EditText morseEdit;
    private String string;
    private Button touchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        String mensagemDaLista;
        Intent intent = getIntent();
        morseEdit = (EditText) findViewById(R.id.morseEdit);
        numberEdit = (EditText) findViewById(R.id.numberEdit);
        messageEdit = (EditText) findViewById(R.id.messageEdit);
        Intent myIntent = getIntent();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mensagemDaLista= extras.getString("mensagemSelecionada");
            if (mensagemDaLista != null){
                numberEdit.setText("04115981061454");
                messageEdit.append(mensagemDaLista);
            }
            else{
                numberEdit.setText(extras.getString("numero"));
        }}

        touchView = (Button) findViewById(R.id.touchView);
        touchView.setOnClickListener(this);
        touchView.setOnLongClickListener(this);

        string = "";


    }

    @Override
    public void onClick(View v) {

        string = string + '.';
        morseEdit.append(".");
        Log.i("sendActivity", string);
    }
    
    @Override
    public boolean onLongClick(View v) {
        string = string + '_';
        morseEdit.append("_");
        return true;
    }

    public void wordButton (View view){
        MorseTree arvore = MorseTree.getInstancia();
        char letra =  arvore.translate(string);
        String letter = Character.toString(letra);
        if (letra != ' ') {
            messageEdit.append(letter);
        }
        string = "";
        morseEdit.setText("");
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
