package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

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
        return testingFacilityRepository.getTestingFacility(API_KEY, testingFacilityID);
    }
}
