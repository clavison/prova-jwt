# 💼 Sistema de Gestão de Funcionários

- Na pasta `resources`, foi adicionado um json pronto para ser importado no postman com todas as requisicoes disponiveis no sistema.

Projeto Spring Boot para gerenciamento de **Funcionários**, **Cargos** e **Usuários** com autenticação via **JWT** e controle de acesso baseado em **roles (ADMIN/USER)**. Utiliza **JPA** para persistência de dados, incluindo **CRUD completo**, **filtros** e **paginação**.

---

## 📦 Tecnologias utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Security + JWT
- Hibernate
- H2 / PostgreSQL / MySQL (adaptável)
- Maven
- Lombok
- Swagger (opcional, mas recomendado)

---

## 🧩 Entidades

### 👨‍💼 Funcionário
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | Long | Identificador |
| `nome` | String | Nome completo |
| `cargo` | Cargo | Cargo associado ao funcionário |

### 💼 Cargo
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | Long | Identificador |
| `nome` | String | Nome do cargo |
| `salario` | BigDecimal | Salário base |

---

### 👤 Usuário
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | Long | Identificador |
| `login` | String | Nome de usuário |
| `senha` | String (criptografada) | Senha de acesso |
| `roles` | List<Role> | Perfis de acesso |

### 🔐 Role
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | Long | Identificador |
| `descricao` | String | Ex: `ROLE_ADMIN`, `ROLE_USER` |

---

## 🔐 Segurança com JWT

### Perfis de acesso:
- **ADMIN**
  - Pode realizar todas as operações (CRUD completo para Funcionários, Cargos e Usuários).
- **USER**
  - Pode visualizar dados públicos.
  - Pode editar **somente seus próprios dados** (usuário autenticado).

### Regras de segurança:

| Recurso | ADMIN | USER |
|---------|-------|------|
| `GET /funcionarios` | ✅ | ✅ |
| `POST /funcionarios` | ✅ | ❌ |
| `PUT /funcionarios/{id}` | ✅ | ❌ |
| `DELETE /funcionarios/{id}` | ✅ | ❌ |
| `GET /usuarios` | ✅ | ❌ |
| `PUT /usuarios/{id}` | ✅ | ✅ *(somente o próprio usuário)* |

> ⚠️ Autenticação é obrigatória para todas as rotas protegidas.

---

## 📄 Funcionalidades

- ✅ CRUD completo para:
  - Funcionários
  - Cargos
  - Usuários
  - Roles
- ✅ Filtros dinâmicos (por nome, cargo etc.)
- ✅ Paginação e ordenação (`/funcionarios?page=0&size=10&sort=nome,asc`)
- ✅ Login com JWT
- ✅ Controle de acesso com roles
- ✅ Validações com Bean Validation
- ✅ Senhas criptografadas com BCrypt

---

## 🚀 Como rodar o projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repo.git
   cd seu-repo

