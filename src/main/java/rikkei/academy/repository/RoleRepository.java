package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {
}
