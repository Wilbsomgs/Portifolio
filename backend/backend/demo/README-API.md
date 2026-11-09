## Publicação do conteúdo (como o site lê os dados)

O site **não chama a API**. O back-end gera o arquivo estático `frontend/content.js`, e a página o carrega direto.
Assim o conteúdo aparece assim que a página abre, com o back-end ligado ou não, e quem acessa o site só recebe arquivos de leitura.

- **Quando é gerado:** a cada criação, edição ou remoção feita em `/admin.html` ou em `/api/admin/**`, e manualmente com `POST /api/admin/publish`.
- **Onde:** `app.export-file` (padrão `../../../frontend/content.js`, relativo à pasta `backend/backend/demo`). Pode ser trocado com a variável `EXPORT_FILE`.
- **Fluxo:** ligar o back-end, editar na administração, conferir o `content.js`, fazer commit e publicar a pasta `frontend` (GitHub Pages, Netlify, Vercel…).
  Em produção só o `frontend` precisa estar no ar; o back-end e a administração podem rodar apenas na sua máquina.
- **Não edite o `content.js` à mão:** ele é sobrescrito a cada publicação.