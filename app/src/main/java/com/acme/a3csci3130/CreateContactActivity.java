package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateContactActivity extends Activity {

    private Button submitButton;
    private EditText nameField, primaryBusinessField, addressField, provinceField;
    private Spinner primaryBusinessSpinner, provinceSpinner;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_activity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        submitButton = (Button) findViewById(R.id.submitButton);
        nameField = (EditText) findViewById(R.id.name);
        //primaryBusinessField = (EditText) findViewById(R.id.primaryBusiness);
        addressField = (EditText) findViewById(R.id.address);
        //provinceField = (EditText) findViewById(R.id.province);

        String[] primaryBusinesses = new String[] {"Fisher", "Distributor", "Processor", "Fish Monger"};
        primaryBusinessSpinner = (Spinner) findViewById(R.id.primaryBusinessSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> primaryBusinessAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, primaryBusinesses);
        primaryBusinessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        primaryBusinessSpinner.setAdapter(primaryBusinessAdapter);

        String[] provinces = new String[] {"", "AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT"};
        provinceSpinner = (Spinner) findViewById(R.id.provinceSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, provinces);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        provinceSpinner.setAdapter(provinceAdapter);
    }

    public void submitInfoButton(View v) {
        //each entry needs a unique ID
        String personID = appState.firebaseReference.push().getKey();
        String name = nameField.getText().toString();
        String primaryBusiness = primaryBusinessSpinner.getSelectedItem().toString();
        String address = addressField.getText().toString();
        String province = provinceSpinner.getSelectedItem().toString();

        //Contact person = new Contact(personID, name, email);
        Contact person = new Contact(personID, name, primaryBusiness, address, province);

        appState.firebaseReference.child(personID).setValue(person);

        finish();
    }
}
