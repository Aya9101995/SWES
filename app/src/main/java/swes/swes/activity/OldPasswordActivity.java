package swes.swes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import swes.swes.R;

public class OldPasswordActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_password);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setTitle("Enter current password");
        et_password=(EditText)findViewById(R.id.et_password);
    }
    public void checkOldpassword(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(),et_password.getText().toString());

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent  = new Intent(OldPasswordActivity.this,NewPasswordActivity.class);
                            intent.putExtra(getString(R.string.old_password),et_password.getText().toString());
                            startActivity(intent);
                        } else {
                            Log.d("old password class", "Error auth failed");
                            Toast.makeText(getApplicationContext(),getString(R.string.wrong_password),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}