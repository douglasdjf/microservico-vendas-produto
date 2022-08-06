INSERT INTO tb_categoria (id, descricao) VALUES (1, 'Comic Books');
INSERT INTO tb_categoria (id, descricao) VALUES  (2, 'Movies') ;
INSERT INTO tb_categoria (id, descricao) VALUES  (3, 'Books');


INSERT INTO tb_fornecedor (id, nome) VALUES (1, 'Panini Comics');
INSERT INTO tb_fornecedor (id, nome) VALUES  (2, 'Amazon');

INSERT INTO tb_produto (id, nome, fornecedor_id, categoria_id, quantidade, data_criacao) VALUES (1, 'Crise nas Infinitas Terras', 1, 1, 10, CURRENT_TIMESTAMP);
INSERT INTO tb_produto (id, nome, fornecedor_id, categoria_id, quantidade, data_criacao) VALUES  (2, 'Interestelar', 2, 2, 5, CURRENT_TIMESTAMP);
 INSERT INTO tb_produto (id, nome, fornecedor_id, categoria_id, quantidade, data_criacao) VALUES (3, 'Harry Potter E A Pedra Filosofal', 2, 3, 3, CURRENT_TIMESTAMP);