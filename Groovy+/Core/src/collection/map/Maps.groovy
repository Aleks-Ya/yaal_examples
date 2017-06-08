package collection.map

class Maps {
    static void main(args) {
        def map = [name:"Gromit", likes:"cheese", id:1234]
        assert map.get("name") == "Gromit"
        assert map.get("id") == 1234
        assert map["name"] == "Gromit"
        assert map['id'] == 1234
        assert map instanceof java.util.Map

        def emptyMap = [:]
        assert emptyMap.size() == 0
        emptyMap.put("foo", 5)
        assert emptyMap.size() == 1
        assert emptyMap.get("foo") == 5
    }
}