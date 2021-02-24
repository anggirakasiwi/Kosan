package com.example.e_kostan.respon;

import java.util.List;
//import javax.annotation.Generated;
import com.example.e_kostan.model.Item_Kosan;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

//@Generated("com.robohorse.robopojogenerator")
public class Response_Kosan implements Serializable {

	@SerializedName("status")
	private boolean status;

	@SerializedName("kosan")
	private List<Item_Kosan> kosan;

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setKosan(List<Item_Kosan> kosan){
		this.kosan = kosan;
	}

	public List<Item_Kosan> getKosan(){
		return kosan;
	}

	@Override
 	public String toString(){
		return 
			"Response_Kosan{" + 
			"status = '" + status + '\'' + 
			",kosan = '" + kosan + '\'' + 
			"}";
		}
}