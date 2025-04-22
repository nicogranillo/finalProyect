DELIMITER //

-- Función para agregar una nueva tarea
CREATE FUNCTION agregarTarea_DB(
    p_nombre VARCHAR(100),
    p_descripcion TEXT,
    p_fechaDeInicio DATE,
    p_fechaDeFinalizacion DATE,
    p_estado BOOLEAN,
    p_categoriaID INT,
    p_usuarioID INT
) RETURNS INT
DETERMINISTIC
BEGIN
    INSERT INTO tarea (nombre, descripcion, fechaDeInicio, fechaDeFinalizacion, estado, categoriaID, usuarioID)
    VALUES (p_nombre, p_descripcion, p_fechaDeInicio, p_fechaDeFinalizacion, p_estado, p_categoriaID, p_usuarioID);
    RETURN LAST_INSERT_ID();
END //

-- Función para editar una tarea existente
CREATE FUNCTION editarTarea_DB(
    p_tareaID INT,
    p_nombre VARCHAR(100),
    p_descripcion TEXT,
    p_fechaDeInicio DATE,
    p_fechaDeFinalizacion DATE,
    p_estado BOOLEAN,
    p_categoriaID INT,
    p_usuarioID INT
) RETURNS BOOLEAN
DETERMINISTIC
BEGIN
    UPDATE tarea
    SET
        nombre = p_nombre,
        descripcion = p_descripcion,
        fechaDeInicio = p_fechaDeInicio,
        fechaDeFinalizacion = p_fechaDeFinalizacion,
        estado = p_estado,
        categoriaID = p_categoriaID,
        usuarioID = p_usuarioID
    WHERE tareaID = p_tareaID;
    RETURN ROW_COUNT() > 0;
END //

-- Función para eliminar una tarea por su ID
CREATE FUNCTION eliminarTarea_DB(p_tareaID INT) RETURNS BOOLEAN
DETERMINISTIC
BEGIN
    DELETE FROM tarea WHERE tareaID = p_tareaID;
    RETURN ROW_COUNT() > 0;
END //

-- Función para ver el estado de una tarea por su ID
CREATE FUNCTION verEstadoTarea_DB(p_tareaID INT) RETURNS BOOLEAN
DETERMINISTIC
BEGIN
    DECLARE estado_tarea BOOLEAN;
    SELECT estado INTO estado_tarea
    FROM tarea
    WHERE tareaID = p_tareaID;
    RETURN estado_tarea;
END //

-- Función para marcar el estado de una tarea por su ID
CREATE FUNCTION marcarEstadoTarea_DB(p_tareaID INT, p_estado BOOLEAN) RETURNS BOOLEAN
DETERMINISTIC
BEGIN
    UPDATE tarea
    SET estado = p_estado
    WHERE tareaID = p_tareaID;
    RETURN ROW_COUNT() > 0;
END //

-- Función para agregar una nueva categoría
CREATE FUNCTION agregarCategoria_DB(p_nombre VARCHAR(100)) RETURNS INT
DETERMINISTIC
BEGIN
    INSERT INTO categoria (nombre)
    VALUES (p_nombre);
    RETURN LAST_INSERT_ID();
END //

DELIMITER ;