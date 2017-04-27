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



public class SendActivity extends AppCompatActivity {
    private EditText numberEdit;
    private EditText messageEdit;
    private ListView listaMensagem;
    private ListAdapter adapter;
    private MessageList a;
    private AdapterView.OnItemClickListener setOnItemClickListener;
    private String mensagem;
    private TextView mensagemView;
    private CountDownTimer touching;
    private CountDownTimer notTouching;
    private View touchView;
    public boolean onTouch;

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

        touching = new CountDownTimer(600,100){
            public void onTick(long millisUntilFinished) {}
            public void onFinish() {}
        };

        notTouching = new CountDownTimer(600,100){
            public void onTick(long millisUntilFinished) {}
            public void onFinish() {}
        };

    }

    public boolean onTouch(View touchView, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_BUTTON_PRESS){
            onTouch = true;
        }

        if(event.getAction()==MotionEvent.ACTION_BUTTON_RELEASE){
            onTouch = false;
        }

        return onTouch;

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
