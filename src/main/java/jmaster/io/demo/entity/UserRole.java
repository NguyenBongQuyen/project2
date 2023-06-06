//package jmaster.io.demo.entity;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import lombok.Data;
//
//@Data
//@Table(name = "project2")
//@Entity
//public class UserRole {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id; //0
//	
//	@ManyToOne
//	private User user; //user_id
//
//	private String role;  //admin, student
//	
//}