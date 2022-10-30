package com.tech.dresshub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.tech.dresshub.models.Cart;

public class AddtoCart extends AppCompatActivity {

    ImageView imageView;
    Button button1;
    Button button2;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    TextView textView;
    String image,type,name;
    DatabaseReference ref;
    String price;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_cart);

        imageView = (ImageView)findViewById(R.id.producttimage);
        editText1 = (EditText) findViewById(R.id.qty);
        editText2 = (EditText) findViewById(R.id.sizecus);
        editText3 = (EditText) findViewById(R.id.address);
        editText4 = (EditText) findViewById(R.id.contact);
        textView = (TextView) findViewById(R.id.totalcal);
        button1 = (Button) findViewById(R.id.cal);
        button2 = (Button) findViewById(R.id.send);

        ref = FirebaseDatabase.getInstance().getReference("MyBag");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            image = extras.getString("image");
            type = extras.getString("type");
            name = extras.getString("name");
            price = extras.getString("price");

            System.out.println(image);
            System.out.println(type);
            System.out.println(name);
            System.out.println(price);

            Picasso.get().load(image).into(imageView);

        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editText1.getText().toString().isEmpty()){
                    Toast.makeText(AddtoCart.this, "Quantity is Required", Toast.LENGTH_SHORT).show();
                }else{
                    int qty = Integer.parseInt(editText1.getText().toString());
                    int intprice = Integer.parseInt(price);

                    total = intprice*qty;

                    textView.setText(Integer.toString(total));
                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String quantity = editText1.getText().toString();
                 String size = editText2.getText().toString();
                 String address = editText3.getText().toString();
                 String contact = editText4.getText().toString();

                if (quantity.isEmpty()) {
                    editText1.setError("Quantity is required");
                }else if (size.isEmpty()) {
                    editText2.setError("Size is required");
                }else if (address.isEmpty()) {
                    editText3.setError("Address is required");
                }else if (contact.isEmpty()) {
                    editText4.setError("Contact number is required");
                }else {

                    String key = ref.push().getKey();
                    Cart cart = new Cart(key,image,type,name,quantity,size,price,textView.getText().toString(),address,contact);
                    ref.child(key).setValue(cart);

                    Toast.makeText(AddtoCart.this, "Successfully added", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddtoCart.this, CustomerHome.class);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddtoCart.this, ManageBag.class);
        startActivity(intent);
    }


}