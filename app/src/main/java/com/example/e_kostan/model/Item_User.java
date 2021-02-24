package com.example.e_kostan.model;

//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

//@Generated("com.robohorse.robopojogenerator")
public class Item_User implements Serializable {

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	@SerializedName("password")
	private String password;

	@SerializedName("nama")
	private String nama;

	@SerializedName("no_hp")
	private String noHp;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("jenis_kelamin")
	private String jenisKelamin;

	@SerializedName("role")
	private String role;

	@SerializedName("gambar")
	private String gambar;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setNoHp(String noHp){
		this.noHp = noHp;
	}

	public String getNoHp(){
		return noHp;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setJenisKelamin(String jenisKelamin){
		this.jenisKelamin = jenisKelamin;
	}

	public String getJenisKelamin(){
		return jenisKelamin;
	}

	public void setRole(String role){
		this.role = role;
	}

	public String getRole(){
		return role;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

	@Override
 	public String toString(){
		return 
			"Item_User{" + 
			"id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",password = '" + password + '\'' + 
			",nama = '" + nama + '\'' + 
			",no_hp = '" + noHp + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",jenis_kelamin = '" + jenisKelamin + '\'' + 
			",role = '" + role + '\'' + 
			",gambar = '" + gambar + '\'' + 
			"}";
		}
}