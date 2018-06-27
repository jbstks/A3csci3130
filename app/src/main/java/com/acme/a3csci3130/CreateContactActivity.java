package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Activity to create a new <code>Contact</code> after pressing the "CREATE CONTACT" button in <code>MainActivity</code>
 * Shows all of input fields necessary to create a <code>Contact</code>, as well as a "SUBMIT" button.
 *
 * @author jmfranz
 * @author jbstks
 * @version 06/30/18
 */
public class CreateContactActivity extends AppCompatActivity {

    private Button submitButton;
    private EditText businessNumberField, nameField, addressField;
    private Spinner primaryBusinessSpinner, provinceSpinner;
    private MyApplicationData appState;

    /**
     * Defines what needs to be done when the activity is created:
     * Sets up the 2 spinners for <code>primaryBusinessSpinner</code> and <code>provinceSpinner</code>.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_activity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        submitButton = (Button) findViewById(R.id.submitButton);
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
    }

    /**
     * onClick function for when the "SUBMIT" button is pressed.
     * Uses the inputted data to create a <code>Contact</code> object and sends that <code>Contact</code> object to Firebase.
     *
     * @param v the current view.
     */
    public void submitInfoButton(View v) {
        //each entry needs a unique ID
        //String personID = appState.firebaseReference.push().getKey();
        String businessNumber = businessNumberField.getText().toString();
        String name = nameField.getText().toString();
        String primaryBusiness = primaryBusinessSpinner.getSelectedItem().toString();
        String address = addressField.getText().toString();
        String province = provinceSpinner.getSelectedItem().toString();

        TextInputLayout numberInputLayout = (TextInputLayout) findViewById(R.id.numberInputLayout);
        TextInputLayout nameInputLayout = (TextInputLayout) findViewById(R.id.nameInputLayout);
        TextInputLayout addressInputLayout = (TextInputLayout) findViewById(R.id.addressInputLayout);

        // Checking for errors in the input
        boolean error = false;
        if (businessNumber.equals("")) {
            numberInputLayout.setError((CharSequence) "Business number is required");
            error = true;
        } else if (businessNumber.length() != 9) {
            numberInputLayout.setError((CharSequence) "Business number needs to be 9 characters");
            error = true;
        }
        if (name.equals("")) {
            nameInputLayout.setError((CharSequence) "Name is required");
            error = true;
        } else if  (name.length() < 2 || name.length() > 48) {
            nameInputLayout.setError((CharSequence) "Name needs to be 2-48 characters");
            error = true;
        }
        if (address.length() >= 50) {
            addressInputLayout.setError((CharSequence) "Address needs be be less than 50 characters");
            error = true;
        }
        if (!error) {
            Contact person = new Contact(businessNumber, name, primaryBusiness, address, province);

            appState.firebaseReference.child(businessNumber).setValue(person);

            finish();
        }
    }
}
