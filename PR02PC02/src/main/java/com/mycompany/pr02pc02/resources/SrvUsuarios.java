package com.mycompany.pr02pc02.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.json.JsonObject;

@Path("usuarios")
public class SrvUsuarios {
    // Endpoint para recibir datos de registro en JSON
    @POST
    @Path("registro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(JsonObject datos) {
        // Extraer datos del JSON
        String nombre = datos.getString("nombre", "");
        String apellido = datos.getString("apellido", "");
        String correo = datos.getString("correo", "");
        String telefono = datos.getString("telefono", "");
        String password = datos.getString("password", "");
        // Aqui iria la logica para guardar el usuario en la base de datos
        // Ahorita solo quiero validar la conexion
        JsonObject respuesta = javax.json.Json.createObjectBuilder()
            .add("status", "ok")
            .add("mensaje", "Usuario registrado correctamente")
            .add("usuario", javax.json.Json.createObjectBuilder()
                .add("nombre", nombre)
                .add("apellido", apellido)
                .add("correo", correo)
                .add("telefono", telefono)
            )
            .build();
        return Response.ok(respuesta).build();
    }
}
