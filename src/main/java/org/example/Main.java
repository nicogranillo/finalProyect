package org.example;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
  public static Connection connectDB() {
    Connection connection = null;
    String url = "jdbc:mysql://localhost:3306/tu_basedatos";
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
  public static void main(String[] args) {
    int activitiesNumber = 0;
    boolean execute = true;
    String[] activities = new String[10];
    Scanner keyboard = new Scanner(System.in);

    while (execute) {
      System.out.println("¡Bienvenido!");
      System.out.println("1. Añadir tarea.");
      System.out.println("2. Buscar tarea.");
      System.out.println("3. Editar tarea.");
      System.out.println("4. Eliminar una tarea.");
      System.out.println("5. Mostrar todas las tareas.");
      System.out.println("6. Salir.");

      int option = keyboard.nextInt();
      keyboard.nextLine();

      switch (option) {
        case 1: // Añadir tarea
          if (activitiesNumber < activities.length) {
            System.out.print("Introduce la tarea: ");
            String task = keyboard.nextLine();
            activities[activitiesNumber] = task;
            activitiesNumber++;
            System.out.println("Tarea añadida.");
          } else {
            System.out.println("No se pueden añadir más tareas.");
          }
          break;

        case 2: // Buscar tarea
          System.out.print("Introduce el índice de la tarea (0-" + (activitiesNumber - 1) + "): ");
          int searchIndex = keyboard.nextInt();
          keyboard.nextLine();
          if (searchIndex >= 0 && searchIndex < activitiesNumber) {
            System.out.println("Tarea encontrada: " + activities[searchIndex]);
          } else {
            System.out.println("Índice no válido.");
          }
          break;

        case 3: // Editar tarea
          System.out.print("Introduce el índice de la tarea a editar (0-" + (activitiesNumber - 1) + "): ");
          int editIndex = keyboard.nextInt();
          keyboard.nextLine();
          if (editIndex >= 0 && editIndex < activitiesNumber) {
            System.out.print("Introduce la nueva tarea: ");
            String newTask = keyboard.nextLine();
            activities[editIndex] = newTask;
            System.out.println("Tarea editada.");
          } else {
            System.out.println("Índice no válido.");
          }
          break;

        case 4: // Eliminar una tarea
          System.out.print("Introduce el índice de la tarea a eliminar (0-" + (activitiesNumber - 1) + "): ");
          int deleteIndex = keyboard.nextInt();
          keyboard.nextLine();
          if (deleteIndex >= 0 && deleteIndex < activitiesNumber) {
            for (int i = deleteIndex; i < activitiesNumber - 1; i++) {
              activities[i] = activities[i + 1];
            }
            activities[activitiesNumber - 1] = null; // Limpiar la última tarea
            activitiesNumber--;
            System.out.println("Tarea eliminada.");
          } else {
            System.out.println("Índice no válido.");
          }
          break;

        case 5: // Mostrar todas las tareas
          System.out.println("Lista de tareas:");
          for (int i = 0; i < activitiesNumber; i++) {
            System.out.println(i + ": " + activities[i]);
          }
          break;

        case 6: // Salir
          execute = false;
          System.out.println("Saliendo del programa.");
          break;

        default:
          System.out.println("Opción no válida. Por favor, intenta de nuevo.");
      }
    }
    keyboard.close();
  }
}