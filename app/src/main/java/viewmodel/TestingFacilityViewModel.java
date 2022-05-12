package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;

import models.TestingFacilityPackage.TestingFacility;
import repository.TestingFacilityRepository;

public class TestingFacilityViewModel extends AndroidViewModel {

    private final TestingFacilityRepository testingFacilityRepository;

    public TestingFacilityViewModel(@NonNull Application application) {
        super(application);
        testingFacilityRepository = new TestingFacilityRepository(application);
    }

    public TestingFacility getTestingFacility( String API_KEY, String testingFacilityID) throws JSONException, IOException {
        String testingFacilityStr = testingFacilityRepository.getTestingFacility(API_KEY, testingFacilityID);
        TestingFacility testingFacility = new Gson().fromJson(testingFacilityStr, TestingFacility.class);
        return testingFacility;
    }
}
