package com.example.e_kostan.model;

//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

//@Generated("com.robohorse.robopojogenerator")
public class Item_Kosan implements Serializable {

	@SerializedName("id")
	private String id;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("nama_kostan")
	private String namaKostan;

	@SerializedName("harga")
	private String harga;

	@SerializedName("fasilitas")
	private String fasilitas;

	@SerializedName("durasi")
	private String durasi;

	@SerializedName("latitutude")
	private String latitutude;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("no_hp")
	private String nomorHp;

	@SerializedName("distance")
	private String distance;

	@SerializedName("total_rating")
	private String total_rating;


	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setNamaKostan(String namaKostan){
		this.namaKostan = namaKostan;
	}

	public String getNamaKostan(){
		return namaKostan;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setFasilitas(String fasilitas){
		this.fasilitas = fasilitas;
	}

	public String getFasilitas(){
		return fasilitas;
	}

	public void setDurasi(String durasi){
		this.durasi = durasi;
	}

	public String getDurasi(){
		return durasi;
	}

	public void setLatitutude(String latitutude){
		this.latitutude = latitutude;
	}

	public String getLatitutude(){
		return latitutude;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

	public void setNomorHp(String nomorHp){
		this.nomorHp = nomorHp;

	}

	public String getNomorHp(){
		return nomorHp;
	}

	public void setDistance(String distance){
		this.distance = distance;

	}
	public String getTotal_rating(){
		return total_rating;
	}

	public void setTotal_rating(String rating){
		this.total_rating = rating;

	}

	public String getDistance(){
		return distance;
	}

	@Override
 	public String toString(){
		return 
			"Item_Kosan{" + 
			"id = '" + id + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",nama_kostan = '" + namaKostan + '\'' + 
			",harga = '" + harga + '\'' + 
			",fasilitas = '" + fasilitas + '\'' + 
			",durasi = '" + durasi + '\'' + 
			",latitutude = '" + latitutude + '\'' + 
			",longitude = '" + longitude + '\'' + 
			",id_user = '" + idUser + '\'' +
					",total_rating = '" + total_rating + '\'' +
			",gambar = '" + gambar + '\'' +
					",distance = '" + distance + '\'' +
			",nomorHp = '" + nomorHp + '\'' +
			"}";
		}
}