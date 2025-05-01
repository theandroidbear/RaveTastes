package com.ravemaster.recipeapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ravemaster.recipeapp.R;
import com.ravemaster.recipeapp.utilities.Constants;
import com.ravemaster.recipeapp.utilities.PreferenceManager;

public class LogInActivity extends AppCompatActivity {

    private Button login,forgotPassWord;
    private TextInputLayout enterName,enterPassword;
    private EditText one, two;
    private Dialog dialog;
    ProgressBar progressBar;
    private TextView txtLoading;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_log_in);

        preferenceManager = new PreferenceManager(this);

//        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
//            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }

        initViews();

        showDialog3();

        one = enterName.getEditText();
        two = enterPassword.getEditText();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                fetchDetails();

            }
        });
        forgotPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

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
        String password = two.getText().toString();
        if (validatePassword(name,password)){
            enterPassword.setHelperTextEnabled(true);
            enterPassword.setHelperText("Fields cannot be empty!!");
            dialog.dismiss();
        }
        else{
            signIn(name,password);
        }
    }

    private void signIn(String name, String password) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_NAME,name)
                .whereEqualTo(Constants.KEY_PASSWORD,password)
                .get()
                .addOnCompleteListener( task -> {
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                        preferenceManager.putString(Constants.KEY_USER_ID,documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME,documentSnapshot.getString(Constants.KEY_NAME));
                        preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));
                        enterPassword.setHelperTextEnabled(false);
                        dialog.dismiss();
                        enterPassword.setHelperTextEnabled(false);
                        Toast.makeText(this, "Log in successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        dialog.dismiss();
                        enterPassword.setHelperTextEnabled(true);
                        enterPassword.setHelperText("Incorrect username or password");
                    }
                });
    }

    private boolean validatePassword(String name, String password){
        return name.isEmpty() || password.isEmpty();
    }

    private void initViews() {
        login = findViewById(R.id.btnLogIn);
        enterName = findViewById(R.id.edtTxtLoginName);
        enterPassword = findViewById(R.id.edtTxtLoginPassword);
        forgotPassWord = findViewById(R.id.txtForgotPassword);
    }
}