# MITM Proxy

Site: https://www.mitmproxy.org
GitHub: https://github.com/mitmproxy/mitmproxy

Run:
1. Run: `docker run -it --rm --name mitm -p 2121:8080 mitmproxy/mitmproxy mitmproxy --listen-port 8080`
2. Test: `curl -x http://localhost:2121 -I http://example.com`