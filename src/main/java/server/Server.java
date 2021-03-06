package server;

import clima.AccuWeather;
import clima.OpenWeather;
import clima.TemperaturaAccuWeather;
import controllers.*;
import cron.RepositorioGuardarropas;
import cron.RepositorioUsuarios;
import domain.evento.FrecuenciaEvento;
import exceptions.UsuarioInexistente;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static spark.Spark.after;
import static spark.Spark.before;

public class Server {
    public static void main(String[] args) {
        Spark.port(getHerokuAssignedPort()); //Si falla, devuelve 9010 para debugear local
        //Spark.staticFiles.location("/public");
        Spark.staticFileLocation("/public");
        Spark.init();

        ControllerSesion controllerSesion= new ControllerSesion();

        //iniciarUsuarioDePrueba();

        //controllerSesion.persistirUsuarioPrueba();

        before((request, response) -> {
            

          /** Ejemplo de pagina de Spark
            boolean authenticated;
            // ... check if authenticated
            if (!authenticated) {
                halt(401, "You are not welcome here");
            }

           */
        });


        /** GLUE CODE */


        Spark.get("/login", controllerSesion::mostrarLogin, new HandlebarsTemplateEngine());
        Spark.post("/login", controllerSesion::iniciarSesion, new HandlebarsTemplateEngine());
        Spark.post("/", controllerSesion::cerrarSesion, new HandlebarsTemplateEngine());

        ControllerPerfil controllerPerfil= new ControllerPerfil();
        Spark.get("/perfil", controllerPerfil::mostrar, new HandlebarsTemplateEngine());

        ControllerGuardarropas controllerGuardarropas = new ControllerGuardarropas();
        Spark.get("/guardarropas", controllerGuardarropas::mostrarGuardarropas, new HandlebarsTemplateEngine());
        /* POST /guardarropas para crear guardarropas */

        ControllerPrendas controllerPrendas = new ControllerPrendas();
        Spark.get("/guardarropas/:id/prendas", controllerPrendas::mostrarPrendas,  new HandlebarsTemplateEngine());
        Spark.get("/guardarropas/:id/prendas/wizard", controllerPrendas::creacionPrenda,  new HandlebarsTemplateEngine());
        Spark.post("/guardarropas/:id/prendas", controllerPrendas::crearPrenda, new HandlebarsTemplateEngine());

        ControllerCalendario controllerCalendario = new ControllerCalendario();
        Spark.get("/calendario", controllerCalendario::mostrar, new HandlebarsTemplateEngine());

        ControllerEvento controllerEvento = new ControllerEvento();
        Spark.get("/evento/wizard", controllerEvento::creacionEvento, new HandlebarsTemplateEngine());
        Spark.post("/evento", controllerEvento::crear, new HandlebarsTemplateEngine());
        Spark.get("/evento/:nombre/sugerencias", controllerEvento::sugerenciasDelEvento, new HandlebarsTemplateEngine());
        Spark.post("/evento/:nombre/sugerencias/:id", controllerEvento::aceptarSugerencia, new HandlebarsTemplateEngine());
        Spark.get("/sugerencias/aceptadas", controllerEvento::verSugerenciasAceptadas, new HandlebarsTemplateEngine());
        Spark.post("/sugerencias/aceptadas/:id", controllerEvento::calificar, new HandlebarsTemplateEngine());

        DebugScreen.enableDebugScreen();

        //TODO: OJO, VER AL MOMENTO DE ver tema TRANSACCIONES

        after((request,response) -> {
            PerThreadEntityManagers.getEntityManager();
            PerThreadEntityManagers.closeEntityManager();
        });
    }


    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }

        return 9010; //return default port if heroku-port isn't set (i.e. on localhost)
    }



    public static void iniciarUsuarioDePrueba() {
        RepositorioUsuarios.admin.setPassword("12345");

        RepositorioUsuarios.admin.crearEvento("Cumplea??os de willy", RepositorioUsuarios.fechaCumpleWilly, FrecuenciaEvento.NO_SE_REPITE, "Casa de willy");
        RepositorioUsuarios.admin.crearEvento("Cumplea??os de pepe", RepositorioUsuarios.fechaCumplePepe, FrecuenciaEvento.NO_SE_REPITE, "Casa de pepe");
        RepositorioUsuarios.admin.crearEvento("Cumplea??os de robertito", RepositorioUsuarios.fechaCumpleRoberto, FrecuenciaEvento.NO_SE_REPITE, "Casa de roberto");
        RepositorioUsuarios.admin.crearEvento("Entrega tp dise??o", RepositorioUsuarios.entregaDise??o, FrecuenciaEvento.NO_SE_REPITE, "campus");

        TemperaturaAccuWeather temperatura = mock(TemperaturaAccuWeather.class);
        when(temperatura.getTemperature()).thenReturn(20.0);

        AccuWeather meteorologo = mock(AccuWeather.class);
        when(meteorologo.obtenerClima()).thenReturn(temperatura);

        RepositorioUsuarios.admin.getEventos().forEach(e -> e.generarSugerencias(meteorologo));
    }
}