package org.gestorTareas;

import static spark.Spark.*;
import java.sql.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TareaAPI {

    // Hardcoded credentials for quick login test
    private static final String VALID_USERNAME = "root";
    private static final String VALID_PASSWORD = "Srot405@";

    // Método de conexión a la base de datos
    private static Connection connectDB() {
        String url = "jdbc:mysql://localhost:3306/basedatos";
        String user = "root";
        String password = "Srot405@";
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos.");
            return con;
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        port(4567);
        staticFiles.location("/public");

        // CORS
        options("/*", (req, res) -> {
            String h = req.headers("Access-Control-Request-Headers");
            if (h != null) res.header("Access-Control-Allow-Headers", h);
            String m = req.headers("Access-Control-Request-Method");
            if (m != null) res.header("Access-Control-Allow-Methods", m);
            return "OK";
        });
        before((req, res) -> res.header("Access-Control-Allow-Origin", "*"));

        // LOGIN
        post("/login", (req, res) -> {
            String username = req.queryParams("username");
            String password = req.queryParams("password");
            if (username == null || password == null) {
                res.status(400);
                return "Parámetros incompletos.";
            }
            if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
                return "Inicio de sesión exitoso";
            } else {
                res.status(401);
                return "Credenciales incorrectas";
            }
        });

        // CREAR USUARIO: requiere nombre, contrasena
        post("/usuario", (req, res) -> {
            Connection con = connectDB();
            if (con == null) { res.status(500); return "Error DB"; }

            String nombre = req.queryParams("nombre");
            String contrasena = req.queryParams("contrasena");

            if (nombre == null || contrasena == null) {
                res.status(400);
                return "Parámetros incompletos.";
            }

            // Verificar si ya existe un usuario con el mismo nombre
            String checkSql = "SELECT COUNT(*) FROM usuario WHERE nombre = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
                checkStmt.setString(1, nombre);
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    res.status(409);
                    return "El usuario ya existe.";
                }
            }

            // Insertar nuevo usuario
            String insertSql = "INSERT INTO usuario (nombre, contrasena) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setString(1, nombre);
                ps.setString(2, contrasena);
                ps.executeUpdate();
                return "Usuario creado correctamente";
            } catch (SQLException e) {
                res.status(500);
                return "Error al crear usuario: " + e.getMessage();
            } finally {
                try { con.close(); } catch (SQLException ignored) {}
            }
        });

        // CREAR CATEGORÍA: requiere nombre
        post("/categoria", (req, res) -> {
            Connection con = connectDB();
            if (con == null) { res.status(500); return "Error DB"; }

            String nombre = req.queryParams("nombre");

            if (nombre == null) {
                res.status(400);
                return "Parámetro 'nombre' requerido.";
            }

            // Verificar si ya existe una categoría con ese nombre
            String checkSql = "SELECT COUNT(*) FROM categoria WHERE nombre = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
                checkStmt.setString(1, nombre);
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    res.status(409);
                    return "La categoría ya existe.";
                }
            }

            // Insertar nueva categoría
            String insertSql = "INSERT INTO categoria (nombre) VALUES (?)";
            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setString(1, nombre);
                ps.executeUpdate();
                return "Categoría creada correctamente";
            } catch (SQLException e) {
                res.status(500);
                return "Error al crear categoría: " + e.getMessage();
            } finally {
                try { con.close(); } catch (SQLException ignored) {}
            }
        });

        // LISTAR TAREAS
        get("/tarea", (req, res) -> {
            res.type("application/json");
            Connection con = connectDB(); if (con==null){res.status(500);return "{}";}
            String sql = "SELECT t.tareaID, t.nombre, t.descripcion, t.fechaDeInicio,"
                    + " t.fechaDeFinalizacion, t.estado, u.nombre AS usuario, c.nombre AS categoria"
                    + " FROM tarea t JOIN usuario u ON t.usuarioID=u.usuarioID"
                    + " JOIN categoria c ON t.categoriaID=c.categoriaID";
            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                JSONArray arr = new JSONArray();
                while(rs.next()){
                    JSONObject o = new JSONObject();
                    o.put("tareaID", rs.getInt("tareaID"));
                    o.put("nombre", rs.getString("nombre"));
                    o.put("descripcion", rs.getString("descripcion"));
                    o.put("fechaDeInicio", rs.getString("fechaDeInicio"));
                    o.put("fechaDeFinalizacion", rs.getString("fechaDeFinalizacion"));
                    o.put("estado", rs.getBoolean("estado"));
                    o.put("usuario", rs.getString("usuario"));
                    o.put("categoria", rs.getString("categoria"));
                    arr.add(o);
                }
                return arr.toJSONString();
            } catch (SQLException e) {
                res.status(500);
                return "{}";
            } finally { try{con.close();}catch(SQLException ignored){} }
        });

        // CREAR TAREA
        post("/tarea", (req, res) -> {
            Connection con = connectDB(); if (con==null){res.status(500);return "Error DB";}
            String nombre = req.queryParams("nombre");
            String descripcion = req.queryParams("descripcion");
            int usuarioID = Integer.parseInt(req.queryParams("usuarioID"));
            int categoriaID = Integer.parseInt(req.queryParams("categoriaID"));
            String sql = "INSERT INTO tarea (nombre, descripcion, fechaDeInicio, fechaDeFinalizacion, estado, categoriaID, usuarioID)"
                    + " VALUES (?, ?, CURDATE(), CURDATE(), false, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nombre);
                ps.setString(2, descripcion);
                ps.setInt(3, categoriaID);
                ps.setInt(4, usuarioID);
                ps.executeUpdate();
                return "Tarea añadida correctamente";
            } catch(SQLException e) {
                res.status(500);
                return "Error al añadir tarea: "+e.getMessage();
            } finally { try{con.close();}catch(SQLException ignored){} }
        });

        // ELIMINAR TAREA
        delete("/tarea/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Connection con = connectDB(); if(con==null){res.status(500);return "Error DB";}
            String sql = "DELETE FROM tarea WHERE tareaID=?";
            try (PreparedStatement ps = con.prepareStatement(sql)){
                ps.setInt(1,id);
                int r=ps.executeUpdate();
                if(r>0)return "Tarea eliminada correctamente";
                res.status(404);return "Tarea no encontrada";
            } catch(SQLException e){res.status(500);return "Error al eliminar tarea: "+e.getMessage();}
            finally{try{con.close();}catch(SQLException ignored){}}
        });

        // ELIMINAR USUARIO
        delete("/usuario/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Connection con = connectDB(); if(con==null){res.status(500);return "Error DB";}
            String sql = "DELETE FROM usuario WHERE usuarioID=?";
            try (PreparedStatement ps = con.prepareStatement(sql)){
                ps.setInt(1,id);
                int r=ps.executeUpdate();
                if(r>0)return "Usuario eliminado correctamente";
                res.status(404);return "Usuario no encontrado";
            } catch(SQLException e){res.status(500);return "Error al eliminar usuario: "+e.getMessage();}
            finally{try{con.close();}catch(SQLException ignored){}}
        });

        // ELIMINAR CATEGORÍA
        delete("/categoria/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Connection con = connectDB(); if(con==null){res.status(500);return "Error DB";}
            String sql = "DELETE FROM categoria WHERE categoriaID=?";
            try (PreparedStatement ps = con.prepareStatement(sql)){
                ps.setInt(1,id);
                int r=ps.executeUpdate();
                if(r>0)return "Categoría eliminada correctamente";
                res.status(404);return "Categoría no encontrada";
            } catch(SQLException e){res.status(500);return "Error al eliminar categoría: "+e.getMessage();}
            finally{try{con.close();}catch(SQLException ignored){}}
        });
    }
}
