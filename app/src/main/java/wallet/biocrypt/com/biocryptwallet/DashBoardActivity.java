package wallet.biocrypt.com.biocryptwallet;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gemalto.tokenlibrary.pojo.GetAccountInfo;
import com.gemalto.tokenlibrary.pojo.Subscribe;
import com.gemalto.tokenlibrary.pojo.TransactionResult;
import com.gemalto.tokenlibrary.pojo.json.TransactionParameters;
import com.gemalto.tokenlibrary.restful.APIClient;
import com.gemalto.tokenlibrary.restful.APIInterface;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.ParseException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class DashBoardActivity extends AppCompatActivity {
    private static final String TAG = DashBoardActivity.class.getSimpleName();
    private boolean isChecked;
    private EditText payText;
    private ImageView scanQR;
    private EditText addressTv;
    private TextView amount;
    Thread backgroundThread;
    private OkHttpClient client;
    private EditText et_address;
    private WebSocket ws;
    private static final String XRP_PUBLIC_ADDRESS = null;//"rUCzEr6jrEyMpjhs4wSdQdz4g8Y382NxfM";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        findViewById(R.id.qr_icon_1).setOnClickListener(clickListener);
        findViewById(R.id.qr_icon_2).setOnClickListener(clickListener);
        findViewById(R.id.qr_icon_3).setOnClickListener(clickListener);
        amount = findViewById(R.id.amount);
        payText = findViewById(R.id.pay_text);
        scanQR = findViewById(R.id.scan_qr);
        addressTv = findViewById(R.id.dest_add);
        client = new OkHttpClient();
        ((RadioGroup) findViewById(R.id.radio_group).findViewById(R.id.radio_group)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                isChecked = checkedRadioButton.isChecked();
            }
        });

        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(DashBoardActivity.this, new String[]{Manifest.permission.CAMERA}, 1);

            }
        });

        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAccountInfo();
            }
        });

        findViewById(R.id.pay_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChecked || payText.getText().toString().length() == 0) {
                    Toast.makeText(DashBoardActivity.this, "Please select your currency OR enter amount.", Toast.LENGTH_SHORT).show();
                } else {
                    String destAddress = "rUCzEr6jrEyMpjhs4wSdQdz4g8Y382NxfM";//addressTv.getText().toString();
                    double amount = Double.valueOf(payText.getText().toString());
                    sendTransaction(destAddress, amount);
                    //TODO: On success call for
                }
            }
        });

        getAccountInfo();
        //subscribeForNotification();

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(DashBoardActivity.this, SimpleScannerActivity.class)
                            , 100);
                } else {
                    Toast.makeText(DashBoardActivity.this, "Permission denied to camera", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 100) {
            String address = data.getStringExtra("SCANNED_RESULT");
            addressTv.setText(address);
            addressTv.setFocusable(false);
        }
    }

    private void getAccountInfo() {
        String server_url = null;
        try {
            ApplicationInfo appInfo = getApplicationContext().getPackageManager().getApplicationInfo(getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
            server_url = appInfo.metaData.get("SERVER_URL").toString();
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "SERVER_URL NOT found!");
            return;
        }


        APIInterface apiInterface = APIClient.getClient(server_url).create(APIInterface.class);
        //Call REST API to request token to server
        Call<GetAccountInfo> call = apiInterface.getAccountInfo();
        call.enqueue(new Callback<GetAccountInfo>() {
            @Override
            public void onResponse(Call<GetAccountInfo> call,final Response<GetAccountInfo> response) {
                if (response.body() != null) {
                    Log.i(TAG, "response:" + response.body());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //tvPassPhrase.setText(response.body().getKeytoenglish());
                            String xrpbalance = response.body().getXrpBalance();
                            amount.setText("XRP: "+xrpbalance);
                            amount.setVisibility(View.VISIBLE);
                            String publicaddress = response.body().getPublicaddress();
                            ((TextView) findViewById(R.id.address_1)).setText(publicaddress);
                            Log.i(TAG, "publicaddress:" + publicaddress + "xrpbalance:" + xrpbalance);
                        }
                    });
                } else {
                    Log.i(TAG, "Sth wrong!");
                }
            }

            @Override
            public void onFailure(Call<GetAccountInfo> call, Throwable t) {
                Log.i(TAG, "onFailure!");
                call.cancel();
            }
        });
    }

    private void sendTransaction(String destAddress, double amount) {
        String server_url = null;
        try {
            ApplicationInfo appInfo = getApplicationContext().getPackageManager().getApplicationInfo(getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
            server_url = appInfo.metaData.get("SERVER_URL").toString();
        } catch(PackageManager.NameNotFoundException e){
            Log.e(TAG, "SERVER_URL NOT found!");
            return;
        }

        Gson gson = new Gson();
        //Prepare json token request
        String transParamStr = "{\"destinationAddress\":\"" + destAddress
                + "\",\"amount\":\"" + amount + "\"}";
        TransactionParameters transParam = gson.fromJson(transParamStr, TransactionParameters.class);

        APIInterface apiInterface = APIClient.getClient(server_url).create(APIInterface.class);
        //Call REST API to request token to server
        Call<TransactionResult> call = apiInterface.sendTransaction(transParam);
        call.enqueue(new Callback<TransactionResult>() {
            @Override
            public void onResponse(Call<TransactionResult> call, Response<TransactionResult> response) {
                if (response.body() != null) {
                    Log.i(TAG, "response:" + response.body());
                    String resultcode = response.body().getResultCode();
                    Log.i(TAG, "resultcode:" + resultcode);
                    if (resultcode.equalsIgnoreCase("tesSUCCESS")) {
                        Toast.makeText(DashBoardActivity.this, "Transaction successful", Toast.LENGTH_LONG).show();
//                        getAccountInfo();
                    }
                } else {
                    Log.i(TAG, "Sth wrong!");
                }
            }

            @Override
            public void onFailure(Call<TransactionResult> call, Throwable t) {
                Log.i(TAG, "onFailure!");
                call.cancel();
            }
        });
    }

    private void subscribeForNotification() {
        Request request = new Request.Builder().url("wss://s.altnet.rippletest.net:51233").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        ws = client.newWebSocket(request, listener);
        String msg = "{ \"id\": 1, \"command\": \"subscribe\", \"accounts\": [\"rUCzEr6jrEyMpjhs4wSdQdz4g8Y382NxfM\"], \"streams\": [ \"transactions\"] }";
        ws.send(msg);
    }

    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        //@Override
        public void onOpen(WebSocket webSocket, Response response) {
            Log.i(TAG, "onOpen : " );
        }
        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Log.i(TAG, "Receiving : " + text);
            processMsg(text);
        }
        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            Log.i(TAG, "Receiving bytes : " + bytes.hex());
        }
        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            Log.i(TAG, "Closing : " + code + " / " + reason);
        }
        //@Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            Log.i(TAG, "Error : " + t.getMessage());
        }
    }

    private void processMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject obj = new JSONObject(msg);
                    JSONObject transaction = obj.getJSONObject("transaction");
                    if (transaction != null) {
                        String trx_type = transaction.getString("TransactionType");
                        String account = transaction.getString("Destination");
                        if (account.equalsIgnoreCase(XRP_PUBLIC_ADDRESS) && trx_type.equalsIgnoreCase("Payment")){
                            double amount = (double)transaction.getInt("Amount");
                            Toast.makeText(DashBoardActivity.this,"You've received " + String.valueOf(amount/1000000) + " XRP",Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + msg + "\"");
                }
            }
        });
    }
}
