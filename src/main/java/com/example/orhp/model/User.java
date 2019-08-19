package com.example.orhp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity
public class User {

    @Id
    private String id;
	private String name;
    private String email;
    @ManyToMany(mappedBy="attendees", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private List<Meetup> meetups;
    
    public User() {} 
    public User(String id, String name) {
    	this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
//	public List<Meetup> getMeetups() {
//		return meetups;
//	}
//	public void setMeetups(List<Meetup> meetups) {
//		this.meetups = meetups;
//	}
    
    
}
