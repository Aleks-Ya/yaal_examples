package ru.yaal.merch.bookshelf.domain;

import java.util.List;

public class Book {
    private Long id;
    private String name;
    private String abstractPart;
    private List<Author> authors;

    public Book() {
    }

    public Book(Long id, String name, String abstractPart, List<Author> authors) {
        this.id = id;
        this.name = name;
        this.abstractPart = abstractPart;
        this.authors = authors;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbstractPart() {
        return abstractPart;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbstractPart(String abstractPart) {
        this.abstractPart = abstractPart;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!id.equals(book.id)) return false;
        if (!name.equals(book.name)) return false;
        if (abstractPart != null ? !abstractPart.equals(book.abstractPart) : book.abstractPart != null) return false;
        return authors != null ? authors.equals(book.authors) : book.authors == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (abstractPart != null ? abstractPart.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", abstractPart='" + abstractPart + '\'' +
                ", authors=" + authors +
                '}';
    }
}
