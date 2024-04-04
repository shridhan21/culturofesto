package com.fsd.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.fsd.entity.Admin;
import com.fsd.exception.AdminNotFoundException;
import com.fsd.repository.AdminRepository;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
@RestController//The class handles HTTP requests related to Admin entities.
@RequestMapping("/admins")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", origins = "http://localhost:3000", methods =  {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.HEAD}, maxAge = 3600L, exposedHeaders = "Access-Control-Allow-Origin")
@Validated// Enable validation for request parameters
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/")// POST method to create a new Admin
    public ResponseEntity<Admin> createAdmin(@Valid @RequestBody Admin admin) {
        Admin newAdmin = adminRepository.save(admin);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    }

    @GetMapping// GET method to retrieve all Admins
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @GetMapping("/{id}")// GET method to retrieve an Admin by ID
    public ResponseEntity<Admin> getAdminById(@Valid @PathVariable Long id) throws AdminNotFoundException{
        Optional<Admin> admin = adminRepository.findById(id);
       
     // If Admin with ID exists, return it with HTTP status 200, otherwise return 404
        return admin.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseThrow(()-> new AdminNotFoundException("Admin Not Found"));
    }

    @PutMapping("/{id}")// PUT method to update an existing Admin 
    public ResponseEntity<Admin> updateAdmin(@Valid @PathVariable Long id, @RequestBody Admin adminDetails) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            admin.setName(adminDetails.getName());
            admin.setPassword(adminDetails.getPassword());
            admin.setEmail(adminDetails.getEmail());
            Admin updatedAdmin = adminRepository.save(admin);
            return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);// Return the updated Admin with HTTP status 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);// Admin with given ID not found, return 404
        }
    }

    @DeleteMapping("/{id}")// DELETE method to delete an Admin by ID
    public ResponseEntity<HttpStatus> deleteAdmin(@Valid @PathVariable Long id) {
        try {
            adminRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);// Return 204 if successfu
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);// Return 500 if an error occurs
        }
    }
}

