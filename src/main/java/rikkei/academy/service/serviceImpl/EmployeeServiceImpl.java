package rikkei.academy.service.serviceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Employee;
import rikkei.academy.model.Role;
import rikkei.academy.repository.EmployeeRepository;
import rikkei.academy.repository.RoleRepository;
import rikkei.academy.service.EmployeeService;
import rikkei.academy.service.dto.EmployeeDTO;
import rikkei.academy.service.mapper.EmployeeMapper;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final RoleRepository roleRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, RoleRepository roleRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        if (employeeDTO.getRoles() != null) {
            Set<Role> roles = employeeDTO
                    .getRoles()
                    .stream()
                    .map(roleRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            employee.setRoles(roles);
        }
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    public Page<EmployeeDTO> findAll(String textSearch, Pageable pageable) {
        return employeeRepository.findAllByNameOrEmailContainingIgnoreCase(textSearch, textSearch, pageable).map(employeeMapper::toDto);
    }

    @Override
    public Optional<EmployeeDTO> findOne(Long id) {
        return employeeRepository.findById(id).map(employeeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return employeeRepository.findOneByEmailIgnoreCase(email).isPresent();
    }
}
