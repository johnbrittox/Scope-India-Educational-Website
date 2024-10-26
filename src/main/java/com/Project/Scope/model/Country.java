package com.Project.Scope.model;

import jakarta.persistence.*;

@Entity
@Table(name="country")
public class Country {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int Countryid;

private String countryname;

public int getCountryid() {
	return Countryid;
}

public void setCountryid(int Countryid) {
	this.Countryid = Countryid;
}

public String getCountryname() {
	return countryname;
}

public void setCountryname(String countryname) {
	this.countryname = countryname;
}


}
