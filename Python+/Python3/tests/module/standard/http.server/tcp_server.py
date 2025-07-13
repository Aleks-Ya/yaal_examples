# Run a simple HTTP server
# Open http://localhost:8000 in browser
from http.server import SimpleHTTPRequestHandler
from socketserver import TCPServer, BaseRequestHandler
from typing import Callable, Any

port: int = 8000
server_address: tuple[str, int] = ('', port)
handler: Callable[[Any, Any, TCPServer], BaseRequestHandler] = SimpleHTTPRequestHandler
with TCPServer(server_address, handler) as httpd:
    try:
        print(f"Serving HTTP on http://localhost:{port}")
        httpd.serve_forever()
    except KeyboardInterrupt:
        print("\nKeyboard interrupt received, shutting down the server.")
    finally:
        httpd.server_close()
