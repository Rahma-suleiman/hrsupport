package hm.project.hrsupport.service;

import java.util.List;
import java.util.stream.Collectors;

// import org.hibernate.annotations.processing.Find;
// import org.hibernate.sql.Update;
import org.modelmapper.ModelMapper;
// import org.modelmapper.internal.bytebuddy.asm.Advice.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.DeptDTO;
import hm.project.hrsupport.entity.Department;
import hm.project.hrsupport.exception.ApiRequestException;
import hm.project.hrsupport.repository.DeptRepository;

@Service
public class DeptService {

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<DeptDTO> getAllDepartment() {
        List<Department> depts = deptRepository.findAll();
        // throw new IllegalStateException("Oops cannot get all department");
        return depts.stream()
        .map(dept -> modelMapper.map(dept, DeptDTO.class))
        .collect(Collectors.toList());
        // deptRepository.findAll();
        // throw new ApiRequestException("Oops cannot get all department with custom exception");
    }

    public DeptDTO getDeptById(Long id) {
        // WITHOUT CUSTOM EXCEPTION HANDLER
        // Department deptId = deptRepository.findById(id)
        // .orElseThrow(() -> new RuntimeException("Department not found));

        // USING custom ApiRequestException so that your ApiExceptionHandler catches it
        // and returns a clean error response.
        Department deptId = deptRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Department not found with id" + id));
        return modelMapper.map(deptId, DeptDTO.class);
    }

    public DeptDTO creatDepartment(DeptDTO deptDTO) {
        Department department = modelMapper.map(deptDTO, Department.class);
        Department saveDept = deptRepository.save(department);
        return modelMapper.map(saveDept, DeptDTO.class);
    }

    // Request DTO → Find in DB → Update Entity → Save → Return DTO.
    public DeptDTO editDepartment(Long id, DeptDTO deptDTO) {
        // 1. Find department by ID in the DB or throw an error(ApiRequestException) if
        // not found.
        Department existingDepart = deptRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Department ID not found"));

        // 2. Ensure DTO ID matches the one we found in the DB
        // (this prevents someone from changing the department ID in the request)
        deptDTO.setId(existingDepart.getId());

        // 3. Copy values frm DeptDTO obj into the existing Department entity
        // Update the existing department with new values from DTO
        // (this updates the entity's fields with the new values from the request)
        modelMapper.map(deptDTO, existingDepart);

        // 4. Save the updated Department entity back into the database
        Department updatedDept = deptRepository.save(existingDepart);

        // 5. Convert updated Department entity back into a DeptDTO object n return it
        // (so we can send it back as the response to the API call)
        return modelMapper.map(updatedDept, DeptDTO.class);
    }

    public void deleteDepartment(Long id) {
        deptRepository.deleteById(id);
    }

}

// {
//     "name": "IT"
//   },
//   {
//     "name": "HR"
//   },
//   {
//     "name": "Account"
//   },
//   {
//     "name": "Science"
//   },
//   {
//     "name": "Agriculture"
//   },
//   {
//     "name": "Finance"
//   },
//   {
//     "name": "Procurement"
//   },
//   {
//     "name": "Production"
//   },
//   {
//     "name": "Sale and Marketing"
//   }