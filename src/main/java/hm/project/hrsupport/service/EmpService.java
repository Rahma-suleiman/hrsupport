package hm.project.hrsupport.service;

import java.util.stream.Collectors;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.EmpDTO;
import hm.project.hrsupport.entity.Department;
import hm.project.hrsupport.entity.Employee;
import hm.project.hrsupport.repository.DeptRepository;
import hm.project.hrsupport.repository.EmpRepository;

@Service
public class EmpService {

    private ModelMapper modelMapper;
    private EmpRepository empRepository;
    private DeptRepository deptRepository;

    public EmpService(ModelMapper modelMapper, EmpRepository empRepository, DeptRepository deptRepository) {
        this.modelMapper = modelMapper;
        this.empRepository = empRepository;
        this.deptRepository = deptRepository;
    }

    public List<EmpDTO> getAllEmployee() {
        List<Employee> employees = empRepository.findAll();
        return employees.stream()
                .map(emp -> modelMapper.map(emp, EmpDTO.class))
                .collect(Collectors.toList());
    }

    public EmpDTO getEmployeeById(Long id) {
        Employee empId = empRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("emploee not found with id" + id));
        return modelMapper.map(empId, EmpDTO.class);
    }

    public EmpDTO createEmployee(EmpDTO empDTO) {
        // Convert DTO to Entity 2 b saved in DB
        Employee employee = modelMapper.map(empDTO, Employee.class);

        // Set Department
        Department department = deptRepository.findById(empDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        employee.setDepartment(department);

        // Save and return
        Employee saveEmp = empRepository.save(employee);

        return modelMapper.map(saveEmp, EmpDTO.class);
    }

    public void deleteEmployeeById(Long id) {
        empRepository.deleteById(id);
    }

    public EmpDTO editEmployee(Long id, EmpDTO empDTO) {
        // emp is fetched from the database — so at this point, it’s a fully populated
        // Employee entity with existing values.
        Employee existingEmp = empRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Employee not found with ID:" + id));
        // Map only non-null fields from DTO to entity to avoid overwriting with null

        // modelMapper.map(empDTO, emp) — this takes every field in empDTO and assigns
        // it to the emp object.
        // If empDTO has non-null values → it replaces the old values in emp.
        // If empDTO has null values → it overwrites the existing values in emp with
        // null. BUT WITH THIS
        // you can keep the old DB values when a field is missing in the DTO, you
        // need to configure ModelMapper to skip nulls during mapping: with this line of
        // code (modelMapper.getConfiguration().setSkipNullEnabled(true);)
        // setSkipNullEnabled(true) → Tells ModelMapper not to overwrite existing values with null.
        modelMapper.map(empDTO, existingEmp);
        // If departmentId is provided, update department
        if (empDTO.getDepartmentId() != null) {
            Department dept = deptRepository.findById(empDTO.getDepartmentId())
                    .orElseThrow(() -> new IllegalStateException("Invalid department id" + empDTO.getDepartmentId()));
            existingEmp.setDepartment(dept);
        }
        Employee updateEmp = empRepository.save(existingEmp);
        return modelMapper.map(updateEmp, EmpDTO.class);
    }

}
// MAKE SURE B4 POSTING EMPLOEE THEIR MUST B EXISTING DEPARTMENT 
// POST
// {
//   "firstName": "Amhar",
//   "lastName": "Suleiman",
//   "email": "amhar@example.com",
//   "phone": "0765359987",
//   "address": "Mombasa",
//   "gender": "Female",
//   "dob": "2000-08-18",
//   "hireDate": "2020-08-18",
//   "position": "Software Developer",
//   "salary": 1238900,
//   "status": "Active",
//   "departmentId": 2
// }
//  {
//   "firstName":"Shuab",
//   "lastName": "Moha",
//   "email": "shuab@example.com",
//   "phone": "076792873",
//   "address": "Malindi",
//   "gender": "Male",
//   "dob": "2001-05-05",
//   "hireDate": "2023-02-14",
//   "position": "Software Developer",
//   "salary": 1970000,
//   "status": "Active",
//   "departmentId": 1
// }
// PUT
// {
//   "firstName": "Rahma",
//   "lastName": "Sule",
//   "email": "rahma@gmail.com",
//   "phone": "098765456789",
//   "address": "Kilifi",
//   "gender": "Female",
//   "dob": "2000-01-11",
//   "hireDate": "2009-08-14",
//   "position": "Programming",
//   "salary": 3200000,
//   "status": "On Leave",
//   "departmentId": 1
// }
// OR WHEN UPDATING FEW FIELDS
// {
//   "firstName": "Shuayb",
//   "lastName": "Mohammed"
// }