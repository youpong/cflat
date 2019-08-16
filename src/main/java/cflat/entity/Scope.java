package cflat.entity;

import java.util.*;
import cflat.exception.SemanticException;

// TODO: implement
/**
 * スコープを表現する抽象クラス
 */
abstract public class Scope {
    protected List<LocalScope> children;

    public Scope() {
	children = new ArrayList<LocalScope>();
    }

    protected void addChild(LocalScope s) {
	children.add(s);
    }

    abstract public Entity get(String name) throws SemanticException;
}
