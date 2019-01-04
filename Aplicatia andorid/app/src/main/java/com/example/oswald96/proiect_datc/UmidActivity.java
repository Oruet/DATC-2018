package com.example.oswald96.proiect_datc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.*;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import cz.msebera.android.httpclient.Header;


public class UmidActivity extends AppCompatActivity {
    TextView txtData;
    ListView listadate;
    Spinner dropdown;
    int idSenzor, year, month, day, hour, minute, second;
    float temperatura;
    float umiditate;
    Calendar dataPreluare;
    String aux;
    List<String> dateSenzori = new ArrayList<String>();
    List<String> listdrop = new ArrayList<String>();
    List<String> aux2 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umid);
        txtData = (TextView) findViewById(R.id.txtdata);
        listadate = (ListView) findViewById(R.id.listadate);
        dropdown = (Spinner) findViewById(R.id.dropdown);
        //incepem sa luam jsonul
        //Toast.makeText(TempActivity.this, "OnSucces Call", Toast.LENGTH_SHORT).show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.1.7:1313/api/values", new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray success) {
                        super.onSuccess(statusCode, headers, success);
                        Toast.makeText(UmidActivity.this, "OnSucces Call", Toast.LENGTH_SHORT).show();
                        try{
                            final ArrayList<String> list = new ArrayList<String>();
                            for(int j = 0; j<success.length();j++)
                            {
                                String data = success.getString(j);
                                list.clear();
                                String[] date = data.split(",");
                                for (int i = 0; i < date.length; i++) {

                                    if (i == 2) {
                                        String[] aux = date[i].split(":");
                                        idSenzor = Integer.parseInt(aux[1]);
                                        listdrop.add("Senzorul "+ Integer.toString(idSenzor));
                                    }
                                    if (i == 3) {
                                        String[] aux = date[i].split(":");
                                        temperatura = Float.valueOf(aux[1]);
                                    }
                                    if (i == 4) {
                                        String[] aux = date[i].split(":");
                                        umiditate = Integer.parseInt(aux[1]);
                                    }
                                    if (i == 5) {
                                        year = Integer.parseInt(date[i].substring(13, 17));
                                        month = Integer.parseInt(date[i].substring(18, 20)) - 1;
                                        day = Integer.parseInt(date[i].substring(21, 23));
                                        hour = Integer.parseInt(date[i].substring(24, 26));
                                        minute = Integer.parseInt(date[i].substring(27, 29));
                                        second = Integer.parseInt(date[i].substring(30, 32));
                                        dataPreluare = new GregorianCalendar(year, month, day, hour, minute, second);

                                    }
                                    list.add(date[i]);
                                }
                                DateSenzori datefinale = new DateSenzori(idSenzor, temperatura, umiditate, dataPreluare);
                                dateSenzori.add(datefinale.toString(2));
                            }
                            ArrayAdapter adapter = new ArrayAdapter<String>(UmidActivity.this, android.R.layout.simple_list_item_1, dateSenzori);
                            listadate.setAdapter(adapter);
                            Set<String> set = new HashSet<>(listdrop);
                            listdrop.clear();
                            listdrop.addAll(set);
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(UmidActivity.this, android.R.layout.simple_spinner_item, listdrop);
                            dropdown.setAdapter(dataAdapter);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(UmidActivity.this, "OnFail Call", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                aux2.clear();
                aux2.addAll(DateSenzori.datesenzorumid(dropdown.getSelectedItem().toString()));
                ArrayAdapter adapter = new ArrayAdapter<String>(UmidActivity.this, android.R.layout.simple_list_item_1, aux2);
                listadate.setAdapter(adapter);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        Button btngraph = (Button) findViewById(R.id.buttongrafic);
        btngraph.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View view){
                                            Intent intent = new Intent(UmidActivity.this, UmidGraphActivity.class);
                                            intent.putExtra("mesaj", dropdown.getSelectedItem().toString());
                                            startActivity(intent);
                                        }
                                    }
        );
    }
}
