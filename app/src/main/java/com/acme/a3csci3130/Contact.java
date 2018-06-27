package com.acme.a3csci3130;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines how the data will be stored in the Firebase database. This is converted to a JSON format.
 *
 * @author jmfranz
 * @author jbstks
 * @version 06/30/18
 */

public class Contact implements Serializable {

    public  String businessNumber;
    public  String name;
    public  String primaryBusiness;
    public  String address;
    public  String province;

    /**
     * Default constructor required for calls to DataSnapshot.getValue.
     */
    public Contact() {
    }

    /**
     * Constructor to create a contact object.
     *
     * @param businessNumber    ID of the business contact
     * @param name              name of the business contact
     * @param primaryBusiness   type of business the business contact primarily does
     * @param address           address of the business contact
     * @param province          province or territory of the business contact
     */
    public Contact(String businessNumber, String name, String primaryBusiness, String address, String province){
        this.businessNumber = businessNumber;
        this.name = name;
        this.primaryBusiness = primaryBusiness;
        this.address = address;
        this.province = province;
    }

    /**
     * Function to map the Contact data to go into the Firebase database.
     *
     * @return the mapped data of the Contact
     */
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("businessNumber", businessNumber);
        result.put("name", name);
        result.put("primaryBusiness", primaryBusiness);
        result.put("address", address);
        result.put("province", province);

        return result;
    }
}
