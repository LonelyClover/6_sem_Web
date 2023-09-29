package ru.msu.cs.TheaterWeb.DAO.impl;

import ru.msu.cs.TheaterWeb.DAO.RoleDAO;
import ru.msu.cs.TheaterWeb.entities.Role;

public class RoleDAOImpl extends CommonDAOImpl<Role> implements RoleDAO {
    public RoleDAOImpl() {
        super(Role.class);
    }
}