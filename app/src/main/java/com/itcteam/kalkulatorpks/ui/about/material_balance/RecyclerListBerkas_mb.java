package com.itcteam.kalkulatorpks.ui.about.material_balance;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.db.DatabaseHandler;
import com.itcteam.kalkulatorpks.ui.calculate.task.task.Hitung01_hasil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class RecyclerListBerkas_mb extends RecyclerView.Adapter<RecyclerListBerkas_mb.BerkasViewHolder> implements Filterable {

    List dataBerkas;
    List<String> dataNama;
    List<HashMap<String, String>> dataBerkasAll;
    Context context;
    DatabaseHandler databaseHandler;
    int tipe;
    String start, end;

    public RecyclerListBerkas_mb(Context context, List data, int tipe) {
        this.context = context;
        databaseHandler = new DatabaseHandler(context);
        this.tipe = tipe;
        dataBerkas = new ArrayList();
        dataBerkasAll = new ArrayList<>();
        prosesUlang(data);
    }

    public void triggerDate(List data){
        dataBerkas.clear();
        dataBerkasAll.clear();
        prosesUlang(data);
        this.notifyDataSetChanged();
    }

    public void prosesUlang(List data){

        for (int i = 0; i<data.size();i++ ){
            final HashMap<String,String> value = (HashMap<String, String>) data.get(i);
            String berkasObj = databaseHandler.getItemValue(value.get("id_record"));;
            String title, matang, date;
            date = value.get("date");
            Log.e("EEE", "MASOK : " + berkasObj);
            try {
                JSONObject jsonObject = new JSONObject(berkasObj);
                title = jsonObject.getString("nama");
                matang = jsonObject.getString("matang");
                HashMap<String,String> nvalue = new HashMap<String, String>();
                nvalue.put("nama", title);
                nvalue.put("id_record", value.get("id_record"));
                nvalue.put("matang", matang);
                nvalue.put("date", date);
                dataBerkas.add(nvalue);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.dataBerkasAll = dataBerkas;
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

        holder.text1.setText(value.get("title"));
        holder.text2.setText("Faksi Matang : " + value.get("matang"));
        holder.text3.setText(value.get("date"));

        holder.rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String fetchData = databaseHandler.getRecordValue(value.get("id_record"));
                    JSONObject jsonBerkas = new JSONObject(fetchData);
                    Intent intent = new Intent(context, Hitung01_hasil.class);
                    intent.putExtra("tbs", jsonBerkas.getString("tbs"));
                    intent.putExtra("tangkosHasil", jsonBerkas.getString("tangkosHasil"));
                    intent.putExtra("tangkosHasilp", jsonBerkas.getString("tangkosHasilp"));
                    intent.putExtra("seratHasil", jsonBerkas.getString("seratHasil"));
                    intent.putExtra("seratHasilp", jsonBerkas.getString("seratHasilp"));
                    intent.putExtra("cangkangHasil", jsonBerkas.getString("cangkangHasil"));
                    intent.putExtra("cangkangHasilp", jsonBerkas.getString("cangkangHasilp"));
                    intent.putExtra("intiHasil", jsonBerkas.getString("intiHasil"));
                    intent.putExtra("intiHasilp", jsonBerkas.getString("intiHasilp"));
                    intent.putExtra("cpoHasil", jsonBerkas.getString("cpoHasil"));
                    intent.putExtra("cpoHasilp", jsonBerkas.getString("cpoHasilp"));
                    intent.putExtra("dirtHasil", jsonBerkas.getString("dirtHasil"));
                    intent.putExtra("dirtHasilp", jsonBerkas.getString("dirtHasilp"));
                    intent.putExtra("hide", "yes");

                    v.getContext().startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
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

            List filteredList = new ArrayList();

            if (constraint.toString().isEmpty()){
                filteredList.addAll(dataBerkasAll);
            }else{
                for (HashMap<String,String> value : dataBerkasAll){
                    if (value.get("title").toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(value);
                    }else{
                        if (value.get("date").toLowerCase().contains(constraint.toString().toLowerCase())){
                            filteredList.add(value);
                        }else{
                            if (value.get("matang").toLowerCase().contains(constraint.toString().toLowerCase())){
                                filteredList.add(value);
                            }
                        }
                    }
                }
            }

            FilterResults val = new FilterResults();
            val.values = filteredList;

            return val;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataBerkas.clear();
            dataBerkas.addAll((Collection) results.values);
            notifyDataSetChanged();
        }
    };

    Filter dateFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List filteredList = new ArrayList();

            if (constraint.toString().isEmpty()){
                filteredList.addAll(dataBerkasAll);
            }else{
                for (HashMap<String,String> value : dataBerkasAll){
                    if (value.get("date").toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(value);
                    }
                }
            }

            FilterResults val = new FilterResults();
            val.values = filteredList;

            return val;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
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
