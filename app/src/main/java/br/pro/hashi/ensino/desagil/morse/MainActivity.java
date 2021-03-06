package br.pro.hashi.ensino.desagil.morse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EXAMPLE = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void goToSendActivity() {
        Intent intent = new Intent(this, SendActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToDictActivity(View view) {
        Intent intent = new Intent(this, DictActivity.class);
        startActivity(intent);
        finish();
    }


    public void tryToGoToNumberActivity(View view) {
        int permission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS);

        if(permission == PackageManager.PERMISSION_GRANTED) {
            goToNumberActivity();
        }
        else {
            String[] permissions = new String[1];
            permissions[0] = Manifest.permission.SEND_SMS;
            ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_EXAMPLE);
        }
    }

    public void goToListActivity(View v) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToNumberActivity() {
        Intent intent = new Intent(this, NumberActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == REQUEST_EXAMPLE) {
            if(grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast toast = Toast.makeText(this, "Cannot use this application without permission to send SMS!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                goToNumberActivity();
            }
        }
    }
}
