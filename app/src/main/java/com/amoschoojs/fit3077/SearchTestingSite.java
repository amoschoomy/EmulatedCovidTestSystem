package com.amoschoojs.fit3077;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import TestingFacilityPackage.TestingFacility;
import TestingFacilityPackage.TestingFacilityCollection;

public class SearchTestingSite extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_testing_site);
    RecyclerView recyclerView = findViewById(R.id.recyclerView2);
    RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter();
    ArrayList<TestingFacility> testingFacilities;
    final String API_KEY = getString(R.string.api_key);
    TestingFacilityCollection testingFacilityCollection = TestingFacilityCollection.getInstance();
    try {
      testingFacilities = testingFacilityCollection.retrieveTestingFacilities(API_KEY);
      //      Log.d("myTag",Integer.toString(testingFacilities.size()));
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