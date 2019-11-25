package com.gvtechcom.myshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataUser {

@SerializedName("object_user_id")
@Expose
private String objectUserId;
@SerializedName("name")
@Expose
private String name;
@SerializedName("gender")
@Expose
private String gender;
@SerializedName("email")
@Expose
private String email;
@SerializedName("birthday")
@Expose
private String birthday;
@SerializedName("telephone")
@Expose
private String telephone;

public String getObjectUserId() {
return objectUserId;
}

public void setObjectUserId(String objectUserId) {
this.objectUserId = objectUserId;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getGender() {
return gender;
}

public void setGender(String gender) {
this.gender = gender;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getBirthday() {
return birthday;
}

public void setBirthday(String birthday) {
this.birthday = birthday;
}

public String getTelephone() {
return telephone;
}

public void setTelephone(String telephone) {
this.telephone = telephone;
}

}