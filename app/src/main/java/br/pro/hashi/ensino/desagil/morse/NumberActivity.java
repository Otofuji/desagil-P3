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
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        touchView2 = (Button) findViewById(R.id.touchView2);
        touchView2.setOnClickListener(this);
        touchView2.setOnLongClickListener(this);

        numberEdit = (EditText) findViewById(R.id.numberEdit);
        morseEdit = (EditText) findViewById(R.id.morseEdit);

    }

    public void mensagem(View view){

        Intent myIntent = new Intent(NumberActivity.this, SendActivity.class);
        myIntent.putExtra("numero", numberEdit.getText().toString());
        startActivity(myIntent);



        string = "";


    }

    @Override
    public void onClick(View v) {
        string = string + '.';
        morseEdit.append(".");
        Log.i("numberActivity", string);
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
            numberEdit.append(letter);
        }
        string = "";
        morseEdit.setText("");
    }
}
