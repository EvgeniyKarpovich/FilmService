package by.karpovich.filmSevice.api.controller;

import by.karpovich.filmSevice.api.dto.UserDto;
import by.karpovich.filmSevice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Api(tags = "User Controller")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Find user by Id")
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @ApiOperation(value = "Save user")
    @PostMapping
    public void save(@RequestBody UserDto UserDto) {
        userService.save(UserDto);
    }

    @ApiOperation(value = "Update by id user")
    @PutMapping("/{id}")
    public void update(@RequestBody UserDto UserDto,
                       @PathVariable("id") Long id) {
        userService.update(UserDto, id);
    }

    @ApiOperation(value = "Find all users")
    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @ApiOperation(value = "Delete by id user")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }
}
