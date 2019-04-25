package wallet.biocrypt.com.biocryptwallet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RegisterTapCardActivity extends AppCompatActivity {
    ConstraintLayout view1;
    ConstraintLayout view2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_first_pay);
        view1 = findViewById(R.id.flow_1);
        view2 = findViewById(R.id.flow_2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
            }
        }, 10000);

        findViewById(R.id.success_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterTapCardActivity.this, WalletSetupActivity.class));
                finish();
            }
        });
    }
}
