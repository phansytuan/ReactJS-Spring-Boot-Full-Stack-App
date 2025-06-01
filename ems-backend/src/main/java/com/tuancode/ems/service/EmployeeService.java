package com.tuancode.ems.service;

import com.tuancode.ems.dto.EmployeeDto;
import java.util.List;

public interface EmployeeService {

  EmployeeDto createEmployee(EmployeeDto employeeDto);

  EmployeeDto getEmployeeById(Long employeeId);

  List<EmployeeDto> getAllEmployees();

  EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee); /* custom */

  void deleteEmployee(Long employeeId);
}