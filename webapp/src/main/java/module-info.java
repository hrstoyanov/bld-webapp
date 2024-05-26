
@SuppressWarnings({"requires-automatic", "requires-transitive-automatic"})
module insbiz.webapp {
    //requires java.logging;

//    requires peruncs.utilities;
//    requires tourbiz.datamodel;
//    requires peruncs.geocode.mapsco;

    requires io.helidon.webserver;
    requires io.helidon.webserver.staticcontent;
    requires io.helidon.logging.common;
    requires io.helidon.http;
    requires peruncs.helidon;
    requires peruncs.utilities;
    //requires io.helidon.webclient;

//    requires org.eclipse.serializer.configuration;
//    requires org.eclipse.store.afs.blobstore;
//    requires org.eclipse.store.afs.aws.s3;
//    requires org.eclipse.serializer.persistence.binary.jdk8;
//    requires org.eclipse.serializer.persistence.binary.jdk17;
//
//    requires software.amazon.awssdk.auth;
//    requires software.amazon.awssdk.regions;
//    requires software.amazon.awssdk.services.s3;
//    requires software.amazon.awssdk.http.crt;
//    requires peruncs.eclipsestore;
//
//    requires me.xdrop.fuzzywuzzy;
//    requires org.jsoup;
}
