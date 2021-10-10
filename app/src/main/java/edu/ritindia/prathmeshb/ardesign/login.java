package edu.ritindia.prathmeshb.ardesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity implements View.OnClickListener {

    private TextView register,forgetPassword;
    private EditText email,password;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register=findViewById(R.id.register);
        register.setOnClickListener(this);



        signIn=findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        progressBar=findViewById(R.id.progressbar);

        forgetPassword=findViewById(R.id.fpass);
        forgetPassword.setOnClickListener(this);


        mAuth=FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.register:
                startActivity(new Intent(this,registeruser.class));
                break;

            case R.id.signIn:
                userLogin();
                break;

            case R.id.fpass:
                    startActivity(new Intent(login.this,fpassword.class));
                    break;


        }
    }

    private void userLogin() {

        String e=email.getText().toString().trim();
        String pw=password.getText().toString().trim();

        if(e.isEmpty()){

            email.setError("Email Address required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(e).matches())
        {

            email.setError("Please Enter a valid email");
            email.requestFocus();
            return;
        }

        if(pw.isEmpty())
        {
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

        mAuth.signInWithEmailAndPassword(e,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()) {
                        startActivity(new Intent(login.this, MainActivity.class));
                    }


                    else {
                        user.sendEmailVerification();
                        Toast.makeText(login.this, "Check Your Email to Verify your account", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }
                else
                {
                    Toast.makeText(login.this,"Failed to login! Try again!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}