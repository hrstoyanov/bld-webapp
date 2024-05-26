package insbiz.webapp;

import io.helidon.config.Config;
import io.helidon.http.HeaderName;
import io.helidon.http.HeaderNames;
import io.helidon.logging.common.LogConfig;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.staticcontent.StaticContentService;
import peruncs.helidon.HelidonHTMX;

import static io.helidon.common.media.type.MediaTypes.TEXT_HTML;
import static io.helidon.config.ConfigSources.classpath;
import static io.helidon.config.ConfigSources.file;
import static io.helidon.http.Status.UNSUPPORTED_MEDIA_TYPE_415;
import static java.lang.System.Logger.Level.INFO;

public class App {

    private static final System.Logger LOGGER = System.getLogger(App.class.getName());
    private static final HeaderName SERVER = HeaderNames.create("Server");
    private Config config;
    private WebServer webServer;
    private LandingPage landingPage;

    public static void main(String[] args) {

        LogConfig.configureRuntime();

        var app = new App();

        //Load configurations
        app.config = Config
                .builder()
                .sources(file(System.getProperty("app.config", "config.yaml")).optional(),  classpath("application.yaml").optional())
                .build();

        app.landingPage = new LandingPage(app);

        //Configure and start the web server
        app.webServer = WebServer
                .builder()
                .config(app.config.get("server"))
                .routing(routingBuilder -> routingBuilder
                        .register("/", StaticContentService.builder("web").build())
                        .any("/*", (request, response) -> {
                            response.headers().add(SERVER, "InsBiz Server");
                            response.next();
                        })
                        //The following handlers are all HTMX!
                        .any("/*", (request, response) -> {
                            if(request.headers().isAccepted(TEXT_HTML)){
                                response.headers().contentType(TEXT_HTML);
                                if (request.headers().contains(HelidonHTMX.RequestHeaders.HX_REQUEST)) {
                                    response.next();
                                } else {
                                    //Someone punched in the URL in the browser directly.
                                    app.landingPage.render(request, response);
                                }
                            }
                            else{
                                response.status(UNSUPPORTED_MEDIA_TYPE_415);
                                response.send();
                            }
                        }))
                .build()
                .start();
    }

    void shutDownServer(){
        LOGGER.log(INFO,"IB: about to shut down system services for this web server");
//        quietCloseAll(tourbizEmbeddedStorageManager, crtHttpClient, geocoder);
//        tourbizEmbeddedStorageManager = null;
//        crtHttpClient = null;
//        geocoder =null;
        LOGGER.log(INFO,"TB: Shut down system services for this web server");
    }
}
