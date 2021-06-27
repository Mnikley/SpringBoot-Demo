package com.leysolutions.iblab_springboot_demo;
//source: https://dzone.com/articles/spring-boot-restful-web-service-complete-example

import java.util.ArrayList;
import java.util.List;

public class ModuleRegistration {

    private List<Module> moduleRecords;


    private static ModuleRegistration stdregd = null;

    private ModuleRegistration(){
    moduleRecords = new ArrayList<Module>();
    }

    public static ModuleRegistration getInstance() {

        if(stdregd == null) {
            stdregd = new ModuleRegistration();
            return stdregd;
        }
        else {
            return stdregd;
        }
    }

    public void add(Module std) {
        moduleRecords.add(std);
    }

public String updateModule(Module std) {

for(int i = 0; i< moduleRecords.size(); i++)
        {
            Module stdn = moduleRecords.get(i);
            if(stdn.getName().equals(std.getName())) {
              moduleRecords.set(i, std);  // update record
              return "Update successful";
            }
        }

return "Update failed";
}

public String deleteModule(String name) {

for(int i = 0; i< moduleRecords.size(); i++)
        {
            Module stdn = moduleRecords.get(i);
            if(stdn.getName().equals(name)){
              moduleRecords.remove(i);  // pop record
              return "Delete successful";
            }
        }

return "Delete failed";
}

    public List<Module> getModuleRecords() {
    return moduleRecords;
    }

}