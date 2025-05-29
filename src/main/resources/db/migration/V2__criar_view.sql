CREATE OR REPLACE VIEW vw_livros_por_autor AS
SELECT
    a.cod_au AS cod_autor,
    a.nome AS nome_autor,
    l.cod_livro,
    l.titulo,
    l.editora,
    l.edicao,
    l.ano_publicacao,
    l.valor,
    string_agg(DISTINCT s.descricao, ', ') AS assuntos
FROM
    autor a
JOIN
    livro_autor la ON la.cod_au = a.cod_au
JOIN
    livro l ON l.cod_livro = la.cod_livro
LEFT JOIN
    livro_assunto ls ON ls.cod_livro = l.cod_livro
LEFT JOIN
    assunto s ON s.cod_as = ls.cod_as
GROUP BY
    a.cod_au, a.nome, l.cod_livro, l.titulo, l.editora, l.edicao, l.ano_publicacao, l.valor
ORDER BY
    a.nome, l.titulo;
