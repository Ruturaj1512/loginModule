package edu.ritindia.prathmeshb.ardesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class fpassword extends AppCompatActivity {
    private EditText emailedit;
    private Button resetPassword;
    private ProgressBar progressbar;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpassword);

        emailedit=findViewById(R.id.email);
        resetPassword=findViewById(R.id.resestpw);
        progressbar=findViewById(R.id.progressbar);
        auth= FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }
    public void resetPassword()
    {
        String email= emailedit.getText().toString().trim();
        if (email.isEmpty()){
            emailedit.setError("Email is required");
            emailedit.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailedit.setError("Please provide valid email");
            emailedit.setError("Email is required");
            emailedit.requestFocus();
            return;
        }
        progressbar.setVisibility((View.VISIBLE));
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if ( task.isSuccessful())
                {
                    Toast.makeText(fpassword.this, "Check your email to reset the password", Toast.LENGTH_LONG).show();
                    progressbar.setVisibility(View.GONE);
                }else
                {
                    Toast.makeText(fpassword.this, "Try again!", Toast.LENGTH_LONG).show();
                    progressbar.setVisibility(View.GONE);
                }
            }
        });

    }
}

