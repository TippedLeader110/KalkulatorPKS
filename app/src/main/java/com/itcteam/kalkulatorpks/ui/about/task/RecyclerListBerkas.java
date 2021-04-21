package com.itcteam.kalkulatorpks.ui.about.task;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itcteam.kalkulatorpks.R;
import com.itcteam.kalkulatorpks.db.DatabaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class RecyclerListBerkas extends RecyclerView.Adapter<RecyclerListBerkas.BerkasViewHolder> {

    List dataBerkas;
    Context context;
    DatabaseHandler databaseHandler;
    int tipe;

    public RecyclerListBerkas(Context context, List data, int tipe) {
        this.context = context;
        this.dataBerkas = data;
        databaseHandler = new DatabaseHandler(context);
        this.tipe = tipe;
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

        if (tipe==1){
            String berkasObj = databaseHandler.getItemValue(value.get("id_record"));;
            String title, matang, date;
            date = value.get("date");
            Log.e("EEE", "MASOK : " + berkasObj);
            try {
                JSONObject jsonObject = new JSONObject(berkasObj);
                title = jsonObject.getString("nama");
                matang = jsonObject.getString("matang");
                holder.text1.setText(title);
                holder.text2.setText("Faksi Matang : " + matang);
                holder.text3.setText(date);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        holder.rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "TERTEKAN ID = " + value.get("id_record"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBerkas.size();
    }

    class BerkasViewHolder extends RecyclerView.ViewHolder{

        TextView text1, text2, text3, id;
        RelativeLayout rec;

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
