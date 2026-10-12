"""Build definitions.md, diagram.mmd and map.html from Anki notes.

Usage (paths are relative to the project root, wherever the script is run from):
    python3 build.py plan  "<user query>"
        Prints the two Anki queries to search with (definitions and synonyms; abbreviations) and the slug.
    python3 build.py build "<user query>" NOTES_JSON [NOTES_JSON ...]
        NOTES_JSON files are anki-mcp `notesInfo` results ({"notes": [...]}) for both queries, one per batch of up to 100 IDs.
        Writes out/<slug>/definitions.md, diagram.mmd and map.html.

out/<slug>/relations.json holds the judgement calls made per query and is kept between runs:
    {
      "title": "Cryptography definitions",
      "proper_names": ["Java Cryptography Architecture"],
      "categories": [{"name": "Keys", "terms": ["Cryptographic key", ...]}, ...],
      "relations": [["Symmetric key", "inherit", "Cryptographic key", ""], ...]
    }
Relation kinds, read as "<source> ... <target>": inherit (is a kind of), compose (is part of),
aggregate (contains), assoc and depend (use the label). Term names match case-insensitively.

Notes tagged `synonym` (cloze answers are the names, e.g. {{c1::A}} is a synonym of {{c2::B}}) need no entry in
relations.json: their names become aliases of the defined term, and two defined synonyms get a `synonym` link.
Notes tagged `abbreviation` ("Decipher <abbr>CA</abbr>" → "Certification Authority", found by the separate abbreviation
query) work like a synonym pair, but are shown as "abbreviated CA" / "stands for …".

Exit codes: 0 built; 2 relations file incomplete (prints what to add; on the first run creates out/<slug>/ with a starter
relations.json, otherwise writes nothing);
3 no usable definition notes.
"""
import html, json, os, re, shlex, sys

NOTE_TYPE = 'Programming-cloze'
KINDS = ('inherit', 'compose', 'aggregate', 'assoc', 'depend')
FORMAT_EXAMPLE = {  # printed with exit code 2 so the format needs no other lookup
    'title': 'Cryptography definitions',
    'proper_names': ['Java Cryptography Architecture'],
    'categories': [{'name': 'Keys', 'terms': ['Cryptographic key', 'Symmetric key']}],
    'relations': [['Symmetric key', 'inherit', 'Cryptographic key', ''], ['Keystore', 'aggregate', 'Cryptographic key', 'stores']],
    'kinds': {'inherit': 'source is a kind of target', 'compose': 'source is part of target',
              'aggregate': 'source contains target', 'depend': 'source uses target (label it)', 'assoc': 'other (label it)'},
}
MAX_CATEGORIES = 8  # colors defined in map.html.tpl
HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.abspath(os.path.join(HERE, '..', '..', '..', '..'))  # <root>/.claude/skills/<skill>/scripts


def full_query(query):
    return f'({query}) (tag:definition OR tag:synonym) note:*cloze*'


def abbreviation_query(query):
    return f'({query}) tag:abbreviation'


def slug(query):
    """Single tag/deck → its name; otherwise the whole query. Negation becomes "not-"."""
    try:
        tokens = shlex.split(query)
    except ValueError:
        tokens = query.split()
    single = len(tokens) == 1 and re.match(r'(?i)(tag|deck):(.+)', tokens[0])
    text = single.group(2) if single else ' '.join(('not-' + t[1:]) if t.startswith('-') else t for t in tokens)
    return re.sub(r'[^a-z0-9]+', '-', text.lower()).strip('-') or 'all'


def clean(s):
    s = html.unescape(s).replace('\xa0', ' ')
    return re.sub(r'\s+', ' ', s).strip()


def plain(s):
    return clean(re.sub(r'<[^>]+>', '', s))


def dedupe(names):
    """Drop empty and case-insensitive repeats, keeping the first spelling and the order."""
    seen = {}
    for x in names:
        if x:
            seen.setdefault(x.lower(), x)
    return list(seen.values())


def singular(word):
    if word.endswith('ies'):
        return word[:-3] + 'y'
    if word.endswith('s') and not word.endswith('ss'):
        return word[:-1]
    return word


def main():
    mode, query = sys.argv[1], sys.argv[2]
    name = slug(query)
    out_dir = os.path.join(ROOT, 'out', name)
    rel_path = os.path.join(out_dir, 'relations.json')
    if mode == 'plan':
        print(json.dumps({'full_query': full_query(query), 'abbreviation_query': abbreviation_query(query), 'slug': name,
                          'relations_file': os.path.relpath(rel_path, ROOT), 'out_dir': os.path.relpath(out_dir, ROOT)}, indent=2))
        return 0

    rels = json.load(open(rel_path)) if os.path.exists(rel_path) else None
    proper = {p.lower() for p in (rels or {}).get('proper_names', [])}

    def sentence_case(term):
        """Capital first letter, the rest lowercase; acronyms (X.509, PKCS, ASN.1) and proper names keep their case."""
        if term.lower() in proper:
            return term
        def keep(w):
            return any(c.isupper() for p in w.split('-') for c in p[1:]) or any(c.isdigit() or c in '.#' for c in w)
        words = [w if keep(w) else w.lower() for w in term.split(' ')]
        return words[0][:1].upper() + words[0][1:] + ''.join(' ' + w for w in words[1:])

    # Parse notes: "{{c1::<dfn>Term</dfn> (Qualifier)}} is {{c2::definition with <dfn>links</dfn>}}."
    notes = {}
    for path in sys.argv[3:]:
        for n in json.load(open(path))['notes']:
            notes[n['noteId']] = n
    entries, skipped, groups, abbr_notes = [], [], [], []
    for nid_, n in notes.items():
        if 'abbreviation' in n.get('tags', []) and n.get('modelName') != NOTE_TYPE:
            # "Decipher <abbr>SAN</abbr> (2)" → "Subject Alternative Name", or a list of meanings / "DDL = Data ..." items
            f = n.get('fields', {})
            if 'Question' not in f or 'Answer' not in f:
                skipped.append({'noteId': nid_, 'reason': f"abbreviation note type {n.get('modelName')}"})
                continue
            q = re.match(r'(?i)de\w*\s+(?:acronym\s+)?(.+?)(?:\s*\(\d+\))?\??$', plain(f['Question']['value']))
            if not q:
                skipped.append({'noteId': nid_, 'reason': 'abbreviation note without "Decipher X" question'})
                continue
            abbrs = [a.strip('"\'“” ') for a in re.split(r',\s*|\s+and\s+', q.group(1))]
            ans = f['Answer']['value']
            items = re.findall(r'<li>(.*?)</li>', ans, re.S) or [next((x for x in re.split(r'<br\s*/?>|</?div>', ans) if plain(x)), '')]
            meanings = {a: [] for a in abbrs}
            for it in map(plain, items):
                pair = re.match(r'(\S+)\s*=\s*(.+)$', it)
                a = pair.group(1) if pair and pair.group(1) in meanings else abbrs[0]
                it = pair.group(2) if pair else re.sub(r'^[A-Z][\w-]*(?: [\w-]+){0,2}:\s+', '', it)
                own = re.match(re.escape(a) + r'\s*\((.+)\)$', it)  # "JSON (JavaScript Object Notation)"
                it = own.group(1) if own else it
                it = re.sub(r'\s*\([^)]*\)$', '', it.rstrip('. ')).strip('"\'“”. ')
                if it and (pair or len(abbrs) == 1):
                    meanings[a].append(sentence_case(it))
            found = [(a, dedupe(x)) for a, x in meanings.items() if x]
            if found:
                abbr_notes += found
            else:
                skipped.append({'noteId': nid_, 'reason': 'abbreviation note without an expansion'})
            continue
        if n.get('modelName') != NOTE_TYPE:
            skipped.append({'noteId': nid_, 'reason': f"note type {n.get('modelName')}"})
            continue
        text = n['fields'].get('Text', {}).get('value', '')
        clozes = re.findall(r'\{\{c\d+::(.*?)\}\}', text)
        m = re.search(r'<(dfn|abbr|code)>(.*?)</\1>', clozes[0]) if clozes else None
        if (len(clozes) < 2 or not m) and 'synonym' in n.get('tags', []):
            # "{{c1::A}} is a synonym of {{c2::B}}": every cloze answer is a name; "(SSE)" is a name too, "(heap)" is not
            names = []
            for raw in re.findall(r'(<code>\s*)?\{\{c\d+::(.*?)\}\}', text):
                is_code, name = bool(raw[0]) or '<code>' in raw[1], plain(raw[1]).strip('"\'“” ')
                bracket = re.search(r'\s*\(([^)]+)\)$', name)
                if bracket and not is_code:
                    name = name[:bracket.start()]
                    if any(c.isupper() for c in bracket.group(1)[1:]):
                        names.append(bracket.group(1))
                names.append(name if is_code else sentence_case(name))
            names = dedupe(names)
            if len(names) >= 2:
                groups.append(names)
            else:
                skipped.append({'noteId': nid_, 'reason': 'synonym note with fewer than 2 names'})
            continue
        if len(clozes) < 2 or not m:
            skipped.append({'noteId': nid_, 'reason': 'neither "{{c1::<dfn>term</dfn>}} is {{c2::definition}}" '
                                                      'nor a note tagged synonym'})
            continue
        head, body = clozes[0], clozes[1]
        term = plain(m.group(2))
        if m.group(1) != 'code':  # code names such as keytool keep their case
            term = sentence_case(term)
        qual = re.search(r'\(([^)]+)\)', plain(head))
        links = [(t, plain(x)) for t, x in re.findall(r'<(dfn|abbr)>(.*?)</\1>', body) if plain(x)]
        entries.append(dict(term=term, qual=qual.group(1) if qual else None, body=plain(body), links=links))
    if not entries:
        print(json.dumps({'error': 'no usable definition notes', 'full_query': full_query(query), 'skipped': skipped}, indent=2))
        return 3
    entries.sort(key=lambda e: e['term'].lower())

    # Resolve links by name, "qualifier name", plural, or (for <abbr>) the term's initials such as JCA
    index, initials = {}, {}
    for e in entries:
        index[e['term'].lower()] = e['term']
        if e['qual']:
            index[f"{e['qual']} {e['term']}".lower()] = e['term']
        words = e['term'].split()
        if len(words) > 1:
            initials[''.join(w[0] for w in words).upper()] = e['term']

    # Abbreviations are synonym pairs; of several meanings keep those that name a known term or link
    known = set(index) | {x.lower() for g in groups for x in g} | {x.lower() for e in entries for _, x in e['links']}
    known |= {singular(k) for k in known}
    syn_names = {x.lower() for g in groups for x in g}
    expands, ambiguous = {}, []
    for a, exps in abbr_notes:
        if len(exps) > 1:
            hits = [x for x in exps if x.lower() in known or singular(x.lower()) in known]
            if not hits:
                ambiguous.append([a] + exps)
                continue
            exps = hits
        expands.setdefault(a.lower(), set()).update(x.lower() for x in exps)
        groups += [[a, x] for x in exps]

    # Synonyms: merge groups that share a name; names become aliases of the defined terms in the group
    merged = []
    for g in groups:
        keys = {x.lower() for x in g}
        overlap = [m for m in merged if keys & {x.lower() for x in m}]
        new = [x for m in overlap for x in m] + g
        merged = [m for m in merged if m not in overlap] + [dedupe(new)]
    by_term = {e['term']: e for e in entries}
    for e in entries:
        e['aliases'] = []
    syn_edges, loose, loose_index = [], [], {}  # loose: groups with no defined term
    for g in merged:
        defined = []
        for x in g:
            t = index.get(x.lower())
            if t and t not in defined:
                defined.append(t)
        if not defined:
            loose.append(g)
            for x in g:
                loose_index[x.lower()] = len(loose) - 1
            continue
        for t in defined:
            by_term[t]['aliases'] += [x for x in g if x.lower() != t.lower() and x not in by_term[t]['aliases']
                                      and index.get(x.lower()) != t]
        for x in g:
            index.setdefault(x.lower(), defined[0])
            if any(c.isupper() for c in x[1:]) and ' ' not in x:
                initials.setdefault(x, defined[0])
        syn_edges += [(a, b) for i, a in enumerate(defined) for b in defined[i + 1:]]

    missing, edges, missing_aliases = {}, [], {}
    for e in entries:
        for tag, link in e['links']:
            k = link.lower()
            target = index.get(k) or index.get(singular(k))
            if tag == 'abbr':
                target = target or initials.get(link)
                if not target:
                    continue  # an <abbr> without a note of its own is not a link
            if not target:
                gi = loose_index.get(k, loose_index.get(singular(k)))
                label = sentence_case(singular(link))
                if gi is not None:  # a term without a note that has synonyms: one node, the rest are aliases
                    label = missing_aliases.setdefault(gi, label)
                target = missing.setdefault(label.lower(), label)
            if target != e['term'] and (e['term'], target) not in edges:
                edges.append((e['term'], target))
    for gi, label in missing_aliases.items():
        missing_aliases[gi] = (label, [x for x in loose[gi] if x.lower() != label.lower()])
    aliases_of = {e['term']: e['aliases'] for e in entries} | {label: al for label, al in missing_aliases.values()}
    unattached = [g for i, g in enumerate(loose) if i not in missing_aliases]
    unattached_abbrs = [g for g in unattached if not syn_names & {x.lower() for x in g}] + ambiguous
    unattached = [g for g in unattached if syn_names & {x.lower() for x in g}]
    missing_names = sorted(missing.values(), key=str.lower)
    canon = {e['term'].lower(): e['term'] for e in entries} | missing
    syn_pairs = {frozenset((a.lower(), b.lower())) for a, b in syn_edges}
    edges = [(a, b) for a, b in edges if frozenset((a.lower(), b.lower())) not in syn_pairs]
    canon = {x.lower(): t for t, al in aliases_of.items() for x in al if x.lower() not in canon} | canon

    # Check the relations file covers every link and term, and nothing stale
    relations = [(canon.get(a.lower(), a), k, canon.get(b.lower(), b), lbl) for a, k, b, lbl in (rels or {}).get('relations', [])]
    defs = {e['term']: f"{e['term']}{' (' + e['qual'] + ')' if e['qual'] else ''} is {e['body']}." for e in entries}
    pair = lambda a, b: frozenset((a.lower(), b.lower()))
    covered = {pair(a, b) for a, _, b, _ in relations}
    linked = {pair(a, b) for a, b in edges}
    cat_of = {t.lower(): i for i, c in enumerate((rels or {}).get('categories', [])) for t in c['terms']}
    todo = {
        'missing_title': not (rels or {}).get('title'),
        'unclassified_links': [{'source': a, 'target': b, 'definition': defs[a]} for a, b in edges if pair(a, b) not in covered],
        'uncategorized_terms': [t for t in dedupe(canon.values()) if t.lower() not in cat_of],
        'stale_relations': [list(r) for r in relations if pair(r[0], r[2]) not in linked],
        'bad_kinds': [list(r) for r in relations if r[1] not in KINDS],
        'too_many_categories': len((rels or {}).get('categories', [])) > MAX_CATEGORIES and f'at most {MAX_CATEGORIES}',
    }
    todo = {k: v for k, v in todo.items() if v}
    if not rels or todo:
        if not rels:  # first run: create the slug folder with a starter file to fill in
            os.makedirs(out_dir, exist_ok=True)
            starter = {'title': '', 'proper_names': [], 'categories': [], 'relations': []}
            json.dump(starter, open(rel_path, 'w'), indent=2)
        print(json.dumps({'relations_file': os.path.relpath(rel_path, ROOT), 'format_example': FORMAT_EXAMPLE,
                          'skipped_notes': skipped, **todo},
                         indent=2, ensure_ascii=False))
        return 2

    # diagram.mmd: Mermaid class diagram in UML notation
    ids = {}
    def nid(t):
        return ids.setdefault(t, f'n{len(ids)}')

    arrows = {  # whole/parent goes on the left of the Mermaid arrow
        'inherit':   lambda a, b: f'{nid(b)} <|-- {nid(a)}',
        'compose':   lambda a, b: f'{nid(b)} *-- {nid(a)}',
        'aggregate': lambda a, b: f'{nid(a)} o-- {nid(b)}',
        'assoc':     lambda a, b: f'{nid(a)} --> {nid(b)}',
        'depend':    lambda a, b: f'{nid(a)} ..> {nid(b)}',
    }

    def mlabel(s):
        return s.replace('#', '#35;').replace('"', '#quot;')

    fq, aq = full_query(query), abbreviation_query(query)
    lines = ['---', 'config:', '  class:', '    hideEmptyMembersBox: true', '---', 'classDiagram', f'    %% {fq}', f'    %% {aq}']
    lines += [f'    class {nid(t)}["{mlabel(t)}"]' for t in [e['term'] for e in entries] + missing_names]
    lines += ['    ' + arrows[k](a, b) + (f' : {lbl}' if lbl else '') for a, k, b, lbl in relations]
    lines += [f'    {nid(a)} -- {nid(b)} : synonym' for a, b in syn_edges]
    lines += [f'    <<missing>> {nid(t)}' for t in missing_names]
    mermaid = '\n'.join(lines)

    # definitions.md
    def name_parts(t):
        """A term's other names: what it stands for, its abbreviations, and its other synonyms."""
        al = aliases_of.get(t, [])
        stands = [x for x in al if x.lower() in expands.get(t.lower(), ())]
        abbr = [x for x in al if x.lower() in expands and x not in stands]
        return stands, abbr, [x for x in al if x not in stands and x not in abbr]

    def also(t):
        stands, abbr, other = name_parts(t)
        parts = ([f"stands for {'; '.join(stands)}"] if stands else []) + ([f"abbreviated {', '.join(abbr)}"] if abbr else []) \
            + ([f"also called {', '.join(other)}"] if other else [])
        return f" ({'; '.join(parts)})" if parts else ''

    def abbr_line(g):
        short = [x for x in g if x.lower() in expands] or g[:1]
        return f"{' / '.join(short)}: {'; '.join(x for x in g if x not in short)}"

    md = [fq, aq, '', f"# {rels['title']}", '']
    md += [f"- **{e['term']}**{' (' + e['qual'] + ')' if e['qual'] else ''}{also(e['term'])} is {e['body']}." for e in entries]
    md += ['', '## Terms without a note', ''] + [f'- {t}{also(t)}' for t in missing_names]
    if unattached:
        md += ['', '## Synonyms without a definition', ''] + [f"- {' = '.join(g)}" for g in unattached]
    if unattached_abbrs:
        md += ['', '## Abbreviations without a definition', ''] + [f'- {abbr_line(g)}' for g in unattached_abbrs]
    md += ['']

    # map.html
    esc = html.escape
    def names(t):
        stands, abbr, other = name_parts(t)
        return dict(stands=stands, abbr=abbr, aliases=other)
    data = dict(
        query=fq,
        cats=[c['name'] for c in rels['categories']],
        nodes=[dict(id=e['term'], cat=cat_of[e['term'].lower()], missing=False, qual=e['qual'], body=e['body'],
                    **names(e['term'])) for e in entries]
              + [dict(id=t, cat=cat_of[t.lower()], missing=True, **names(t)) for t in missing_names],
        links=[dict(s=a, t=b, kind=k, label=lbl) for a, k, b, lbl in relations]
              + [dict(s=a, t=b, kind='synonym', label='synonym') for a, b in syn_edges],
    )
    page = open(os.path.join(HERE, 'map.html.tpl')).read()
    for k, v in {
        'TITLE': esc(rels['title']), 'QUERY': esc(fq), 'COUNT': str(len(entries)),
        'MISSING_COUNT': str(len(missing_names)), 'LINK_COUNT': str(len(relations)),
        'ITEMS': '\n'.join(f"<li><dfn>{esc(e['term'])}</dfn>{esc(' (' + e['qual'] + ')') if e['qual'] else ''}{esc(also(e['term']))} is {esc(e['body'])}.</li>" for e in entries),
        'MISSING': '\n'.join(f'<li>{esc(t + also(t))}</li>' for t in missing_names),
        'SYNONYMS': '\n'.join(f"<li>{esc(' = '.join(g))}</li>" for g in unattached),
        'SYNONYMS_HIDDEN': '' if unattached else ' hidden',
        'ABBREVIATIONS': '\n'.join(f'<li>{esc(abbr_line(g))}</li>' for g in unattached_abbrs),
        'ABBREVIATIONS_HIDDEN': '' if unattached_abbrs else ' hidden',
        'MERMAID': esc(mermaid),
        'DATA': json.dumps(data, ensure_ascii=False).replace('</', '<\\/'),
    }.items():
        page = page.replace('{{' + k + '}}', v)

    os.makedirs(out_dir, exist_ok=True)
    files = {'definitions.md': '\n'.join(md), 'diagram.mmd': mermaid + '\n', 'map.html': page}
    for fname, content in files.items():
        open(os.path.join(out_dir, fname), 'w').write(content)
    print(json.dumps({
        'definitions': len(entries), 'links': len(relations), 'missing': missing_names,
        'synonyms_attached': {t: al for t, al in aliases_of.items() if al}, 'synonym_links': [list(x) for x in syn_edges],
        'unattached_synonyms': unattached,
        'abbreviations_attached': {t: name_parts(t)[0] + name_parts(t)[1] for t in aliases_of if name_parts(t)[0] + name_parts(t)[1]},
        'unattached_abbreviations': [abbr_line(g) for g in unattached_abbrs], 'skipped_notes': skipped,
        'files': [os.path.relpath(os.path.join(out_dir, f), ROOT) for f in files],
    }, indent=2, ensure_ascii=False))
    return 0


if __name__ == '__main__':
    sys.exit(main())
