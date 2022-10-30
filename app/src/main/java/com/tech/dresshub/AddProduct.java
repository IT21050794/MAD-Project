package com.tech.dresshub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tech.dresshub.models.Products;

import java.io.IOException;

public class AddProduct extends AppCompatActivity {

    private RadioButton radioButton1,radioButton2,radioButton3,radioButton4,radioButton5;
    private EditText editText1,editText2,editText3,editText4,editText5;
    private ImageView imageView;
    Button button;

    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";

    // Creating URI.
    Uri FilePathUri;

    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;
    DatabaseReference databaseReference;

    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    private static final int SELECT_PICTURE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        radioButton5 = (RadioButton) findViewById(R.id.radioButton5);
        editText1 =(EditText) findViewById(R.id.dresstype);
        editText2 =(EditText) findViewById(R.id.matireal);
        editText3 =(EditText) findViewById(R.id.strech);
        editText4 =(EditText) findViewById(R.id.size);
        editText5 =(EditText) findViewById(R.id.price);
        imageView =(ImageView) findViewById(R.id.productimage);
        button = (Button) findViewById(R.id.submit);



        // Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();

        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);


            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImageFileToFirebaseStorage();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                imageView.setImageBitmap(bitmap);


            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                    new OnCompleteListener<Uri>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            String fileLink = task.getResult().toString();
                                            //next work with URL

                                            System.out.println(fileLink);

                                            String type = null;
                                            if (radioButton1.isChecked()){
                                                type = "Tops";
                                            }else if(radioButton2.isChecked()){
                                                type = "Pants";
                                            }else if(radioButton3.isChecked()){
                                                type = "Lounge wares";
                                            }else if(radioButton4.isChecked()){
                                                type = "Dresses";
                                            }else if(radioButton5.isChecked()){
                                                type = "Active Wares";
                                            }

                                            // Getting image name from EditText and store into string variable.
                                            String name = editText1.getText().toString();
                                            String matireal = editText2.getText().toString();
                                            String streachable = editText3.getText().toString();
                                            String size = editText4.getText().toString();
                                            String prize = editText5.getText().toString();

                                            System.out.println("TYPE "+type);
                                            if (name.isEmpty()) {
                                                editText2.setError("Name is required");
                                            }else if (matireal.isEmpty()) {
                                                editText3.setError("Matireal is required");
                                            }else if (streachable.isEmpty()) {
                                                editText4.setError("Streachable is required");
                                            }
                                            else if (size.isEmpty()) {
                                                editText4.setError("Size is required");
                                            }
                                            else if (prize.isEmpty()) {
                                                editText4.setError("Prize is required");
                                            }else {

                                                // Getting image upload ID.
                                                String key = databaseReference.push().getKey();

                                                System.out.println();

                                                @SuppressWarnings("VisibleForTests")
                                                Products products = new Products(key, fileLink, type, name, matireal, streachable, size, prize);

                                                // Adding image upload id s child element into databaseReference.
                                                databaseReference.child(key).setValue(products);

                                                // Showing toast message after done uploading.
                                                Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();

                                            }

                                        }
                                    });


                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Showing exception erro message.
                            Toast.makeText(AddProduct.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        }
        else {

            Toast.makeText(AddProduct.this, "Failed", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddProduct.this, SellerHome.class);
        startActivity(intent);
    }
}
