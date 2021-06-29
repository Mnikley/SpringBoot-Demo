package com.leysolutions.iblab_springboot_demo;
//source: https://dzone.com/articles/spring-boot-restful-web-service-complete-example

import java.util.ArrayList;
import java.util.List;

public class ModuleRegistration {

    private List<Module> moduleRecords;

    private static ModuleRegistration stdregd = null;

    private ModuleRegistration(){
        // init?
        moduleRecords = new ArrayList<Module>();
    }

    public static ModuleRegistration getInstance() {
        // init?
        if(stdregd == null) {
            stdregd = new ModuleRegistration();
        }
        return stdregd;
    }

    public void add(Module std) {
        // add a module
        moduleRecords.add(std);
    }

    public List<Module> getModuleRecords() {
        // get current module register
        return moduleRecords;
    }

}