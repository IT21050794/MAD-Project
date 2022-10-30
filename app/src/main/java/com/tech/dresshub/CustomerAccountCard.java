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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech.dresshub.models.CardDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomerAccountCard extends AppCompatActivity {

    Button button;
    ListView listView;
    List<CardDetails> user;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_account_card);

        button = (Button)findViewById(R.id.addcard);
        listView = (ListView)findViewById(R.id.listview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerAccountCard.this, AddCard.class);
                startActivity(intent);
            }
        });

        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("CardDetails");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    CardDetails cardDetails = studentDatasnap.getValue(CardDetails.class);
                    user.add(cardDetails);
                }

                MyAdapter adapter = new MyAdapter(CustomerAccountCard.this, R.layout.custom_card_details, (ArrayList<CardDetails>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    static class ViewHolder {

        TextView COL1;
        TextView COL2;
        TextView COL3;
        TextView COL4;
        TextView COL5;
        Button button1;
        Button button2;
    }

    class MyAdapter extends ArrayAdapter<CardDetails> {
        LayoutInflater inflater;
        Context myContext;
        List<CardDetails> user;


        public MyAdapter(Context context, int resource, ArrayList<CardDetails> objects) {
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
                view = inflater.inflate(R.layout.custom_card_details, null);

                holder.COL1 = (TextView) view.findViewById(R.id.uname);
                holder.COL2 = (TextView) view.findViewById(R.id.ucard);
                holder.COL3 = (TextView) view.findViewById(R.id.uexpire);
                holder.COL4 = (TextView) view.findViewById(R.id.ucsv);
                holder.COL5 = (TextView) view.findViewById(R.id.ubank);
                holder.button1 = (Button) view.findViewById(R.id.edit);
                holder.button2 = (Button) view.findViewById(R.id.delete);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText("Name:- "+user.get(position).getOwner());
            holder.COL2.setText("Card No:- "+user.get(position).getCardNumber());
            holder.COL3.setText("Ex Date:- "+user.get(position).getExpireDate());
            holder.COL4.setText("Csv:- "+user.get(position).getCsv());
            holder.COL5.setText("Bank:- "+user.get(position).getBankName());
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
                                    FirebaseDatabase.getInstance().getReference("CardDetails").child(idd).removeValue();
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
                    View view1 = inflater.inflate(R.layout.custom_update_card_details, null);
                    dialogBuilder.setView(view1);

                    final EditText editText1 = (EditText) view1.findViewById(R.id.ownername_update);
                    final EditText editText2 = (EditText) view1.findViewById(R.id.cardnum_update);
                    final EditText editText3 = (EditText) view1.findViewById(R.id.expiredate_update);
                    final EditText editText4 = (EditText) view1.findViewById(R.id.cardcsv_update);
                    final EditText editText5 = (EditText) view1.findViewById(R.id.bankname_update);
                    final Button buttonupdate = (Button) view1.findViewById(R.id.updatebtn);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CardDetails").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = (String) snapshot.child("owner").getValue();
                            String cardnum = (String) snapshot.child("cardNumber").getValue();
                            String expire = (String) snapshot.child("expireDate").getValue();
                            String csv = (String) snapshot.child("csv").getValue();
                            String bank = (String) snapshot.child("bankName").getValue();

                            editText1.setText(name);
                            editText2.setText(cardnum);
                            editText3.setText(expire);
                            editText4.setText(csv);
                            editText5.setText(bank);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    buttonupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String Ownername = editText1.getText().toString();
                            String cardNum = editText2.getText().toString();
                            String expireDate = editText3.getText().toString();
                            String Csv = editText4.getText().toString();
                            String bank = editText5.getText().toString();

                            if (Ownername.isEmpty()) {
                                editText1.setError("Name is required");
                            }else if (cardNum.isEmpty()) {
                                editText2.setError("Card Number is required");
                            }else if (expireDate.isEmpty()) {
                                editText3.setError("Expire date Number is required");
                            }else if (Csv.isEmpty()) {
                                editText4.setError("Csv is required");
                            }else if (bank.isEmpty()) {
                                editText5.setError("Bank name is required");
                            }else {

                                HashMap map = new HashMap();
                                map.put("owner", Ownername);
                                map.put("cardNumber", cardNum);
                                map.put("expireDate", expireDate);
                                map.put("csv", Csv);
                                map.put("bankName", bank);
                                reference.updateChildren(map);

                                Toast.makeText(CustomerAccountCard.this, "Updated successfully", Toast.LENGTH_SHORT).show();

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
        Intent intent = new Intent(CustomerAccountCard.this, CustomerHome.class);
        startActivity(intent);
    }
}
