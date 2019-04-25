package wallet.biocrypt.com.biocryptwallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    String val = "LAUNCHED_FIRST";
    boolean isSubsequentLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getPreferences(MODE_PRIVATE);
        if (preferences != null) {
            isSubsequentLaunch = preferences.getBoolean(val, false);
        }
        final EditText text1 = findViewById(R.id.enter_pin);
        final EditText text2 = findViewById(R.id.enter_pin_two);
        if (isSubsequentLaunch) {
            startActivity(new Intent(MainActivity.this, DashBoardActivity.class));
            finish();
        }

        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text1.getText().toString().equals(text2.getText().toString())) {
                    preferences.edit().putBoolean(val, true).apply();
                    startActivity(new Intent(MainActivity.this, RegisterTapCardActivity.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "PIN did not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
