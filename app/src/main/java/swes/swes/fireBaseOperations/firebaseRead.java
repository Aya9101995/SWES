package swes.swes.fireBaseOperations;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import swes.swes.R;
import swes.swes.classes.Student;

/**
 * Created by win on 12/05/2017.
 */

public  class firebaseRead {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef ;

   public static Student getCurrentStudent(final String uid , final Context context){
         final  Student s = new Student();
       myRef = database.getReference(context.getString(R.string.fb_users));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

               s.setEmail(dataSnapshot.child(uid).child(context.getString(R.string.fb_users_email)).getValue(String.class));
               s.setName(dataSnapshot.child(uid).child(context.getString(R.string.fb_users_name)).getValue(String.class));

                Log.d("Firebaseclass", "Value is: " + s.getName());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Firebaseclass", "Failed to read value.", error.toException());
            }
        });
        return s;
    }
}
