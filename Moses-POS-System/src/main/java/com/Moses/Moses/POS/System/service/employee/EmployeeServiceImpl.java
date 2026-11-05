package com.Moses.Moses.POS.System.service.employee;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.Moses.Moses.POS.System.mapper.UserMapper;
import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.modal.UserRole;
import com.Moses.Moses.POS.System.modal.branchModal.Branch;
import com.Moses.Moses.POS.System.modal.storeModal.Store;
import com.Moses.Moses.POS.System.payload.dto.UserDto;
import com.Moses.Moses.POS.System.repository.BranchRepository;
import com.Moses.Moses.POS.System.repository.StoreRepository;
import com.Moses.Moses.POS.System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDto createStoreEmployee(UserDto employee, Long storeId) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(
                ()-> new Exception("Store not found!")
        );
        Branch branch = null;
        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER){
            if(employee.getBranchId()==null){
                throw new Exception("Branch id is required to create branch manager");
            }
            branch = branchRepository.findById(employee.getBranchId()).orElseThrow(
                    ()-> new Exception("Branch is not found!")
            );
        }
        User user = UserMapper.toEntity(employee);
        user.setStore(store);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(employee.getPassword()));
        User savedEmployee = userRepository.save(user);
        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER && branch != null){
            branch.setManager(savedEmployee);
            branchRepository.save(branch);
        }
        return UserMapper.toDTO(savedEmployee);
    }

    @Override
    public UserDto createBranchEmployee(UserDto employee, Long branchId) throws Exception {
        Branch branch = branchRepository.findById(branchId).orElseThrow(
                ()-> new Exception("Branch ont found!")
        );
        if(employee.getRole()==UserRole.ROLE_BRANCH_CASHIER || employee.getRole()==UserRole.ROLE_BRANCH_MANAGER){
            User user = UserMapper.toEntity(employee);
            user.setBranch(branch);
            user.setPassword(passwordEncoder.encode(employee.getPassword()));
            return UserMapper.toDTO(userRepository.save(user));
        }
        throw new Exception("Branch role not supported");
    }

    @Override
    public User updateEmployee(Long employeeId, UserDto employeeDetails) throws Exception {
        User existingEmployee = userRepository.findById(employeeId).orElseThrow(
                ()-> new Exception("Employee does not exist with the given id!")
        );
        Branch branch = branchRepository.findById(employeeDetails.getBranchId())
                        .orElseThrow(()-> new Exception("Branch does not exist!"));
        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setFullName(employeeDetails.getFullName());
        existingEmployee.setPassword(employeeDetails.getPassword());
        existingEmployee.setRole(employeeDetails.getRole());
        existingEmployee.setBranch(branch);
        return userRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) throws Exception {
        User employee = userRepository.findById(employeeId).orElseThrow(
                ()-> new Exception("Employee not found!")
        );

        userRepository.delete(employee);
    }

    @Override
    public List<UserDto> findStoreEmployees(Long storeId, UserRole role) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(
                ()-> new Exception("Store not found!")
        );
        return userRepository.findByStore(store).stream().filter(user -> role == null ||user.getRole()==role).map(UserMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findBranchEmployees(Long branchId, UserRole role) throws Exception {
         Branch branch = branchRepository.findById(branchId).orElseThrow(
                 ()-> new Exception("Branch not found!")
         );
        return userRepository.findByBranchId(branchId).stream().filter(
                user -> role == null || user.getRole()==role
        ).map(UserMapper::toDTO).collect(Collectors.toList());
    }
}
