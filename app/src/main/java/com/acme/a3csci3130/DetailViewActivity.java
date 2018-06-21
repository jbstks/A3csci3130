package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetailViewActivity extends Activity {

    private EditText nameField, primaryBusinessField, addressField, provinceField;
    Contact receivedPersonInfo;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        receivedPersonInfo = (Contact) getIntent().getSerializableExtra("Contact");

        nameField = (EditText) findViewById(R.id.name);
        primaryBusinessField = (EditText) findViewById(R.id.primaryBusiness);
        addressField = (EditText) findViewById(R.id.address);
        provinceField = (EditText) findViewById(R.id.province);

        if (receivedPersonInfo != null) {
            nameField.setText(receivedPersonInfo.name);
            primaryBusinessField.setText(receivedPersonInfo.primaryBusiness);
            addressField.setText(receivedPersonInfo.address);
            provinceField.setText(receivedPersonInfo.province);
        }
    }

    public void updateContact(View v){
        //TODO: Update contact functionality
        //receivedPersonInfo = (Contact) getIntent().getSerializableExtra("Contact");
        String name = nameField.getText().toString();
        String primaryBusiness = primaryBusinessField.getText().toString();
        String address = addressField.getText().toString();
        String province = provinceField.getText().toString();

        //Contact person = new Contact(personID, name, email);
        Contact person = new Contact(receivedPersonInfo.uid, name, primaryBusiness, address, province);

        appState.firebaseReference.child(receivedPersonInfo.uid).setValue(person);

        finish();
    }

    public void eraseContact(View v)
    {
        //TODO: Erase contact functionality
        
    }
}
