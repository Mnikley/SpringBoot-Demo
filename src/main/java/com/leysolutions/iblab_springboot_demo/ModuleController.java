package com.leysolutions.iblab_springboot_demo;

// data type imports
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// spring REST imports
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ModuleController {

  @GetMapping(path="/modules")
  public List<Module> getAllModules()  {
    // returns a list of registered modules
    return ModuleRegistration.getInstance().getModuleRecords();
  }

  @PostMapping(path="/", consumes="application/json", produces="application/json")
  public void registerModule(@RequestBody Module module, HttpServletResponse response) {
    // Register a new module and validates given parameters;
    // args: name (string), expiration date (Localdate as string 'yyyy-MM-dd'), bool (unused)

    try {
      ModuleRegistration.getInstance().add(module);
      System.out.println("Added new module: " + module.getName());
      response.setStatus(HttpServletResponse.SC_CREATED);  // if successful return 201
    }
    catch (Exception e) {
      System.out.println("Module does not meet requirements! Date format must be yyyy-MM-dd");  // not working
      System.out.println(e);  // not printed either
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // if model is invalid return 400
    }

  }

  @GetMapping(path="/init_database")
  public String instanciateDatabase() {
    // instanciates the database with two test modules
    Module tmp = new Module("KOALA", LocalDate.of(2021, 6, 1));
    ModuleRegistration.getInstance().add(tmp);
    Module tmpTwo = new Module("PANDA", LocalDate.of(2021, 8, 30));
    ModuleRegistration.getInstance().add(tmpTwo);
    return "Successfully initialized database with 2 models.";
  }

  @GetMapping(path="/{module}")
  public String getModuleByName(@PathVariable String module, HttpServletResponse response){
    // returns the expiration date of a module as string; returns 302 if module name exists, otherwise 404
    // get all registered modules
    List<Module> modulesList = ModuleRegistration.getInstance().getModuleRecords();

    // strip { and } from module name
    String queryName = module.replace("{", "");
    queryName = queryName.replace("}", "");

    System.out.println("Searching for module name: " + queryName);

    // iterate over ArrayList to find module with matching name
    for(int i=0; i < modulesList.size(); i++)
    {
      // System.out.println("Checking module name: " + modulesList.get(i).name);  // debug print current module name
      if (modulesList.get(i).name.equals(queryName))
      {
        System.out.println("Module found: " + queryName);
        // format LocalDate to string because i specified string as output
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        response.setStatus(HttpServletResponse.SC_FOUND);  // return 302 if name is found
        return modulesList.get(i).expirationDate.format(formatter);
      }
    }

    response.setStatus(HttpServletResponse.SC_NOT_FOUND);  // return 404 if nothing found
    return "No module with name " + queryName + " found";
  }


  @GetMapping(path="/isExpired/{module}")
  public Map<String, String> getModuleExpiredStatus(@PathVariable String module){
    // returns json whether a module is expired or not; returns 200 all the time
    // define map for return
    HashMap<String, String> map = new HashMap<>();

    // todays date for checking expiration
    LocalDate dateToday = LocalDate.now();

    // flag if module was found (not required - used to append an error text to the map)
    Boolean moduleFound = false;

    // get all registered modules
    List<Module> modulesList = ModuleRegistration.getInstance().getModuleRecords();

    // strip { and } from module name
    String queryName = module.replace("{", "");
    queryName = queryName.replace("}", "");

    System.out.println("Searching for module name: " + queryName);

    // iterate over ArrayList to find module with matching name
    for(int i=0; i < modulesList.size(); i++)
    {
      if (modulesList.get(i).name.equals(queryName))
      {
        moduleFound = true;
        System.out.println("Module found: " + queryName + ". Checking expiration date..");

        // compare expiration date to LocalDate.now()
        if (modulesList.get(i).expirationDate.compareTo(dateToday) > 0)
        {
          // could also overwrite expiration flag in module itself i guess
          map.put("expired", "false");
        }
        else {
          map.put("expired", "true");
        }
      }
    }

    if (!moduleFound) {
      map.put("invalid name", queryName);
    }

    return map;
  }

  @GetMapping(path="/isExpired")
  public Map<String, String> getAllModulesExpiredStatus(){
    // Returns json body (map) with all registered modules and their expiration state;
    // calculates expiration date when called based on LocalDate.now()
    // define map for return
    HashMap<String, String> map = new HashMap<>();

    // todays date for checking expiration
    LocalDate dateToday = LocalDate.now();

    // get all registered modules
    List<Module> modulesList = ModuleRegistration.getInstance().getModuleRecords();

    // iterate over list
    for(int i=0; i < modulesList.size(); i++)
    {
        if (modulesList.get(i).expirationDate.compareTo(dateToday) > 0)
        {
          // could also overwrite expiration flag in module itself i guess
          map.put(modulesList.get(i).name, "false");
        }
        else {
          map.put(modulesList.get(i).name, "true");
        }
    }

    return map;
  }

  @DeleteMapping(path="/{module}")
  public void deleteModuleByName(@PathVariable String module, HttpServletResponse response){
    // deletes a module from the registry by given path variable; returns 200 when successful, else 404
    // get all registered modules
    List<Module> modulesList = ModuleRegistration.getInstance().getModuleRecords();

    // strip { and } from module name
    String queryName = module.replace("{", "");
    queryName = queryName.replace("}", "");

    // flag if module was found
    Boolean moduleFound = false;

    // iterate over ArrayList to find module with matching name
    for(int i=0; i < modulesList.size(); i++)
    {
      if (modulesList.get(i).name.equals(queryName))
      {
        modulesList.remove(i); // pop record
        System.out.println("Deleted module " + queryName);
        response.setStatus(HttpServletResponse.SC_OK);  // return 200
        moduleFound = true;
      }
    }

    if (!moduleFound) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);  // return 404 if nothing found
    }
  }


  @PutMapping(path="/{module}")
  public void registerModule(@PathVariable String module, @RequestBody String expirationDateString,
                             HttpServletResponse response) {
    // update expiration date of a module given as path variable; requests expiration date as string (yyyy-MM-dd);

    // get all registered modules
    List<Module> modulesList = ModuleRegistration.getInstance().getModuleRecords();

    // strip { and } from module name
    String queryName = module.replace("{", "");
    queryName = queryName.replace("}", "");

    // allocate newExpiurationDate variable
    LocalDate newExpirationDate;

    // flag to check if parse was successfull
    Boolean parseSuccessful = false;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    try {
      // try to parse string as date
      newExpirationDate = LocalDate.parse(expirationDateString);
      parseSuccessful = true;
    } catch (Exception e) {
      newExpirationDate = LocalDate.now();  // TODO: find better solution
      System.out.println("Invalid date format!");
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    if (parseSuccessful) {
      // iterate over ArrayList to find module with matching name
      for (int i = 0; i < modulesList.size(); i++) {
        if (modulesList.get(i).name.equals(queryName)) {
          modulesList.get(i).setExpirationDate(newExpirationDate);
        }
      }
    }

  }

  @GetMapping(path="/echo")
  public String echoString(@RequestBody String inputString) {
    // accepts a string as request parameter, return string as body back to the sender
    try {
      return inputString;
    } catch (Exception e) {
      return "";
    }

  }

}