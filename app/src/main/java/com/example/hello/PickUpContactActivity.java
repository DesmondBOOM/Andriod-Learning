package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class PickUpContactActivity extends AppCompatActivity {

    private static final String TAG = "[PickUpContactActivity]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_contact);
        Log.d(TAG, "on create");
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK) {
//            Cursor cursor = getContentResolver().query(data.getData(), new String[]{"display_name", "data1"}, null, null, null);
//            cursor.moveToNext();
//            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//            String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//            cursor.close();
//            Toast.makeText(this, contactName + " " + phoneNum, Toast.LENGTH_SHORT).show();
//        }
//    }
}