package rikkei.academy.service.serviceImpl;

import org.springframework.stereotype.Service;
import rikkei.academy.model.Role;
import rikkei.academy.repository.RoleRepository;
import rikkei.academy.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
