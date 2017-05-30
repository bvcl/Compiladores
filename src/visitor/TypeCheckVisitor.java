package visitor;

import symboltable.Method;
import symboltable.SymbolTable;
import symboltable.Class;
import ast.And;
import ast.ArrayAssign;
import ast.ArrayLength;
import ast.ArrayLookup;
import ast.Assign;
import ast.Block;
import ast.BooleanType;
import ast.Call;
import ast.ClassDeclExtends;
import ast.ClassDeclSimple;
import ast.False;
import ast.Formal;
import ast.Identifier;
import ast.IdentifierExp;
import ast.IdentifierType;
import ast.If;
import ast.IntArrayType;
import ast.IntegerLiteral;
import ast.IntegerType;
import ast.LessThan;
import ast.MainClass;
import ast.MethodDecl;
import ast.Minus;
import ast.NewArray;
import ast.NewObject;
import ast.Not;
import ast.Plus;
import ast.Print;
import ast.Program;
import ast.This;
import ast.Times;
import ast.True;
import ast.Type;
import ast.VarDecl;
import ast.While;

public class TypeCheckVisitor implements TypeVisitor {

	private SymbolTable symbolTable;
	private Class currClass;
	private Method currMethod;

	TypeCheckVisitor(SymbolTable st) {
		symbolTable = st;
	}

	// MainClass m;
	// ClassDeclList cl;
	public Type visit(Program n) {
		n.m.accept(this);
		for (int i = 0; i < n.cl.size(); i++) {
			n.cl.elementAt(i).accept(this);
		}
		return null;
	}

	// Identifier i1,i2;
	// Statement s;
	public Type visit(MainClass n) {
		n.i1.accept(this);
		n.i2.accept(this);
		n.s.accept(this);
		return null;
	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Type visit(ClassDeclSimple n) {
		n.i.accept(this);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this);
		}
		return null;
	}

	// Identifier i;
	// Identifier j;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Type visit(ClassDeclExtends n) {
		n.i.accept(this);
		n.j.accept(this);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this);
		}
		return null;
	}

	// Type t;
	// Identifier i;
	public Type visit(VarDecl n) {
		n.t.accept(this);
		n.i.accept(this);
		return null;
	}

	// Type t;
	// Identifier i;
	// FormalList fl;
	// VarDeclList vl;
	// StatementList sl;
	// Exp e;
	public Type visit(MethodDecl n) {
		currMethod = symbolTable.getMethod(n.i.s, currClass.getId());
		n.t.accept(this);
		n.i.accept(this);
		for (int i = 0; i < n.fl.size(); i++) {
			n.fl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		if(!(symbolTable.compareTypes(n.t, n.e.accept(this)))) System.out.println("Error: Method return is different from method type declaration");
		return null;
	}

	// Type t;
	// Identifier i;
	public Type visit(Formal n) {
		n.t.accept(this);
		n.i.accept(this);
		return null;
	}

	public Type visit(IntArrayType n) {
		return n;
	}

	public Type visit(BooleanType n) {
		return n;
	}

	public Type visit(IntegerType n) {
		return n;
	}

	// String s;
	public Type visit(IdentifierType n) {
		if(!(symbolTable.containsClass(n.s))) System.out.println("Error: identifier not found");
		return n;
	}

	// StatementList sl;
	public Type visit(Block n) {
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		return null;
	}

	// Exp e;
	// Statement s1,s2;
	public Type visit(If n) {
		if(!(n.e.accept(this) instanceof BooleanType)) System.out.println("Error: boolean expression expected in if condition");
		n.s1.accept(this);
		n.s2.accept(this);
		return null;
	}

	// Exp e;
	// Statement s;
	public Type visit(While n) {
		if(!(n.e.accept(this) instanceof BooleanType)) System.out.println("Error: boolean expression expected in while condition");
		n.s.accept(this);
		return null;
	}

	// Exp e;
	public Type visit(Print n) {
		n.e.accept(this);
		return null;
	}

	// Identifier i;
	// Exp e;
	public Type visit(Assign n) {
		if(!(symbolTable.compareTypes(symbolTable.getVarType(currMethod, currClass, n.i.s), n.e.accept(this)))) System.out.println("Error: Expressions must have the same type");
		return null;
	}

	// Identifier i;
	// Exp e1,e2;
	public Type visit(ArrayAssign n) {
		if(!(symbolTable.getVarType(currMethod, currClass, n.i.s) instanceof IntArrayType)) System.out.println("Error: Identifier is not an int array");
		if(!(n.e1.accept(this) instanceof IntegerType)) System.out.println("Error: Expression must be of type int");
		if(!(n.e2.accept(this) instanceof IntegerType)) System.out.println("Error: Expression must be of type int");
		return null;
	}

	// Exp e1,e2;
	public Type visit(And n) {
		if(!(symbolTable.compareTypes(n.e1.accept(this), n.e2.accept(this)))) System.out.println("Error: Expressions must be of same type");
		if(!(n.e1.accept(this) instanceof BooleanType)) System.out.println("Error: Expression must be of type boolean");
		if(!(n.e2.accept(this) instanceof BooleanType)) System.out.println("Error: Expression must be of type boolean");
		return new BooleanType();
	}

	// Exp e1,e2;
	public Type visit(LessThan n) {
		if(!(symbolTable.compareTypes(n.e1.accept(this), n.e2.accept(this)))) System.out.println("Error: Expressions must be of same type");
		if(!(n.e1.accept(this) instanceof IntegerType)) System.out.println("Error: Expression must be of type int");
		if(!(n.e2.accept(this) instanceof IntegerType)) System.out.println("Error: Expression must be of type int");
		return new BooleanType();
	}

	// Exp e1,e2;
	public Type visit(Plus n) {
		if(!(symbolTable.compareTypes(n.e1.accept(this), n.e2.accept(this)))) System.out.println("Error: Expressions must be of same type");
		if(!(n.e1.accept(this) instanceof IntegerType)) System.out.println("Error: Expression must be of type int");
		if(!(n.e2.accept(this) instanceof IntegerType)) System.out.println("Error: Expression must be of type int");
		return new IntegerType();
	}

	// Exp e1,e2;
	public Type visit(Minus n) {
		if(!(symbolTable.compareTypes(n.e1.accept(this), n.e2.accept(this)))) System.out.println("Error: Expressions must be of same type");
		if(!(n.e1.accept(this) instanceof IntegerType)) System.out.println("Error: Expression must be of type int");
		if(!(n.e2.accept(this) instanceof IntegerType)) System.out.println("Error: Expression must be of type int");
		return new IntegerType();
	}

	// Exp e1,e2;
	public Type visit(Times n) {
		if(!(symbolTable.compareTypes(n.e1.accept(this), n.e2.accept(this)))) System.out.println("Error: Expressions must be of same type");
		if(!(n.e1.accept(this) instanceof IntegerType)) System.out.println("Error: Expression must be of type int");
		if(!(n.e2.accept(this) instanceof IntegerType)) System.out.println("Error: Expression must be of type int");
		return new IntegerType();
	}

	// Exp e1,e2;
	public Type visit(ArrayLookup n) {
		if(!(n.e1.accept(this) instanceof IntArrayType)) System.out.println("Error: Expression must be of type int array");
		if(!(n.e2.accept(this) instanceof IntegerType)) System.out.println("Error: Expression must be of type int");
		return new IntegerType();
	}

	// Exp e;
	public Type visit(ArrayLength n) {
		if(!(n.e.accept(this) instanceof IntArrayType)) System.out.println("Error: Expression must be of type int array");;
		return new IntegerType();
	}

	// Exp e;
	// Identifier i;
	// ExpList el;
	//PARA DEPOIS N ENTENDI MTO BEM
	public Type visit(Call n) {
		n.e.accept(this);
		n.i.accept(this);
		for (int i = 0; i < n.el.size(); i++) {
			n.el.elementAt(i).accept(this);
		}
		return null;
	}

	// int i;
	public Type visit(IntegerLiteral n) {
		return new IntegerType();
	}

	public Type visit(True n) {
		return new BooleanType();
	}

	public Type visit(False n) {
		return new BooleanType();
	}

	// String s;
	public Type visit(IdentifierExp n) {
		return symbolTable.getVarType(currMethod, currClass, n.s);
	}

	public Type visit(This n) {
		return currClass.type();
	}

	// Exp e;
	public Type visit(NewArray n) {
		if(!(n.e.accept(this) instanceof IntegerType)) System.out.println("Error: Expression must be of type int");
		return null;
	}

	// Identifier i;
	public Type visit(NewObject n) {
		return new IdentifierType(n.i.s);
	}

	// Exp e;
	public Type visit(Not n) {
		if(!(n.e.accept(this) instanceof BooleanType)) System.out.println("Error: boolean expression expected");
		return null;
	}

	// String s;
	public Type visit(Identifier n) {
		return new IdentifierType(n.s);
	}
}
