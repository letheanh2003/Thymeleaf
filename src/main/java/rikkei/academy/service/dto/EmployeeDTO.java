package rikkei.academy.service.dto;

import java.io.Serializable;
import java.util.Set;

public class EmployeeDTO implements Serializable {
    private Long id;

    private String name;

    private String email;

    private Long departmentId;

    private String departmentName;

    private Set<String> roles;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long id, String name, String email, Long departmentId, String departmentName, Set<String> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
