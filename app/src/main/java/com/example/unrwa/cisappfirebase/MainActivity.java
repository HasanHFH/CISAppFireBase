package com.example.unrwa.cisappfirebase;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int Gallery_intent=2;
    private EditText name,age,phone,profilepic;
    private Button addBtn;
    private ImageButton imageButton;
    private DatabaseReference reference;
    private StorageReference imagePathe=null,storage=null;


    private Model model;
    private String ImageLocation;

    private RecyclerView recyclerView;
    private ArrayList<Model>list;
    private MyAdapter adapter;

    ProgressBar progressBar;

   FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reference=FirebaseDatabase.getInstance().getReference("DataBase").child("Users");
        model = new Model();

        storage=FirebaseStorage.getInstance().getReference();
 //       imagePathe=FirebaseStorage.getInstance().getReference();
        name=(EditText)findViewById(R.id.name);
        age=(EditText)findViewById(R.id.age);
        phone=(EditText)findViewById(R.id.phonNo);

        addBtn=(Button)findViewById(R.id.addBtn);
        imageButton=(ImageButton)findViewById(R.id.imageButton);
        //progressBar=(ProgressBar)findViewById(R.id.progressBar);

        recyclerView=(RecyclerView)findViewById(R.id.rViewFireBase);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList <Model>();

        //progressBar.setVisibility(View.VISIBLE);


        //mAuth=FirebaseAuth.getInstance();

          reference.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        Model p=dataSnapshot1.getValue(Model.class);
                        //p.setProfilepic(dataSnapshot1.getValue(Model.class).profilepic);
                        list.add(p);
                        }
                        adapter=new MyAdapter(MainActivity.this,list);

                    recyclerView.setAdapter(adapter);

              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {
                  Toast.makeText(MainActivity.this, "هنالك خطأ.......", Toast.LENGTH_SHORT).show();
              }
          });



        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

       //         progressBar.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(name.getText().toString())){


                    String id=reference.push().getKey();
                    Model model=new Model(id,name.getText().toString(),age.getText().toString(),phone.getText().toString(),ImageLocation);
                    reference.child(id).setValue(model);

       Toast.makeText(MainActivity.this, "تمت عملية التخزين بنجاح", Toast.LENGTH_SHORT).show();
         //           progressBar.setVisibility(View.GONE);
                }
            else
                {  //                  progressBar.setVisibility(View.GONE);

                    Toast.makeText(MainActivity.this, "برجاء إدخال البيانات كاملا", Toast.LENGTH_SHORT).show();
            }}
        });
        //1 DatabaseReference reference=FirebaseDatabase.getInstance().getReference("DataBase").child("Text");

        //1 reference.setValue("Hasan");
        //2 reference.push().setValue("Khaled");
        //3 Model model=new Model("Mohammad","26","123456789");
        //3 reference.push().setValue(model);
    }

    public void btnimageAdd(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent,2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2 && resultCode==RESULT_OK) {
            Uri uri = data.getData();
            //imageButton.setImageURI(uri);
            //imagePathe =FirebaseStorage.getInstance().getReference().child("profile").child(uri.getLastPathSegment());
            imagePathe =FirebaseStorage.getInstance().getReference().child("profile");
            //imagePathe=storage.child("profile").child(uri.getLastPathSegment());
            //ImageLocation=uri.getPath();
            Picasso.with(this).load(uri).into(imageButton);
            //ImageLocation=uri.getLastPathSegment();
            StorageReference fileRef =storage.child(System.currentTimeMillis()+"."+getFileExtention(uri));

            fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener <UploadTask.TaskSnapshot>() {
    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


        //Model model=new Model()
        //Task<Uri> downUrl=taskSnapshot.getMetadata().getReference().getDownloadUrl();
        String id=reference.push().getKey();
        Model model=new Model(id,name.getText().toString(),age.getText().toString(),phone.getText().toString(),taskSnapshot.getStorage().getDownloadUrl().toString());
        reference.child(id).setValue(model);
        Toast.makeText(MainActivity.this, "تم التحميل الصورة بنجاح", Toast.LENGTH_LONG).show();


    }
})
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        })
        .addOnProgressListener(new OnProgressListener <UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

          //  imagePathe.putFile(uri).addOnSuccessListener(new OnSuccessListener <UploadTask.TaskSnapshot>() {


            //    @Override
              //  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Model model=new Model(profilepic.getText().toString().trim(),taskSnapshot.get);
                //String id=reference.push().getKey();
                //reference.child(id).setValue(model);
                //    Toast.makeText(MainActivity.this, "تم التحميل الصورة بنجاح", Toast.LENGTH_LONG).show();
                //}
            //}).addOnFailureListener(new OnFailureListener() {
              //  @Override
                //public void onFailure(@NonNull Exception e) {
                  //  Toast.makeText(MainActivity.this, "لم يتم  تحميل الصورة هناك خطأ", Toast.LENGTH_SHORT).show();

                //}
            //});
        }

    }

    private String getFileExtention(Uri uri) {
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
