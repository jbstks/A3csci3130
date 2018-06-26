package com.acme.a3csci3130;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The main activity of the app.
 *
 * @author jmfranz
 * @author jbstks
 * @version 06/30/18
 */
public class MainActivity extends AppCompatActivity {

    private ListView contactListView;
    private FirebaseListAdapter<Contact> firebaseAdapter;

    /**
     * Defines what needs to be done when the activity is created:
     * Sets up the Firebase connection.
     * Sets up the <code>ListView</code> for the database contents.
     * When a list item is pressed, it gets that specific Contact data and sends it to <code>DetailViewActivity</code>.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the app wide shared variables
        MyApplicationData appData = (MyApplicationData) getApplication();

        //Set-up Firebase
        appData.firebaseDBInstance = FirebaseDatabase.getInstance();
        appData.firebaseReference = appData.firebaseDBInstance.getReference("contacts");

        //Get the reference to the UI contents
        contactListView = (ListView) findViewById(R.id.listView);

        //Set up the List View
        firebaseAdapter = new FirebaseListAdapter<Contact>(this, Contact.class,
                android.R.layout.simple_list_item_1, appData.firebaseReference) {
            @Override
            protected void populateView(View v, Contact model, int position) {
                TextView contactName = (TextView) v.findViewById(android.R.id.text1);
                //TextView contactBusiness = (TextView) v.findViewById(android.R.id.text2);
                contactName.setText(model.name);
                //contactBusiness.setText(model.primaryBusiness);
            }
        };
        contactListView.setAdapter(firebaseAdapter);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // onItemClick method is called everytime a user clicks an item on the list
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact person = (Contact) firebaseAdapter.getItem(position);
                showDetailView(person);
            }
        });
    }

    /**
     * onClick function for when the "CREATE CONTACT" button is pressed.
     * Goes to <code>CreateContactActivity</code> on press.
     *
     * @param v The current view
     */
    public void createContactButton(View v)
    {
        Intent intent = new Intent(this, CreateContactActivity.class);
        startActivity(intent);
    }

    /**
     * onClick function for when a list item (contact) is pressed.
     * Goes to <code>DetailViewActivity</code> on press, sends the pressed <code>Contact</code> as well.
     *
     * @param person The contact in which more detail is requested
     */
    private void showDetailView(Contact person)
    {
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putExtra("Contact", person);
        startActivity(intent);
    }
}
