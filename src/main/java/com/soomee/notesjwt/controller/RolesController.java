package com.soomee.notesjwt.controller;


import com.soomee.notesjwt.dto.RoleDTO;
import com.soomee.notesjwt.dto.response.Response;
import com.soomee.notesjwt.service.implementation.RoleServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    private final RoleServiceImpl roleService;

    public RolesController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }


    @GetMapping
    public ResponseEntity<Response> getAllRoles() {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("roles", roleService.getAllRoles()))
                        .message("All roles fetched!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping
    public ResponseEntity<Response> addRole(@RequestBody RoleDTO role) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("role", roleService.addRole(role)))
                        .message("New role added!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> getRoleById(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("role", roleService.getRoleById(id)))
                        .message("Role fetched by id!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping("/{id}")
    public ResponseEntity<Response> updateRole(@PathVariable("id") String id, @RequestBody RoleDTO role) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("role", roleService.updateRoleById(id, role)))
                        .message("Role updated by id!")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }


}
