package com.tech.dresshub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tech.dresshub.models.Events;
import com.tech.dresshub.models.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewEvents extends AppCompatActivity {

    ListView listView;
    List<Events> user;
    DatabaseReference ref;
    String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);

        listView = (ListView) findViewById(R.id.eventlist);
        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("Events");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    Events events = studentDatasnap.getValue(Events.class);
                    user.add(events);

                }

                MyAdapter adapter = new MyAdapter(ViewEvents.this, R.layout.custom_event_list, (ArrayList<Events>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    static class ViewHolder {

        ImageView imageView1;
        Button button1;
        Button button2;
        TextView COL1;
        TextView COL2;
        TextView COL3;

    }

    class MyAdapter extends ArrayAdapter<Events> {
        LayoutInflater inflater;
        Context myContext;
        List<Events> user;


        public MyAdapter(Context context, int resource, ArrayList<Events> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.custom_event_list, null);

                holder.COL1 = (TextView) view.findViewById(R.id.startc);
                holder.COL2 = (TextView) view.findViewById(R.id.endc);
                holder.COL3 = (TextView) view.findViewById(R.id.desc);
                holder.imageView1 = (ImageView) view.findViewById(R.id.imagegetc);
                holder.button1 = (Button) view.findViewById(R.id.updatec);
                holder.button2 = (Button) view.findViewById(R.id.deletec);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText("Event Start Date :- "+user.get(position).getStartDate());
            holder.COL2.setText("Event End Date :- "+user.get(position).getEndDate());
            holder.COL3.setText("Description :- "+user.get(position).getDescription());
            Picasso.get().load(user.get(position).getImage()).into(holder.imageView1);
            System.out.println(holder);


            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to delete this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    final String idd = user.get(position).getId();
                                    FirebaseDatabase.getInstance().getReference("Events").child(idd).removeValue();
                                    //remove function not written
                                    Toast.makeText(myContext, "Item deleted successfully", Toast.LENGTH_SHORT).show();

                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            });

            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view1 = inflater.inflate(R.layout.custom_event_update, null);
                    dialogBuilder.setView(view1);

                    final EditText editText1 = (EditText) view1.findViewById(R.id.startdup);
                    final EditText editText2 = (EditText) view1.findViewById(R.id.enddup);
                    final EditText editText3 = (EditText) view1.findViewById(R.id.descriptionup);
                    final Button buttonupdate = (Button) view1.findViewById(R.id.submiteventup);
                    final ImageView imageView = (ImageView) view1.findViewById(R.id.eventimageup);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String start = (String) snapshot.child("startDate").getValue();
                            String end = (String) snapshot.child("endDate").getValue();
                            String description = (String) snapshot.child("description").getValue();
                            String image = (String) snapshot.child("image").getValue();

                            editText1.setText(start);
                            editText2.setText(end);
                            editText3.setText(description);
                            Picasso.get().load(image).into(imageView);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    buttonupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String startDAte = editText1.getText().toString();
                            String endDate = editText2.getText().toString();
                            String description = editText3.getText().toString();

                            if (startDAte.isEmpty()) {
                                editText1.setError("Start date is required");
                            }else if (endDate.isEmpty()) {
                                editText2.setError("End date is required");
                            }else if (description.isEmpty()) {
                                editText3.setError("Description is required");
                            }else {


                                HashMap map = new HashMap();
                                map.put("startDate", startDAte);
                                map.put("endDate", endDate);
                                map.put("description", description);
                                reference.updateChildren(map);

                                Toast.makeText(ViewEvents.this, "Updated successfully", Toast.LENGTH_SHORT).show();

                                alertDialog.dismiss();
                            }
                        }
                    });
                }
            });

            return view;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewEvents.this, SellerHome.class);
        startActivity(intent);
    }
}