CREATE TABLE tipo_empleado (
                               id_tipo_empleado SERIAL PRIMARY KEY,
                               nombre VARCHAR(255) NOT NULL
);

CREATE TABLE usuario (
                         id_usuario SERIAL PRIMARY KEY,
                         nombre VARCHAR(255) NOT NULL,
                         apellidos VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         cuenta_bancaria VARCHAR(255),
                         num_seguridad_social VARCHAR(255),
                         tipo_Empleado_id BIGINT,
                         puesto_trabajo VARCHAR(255),  -- Nuevo campo para el puesto de trabajo
                         departamento VARCHAR(255),    -- Nuevo campo para el departamento
                         fecha_contratacion VARCHAR(255), -- Nuevo campo para la fecha de contratación
                         telefono_contacto VARCHAR(20),   -- Nuevo campo para el teléfono de contacto
                         direccion VARCHAR(255),       -- Nuevo campo para la dirección
                         fecha_nacimiento VARCHAR(255) -- Nuevo campo para la fecha de nacimiento
);

CREATE TABLE archivos (
                          id_archivo SERIAL PRIMARY KEY,
                          nombre_archivo VARCHAR(255) NOT NULL,
                          categoria VARCHAR(255) NOT NULL,
                          url_archivo TEXT NOT NULL,
                          usuario_id BIGINT,
                          fecha_subida TIMESTAMP NOT NULL
);
CREATE TABLE mensaje (
                         id_mensaje SERIAL PRIMARY KEY,
                         id_usuario INT NOT NULL,
                         contenido TEXT NOT NULL,
                         fecha TIMESTAMP NOT NULL
);
create table curso (
                       id_curso integer generated by default as identity,
                       usuario_id integer not null,
                       curso varchar(255) not null,
                       link_certificacion varchar(255) not null,
                       primary key (id_curso)
);
create table fichajes (
                          hora time(6) not null,
                          usuario_id integer not null,
                          id bigint generated by default as identity,
                          fecha varchar(255),
                          tiempo_total_jornada varchar(255),
                          tipo varchar(255) not null,
                          primary key (id)
);
create table usuarios_vacaciones (
                                     id_usuario_vacaciones integer generated by default as identity,
                                     usuario_id integer,
                                     vacacion_id integer,
                                     primary key (id_usuario_vacaciones)
);
create table vacacion (
                          aprobada boolean not null,
                          dias integer not null,
                          fecha_fin VARCHAR(40) not null,
                          fecha_inicio VARCHAR(40) not null,
                          id_usuario integer not null,
                          id_vacacion integer generated by default as identity,
                          pendiente boolean not null,
                          primary key (id_vacacion)
);
alter table if exists archivos
    add constraint FKi624ubmj944vcxuqe834hucho
        foreign key (usuario_id)
            references usuario;
alter table if exists curso
    add constraint FKqgls7v9osmi479bbgi2t9aaoq
        foreign key (usuario_id)
            references usuario;
alter table if exists fichajes
    add constraint FK1cm95wfpl6upnm3olmgkfm96c
        foreign key (usuario_id)
            references usuario;
alter table if exists usuario
    add constraint FKmsql398f5cx0h4e8dg15sywof
        foreign key (tipo_empleado_id)
            references tipo_empleado;
alter table if exists usuarios_vacaciones
    add constraint FKioqsh7b13afp03g2av78oxsh
        foreign key (usuario_id)
            references usuario;
alter table if exists usuarios_vacaciones
    add constraint FKaopa27xee47eorgeijvkyuufd
        foreign key (vacacion_id)
            references vacacion;
alter table if exists vacacion
    add constraint FKiy18glww8m1xif6uni8o4a9km
        foreign key (id_usuario)
            references usuario;

INSERT INTO tipo_empleado (nombre) VALUES ('Admin');
INSERT INTO tipo_empleado (nombre) VALUES ('Empleado');
-- Insert para Admin
-- Insert para Admin
INSERT INTO usuario (nombre, apellidos, email, password, cuenta_bancaria, num_seguridad_social, tipo_Empleado_id, puesto_trabajo, departamento, fecha_contratacion, telefono_contacto, direccion, fecha_nacimiento)
VALUES ('Admin', 'Admin', 'admin@example.com', 'admin', 'ES12345678901234567890', '123456789', 1, 'Administrador', 'Administración', '2020-01-01', '123456789', 'Calle Principal 1', '1980-01-01');

-- Insert para Empleado 1 (Front-end Developer)
INSERT INTO usuario (nombre, apellidos, email, password, cuenta_bancaria, num_seguridad_social, tipo_Empleado_id, puesto_trabajo, departamento, fecha_contratacion, telefono_contacto, direccion, fecha_nacimiento)
VALUES ('Juan', 'Pérez', 'juan@example.com', 'juan123', 'ES09876543210987654321', '987654321', 2, 'Desarrollador Front-end', 'Desarrollo', '2021-05-15', '987654321', 'Calle Secundaria 2', '1990-04-15');

-- Insert para Empleado 2 (Back-end Developer)
INSERT INTO usuario (nombre, apellidos, email, password, cuenta_bancaria, num_seguridad_social, tipo_Empleado_id, puesto_trabajo, departamento, fecha_contratacion, telefono_contacto, direccion, fecha_nacimiento)
VALUES ('María', 'García', 'maria@example.com', 'maria123', 'ES11223344556677889900', '112233445', 2, 'Desarrollador Back-end', 'Desarrollo', '2021-06-01', '112233445', 'Avenida del Sol 3', '1992-07-10');

-- Insert para Empleado 3 (Cloud Engineer)
INSERT INTO usuario (nombre, apellidos, email, password, cuenta_bancaria, num_seguridad_social, tipo_Empleado_id, puesto_trabajo, departamento, fecha_contratacion, telefono_contacto, direccion, fecha_nacimiento)
VALUES ('Carlos', 'Rodríguez', 'carlos@example.com', 'carlos123', 'ES22334455667788990011', '223344556', 2, 'Cloud Engineer', 'Infraestructura', '2022-01-20', '223344556', 'Calle Nube 4', '1991-02-20');

-- Insert para Empleado 3
INSERT INTO usuario (nombre, apellidos, email, password, cuenta_bancaria, num_seguridad_social, tipo_Empleado_id)
VALUES ('Carlos', 'López', 'carlos@example.com', 'carlos123', 'ES22334455667788990011', '223344556', 2);
-- Inserts para el trabajador BackEnd Developer (usuario_id = 1)
INSERT INTO archivos (nombre_archivo, categoria, url_archivo, usuario_id, fecha_subida)
VALUES ('Contrato BackEnd Developer', 'contrato', 'https://bucketportalempleado.s3.us-east-1.amazonaws.com/Contrato_BackEnd_Developer.pdf', 1, NOW());

INSERT INTO archivos (nombre_archivo, categoria, url_archivo, usuario_id, fecha_subida)
VALUES ('Nomina BackEnd Developer Diciembre', 'nomina', 'https://bucketportalempleado.s3.us-east-1.amazonaws.com/Nomina_BackEnd_Developer_Dec.pdf', 1, NOW());

INSERT INTO archivos (nombre_archivo, categoria, url_archivo, usuario_id, fecha_subida)
VALUES ('Nomina BackEnd Developer Noviembre', 'nomina', 'https://bucketportalempleado.s3.us-east-1.amazonaws.com/Nomina_BackEnd_Developer_Nov.pdf', 1, NOW());

INSERT INTO archivos (nombre_archivo, categoria, url_archivo, usuario_id, fecha_subida)
VALUES ('Nomina BackEnd Developer', 'nomina', 'https://bucketportalempleado.s3.us-east-1.amazonaws.com/Nomina_BackEnd_Developer.pdf', 1, NOW());

-- Inserts para el trabajador Cloud Engineer (usuario_id = 2)
INSERT INTO archivos (nombre_archivo, categoria, url_archivo, usuario_id, fecha_subida)
VALUES ('Contrato Cloud Engineer', 'contrato', 'https://bucketplanetalert.s3.amazonaws.com/Contrato_Cloud_Engineer.pdf', 2, NOW());

INSERT INTO archivos (nombre_archivo, categoria, url_archivo, usuario_id, fecha_subida)
VALUES ('Nomina Cloud Engineer Noviembre', 'nomina', 'https://bucketportalempleado.s3.us-east-1.amazonaws.com/Nomina_Cloud_Engineer_Nov.pdf', 2, NOW());

INSERT INTO archivos (nombre_archivo, categoria, url_archivo, usuario_id, fecha_subida)
VALUES ('Nomina Cloud Engineer', 'nomina', 'https://bucketportalempleado.s3.us-east-1.amazonaws.com/Nomina_Cloud_Engineer.pdf', 2, NOW());

-- Inserts para el trabajador FrontEnd Developer (usuario_id = 3)
INSERT INTO archivos (nombre_archivo, categoria, url_archivo, usuario_id, fecha_subida)
VALUES ('Contrato FrontEnd Developer', 'contrato', 'https://bucketportalempleado.s3.us-east-1.amazonaws.com/Contrato_FrontEnd_Developer.pdf', 3, NOW());

INSERT INTO archivos (nombre_archivo, categoria, url_archivo, usuario_id, fecha_subida)
VALUES ('Nomina FrontEnd Developer Noviembre', 'nomina', 'https://bucketportalempleado.s3.us-east-1.amazonaws.com/Nomina_FrontEnd_Developer+(1).pdf', 3, NOW());

INSERT INTO archivos (nombre_archivo, categoria, url_archivo, usuario_id, fecha_subida)
VALUES ('Nomina FrontEnd Developer', 'nomina', 'https://bucketportalempleado.s3.us-east-1.amazonaws.com/Nomina_Desarrolladora_Frontend.pdf', 3, NOW());
