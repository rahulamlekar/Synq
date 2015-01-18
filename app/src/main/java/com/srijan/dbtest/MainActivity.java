package com.srijan.dbtest;

import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.*;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    private Firebase f;
    private Card c;
    private String currUser;
    private Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        Bundle extra = getIntent().getExtras();
        f = new Firebase("https://scorching-heat-4537.firebaseio.com/");
        if(extra!=null) {
            currUser = extra.getString("uid");
        }
        setContentView(R.layout.activity_main);
        c = new Card();
        post = (Button) findViewById(R.id.postinfo);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setField();
            }
        });

    }

    public void postinfo() {
        f.child("users").child(currUser).setValue(c);
    }

    public void setField() {
        String firstName = ((EditText) (findViewById(R.id.editFirst))).getText().toString();
        c.firstName = ((EditText) (findViewById(R.id.editFirst))).getText().toString();
        c.lastName = ((EditText) (findViewById(R.id.editLast))).getText().toString();
        c.emailID = ((EditText) (findViewById(R.id.editEmail))).getText().toString();
        String mobile = ((EditText) (findViewById(R.id.editPhone1))).getText().toString();
        String home = ((EditText) (findViewById(R.id.editPhone2))).getText().toString();
        String work = ((EditText) (findViewById(R.id.editPhone3))).getText().toString();
        c.numbers.put("TYPE_MOBILE", mobile);
        c.numbers.put("TYPE_HOME", home);
        c.numbers.put("TYPE_WORK", work);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
