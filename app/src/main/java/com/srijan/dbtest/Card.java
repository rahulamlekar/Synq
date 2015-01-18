package com.srijan.dbtest;

import android.provider.ContactsContract;

import java.util.HashSet;

/**
 * Created by Srijan on 1/17/15.
 */
public class Card {

    public String firstName, lastName;
    public HashSet<ContactsContract.CommonDataKinds.Phone> numbers;
    public ContactsContract.CommonDataKinds.Email emailID;
    public ContactsContract.CommonDataKinds.Photo picture;
    public ContactsContract.CommonDataKinds.StructuredName fullName;

}
