package cflat.ast;

import java.util.*;
import cflat.entity.DefinedVariable;
import cflat.entity.DefinedFunction;
import cflat.entity.Constant;
import cflat.entity.UndefinedVariable;
import cflat.entity.UndefinedFunction;
import cflat.ast.StructNode;
import cflat.ast.UnionNode;
import cflat.ast.TypedefNode;

public class Declarations {
    Set<DefinedVariable> defvars = new LinkedHashSet<DefinedVariable>();
    Set<UndefinedVariable> vardecls = new LinkedHashSet<UndefinedVariable>();
    Set<DefinedFunction> defuns = new LinkedHashSet<DefinedFunction>();
    Set<UndefinedFunction> funcdecls = new LinkedHashSet<UndefinedFunction>();
    Set<Constant> constants = new LinkedHashSet<Constant>();
    Set<StructNode> defstructs = new LinkedHashSet<StructNode>();
    Set<UnionNode> defunions = new LinkedHashSet<UnionNode>();
    Set<TypedefNode> typedefs = new LinkedHashSet<TypedefNode>();

    // TODO: test
    public void add(Declarations decls) {
	defvars.addAll(decls.defvars);
	vardecls.addAll(decls.vardecls);
	//defuns.addAll(decls.defuns);
	funcdecls.addAll(decls.funcdecls);
	constants.addAll(decls.constants);
	defstructs.addAll(decls.defstructs);
	defunions.addAll(decls.defunions);
	typedefs.addAll(decls.typedefs);
    }

    public void addDefvars(List<DefinedVariable> vars) {
	defvars.addAll(vars);
    }
    public List<DefinedVariable> defvars() {
	return new ArrayList<DefinedVariable>(defvars);
    }
    public void addVardecl(UndefinedVariable var) {
	vardecls.add(var);
    }
    public void addDefun(DefinedFunction func) {
	defuns.add(func);
    }
    public List<DefinedFunction> defuns() {
	return new ArrayList<DefinedFunction>(defuns);
    }
    public void	addConstant(Constant c) {
	constants.add(c);
    }
    public void addDefstruct(StructNode n) {
	defstructs.add(n);
    }
    public void addDefunion(UnionNode n) {
	defunions.add(n);
    }
    public void addFuncdecl(UndefinedFunction func) {
	funcdecls.add(func);
    }
    public void addTypedef(TypedefNode n) {
	typedefs.add(n);
    }
    public List<TypedefNode> typedefs() {
	return new ArrayList<TypedefNode>(typedefs);
    }
}