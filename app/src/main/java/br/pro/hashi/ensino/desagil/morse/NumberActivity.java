package br.pro.hashi.ensino.desagil.morse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

public class NumberActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private EditText numberEdit;
    private EditText morseEdit;
    private Button touchView;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
    }

    public void mensagem(View view){
        numberEdit = (EditText) findViewById(R.id.numberEdit);
        morseEdit = (EditText) findViewById(R.id.morseEdit);
        Intent myIntent = new Intent(this, SendActivity.class);
        myIntent.putExtra("numero", numberEdit.getText().toString());
        startActivity(myIntent);

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
        return false;
    }
    public void wordButton (View view){
        MorseTree arvore = MorseTree.getInstancia();
        char letra =  arvore.translate(string);
        String letter = Character.toString(letra);
        if (letra != ' ') {
            numberEdit.append(letter);
        }
        string = "";
        morseEdit.setText("");
    }
}
