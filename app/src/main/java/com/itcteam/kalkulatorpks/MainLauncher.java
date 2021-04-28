package com.itcteam.kalkulatorpks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainLauncher extends AppCompatActivity {

    private static final int PERMISSION_STORAGE_CODE_READ = 1000;
    private static final int PERMISSION_STORAGE_CODE_WRITE = 1001;
    private static final int PERMISSION_STORAGE_CODE = 1002;
    private static final int CALL_CODE_PERMISSION = 1004;
    Button btnStart;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_launcher);
        context = this;
        btnStart = findViewById(R.id.StartApp);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainMenu.class);
                startActivity(intent);
            }
        });
        checkFilePermission();
    }

    public void checkFilePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_STORAGE_CODE_WRITE);
            }
            if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_STORAGE_CODE_WRITE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_STORAGE_CODE_WRITE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                }else{
                    Toast.makeText(context, "Akses Penyimpanan External Ditolak", Toast.LENGTH_SHORT).show();
                }
            }
            case PERMISSION_STORAGE_CODE_READ: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                }else{
                    Toast.makeText(context, "Akses Penyimpanan External Ditolak", Toast.LENGTH_SHORT).show();
                }
            }
            case PERMISSION_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
//                    downloadFile(url, filePath);
                }else{
                    Toast.makeText(context, "Akses Penyimpanan External Ditolak", Toast.LENGTH_SHORT).show();
                }
            }
            case CALL_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
//                    createCallIntent();
                }else{
                    Toast.makeText(context, "Akses Telepon Ditolak", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}