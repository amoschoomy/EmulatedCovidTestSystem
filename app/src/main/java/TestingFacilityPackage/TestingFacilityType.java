package TestingFacilityPackage;

import com.google.gson.annotations.SerializedName;

public enum TestingFacilityType {
  @SerializedName("DRIVE_THROUGH")
  DRIVE_THROUGH,
  @SerializedName("WALK_IN")
  WALK_IN,
  @SerializedName("CLINIC")
  CLINIC,
  @SerializedName("GP")
  GP,
  @SerializedName("HOSPITAL")
  HOSPITAL
}
