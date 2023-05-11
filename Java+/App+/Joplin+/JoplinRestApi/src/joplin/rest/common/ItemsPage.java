package joplin.rest.common;

import java.util.List;

record ItemsPage(List<Item> items, Boolean has_more) {
}
