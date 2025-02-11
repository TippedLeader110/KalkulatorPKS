package com.itcteam.kalkulatorpks.ui.about.perhitungan_rendemen;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.util.DatabaseHandler;
import com.itcteam.kalkulatorpks.ui.about.ExportCSV;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung04_simpan;
import com.itcteam.kalkulatorpks.util.DateTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RecyclerListBerkas_pr extends RecyclerView.Adapter<RecyclerListBerkas_pr.BerkasViewHolder> implements Filterable {

    List dataBerkas;
    List<HashMap<String, String>> dataBerkasAll;
    Context context;
    DatabaseHandler databaseHandler;
    int tipe;
    String start, end;
    Boolean export;

    public RecyclerListBerkas_pr(Context context, List data, int tipe, Boolean export) {
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
        String msgDate = "";

        try {
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String startString = df.format(c);
            String[] spltDate = value.get("date").split("\\s+");

            Date end = new SimpleDateFormat("yyyy-MM-dd").parse(spltDate[0]);
            Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startString);
            DateTools dateTools = new DateTools(start, end);
            int dateCount = dateTools.getDaysDifference();

            if (dateCount<0){
                dateCount *=-1;
                msgDate = dateCount + " hari yang lalu";
            }else if(dateCount==0){
                msgDate = "Hari ini";
            }else{
                msgDate = dateCount + " hari yang lalu";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.text1.setText(value.get("nama"));
        holder.text3.setText(msgDate);
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

                    String linesC =  "Tanggal,Nama Kebun,CPO,Inti,Storage\n";
                    try {
                        String fetchData = databaseHandler.getRecordValue(value.get("id_record"), tipe);
                        JSONObject jsonBerkas = new JSONObject(fetchData);
                        String lines = value.get("date")+ "," +
                                value.get("nama")+ "," +
                                jsonBerkas.getString("cpo")+ "," +
                                jsonBerkas.getString("inti")+ "," +
                                jsonBerkas.getString("storage");

                        Log.w("Lines", lines);
                        Log.w("LinesC", linesC);
                        dataLines.add(linesC);
                        dataLines.add(lines);

                        ExportCSV exportCSV = new ExportCSV(dataLines, context);
                        exportCSV.DoExportCSV("PerhitunganRendemenSingle");
                        Toast.makeText(context, "Berhasil di export", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.w("HolderREC","false");
                    try {
                        String fetchData = databaseHandler.getRecordValue(value.get("id_record"), tipe);
                        JSONObject jsonBerkas = new JSONObject(fetchData);
                        Intent intent = new Intent(context, Hitung04_simpan.class);
                        intent.putExtra("cpo", jsonBerkas.getString("cpo"));
                        intent.putExtra("inti", jsonBerkas.getString("inti"));
                        intent.putExtra("storage", jsonBerkas.getString("storage"));
                        intent.putExtra("id", value.get("id_record").toString());
                        intent.putExtra("hide", "yes");
                        v.getContext().startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

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
