package insbiz;

import peruncs.platform.PerunPlatform;
import rife.bld.BaseProject;
import rife.bld.BuildCommand;

import java.util.List;

import static peruncs.platform.PerunPlatform.configureBld;
import static rife.bld.dependencies.Repository.MAVEN_CENTRAL;
import static rife.bld.dependencies.Repository.MAVEN_LOCAL;

public class InsBizBld extends BaseProject {

         private InsWebAppBld insWebAppBld;

    public InsBizBld() {
        javaRelease = 22;
        pkg = "insbiz.bld";
        name = "insbiz-bld";
        version = version(0,0,1);
        downloadSources = true;
        autoDownloadPurge = true;
        repositories = List.of(MAVEN_LOCAL, MAVEN_CENTRAL);
        configureBld(this);
        insWebAppBld = new InsWebAppBld(this);
    }

    @BuildCommand(value = "webapp-rebuild", summary = "RE-BUILD web app")
    public void rebuildWebApp() throws Exception {
        cleanWebApp();
        downloadWebApp();
        compileWebApp();
    }

    @BuildCommand(value = "webapp-build", summary = "BUILD web app")
    public void buildWebApp() throws Exception {
        updateWebApp();
        compileWebApp();
    }

    @BuildCommand(value = "webapp-debug", summary = "DEBUG web app")
    public void runWebApp() throws Exception {
        updateWebApp();
        compileWebApp();
        insWebAppBld
                .runOperation()
                .javaOptions()
                .agentLib("jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005")
                .property("server.port","8080");
        insWebAppBld.run();
    }

    @BuildCommand(value = "webapp-compile", summary = "COMPILE web app")
    public void compileWebApp() throws Exception {
        insWebAppBld.compile();
    }

    @BuildCommand(value = "webapp-update", summary = "UPDATE web app")
    public void updateWebApp() throws Exception {
        insWebAppBld.updates();
    }

    @BuildCommand(value = "webapp-deptree", summary = "DEPENDENCY TREE web app")
    public void depTreeWebApp() throws Exception {
        insWebAppBld.dependencyTree();
    }

    @BuildCommand(value = "webapp-clean", summary = "CLEAN web app")
    public void cleanWebApp() throws Exception {
        insWebAppBld.clean();
    }

    @BuildCommand(value = "webapp-purge", summary = "PURGE web app")
    public void purgeWebApp() throws Exception {
        insWebAppBld.purge();
    }

    @BuildCommand(value = "webapp-jar", summary = "JAR web app")
    public void jarWebApp() throws Exception {
        insWebAppBld.jar();
    }

    @BuildCommand(value="webapp-download", summary = "DOWNLOAD webapp dependencies")
    public void downloadWebApp() throws Exception {
        insWebAppBld.download();
    }

    @BuildCommand(value="webapp-dist", summary = "DIST webapp")
    public void distWebApp() throws Exception {
        PerunPlatform.prepareDistribution(insWebAppBld);
    }


    @BuildCommand(value = "all-compile", summary = "COMPILE all")
    public void compileAll() throws Exception {
        compile();
        insWebAppBld.compile();
    }

    @BuildCommand(value = "all-clean", summary = "CLEAN all")
    public void cleanAll() throws Exception {
        clean();
        insWebAppBld.clean();
    }

    @BuildCommand(value = "all-purge", summary = "PURGE all")
    public void purgeAll() throws Exception {
        purge();
        insWebAppBld.purge();
    }

    @BuildCommand(value = "all-jar", summary = "JAR  all")
    public void jarAll() throws Exception {
        insWebAppBld.jar();
    }

    @BuildCommand(value="all-download", summary = "DOWNLOAD all dependencies")
    public void downloadAll() throws Exception {
        download();
        insWebAppBld.download();
    }

    @BuildCommand(value="all-dist", summary = "DIST all")
    public void distAll() throws Exception {
        distWebApp();
    }

    public static void main(String[] args) {
        new InsBizBld().start(args);
    }

}
