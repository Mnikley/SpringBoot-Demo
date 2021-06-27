package com.leysolutions.iblab_springboot_demo;

// data type imports
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// spring REST imports
import org.springframework.web.bind.annotation.*;

@RestController
public class ModuleController {

//  @GetMapping("/person")
//  public List<Module> getAllPersons(){
//    //Returns hardcoded data, a real world application would return from the database
//    List<Module> moduleList = new ArrayList<Module>();
//    moduleList.add(new Module(1,"Mickey Mouse", 30));
//    moduleList.add(new Module(2,"Donald Duck", 35));
//    moduleList.add(new Module(3,"Peppa Pig", 15));
//    moduleList.add(new Module(4, "Frecher Fredl", 32));
//    return moduleList;
//  }
//
//  @GetMapping("/person/{personId}")
//  public Module getPersonWithId(@PathVariable Integer personId){
//    //Returns hardcoded data, a real world application would return from the database
//    return new Module(3,"Peppa Pig", 15);
//  }
//
//
//  @PostMapping("/person/newperson")
//  public void addPerson(@RequestBody Module module){
//    //Just has a Sysout stmt, a real world application would save this record to the database
//    System.out.println("Saving person information");
//
//  }

  // List<String> myModules = List.of("one", "two", "three");

//  List<Module> myModules = new ArrayList<Module>();
//  myModules.add(1, "KOALA", 5);
//  myModules.add(2, "PANDA", 6);

//  @GetMapping("/modules")
//  public List<Module> getAllModules(){
//    List<Module> moduleList = new ArrayList<Module>();
//    // moduleList.add(new Module(1,"Mickey Mouse", 30));
//    return moduleList;
//  }
//
//  @PostMapping(path="/", consumes="application/json", produces="application/json")
//  public String addModule(@RequestBody String name){
//
//    // new_module.setExpirationDate(30);
//    return "Done registering model: " + name;
//  }

  @GetMapping("/modules")
  public List<Module> getAllModules()  {
    return ModuleRegistration.getInstance().getModuleRecords();
  }

//  @PostMapping(path="/", consumes="application/json", produces="application/json")
//  public ModuleRegistrationReply registerModule(@RequestBody Module module) {
//    System.out.println("Registering new module..");
//    ModuleRegistrationReply reply = new ModuleRegistrationReply();
//    ModuleRegistration.getInstance().add(module);
//    // this is just for a reply message back to the sender
//    reply.setName(module.getName());
//    reply.setExpirationDate(module.getExpirationDate());
//    reply.setExpiredState(module.getExpiredState());
//    System.out.println("Registration of module " + module.getName() + " successful.");
//    return reply;
//  }
  
  @PostMapping(path="/", consumes="application/json", produces="application/json")
  public void registerModule(@RequestBody Module module) {
    // Register a new module via POST to "/"
    ModuleRegistration.getInstance().add(module);
    System.out.println("Added new module: " + module.getName());
  }

  @GetMapping("/init_database")
  public String instanciateDatabase() {
    // instanciates the database via a GET to "/init_database", mainly for testing
    // im sure this can be done nicely with a HashMap
    Module tmp = new Module("KOALA", LocalDate.of(2021, 6, 1), true);
    ModuleRegistration.getInstance().add(tmp);
    Module tmpTwo = new Module("PANDA", LocalDate.of(2021, 8, 30), false);
    ModuleRegistration.getInstance().add(tmpTwo);
    return "Successfully initialized database with 2 models.";
  }

}