package com.soomee.notesjwt.service;

import com.soomee.notesjwt.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    RoleDTO addRole(RoleDTO roleDTO);

    List<RoleDTO> getAllRoles();

    RoleDTO getRoleById(String id);

    RoleDTO updateRoleById(String id, RoleDTO roleDTO);

}
