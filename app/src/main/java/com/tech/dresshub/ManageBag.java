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
import com.tech.dresshub.models.Cart;
import com.tech.dresshub.models.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManageBag extends AppCompatActivity {

    ListView listView;
    List<Cart> user;
    DatabaseReference ref;
    String pprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bag);

        listView = (ListView) findViewById(R.id.baglist);
        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("MyBag");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    Cart cart = studentDatasnap.getValue(Cart.class);
                    user.add(cart);

                }

                MyAdapter adapter = new MyAdapter(ManageBag.this, R.layout.custom_bagitem_list, (ArrayList<Cart>) user);
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
        Button button3;
        TextView COL1;
        TextView COL2;
        TextView COL3;
        TextView COL4;
        TextView COL5;
        TextView COL6;
        TextView COL7;
    }

    class MyAdapter extends ArrayAdapter<Cart> {
        LayoutInflater inflater;
        Context myContext;
        List<Cart> user;


        public MyAdapter(Context context, int resource, ArrayList<Cart> objects) {
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
                view = inflater.inflate(R.layout.custom_bagitem_list, null);

                holder.COL1 = (TextView) view.findViewById(R.id.typebag);
                holder.COL2 = (TextView) view.findViewById(R.id.namebag);
                holder.COL3 = (TextView) view.findViewById(R.id.qtybag);
                holder.COL4 = (TextView) view.findViewById(R.id.sizebag);
                holder.COL5 = (TextView) view.findViewById(R.id.prizebag);
                holder.COL6 = (TextView) view.findViewById(R.id.addressbag);
                holder.COL7 = (TextView) view.findViewById(R.id.contactbag);
                holder.imageView1 = (ImageView) view.findViewById(R.id.imagebag);
                holder.button1 = (Button) view.findViewById(R.id.updatebag);
                holder.button2 = (Button) view.findViewById(R.id.deletebag);
                holder.button3 = (Button) view.findViewById(R.id.paybag);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText("Dress Type :- "+user.get(position).getType());
            holder.COL2.setText("Dress Name :- "+user.get(position).getName());
            holder.COL3.setText("Dress Qty :- "+user.get(position).getQty());
            holder.COL4.setText("Dress Size :- "+user.get(position).getSize());
            holder.COL5.setText("Price :- "+user.get(position).getPrize());
            holder.COL6.setText("Address :- "+user.get(position).getAddress());
            holder.COL7.setText("Contact :- "+user.get(position).getContact());
            Picasso.get().load(user.get(position).getImageURL()).into(holder.imageView1);
            System.out.println(holder);

            holder.button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mainIntent = new Intent(ManageBag.this, SelectPaymentMethod.class);
                    ManageBag.this.startActivity(mainIntent);
                    ManageBag.this.finish();
                }
            });


            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to delete this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    final String idd = user.get(position).getId();
                                    FirebaseDatabase.getInstance().getReference("MyBag").child(idd).removeValue();
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
                    View view1 = inflater.inflate(R.layout.custom_bagitems_update, null);
                    dialogBuilder.setView(view1);

                    final EditText editText1 = (EditText) view1.findViewById(R.id.qtyup);
                    final EditText editText2 = (EditText) view1.findViewById(R.id.sizecusup);
                    final EditText editText3 = (EditText) view1.findViewById(R.id.addressup);
                    final EditText editText4 = (EditText) view1.findViewById(R.id.contactup);
                    final TextView textView = (TextView) view1.findViewById(R.id.totalcalup);
                    final Button button1 = (Button) view1.findViewById(R.id.calup);
                    final Button buttonupdate = (Button) view1.findViewById(R.id.sendup);
                    final int[] total = new int[1];

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("MyBag").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String dqty = (String) snapshot.child("qty").getValue();
                            String dsize = (String) snapshot.child("size").getValue();
                            String daddress = (String) snapshot.child("address").getValue();
                            String dcontact = (String) snapshot.child("contact").getValue();
                            String dprice = (String) snapshot.child("prize").getValue();
                            pprice = (String) snapshot.child("productPrice").getValue();

                            editText1.setText(dqty);
                            editText2.setText(dsize);
                            editText3.setText(daddress);
                            editText4.setText(dcontact);
                            textView.setText(dprice);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (editText1.getText().toString().isEmpty()){
                                Toast.makeText(ManageBag.this, "Quantity is Required", Toast.LENGTH_SHORT).show();
                            }else{
                                int qty = Integer.parseInt(editText1.getText().toString());
                                int intprice = Integer.parseInt(pprice);

                                total[0] = intprice*qty;

                                textView.setText(Integer.toString(total[0]));
                            }

                        }
                    });


                    buttonupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String dqty = editText1.getText().toString();
                            String dsize = editText2.getText().toString();
                            String daddress = editText3.getText().toString();
                            String dcontact = editText4.getText().toString();

                            if (dqty.isEmpty()) {
                                editText1.setError("Quantity is required");
                            }else if (dsize.isEmpty()) {
                                editText2.setError("Size is required");
                            }else if (daddress.isEmpty()) {
                                editText3.setError("Address is required");
                            }else if (dcontact.isEmpty()) {
                                editText4.setError("Contact is required");
                            } else {

                                HashMap map = new HashMap();
                                map.put("qty", dqty);
                                map.put("size", dsize);
                                map.put("prize", textView.getText().toString());
                                map.put("address", daddress);
                                map.put("contact", dcontact);
                                reference.updateChildren(map);

                                Toast.makeText(ManageBag.this, "Updated successfully", Toast.LENGTH_SHORT).show();

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
        Intent intent = new Intent(ManageBag.this, CustomerHome.class);
        startActivity(intent);
    }
}