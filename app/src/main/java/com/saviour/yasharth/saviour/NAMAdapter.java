package com.saviour.yasharth.saviour;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class NAMAdapter extends RecyclerView.Adapter<NAMAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private List<EmergencyContacts> objectList;
    Context context ;

    public NAMAdapter(Context context, List<EmergencyContacts> objectList) {
        inflater = LayoutInflater.from(context);
        this.objectList = objectList;
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView phone;
        private ImageView imgDelete;
        private int position;
        private EmergencyContacts currentObject;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nametv);
            phone = (TextView) itemView.findViewById(R.id.textph);
            imgDelete = (ImageView) itemView.findViewById(R.id.delete);
        }

        public void setdata(EmergencyContacts current, int position) {
            this.name.setText(current.getName());
            this.phone.setText(current.getPhoneno());
            this.position = position;
            this.currentObject = current;
        }

    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_layout,parent,false);
        MyViewHolder holder =  new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final EmergencyContacts current = objectList.get(position);
        holder.setdata(current,position);

        holder.imgDelete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)  {

                try {
                    removeItem(position);
                    //selContacts.removeContact(current.getName());
                }
                catch (Exception e){

                }
                return true;
            }
        });

    }

    private void removeItem(int curposition)throws Exception{

        String phone = objectList.get(curposition).getName();



        String name1[] = new String[100];

        String phn1[]= new String[100];

        int i = 0;
        try {
            FileInputStream fIn = context.openFileInput("emergencyNumbers.txt");
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

            FileInputStream fIn2 = context.openFileInput("emergencyNames.txt");
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

            }
        }


        FileOutputStream fOut = context.openFileOutput("emergencyNumbers.txt",context.MODE_PRIVATE);
        FileOutputStream fOut1 = context.openFileOutput("emergencyNames.txt",context.MODE_PRIVATE);

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




        objectList.remove(curposition);
        notifyItemRemoved(curposition);
        notifyItemRangeChanged(curposition, objectList.size());
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }
}
