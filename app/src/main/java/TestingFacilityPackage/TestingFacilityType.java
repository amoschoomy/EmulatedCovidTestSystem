package TestingFacilityPackage;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public enum TestingFacilityType {
  @SerializedName("DRIVE_THROUGH")
  DRIVE_THROUGH("Drive Through"),
  @SerializedName("WALK_IN")
  WALK_IN("Walk In"),
  @SerializedName("CLINIC")
  CLINIC("Clinic"),
  @SerializedName("GP")
  GP("GP"),
  @SerializedName("HOSPITAL")
  HOSPITAL("Hospital");

  private String string;

  // constructor to set the string
  TestingFacilityType(String name) {
    string = name;
  }

  @Override
  @NonNull
  public String toString() {
    return string;
  }
}
