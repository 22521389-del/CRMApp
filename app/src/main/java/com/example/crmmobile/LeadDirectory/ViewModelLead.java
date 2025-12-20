package com.example.crmmobile.LeadDirectory;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModelLead extends ViewModel {
    public MutableLiveData<String> title = new MutableLiveData<>();
    public MutableLiveData<String> first_name = new MutableLiveData<>();
    public MutableLiveData<String> company = new MutableLiveData<String>();
    public MutableLiveData<String> Job = new MutableLiveData<>();
    public MutableLiveData<String> hovatendem = new MutableLiveData<>();
    public MutableLiveData<String> phonenumber = new MutableLiveData<>();
    public MutableLiveData<String> Email = new MutableLiveData<>();
    public MutableLiveData<String> Sex = new MutableLiveData<>();
    public MutableLiveData<String> Birthday = new MutableLiveData<>();
    public MutableLiveData<String> Address = new MutableLiveData<>();
    public MutableLiveData<String> Province = new MutableLiveData<>();
    public MutableLiveData<String> Nation = new MutableLiveData<>();
    public MutableLiveData<String> District = new MutableLiveData<>();
    public MutableLiveData<String> position_company = new MutableLiveData<>();
    public MutableLiveData<String> number_of_employees = new MutableLiveData<>();
    public MutableLiveData<String> state = new MutableLiveData<>();
    public MutableLiveData<String> Note = new MutableLiveData<>();
    public MutableLiveData<String> description = new MutableLiveData<>();
    public MutableLiveData<String> SendtoName = new MutableLiveData<>();
    public MutableLiveData<Integer> SendtoID = new MutableLiveData<>();
    public MutableLiveData<String> CreatedByName = new MutableLiveData<>();
    public MutableLiveData<Integer> CreatedByID = new MutableLiveData<>();
    public MutableLiveData<String> potential = new MutableLiveData<>();
    public MutableLiveData<String> contact_day = new MutableLiveData<>();
    public MutableLiveData<String> Tax = new MutableLiveData<>();
    public MutableLiveData<String> Revenue = new MutableLiveData<>();
    public MutableLiveData<String> Evaluate = new MutableLiveData<>();
}
