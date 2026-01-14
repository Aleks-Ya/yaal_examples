# Gremlin in Docker

1. Run server: `docker run --rm -p 8182:8182 tinkerpop/gremlin-server`
2. Run console: `docker run --rm -it --network host tinkerpop/gremlin-console`
3. In console (after `gremlin>`):
	1. Connect: `:remote connect tinkerpop.server conf/remote.yaml`
	2. Test: 
		1. Create a vertex: `:> g.addV('person').property('name', 'Alice').property('age', 30)`
		2. Vertex count: `:> g.V().count()`
