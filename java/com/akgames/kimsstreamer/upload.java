package com.akgames.kimsstreamer;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.cloudmersive.client.AudioApi; com.github.Cloudmersive

public class upload extends AppCompatActivity {
    private Button home, cancel, upload, browse, file, findTags;
    private ImageView filestr;
    private PopupMenu popupMenu;
    private String filepath = "";
    private Intent fileFinder = new Intent();
    private Uri uri;

    private String filepath2 = "";
    private Intent fileFinder2 = new Intent();
    private Uri uri2;
    private byte[] result;
    private String filename = "";
    private DatabaseReference mDatabase;
    private StorageReference storageReference;
    private EditText name, description;
    private TextView tags;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private StorageReference fileReference;
    private String itemname, fileType, author;
    private ProgressBar mProgressBar;
    private RadioGroup radioGroup;
    private RadioButton rad;//movie, radPodcast, radMusic;
    private StorageTask mUploadTask1;
    private String user;
    private SharedPreferences sharedPreferences;
    private int checkId;
    private ArrayList<Integer> chMovieTags = new ArrayList<>();
    private String[] movieTags = {"Drama","Comedy", "Thriller","Romance" ,"Action",
            "Horror","Crime", "Adventure", "Mystery", "Family", "War", "Educational",
            "History", "Sci-Fi", "Musical", "Animation", "Sports", "News", "Other",
            "Blues", "Classical", "Country", "Jazz", "Hip-hop", "Soul", "Rock", "Pop",
            "R and B", "Latin", "Metal", "Rap" };
    private boolean[] seMovieTags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        seMovieTags = new boolean[movieTags.length];
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads");
        cancel = findViewById(R.id.cancel);
        upload = findViewById(R.id.submitFile);
        filestr = findViewById(R.id.findimage);
        home = findViewById(R.id.homepage);
        browse = findViewById(R.id.browse);
        name = findViewById(R.id.newTitle);
        mProgressBar = findViewById(R.id.progress_bar);
        file = findViewById(R.id.video_music);
        findTags = findViewById(R.id.tagtype);


        description = findViewById(R.id.uploadDescription);
        tags = findViewById(R.id.tags);
        sharedPreferences = getSharedPreferences("kimsStreamerProfileSaved",MODE_PRIVATE);
        author = sharedPreferences.getString("publicname","");
        user = sharedPreferences.getString("user","");
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //radmovie = findViewById(R.id.radmovie);
        //radPodcast = findViewById(R.id.radPodcast);
        //radMusic = findViewById(R.id.radMusic);
        uri = null;
        uri2 = null;

        radioGroup = findViewById(R.id.radioGroup);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), home.class);
                startActivity(intent);
                finish();
            }
        });

        findTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkId = radioGroup.getCheckedRadioButtonId();
                if(checkId == -1){

                }
                else {
                    rad = findViewById(checkId);
                    String radstr = rad.getText().toString();
                    AlertDialog.Builder builder = new AlertDialog.Builder(upload.this);

                    builder.setTitle("Tags");
                    builder.setCancelable(false);
                    //if(radstr.equals("Movies")) {
                        builder.setMultiChoiceItems(movieTags, seMovieTags, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                                if (isChecked) {
                                    if (!chMovieTags.contains(which)) {
                                        chMovieTags.add(which);
                                    }
                                }
                                else if (chMovieTags.contains(which)) {
                                    chMovieTags.remove(chMovieTags.indexOf(which));
                                }
                            }
                        });
                        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                String strtags = "";
                                Collections.sort(chMovieTags);
                                for (int j : chMovieTags) {
                                    strtags += movieTags[j].toLowerCase() + " ";
                                }
                                tags.setText(strtags);
                                //return true;
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Do nothing
                                dialog.dismiss();

                            }
                        });
                        builder.show();
                }
            }
        });


        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fileFinder.setType("image/*");
                fileFinder.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(fileFinder, "Select Picture"), 10);
//                startActivity(intent);
//                finish();
            }
        });
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkId = radioGroup.getCheckedRadioButtonId();
                rad = findViewById(checkId);
                String radstr = rad.getText().toString();

                fileFinder2.setAction(Intent.ACTION_GET_CONTENT);
                if (radstr.contains("Movie")) {
                    fileFinder2.setType("video/*");
                } else if (radstr.contains("Podcast") || radstr.contains("Music")) {
                    fileFinder2.setType("*/*");
                } else {
                    fileFinder2.setType("*/*");
                }


                startActivityForResult(Intent.createChooser(fileFinder2, "Select file"), 11);
//                startActivity(intent);
//                finish();

            }
        });



        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkId = radioGroup.getCheckedRadioButtonId();
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(upload.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }
                else if (mUploadTask1 != null && mUploadTask.isInProgress()) {
                    Toast.makeText(upload.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }
                else if(checkId == -1){
                    Toast.makeText(upload.this,"select a file type",Toast.LENGTH_SHORT).show();
                }
                else if(uri == null){
                    Toast.makeText(upload.this, "No image file selected", Toast.LENGTH_SHORT).show();
                }
                else if(uri2 == null){
                    Toast.makeText(upload.this, "No file selected", Toast.LENGTH_SHORT).show();
                }
//                else if(!".M4A_.WEBM_.MPG_.MP2_.MPEG_.MPE_.MPV_.OGG_.MP4_.M4P_.M4V_.AVI_.WMV_.MOV_.QT_.FLV_.SWF_.AVCHD_.MP3_.WMA_.AAC_.WAV_.FLAC".toLowerCase().contains(getFileExtension(uri2))){
//                    Toast.makeText(upload.this, "Unsupported file selected", Toast.LENGTH_SHORT).show();
//                }
//                else if(".JPG_.PNG_.GIF_.WEBP_.TIFF_.PSD_.RAW_.BMP_.HEIF_.INDD_.JPEG_.SVG_.AI_.EPS_.PDF_".contains(getFileExtension(uri))){
//
//                }
                else {
                    try {
                        uploadFile();
                    }catch (Exception e){
                        tags.setText(e.toString());
                    }
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), home.class);
                startActivity(intent);
                finish();
            }
        });
    }








    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode ==RESULT_OK) {
            filepath = data.getData().getPath();
            uri = data.getData();
            try {
                filestr.setImageURI(uri);
            }catch (Exception e){
                Toast.makeText(upload.this, "Unsupported file selected", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 11 && resultCode ==RESULT_OK) {
            filepath2 = data.getData().getPath();
            uri2 = data.getData();
            //File newfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            //new CompressVideo().execute("false", uri2.toString(),newfile.getPath());
            
            //filestr.setImageURI(uri);
            //Toast.makeText(upload.this, imageB64+"00" , Toast.LENGTH_LONG).show();
        }
    }
    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadImage() {
        //checking if file is available
        if (uri != null) {

            itemname = (System.currentTimeMillis()
                    + "." + getFileExtension(uri));
            //getting the storage reference
            //itemname

            //Bitmap bitmap = decodeImageFromFiles(path, /* your desired width*/300, /*your desired height*/ 300);
            mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
            fileReference = mStorageRef.child(itemname);
            //adding the file to reference
            Bitmap bmp = null;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
            byte[] data = baos.toByteArray();
            //uploading the image


            mUploadTask = fileReference.putBytes(data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            //progressDialog.dismiss();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar.setProgress(0);
                                            Intent intent = new Intent(getApplicationContext(), home.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 500);

                                    int checkId = radioGroup.getCheckedRadioButtonId();
                                    rad = findViewById(checkId);
                                    String radstr = rad.getText().toString();
                                    Toast.makeText(upload.this, "Upload successful", Toast.LENGTH_LONG).show();
                                    newUpload upload = new newUpload(name.getText().toString().trim(), uri.toString().trim(),
                                            uri2.toString(), description.getText().toString().trim(), author, tags.getText().toString().trim(),
                                            radstr, user);
                                    //adding an upload to firebase database

                                    String uploadId = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(radstr).child(uploadId).setValue(upload);
                                    //Toast.makeText(getApplicationContext(), uri.toString() , Toast.LENGTH_LONG).show();
                                    //Log.d(TAG, "onSuccess: uri= "+ uri.toString());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }
    }

    private void uploadFile() {
        try {
            upload.setEnabled(false);
            upload.setVisibility(View.INVISIBLE);
            home.setEnabled(false);
            filename = (System.currentTimeMillis()
                    + "." + getFileExtension(uri2));
            fileReference = mStorageRef.child(filename);
            mUploadTask1 = fileReference.putFile(uri2)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            //progressDialog.dismiss();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uriget) {
                                    uri2 = uriget;
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar.setProgress(0);
                                            uploadImage();
                                        }
                                    }, 0);
                                    //Toast.makeText(getApplicationContext(), uri.toString() , Toast.LENGTH_LONG).show();
                                    //Log.d(TAG, "onSuccess: uri= "+ uri.toString());
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }catch (Exception e){
            tags.setText(e.toString());
        }
    }

}