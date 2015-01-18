package com.srijan.dbtest;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.*;
import android.provider.ContactsContract.CommonDataKinds.*;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private Firebase f;
    private Card c;
    private String currUser;
    private Button post, syncB;
    private DataSnapshot data;

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
        syncB = (Button) findViewById(R.id.syncButton);

        f.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                data = snapshot;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setBackgroundColor(Color.parseColor("#42ff23"));
                setField();
                post.setBackgroundResource(android.R.drawable.btn_default);
            }
        });

        syncB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncB.setBackgroundColor(Color.parseColor("#42ff23"));
                sync();
                syncB.setBackgroundResource(android.R.drawable.btn_default);
            }
        });


    }


    public void sync() {

        Object o = data.getValue();
        HashMap<String, HashMap> vals = ((HashMap<String, HashMap>)o).get("users");
        Collection<HashMap> firstMap = vals.values();
        HashMap<String, String> innerMost;
        String mobile, work, home, firstName, lastName, emailID;
        for(HashMap innerLayer : firstMap) {
            firstName = innerLayer.get("firstName").toString();
            lastName = innerLayer.get("lastName").toString();
            emailID = innerLayer.get("emailID").toString();
            innerMost = (HashMap) innerLayer.get("numbers");
            mobile = innerMost.get("TYPE_MOBILE");
            work = innerMost.get("TYPE_MOBILE");
            home = innerMost.get("TYPE_HOME");

            update(firstName, lastName,mobile,work,home,emailID);


        }



    }




    public void update(String firstname, String lastname, String mobile, String work, String home, String email)
    {
        int id = 1;

        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        // Name
        ContentProviderOperation.Builder builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
        builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "=?", new String[]{String.valueOf(id), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE});
        builder.withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, lastname);
        builder.withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, firstname);
        ops.add(builder.build());

        // Number
        builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
        builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "=?"+ " AND " + ContactsContract.CommonDataKinds.Organization.TYPE + "=?", new String[]{String.valueOf(id), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_HOME)});
        builder.withValue(Phone.NUMBER, mobile);
        ops.add(builder.build());


        // Update
        try
        {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

//    public static int getContactIDFromNumber(String contactNumber,Context context)
//    {
//        contactNumber = Uri.encode(contactNumber);
//        int phoneContactID = new Random().nextInt();
//        Cursor contactLookupCursor = context.getContentResolver().query(Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,Uri.contactNumber),new String[] {PhoneLookup.DISPLAY_NAME, PhoneLookup._ID}, null, null, null);
//        while(contactLookupCursor.moveToNext()){
//            phoneContactID = contactLookupCursor.getInt(contactLookupCursor.getColumnIndexOrThrow(PhoneLookup._ID));
//        }
//        contactLookupCursor.close();
//
//        return phoneContactID;
//
//    }


    public void postinfo() {
        f.child("users").child(currUser).setValue(c);
        HashSet<Integer> tempUserList = new HashSet<Integer>();
        if (!currUser.equals("simplelogin:4")) tempUserList.add(4);
        if (!currUser.equals("simplelogin:5")) tempUserList.add(5);
        if (!currUser.equals("simplelogin:7")) tempUserList.add(7);
        f.child("users").child(currUser).child("friends").setValue(tempUserList);
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
        postinfo();
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

        if (id == R.id.action_logout) {
            //LoginActivity.logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
