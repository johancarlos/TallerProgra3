package com.ucbcba.logindemo.entities;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    public Integer getRoleId() {
        return RoleId;
    }

    public void setRoleId(Integer roleId) {
        RoleId = roleId;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer RoleId;

    private String RoleName;
}
