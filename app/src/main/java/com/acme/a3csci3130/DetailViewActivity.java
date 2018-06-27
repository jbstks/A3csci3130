package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Activity for the detailed view of a business contact. Shows all the information of the given
 * <code>contact</code>, and gives the option to update or delete it.
 *
 * @author jmfranz
 * @author jbstks
 * @version 06/30/18
 */
public class DetailViewActivity extends AppCompatActivity {

    private EditText businessNumberField, nameField, addressField, provinceField;
    Contact receivedPersonInfo;
    private Spinner primaryBusinessSpinner, provinceSpinner;
    private MyApplicationData appState;

    /**
     * Defines what needs to be done when the activity is created:
     * Grabs the necessary contact data and sets each <code>EditText</code> to its respective content.
     * Sets up the 2 spinners for <code>primaryBusinessSpinner</code> and <code>provinceSpinner</code>.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        receivedPersonInfo = (Contact) getIntent().getSerializableExtra("Contact");

        businessNumberField = (EditText) findViewById(R.id.number);
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
            businessNumberField.setText(receivedPersonInfo.businessNumber);
            nameField.setText(receivedPersonInfo.name);
            for (int i = 0; i < primaryBusinesses.length; i++) {
                if (primaryBusinesses[i].equals(receivedPersonInfo.primaryBusiness))
                    primaryBusinessSpinner.setSelection(i);
            }
            addressField.setText(receivedPersonInfo.address);
            for (int i = 0; i < provinces.length; i++) {
                if (provinces[i].equals(receivedPersonInfo.province))
                    provinceSpinner.setSelection(i);
            }
        }
    }

    /**
     * onClick function for when the "UPDATE" button is pressed.
     * Updates this contact by creating a new <code>contact</code> with the same ID.
     *
     * @param v The current view
     */
    public void updateContact(View v){
        //TODO: Update contact functionality
        String name = nameField.getText().toString();
        String primaryBusiness = primaryBusinessSpinner.getSelectedItem().toString();
        String address = addressField.getText().toString();
        String province = provinceSpinner.getSelectedItem().toString();

        //Contact person = new Contact(personID, name, email);
        Contact person = new Contact(receivedPersonInfo.businessNumber, name, primaryBusiness, address, province);

        appState.firebaseReference.child(receivedPersonInfo.businessNumber).setValue(person);

        finish();
    }

    /**
     * onClick function for when the "ERASE CONTACT" button is pressed.
     * Deletes this contact by removing the child.
     *
     * @param v The current view
     */
    public void eraseContact(View v)
    {
        //TODO: Erase contact functionality
        appState.firebaseReference.child(receivedPersonInfo.businessNumber).removeValue();

        finish();
    }
}
