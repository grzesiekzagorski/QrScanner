package com.example.grzesiek87.qrscanner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.grzesiek87.qrscanner.domain.WarehouseRest;
import com.example.grzesiek87.qrscanner.repository.ApiClient;
import com.example.grzesiek87.qrscanner.repository.ApiInterface;
import com.google.zxing.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }


    public void Scanner(View view) {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();

    }

    @Override
    public void handleResult(final Result rawResult) {
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Wynik skanowania:");
        Call<List<WarehouseRest>> call = apiInterface.getWarehousesRest();
        call.enqueue(new Callback<List<WarehouseRest>>() {
            @Override
            public void onResponse(Call<List<WarehouseRest>> call, Response<List<WarehouseRest>> response) {
                boolean isFound = false;
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        try {
                            if (response.body().get(i).getId() == Integer.parseInt(rawResult.getText())) {
                                WarehouseRest warehouseRest = response.body().get(i);
                                Log.d("INFO",warehouseRest.toString());
                                alertDialog.setMessage(warehouseRest.toString());
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                                isFound = true;
                                break;
                            }
                        } catch (NumberFormatException e) {
                            alertDialog.setTitle("Błąd");
                            alertDialog.setMessage("Nieprawidłowy format kodu");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                            isFound = true;
                        }
                    }
                    if (!isFound) {
                        alertDialog.setMessage("Nie znaleziono produktu");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<WarehouseRest>> call, Throwable t) {
                Log.d("ERROR","Brak połączenia",t);
                alertDialog.setTitle("Błąd");
                alertDialog.setMessage("Brak połączenia");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        setContentView(R.layout.activity_main);
    }
}