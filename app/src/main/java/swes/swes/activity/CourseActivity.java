package swes.swes.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import swes.swes.DataClasses.CourseInfo;
import swes.swes.R;

public class CourseActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    private CourseInfo courseInfo= new CourseInfo();
    private ImageView iv_course_pic;
    private TextView tv_desc;
   ArrayList<Map<String,String>> prerequiste = new ArrayList<>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(getString(R.string.fb_course_data));
        mStorageRef = FirebaseStorage.getInstance().getReference();
        iv_course_pic = (ImageView)findViewById(R.id.iv_course_pic);
        tv_desc = (TextView)findViewById(R.id.tv_course_desc);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get course info
                courseInfo.setPhoto(dataSnapshot.child(getString(R.string.fb_course_photo)).getValue(String.class));
                courseInfo.setDesc(dataSnapshot.child(getString(R.string.fb_course_desc)).getValue(String.class));
                DataSnapshot snapshot = dataSnapshot.child(getString(R.string.fb_prerequiste));
                //get prerequiste
                for (DataSnapshot prereq_Snapshot : snapshot.getChildren()){
                    Log.d("FIREBASETEST", "Value is: " + prereq_Snapshot.getKey().toString());
                    String key = prereq_Snapshot.getKey().toString();
                    String value = prereq_Snapshot.getValue().toString();
                    Map<String,String> map = new HashMap<String, String>();
                    map.put(key,value);
                    prerequiste.add(map);
                }
                courseInfo.setPrerequisets(prerequiste);
                Log.d("FIREBASETEST", "Value is: " + courseInfo.getPhoto());
                // get photo download uri
                mStorageRef.child(courseInfo.getPhoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // display photo
                        Picasso.with(CourseActivity.this).load(uri).fit().centerCrop().into(iv_course_pic);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
                // set description
                tv_desc.setText(courseInfo.getDesc());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASETEST", "Failed to read value.", error.toException());
            }
        });

    }
}
