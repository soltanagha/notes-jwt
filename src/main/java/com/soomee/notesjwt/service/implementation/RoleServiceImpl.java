package com.soomee.notesjwt.service.implementation;

import com.soomee.notesjwt.dto.RoleDTO;
import com.soomee.notesjwt.model.Role;
import com.soomee.notesjwt.dto.exception.EmptyInputException;
import com.soomee.notesjwt.repository.RoleRepository;
import com.soomee.notesjwt.service.RoleService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    public RoleServiceImpl(ModelMapper modelMapper, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDTO addRole(RoleDTO roleDTO) {

        logger.info("Creating new role by name: "+roleDTO.getName());

        if (roleDTO.getName() == null)
            throw  new EmptyInputException("601", "Role name is empty!");

        Role role = convertToRoleEntity(roleDTO);
        role = roleRepository.save(role);

        return convertToRoleDto(role);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        logger.info("Fetching all roles!");
        return roleRepository.findAll()
                .stream()
                .map(this::convertToRoleDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleById(String id) {
        logger.info("Fetching role by id: "+ id);
        return roleRepository.findById(id)
                .stream()
                .map(this::convertToRoleDto)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Role not found by id: " + id));
    }

    @Override
    public RoleDTO updateRoleById(String id, RoleDTO roleDTO) {
        logger.info("Update role by id: "+id);
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Role not found by id: " + id));
        role.setName(roleDTO.getName());
        role = roleRepository.save(role);

        return convertToRoleDto(role);
    }

    private RoleDTO convertToRoleDto(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    private Role convertToRoleEntity(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }

}
