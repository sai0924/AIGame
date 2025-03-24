package api;

import game.Board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RuleSet<T extends Board> implements Iterable<Rule<T>>{

    private final List<Rule<T>> ruleList = new ArrayList<>();

    public void add(Rule<T> rule) {
        ruleList.add(rule);
    }

    @Override
    public Iterator<Rule<T>> iterator() {
        return ruleList.iterator();
    }
}
