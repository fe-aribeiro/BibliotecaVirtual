-- V3__insert_massa_dados.sql
-- Script de carga de massa de dados para testes - PostgreSQL

-- Inserir Autores
INSERT INTO autor (nome) VALUES 
('Carlos Drummond'),
('Clarice Lispector'),
('Machado de Assis'),
('Cecília Meireles'),
('Jorge Amado'),
('Monteiro Lobato'),
('Graciliano Ramos'),
('Rachel de Queiroz'),
('José de Alencar'),
('Lima Barreto');

-- Inserir Assuntos
INSERT INTO assunto (descricao) VALUES 
('Literatura'),
('Ficção'),
('História'),
('Romance'),
('Drama'),
('Fantasia'),
('Poesia'),
('Crônica'),
('Política'),
('Infantil'),
('Suspense'),
('Biografia'),
('Aventura'),
('Sociologia'),
('Mitologia'),
('Tecnologia'),
('Economia'),
('Psicologia'),
('Mistério'),
('Ensaios');

-- Inserir Livros
INSERT INTO livro (titulo, editora, edicao, ano_publicacao, valor) VALUES
('O Avesso da Pele', 'Companhia das Letras', 1, '2020', 5550),
('A Paixão Segundo G.H.', 'Rocco', 2, '1964', 4200),
('Dom Casmurro', 'Saraiva', 3, '1899', 3899),
('Vidas Secas', 'Record', 1, '1938', 4775),
('Capitães da Areia', 'Companhia das Letras', 4, '1937', 3990),
('Reinações de Narizinho', 'Brasil', 1, '1931', 3540),
('Quarto de Despejo', 'Ática', 2, '1960', 3299),
('O Cortiço', 'Globo', 5, '1890', 4150),
('Memórias Póstumas', 'Penguin', 2, '1881', 4565),
('Sagarana', 'Editora UFMG', 1, '1946', 5000),
('A Hora da Estrela', 'Rocco', 1, '1977', 3399),
('Auto da Compadecida', 'Agir', 2, '1955', 3798),
('Macunaíma', 'Brasiliense', 3, '1928', 4099),
('Os Sertões', 'Companhia Editora Nacional', 1, '1902', 6050),
('Iracema', 'Martins', 2, '1865', 3650);

-- Vincular livros com autores
INSERT INTO livro_autor (cod_livro, cod_au) VALUES 
(1, 1), (1, 2),
(2, 2),
(3, 3), (3, 4),
(4, 7),
(5, 5), (5, 3),
(6, 6),
(7, 8),
(8, 3), (8, 9),
(9, 3),
(10, 10),
(11, 2),
(12, 10), (12, 4),
(13, 5),
(14, 9), (14, 4),
(15, 9);

-- Vincular livros com assuntos
INSERT INTO livro_assunto (cod_livro, cod_as) VALUES
(1, 1), (1, 4), (1, 7),
(2, 4), (2, 19),
(3, 1), (3, 4),
(4, 4), (4, 5),
(5, 1), (5, 2), (5, 4),
(6, 10),
(7, 3), (7, 9),
(8, 1), (8, 3), (8, 14),
(9, 1), (9, 4),
(10, 2), (10, 13), (10, 18),
(11, 1), (11, 5), (11, 19),
(12, 1), (12, 4), (12, 20),
(13, 2), (13, 15),
(14, 3), (14, 9), (14, 14),
(15, 4), (15, 1);
