
CREATE TABLE autor (
    cod_au SERIAL PRIMARY KEY,
    nome VARCHAR(40) NOT NULL
);

CREATE TABLE assunto (
    cod_as SERIAL PRIMARY KEY,
    descricao VARCHAR(20) NOT NULL
);

CREATE TABLE livro (
    cod_livro SERIAL PRIMARY KEY,
    titulo VARCHAR(40) NOT NULL,
    editora VARCHAR(40) NOT NULL,
    edicao INTEGER NOT NULL,
    ano_publicacao VARCHAR(4) NOT NULL,
    valor INTEGER NOT NULL
);

CREATE TABLE livro_autor (
    cod_livro INTEGER REFERENCES livro(cod_livro),
    cod_au INTEGER REFERENCES autor(cod_au),
    PRIMARY KEY (cod_livro, cod_au)
);

CREATE TABLE livro_assunto (
    cod_livro INTEGER REFERENCES livro(cod_livro),
    cod_as INTEGER REFERENCES assunto(cod_as),
    PRIMARY KEY (cod_livro, cod_as)
);
