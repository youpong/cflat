package cflat.ast;

import java.util.*;
import cflat.entity.DefinedVariable;
import cflat.entity.DefinedFunction;
import cflat.entity.Constant;
import cflat.ast.StructNode;
import cflat.ast.UnionNode;
import cflat.ast.TypedefNode;

public class Declarations {
    Set<DefinedVariable> defvars = new LinkedHashSet<DefinedVariable>();
    Set<DefinedFunction> defuns = new LinkedHashSet<DefinedFunction>();
    Set<Constant> constants = new LinkedHashSet<Constant>();
    Set<StructNode> defstructs = new LinkedHashSet<StructNode>();
    Set<UnionNode> defunions = new LinkedHashSet<UnionNode>();
    Set<TypedefNode> typedefs = new LinkedHashSet<TypedefNode>();

    public void add(Declarations decls) {
	defvars.addAll(decls.defvars);
	defuns.addAll(decls.defuns);
	constants.addAll(decls.constants);
	defstructs.addAll(decls.defstructs);
	defunions.addAll(decls.defunions);
	typedefs.addAll(decls.typedefs);
    }
    //   public void addDefvars(DefinedVariable var)
    public void addDefvars(List<DefinedVariable> vars) {
	defvars.addAll(vars);
    }
    public void addDefun(DefinedFunction func) {
	defuns.add(func);
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
    public void addTypedef(TypedefNode n) {
	typedefs.add(n);
    }
}
