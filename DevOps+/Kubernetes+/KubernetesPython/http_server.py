import http.server
import socketserver
import sys

PORT = 8000

Handler = http.server.SimpleHTTPRequestHandler

with socketserver.TCPServer(("", PORT), Handler) as httpd:
    print("serving at port", PORT)
    sys.stdout.flush()
    httpd.serve_forever()
