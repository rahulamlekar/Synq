package com.srijan.dbtest;

import android.provider.ContactsContract;

import java.util.HashSet;
import java.util.HashMap;

/**
 * Created by Srijan on 1/17/15.
 * Represents a contact card
 */
public class Card {

//    public HashSet<ContactsContract.CommonDataKinds.Phone> numbers;
//    public ContactsContract.CommonDataKinds.Email emailID;
//    public ContactsContract.CommonDataKinds.Photo picture;
//    public ContactsContract.CommonDataKinds.StructuredName fullName;

    public String firstName, lastName, emailID;
    public HashMap<String, String> numbers;


}
