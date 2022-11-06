CREATE TABLE lab2_pdv_movie
(
    id             int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title          varchar,
    genre          varchar,
    country        varchar,
    movie_language varchar,
    starring       varchar,
    directed_by    varchar,
    screenplay_by  varchar,
    duration       varchar,
    description    varchar
);
CREATE TABLE lab2_pdv_users
(
    id       int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username varchar(100) not null,
    password varchar      not null,
    role     varchar(100) not null
)
