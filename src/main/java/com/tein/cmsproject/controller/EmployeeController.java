package com.tein.cmsproject.controller;

import com.tein.cmsproject.model.Employee;
import com.tein.cmsproject.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;



    @GetMapping("/admin/")
    public String viewHomePage(Model model){
        model.addAttribute("listEmployee",employeeService.getAllEmployees());
        return "index";
    }

    @GetMapping("/user/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee",employee);
        return "new_employee";
    }

    @PostMapping("/user/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee")Employee employee){
        employeeService.saveEmployee(employee);
        //TODO : kullanıcı sisteme kayıt edildi admin goruntuluyebilir
        return "redirect:/admin/";
    }
    @GetMapping("/user/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable( value = "id") long id, Model model){
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "update_employee";
    }

    @GetMapping("/user/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable (value = "id") long id) {

        // call delete employee method
        this.employeeService.deleteEmployeeById(id);
        return "redirect:/admin/";
    }

    @GetMapping("/not-auth")
    public String getAccessDenied() {
        return "/not-auth";
    }

}
