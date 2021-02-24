package com.example.e_kostan.respon;

//import java.util.List;
//import javax.annotation.Generated;
//import com.google.gson.annotations.SerializedName;
//import java.io.Serializable;

import com.example.e_kostan.model.Item_User;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

//@Generated("com.robohorse.robopojogenerator")
public class Response_User implements Serializable {

	@SerializedName("status")
	private boolean status;

	@SerializedName("user")
	private List<Item_User> user;

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setUser(List<Item_User> user){
		this.user = user;
	}

	public List<Item_User> getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"Response_User{" + 
			"status = '" + status + '\'' + 
			",user = '" + user + '\'' + 
			"}";
		}
}