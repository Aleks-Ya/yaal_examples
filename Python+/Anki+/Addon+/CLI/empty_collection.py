from anki.collection import Collection

col = Collection("/tmp/collection.anki2")
print(col.sched.deck_due_tree())
col.close()
