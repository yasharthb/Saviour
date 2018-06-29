package com.saviour.yasharth.saviour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class selContacts extends AppCompatActivity {

    ListView simpleList;
    //String countryList[] = {"India", "China", "Australia", "Portugal", "America", "New Zealand"};

    @Override   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      setContentView(R.layout.activity_sel_contacts);
        simpleList = (ListView)findViewById(R.id.simpleListView);
      //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView8, countryList);
        //simpleList.setAdapter(arrayAdapter);

        String phn1[]= new String[10];
        int i=0;
        for ( i = 0; i<10;i++)
            phn1[i] = "Add";


        i = 0;
        try {
            FileInputStream fIn = openFileInput("emergencyNumbers.txt");
            BufferedReader myReader = new BufferedReader( new InputStreamReader(fIn));
                //phn1[0]= null;
                while(i<10) {

                    if(phn1[i]==null) {
                        phn1[i]= "Add";
                        break;
                    }
                    phn1[i] = myReader.readLine();
                    i++;
                }
            myReader.close();


            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView8, phn1);
            //simpleList.setAdapter(arrayAdapter);

        }
        catch(Exception e){

        }
        String countryList[] = {"India", "China", "australia", "Portugal", "America", "NewZealand"};
       // countryList[0] = phn1[0];
        //countryList[1] = phn1[1];
        //countryList[2] = phn1[2];

        for ( i = 0; i<10;i++) {
            if(phn1[i]==null)
                phn1[i] = "Add";
        }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView8, phn1);
        simpleList.setAdapter(arrayAdapter);


    }
}
