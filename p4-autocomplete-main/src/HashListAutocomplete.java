import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashListAutocomplete implements Autocompletor {
    
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;


    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}
		
		initialize(terms,weights);
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        if (terms.length != weights.length) {
            throw new IllegalArgumentException();
        }
        myMap = new HashMap<>();
        for (int i = 0; i < terms.length; i++) {
			String term = terms[i];
			for (int j = 0; j <= Math.min(MAX_PREFIX, term.length()); j += 1) {
                String toAdd = term.substring(0, j);
				if (!myMap.containsKey(toAdd)) {
                    mySize += j * BYTES_PER_CHAR;
					myMap.put(toAdd, new ArrayList<Term>());
                }
				myMap.get(toAdd).add(new Term(term, weights[i]));
                mySize += (term.length() * BYTES_PER_CHAR) + BYTES_PER_DOUBLE;
			}
        }

        for (String prefix : myMap.keySet()) {
            List<Term> TermList = myMap.get(prefix);
            Collections.sort(TermList, Comparator.comparing(Term::getWeight).reversed());
        }
    }

    @Override
    public int sizeInBytes() {
		return mySize;
    }


    @Override
    public List<Term> topMatches(String prefix, int k) {
        if (k < 0) {
            throw new NullPointerException();
        }
        if (prefix.length() > MAX_PREFIX) {
            prefix = prefix.substring(0, MAX_PREFIX-1);
        }
        if (myMap.containsKey(prefix) && k!=0) {
            List<Term> all = myMap.get(prefix);
            List<Term> list = all.subList(0, Math.min(all.size(), k));
            return list;
        }
        return new ArrayList<Term>();
    }
    
}

