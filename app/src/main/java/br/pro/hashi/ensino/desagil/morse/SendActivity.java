package br.pro.hashi.ensino.desagil.morse;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

public class SendActivity extends AppCompatActivity {
    private EditText numberEdit;
    private EditText messageEdit;
    private long unit = 300;
    long startTouching;
    long startNotTouching;
    long touching;
    long notTouching;
    private MorseTree morseTree;
    private boolean send;
    private boolean startRecording = false;
    private List<String> morseText;
    private String phrase;
    private TextView phraseView;
    private TextView phraseViewTouch;
    private CountDownTimer countdown;
    private TextView phoneView;
    private View touchView;


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

        this.send = false;
        this.startTouching = 0;
        this.startNotTouching = 0;
        this.touching = 0;
        this.notTouching = 0;
        this.morseText = new ArrayList<String>();
        this.morseTree = new MorseTree();
        this.phrase = "";
        this.touchView = (View) findViewById(id.touchView);
        touchView.setOnTouchListener(this);


        countdown = new CountDownTimer(2*unit, 100) {
            @Override
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                if (!send) {
                    morseText.add(" ");
                    phrase += morseTree.translate(morseText.toString());
                    phraseView.setText(phrase);
                    morseText.clear();
                }
                else{
                    Log.e("MorseTouch", "NumSpace");
                    if (morseText.size() != 5) {
                        //Toast.makeText(MorseTouch.this, "Invalid Digit", Toast.LENGTH_SHORT).show();
                        morseText.clear();
                    } else {
                        morseText.add(" ");
                        phrase += morseTree.translate(morseText.toString());
                        phoneView.setText(phrase);
                        morseText.clear();
                    }
                }
            }
        };
    }


    //public SendActivity(EditText numberEdit) {
        //this.numberEdit = numberEdit;
    //}

    public boolean onTouch(View touchView, MotionEvent event) {
        if (!startRecording) {
            this.startRecording = true;
            phraseViewTouch.setText("");
        }
        if (startRecording) {
            Log.e("MorseTouch", String.valueOf(event.getActionMasked()));
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
        }

        if (!send) {
            send = true;
            if (touching > 0 && notTouching > 0) {
                if (touching > 5 * unit) {
                    Log.e("MorseTouch", "EndPhrase");
                    morseText.add(" ");
                    phrase += morseTree.translate(morseText.toString());
                    phrase += ".";
                    phraseView.setText(phrase);
                    Log.e("MorseTouch", phrase);
                    morseText.clear();
                } else {
                    //End Word
                    if (notTouching > 6 * unit && notTouching < 100 * unit) {
                        Log.e("MorseTouch", "EndWord");
                        Log.e("MorseTouch", "Space");
                        morseText.add(" ");
                        Log.e("MorseTouch", morseText.toString());
                        phrase += morseTree.translate(morseText.toString());
                        phrase += " ";
                        phraseView.setText(phrase);
                        Log.e("MorseTouch", phrase);
                        morseText.clear();
                    }
                    //Space
                    if (notTouching > 2 * unit && notTouching < 6 * unit) {
                        Log.e("MorseTouch", "Space");
                        morseText.add(" ");
                        phrase += morseTree.translate(morseText.toString());
                        phraseView.setText(phrase);
                        morseText.clear();
                    }
                    //LongPress
                    if (touching > unit && touching < 5 * unit) {
                        Log.e("MorseTouch", "LongPress");
                        morseText.add("-");
                    }
                    //ShortPress
                    if (touching < unit) {
                        Log.e("MorseTouch", "ShortPress");
                        morseText.add(".");
                    }
                }
            }
        } else {
            if (touching > 0 && notTouching > 0) {
                if (touching > 5 * unit) {
                    Log.e("MorseTouch", "EndPhone");
                    morseText.add(" ");
                    phrase += morseTree.translate(morseText.toString());
                    phoneView.setText(phrase);
                    Log.e("MorseTouch", phrase);
                    morseText.clear();
                }
                //Space
                if (notTouching > 2 * unit) {
                    Log.e("MorseTouch", "NumSpace");
                    if (morseText.size() != 5) {
                        //Toast.makeText(MorseTouch.this, "Invalid Digit", Toast.LENGTH_SHORT).show();
                        morseText.clear();
                    } else {
                        morseText.add(" ");
                        phrase += morseTree.translate(morseText.toString());
                        phoneView.setText(phrase);
                        morseText.clear();
                    }
                }
                //LongPress
                if (touching > unit && touching < 5 * unit) {
                    Log.e("MorseTouch", "NumLongPress");
                    morseText.add("-");
                }
                //ShortPress
                if (touching < unit) {
                    Log.e("MorseTouch", "NumShortPress");
                    morseText.add(".");
                }
            }
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