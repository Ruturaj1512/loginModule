package edu.ritindia.prathmeshb.ardesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class registeruser extends AppCompatActivity implements View.OnClickListener {

     private TextView banner,registerusers;
    private EditText name,email,password;
     private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeruser);
        mAuth = FirebaseAuth.getInstance();

        banner=findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerusers=findViewById(R.id.registerusers);
        registerusers.setOnClickListener(this);

        name=findViewById(R.id.name);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

        progressBar= findViewById(R.id.progressbar);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.banner:

                startActivity(new Intent(this,login.class));
                break;

            case R.id.registerusers:
                reguser();
                break;


        }

    }

    private void reguser() {

        String e= email.getText().toString().trim();
        String pw= password.getText().toString().trim();
        String n=name.getText().toString().trim();


        if (n.isEmpty()){
            name.setError("Name is required");
            name.requestFocus();
            return;
        }



        if (e.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(e).matches())
        {
            email.setError("Please Provide valid email id");
            email.requestFocus();
            return;
        }

        if (pw.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if (pw.length()<6)
        {
            password.setError("Min Password length should be 6 characters");
            password.requestFocus();
            return;
        }



progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(e,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(e,n);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(registeruser.this,"User Has been registered successfully",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            }
                            else
                            {
                                Toast.makeText(registeruser.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(registeruser.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });



    }
}