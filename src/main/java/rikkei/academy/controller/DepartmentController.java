package rikkei.academy.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rikkei.academy.repository.DepartmentRepository;
import rikkei.academy.service.DepartmentService;
import rikkei.academy.service.dto.DepartmentDTO;

import java.util.Optional;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentService departmentService, DepartmentRepository departmentRepository) {
        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/index")
    public ModelAndView index(Pageable pageable) {
        Page<DepartmentDTO> listDepartment = departmentService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("department/index");
        modelAndView.addObject("listDepartment", listDepartment);
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView doAdd(@RequestParam("name") String name,
                              @RequestParam("description") String description) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setName(name);
        departmentDTO.setDescription(description);
        departmentService.save(departmentDTO);
        ModelAndView modelAndView = new ModelAndView("department/add");
        modelAndView.addObject("message", "Create success!!");
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView showAdd() {
        ModelAndView modelAndView = new ModelAndView("department/add");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView doDelete(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/department/index");
        Optional<DepartmentDTO> departmentDTO = departmentService.findOne(id);
        if (departmentDTO.isPresent()) {
            departmentService.delete(id);
            return modelAndView;
        }
        return modelAndView.addObject("message", "Entity not found");
    }


    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("department/edit");
        Optional<DepartmentDTO> departmentDTO = departmentService.findOne(id);
        if (departmentDTO.isPresent()) {
            return modelAndView.addObject("department", departmentDTO.get());
        }
        return new ModelAndView("department/index").addObject("message", "Entity not found");
    }

    @PostMapping("/edit")
    public ModelAndView doEdit(@RequestParam("id") Long id,
                               @RequestParam("name") String name,
                               @RequestParam("description") String description, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("department/index");
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setName(name);
        departmentDTO.setId(id);
        departmentDTO.setDescription(description);
        departmentService.save(departmentDTO);
        Page<DepartmentDTO> listDepartment = departmentService.findAll(pageable);
        modelAndView.addObject("listDepartment", listDepartment);
        return modelAndView;
    }
    @GetMapping("/detail/{id}")
    public ModelAndView showDetail(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("department/detail");
        Optional<DepartmentDTO> departmentDTO = departmentService.findOne(id);
        if (departmentDTO.isPresent()) {
            modelAndView.addObject("department", departmentDTO.get());
            return modelAndView;
        }
        return new ModelAndView("department/index").addObject("message", "Entity not found");
    }
}
