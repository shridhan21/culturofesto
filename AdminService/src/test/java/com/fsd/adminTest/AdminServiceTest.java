package com.fsd.adminTest;
import com.fsd.entity.Admin;
import com.fsd.repository.AdminRepository;
import com.fsd.service.AdminService;
import org.junit.jupiter.api.Assertions;
	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.mockito.InjectMocks;
	import org.mockito.Mock;
	import org.mockito.Mockito;
	import org.mockito.MockitoAnnotations;

	import java.util.ArrayList;
	import java.util.List;
	import java.util.Optional;

	public class AdminServiceTest {

	    @Mock
	    private AdminRepository adminRepository;

	    @InjectMocks
	    private AdminService adminService;

	    @BeforeEach
	    public void setup() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testCreateAdmin() {
	        Admin adminToCreate = new Admin("John Doe", "password123", "john@example.com");

	        Mockito.when(adminRepository.save(Mockito.any())).thenReturn(adminToCreate);

	        Admin createdAdmin = adminService.createAdmin(adminToCreate);

	        Assertions.assertEquals(adminToCreate.getName(), createdAdmin.getName());
	        Assertions.assertEquals(adminToCreate.getPassword(), createdAdmin.getPassword());
	        Assertions.assertEquals(adminToCreate.getEmail(), createdAdmin.getEmail());
	    }

	    @Test
	    public void testGetAllAdmins() {
	        List<Admin> admins = new ArrayList<>();
	        admins.add(new Admin("John Doe", "password123", "john@example.com"));
	        admins.add(new Admin("Jane Smith", "admin456", "jane@example.com"));

	        Mockito.when(adminRepository.findAll()).thenReturn(admins);

	        List<Admin> result = adminService.getAllAdmins();

	        Assertions.assertEquals(2, result.size());
	    }

	    @Test
	    public void testGetAdminById() {
	        Long adminId = 1L;
	        Admin admin = new Admin("John Doe", "password123", "john@example.com");
	        admin.setId(adminId);

	        Mockito.when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));

	        Optional<Admin> result = adminService.getAdminById(adminId);

	        Assertions.assertTrue(result.isPresent());
	        Assertions.assertEquals(admin.getName(), result.get().getName());
	        Assertions.assertEquals(admin.getPassword(), result.get().getPassword());
	        Assertions.assertEquals(admin.getEmail(), result.get().getEmail());
	    }

	    @Test
	    public void testUpdateAdmin() {
	        Long adminId = 1L;
	        Admin existingAdmin = new Admin("John Doe", "password123", "john@example.com");
	        existingAdmin.setId(adminId);

	        Admin updatedAdminDetails = new Admin("Updated Name", "updatedpassword", "updated@example.com");

	        Mockito.when(adminRepository.findById(adminId)).thenReturn(Optional.of(existingAdmin));
	        Mockito.when(adminRepository.save(Mockito.any())).thenReturn(updatedAdminDetails);

	        Admin updatedAdmin = adminService.updateAdmin(adminId, updatedAdminDetails);

	        Assertions.assertEquals(updatedAdminDetails.getName(), updatedAdmin.getName());
	        Assertions.assertEquals(updatedAdminDetails.getPassword(), updatedAdmin.getPassword());
	        Assertions.assertEquals(updatedAdminDetails.getEmail(), updatedAdmin.getEmail());
	    }

	    @Test
	    public void testDeleteAdmin() {
	        Long adminId = 1L;

	        // Mocking the deleteById method, which typically doesn't return anything
	        Mockito.doNothing().when(adminRepository).deleteById(adminId);

	        // Verifying that the deleteById method was called once with the correct adminId
	        adminService.deleteAdmin(adminId);
	        Mockito.verify(adminRepository, Mockito.times(1)).deleteById(adminId);
	    }
	}


