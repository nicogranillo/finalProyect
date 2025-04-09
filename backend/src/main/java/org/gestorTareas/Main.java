package org.gestorTareas;

import java.util.Scanner;
import java.sql.*;

public class Main {
  public static Connection connectDB() {
    Connection connection = null;
    String url = "jdbc:mysql://localhost:3306/proyectofinal.sql";
    String user = "tu_usuario";
    String password = "tu_contraseña";

    try {
      connection = DriverManager.getConnection(url, user, password);
      System.out.println("Conexión exitosa a la base de datos.");
    } catch (SQLException e) {
      System.out.println("Error al conectar a la base de datos: " + e.getMessage());
    }

    return connection;
  }

  public static void addTask(Connection connection, String task) {
    String query = "INSERT INTO tareas (descripcion) VALUES (?)";
    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setString(1, task);
      pstmt.executeUpdate();
      System.out.println("Tarea añadida.");
    } catch (SQLException e) {
      System.out.println("Error al añadir tarea: " + e.getMessage());
    }
  }

  public static void listTasks(Connection connection) {
    String query = "SELECT * FROM tareas";
    try (Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
      System.out.println("Lista de tareas:");
      while (rs.next()) {
        System.out.println(rs.getInt("id") + ": " + rs.getString("descripcion"));
      }
    } catch (SQLException e) {
      System.out.println("Error al mostrar tareas: " + e.getMessage());
    }
  }

  public static void updateTask(Connection connection, int id, String newTask) {
    String query = "UPDATE tareas SET descripcion = ? WHERE id = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setString(1, newTask);
      pstmt.setInt(2, id);
      int updatedRows = pstmt.executeUpdate();
      if (updatedRows > 0) {
        System.out.println("Tarea editada.");
      } else {
        System.out.println("No se encontró la tarea con ese ID.");
      }
    } catch (SQLException e) {
      System.out.println("Error al editar tarea: " + e.getMessage());
    }
  }

  public static void deleteTask(Connection connection, int id) {
    String query = "DELETE FROM tareas WHERE id = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setInt(1, id);
      int deletedRows = pstmt.executeUpdate();
      if (deletedRows > 0) {
        System.out.println("Tarea eliminada.");
      } else {
        System.out.println("No se encontró la tarea con ese ID.");
      }
    } catch (SQLException e) {
      System.out.println("Error al eliminar tarea: " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    Connection connection = connectDB();
    Scanner keyboard = new Scanner(System.in);
    boolean execute = true;

    while (execute) {
      System.out.println("¡Bienvenido!");
      System.out.println("1. Añadir tarea.");
      System.out.println("2. Buscar tarea."); // Esta opción puede cambiar a "Mostrar todas las tareas"
      System.out.println("3. Editar tarea.");
      System.out.println("4. Eliminar una tarea.");
      System.out.println("5. Mostrar todas las tareas.");
      System.out.println("6. Salir.");

      int option = keyboard.nextInt();
      keyboard.nextLine(); // Consumir el salto de línea

      switch (option) {
        case 1: // Añadir tarea
          System.out.print("Introduce la tarea: ");
          String task = keyboard.nextLine();
          addTask(connection, task);
          break;

        case 2: // Buscar tarea (opcional si se quiere implementar)
          System.out.print("Introduce el ID de la tarea a buscar: ");
          int searchId = keyboard.nextInt();
          listTasks(connection); // Se puede ajustar para buscar específicamente
          break;

        case 3: // Editar tarea
          System.out.print("Introduce el ID de la tarea a editar: ");
          int editId = keyboard.nextInt();
          keyboard.nextLine(); // Consumir salta de línea
          System.out.print("Introduce la nueva descripción de la tarea: ");
          String newTask = keyboard.nextLine();
          updateTask(connection, editId, newTask);
          break;

        case 4: // Eliminar una tarea
          System.out.print("Introduce el ID de la tarea a eliminar: ");
          int deleteId = keyboard.nextInt();
          deleteTask(connection, deleteId);
          break;

        case 5: // Mostrar todas las tareas
          listTasks(connection);
          break;

        case 6: // Salir
          execute = false;
          System.out.println("Saliendo del programa.");
          break;

        default:
          System.out.println("Opción no válida. Por favor, intenta de nuevo.");
      }
    }
    try {
      connection.close();
    } catch (SQLException e) {
      System.out.println("Error al cerrar la conexión: " + e.getMessage());
    }
    keyboard.close();
  }
}