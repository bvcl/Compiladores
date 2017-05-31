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

	public TypeCheckVisitor(SymbolTable st) {
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
		currClass = symbolTable.getClass(n.i1.s);
		n.i1.accept(this);
		n.i2.accept(this);
		n.s.accept(this);
		return null;
	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Type visit(ClassDeclSimple n) {
		currClass = symbolTable.getClass(n.i.s);
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
		currClass = symbolTable.getClass(n.i.s);
		n.i.accept(this);
		if (!(symbolTable.containsClass(n.j.s)))System.out.println("Error: Parent Class '"+ n.j.s +"' not found");
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
		if(!(symbolTable.compareTypes(n.t, n.e.accept(this)))) {
			System.out.println("Error: Method return is different from method type declaration");
			if(!(n.t instanceof IdentifierType))System.out.print("       Return should be of type "+ n.t.getClass().toString().substring(10,n.t.getClass().toString().length()-4)+" but it resolved to be of type ");
			else System.out.print("       Return should be of type "+((IdentifierType) n.t).s +" but it resolved to be of type ");
			if(n.e instanceof True || n.e instanceof False ){
				System.out.println("Boolean");
			}
			else{
				Type tp = symbolTable.getVarType(currMethod, currClass,((IdentifierExp)n.e).s );
				if(tp instanceof IdentifierType ) System.out.println(((IdentifierType) tp).s);
				else System.out.println(tp.toString().substring(4, tp.toString().indexOf('@')-4) );
			}	
		}
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
		if(!(symbolTable.containsClass(n.s))) System.out.println("Error: Type not found: "+n.s);
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
		if(!(n.e.accept(this) instanceof BooleanType)) System.out.println("Error: Boolean expression expected in if condition, but it resolved to be of type "+ n.e.getClass().toString().substring(10) );
		n.s1.accept(this);
		n.s2.accept(this);
		return null;
	}

	// Exp e;
	// Statement s;
	public Type visit(While n) {
		if(!(n.e.accept(this) instanceof BooleanType)) System.out.println("Error: Boolean expression expected in while condition, but it resolved to be of type "+ n.e.getClass().toString().substring(10));
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
		if(!(symbolTable.getVarType(currMethod, currClass, n.i.s) instanceof IntArrayType)) System.out.println("Error: "+n.i.s+" is not an int array");
		if(!(n.e1.accept(this) instanceof IntegerType)) System.out.println("Error: Array position must be of type int");
		if(!(n.e2.accept(this) instanceof IntegerType)) System.out.println("Error: Right side of  array assign must be of type int");
		return null;
	}

	// Exp e1,e2;
	public Type visit(And n) {
		if(!(symbolTable.compareTypes(n.e1.accept(this), n.e2.accept(this)))) System.out.println("Error: Expressions must be of same type");
		if(!(n.e1.accept(this) instanceof BooleanType)) System.out.println("Error: Left side expression must be of type boolean");
		if(!(n.e2.accept(this) instanceof BooleanType)) System.out.println("Error: Right side expression must be of type boolean");
		return new BooleanType();
	}

	// Exp e1,e2;
	public Type visit(LessThan n) {
		if(!(symbolTable.compareTypes(n.e1.accept(this), n.e2.accept(this)))) System.out.println("Error: Expressions must be of same type");
		if(!(n.e1.accept(this) instanceof IntegerType)) System.out.println("Error: Left side expression must be of type int");
		if(!(n.e2.accept(this) instanceof IntegerType)) System.out.println("Error: Right side expression must be of type int");
		return new BooleanType();
	}

	// Exp e1,e2;
	public Type visit(Plus n) {
		if(!(symbolTable.compareTypes(n.e1.accept(this), n.e2.accept(this)))) System.out.println("Error: Expressions must be of same type");
		if(!(n.e1.accept(this) instanceof IntegerType)) System.out.println("Error: Left side expression must be of type int");
		if(!(n.e2.accept(this) instanceof IntegerType)) System.out.println("Error: Right side expression must be of type int");
		return new IntegerType();
	}

	// Exp e1,e2;
	public Type visit(Minus n) {
		if(!(symbolTable.compareTypes(n.e1.accept(this), n.e2.accept(this)))) System.out.println("Error: Expressions must be of same type");
		if(!(n.e1.accept(this) instanceof IntegerType)) System.out.println("Error: Left side expression must be of type int");
		if(!(n.e2.accept(this) instanceof IntegerType)) System.out.println("Error: Right side expression must be of type int");
		return new IntegerType();
	}

	// Exp e1,e2;
	public Type visit(Times n) {
		if(!(symbolTable.compareTypes(n.e1.accept(this), n.e2.accept(this)))) System.out.println("Error: Expressions must be of same type");
		if(!(n.e1.accept(this) instanceof IntegerType)) System.out.println("Error: Left side expression must be of type int");
		if(!(n.e2.accept(this) instanceof IntegerType)) System.out.println("Error: Right side expression must be of type int");
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
		Type tp = n.e.accept(this);
		Method met = null;
		if(tp instanceof IdentifierType){
			Class c;
			c = symbolTable.getClass(((IdentifierType)tp).s);
			if(c == null){
				System.out.println("Error: Type "+ ((IdentifierType)tp).s + " of object does not exist");
			}
			else{
				met = c.getMethod(n.i.toString());
				if(met==null)System.out.println("Method not found for class "+((IdentifierType)tp).s);
				for(int i = 0; i < n.el.size(); i++){
					if(!symbolTable.compareTypes(n.el.elementAt(i).accept(this), met.getParamAt(i).type())){
						System.out.println("Error: Type of parameter "+n.el.elementAt(i).toString()+" does not match method declaration");
					}
				}
			}
		} 
		else {
			System.out.println("Error: Type cannot call methods");
		}
		if(met!=null)return met.type();
		else return null;
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
		if( symbolTable.getClass(n.i.toString())==null){
			System.out.println("Error:Object not found");
			return null;
		}
		return new IdentifierType(n.i.s);
	}

	// Exp e;
	public Type visit(Not n) {
		if(!(n.e.accept(this) instanceof BooleanType)) System.out.println("Error: boolean expression expected");
		return new BooleanType();
	}

	// String s;
	public Type visit(Identifier n) {
		return new IdentifierType(n.s);
	}
}
