package insbiz;

import peruncs.platform.PerunPlatform;
import rife.bld.Project;

import java.util.List;
import java.io.File;

import static peruncs.platform.PerunPlatform.*;

class InsWebAppBld extends BaseBld {
    public InsWebAppBld(InsBizBld parent) {
        super(parent,"webapp");
        pkg = "insbiz.webapp";
        name = "webapp";
        mainClass = "insbiz.webapp.App";
        version = version(0,0,1);
        configureHelidonServer(this);
        configurePerunHelidon(this);
        configurePerunUtilities(this);
        configureJunit(this);
        configureHamcrest(this);

        var modules = List.of("io.helidon.webserver", "io.helidon.webserver.staticcontent","io.helidon.logging.common");

        runOperation()
                .javaOptions()
                .enablePreview()
                .modulePath(compileMainClasspath().stream().map(File::new).toList())
                .addModules(modules);

        compileOperation()
                .compileOptions()
                .enablePreview()
                .modulePath(compileMainClasspath().stream().map(File::new).toList())
               .addModules(modules);

        testOperation()
                .javaOptions()
                .enablePreview()
                .modulePath(testClasspath().stream().map(File::new).toList())
                .modulePath(compileMainClasspath().stream().map(File::new).toList())
                .addModules(modules);
    }

}
