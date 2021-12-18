package com.example.jdbl.libraryapp;

import com.example.jdbl.libraryapp.models.Admin;
import com.example.jdbl.libraryapp.requests.AdminCreateRequest;
import com.example.jdbl.libraryapp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryAppApplication implements CommandLineRunner {

	@Autowired
	AdminService adminService;


	public static void main(String[] args) {
		SpringApplication.run(LibraryAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		AdminCreateRequest admin= AdminCreateRequest.builder()
				.name("ABC")
				.email("abc@google.com")
				.password("abc123")
				.build();

		adminService.saveAdmin(admin);
	}
}
