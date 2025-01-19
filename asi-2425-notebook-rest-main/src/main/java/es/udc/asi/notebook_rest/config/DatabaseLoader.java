package es.udc.asi.notebook_rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import es.udc.asi.notebook_rest.model.domain.User;
import es.udc.asi.notebook_rest.model.exception.UserLoginExistsException;
import es.udc.asi.notebook_rest.model.repository.UserDao;
import es.udc.asi.notebook_rest.model.service.UserService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Configuration
public class DatabaseLoader {

  @Autowired
  private UserDao userDAO;

  @Autowired
  private UserService userService;

  @Transactional(readOnly = false, rollbackFor = Exception.class)
  public void loadData() throws UserLoginExistsException, InterruptedException {
    LocalDate localDate = LocalDate.of(2024, 12, 24);

    // Convertir LocalDate a Date
    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    userService.registerUser("pepemin", "pepemin", "pepemin", "pepemin", date,true);
    userService.registerUser("mariadmin", "mariadmin", "mariadmin", "mariadmin",date,true);
    userService.registerUser("laura", "laura", "laura", "laura", date,false);
    userService.registerUser("pedroff", "pedroff", "pedroff", "pedroff",date,false);
    User pedro = userDAO.findByLogin("pedroff");
    pedro.setActive(false);
    userDAO.update(pedro);
    userService.registerUser("ramón", "ramón", "ramón", "ramón", date ,false);

  }

}
