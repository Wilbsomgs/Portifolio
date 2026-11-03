# API do portfólio (Spring Boot)

Visitantes só **leem** (`GET /api/portfolio`). Criar, editar e remover só com login do administrador.

## Rodar

```bash
cd backend/demo
export ADMIN_PASSWORD='uma-senha-forte'        # PowerShell: $env:ADMIN_PASSWORD="uma-senha-forte"
./mvnw spring-boot:run                          # Windows: mvnw.cmd spring-boot:run
```

Sem `ADMIN_PASSWORD` a aplicação não inicia (não existe senha padrão).
Variáveis opcionais: `ADMIN_USERNAME` (padrão `admin`), `CORS_ORIGINS` (endereços do site, separados por vírgula),
`DB_URL`, `DB_USER`, `DB_PASSWORD` (PostgreSQL em produção). Adicione `data/` ao `.gitignore` (banco H2 local).

## Endpoints

| Método | Rota | Acesso |
|---|---|---|
| GET | `/api/portfolio` | público |
| PUT | `/api/admin/profile` | admin |
| GET, POST | `/api/admin/projects` | admin |
| PUT, DELETE | `/api/admin/projects/{id}` | admin |
| GET, POST | `/api/admin/timeline` | admin |
| PUT, DELETE | `/api/admin/timeline/{id}` | admin |

## Exemplos (curl)

```bash
# adicionar projeto
curl -u admin:$ADMIN_PASSWORD -H "Content-Type: application/json" -X POST http://localhost:8080/api/admin/projects \
  -d '{"title":"Meu projeto","status":"Concluído","summary":"Resumo","description":"Detalhes","repoUrl":"https://github.com/Wilbsomgs/x","published":true,"tech":["Java","Spring Boot"]}'

# remover projeto 3
curl -u admin:$ADMIN_PASSWORD -X DELETE http://localhost:8080/api/admin/projects/3

# atualizar informações gerais
curl -u admin:$ADMIN_PASSWORD -H "Content-Type: application/json" -X PUT http://localhost:8080/api/admin/profile \
  -d '{"email":"voce@email.com","githubUrl":"https://github.com/Wilbsomgs","cvUrl":"","about":"Texto","roles":["Desenvolvedor Full Stack"],"skills":[{"name":"Java","level":70}]}'

# adicionar experiência (kind: EXPERIENCE ou EDUCATION)
curl -u admin:$ADMIN_PASSWORD -H "Content-Type: application/json" -X POST http://localhost:8080/api/admin/timeline \
  -d '{"kind":"EXPERIENCE","title":"Cargo","organization":"Empresa","period":"2026 – Presente","description":"O que fez"}'
```

## Segurança

HTTP Basic envia a senha a cada requisição: em produção use **HTTPS** (RNF003). Em `CORS_ORIGINS` coloque só o endereço real do site.
