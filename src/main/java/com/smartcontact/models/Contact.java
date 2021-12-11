package com.smartcontact.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Contact")
public class Contact {

@Id
@GeneratedValue(strategy=GenerationType.AUTO)
  private  int cid;

//@NotBlank(message="Name field is required !!")
  private String cname;

//@NotBlank(message="Nick Name field is required !!")
  private String nickname;

//@NotBlank(message="Work field is required !!")
  private String work;

//@NotBlank(message="Email field is required !!")
  private String cemail;

//@NotBlank(message="Phone Number field is required !!")
  private String phone;

//@NotBlank(message="Imageurl field is required !!")
  private String cimageurl;

//@NotBlank(message="description field is required !!")
  private String cdesc;
  
  @ManyToOne
  @JsonIgnore
  private User user;

public int getCid() {
	return cid;
}

public void setCid(int cid) {
	this.cid = cid;
}

public String getCname() {
	return cname;
}

public void setCname(String cname) {
	this.cname = cname;
}

public String getNickname() {
	return nickname;
}

public void setNickname(String nickname) {
	this.nickname = nickname;
}

public String getWork() {
	return work;
}

public void setWork(String work) {
	this.work = work;
}

public String getCemail() {
	return cemail;
}

public void setCemail(String cemail) {
	this.cemail = cemail;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public String getCimageurl() {
	return cimageurl;
}

public void setCimageurl(String cimageurl) {
	this.cimageurl = cimageurl;
}

public String getCdesc() {
	return cdesc;
}

public void setCdesc(String cdesc) {
	this.cdesc = cdesc;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public Contact(int cid, String cname, String nickname, String work, String cemail, String phone, String cimageurl,
		String cdesc, User user) {
	super();
	this.cid = cid;
	this.cname = cname;
	this.nickname = nickname;
	this.work = work;
	this.cemail = cemail;
	this.phone = phone;
	this.cimageurl = cimageurl;
	this.cdesc = cdesc;
	this.user = user;
}

public Contact() {
	super();
	// TODO Auto-generated constructor stub
}



  

}