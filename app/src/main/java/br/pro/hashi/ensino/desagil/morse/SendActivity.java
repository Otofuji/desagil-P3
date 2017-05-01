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

import java.util.ArrayList;
import java.util.List;

import br.pro.hashi.ensino.desagil.morse.MorseTree;

import static android.R.attr.id;


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

        this.send = false;
        this.startTouching = 0;
        this.startNotTouching = 0;
        this.touching = 0;
        this.notTouching = 0;
        this.morseText = new ArrayList<String>();
        this.morseTree = new MorseTree();
        this.phrase = "";
        this.phraseView = (TextView) findViewById(id.textMorse);
        this.phraseViewTouch = (TextView) findViewById(id.textMorse);
        this.phoneView = (TextView) findViewById(id.textPhone);
        this.touchWarning = (ImageView) findViewById(id.touchWarning);
        this.sendButton = (ImageButton) findViewById(id.sendButton);
        this.backspace = (ImageButton) findViewById(id.backspace);
        this.menuButton = (ImageButton) findViewById(id.menuButton);
        this.contactButton = (ImageButton) findViewById(id.contactButton);
        this.contactList = (ListView) findViewById(id.contactList);
        this.messageList = (ListView) findViewById(id.messageList);
        this.touchView = (View) findViewById(id.touchView);
        this.all = (View) findViewById(id.all);
        this.fromContact = false;



        countdown = new CountDownTimer(2*unit, 100) {
            @Override
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                if (!send) {
                    morseText.add(" ");
                    phrase += decoder.decodeMorse(morseText);
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
                        phrase += decoder.decodeMorse(morseText);
                        phoneView.setText(phrase);
                        morseText.clear();
                    }
                }
            }
        };
        all.setOnClickListener(null);
    }


    public SendActivity(EditText numberEdit) {
        this.numberEdit = numberEdit;
    }

    public boolean onTouch(View touchView, MotionEvent event) {
        if(fromContact){
            Toast.makeText(MorseTouch.this, "You have already chosen a contact", Toast.LENGTH_SHORT).show();
        }
        if (!startRecording) {
            this.startRecording = true;
            phraseViewTouch.setText("");
        }
        if (startRecording) {
            Log.e("MorseTouch", String.valueOf(event.getActionMasked()));
            int action = event.getAction() & MotionEvent.ACTION_MASK;
            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
                setActivityBackgroundColor(Color.parseColor("#2C2C2C"));
                this.startTouching = 0;
                this.touching = 0;
                startTouching = System.currentTimeMillis();
                notTouching = System.currentTimeMillis() - startNotTouching;
                countdown.cancel();
                return true;
            }

            if (event.getAction() == MotionEvent.ACTION_UP || event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
                setActivityBackgroundColor(Color.parseColor("#E0E0E0"));
                touching = System.currentTimeMillis() - startTouching;
                startNotTouching = System.currentTimeMillis();
                countdown.start();
            }
        }

        if (!send) {
            if (touching > 0 && notTouching > 0) {
                if (touching > 5 * unit) {
                    Log.e("MorseTouch", "EndPhrase");
                    morseText.add(" ");
                    phrase += decoder.decodeMorse(morseText);
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
                        phrase += decoder.decodeMorse(morseText);
                        phrase += " ";
                        phraseView.setText(phrase);
                        Log.e("MorseTouch", phrase);
                        morseText.clear();
                    }
                    //Space
                    if (notTouching > 2 * unit && notTouching < 6 * unit) {
                        Log.e("MorseTouch", "Space");
                        morseText.add(" ");
                        phrase += decoder.decodeMorse(morseText);
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
                    phrase += decoder.decodeMorse(morseText);
                    phoneView.setText(phrase);
                    Toast.makeText(MorseTouch.this, phrase, Toast.LENGTH_SHORT).show();
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
                        phrase += decoder.decodeMorse(morseText);
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

    public void setActivityBackgroundColor(int color) {
        View view = findViewById(id.thirdLayout);
        view.setBackgroundColor(color);
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