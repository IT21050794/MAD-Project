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

public class CustomerHome extends AppCompatActivity {

    ListView listView;
    List<Products> user;
    DatabaseReference ref;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        button = (Button)findViewById(R.id.gotobag);
        listView = (ListView) findViewById(R.id.listproducts);
        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("Products");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerHome.this, ManageBag.class);
                startActivity(intent);
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    Products products = studentDatasnap.getValue(Products.class);
                    user.add(products);

                }
                MyAdapter adapter = new MyAdapter(CustomerHome.this, R.layout.custom_addto_cart, (ArrayList<Products>) user);
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
                view = inflater.inflate(R.layout.custom_addto_cart, null);

                holder.COL1 = (TextView) view.findViewById(R.id.type);
                holder.COL2 = (TextView) view.findViewById(R.id.name);
                holder.COL3 = (TextView) view.findViewById(R.id.metireal);
                holder.COL4 = (TextView) view.findViewById(R.id.strech);
                holder.COL5 = (TextView) view.findViewById(R.id.size);
                holder.COL6 = (TextView) view.findViewById(R.id.price);
                holder.imageView1 = (ImageView) view.findViewById(R.id.imageget);
                holder.button1 = (Button) view.findViewById(R.id.gotocart);


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

            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(CustomerHome.this, AddtoCart.class);
                    i.putExtra("image",user.get(position).getImageURL());
                    i.putExtra("type",user.get(position).getType());
                    i.putExtra("name",user.get(position).getName());
                    i.putExtra("price",user.get(position).getPrize());
                    startActivity(i);
                }
            });

            return view;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CustomerHome.this, ChooseActor.class);
        startActivity(intent);
    }
}