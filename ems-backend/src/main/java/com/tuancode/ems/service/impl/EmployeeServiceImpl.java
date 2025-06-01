package com.tuancode.ems.service.impl;

import com.tuancode.ems.dto.EmployeeDto;
import com.tuancode.ems.entities.EmployeeEntity;
import com.tuancode.ems.exception.ResourceNotFoundException;
import com.tuancode.ems.mapper.EmployeeMapper;
import com.tuancode.ems.repositories.EmployeeRepository;
import com.tuancode.ems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;

  @Override
  public EmployeeDto createEmployee(EmployeeDto employeeDto) {
    EmployeeEntity employeeEntity = employeeMapper.mapToEntity(employeeDto); // ✅ instance method
    employeeEntity.setId(null); // ensure null to trigger auto-generation

    EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
    return employeeMapper.mapToDto(savedEmployee);
  }

  @Override
  public EmployeeDto getEmployeeById(Long employeeId) {
    EmployeeEntity employeeEntity = employeeRepository.findById(employeeId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with id: " + employeeId));

    return employeeMapper.mapToDto(employeeEntity);
  }

  @Override
  public List<EmployeeDto> getAllEmployees() {
    List<EmployeeEntity> employees = employeeRepository.findAll();
    
    return employees.stream()
        .map(employeeMapper::mapToDto) // ✅ instance method reference
        .collect(Collectors.toList());
  }

  @Override
  public EmployeeDto updateEmployee(Long employeeId,
                                    EmployeeDto updatedEmployee)
  {
    EmployeeEntity employeeEntity = employeeRepository.findById(employeeId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with id: " + employeeId));

    employeeEntity.setFirstName(updatedEmployee.getFirstName());
    employeeEntity.setLastName(updatedEmployee.getLastName());
    employeeEntity.setEmail(updatedEmployee.getEmail());
    employeeEntity.setDepartment(updatedEmployee.getDepartment());

    EmployeeEntity updatedEmployeeObj = employeeRepository.save(employeeEntity);
    return employeeMapper.mapToDto(updatedEmployeeObj);
  }

  @Override
  public void deleteEmployee(Long employeeId) {
    EmployeeEntity employeeEntity = employeeRepository.findById(employeeId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with id: " + employeeId));

    employeeRepository.delete(employeeEntity);
  }
}