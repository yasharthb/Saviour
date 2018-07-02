package com.saviour.yasharth.saviour;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class selContacts extends AppCompatActivity {

    ListView simpleList;
    //String countryList[] = {"India", "China", "Australia", "Portugal", "America", "New Zealand"};

    FloatingActionButton select2;
    public final int PICK_CONTACT = 2015;

    @Override   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      setContentView(R.layout.activity_sel_contacts);
        simpleList = (ListView)findViewById(R.id.simpleListView);
      //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView8, countryList);
        //simpleList.setAdapter(arrayAdapter);
        select2 = (FloatingActionButton) findViewById(R.id.button2);

        select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(i, PICK_CONTACT);
            }
        });






        String phn1[]= new String[100];




        int i = 0;
        try {
            FileInputStream fIn = openFileInput("emergencyNames.txt");
            BufferedReader myReader = new BufferedReader( new InputStreamReader(fIn));
                //phn1[0]= null;
                while(i<100) {

                    phn1[i] = myReader.readLine();

                    if(phn1[i]==null) {
                        phn1[i]= "Add";
                        break;
                    }
                    i++;
                }
            myReader.close();


            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView8, phn1);
            //simpleList.setAdapter(arrayAdapter);

        }
        catch(Exception e){

        }
        //String countryList[] = {"India", "China", "australia", "Portugal", "America", "NewZealand"};
       // countryList[0] = phn1[0];
        //countryList[1] = phn1[1];
        //countryList[2] = phn1[2];



        String phn2[]=new String[i];

        for ( i = 0; i<phn2.length;i++) {
            phn2[i]=phn1[i];
        }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView8, phn2);
        simpleList.setAdapter(arrayAdapter);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
             String contactNumberName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            try {
//                            File myFile = new File(emergencyNumbers.txt");
//                            myFile.createNewFile();
                FileOutputStream fOut = openFileOutput("emergencyNumbers.txt",MODE_PRIVATE-MODE_APPEND);
                FileOutputStream fOut2 = openFileOutput("emergencyNames.txt",MODE_PRIVATE-MODE_APPEND);

                OutputStreamWriter myOutWriter1 =
                        new OutputStreamWriter(fOut2);

                OutputStreamWriter myOutWriter =
                        new OutputStreamWriter(fOut);
               // myOutWriter.write(checkedcontacts.toString());
                myOutWriter.write(cursor.getString(column)+"\n");

                myOutWriter1.write(contactNumberName+"\n");
                myOutWriter.close();
                fOut.close();
                myOutWriter1.close();
                fOut2.close();

                startActivity(new Intent(selContacts.this,selContacts.class));
                finish();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
