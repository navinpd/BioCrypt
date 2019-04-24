package wallet.biocrypt.com.biocryptwallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class WalletSetupActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_setup);
        editText = findViewById(R.id.enter_pin);
        findViewById(R.id.regenerate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.pass_phrase))
                        .setText("The text has been changed to new pass phrase which is different than last one.");
            }
        });

        findViewById(R.id.proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WalletSetupActivity.this, RegisterTapCardActivity.class));
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (((s.length() - temp) % 4) == 0) {
                    editText.setText(editText.getText().toString() + " ");
                    int position = editText.getText().toString().length();
                    Editable etext = editText.getText();
                    Selection.setSelection(etext, position);
                    temp++;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    int temp = 0;
}
