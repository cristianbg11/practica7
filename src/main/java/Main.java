
import freemarker.template.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.String.join;
import static spark.Spark.*;
import static spark.Spark.*;

public class Main {
    public static ArrayList estudiantes=new ArrayList<Estudiante>();
    public static void main(String[] args) throws IOException {

        port(8080);
        //freemarker.template.Configuration config = new Configuration();
        //config.setClassForTemplateLoading(this.getClass(), "/templates/");
        staticFiles.location("/publico");

        /*
        JSONObject req = new JSONObject(join(br.readLine(),""));
        JSONObject locs = req.getJSONObject("");
        JSONArray recs = locs.getJSONArray("");
         */

        /*
        String str = "";
        while (null != (str = br.readLine())) {
            System.out.println(str);
        }
         */

        get("/", (request, response)-> {
           // return renderContent("publico/index.html");
            Map<String, Object> attributes = new HashMap<>();

            attributes.put("listado",estudiantes);
            return new ModelAndView(attributes, "index.ftl");

        } , new FreeMarkerEngine());

        get("/listar", (request, response) -> {
            URL url = new URL("http://localhost:4567/rest/estudiantes/");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

            JSONArray recs = new JSONArray(br.readLine());

            for (int i = 0; i < recs.length(); ++i) {
                Estudiante estudiante = new Estudiante();
                JSONObject rec = recs.getJSONObject(i);
                estudiante.matricula = rec.getInt("matricula");
                estudiante.nombre = rec.getString("nombre");
                if(rec.isNull("correo") || rec.isNull("carrera")){
                    estudiante.correo = "null";
                    estudiante.carrera = "null";
                } else {
                    estudiante.correo = rec.getString("correo");
                    estudiante.carrera = rec.getString("carrera");
                }
                estudiantes.add(estudiante);
            }
            response.redirect("/lista");
            return "Listado estudiantes";
        });

        get("/lista", (request, response)-> {
            // return renderContent("publico/index.html");
            Map<String, Object> attributes = new HashMap<>();

            attributes.put("listado",estudiantes);
            return new ModelAndView(attributes, "lista.ftl");

        } , new FreeMarkerEngine());

        get("/view", (request, response)-> {
            Map<String, Object> attributes = new HashMap<>();
            int id = Integer.parseInt(request.queryParams("id"));
            //Estudiante est = (Estudiante) estudiantes.get(id);
            attributes.put("vista",estudiantes.get(id));
            return new ModelAndView(attributes, "view.ftl");

        } , new FreeMarkerEngine());

        get("/edit", (request, response)-> {
            Map<String, Object> attributes = new HashMap<>();
            int id = Integer.parseInt(request.queryParams("id"));
            attributes.put("edicion",estudiantes.get(id));
            attributes.put("id",id);
            return new ModelAndView(attributes, "edit.ftl");

        } , new FreeMarkerEngine());

        get("/delete", (request, response) -> {
            int id = Integer.parseInt(request.queryParams("id"));
            estudiantes.remove(id);
            response.redirect("/lista");
            return "Estudiante Eliminado";
        });

        post("/insertar", (request, response) -> {
            Estudiante est=new Estudiante();
            est.matricula = Integer.parseInt(request.queryParams("matricula"));
            est.nombre=request.queryParams("nombre");
            est.carrera = request.queryParams("apellido");
            est.correo = request.queryParams("telefono");
            estudiantes.add(est);
            response.redirect("/");
            return "Estudiante Creado";
        });

        post("/actualizar", (request, response) -> {
            int id = Integer.parseInt(request.queryParams("id"));
            Estudiante est = (Estudiante) estudiantes.get(id);
            est.matricula = Integer.parseInt(request.queryParams("matricula"));
            est.nombre=request.queryParams("nombre");
            est.correo = request.queryParams("apellido");
            est.carrera = request.queryParams("telefono");
            response.redirect("/");
            return 0;
        });

    }
    private static String renderContent(String htmlFile) throws IOException, URISyntaxException {
        URL url = Main.class.getResource(htmlFile);
        Path path = Paths.get(url.toURI());
        return new String(Files.readAllBytes(path), Charset.defaultCharset());
    }
}
