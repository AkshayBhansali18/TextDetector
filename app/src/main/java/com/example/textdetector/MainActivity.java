package com.example.textdetector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class MainActivity extends AppCompatActivity {
    FirebaseVisionImage image;
    FirebaseVisionTextRecognizer recognizer;
    Button camera_button;
    int request_code=123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera_button=(Button)findViewById(R.id.camera_button);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(pictureIntent.resolveActivity(getPackageManager())!=null)
                {
                    startActivityForResult(pictureIntent,request_code);
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==request_code&&resultCode==RESULT_OK)
        {
            Bundle bundle=data.getExtras();
            Bitmap bitmap= (Bitmap) bundle.get("data");
            image=FirebaseVisionImage.fromBitmap(bitmap);
            recognizer= FirebaseVision.getInstance().getOnDeviceTextRecognizer();
            recognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                    String resultText=firebaseVisionText.getText().toString();
                    if(resultText.isEmpty())
                    {
                        Toast.makeText(MainActivity.this, "No Text Recognized", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Intent intent=new Intent(MainActivity.this,ResultActivity.class);
                        intent.putExtra("result",resultText);
                        startActivity(intent);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Main Activity",e.toString());
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
