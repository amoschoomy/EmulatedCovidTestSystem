package com.amoschoojs.fit3077;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import models.BookingPackage.TestingOnSiteBooking;
import models.TestingFacilityPackage.TestingFacility;
import models.TestingFacilityPackage.TestingFacilityCollection;
import viewmodel.UserViewModel;

public class SearchTestingSite extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_testing_site);
    RecyclerView recyclerView = findViewById(R.id.recyclerView2);
    Bundle extras = getIntent().getExtras();
    UserViewModel userViewModel = new UserViewModel(getApplication());
    String bookingJSON = null;
    if (extras != null) {
      bookingJSON = extras.getString("key");
    }

    Gson gson = new Gson();
    TestingOnSiteBooking ob = gson.fromJson(bookingJSON, TestingOnSiteBooking.class);
    RecyclerViewAdapterSTS recyclerViewAdapter =
        new RecyclerViewAdapterSTS(getApplication(), ob, this);
    ArrayList<TestingFacility> testingFacilities;
    final String API_KEY = getString(R.string.api_key);
    TestingFacilityCollection testingFacilityCollection = TestingFacilityCollection.getInstance();

    try {
      testingFacilities = testingFacilityCollection.retrieveTestingFacilities(API_KEY);
      recyclerViewAdapter.setCars(testingFacilities);
      LinearLayoutManager layoutManager = new LinearLayoutManager(this);
      recyclerView.setLayoutManager(layoutManager);
      recyclerView.setAdapter(recyclerViewAdapter);
    } catch (IOException e) {
      e.printStackTrace();
    }
    SearchView searchView = (SearchView) findViewById(R.id.searchView2);

    searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

    searchView.setOnQueryTextListener(
        new SearchView.OnQueryTextListener() {
          @Override
          public boolean onQueryTextSubmit(String query) {
            return false;
          }

          @Override
          public boolean onQueryTextChange(String newText) {
            recyclerViewAdapter.getFilter().filter(newText);
            return false;
          }
        });
  }
}
