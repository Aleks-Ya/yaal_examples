Requirements:
1. Java 8
2. Maven 3
3. Free 8080 port

Run in Demo mode (in-memory DB, contains some data):
mvn spring-boot:run
Verify:
See all authors (to see author's id for serarch): http://localhost:8080/author
Find books by author id: http://localhost:8080/book/3
Search in book’s name by substring: http://localhost:8080/book/find?query=Great