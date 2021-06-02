package com.itcteam.kalkulatorpks.ui.about.losses_pabrik;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Edits;
import android.os.Build;
import android.os.Environment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung03_simpan;
import com.itcteam.kalkulatorpks.util.DatabaseHandler;
import com.itcteam.kalkulatorpks.ui.about.ExportCSV;
import com.itcteam.kalkulatorpks.ui.calculate.task.fragment.Hitung05_final_nonFrag;
import com.itcteam.kalkulatorpks.util.DateTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class RecyclerListBerkas_lb extends RecyclerView.Adapter<RecyclerListBerkas_lb.BerkasViewHolder> implements Filterable {

    List dataBerkas;
    List<HashMap<String, String>> dataBerkasAll;
    Context context;
    DatabaseHandler databaseHandler;
    int tipe;
    String start, end;
    Boolean export;

    public RecyclerListBerkas_lb(Context context, List data, int tipe, Boolean export) {
        this.context = context;
        databaseHandler = new DatabaseHandler(context);
        this.tipe = tipe;
        this.export = export;

        prosesUlang(data);
    }

    public void triggerDate(List data){
        dataBerkas.clear();
        dataBerkasAll.clear();
        prosesUlang(data);
        this.notifyDataSetChanged();
    }

    public void prosesUlang(List data){
        dataBerkas = new ArrayList<>();

        for (int i = 0; i<data.size();i++ ){
            final HashMap<String,String> value = (HashMap<String, String>) data.get(i);
            String berkasObj = databaseHandler.getItemValue(value.get("id_record"));;
            String title, date;
            date = value.get("date");
            Log.e("EEE", "MASOK : " + berkasObj);
            try {
                JSONObject jsonObject = new JSONObject(berkasObj);
                title = jsonObject.getString("nama");
                HashMap<String,String> nvalue = new HashMap<String, String>();
                nvalue.put("nama", title);
                nvalue.put("id_record", value.get("id_record"));
                nvalue.put("date", date);
                dataBerkas.add(nvalue);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.dataBerkasAll = new ArrayList<>(dataBerkas);
    }

    @NonNull
    @Override
    public BerkasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_berkas, null);
        return new BerkasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BerkasViewHolder holder, int position) {
        final HashMap<String,String> value = (HashMap<String, String>) dataBerkas.get(position);
        Log.w("tipe", Integer.toString(tipe));
        Log.w("Date", value.get("date"));
        Log.w("id_record", value.get("id_record"));

        try {
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String startString = df.format(c);
            Date end = new SimpleDateFormat("yyyy-MM-dd").parse(value.get("date"));
            Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startString);
            DateTools dateTools = new DateTools(start, end);
            int dateCount = dateTools.getDaysDifference();
            String msgDate = "";
            if (dateCount<0){
                dateCount *=-1;
                msgDate = dateCount + " hari yang lalu";
            }else if(dateCount==0){
                msgDate = "Hari ini";
            }else{
                msgDate = dateCount + " hari yang lalu";
            }
            holder.text3.setText(msgDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.text1.setText(value.get("nama"));

//        holder.text3.setVisibility(View.GONE);
        holder.text2.setText(value.get("date"));

        Log.w("export", "D : " + export);

        holder.rec.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Log.w("Onclick", "Pressed");
                if(export){
                    Log.w("Ma", "export");
                    List dataLines = new ArrayList<String>();
                    String fetchData = databaseHandler.getRecordValue(value.get("id_record"), tipe);
                    try {
                        singleExport(new JSONObject(fetchData), value.get("nama"), value.get("date"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    String linesC =  "Tanggal,Nama Kebun,Perfomance,Availability,Quality,OEE\n";
//                    try {
//                        String fetchData = databaseHandler.getRecordValue(value.get("id_record"), tipe);
//
//                        singleExport(new JSONObject(fetchData), value.get("nama"), value.get("date"));
//
//                        JSONObject jsonBerkas = new JSONObject(fetchData);
//                        String lines = value.get("date")+ "," +
//                                value.get("nama")+ "," +
//                                jsonBerkas.getString("perfomance")+ "," +
//                                jsonBerkas.getString("availability")+ "," +
//                                jsonBerkas.getString("quality")+ "," +
//                                jsonBerkas.getString("hasil");
//
//                        Log.w("Lines", lines);
//                        Log.w("LinesC", linesC);
//                        dataLines.add(linesC);
//                        dataLines.add(lines);
//
//                        ExportCSV exportCSV = new ExportCSV(dataLines, context);
//                        exportCSV.DoExportCSV("OEESingle");
//                        Toast.makeText(context, "Berhasil di export", Toast.LENGTH_SHORT).show();
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }else{
                    Log.w("HolderREC","false");
                    Intent intent = new Intent(context, Hitung03_simpan.class);
                    intent.putExtra("json", databaseHandler.getRecordValue(value.get("id_record"), tipe));
                    intent.putExtra("hide", "yes");
                    context.startActivity(intent);
                }
            }
        });

    }

    private void singleExport(JSONObject retJson, String nama, String tanggal) {
        try {

            List listDataName = new ArrayList();
            listDataName.add("USF");
            listDataName.add("USB");
            listDataName.add("Minyak");
            listDataName.add("Kernel");
            File sd = Environment.getExternalStorageDirectory();
            String csvFile = "LossesPabrikSingle-"+ tanggal +"-"+ nama +".xls";

            File directory = new File(sd.getAbsolutePath());

            //create directory if not exist
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }
            //file path
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale(Locale.US.getLanguage(), Locale.US.getCountry()));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);


            WritableSheet sheetA = workbook.createSheet("Losses Pabrik", 0);
            int basePosition = 0;

            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            for (int id = 0; id < listDataName.size(); id++){

                Log.w("Base Position", String.valueOf(basePosition));
                Log.w("Base Position2", String.valueOf(id));
                Float totalLosses = Float.valueOf(0);
                Float totalLossesTBS = Float.valueOf(0);
                Iterator nameKey;
                Iterator valueKey;
                List listKey = new ArrayList<>();
                List listKeyValue = new ArrayList();


                JSONObject getJson = (JSONObject) retJson.get(listDataName.get(id).toString());
                JSONObject getJson2 = new JSONObject();
                nameKey = getJson.keys();


                while (nameKey.hasNext()){
                    listKey.add(nameKey.next().toString());
                    Log.w("Key Name", nameKey.toString());
                }


                //Excel sheetA first sheetA
                // column and row titles


                sheetA.addCell(new Label(0, 0, "Tanggal"));
                sheetA.addCell(new Label(1, 0, tanggal));
                sheetA.addCell(new Label(0, 1, "Nama Kebun"));
                sheetA.addCell(new Label(1, 1, nama));

                sheetA.addCell(new Label(0, 3+basePosition, listDataName.get(id).toString(), cellFormat));
                sheetA.addCell(new Label(0, 4+basePosition, "No", cellFormat));
                sheetA.addCell(new Label(1, 4+basePosition, "Sampel", cellFormat));
                sheetA.addCell(new Label(2, 4+basePosition, "On Sampel", cellFormat));
                sheetA.addCell(new Label(3, 4+basePosition, "On TBS", cellFormat));

                for (int index = 0;index < listKey.size(); index++ ){

                    sheetA.addCell(new Label(0, 5+index+basePosition, String.valueOf(index+1), cellFormat));
                    getJson2 = (JSONObject) getJson.get(listKey.get(index).toString());

                    sheetA.addCell(new Label(1, index+5+basePosition, listKey.get(index).toString(), cellFormat));

                    sheetA.addCell(new Label(2, index+5+basePosition, getJson2.get("Hasil Sampel").toString(),cellFormat));
                    sheetA.addCell(new Label(3, index+5+basePosition, getJson2.get("Hasil ON TBS").toString(),cellFormat));

                    if (index+1==listKey.size()){
                        sheetA.addCell(new Label(0, index+6+basePosition, "Total",cellFormat));
                        sheetA.addCell(new Label(2, index+6+basePosition, String.valueOf(totalLosses),cellFormat));
                        sheetA.addCell(new Label(3, index+6+basePosition, String.valueOf(totalLossesTBS),cellFormat));
                        sheetA.mergeCells(0,index+6+basePosition,1,index+6+basePosition);
                        basePosition = index+6+basePosition;
                    }

                }

                for (int c=0; c<100; c++){
                    CellView cell = sheetA.getColumnView(c);
                    cell.setAutosize(true);
                    sheetA.setColumnView(c, cell);
                }

                // close workbook
            }
            workbook.write();
            workbook.close();
            Toast.makeText(context, "File berhasil di buat", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//    private void singleExport(JSONObject retJson, String nama, String tanggal) {
//        try {
//
//            List listDataName = new ArrayList();
//            listDataName.add("USF");
//            listDataName.add("USB");
//            listDataName.add("Minyak");
//            listDataName.add("Kernel");
//            File sd = Environment.getExternalStorageDirectory();
//            String csvFile = "LossesPabrikSingle-"+ tanggal +"-"+ nama +".xls";
//
//            File directory = new File(sd.getAbsolutePath());
//
//            //create directory if not exist
//            if (!directory.isDirectory()) {
//                directory.mkdirs();
//            }
//            //file path
//            File file = new File(directory, csvFile);
//            WorkbookSettings wbSettings = new WorkbookSettings();
//            wbSettings.setLocale(new Locale(Locale.US.getLanguage(), Locale.US.getCountry()));
//            WritableWorkbook workbook;
//            workbook = Workbook.createWorkbook(file, wbSettings);
//
//            for (int id = 0; id < listDataName.size(); id++){
//                Float totalLosses = Float.valueOf(0);
//                Iterator nameKey;
//                Iterator valueKey;
//                List listKey = new ArrayList<>();
//                List listKeyValue = new ArrayList();
//
//
//                JSONObject getJson = (JSONObject) retJson.get(listDataName.get(id).toString());
//                JSONObject getJson2 = new JSONObject();
//                nameKey = getJson.keys();
//
//
//                while (nameKey.hasNext()){
//                    listKey.add(nameKey.next().toString());
//                    Log.w("Key Name", nameKey.toString());
//                }
//
//
//                //Excel sheetA first sheetA
//                WritableSheet sheetA = workbook.createSheet(listDataName.get(id).toString(), id+1);
//
//
//                // column and row titles
//                sheetA.addCell(new Label(0, 0, "Tanggal"));
//                sheetA.addCell(new Label(0, 1, tanggal));
//                sheetA.addCell(new Label(1, 0, "Nama Kebun"));
//                sheetA.addCell(new Label(1, 1, nama));
//                sheetA.addCell(new Label(2, 0, "Sampel"));
//
//                for (int index = 0;index < listKey.size(); index++ ){
//                    getJson2 = (JSONObject) getJson.get(listKey.get(index).toString());
//                    if (index==0){
//                        valueKey = getJson2.keys();
//                        while (valueKey.hasNext()){
//                            listKeyValue.add(valueKey.next().toString());
//                            Log.w("Key Value Name", valueKey.toString());
//                        }
//                        for (int ind = 0; ind < listKeyValue.size(); ind++){
//                            sheetA.addCell(new Label(ind+3, 0, listKeyValue.get(ind).toString()));
//                        }
//                    }
//                    sheetA.addCell(new Label(2, index+1, listKey.get(index).toString()));
//
//                    for (int inde = 0; inde < getJson2.length(); inde++){
//                        sheetA.addCell(new Label(inde+3, index+1, getJson2.get(listKeyValue.get(inde).toString()).toString()));
//                        if (inde+1==getJson2.length()){
//                            totalLosses += Float.valueOf(getJson2.get(listKeyValue.get(inde).toString()).toString());
//                        }
//                    }
//
//                    if (index+1==listKey.size()){
//                        sheetA.addCell(new Label(2, index+2, "Total Losses"));
//                        sheetA.addCell(new Label(3, index+2, String.valueOf(totalLosses)));
//                        sheetA.mergeCells(3,index+2,8,index+2);
//                    }
//
//                }
//
//                for (int c=0; c<9; c++){
//                    CellView cell = sheetA.getColumnView(c);
//                    cell.setAutosize(true);
//                    sheetA.setColumnView(c, cell);
//                }
//
//                // close workbook
//            }
//            workbook.write();
//            workbook.close();
//            Toast.makeText(context, "File berhasil di buat", Toast.LENGTH_SHORT).show();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (WriteException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }

    @Override
    public int getItemCount() {
        return dataBerkas.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List filteredList = new ArrayList<>();

            if (constraint.toString().isEmpty()){
                filteredList.addAll(dataBerkasAll);
            }else{
                for (HashMap<String, String> value : dataBerkasAll){

                    if (value.get("nama").toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(value);
                    }else{
                        if (value.get("date").toLowerCase().contains(constraint.toString().toLowerCase())){
                            filteredList.add(value);
                        }
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.w("data", String.valueOf(dataBerkas.size()));
            Log.w("data", String.valueOf(results.values.toString()));
            dataBerkas.clear();
            dataBerkas.addAll((Collection) results.values);
            notifyDataSetChanged();
        }
    };



    class BerkasViewHolder extends RecyclerView.ViewHolder{

        TextView text1, text2, text3, id;
        LinearLayout rec;

        public BerkasViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.recview_text1);
            text2 = itemView.findViewById(R.id.recview_text2);
            text3 = itemView.findViewById(R.id.recview_text3);
            id = itemView.findViewById(R.id.id_berkas);
            rec = itemView.findViewById(R.id.id_recview);
        }
    }
}
