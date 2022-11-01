import java.util.Comparator;

/**
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 *
 *     Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 */
public class    PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
        return new PrefixComparator(prefix);
    }


    @Override
    /**
     * Use at most myPrefixSize characters from each of v and w
     * to return a value comparing v and w by words. Comparisons
     * should be made based on the first myPrefixSize chars in v and w.
     * @return < 0 if v < w, == 0 if v == w, and > 0 if v > w
     */
    public int compare(Term v, Term w) {
        // change this to use myPrefixSize as specified,
        // replacing line below with code
        String v_Word = v.getWord();
        String w_Word = w.getWord();
        for (int i = 1; i <= myPrefixSize; i++) {
            if (i > v_Word.length() || i > w_Word.length()) {
                if (i > v_Word.length() && i > w_Word.length()) {
                    return 0;
                }
                if (i > v_Word.length()) {
                    return -1;
                }
                return 1;
            }
            if (v_Word.charAt(i - 1) != w_Word.charAt(i - 1)) {
                if (v_Word.charAt(i - 1) < w_Word.charAt(i - 1)) {
                    return -1;
                }
                return 1;
            }
        }
        return 0;
    }
}
