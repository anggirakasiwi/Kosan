package com.example.e_kostan.model;

//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

//@Generated("com.robohorse.robopojogenerator")
public class Item_pesan implements Serializable {

	@SerializedName("id")
	private String id;

	@SerializedName("isi_pesan")
	private String isiPesan;

	@SerializedName("pengirim")
	private String pengirim;

	@SerializedName("penerima")
	private String penerima;

	@SerializedName("nama_pengirim")
	private String namaPengirim;

	@SerializedName("tanggal")
	private String tanggal;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setIsiPesan(String isiPesan){
		this.isiPesan = isiPesan;
	}

	public String getIsiPesan(){
		return isiPesan;
	}

	public void setPengirim(String pengirim){
		this.pengirim = pengirim;
	}

	public String getPengirim(){
		return pengirim;
	}

	public void setPenerima(String penerima){
		this.penerima = penerima;
	}

	public String getPenerima(){
		return penerima;
	}

	public void setNamaPengirim(String namaPengirim){
		this.namaPengirim = namaPengirim;
	}

	public String getNamaPengirim(){
		return namaPengirim;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	@Override
 	public String toString(){
		return 
			"Item_pesan{" + 
			"id = '" + id + '\'' + 
			",isi_pesan = '" + isiPesan + '\'' + 
			",pengirim = '" + pengirim + '\'' + 
			",penerima = '" + penerima + '\'' + 
			",nama_pengirim = '" + namaPengirim + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			"}";
		}
}