package wallet.biocrypt.com.biocryptwallet;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DashBoardActivity extends AppCompatActivity {
    private boolean isChecked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        findViewById(R.id.qr_icon_1).setOnClickListener(clickListener);
        findViewById(R.id.qr_icon_2).setOnClickListener(clickListener);
        findViewById(R.id.qr_icon_3).setOnClickListener(clickListener);
        ((RadioGroup) findViewById(R.id.radio_group).findViewById(R.id.radio_group)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                isChecked = checkedRadioButton.isChecked();

            }
        });
        findViewById(R.id.pay_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isChecked) {
                    Toast.makeText(DashBoardActivity.this, "Please select your currency first.", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ImageView image = new ImageView(DashBoardActivity.this);
            image.setImageResource(R.drawable.qr_big_icon);


            AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardActivity.this);
            builder.setCancelable(true)
            .setView(image)
            .setTitle("SHARE YOUR PUBLIC ADDRESS")
            .setInverseBackgroundForced(true)
            .setPositiveButton("SHARE", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };

}
