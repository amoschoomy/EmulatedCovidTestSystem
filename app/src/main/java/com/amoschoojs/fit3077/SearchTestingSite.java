package com.amoschoojs.fit3077;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import TestingFacilityPackage.TestingFacilityCollection;

public class SearchTestingSite extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_testing_site);
    final String API_KEY = getString(R.string.api_key);
    TestingFacilityCollection testingFacilityCollection = TestingFacilityCollection.getInstance();
    testingFacilityCollection.retrieveTestingFacilities(API_KEY);
  }

}
