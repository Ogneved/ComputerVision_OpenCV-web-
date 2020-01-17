package com.evilcorp.computervisionweb;

import com.evilcorp.computervisionweb.controller.ImageController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("imageproc")
public class ComputerVisionApplication extends Application {

    private Set<Object> singletons = new HashSet<Object>();

    public ComputerVisionApplication() {
        singletons.add(new ImageController());
    }

    @Override
    public Set<Object> getSingletons() {
        return super.getSingletons();
    }
}
