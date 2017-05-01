package br.pro.hashi.ensino.desagil.morse;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.pro.hashi.ensino.desagil.morse.MorseTree;


public class SendActivity extends AppCompatActivity {
    private EditText numberEdit;
    private EditText messageEdit;
    private long unit = 300;
    private ListView listaMensagem;
    private ListAdapter adapter;
    private MessageList a;
    private AdapterView.OnItemClickListener setOnItemClickListener;
    private String mensagem;
    private TextView mensagemView;
    long startTouching;
    long startNotTouching;
    long touching;
    long notTouching;
    private CountDownTimer countdown;
    private View touchView;
    public boolean onTouch;
    private List<String> morseText;
    private String phrase;
    private TextView phraseView;
    private TextView phoneView;
    private MorseTree morseTree;

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
        messageEdit.append(mensagemDaLista);



        countdown = new CountDownTimer(2*unit, 100) {
            @Override
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {

                morseText.add(" ");
                phrase += morseTree.translate(morseText);
                phraseView.setText(phrase);
                morseText.clear();

                }
            }
        };


    public boolean onTouch(View touchView, MotionEvent event) {


        Log.e("SendActivity", String.valueOf(event.getActionMasked()));
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {

            this.startTouching = 0;
            this.touching = 0;
            startTouching = System.currentTimeMillis();
            notTouching = System.currentTimeMillis() - startNotTouching;
            countdown.cancel();
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP || event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {

            touching = System.currentTimeMillis() - startTouching;
            startNotTouching = System.currentTimeMillis();
            countdown.start();
        }



        if (touching > 0 && notTouching > 0) {
            if (touching > 5 * unit) {
                Log.e("SendActivity", "EndPhrase");
                morseText.add(" ");
                phrase += morseTree.translate(morseText);
                phrase += ".";
                phraseView.setText(phrase);
                Log.e("SendActivity", phrase);
                morseText.clear();
            } else {
                //End Word
                if (notTouching > 6 * unit && notTouching < 100 * unit) {
                    Log.e("SendActivity", "EndWord");
                    Log.e("SendActivity", "Space");
                    morseText.add(" ");
                    Log.e("SendActivity", morseText.toString());
                    phrase += morseTree.translate(morseText);
                    phrase += " ";
                    phraseView.setText(phrase);
                    Log.e("SendActivity", phrase);
                    morseText.clear();
                }
                //Space
                if (notTouching > 2 * unit && notTouching < 6 * unit) {
                    Log.e("SendActivity", "Space");
                    morseText.add(" ");
                    phrase += morseTree.translate(morseText);
                    phraseView.setText(phrase);
                    morseText.clear();
                }
                //LongPress
                if (touching > unit && touching < 5 * unit) {
                    Log.e("SendActivity", "LongPress");
                    morseText.add("-");
                }
                //ShortPress
                if (touching < unit) {
                    Log.e("SendActivity", "ShortPress");
                    morseText.add(".");
                }
            }
        }

        if (touching > 5 * unit) {
            Log.e("SendActivity", "EndPhone");
            morseText.add(" ");
            phrase += morseTree.translate(morseText);
            phoneView.setText(phrase);
            Toast.makeText(SendActivity.this, phrase, Toast.LENGTH_SHORT).show();
            Log.e("SendActivity", phrase);
            morseText.clear();
        }
        //Space
        if (notTouching > 2 * unit) {
            Log.e("SendActivity", "NumSpace");
            if (morseText.size() != 5) {
                //Toast.makeText(SendActivity.this, "Invalid Digit", Toast.LENGTH_SHORT).show();
                morseText.clear();
            } else {
                morseText.add(" ");
                phrase += morseTree.translate(morseText);
                phoneView.setText(phrase);
                morseText.clear();
            }
        }
        //LongPress
        if (touching > unit && touching < 5 * unit) {
            Log.e("SendActivity", "NumLongPress");
            morseText.add("-");
        }
        //ShortPress
        if (touching < unit) {
            Log.e("SendActivity", "NumShortPress");
            morseText.add(".");
        }


        return false;
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