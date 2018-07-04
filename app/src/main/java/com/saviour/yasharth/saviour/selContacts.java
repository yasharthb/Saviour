package com.saviour.yasharth.saviour;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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

   // ListView simpleList;
    //String countryList[] = {"India", "China", "Australia", "Portugal", "America", "New Zealand"};

    FloatingActionButton select2;
    public final int PICK_CONTACT = 2015;

    @Override   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_contacts);
     //
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);




        select2 = (FloatingActionButton) findViewById(R.id.button2);

        select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(i, PICK_CONTACT);
            }
        });





        String name1[] = new String[100];

        List<EmergencyContacts> datalist = new ArrayList<>();
        String phn1[]= new String[100];
        int j;int i = 0;
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
            fIn.close();


            i =0;

            FileInputStream fIn2 = openFileInput("emergencyNumbers.txt");
            BufferedReader myReader2 = new BufferedReader( new InputStreamReader(fIn2));
            //phn1[0]= null;
            while(i<100) {

                name1[i] = myReader2.readLine();

                if(name1[i]==null) {
                    name1[i]= "Add";
                    break;
                }
                i++;
            }
            myReader2.close();
            fIn2.close();


            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView8, phn1);
            //simpleList.setAdapter(arrayAdapter);

        }
        catch(Exception e){

        }

        j = i;
        for (int k=0;k<j;k++){
            EmergencyContacts contact = new EmergencyContacts();
            contact.setName(phn1[k]);
            contact.setPhoneno(name1[k]);
            datalist.add(contact);
        }





        //  simpleList = (ListView)findViewById(R.id.simpleListView);
      //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView8, countryList);
        //simpleList.setAdapter(arrayAdapter);





        NAMAdapter adapter = new NAMAdapter(this,datalist);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutmanager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

              //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView8, phn2);
        //simpleList.setAdapter(arrayAdapter);
    }

    public void removeContact(String phone)throws Exception{

        Toast.makeText(this,phone+" here",Toast.LENGTH_SHORT).show();

        String name1[] = new String[100];

        String phn1[]= new String[100];

        int i = 0;
        try {
            FileInputStream fIn = openFileInput("emergencyNumbers.txt");
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
            fIn.close();


            i = 0;

            FileInputStream fIn2 = openFileInput("emergencyNames.txt");
            BufferedReader myReader2 = new BufferedReader( new InputStreamReader(fIn2));
            //phn1[0]= null;
            while(i<100) {

                name1[i] = myReader2.readLine();

                if(name1[i]==null) {
                    name1[i]= "Add";
                    break;
                }
                i++;
            }
            myReader2.close();
            fIn2.close();


            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView8, phn1);
            //simpleList.setAdapter(arrayAdapter);

        }
        catch(Exception e){

        }



        StringBuilder checkedNames= new StringBuilder();
        StringBuilder checkedNumbers= new StringBuilder();


        for (int q = 0; q < i; q++){

            if(phone.compareTo(name1[q])!=0){


                checkedNames.append(name1[q]);
                checkedNumbers.append(phn1[q]);

                checkedNames.append("\n");
                checkedNumbers.append("\n");

            }
            else {
                Toast.makeText(this,phone,Toast.LENGTH_SHORT).show();
            }
        }


        FileOutputStream fOut = openFileOutput("emergencyNumbers.txt",MODE_PRIVATE);
        FileOutputStream fOut1 = openFileOutput("emergencyNames.txt",MODE_PRIVATE);

        OutputStreamWriter myOutWriter =
                new OutputStreamWriter(fOut);

        OutputStreamWriter myOutWriter1 =
                new OutputStreamWriter(fOut1);

        myOutWriter.write(checkedNumbers.toString());
        myOutWriter1.write(checkedNames.toString());


        myOutWriter.close();
        fOut.close();
        myOutWriter1.close();
        fOut1.close();

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
