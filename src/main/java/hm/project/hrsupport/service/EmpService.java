package hm.project.hrsupport.service;

import java.util.stream.Collectors;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.EmpDTO;
import hm.project.hrsupport.entity.Department;
import hm.project.hrsupport.entity.Employee;
import hm.project.hrsupport.exception.ApiRequestException;
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

    // public EmpDTO createEmployee(EmpDTO empDTO) {
    // // Convert DTO to Entity 2 b saved in DB
    // Employee employee = modelMapper.map(empDTO, Employee.class);

    // // Set Department
    // Department department = deptRepository.findById(empDTO.getDepartmentId())
    // .orElseThrow(() -> new RuntimeException("Department not found"));

    // employee.setDepartment(department);

    // // Save and return
    // Employee saveEmp = empRepository.save(employee);

    // return modelMapper.map(saveEmp, EmpDTO.class);
    // }
    public EmpDTO createEmployee(EmpDTO empDTO) {
        // Convert DTO to Entity 2 b saved in DB
        Employee employee = modelMapper.map(empDTO, Employee.class);

        // set Manager(optional)
        if (empDTO.getManagerId() != null) {
            Employee manager = empRepository.findById(empDTO.getManagerId())
                    .orElseThrow(() -> new ApiRequestException("Manager not found with id" + empDTO.getManagerId()));
            employee.setManager(manager);
        }
        // Required departmentId
        if (empDTO.getDepartmentId() == null) {
            throw new ApiRequestException("Department is required for every employee");
        }
        // Set Department (mandatory)
        Department department = deptRepository.findById(empDTO.getDepartmentId())
                .orElseThrow(() -> new ApiRequestException("Department not found"));
        employee.setDepartment(department);


        

        // Save and return
        Employee savedEmp = empRepository.save(employee);

        // Map Back to DTO and assign it 2 a variable(2 send emploee saved response to
        // user) and set extra field manually
        EmpDTO empResponse = modelMapper.map(savedEmp, EmpDTO.class);
        // set field by checking if(Ternary Operator) dea are filled if not will
        // assigned manager as null(i.e for boss/CEO is managerId = null bcz dey dont hv
        // a manager)
        // savedEmp.getManager() → returns an Manager object (the boss/manager).
        // .getId() → returns the Employee ID field of that manager(a Long).
        empResponse.setManagerId(savedEmp.getManager() != null ? savedEmp.getManager().getId() : null);
        // empResponse.setDepartmentId(savedEmp.getDepartment() != null ?
        // savedEmp.getDepartment() : null);
        empResponse.setSubordinateIds(savedEmp.getSubordinates() != null
                ? savedEmp.getSubordinates().stream()
                        .map(sub -> modelMapper.map(sub, EmpDTO.class).getId())
                        .collect(Collectors.toList())
                : null);

        return empResponse;
    }

    public void deleteEmployeeById(Long id) {
        empRepository.deleteById(id);
    }

    public EmpDTO editEmployee(Long id, EmpDTO empDTO) {
        // Fetch the existing employee from the database
        Employee existingEmp = empRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Employee not found with ID:" + id));
        // Ensure DTO ID matches the one we found in the DB
        // (this prevents someone from changing the department ID in the request)
        empDTO.setId(existingEmp.getId());
        // Map only non-null fields from DTO to entity to avoid overwriting with null

        // modelMapper.map(empDTO, emp) — this takes every field in empDTO and assigns
        // it to the emp object.
        // If empDTO has non-null values → it replaces the old values in emp.
        // If empDTO has null values → it overwrites the existing values in emp with
        // null. BUT WITH THIS
        // you can keep the old DB values when a field is missing in the DTO, you
        // need to configure ModelMapper to skip nulls during mapping: with this line of
        // code => modelMapper.getConfiguration().setSkipNullEnabled(true); in the
        // AppConfig class
        // setSkipNullEnabled(true) → Tells ModelMapper not to overwrite existing values
        // with null.
        modelMapper.map(empDTO, existingEmp);

        // Update manager if provided
        if (empDTO.getManagerId() != null) {
            Employee manager = empRepository.findById(empDTO.getManagerId())
                    .orElseThrow(() -> new ApiRequestException("manager not found with id" + empDTO.getManagerId()));
            existingEmp.setManager(manager);
        }
        // If departmentId is provided, update department
        if (empDTO.getDepartmentId() != null) {
            Department dept = deptRepository.findById(empDTO.getDepartmentId())
                    .orElseThrow(() -> new ApiRequestException("Invalid department id" + empDTO.getDepartmentId()));
            existingEmp.setDepartment(dept);
        }
        Employee updatedEmp = empRepository.save(existingEmp);
        // map back to DTO
        EmpDTO empDtoResponse = modelMapper.map(updatedEmp, EmpDTO.class);
        // Set manager ID manually for response (null if no manager)
        empDtoResponse.setManagerId(updatedEmp.getManager() != null ? updatedEmp.getManager().getId() : null);

        // Set subordinate IDs manually for response
        empDtoResponse.setSubordinateIds(updatedEmp.getSubordinates() != null
                ? updatedEmp.getSubordinates().stream()
                        .map(sub -> modelMapper.map(sub, EmpDTO.class).getId())
                        .collect(Collectors.toList())
                : null);
        return empDtoResponse;
    }

}
// MAKE SURE B4 POSTING EMPLOEE THEIR MUST B EXISTING DEPARTMENT
// POST
// {
//   "firstName": "Rahma",
//   "lastName": "Suleiman",
//   "email": "rahma.suleiman@example.com",
//   "phone": "+255712345678",
//   "address": "Mombasa",
//   "gender": "Female",
//   "dob": "1998-04-15",
//   "hireDate": "2023-06-01",
//   "position": "Software Engineer",
//   "salary": 1200000,
//   "status": "ACTIVE",
//   "departmentId": 2
// }