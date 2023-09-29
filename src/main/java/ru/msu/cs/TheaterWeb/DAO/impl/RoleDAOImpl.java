package ru.msu.cs.TheaterWeb.DAO.impl;

import org.springframework.stereotype.Repository;
import ru.msu.cs.TheaterWeb.DAO.RoleDAO;
import ru.msu.cs.TheaterWeb.entities.Role;

@Repository
public class RoleDAOImpl extends CommonDAOImpl<Role> implements RoleDAO {
    public RoleDAOImpl() {
        super(Role.class);
    }
}