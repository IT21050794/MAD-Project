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
import com.tech.dresshub.models.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewProducts extends AppCompatActivity {

    ListView listView;
    List<Products> user;
    DatabaseReference ref;
    String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);

        listView = (ListView) findViewById(R.id.productslist);
        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("Products");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    Products products = studentDatasnap.getValue(Products.class);
                    user.add(products);

                }

                MyAdapter adapter = new MyAdapter(ViewProducts.this, R.layout.custom_product_list, (ArrayList<Products>) user);
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
        TextView COL4;
        TextView COL5;
        TextView COL6;
    }

    class MyAdapter extends ArrayAdapter<Products> {
        LayoutInflater inflater;
        Context myContext;
        List<Products> user;


        public MyAdapter(Context context, int resource, ArrayList<Products> objects) {
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
                view = inflater.inflate(R.layout.custom_product_list, null);

                holder.COL1 = (TextView) view.findViewById(R.id.type);
                holder.COL2 = (TextView) view.findViewById(R.id.name);
                holder.COL3 = (TextView) view.findViewById(R.id.metireal);
                holder.COL4 = (TextView) view.findViewById(R.id.strech);
                holder.COL5 = (TextView) view.findViewById(R.id.size);
                holder.COL6 = (TextView) view.findViewById(R.id.price);
                holder.imageView1 = (ImageView) view.findViewById(R.id.imageget);
                holder.button1 = (Button) view.findViewById(R.id.update);
                holder.button2 = (Button) view.findViewById(R.id.delete);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText("Dress type :- "+user.get(position).getType());
            holder.COL2.setText("Dress Name :- "+user.get(position).getName());
            holder.COL3.setText("Dress Matireal :- "+user.get(position).getMatireal());
            holder.COL4.setText("Dress Streachable :- "+user.get(position).getStreachable());
            holder.COL5.setText("Dress Size :- "+user.get(position).getSize());
            holder.COL6.setText("Dress Price :- "+user.get(position).getPrize());
            Picasso.get().load(user.get(position).getImageURL()).into(holder.imageView1);
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
                                    FirebaseDatabase.getInstance().getReference("Products").child(idd).removeValue();
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
                    View view1 = inflater.inflate(R.layout.custom_product_update, null);
                    dialogBuilder.setView(view1);

                    final EditText editText1 = (EditText) view1.findViewById(R.id.dtype);
                    final EditText editText2 = (EditText) view1.findViewById(R.id.dname);
                    final EditText editText3 = (EditText) view1.findViewById(R.id.dmetireal);
                    final EditText editText4 = (EditText) view1.findViewById(R.id.dstrech);
                    final EditText editText5 = (EditText) view1.findViewById(R.id.dsize);
                    final EditText editText6 = (EditText) view1.findViewById(R.id.dprice);
                    final Button buttonupdate = (Button) view1.findViewById(R.id.dsubmit);
                    final ImageView imageView = (ImageView) view1.findViewById(R.id.imageView3);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String dtype = (String) snapshot.child("type").getValue();
                            String dname = (String) snapshot.child("name").getValue();
                            String dmetireal = (String) snapshot.child("matireal").getValue();
                            String dstrech = (String) snapshot.child("streachable").getValue();
                            String dsize = (String) snapshot.child("size").getValue();
                            String dprice = (String) snapshot.child("prize").getValue();
                            String image = (String) snapshot.child("imageURL").getValue();

                            editText1.setText(dtype);
                            editText2.setText(dname);
                            editText3.setText(dmetireal);
                            editText4.setText(dstrech);
                            editText5.setText(dsize);
                            editText6.setText(dprice);
                            Picasso.get().load(image).into(imageView);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    buttonupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String dtype = editText1.getText().toString();
                            String dname = editText2.getText().toString();
                            String dmetireal = editText3.getText().toString();
                            String dstrech = editText4.getText().toString();
                            String dsize = editText5.getText().toString();
                            String dprice = editText6.getText().toString();

                            if (dtype.isEmpty()) {
                                editText1.setError("Type is required");
                            }else if (dname.isEmpty()) {
                                editText2.setError("Name is required");
                            }else if (dmetireal.isEmpty()) {
                                editText3.setError("Metireal is required");
                            }else if (dstrech.isEmpty()) {
                                editText4.setError("Streach is required");
                            }else if (dsize.isEmpty()) {
                                editText5.setError("Size is required");
                            }
                            else if (dprice.isEmpty()) {
                                editText6.setError("Price is required");
                            }else {

                                HashMap map = new HashMap();
                                map.put("type", dtype);
                                map.put("name", dname);
                                map.put("matireal", dmetireal);
                                map.put("streachable", dstrech);
                                map.put("size", dsize);
                                map.put("prize", dprice);
                                reference.updateChildren(map);

                                Toast.makeText(ViewProducts.this, "Updated successfully", Toast.LENGTH_SHORT).show();

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
        Intent intent = new Intent(ViewProducts.this, SellerHome.class);
        startActivity(intent);
    }
}