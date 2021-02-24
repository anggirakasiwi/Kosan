package com.example.e_kostan.respon;

import com.example.e_kostan.model.Item_pesan;

import java.util.List;
import java.io.Serializable;

public class Response_Pesan implements Serializable {
	private boolean status;
	private List<Item_pesan> pesan;

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setPesan(List<Item_pesan> pesan){
		this.pesan = pesan;
	}

	public List<Item_pesan> getPesan(){
		return pesan;
	}

	@Override
 	public String toString(){
		return 
			"Response_Pesan{" + 
			"status = '" + status + '\'' + 
			",pesan = '" + pesan + '\'' + 
			"}";
		}
}