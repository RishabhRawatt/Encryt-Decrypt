package com.example.vinci;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import utils.MyEncrypter;


public class fragmentOnbording2 extends Fragment {

    private static final String FILE_NAME_ENC ="encrypted_image" ;
    private static final String FILE_NAME_DEC ="image_dec.png" ;

    int SELECT_PHOTO = 1;
    Button choose1;
    Button btn_encrypt;
    ImageView testimage;
    TextView  textView;
    InputStream inputStream;
    InputStream is;
    Bitmap bitmap;


    File myDir;

    //key hardcoded
    String my_key="E3CP7VdFGE5eJkra";
    String my_spec_key="JXQKVYXKBUb8l8VY";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = (ViewGroup) inflater.inflate(R.layout.fragment_onbording2, container, false);

        testimage= view.findViewById(R.id.test1);
        choose1 = (Button) view.findViewById(R.id.choose1);
        btn_encrypt = (Button) view.findViewById(R.id.btn_encrypt);

        //for input the key
        //textView = view.findViewById((R.id.inputkey));

        myDir=new File(Environment.getExternalStorageDirectory().toString()+"/saved_images");
//        String root=Environment.getExternalStorageDirectory().toString();
//        File myDir=new File(root+"/saved_images");
        File outputFileEnc =new File(myDir,FILE_NAME_ENC);


        //choose

        choose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "pick img"), 1);
                Toast.makeText(getActivity(), "pick image", Toast.LENGTH_SHORT).show();
            }
        });


        //encrypt

        btn_encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    MyEncrypter.encryptToFile(my_key,my_spec_key,is,new FileOutputStream(outputFileEnc));
                    Toast.makeText(getActivity(), "Encrypted", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;

    }

    // for pick image and convert into bitmap

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK && requestCode == 1) {

            //ImageView imageView= ImageView.findViewById(R.id.imageView);

            try {
                Uri imageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), imageUri);
                ByteArrayOutputStream stream =new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                 is=new ByteArrayInputStream(stream.toByteArray());

                testimage.setImageURI(imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

}









