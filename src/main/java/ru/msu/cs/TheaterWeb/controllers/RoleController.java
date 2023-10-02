package ru.msu.cs.TheaterWeb.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cs.TheaterWeb.DAO.ActorDAO;
import ru.msu.cs.TheaterWeb.DAO.PlayDAO;
import ru.msu.cs.TheaterWeb.DAO.RoleDAO;
import ru.msu.cs.TheaterWeb.DAO.impl.ActorDAOImpl;
import ru.msu.cs.TheaterWeb.DAO.impl.PlayDAOImpl;
import ru.msu.cs.TheaterWeb.DAO.impl.RoleDAOImpl;
import ru.msu.cs.TheaterWeb.entities.Role;

@Controller
public class RoleController {
    @Autowired
    private final RoleDAO roleDAO = new RoleDAOImpl();

    @Autowired
    private final ActorDAO actorDAO = new ActorDAOImpl();

    @Autowired
    private final PlayDAO playDAO = new PlayDAOImpl();

    @PostMapping("/roleAdd")
    public String roleAddPage(@RequestParam(name = "play_id") Long playId,
                              @RequestParam(name = "actor_id") Long actorId,
                              @RequestParam(name = "role_info", required = false) String roleInfo) {
        Role role = new Role(actorDAO.getById(actorId), playDAO.getById(playId));
        role.setInfo(roleInfo);
        roleDAO.save(role);
        return "redirect:/play?play_id=" + playId;
    }
    @PostMapping("/roleEdit")
    public String roleEditPage(@RequestParam(name = "play_id") Long playId,
                               @RequestParam(name = "role_id") Long roleId,
                               @RequestParam(name = "role_info", required = false) String roleInfo) {
        Role role = roleDAO.getById(roleId);
        role.setInfo(roleInfo);
        roleDAO.update(role);
        return "redirect:/play?play_id=" + playId;
    }
    @PostMapping("/roleDelete")
    public String roleDeletePage(@RequestParam(name = "play_id") Long playId,
                                 @RequestParam(name = "role_id") Long roleId) {
        roleDAO.deleteById(roleId);
        return "redirect:/play?play_id=" + playId;
    }
}