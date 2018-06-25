package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class DetailViewActivity extends AppCompatActivity {

    private EditText nameField, primaryBusinessField, addressField, provinceField;
    Contact receivedPersonInfo;
    private Spinner primaryBusinessSpinner, provinceSpinner;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        receivedPersonInfo = (Contact) getIntent().getSerializableExtra("Contact");

        nameField = (EditText) findViewById(R.id.name);
        addressField = (EditText) findViewById(R.id.address);

        // Grabbed from documentation https://developer.android.com/guide/topics/ui/controls/spinner
        String[] primaryBusinesses = new String[] {"Fisher", "Distributor", "Processor", "Fish Monger"};
        primaryBusinessSpinner = (Spinner) findViewById(R.id.primaryBusinessSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> primaryBusinessAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, primaryBusinesses);
        primaryBusinessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        primaryBusinessSpinner.setAdapter(primaryBusinessAdapter);

        String[] provinces = new String[] {"", "AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT"};
        provinceSpinner = (Spinner) findViewById(R.id.provinceSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, provinces);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        provinceSpinner.setAdapter(provinceAdapter);

        if (receivedPersonInfo != null) {
            nameField.setText(receivedPersonInfo.name);
            //primaryBusinessField.setText(receivedPersonInfo.primaryBusiness);
            for (int i = 0; i < primaryBusinesses.length; i++) {
                if (primaryBusinesses[i].equals(receivedPersonInfo.primaryBusiness))
                    primaryBusinessSpinner.setSelection(i);
            }
            addressField.setText(receivedPersonInfo.address);
            //provinceField.setText(receivedPersonInfo.province);
            for (int i = 0; i < provinces.length; i++) {
                if (provinces[i].equals(receivedPersonInfo.province))
                    provinceSpinner.setSelection(i);
            }
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
