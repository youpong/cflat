package cflat.entity;

import java.util.*;

// TODO: implement
public class ConstantTable implements Iterable<ConstantEntry> {
    protected Map<String, ConstantEntry> table;

    public ConstantTable() {
	table = new LinkedHashMap<String, ConstantEntry>();
    }
    public Iterator<ConstantEntry> iterator() {
	return table.values().iterator();
    }
    public ConstantEntry intern(String s) {
	ConstantEntry ent = table.get(s);
	if (ent == null) {
	    ent = new ConstantEntry(s);
	    table.put(s, ent);
	}
	return ent;
    }
}
