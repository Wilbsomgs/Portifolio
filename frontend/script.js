/* O conteúdo vem do content.js, gerado pelo back-end (somente leitura). Este arquivo só exibe: não há dados para editar aqui. */
(()=>{
const clean=v=>typeof v==="string"?v.replace(/[<>]/g,"").replace(/"/g,"”"):Array.isArray(v)?v.map(clean):v&&typeof v==="object"?Object.fromEntries(Object.entries(v).map(([k,x])=>[k,clean(x)])):v;
const url=u=>/^https?:\/\//i.test(u)?u:"";
const src=window.PORTFOLIO?clean(window.PORTFOLIO):null;
if(!src)console.warn("content.js não encontrado: inicie o back-end e publique o conteúdo.");
const f=(src&&src.profile)||{},toEntry=x=>(x||[]).map(e=>({t:e.title,o:e.organization,p:e.period,d:e.description}));
const DATA={
  email:f.email||"",github:url(f.githubUrl),cvUrl:url(f.cvUrl),about:f.about||"",
  roles:f.roles&&f.roles.length?f.roles:[""],
  skills:(f.skills||[]).map(k=>[k.name,k.level]),
  projects:((src&&src.projects)||[]).map(p=>({id:String(p.id),title:p.title,status:p.status,published:true,tech:p.tech||[],repo:url(p.repoUrl),desc:p.summary,long:p.description})),
  exp:toEntry(src&&src.experience),edu:toEntry(src&&src.education)
};
const I={home:'<path d="M3 10.5 12 3l9 7.5V20a1 1 0 0 1-1 1h-5v-6H9v6H4a1 1 0 0 1-1-1z"/>',user:'<circle cx="12" cy="8" r="4"/><path d="M4 21c0-4 4-6 8-6s8 2 8 6"/>',folder:'<path d="M3 6a2 2 0 0 1 2-2h4l2 2h8a2 2 0 0 1 2 2v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/>',brief:'<rect x="3" y="7" width="18" height="13" rx="2"/><path d="M9 7V5a2 2 0 0 1 2-2h2a2 2 0 0 1 2 2v2M3 13h18"/>',cap:'<path d="M2 9l10-5 10 5-10 5z"/><path d="M6 11v5c0 1.5 3 3 6 3s6-1.5 6-3v-5"/>',mail:'<rect x="3" y="5" width="18" height="14" rx="2"/><path d="m3 7 9 6 9-6"/>',down:'<path d="M12 4v11m-5-4 5 5 5-5M5 20h14"/>',arr:'<path d="M5 12h14m-6-6 6 6-6 6"/>',send:'<path d="m22 2-11 11M22 2l-7 20-4-9-9-4z"/>',sun:'<circle cx="12" cy="12" r="4"/><path d="M12 2v2m0 16v2M2 12h2m16 0h2M5 5l1.5 1.5m11 11L19 19M5 19l1.5-1.5m11-11L19 5"/>',moon:'<path d="M20 14.5A8 8 0 0 1 9.5 4 8 8 0 1 0 20 14.5z"/>',x:'<path d="M6 6l12 12M18 6 6 18"/>',cube:'<path d="M12 3 3 8v8l9 5 9-5V8z"/><path d="m3 8 9 5 9-5M12 13v8"/>',gh:'<path d="M15 22v-4a4.8 4.8 0 0 0-1-3.5c3 0 6-2 6-5.5.08-1.25-.27-2.48-1-3.5.28-1.15.28-2.35 0-3.5 0 0-1 0-3 1.5-2.64-.5-5.36-.5-8 0C6 2 5 2 5 2c-.3 1.15-.3 2.35 0 3.5A5.4 5.4 0 0 0 4 9c0 3.5 3 5.5 6 5.5-.39.49-.68 1.05-.85 1.65-.17.6-.22 1.23-.15 1.85v4"/><path d="M9 18c-4.51 2-5-2-7-2"/>'};
const ic=(n,s=20)=>`<svg width="${s}" height="${s}" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">${I[n]}</svg>`;
const $=id=>document.getElementById(id);
const R=document.documentElement;

/* Navegação */
const N=[["inicio","home","Início"],["sobre","user","Sobre e skills"],["projetos","folder","Projetos"],["trajetoria","brief","Experiência e educação"],["contato","mail","Contato"]];
$("nav").innerHTML=N.map(([id,i,l])=>`<a href="#${id}" aria-label="${l}" title="${l}">${ic(i,22)}</a>`).join("");

/* Textos com ícones */
$("about").textContent=DATA.about;
$("seeWork").innerHTML="Ver projetos "+ic("arr",18);
document.querySelectorAll(".cv").forEach(b=>b.innerHTML="Baixar currículo "+ic("down",18));
$("t-sk").innerHTML=ic("cube")+"Sobre e skills";
$("t-pj").innerHTML=ic("folder")+"Projetos";
$("t-ex").innerHTML=ic("brief")+"Experiência";
$("t-ed").innerHTML=ic("cap")+"Educação";
$("send").innerHTML="Enviar mensagem "+ic("send",18);
$("dx").innerHTML=ic("x",18);
$("mail").href="mailto:"+DATA.email;$("mail").innerHTML=ic("mail")+DATA.email;
$("gh").href=DATA.github;$("gh").innerHTML=ic("gh")+"GitHub (abre em nova aba)";
$("ft").innerHTML=`<a class="ib" href="${DATA.github}" target="_blank" rel="noopener noreferrer" aria-label="GitHub (abre em nova aba)">${ic("gh")}</a><a class="ib" href="mailto:${DATA.email}" aria-label="Enviar e-mail">${ic("mail")}</a>`;

/* Skills e números */
const pub=DATA.projects.filter(p=>p.published);
$("bars").innerHTML=DATA.skills.map(([n,v])=>`<div class="bar"><span>${n}</span><div class="tr" aria-hidden="true"><i style="--w:${v}%"></i></div><b>${v}%</b></div>`).join("");
$("stats").innerHTML=[[DATA.skills.length,"Tecnologias"],[pub.length,"Projetos"],[pub.filter(p=>p.status==="Em andamento").length,"Em andamento"]].map(([n,l])=>`<div class="card stat"><b>${n}</b><span>${l}</span></div>`).join("");
new IntersectionObserver((es,o)=>es.forEach(e=>{if(e.isIntersecting){e.target.classList.add("on");o.unobserve(e.target)}}),{threshold:.3}).observe($("sk"));

/* Projetos: lista, filtro por tecnologia e detalhes */
const techs=[...new Set(pub.flatMap(p=>p.tech))];
$("chips").innerHTML=["Todos",...techs].map((t,i)=>`<button type="button" class="chip" aria-pressed="${i===0}" data-f="${i?t:""}">${t}</button>`).join("");
const tags=a=>a.map(t=>`<span class="tag">${t}</span>`).join("");
const link=p=>p.repo?`<a class="btn sm" href="${p.repo}" target="_blank" rel="noopener noreferrer">${ic("gh",16)}Código (nova aba)</a>`:"";
$("grid").innerHTML=pub.map(p=>`<article class="card pj" data-t="${p.tech.join("|")}"><div class="th">${ic("folder",40)}</div><div class="row"><h3>${p.title}</h3><span class="st">${p.status}</span></div><p class="mu">${p.desc}</p><div class="tags">${tags(p.tech)}</div><div class="row"><button type="button" class="btn sm" data-id="${p.id}">Ver detalhes ${ic("arr",16)}</button>${p.repo?`<a class="ib" href="${p.repo}" target="_blank" rel="noopener noreferrer" aria-label="Código de ${p.title} no GitHub (nova aba)">${ic("gh")}</a>`:""}</div></article>`).join("");
$("chips").addEventListener("click",e=>{const b=e.target.closest(".chip");if(!b)return;
  document.querySelectorAll(".chip").forEach(c=>c.setAttribute("aria-pressed",c===b));
  document.querySelectorAll(".pj").forEach(c=>c.hidden=!!b.dataset.f&&!c.dataset.t.split("|").includes(b.dataset.f))});
const dlg=$("dlg");
$("grid").addEventListener("click",e=>{const b=e.target.closest("button[data-id]");if(!b)return;
  const p=pub.find(x=>x.id===b.dataset.id);
  $("dt").textContent=p.title;$("ds").textContent=p.status;$("dd").textContent=p.long||p.desc;$("dtg").innerHTML=tags(p.tech);$("dl").innerHTML=link(p);
  dlg.showModal()});
$("dx").onclick=()=>dlg.close();
dlg.addEventListener("click",e=>{if(e.target===dlg)dlg.close()});

/* Experiência e educação */
const tl=a=>a.map(x=>`<li><h3>${x.t}</h3><div class="o">${x.o}</div><div class="d">${x.p}</div><p class="mu" style="margin-top:4px">${x.d}</p></li>`).join("");
$("exp").innerHTML=tl(DATA.exp);$("edu").innerHTML=tl(DATA.edu);

/* Currículo: usa o PDF informado ou abre a impressão para salvar como PDF */
document.querySelectorAll(".cv").forEach(b=>b.onclick=()=>DATA.cvUrl?window.open(DATA.cvUrl,"_blank","noopener"):window.print());

/* Contato: abre o e-mail já preenchido (sem back-end ainda) */
let tt;const toast=m=>{const t=$("toast");t.textContent=m;t.classList.add("show");clearTimeout(tt);tt=setTimeout(()=>t.classList.remove("show"),4000)};
$("form").addEventListener("submit",e=>{e.preventDefault();const d=new FormData(e.target);
  const body=d.get("mensagem")+"\n\n"+d.get("nome")+" ("+d.get("email")+")";
  toast("Mensagem pronta. Confirme o envio no seu aplicativo de e-mail.");
  window.location.href="mailto:"+DATA.email+"?subject="+encodeURIComponent(d.get("assunto"))+"&body="+encodeURIComponent(body);
  e.target.reset()});

/* Tema claro e escuro */
const tb=$("theme");
try{const s=localStorage.getItem("theme");if(s)R.dataset.theme=s}catch(e){}
const paint=()=>{const d=R.dataset.theme==="dark";tb.innerHTML=ic(d?"sun":"moon");tb.setAttribute("aria-label",d?"Ativar tema claro":"Ativar tema escuro")};
tb.onclick=()=>{R.dataset.theme=R.dataset.theme==="dark"?"light":"dark";try{localStorage.setItem("theme",R.dataset.theme)}catch(e){}paint()};
paint();

/* Item ativo no menu */
const navIO=new IntersectionObserver(es=>es.forEach(e=>{if(e.isIntersecting)document.querySelectorAll("nav a").forEach(a=>a.hash==="#"+e.target.id?a.setAttribute("aria-current","true"):a.removeAttribute("aria-current"))}),{rootMargin:"-45% 0px -50% 0px"});
N.forEach(([id])=>navIO.observe($(id)));

/* Efeito de digitação (desligado com movimento reduzido) */
if(!matchMedia("(prefers-reduced-motion: reduce)").matches){const rs=DATA.roles,re=$("role");let i=0,j=0,del=false;
  (function t(){const w=rs[i];re.textContent=w.slice(0,j);
    if(!del&&j===w.length){del=true;return setTimeout(t,1800)}
    if(del&&j===0){del=false;i=(i+1)%rs.length}
    j+=del?-1:1;setTimeout(t,del?35:70)})()}
})();