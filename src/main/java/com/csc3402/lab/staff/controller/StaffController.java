package com.csc3402.lab.staff.controller;

import com.csc3402.lab.staff.model.Staff;
import com.csc3402.lab.staff.repository.StaffRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staffs")
public class StaffController {

    // Variables
    private final StaffRepository staffRepository;

    // Constructor
    public StaffController(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    // Methods
    @GetMapping("list")
    public String showUpdateForm(Model model) {
        // Logic to retrieve and add staff data to the model
        model.addAttribute("staffs", staffRepository.findAll());
        // Returns the name of the view template to render
        return "list-staff"; // link to 'list-staff.html'
    }

    @GetMapping("signup")
    public String showSignUpForm(Staff staff){
        return "add-staff";
    }

    @PostMapping("add")
    public String addStaff(@Valid Staff staff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-staff";
        }

        staffRepository.save(staff);
        return "redirect:list";
    }

    // Lab 11
    // Update Staff
    @GetMapping("update")
    public String showUpdateMainForm(Model model) {
        model.addAttribute("staffs", staffRepository.findAll());
        return "choose-staff-to-update";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Staff staff = staffRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid staff Id:" + id));
        model.addAttribute("staff", staff);
        return "update-staff";
    }


    @PostMapping("update/{id}")
    public String updateStaff(@PathVariable("id") long id, @Valid Staff staff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            staff.setStaffid((int) id);
            return "index";
        }

        model.addAttribute("staffs", staffRepository.findAll());
        staffRepository.save(staff);
        return "list-staff";
    }

    // Delete staff
    @GetMapping("delete")
    public String showDeleteMainForm(Model model) {
        model.addAttribute("staffs", staffRepository.findAll());
        return "choose-staff-to-delete";
    }

    @GetMapping("delete/{id}")
    public String deleteStaff(@PathVariable("id") long id, Model model) {
        Staff staff = staffRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid staff Id:" + id));

        staffRepository.delete(staff);
        model.addAttribute("staffs", staffRepository.findAll());

        return "list-staff";
    }


}
