package com.tuancode.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// full combo
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto { // use Dto class to transfer the data between client & server. We use this as a response for our REST APIs.

  private long id;
  private String firstName;
  private String lastName;
  private String email;
  private String department;
}
// định nghĩa các biến thể hiện (instance variables)