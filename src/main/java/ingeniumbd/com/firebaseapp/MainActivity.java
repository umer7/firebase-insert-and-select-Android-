package ingeniumbd.com.firebaseapp;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    public static  final String TAG="FIREBASE";

    public  RecyclerView recyclerListView;
    public  UserAdapter myAdapter;
    public  EditText editTextName;
    public  EditText editTextCountry;
    public  EditText editTextPhone;
    public  EditText editTextEmail;
    Button buttonAdd;
    public  ProgressBar myProgressBar;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        // creating layout
        creatingLayouts();
    }
    public void creatingLayouts(){
        myProgressBar=(ProgressBar) findViewById(R.id.loader);
        editTextName = (EditText) findViewById(R.id.nameEditText);
        editTextCountry=(EditText) findViewById(R.id.countryEditText);
        editTextPhone=(EditText) findViewById(R.id.weightEditText);
        editTextEmail = (EditText) findViewById(R.id.emailEditText);
        buttonAdd = (Button) findViewById(R.id.addButton);
        recyclerListView=(RecyclerView) findViewById(R.id.recylerview_list);
        recyclerListView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter= new UserAdapter(this);
        updateAdapter();
        recyclerListView.setAdapter(myAdapter);
    }

    //add new user to database
    public void btnAddOnClick(View v) {

        String name = editTextName.getText().toString().trim();
        String Address=editTextCountry.getText().toString().trim();
        double weight=Double.parseDouble(editTextPhone.getText().toString().trim());
        String email = editTextEmail.getText().toString().trim();
        User user= new User(name, Address, weight,email);

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Please enter name",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Address)) {
            Toast.makeText(getApplicationContext(), "Please enter Address",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Pleas enter valid email",
                    Toast.LENGTH_SHORT).show();
        }

        updateDatabase(user);

    }

    // adding new user to end  the user using on firebase database
    public void updateDatabase(User user){

        databaseReference.child("users").push().setValue(user);

        editTextName.setText(null);
        editTextCountry.setText(null);
        editTextPhone.setText(null);
        editTextEmail.setText(null);

        updateAdapter();

    }

    //update adapter
    public void updateAdapter(){

        final List<User> listUsers= new ArrayList<>();
        databaseReference.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listUsers.add(dataSnapshot.getValue(User.class));
                displayUsers(listUsers);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Canceled",Toast.LENGTH_SHORT).show();
            }
        });


    }


    //display the user on Adapter
    public void displayUsers(List<User> ls){
        myProgressBar.setVisibility(View.GONE);
        recyclerListView.setVisibility(View.VISIBLE);
        editTextName.setText(null);
        editTextCountry.setText(null);
        editTextEmail.setText(null);
        myAdapter.setData(ls);
        myAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
