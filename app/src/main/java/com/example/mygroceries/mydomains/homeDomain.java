package com.example.mygroceries.mydomains;

import java.io.Serializable;

public class homeDomain implements Serializable {
	public String name;
	public String image;
	homeDomain(){}


	public homeDomain(String name, String image) {
		this.name = name;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
