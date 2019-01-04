package com.example.oswald96.proiect_datc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UmidGraphActivity extends AppCompatActivity {
    List<String> datesenzor = new ArrayList<String>();
    List<Float> aux = new ArrayList<Float>();
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umid_graph);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String drpelement = intent.getExtras().getString("mesaj");

        datesenzor.addAll(DateSenzori.datesenzorumid(drpelement));

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
        });
        for (String element : datesenzor) {
            i++;
            series.appendData(new DataPoint(i, Float.valueOf(element.substring(26, 28))), true, datesenzor.size());

        }
        graph.addSeries(series);

    }
}
