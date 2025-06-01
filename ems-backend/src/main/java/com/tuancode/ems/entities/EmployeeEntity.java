package com.tuancode.ems.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // tạo constructor không tham số.
@AllArgsConstructor // tạo constructor với tất cả các tham số.
@Entity
@Table(name = "employees")
public class EmployeeEntity { // a Employee POJO class -> Employee entity

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name") // ánh xạ cột trong bảng vs tên cột cụ thể và các ràng buộc (nullable, unique...).
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email_id", nullable = false, unique = true)
  private String email;
  
  @Column(name = "department", nullable = false)
  private String department;
}