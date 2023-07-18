package rikkei.academy.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rikkei.academy.model.Employee;
import rikkei.academy.repository.EmployeeRepository;
import rikkei.academy.repository.RoleRepository;
import rikkei.academy.service.DepartmentService;
import rikkei.academy.service.EmployeeService;
import rikkei.academy.service.RoleService;
import rikkei.academy.service.dto.DepartmentDTO;
import rikkei.academy.service.dto.EmployeeDTO;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final RoleService roleService;

    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository, DepartmentService departmentService, RoleService roleService) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
        this.roleService = roleService;
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(name = "name", required = false, defaultValue = "") String name, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("employee/index");
        Page<EmployeeDTO> employeeDTO = employeeService.findAll(name, pageable);
        modelAndView.addObject("listEmployee", employeeDTO);
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView showAdd() {
        ModelAndView modelAndView = new ModelAndView("employee/add");
        modelAndView.addObject("role", roleService.findAll());
        modelAndView.addObject("departments", departmentService.findAll());
        modelAndView.addObject("employee",new EmployeeDTO());
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView doAdd(@Valid @ModelAttribute("employee") EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("employee/add", bindingResult.getModel());
            modelAndView.addObject("employee", employeeDTO);
            modelAndView.addObject("departments", departmentService.findAll());
            modelAndView.addObject("roles", roleService.findAll());
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("employee/add");
        modelAndView.addObject("departments", departmentService.findAll());
        modelAndView.addObject("roles", roleService.findAll());
        if (employeeRepository.findOneByEmailIgnoreCase(employeeDTO.getEmail()).isPresent()) {
            return modelAndView.addObject("message1", "Email is already in use!");
        }
        employeeService.save(employeeDTO);
        modelAndView.addObject("message1", "Create Success!!!");
        return new ModelAndView("redirect:/employee/index");
    }


    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable("id") Long id, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("employee/edit");
        Optional<EmployeeDTO> employeeDTO = employeeService.findOne(id);
        if (employeeDTO.isPresent()) {
            modelAndView.addObject("employeeUpdate", employeeDTO.get());
            Page<DepartmentDTO> listDepart = departmentService.findAll(pageable);
            modelAndView.addObject("listDepartment", listDepart);
            return modelAndView;

        }
        return modelAndView.addObject("message", "Entity not found");
    }

    @PostMapping("/edit")
    public ModelAndView doEdit(@ModelAttribute("employeeUpdate") EmployeeDTO employeeDTO) {
        ModelAndView modelAndView = new ModelAndView("employee/edit");
        if (!employeeRepository.existsById(employeeDTO.getId())) {
            return modelAndView.addObject("message1", "Entity not found");
        }
        Optional<Employee> employee = employeeRepository.findOneByEmailIgnoreCase(employeeDTO.getEmail());
        if (employee.isPresent() && !employee.get().getId().equals(employeeDTO.getId())) {
            return modelAndView.addObject("message2", "Email already exists!!! ");
        }
        employeeService.save(employeeDTO);
        return modelAndView.addObject("message3", "Update success!!!");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView showDetail(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("employee/detail");
        Optional<EmployeeDTO> employeeDTO = employeeService.findOne(id);
        if (employeeDTO.isPresent()) {
            modelAndView.addObject("employee", employeeDTO.get());
            return modelAndView;
        }
        return modelAndView.addObject("message", "Entity not found");
    }


    @GetMapping("/delete/{id}")
    public ModelAndView doDelete(@PathVariable("id") Long id) {
        if (!employeeRepository.existsById(id)) {
            return new ModelAndView("employee/index").addObject("message2", "Entity not found");
        }
        employeeService.delete(id);
        return new ModelAndView("redirect:/employee/index").addObject("message1", "Delete successful");
    }
}
