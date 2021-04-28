package com.itcteam.kalkulatorpks.ui.about;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.itcteam.kalkulatorpks.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExportCSV {

    List<String> dataLines;
    Context context;
    ProgressDialog dialog;

    public ExportCSV(List<String> dataLines, Context context) {
        this.dataLines =  new ArrayList<String>(dataLines);
        this.context = context;
        dialog = new ProgressDialog(context);
//        dataLines.add(new String[]
//                { "John", "Doe", "38", "Comment Data\nAnother line of comment data" });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)

    public boolean DoExportCSV(String title){
        FileWriter fWriter;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
//        System.out.println(dtf.format(now));
        File sdCardFile = new File(Environment.getExternalStorageDirectory() + "/"+ title +"-"+ dtf.format(now).toString() +".csv");
        dialog.setMessage("Sedang membuat file csv.");
        dialog.show();
        Log.d("TAG", sdCardFile.getPath()); //<-- check the log to make sure the path is correct.
        try{
            fWriter = new FileWriter(sdCardFile, false);
            String baru = "";
            for (String string:dataLines){
                baru = baru + string;
            }
            Log.w("String CSV", baru);

            fWriter.write(baru);
            fWriter.flush();
            fWriter.close();
            dialog.dismiss();
//            Toast.makeText(context, "CSV Berhasil dibuat !!!", Toast.LENGTH_SHORT).show();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void DoExportCSV1() throws IOException {
        {
            Log.w("Ma", "DoExportCSV");

            File folder = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)));

            boolean var = false;
            if (!folder.exists())
                var = folder.mkdir();

            System.out.println("" + var);


            final String filename = folder.toString() + "/" + "Test.csv";

            // show waiting screen
            CharSequence contentTitle = context.getString(R.string.app_name);
            final ProgressDialog progDailog = ProgressDialog.show(
                    context, contentTitle, "Membuat file CSV........",
                    true);//please wait
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                }
            };

            progDailog.show();

            new Thread() {
                public void run() {
                    try {

                        Log.w("Ma", "thread export");
                        Log.w("DataLines", "dataLines : " + dataLines.get(0).toString());
                        FileWriter fw = new FileWriter(filename);


                        for (String lines : dataLines){
                            Log.w("lines", "lines : " + lines);
                            fw.append(lines.toString());
                            fw.append("\n");
                        }
                        // fw.flush();
                        fw.close();

                    } catch (Exception e) {
                    }
                    handler.sendEmptyMessage(0);
                    progDailog.dismiss();
                }
            }.start();
        }
    }

    public void setDate(String first, String end, int i) {



    }
}
