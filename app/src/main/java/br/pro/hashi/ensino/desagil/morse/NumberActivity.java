package br.pro.hashi.ensino.desagil.morse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NumberActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private EditText numberEdit;
    private EditText morseEdit;
    private Button touchView2;
    private String frase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        touchView2 = (Button) findViewById(R.id.touchView2);
        touchView2.setOnClickListener(this);
        touchView2.setOnLongClickListener(this);

        numberEdit = (EditText) findViewById(R.id.numberEdit);
        morseEdit = (EditText) findViewById(R.id.morseEdit);

        if (numberEdit.getText().toString().isEmpty()){
            frase="";
        }


    }

    public void mensagem(View view){

        Intent myIntent = new Intent(NumberActivity.this, SendActivity.class);
        myIntent.putExtra("numero", numberEdit.getText().toString());
        startActivity(myIntent);

    }

    @Override
    public void onClick(View v) {
        frase = frase + '.';
        morseEdit.append(".");
        Log.i("numberActivity", frase);
    }

    @Override
    public boolean onLongClick(View v) {
        frase = frase + '_';
        morseEdit.append("_");
        return true;
    }
    public void wordButton (View view){
        MorseTree arvore = MorseTree.getInstancia();
        char letra =  arvore.translate(frase);
        String letter = Character.toString(letra);
        if (letra != ' ') {
            numberEdit.append(letter);
        }
        frase = "";
        morseEdit.setText("");
    }
}
