<title>{{TITLE}} map</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Source+Serif+4:opsz,wght@8..60,400;8..60,600&family=IBM+Plex+Sans:wght@400;500&family=IBM+Plex+Mono:wght@400&display=swap">
<style>
/* Layout: header and controls, then map + side panel (stacked on phones), then the glossary, gaps and Mermaid source */
:root {
  --bg: #f7f8fa; --surface: #ffffff; --fg: #1c2330; --muted: #5d6676;
  --line: #dde1e8; --accent: #2b5a8c; --edge: #8a93a3; --edge-strong: #2a3140;
  --c-0: #6b4fa3; --c-1: #b5622a; --c-2: #2f7f8f; --c-3: #2a7457; --c-4: #a3405e; --c-5: #6b7a2a; --c-6: #3e5fa8; --c-7: #8a6a3a;
  --display: "Source Serif 4", Georgia, serif;
  --body: "IBM Plex Sans", system-ui, sans-serif;
  --mono: "IBM Plex Mono", ui-monospace, monospace;
}
@media (prefers-color-scheme: dark) { :root:not([data-theme="light"]) {
  --bg: #12161d; --surface: #1a2029; --fg: #e3e7ee; --muted: #9aa3b2;
  --line: #2c3442; --accent: #8db6e3; --edge: #6b7486; --edge-strong: #dfe4ec;
  --c-0: #b39ce0; --c-1: #e59a66; --c-2: #77c3d1; --c-3: #74c9a2; --c-4: #e38aa5; --c-5: #b9c76d; --c-6: #97b0e6; --c-7: #d2b07e;
  color-scheme: dark } }
:root[data-theme="dark"] {
  --bg: #12161d; --surface: #1a2029; --fg: #e3e7ee; --muted: #9aa3b2;
  --line: #2c3442; --accent: #8db6e3; --edge: #6b7486; --edge-strong: #dfe4ec;
  --c-0: #b39ce0; --c-1: #e59a66; --c-2: #77c3d1; --c-3: #74c9a2; --c-4: #e38aa5; --c-5: #b9c76d; --c-6: #97b0e6; --c-7: #d2b07e;
  color-scheme: dark }
body { background: var(--bg); color: var(--fg); font: 15px/1.6 var(--body); padding: 0 16px; }
main { max-width: 1120px; margin: 0 auto; padding-block: 32px 64px; display: grid; gap: 32px; }
header { display: grid; gap: 6px; }
h1, h2, h3 { font-family: var(--display); font-weight: 600; text-wrap: balance; margin: 0; }
h1 { font-size: 2rem; line-height: 1.2; }
h2 { font-size: 1.3rem; }
h3 { font-size: 1.2rem; }
.lede { margin: 0; color: var(--muted); max-width: 75ch; }
code { font-family: var(--mono); font-size: 0.88em; overflow-wrap: anywhere; }
.lede code { color: var(--accent); }
section { display: grid; gap: 12px; min-width: 0; }
.controls { display: flex; flex-wrap: wrap; gap: 10px; align-items: center; }
.controls input { font: inherit; color: var(--fg); background: var(--surface); border: 1px solid var(--line); border-radius: 6px; padding: 7px 12px; width: min(280px, 100%); }
button { font: inherit; color: var(--fg); background: var(--surface); border: 1px solid var(--line); border-radius: 6px; padding: 6px 14px; cursor: pointer; }
button:hover { border-color: var(--accent); }
:focus-visible { outline: 2px solid var(--accent); outline-offset: 2px; }
.legend { display: flex; flex-wrap: wrap; gap: 6px 18px; font-size: 0.85rem; color: var(--muted); }
.legend span { display: inline-flex; align-items: center; gap: 6px; }
.sw { width: 12px; height: 12px; border-radius: 3px; border: 1.5px solid var(--c); background: color-mix(in srgb, var(--c) 22%, var(--surface)); }
.sw.missing { border-style: dashed; background: var(--surface); --c: var(--muted); }
.legend svg { overflow: visible; }
.workspace { display: grid; grid-template-columns: minmax(0, 1fr) 300px; gap: 16px; align-items: start; }
@media (max-width: 820px) { .workspace { grid-template-columns: minmax(0, 1fr); } }
.map { background: var(--surface); border: 1px solid var(--line); border-radius: 8px; min-width: 0; }
#map { display: block; width: 100%; height: clamp(440px, 72vh, 760px); touch-action: none; cursor: grab; }
.panel { background: var(--surface); border: 1px solid var(--line); border-radius: 8px; padding: 18px; display: grid; gap: 12px; min-width: 0; align-content: start; }
.panel p { margin: 0; }
.eyebrow { font-size: 0.72rem; letter-spacing: 0.08em; text-transform: uppercase; color: var(--muted); }
.chip { justify-self: start; font-size: 0.75rem; border: 1px solid var(--c); color: var(--fg); border-radius: 999px; padding: 0 8px; background: color-mix(in srgb, var(--c) 15%, var(--surface)); }
.panel ul { margin: 0; padding: 0; list-style: none; display: grid; gap: 4px; font-size: 0.92rem; }
.rel { color: var(--muted); }
.term-link { border: 0; background: none; padding: 0; color: var(--accent); text-decoration: underline; text-underline-offset: 2px; cursor: pointer; font: inherit; }
/* Graph */
.node rect { fill: color-mix(in srgb, var(--c) 18%, var(--surface)); stroke: var(--c); stroke-width: 1.2; }
.node.missing rect { fill: var(--surface); stroke-dasharray: 3 2; }
.node text { fill: var(--fg); font: 500 10px var(--body); pointer-events: none; }
.node.missing text { font-style: italic; fill: var(--muted); }
.node { cursor: pointer; outline: none; }
.node.sel rect { stroke-width: 2.6; }
.node:focus-visible rect { stroke: var(--accent); stroke-width: 2.6; }
.link { stroke: var(--edge); stroke-width: 1; fill: none; }
.link.inherit { stroke: var(--edge-strong); stroke-width: 1.4; }
.link.depend { stroke-dasharray: 4 3; }
.link.synonym { stroke: var(--edge-strong); stroke-width: 4; }
.link.syn-gap { stroke: var(--surface); stroke-width: 1.6; pointer-events: none; }
.link.hot { stroke: var(--accent); stroke-width: 1.8; }
.link.synonym.hot { stroke-width: 4.4; }
.lbl { fill: var(--muted); font: 9px var(--body); paint-order: stroke; stroke: var(--surface); stroke-width: 3px; pointer-events: none; }
.dim { opacity: 0.12; }
.mk-line { stroke: var(--edge-strong); fill: none; }
.mk-hollow { stroke: var(--edge-strong); fill: var(--surface); }
.mk-solid { fill: var(--edge-strong); stroke: var(--edge-strong); }
/* Glossary */
ul.defs { list-style: none; margin: 0; padding: 0; display: grid; grid-template-columns: repeat(auto-fill, minmax(min(100%, 340px), 1fr)); gap: 0 32px; }
ul.defs li { padding-block: 8px; border-bottom: 1px solid var(--line); }
dfn { font-style: normal; font-weight: 600; font-family: var(--display); font-size: 1.05em; color: var(--accent); }
ul.missing { margin: 0; padding: 0; list-style: none; display: flex; flex-wrap: wrap; gap: 8px; }
ul.missing li { border: 1px dashed var(--muted); border-radius: 4px; padding: 2px 10px; font-size: 0.9rem; }
details { background: var(--surface); border: 1px solid var(--line); border-radius: 8px; padding: 12px 16px; min-width: 0; }
summary { cursor: pointer; font-weight: 500; }
details[open] summary { margin-bottom: 10px; }
.src-tools { display: flex; flex-wrap: wrap; gap: 10px; align-items: center; margin-bottom: 8px; font-size: 0.85rem; color: var(--muted); }
pre.src { margin: 0; overflow: auto; max-height: 420px; font: 12px/1.5 var(--mono); background: var(--bg); border-radius: 6px; padding: 12px; }
</style>
<main>
  <header>
    <h1>{{TITLE}}</h1>
    <p class="lede">{{COUNT}} defined terms from <code>{{QUERY}}</code>, plus {{MISSING_COUNT}} terms they mention that have no note yet. {{LINK_COUNT}} links, each read from a definition.</p>
  </header>

  <section aria-label="Term map">
    <div class="controls">
      <input id="find" type="search" placeholder="Find a term" aria-label="Find a term" autocomplete="off">
      <button id="reset" type="button">Show all</button>
    </div>
    <div class="legend" id="legend"></div>
    <div class="workspace">
      <div class="map"><svg id="map" role="img" aria-label="Map of how the terms reference each other"></svg></div>
      <aside class="panel" id="panel" aria-live="polite"></aside>
    </div>
  </section>

  <section>
    <h2>Definitions</h2>
    <ul class="defs">
{{ITEMS}}
    </ul>
  </section>

  <section>
    <h2>Terms without a note</h2>
    <ul class="missing">
{{MISSING}}
    </ul>
  </section>

  <section{{SYNONYMS_HIDDEN}}>
    <h2>Synonyms without a definition</h2>
    <ul class="missing">
{{SYNONYMS}}
    </ul>
  </section>

  <section{{ABBREVIATIONS_HIDDEN}}>
    <h2>Abbreviations without a definition</h2>
    <ul class="missing">
{{ABBREVIATIONS}}
    </ul>
  </section>

  <details>
    <summary>Mermaid version</summary>
    <div class="src-tools"><button id="copy" type="button">Copy</button><span id="copied"></span><span>Saved as <code>diagram.mmd</code></span></div>
    <pre class="src" id="mmd">{{MERMAID}}</pre>
  </details>
</main>
<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/7.9.0/d3.min.js"></script>
<script>
const DATA = {{DATA}};
const KIND = {
  inherit: { out: 'is a kind of', inc: 'is a kind of this' },
  compose: { out: 'is part of', inc: 'is part of this' },
  aggregate: { out: 'contains', inc: 'contains this' },
  assoc: {},
  depend: {},
  synonym: { out: 'is a synonym of', inc: 'is a synonym of this' },
};
const phraseOut = l => KIND[l.kind].out || l.label;
const phraseIn = l => KIND[l.kind].inc || l.label + ' this';

const byId = new Map(DATA.nodes.map(n => [n.id, n]));
const nodes = DATA.nodes;
const links = DATA.links.map(l => ({ ...l, source: byId.get(l.s), target: byId.get(l.t) }));
const outOf = id => links.filter(l => l.s === id);
const into = id => links.filter(l => l.t === id);
const indeg = new Map(nodes.map(n => [n.id, into(n.id).length]));

// Legend
const legend = document.getElementById('legend');
for (const [k, name] of DATA.cats.entries()) {
  const s = document.createElement('span');
  s.innerHTML = `<i class="sw" style="--c: var(--c-${k})"></i>`;
  s.append(name);
  legend.append(s);
}
legend.insertAdjacentHTML('beforeend', `<span><i class="sw missing"></i>No note yet</span>
  <span><svg width="34" height="10"><line x1="0" y1="5" x2="26" y2="5" class="mk-line"/><path d="M26,1 L34,5 L26,9 Z" class="mk-hollow"/></svg>is a kind of</span>
  <span><svg width="34" height="10"><line x1="0" y1="5" x2="24" y2="5" class="mk-line"/><path d="M22,5 L28,1.5 L34,5 L28,8.5 Z" class="mk-solid"/></svg>is part of</span>
  <span><svg width="34" height="10"><path d="M0,5 L6,1.5 L12,5 L6,8.5 Z" class="mk-hollow"/><line x1="12" y1="5" x2="34" y2="5" class="mk-line"/></svg>contains</span>
  <span><svg width="34" height="10"><line x1="0" y1="5" x2="33" y2="5" class="mk-line"/><path d="M27,1.5 L33,5 L27,8.5" class="mk-line"/></svg>association</span>
  <span><svg width="34" height="10"><line x1="0" y1="5" x2="33" y2="5" class="mk-line" stroke-dasharray="4 3"/><path d="M27,1.5 L33,5 L27,8.5" class="mk-line"/></svg>uses</span>
  <span><svg width="34" height="10"><line x1="0" y1="5" x2="34" y2="5" class="mk-line" stroke-width="4"/><line x1="0" y1="5" x2="34" y2="5" stroke="var(--surface)" stroke-width="1.6"/></svg>synonym</span>`);

// Graph
const W = 1000, H = 760;
const svg = d3.select('#map').attr('viewBox', `0 0 ${W} ${H}`);
const defs = svg.append('defs');
const marker = (id, path, cls, refX, w) => defs.append('marker').attr('id', id).attr('viewBox', `0 0 ${w} 10`)
  .attr('refX', refX).attr('refY', 5).attr('markerWidth', w).attr('markerHeight', 10).attr('markerUnits', 'userSpaceOnUse')
  .attr('orient', 'auto-start-reverse').append('path').attr('d', path).attr('class', cls);
marker('m-tri', 'M0,0 L10,5 L0,10 Z', 'mk-hollow', 10, 10);
marker('m-dia-solid', 'M0,5 L6,1.5 L12,5 L6,8.5 Z', 'mk-solid', 12, 12);
marker('m-dia-hollow', 'M0,5 L6,1.5 L12,5 L6,8.5 Z', 'mk-hollow', 12, 12);
marker('m-arrow', 'M1,1.5 L9,5 L1,8.5', 'mk-line', 9, 10);

const root = svg.append('g');
const zoom = d3.zoom().scaleExtent([0.3, 4]).on('zoom', e => root.attr('transform', e.transform));
svg.call(zoom).on('dblclick.zoom', null);
svg.on('click', e => { if (e.target === svg.node()) select(null); });

const linkSel = root.append('g').selectAll('path').data(links).join('path')
  .attr('class', l => `link ${l.kind}`)
  .attr('marker-end', l => ({ inherit: 'url(#m-tri)', compose: 'url(#m-dia-solid)', assoc: 'url(#m-arrow)', depend: 'url(#m-arrow)' })[l.kind] || null)
  .attr('marker-start', l => l.kind === 'aggregate' ? 'url(#m-dia-hollow)' : null);
const gapSel = root.append('g').selectAll('path').data(links.filter(l => l.kind === 'synonym')).join('path').attr('class', 'link syn-gap');
const lblSel = root.append('g').selectAll('text').data(links).join('text').attr('class', 'lbl').attr('text-anchor', 'middle')
  .text(phraseOut).style('display', 'none');

function wrap(label) {
  const lines = [''];
  for (const w of label.split(' ')) {
    const cur = lines[lines.length - 1];
    if (cur && (cur + ' ' + w).length > 15) lines.push(w); else lines[lines.length - 1] = cur ? cur + ' ' + w : w;
  }
  return lines;
}
const nodeSel = root.append('g').selectAll('g').data(nodes).join('g')
  .attr('class', n => 'node' + (n.missing ? ' missing' : ''))
  .attr('style', n => `--c: var(--c-${n.cat})`)
  .attr('tabindex', 0).attr('role', 'button').attr('aria-label', n => n.id)
  .on('click', (e, n) => { e.stopPropagation(); select(n.id); })
  .on('keydown', (e, n) => { if (e.key === 'Enter' || e.key === ' ') { e.preventDefault(); select(n.id); } });
nodeSel.append('rect').attr('rx', 3);
nodeSel.append('text').attr('text-anchor', 'middle').each(function (n) {
  const ls = wrap(n.id), t = d3.select(this);
  ls.forEach((l, i) => t.append('tspan').attr('x', 0).attr('y', (i - (ls.length - 1) / 2) * 11 + 3.5).text(l));
});

function measure() {
  nodeSel.each(function (n) {
    const b = this.querySelector('text').getBBox();
    n.w = Math.max(b.width + 12, 30); n.h = Math.max(b.height + 7, 18);
    d3.select(this).select('rect').attr('x', -n.w / 2).attr('y', -n.h / 2).attr('width', n.w).attr('height', n.h);
  });
}

const sim = d3.forceSimulation(nodes)
  .force('link', d3.forceLink(links).distance(l => l.kind === 'inherit' ? 70 : 95).strength(0.5))
  .force('charge', d3.forceManyBody().strength(-260))
  .force('collide', d3.forceCollide(n => Math.hypot(n.w || 60, n.h || 18) / 2 + 6))
  .force('x', d3.forceX(W / 2).strength(0.05)).force('y', d3.forceY(H / 2).strength(0.06))
  .stop().on('tick', draw);

function edgePoint(a, b, gap) {
  const dx = b.x - a.x, dy = b.y - a.y;
  if (!dx && !dy) return [a.x, a.y];
  const t = Math.min(dx ? (a.w / 2 + gap) / Math.abs(dx) : Infinity, dy ? (a.h / 2 + gap) / Math.abs(dy) : Infinity);
  return [a.x + dx * t, a.y + dy * t];
}
function draw() {
  linkSel.attr('d', l => {
    const [x1, y1] = edgePoint(l.source, l.target, 1), [x2, y2] = edgePoint(l.target, l.source, 1);
    return `M${x1},${y1} L${x2},${y2}`;
  });
  gapSel.attr('d', l => `M${l.source.x},${l.source.y} L${l.target.x},${l.target.y}`);
  lblSel.attr('x', l => (l.source.x + l.target.x) / 2).attr('y', l => (l.source.y + l.target.y) / 2 - 3);
  nodeSel.attr('transform', n => `translate(${n.x},${n.y})`);
}

function fit() {
  const xs = nodes.map(n => n.x), ys = nodes.map(n => n.y);
  const x0 = Math.min(...xs) - 70, x1 = Math.max(...xs) + 70, y0 = Math.min(...ys) - 30, y1 = Math.max(...ys) + 30;
  const k = Math.min(4, 0.95 * Math.min(W / (x1 - x0), H / (y1 - y0)));
  svg.call(zoom.transform, d3.zoomIdentity.translate(W / 2, H / 2).scale(k).translate(-(x0 + x1) / 2, -(y0 + y1) / 2));
}

const reduce = matchMedia('(prefers-reduced-motion: reduce)').matches;
nodeSel.call(d3.drag()
  .on('start', (e, n) => { if (!e.active && !reduce) sim.alphaTarget(0.15).restart(); n.fx = n.x; n.fy = n.y; })
  .on('drag', (e, n) => { n.fx = e.x; n.fy = e.y; if (reduce) { n.x = e.x; n.y = e.y; draw(); } })
  .on('end', (e, n) => { if (!e.active) sim.alphaTarget(0); n.fx = null; n.fy = null; }));

// Selection, search and the side panel
let selected = null, query = '';
const panel = document.getElementById('panel');
const find = document.getElementById('find');

const names = n => [n.id, ...(n.aliases || []), ...(n.abbr || []), ...(n.stands || [])].map(x => x.toLowerCase());
function select(id) { selected = id; update(); }
function update() {
  const q = query.trim().toLowerCase();
  const hits = q ? new Set(nodes.filter(n => names(n).some(x => x.includes(q))).map(n => n.id)) : null;
  let near = null;
  if (selected) {
    near = new Set([selected]);
    links.forEach(l => { if (l.s === selected) near.add(l.t); if (l.t === selected) near.add(l.s); });
  }
  const touches = l => l.s === selected || l.t === selected;
  nodeSel.classed('sel', n => n.id === selected)
    .classed('dim', n => near ? !near.has(n.id) : !!hits && !hits.has(n.id));
  gapSel.classed('dim', l => selected ? !touches(l) : !!hits && !(hits.has(l.s) && hits.has(l.t)));
  linkSel.classed('hot', l => !!selected && touches(l))
    .classed('dim', l => selected ? !touches(l) : !!hits && !(hits.has(l.s) && hits.has(l.t)));
  lblSel.style('display', l => selected && touches(l) ? null : 'none');
  renderPanel();
}

function el(tag, cls, text) { const e = document.createElement(tag); if (cls) e.className = cls; if (text != null) e.textContent = text; return e; }
function termButton(id) {
  const b = el('button', 'term-link', id);
  b.type = 'button';
  b.addEventListener('click', () => select(id));
  return b;
}

function renderPanel() {
  panel.replaceChildren();
  if (!selected) {
    panel.append(el('div', 'eyebrow', 'How to read it'), el('h3', null, 'Select a term'),
      el('p', null, 'Click or tap a term to highlight the terms its definition uses and the terms whose definitions use it. Drag terms to rearrange them; scroll or pinch to zoom.'));
    const top = [...nodes].sort((a, b) => indeg.get(b.id) - indeg.get(a.id)).slice(0, 5);
    const p = el('p', null, 'Most referenced: ');
    top.forEach((n, i) => p.append(termButton(n.id), ` (${indeg.get(n.id)})`, i < top.length - 1 ? ', ' : '.'));
    panel.append(p, el('p', 'rel', 'Dashed terms appear inside definitions but have no note of their own yet.'));
    return;
  }
  const n = byId.get(selected);
  const chip = el('span', 'chip', DATA.cats[n.cat]);
  chip.style.setProperty('--c', `var(--c-${n.cat})`);
  panel.append(el('div', 'eyebrow', n.missing ? 'No note yet' : 'Definition'), el('h3', null, n.id), chip);
  if (n.missing) panel.append(el('p', 'rel', 'This term is used in definitions but has no note of its own.'));
  else panel.append(el('p', null, `${n.id}${n.qual ? ` (${n.qual})` : ''} is ${n.body}.`));
  if (n.stands && n.stands.length) panel.append(el('p', 'rel', 'Stands for: ' + n.stands.join('; ')));
  if (n.abbr && n.abbr.length) panel.append(el('p', 'rel', 'Abbreviation: ' + n.abbr.join(', ')));
  if (n.aliases && n.aliases.length) panel.append(el('p', 'rel', 'Also called: ' + n.aliases.join(', ')));
  const outs = outOf(n.id), ins = into(n.id);
  if (outs.length) {
    const ul = el('ul');
    outs.forEach(l => { const li = el('li'); li.append(el('span', 'rel', phraseOut(l) + ' '), termButton(l.t)); ul.append(li); });
    panel.append(el('div', 'eyebrow', 'Its definition uses'), ul);
  }
  if (ins.length) {
    const ul = el('ul');
    ins.forEach(l => { const li = el('li'); li.append(termButton(l.s), el('span', 'rel', ' ' + phraseIn(l))); ul.append(li); });
    panel.append(el('div', 'eyebrow', 'Used by'), ul);
  }
  if (!outs.length && !ins.length) panel.append(el('p', 'rel', 'No links to other terms.'));
}

find.addEventListener('input', () => { query = find.value; selected = null; update(); });
find.addEventListener('keydown', e => {
  if (e.key !== 'Enter') return;
  const q = find.value.trim().toLowerCase();
  if (!q) return;
  const hit = nodes.find(n => names(n).includes(q)) || nodes.find(n => names(n).some(x => x.includes(q)));
  if (hit) select(hit.id);
});
document.getElementById('reset').addEventListener('click', () => { find.value = ''; query = ''; select(null); fit(); });

document.getElementById('copy').addEventListener('click', async () => {
  const pre = document.getElementById('mmd'), out = document.getElementById('copied');
  try { await navigator.clipboard.writeText(pre.textContent); out.textContent = 'Copied'; }
  catch { getSelection().selectAllChildren(pre); out.textContent = 'Selected. Press Ctrl+C to copy'; }
});

function start() {
  measure();
  sim.tick(400);
  draw(); fit(); update();
}
(document.fonts && document.fonts.ready ? Promise.race([document.fonts.ready, new Promise(r => setTimeout(r, 1500))]) : Promise.resolve()).then(start);
</script>
