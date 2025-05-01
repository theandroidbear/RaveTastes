package com.ravemaster.recipeapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ravemaster.recipeapp.R;
import com.ravemaster.recipeapp.utilities.Constants;
import com.ravemaster.recipeapp.utilities.PreferenceManager;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout enterUserName, enterPassword, confirmPassword;
    private EditText one,two,three;
    private Button createPersona, goToLogin, addPhoto;
    private Dialog dialog;
    ProgressBar progressBar;
    private TextView txtLoading;
    private ImageView imageView;
    String encodedImage = "";
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        initViews();
        initEditTexts();
        showDialog3();

        preferenceManager = new PreferenceManager(this);

        createPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                fetchDetails();
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*"); // Allows all file types; you can specify a specific type if needed
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pickImage.launch(intent);
            }
        });

    }

    private ActivityResultLauncher<Intent> pickImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == RESULT_OK){
                if (o.getData() != null){
                    Uri imageUri = o.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageView.setImageBitmap(bitmap);
                        encodedImage = getEncodedImage(bitmap);
                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    });

    private String getEncodedImage(Bitmap bitmap){
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth/bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight,false);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG,50,outputStream);
        byte[] bytes = outputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }

    private void initEditTexts(){
        one = enterUserName.getEditText();
        two = enterPassword.getEditText();
        three = confirmPassword.getEditText();
    }

    private void showDialog3() {
        dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_progress_layout,null);
        progressBar = view.findViewById(R.id.myProgressBar);
        txtLoading = view.findViewById(R.id.txtProgress);
        dialog.setContentView(view);
        int widthInDp = 250;

        final float scale = getResources().getDisplayMetrics().density;
        int widthInPx = (int) (widthInDp * scale + 0.5f);

        dialog.getWindow().setLayout(widthInPx, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
        dialog.setCancelable(false);
        txtLoading.setText("Logging you in...");
    }

    private void fetchDetails(){
        String name = one.getText().toString();
        String password1 = two.getText().toString();
        String password2 = three.getText().toString();

        if (checkIsEmpty(name,password1,password2)){
            confirmPassword.setHelperTextEnabled(true);
            confirmPassword.setHelperText("Fields cannot be empty");
            dialog.dismiss();
        }
        else{
            confirmPassword.setHelperTextEnabled(false);
            if (validatePassword(password1,password2)){
                confirmPassword.setHelperTextEnabled(false);
                signUp(name,password2,encodedImage);
            } else {
                confirmPassword.setHelperTextEnabled(true);
                confirmPassword.setHelperText("Passwords do not match");
                dialog.dismiss();
            }
        }
    }

    private void signUp(String name, String password2, String image) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> data = new HashMap<>();
        data.put(Constants.KEY_NAME, name);
        data.put(Constants.KEY_PASSWORD, password2);
        if (checkImage(image)){
            data.put(Constants.KEY_IMAGE, "image");
        } else {
            data.put(Constants.KEY_IMAGE, image);
        }
        data.put(Constants.KEY_IMAGE, image);
        db.collection(Constants.KEY_COLLECTION_USERS)
                .add(data)
                .addOnSuccessListener( documentReference -> {

                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                    preferenceManager.putString(Constants.KEY_USER_ID,documentReference.getId());
                    preferenceManager.putString(Constants.KEY_NAME,name);
                    preferenceManager.putString(Constants.KEY_IMAGE, image);
                    dialog.dismiss();
                    Toast.makeText(this, "Account created successfully.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(exception ->{
                    confirmPassword.setHelperTextEnabled(true);
                    confirmPassword.setHelperText("Unable to create account, try again later");
                });

    }

    private boolean checkIsEmpty(String name, String p1, String p2){
        return name.isEmpty() || p1.isEmpty() || p2.isEmpty();
    }

    private boolean checkImage(String encodedImage){
        return encodedImage.isEmpty();
    }


    private boolean validatePassword(String password1, String password2){
        return password1.equals(password2);
    }

    private void initViews() {
        enterPassword = findViewById(R.id.edtTxtEnterPassword);
        confirmPassword = findViewById(R.id.edtTxtConfirmPassword);
        enterUserName = findViewById(R.id.enterNewUserName);
        createPersona = findViewById(R.id.btnCreatePersona1);
        goToLogin = findViewById(R.id.btngoToLogIn);
        addPhoto = findViewById(R.id.btnAddProfilePhoto);
        imageView = findViewById(R.id.imgProfilePic);
    }
}