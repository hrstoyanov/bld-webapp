package insbiz;

import rife.bld.Project;

import java.io.File;

abstract class BaseBld  extends Project {

     BaseBld(InsBizBld parent, String root){
         javaRelease = parent.javaRelease();
         version = version(0,0,1);
         workDirectory = new File(parent.workDirectory(),root);
         javaRelease = parent.javaRelease();
         buildBldDirectory = parent.buildBldDirectory();
         srcBldDirectory = parent.srcBldDirectory();
         downloadSources = parent.downloadSources();
         autoDownloadPurge = parent.autoDownloadPurge();
         repositories = parent.repositories();
     }
}
