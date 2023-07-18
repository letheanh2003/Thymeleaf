package rikkei.academy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rikkei.academy.service.dto.EmployeeDTO;

import java.util.Optional;

public interface EmployeeService {
    EmployeeDTO save(EmployeeDTO notifyDTO);

    Page<EmployeeDTO> findAll(String textSearch, Pageable pageable);

    Optional<EmployeeDTO> findOne(Long id);

    void delete(Long id);

    boolean existsByEmail(String email);
}
