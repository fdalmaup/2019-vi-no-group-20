package server;

import controllers.*;
import cron.RepositorioGuardarropas;
import cron.RepositorioUsuarios;
import domain.evento.FrecuenciaEvento;
import exceptions.UsuarioInexistente;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.after;
import static spark.Spark.before;

public class Server {
    public static void main(String[] args) {
        Spark.port(9010);
        //Spark.staticFiles.location("/public");
        Spark.staticFileLocation("/public");
        Spark.init();

        ControllerSesion controllerSesion= new ControllerSesion();

        iniciarUsuarioDePrueba();

        controllerSesion.persistirUsuarioPrueba();

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

        DebugScreen.enableDebugScreen();

        //TODO: OJO, VER AL MOMENTO DE ver tema TRANSACCIONES

        after((request,response) -> {
            PerThreadEntityManagers.getEntityManager();
            PerThreadEntityManagers.closeEntityManager();
        });
    }


    public static void iniciarUsuarioDePrueba() {
            RepositorioUsuarios.admin.setPassword("12345");
            RepositorioUsuarios.admin.crearEvento("Cumpleaños de willy", RepositorioUsuarios.fechaCumpleWilly, FrecuenciaEvento.NO_SE_REPITE, "Casa de willy");
            RepositorioUsuarios.admin.crearEvento("Cumpleaños de pepe", RepositorioUsuarios.fechaCumplePepe, FrecuenciaEvento.NO_SE_REPITE, "Casa de pepe");
            RepositorioUsuarios.admin.crearEvento("Cumpleaños de robertito", RepositorioUsuarios.fechaCumpleRoberto, FrecuenciaEvento.NO_SE_REPITE, "Casa de roberto");
            RepositorioUsuarios.admin.crearEvento("Entrega tp diseño", RepositorioUsuarios.entregaDiseño, FrecuenciaEvento.NO_SE_REPITE, "campus");
    }
}