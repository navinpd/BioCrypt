package wallet.biocrypt.com.biocryptwallet;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gemalto.tokenlibrary.pojo.GenerateAddress;
import com.gemalto.tokenlibrary.restful.APIClient;
import com.gemalto.tokenlibrary.restful.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletSetupActivity extends AppCompatActivity {
    EditText editText;
    TextView tvPassPhrase;

    private static final String TAG = WalletSetupActivity.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_setup);
        editText = findViewById(R.id.enter_pin);
        tvPassPhrase = findViewById(R.id.pass_phrase);
        findViewById(R.id.regenerate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.pass_phrase))
                        .setText("Retrieving pass phrase ...");
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
        generate_address();
    }

    private void generate_address() {
        String server_url = null;
        try {
            ApplicationInfo appInfo = getApplicationContext().getPackageManager().getApplicationInfo(getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
            server_url = appInfo.metaData.get("SERVER_URL").toString();
        } catch(PackageManager.NameNotFoundException e){
            Log.e(TAG, "SERVER_URL NOT found!");
            return;
        }

        APIInterface apiInterface = APIClient.getClient(server_url).create(APIInterface.class);
        //Call REST API to request token to server
        Call<GenerateAddress> call = apiInterface.generateAddress();
        call.enqueue(new Callback<GenerateAddress>() {
            @Override
            public void onResponse(Call<GenerateAddress> call, Response<GenerateAddress> response) {
                if (response.body() != null) {
                    Log.i(TAG, "response:" + response.body());
                    tvPassPhrase.setText(response.body().getKeytoenglish());
                } else {
                    Log.i(TAG, "Sth wrong!");
                }
            }

            @Override
            public void onFailure(Call<GenerateAddress> call, Throwable t) {
                Log.i(TAG, "onFailure!");
                call.cancel();
            }
        });
    }

    int temp = 0;
}
