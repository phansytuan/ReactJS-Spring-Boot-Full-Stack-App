package com.tuancode.ems.repositories;

import com.tuancode.ems.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> { // pass in <Type of the Entity, type of the PK>

}
/* Khi kế thừa JpaRepository, bạn tự động có sẵn các phương thức như:

  findAll()
  findById(id)
  save(entity)
  deleteById(id)
  ...
*/