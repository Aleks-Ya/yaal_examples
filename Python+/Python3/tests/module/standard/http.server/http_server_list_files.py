# Run a simple HTTP server: shows the file list for the current dir
# Open http://localhost:8000 in browser
from http.server import SimpleHTTPRequestHandler, HTTPServer
from socketserver import BaseRequestHandler
from typing import Any, Callable

port: int = 8000
server_address: tuple[str, int] = ('', port)
handler: Callable[[Any, Any, HTTPServer], BaseRequestHandler] = SimpleHTTPRequestHandler
httpd: HTTPServer = HTTPServer(server_address, handler)
try:
    print(f"Serving HTTP on http://localhost:{port}")
    httpd.serve_forever()
except KeyboardInterrupt:
    print("\nKeyboard interrupt received, shutting down the server.")
finally:
    httpd.server_close()
