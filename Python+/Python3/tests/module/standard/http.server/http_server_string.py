# Run a simple HTTP server: shows a given string
# Open http://localhost:8000 in browser
from http.server import HTTPServer, BaseHTTPRequestHandler
from socketserver import BaseRequestHandler
from typing import Any, Callable


class StrHandler(BaseHTTPRequestHandler):
    def do_GET(self) -> None:
        self.send_response(200)
        self.send_header('Content-type', 'text/plain')
        self.end_headers()
        self.wfile.write(b'Hello, World!')


port: int = 8000
server_address: tuple[str, int] = ('', port)
handler: Callable[[Any, Any, HTTPServer], BaseRequestHandler] = StrHandler
httpd: HTTPServer = HTTPServer(server_address, handler)
try:
    print(f"Serving HTTP on http://localhost:{port}")
    httpd.serve_forever()
except KeyboardInterrupt:
    print("\nKeyboard interrupt received, shutting down the server.")
finally:
    httpd.server_close()
