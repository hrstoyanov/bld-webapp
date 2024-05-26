package insbiz.webapp;

import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;

record LandingPage(App app) implements HttpService {

    @Override
    public void routing(HttpRules rules) {
        rules
                .get(this::render)
                .delete(this::logOut)
        ;
    }

    private void logOut(ServerRequest req, ServerResponse res){
        //todo remove session!
        res.send(// language=HTML
                """
               <main>
                    <section class='logout'>
                       <p>You logged out of TourBiz!</p>
                       <p><a href='/'>Click</a> here to log in again.</p>
                   </section>
               </main>
               """);
    }

    void render(ServerRequest request, ServerResponse response){
        response.send(// language=HTML
                STR."""
                <!DOCTYPE html>
                <html lang="en">
                  <head>
                    <meta charset="UTF-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                    <meta name="description" content="Tourism and vacation business management">
                    <link rel="apple-touch-icon" sizes="180x180" href="/images/favicons/apple-touch-icon.png">
                    <link rel="icon" type="image/png" sizes="32x32" href="/images/favicons/favicon-32x32.png">
                    <link rel="icon" type="image/png" sizes="16x16" href="/images/favicons/favicon-16x16.png">
                    <link rel="manifest" href="/site.webmanifest">
                    <style>
                        @layer reset, theme, global, layout, components,app;
                        @import url('reset.css?v1.1') ;
                        @import url('theme.css?v1.1') ;
                        @import url('app.css?v1.1') ;
                        @import url('layout.css?v1.1');
                        @import url('components.css?v1.1');
                    </style>
                    <title>TourBiz</title>
                  </head>
                  <body>
                    <h1>Hello ins app!</h1>
                  </body>
                </html>
                """);
    }

    private String generateDashBoard(ServerRequest request){
        return  """
                <section class='messages'>
                     <p>This is the default screen with several useful dashboards.</p>
                </section>
                """;//todo
    }

    @Override
    public void afterStop() {
        app.shutDownServer();
    }

}

