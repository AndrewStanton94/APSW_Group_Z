/*
 * StringFormatter.java
 *
 * Created on 17 March 2005, 15:35
 */
package jim.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Methods for doing {parameter} substitution in strings
 * @author briggsj
 */
public class StringFormatter {

    private StringFormatter() {
    }

    static private Pattern getPattern(String inner) {
        //embed the pattern inner inside braces (preceded by anything other than a backslash
        return Pattern.compile("([^\\\\]?)\\{(" + inner + ")\\}", Pattern.CASE_INSENSITIVE);
    }

    /**
     * Find the {parameters} within a string
     * @param text the string to be searched
     * @return a list of Strings containing the names (with braces removed) of parameters in <CODE>text</CODE>
     */
    static public List findComponents(String text) {
        Pattern p = getPattern("\\w+"); //A non-zero length sequence of word characters

        Matcher m = p.matcher(text);
        List<String> fields = new ArrayList<String>();
        while (m.find()) {
            String found = m.group(2);
            fields.add(found);
        }
        return fields;
    }

    /**
     * Apply parameter substitution to the specified string using the specified key->value
     * mappings
     * @param in String to apply substitutions to
     * @param args Map containing key->value mappings
     * @return {@link String} with each occurrence of {key} replaced by value
     */
    static public String apply(String in, Map args) {
        if (args == null || in == null) {
            return in;
        }
        String last = in;
        boolean working = true;
        while (working) {
            List fields = findComponents(in);
            if (fields.size() > 0) {
                last = in;
                Iterator it = fields.iterator();
                //        Iterator it = args.keySet().iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = args.get(key).toString();
                    if (value == null) {
                        value = "[" + key + " not found]";
                    }
                    //            in = in.replaceAll("\\{"+key+"\\}", value);
                    Pattern p = getPattern(key);
                    Matcher m = p.matcher(in);
                    in = m.replaceAll("$1" + value);
                }
                if (last.equalsIgnoreCase(in)) {
                    working = false;
                }
            } else {
                working = false;
            }
        }
        return in;
    }

    static public String truncate(String s, int maxLength) {
        if (s.length() > maxLength) {
            return s.substring(0, maxLength - 1);
        } else {
            return s;
        }
    }

    static public String trimAndTruncate(String s, int maxLength) {
        return truncate(s.trim(), maxLength);
    }

    static public String join(Collection list, String separator) {
        Iterator it = list.iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            Object elem = it.next();
            sb.append(elem.toString());
            if (it.hasNext()) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    static public String join(Object[] list, String separator) {
        return join(Arrays.asList(list), separator);
    }

    static public String join(int[] list, String separator) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (int n = 0; n < list.length; n++) {
            if (first) {
                first = false;
            } else {
                sb.append(separator);
            }
            sb.append(list[n]);
        }
        return sb.toString();
    }

    static public List<String> split(String str, String delim) {
        StringTokenizer st = new StringTokenizer(str, delim);
        List<String> result = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            result.add(st.nextToken());
        }
        return result;
    }
}
