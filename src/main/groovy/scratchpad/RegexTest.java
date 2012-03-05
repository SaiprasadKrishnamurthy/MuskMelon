package scratchpad;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA. User: Sai Date: 28/01/12 Time: 22:36 To change this
 * template use File | Settings | File Templates.
 */
public class RegexTest {

    public static void main(String[] args) {
	String s = "QAT-1802 Decodes on PDF and XLS Exports - for Referral Search";
	Pattern pattern = Pattern.compile("(?i)\\s*(QAT\\s*-\\s*[0-9]*).*");
	
	Matcher matcher = pattern.matcher(s);
	if (matcher.matches()) {
	    System.out.println(matcher.group(1));
	}
    }
}
