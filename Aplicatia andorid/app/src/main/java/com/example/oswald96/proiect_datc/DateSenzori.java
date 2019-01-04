package com.example.oswald96.proiect_datc;

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DateSenzori {

    private int idSenzor;
    private float temperatura;
    private float umiditate;
    private Calendar dataPreluare;
    private static List<String> listadate1 = new ArrayList<String>();
    private static List<String> listadate2 = new ArrayList<String>();
    private static List<String> listadate3 = new ArrayList<String>();

    public DateSenzori(int idSenzor, float temperatura, float umiditate, Calendar dataPreluare)
    {
        this.idSenzor = idSenzor;
        this.temperatura = temperatura;
        this.umiditate = umiditate;
        this.dataPreluare = dataPreluare;
        Set<String> set = new HashSet<>(listadate1);
        listadate1.clear();
        listadate1.addAll(set);
        listadate1.add(this.toString(1));
        Set<String> set2 = new HashSet<>(listadate2);
        listadate2.clear();
        listadate2.addAll(set2);
        listadate2.add(this.toString(2));
        Set<String> set3 = new HashSet<>(listadate3);
        listadate3.clear();
        listadate3.addAll(set3);
        listadate3.add(this.toString(1));
    }

    public String toString(int x) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = dateFormat.format(dataPreluare.getTime());
        if(x == 1)
        {
            return "Senzorul "+ Integer.toString(idSenzor) + " temperatura: "+ Float.toString(temperatura) + " data de preluare " + strDate;
        }
        else if( x == 2)
        {
            return "Senzorul "+ Integer.toString(idSenzor) + " cu umiditatea: "+  Float.toString(umiditate) + "% data de preluare " + strDate ;
        }
        else
        {
            return "Senzorul"+ idSenzor + " cu temperatura: "+ temperatura + " cu umiditatea: "+ umiditate + " si cu data de preluare " + dataPreluare ;
        }
    }

    public static List<String> datesenzortemp(String idSenzordindrp)
    {
        List<String> aux2 = new ArrayList<String>();
        aux2.clear();
        for (String element : listadate1) {
            if(element.substring(0, 10).equals(idSenzordindrp))
            {
                aux2.add(element);
            }
        }
        Set<String> set = new HashSet<>(aux2);
        aux2.clear();
        aux2.addAll(set);
        return aux2;
    }

    public static List<String> datesenzorumid(String idSenzordindrp)
    {
        List<String> aux2 = new ArrayList<String>();
        aux2.clear();
        for (String element : listadate2) {
            if(element.substring(0, 10).equals(idSenzordindrp))
            {
                aux2.add(element);
            }
        }
        Set<String> set = new HashSet<>(aux2);
        aux2.clear();
        aux2.addAll(set);
        return aux2;
    }
}
