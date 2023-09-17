package jlink.app;

import jlink.utils.CaseUtils;

import java.util.logging.Logger;

public class SearchApp {
    public static void main(String[] args) {
        var log = Logger.getLogger(SearchApp.class.getSimpleName());
        var text = args[0];
        var substring = args[1];
        var lowercaseText = CaseUtils.toLowerCase(text);
        var lowercaseSubstring = CaseUtils.toLowerCase(substring);
        if (lowercaseText.contains(lowercaseSubstring)) {
            log.info("====== Text contains substring ======");
        } else {
            log.info("====== Text does NOT contain substring ======");
        }
    }
}
