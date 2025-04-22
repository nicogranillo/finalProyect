USE toDoList;

-- Insertar datos de prueba en la tabla categoria
INSERT INTO categoria (categoriaID, nombre) VALUES
(1, 'Personal'),
(2, 'Trabajo'),
(3, 'Hogar'),
(4, 'Estudios');

-- Insertar datos de prueba en la tabla usuario
INSERT INTO usuario (usuarioID, nombre, contrasena) VALUES
(1, 'Ana Pérez', 'contrasena123'),
(2, 'Carlos López', 'clave456'),
(3, 'Sofía Gómez', 'seguro789');

-- Insertar datos de prueba en la tabla tarea
INSERT INTO tarea (tareaID, nombre, descripcion, fechaDeInicio, fechaDeFinalizacion, estado, categoriaID, usuarioID) VALUES
(1, 'Comprar leche', 'Ir al supermercado a comprar leche y pan', '2025-04-10', '2025-04-10', FALSE, 3, 1),
(2, 'Preparar informe', 'Elaborar el informe de ventas mensual', '2025-04-11', '2025-04-12', FALSE, 2, 2),
(3, 'Estudiar álgebra', 'Repasar los temas del examen', '2025-04-12', '2025-04-15', FALSE, 4, 1),
(4, 'Llamar al técnico', 'Coordinar la visita para la reparación del lavavajillas', '2025-04-13', '2025-04-13', FALSE, 3, 3),
(5, 'Enviar correo al cliente', 'Responder las preguntas del cliente sobre el proyecto', '2025-04-10', '2025-04-10', TRUE, 2, 1),
(6, 'Leer capítulo del libro', 'Avanzar con la lectura del libro de historia', '2025-04-14', '2025-04-16', FALSE, 1, 2),
(7, 'Revisar código', 'Hacer una revisión del código del módulo nuevo', '2025-04-11', '2025-04-11', TRUE, 2, 3),
(8, 'Pagar facturas', 'Pagar las facturas de luz e internet', '2025-04-15', '2025-04-15', FALSE, 3, 1),
(9, 'Hacer ejercicio', 'Ir al gimnasio por la tarde', '2025-04-12', '2025-04-12', TRUE, 1, 2),
(10, 'Preparar presentación', 'Crear las diapositivas para la reunión del viernes', '2025-04-16', '2025-04-18', FALSE, 2, 3);