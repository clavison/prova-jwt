Entidades:
Funcionario (id, nome, cargo)
Cargo (id, nome salario)

Usuario (id, login, senha, roles)
Role (id, descricao)

* Utilizar JPA para os cadastros - todas as operações CRUD para funcionário, cargo, usuários e Roles
* Utilizar filtros e paginação
* Utilizar JWT de modo que:
    1 - Usuários ADMIN podem fazer todas as operações
    2 - Usuários USER podem apenas visualizar e editar os seus próprios dados
