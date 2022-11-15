package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.AdministratorDto;
import com.isa.bloodbank.dto.RegisterUserDto;
import com.isa.bloodbank.dto.UserDto;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.service.UserService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/bloodBankId") //bilo i /{bloodBankId}/ @PathVariable("bloodBankId") final Long bloodBankId
    public ResponseEntity<List<AdministratorDto>> findByAdministratorId() {
        final Long administratorId = (long) (3);
        Long bloodBankId = userService.findById(administratorId).getBloodBank().getId();
        return ResponseEntity.ok(userService.findByBloodBankId(bloodBankId, administratorId));
    }
    @PostMapping("/register/admin")
    public ResponseEntity<RegisterUserDto> registerCenterAdmin(@RequestBody final RegisterUserDto centerAdmin) {
        System.out.println(centerAdmin + "e");
        return ResponseEntity.ok(userService.registerCenterAdmin(centerAdmin));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> search(@RequestParam("name") final String name, @RequestParam("surname") final String lastName) {
        return ResponseEntity.ok(userService.search(name, lastName));
    }
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findUserById(@PathVariable("id") final Long id) {
		return ResponseEntity.ok(userService.findById(id));
	}

	@PutMapping("/update")
	private ResponseEntity<User> updateUser(@Valid @RequestBody final User user) {
		return ResponseEntity.ok(userService.updateUser(user));
	}

    @GetMapping("/loggedInUser/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/update/")
    private ResponseEntity<User> updateUser(@RequestBody final UserDto userDto) {
        System.out.println(userDto);
        return ResponseEntity.ok(userService.update(userDto));
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAllDto());
    }


    @GetMapping("/center-admins")
    public ResponseEntity<List<UserDto>> getAvailableCenterAdmins(){
        return ResponseEntity.ok(userService.getAvailableCenterAdmins());
    }

}