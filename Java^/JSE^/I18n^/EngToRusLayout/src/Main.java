public class Main {
    public static void main(String[] args) {
        final String eng = "qwerty";
        final String expRus = "йцукен";
        Character c = 'a';
        System.out.println(Character.codePointAt(eng, 0));
        System.out.println(Character.codePointAt(expRus, 0));
        char c2 = (char) 1081;
        System.out.println(c2);

        System.out.println(convert("qwerty"));
    }

    private static String convert(String eng) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < eng.length(); i++) {
            int engCP = Character.codePointAt(eng, i);
            int rusCP = engCP + 968;
            char[] rus = Character.toChars(rusCP);
            result.append(rus);
        }
        return result.toString();
    }
}
