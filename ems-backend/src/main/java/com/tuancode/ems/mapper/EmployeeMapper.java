package com.tuancode.ems.mapper;

import com.tuancode.ems.dto.EmployeeDto;
import com.tuancode.ems.entities.EmployeeEntity;
import org.mapstruct.Mapper;

// separate the (common logic) mapping job instead of writing the same logic in all classes
@Mapper(componentModel = "spring")
public interface EmployeeMapper {

  // Entity -> DTO
  EmployeeDto mapToDto(EmployeeEntity employeeEntity);

  // DTO -> Entity
  EmployeeEntity mapToEntity(EmployeeDto employeeDto);
}

/*
    public class EmployeeMapper {
      public static EmployeeDto mapToDto(EmployeeEntity employeeEntity) {
        return new EmployeeDto(
            employeeEntity.getId(),
            employeeEntity.getFirstName(),
            employeeEntity.getLastName(),
            employeeEntity.getEmail()
        );
      }
      public static EmployeeEntity mapToEntity(EmployeeDto employeeDto) {
        return new EmployeeEntity(
            employeeDto.getFirstName(),
            employeeDto.getLastName(),
            employeeDto.getEmail()
        );
      }
    }
*/