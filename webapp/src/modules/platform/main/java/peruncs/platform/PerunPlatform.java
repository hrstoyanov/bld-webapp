package peruncs.platform;

import rife.bld.BaseProject;
import rife.bld.Project;
import rife.bld.operations.JarOperation;
import rife.tools.FileUtils;

import java.io.File;


import static rife.bld.dependencies.Scope.compile;
import static rife.bld.dependencies.Scope.test;

public interface PerunPlatform {

    String JSOUP_VERSION = "1.17.2";
    String HELIDON_VERSION = "4.0.8";
    String JUNIT_VERSION = "5.10.2";
    String HAMCREST_VERSION = "2.2";
    String JUNIT_PLATFORM_VERSION = "1.10.2";
    String BLD_VERSION = "1.9.1";
    int JAVA_VERSION = 22;

    //PERUNCS definitions
    String PERUN_GROUP_ID="peruncs";
    String PERUN_UTILITIES_ARTIFACT = "peruncs-utilities";
    String PERUN_UTILITIES_VERSION = "0.0.1";
    String PERUN_HELIDON_ARTIFACT="peruncs-helidon";
    String PERUN_HELIDON_VERSION = "0.0.1";
    String PERUN_WEB_ASSETS_ARTIFACT="peruncs-web-assets";
    String PERUN_WEB_ASSETS_VERSION = "0.0.1";

    static void configurePerunUtilities(BaseProject project){
        project.scope(compile).include(project.dependency(PERUN_GROUP_ID, PERUN_UTILITIES_ARTIFACT, PERUN_UTILITIES_VERSION));
    }

    static void configurePerunHelidon(BaseProject project){
        project.scope(compile).include(project.dependency(PERUN_GROUP_ID, PERUN_HELIDON_ARTIFACT, PerunPlatform.PERUN_HELIDON_VERSION));
    }

    static void  configureBld(BaseProject project){
        project.scope(compile).include(project.dependency("com.uwyn.rife2", "bld", BLD_VERSION));
    }

    static void configureJunit(Project project){
        project.scope(test).include(project.dependency("org.junit.jupiter", "junit-jupiter", JUNIT_VERSION));
    }

    static void configureJunitPlatform(BaseProject project){
        project.scope(test)
                .include(project.dependency("org.junit.platform", "junit-platform-launcher", JUNIT_PLATFORM_VERSION))
                .include(project.dependency("org.junit.platform", "junit-platform-console-standalone", JUNIT_PLATFORM_VERSION));
    }

    static void configureHamcrest(BaseProject project){
        project.scope(test).include(project.dependency("org.hamcrest", "hamcrest", HAMCREST_VERSION));
    }

    static void configureJsoup(BaseProject project) {
        project.scope(compile).include(project.dependency("org.jsoup", "jsoup", JSOUP_VERSION));
    }

    static void configureHelidonServer(BaseProject project) {
        project.scope(compile)
                .include(project.dependency("io.helidon.webserver", "helidon-webserver", HELIDON_VERSION))
                .include(project.dependency("io.helidon.webserver", "helidon-webserver-http2", HELIDON_VERSION))
                .include(project.dependency("io.helidon.webserver", "helidon-webserver-static-content", HELIDON_VERSION))
                .include(project.dependency("io.helidon.common", "helidon-common-media-type", HELIDON_VERSION))
                .include(project.dependency("io.helidon.config", "helidon-config-yaml", HELIDON_VERSION));
    }

    static void configureHelidonWebClient(BaseProject project) {
        project.scope(compile)
                .include(project.dependency("io.helidon.webclient", "helidon-webclient", HELIDON_VERSION))
                .include(project.dependency("io.helidon.webclient", "helidon-http2-webclient", HELIDON_VERSION))
                .include(project.dependency("io.helidon.http.media", "helidon-http-media-jsonp", HELIDON_VERSION));
    }

    static  void prepareDistribution(Project p) throws Exception {
        p.jar();
        var fileNoExtension = p.name()+"-"+p.version();
        var distDir = new File(p.buildDistDirectory(), fileNoExtension);
        if(distDir.exists()) FileUtils.deleteDirectory(distDir);
        var distLibDir = new File(distDir,"libs");
        var ignore = distLibDir.mkdirs();
        for (var jar : p.compileClasspathJars()) FileUtils.copy(jar, new File(distLibDir, jar.getName()));
        for (var jar : p.runtimeClasspathJars()) FileUtils.copy(jar, new File(distLibDir, jar.getName()));
        FileUtils.copy(new File(p.buildDistDirectory(), p.jarFileName()), new File(distDir, p.jarFileName()));
        new JarOperation()
                .destinationDirectory(p.buildDistDirectory())
                .destinationFileName(fileNoExtension+".zip")
                .sourceDirectories(distDir)
                .execute();
    }

}

//dependencyResolutionManagement {
//    versionCatalogs {
//        libs {
//
//            //JSOUP
//            version('jsoup','1.17.2')
//            library('jsoup','org.jsoup','jsoup').versionRef('jsoup')
//
//            //SLF4J
//            version('slf4j','2.0.+')
//            library('slf4j-jdk14', 'org.slf4j','slf4j-jdk14').versionRef('slf4j')
//
//            //АWS
//            version('aws', '2.25.18')
//            library('aws-s3','software.amazon.awssdk','s3').versionRef('aws')
//            library('aws-crt-client','software.amazon.awssdk', 'aws-crt-client').versionRef('aws')
//            bundle('aws-s3', ['aws-s3', 'aws-crt-client'])
//
//            //HELIDON
//            version('helidon', '4.0.8')
//            library('helidon-webserver','io.helidon.webserver', 'helidon-webserver').versionRef('helidon')
//            library('helidon-webserver-http2','io.helidon.webserver', 'helidon-webserver-http2').versionRef('helidon')
//            library('helidon-webserver-static-content','io.helidon.webserver', 'helidon-webserver-static-content').versionRef('helidon')
//            library('helidon-webserver-observe-health','io.helidon.webserver.observe', 'helidon-webserver-observe-health').versionRef('helidon')
//            library('helidon-webserver-observe-metrics','io.helidon.webserver.observe', 'helidon-webserver-observe-metrics').versionRef('helidon')
//            library('helidon-http-http2','io.helidon.http', 'helidon-http-http2').versionRef('helidon')
//            library('helidon-http-media-jsonp','io.helidon.http.media', 'helidon-http-media-jsonp').versionRef('helidon')
//            //library('helidon-http-media','io.helidon.http.media', 'helidon-http-media').versionRef('helidon')
//            library('helidon-common-media-type','io.helidon.common', 'helidon-common-media-type').versionRef('helidon')
//            library('helidon-config-yaml','io.helidon.config', 'helidon-config-yaml').versionRef('helidon')
//            library('helidon-webclient','io.helidon.webclient', 'helidon-webclient').versionRef('helidon')
//            library('helidon-http2-webclient','io.helidon.webclient', 'helidon-webclient-http2').versionRef('helidon')
//            library('helidon-logging-jul','io.helidon.logging', 'helidon-logging-jul').versionRef('helidon')
//            library('helidon-metrics-system-meters','io.helidon.metrics', 'helidon-metrics-system-meters').versionRef('helidon')
//            library('helidon-health-checks','io.helidon.health', 'helidon-health-checks').versionRef('helidon')
//            library('helidon-webserver-testing-junit5','io.helidon.webserver.testing.junit5','helidon-webserver-testing-junit5').versionRef('helidon')
//
//            bundle('helidon-webclient',['helidon-webclient', 'helidon-http2-webclient','helidon-http-media-jsonp'])
//            bundle('helidon-config',['helidon-config-yaml'])
//            bundle('helidon-webserver',
//                    ['helidon-webserver',
//                    'helidon-webserver-http2',
//                    'helidon-webserver-static-content',
//                    'helidon-common-media-type',
//                    'helidon-config-yaml'
//                    ])
//
//            //LUCENE
//            //version('lucene', '9.10.0')
//            //library('lucene-queryparser','org.apache.lucene', 'lucene-queryparser').versionRef('lucene')
//
////            //JUNIT5
////            version('junit', '5.10.2')
////            library('junit-jupiter', 'org.junit.jupiter','junit-jupiter').versionRef('junit')
////            version('junit-platform-launcher', '1.10.2')
////            library('junit-platform-launcher', 'org.junit.platform','junit-platform-launcher').versionRef('junit-platform-launcher')
//
//            //HAMCREST
//            version('hamcrest', '2.2')
//            library('hamcrest','org.hamcrest', 'hamcrest').versionRef('hamcrest')
//
////            bundle('junit',['junit-jupiter', 'junit-platform-launcher', 'hamcrest'])
//
//            version('estore','1.3.2')
//            library('storage-embedded','org.eclipse.store', 'storage-embedded').versionRef('estore')
//            library('afs-blobstore','org.eclipse.store', 'afs-blobstore').versionRef('estore')
//            library('afs-aws-s3','org.eclipse.store', 'afs-aws-s3').versionRef('estore')
//
//            version('eserial', '1.3.2')
//            library('persistence-binary-jdk8','org.eclipse.serializer', 'persistence-binary-jdk8').versionRef('eserial')
//            library('persistence-binary-jdk17','org.eclipse.serializer', 'persistence-binary-jdk17').versionRef('eserial')
//            library('codegen-entity','org.eclipse.serializer', 'codegen-entity').versionRef('eserial')
//            library('codegen-wrapping','org.eclipse.serializer', 'codegen-wrapping').versionRef('eserial')
//
//            bundle('estore',['storage-embedded','afs-blobstore','afs-aws-s3','persistence-binary-jdk8','persistence-binary-jdk17'])
//            //bundle('estore-lucene',['storage-embedded','afs-blobstore','afs-aws-s3','persistence-binary-jdk8','persistence-binary-jdk17','lucene-queryparser'])
//
//            version('excel','0.18.0')
//            library('fastexcel-reader', 'org.dhatim', 'fastexcel-reader').versionRef('excel')
//
//            //TSID
//            version('tsid','5.2.6')
//            library('tsid-creator','com.github.f4b6a3','tsid-creator').versionRef('tsid')
//
//            //Fuzzy Wuzzy string search library
//            version('fuzzywuzzy','1.4.0')
//            library( 'fuzzywuzzy','me.xdrop','fuzzywuzzy').versionRef('fuzzywuzzy')
//
//        }
//
//    }
