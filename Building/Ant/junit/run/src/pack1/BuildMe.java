package pack1;

import org.apache.commons.lang3.RandomUtils;

public class BuildMe {
    public static void main(String[] args) {
        System.out.println(RandomUtils.nextInt(0, 1000));
    }

    public String getEmptyString() {
        return "";
    }
}
