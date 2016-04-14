package language;

import com.intellij.lang.Language;

/**
 * Author: Ligia Centea
 * Date: 4/14/2016.
 */
public class SlangLanguage extends Language {

    public static final SlangLanguage INSTANCE = new SlangLanguage();

    protected SlangLanguage() {
        super("slang");
    }
}
