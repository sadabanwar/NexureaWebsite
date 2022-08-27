package com.springboot.blog;

import com.springboot.blog.entity.Role;
import com.springboot.blog.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class RestfulBlogBackend implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(RestfulBlogBackend.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {

	/*	Commented Code C1 starts here

		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);

		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		roleRepository.save(userRole);

	Commented Code C1 ends here */

		/*Commented the above code "C1" to handle:
		 * query did not return a unique result Exception
		 * As the above code was running every time the application was starting,
		 * resulting in duplicate role entries in roles table
		 * Added the below piece of code "AC1"
		 * */

		// AC1 Starts here
		Optional<Role> role_user = roleRepository.findByName("ROLE_USER");

		if (!role_user.isPresent()) {
			Role userRole = new Role();
			userRole.setName("ROLE_USER");
			roleRepository.save(userRole);
		}

		Optional<Role> role_admin = roleRepository.findByName("ROLE_ADMIN");

		if (!role_admin.isPresent()) {
			Role userRole = new Role();
			userRole.setName("ROLE_ADMIN");
			roleRepository.save(userRole);
		}

		// AC1 ends here

	}
}
