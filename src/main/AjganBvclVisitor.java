package main;
import ast.*;
import visitor.*;
import baseClasses.*;
public class AjganBvclVisitor extends ajgan_bvclBaseVisitor<Object> {
	
	public Object visitGoal(ajgan_bvclParser.GoalContext ctx) {
		System.out.println("Visited Goal");
		MainClass mainClass = (MainClass) this.visit(ctx.getChild(0));
		ClassDeclList classDecList = new ClassDeclList();
		for (ajgan_bvclParser.ClassDeclarationContext classDeclarList : ctx.classDeclaration()) {
			classDecList.addElement((ClassDecl) this.visit(classDeclarList));
		}
		Program prog = new Program(mainClass, classDecList);
		return prog;
	}
	
	public Object visitMainClass(ajgan_bvclParser.MainClassContext ctx) {
		System.out.println("Visited Main Class");
		Identifier id = new Identifier(ctx.identifier(0).getText());
		Identifier id2 = new Identifier(ctx.identifier(1).getText());
		Statement st = (Statement) this.visit(ctx.statement());
		MainClass mc = new MainClass(id, id2, st);
		return mc;
	}
	
	public Object visitIdentifier(ajgan_bvclParser.IdentifierContext ctx) {
		System.out.println("Identifier" + ctx.getText());
		Identifier id = new Identifier(ctx.getText());
		return id;
	}
	
	public Object visitStatement(ajgan_bvclParser.StatementContext ctx){
		System.out.println("Visited Statment");
		Statement stat=null;
		if(ctx.getChild(0).toString().equals("{")){
			StatementList stList = new StatementList();
			for (ajgan_bvclParser.StatementContext statment : ctx.statement()) {
				stList.addElement((Statement) this.visit(statment));
			}
			stat = new Block(stList);
		}
		else if(ctx.getChild(0).toString().equals("if")){
			Exp e = (Exp) this.visit(ctx.expression(0));
			Statement s1 = (Statement) this.visit(ctx.statement(0));
			Statement s2 = (Statement) this.visit(ctx.statement(1));
			stat = new If(e, s1, s2);		
		}
		else if(ctx.getChild(0).toString().equals("while")){
			Exp e = (Exp) this.visit(ctx.expression(0));
			Statement s1 = (Statement) this.visit(ctx.statement(0));	
			stat = new While(e, s1);	
		}
		else if(ctx.getChild(0).toString().equals("System.out.println")){
			Exp e = (Exp) this.visit(ctx.expression(0));
			stat = new Print(e);
		}
		else if(ctx.getChild(1).toString().equals("=")){
			Identifier id = new Identifier(ctx.getChild(0).getText());
			Exp e = (Exp) this.visit(ctx.expression(0));
			stat = new Assign(id, e);
		}
		else if(ctx.getChild(1).toString().equals("[")){
			Identifier id = new Identifier(ctx.getChild(0).getText());
			Exp e = (Exp) this.visit(ctx.expression(0));
			Exp e2 = (Exp) this.visit(ctx.expression(1));
			stat = new ArrayAssign(id, e, e2);
		}
		return stat;
	}
	
	public Object visitClassDeclaration(ajgan_bvclParser.ClassDeclarationContext ctx) {
		Identifier id = new Identifier(ctx.getChild(1).getText());
		Identifier id2 = null;
		ClassDecl cd = null;
		if(ctx.getChild(2).toString().equals("extends")){
			id2 = new Identifier(ctx.getChild(3).getText());
		}
		
		VarDeclList vList = new VarDeclList();
		MethodDeclList mList = new MethodDeclList();
		for (ajgan_bvclParser.VarDeclarationContext var : ctx.varDeclaration()) {
			vList.addElement((VarDecl) this.visit(var));
		}
		for (ajgan_bvclParser.MethodDeclarationContext method : ctx.methodDeclaration()) {
			mList.addElement((MethodDecl) this.visit(method));
		}
		
		if(id2!=null)cd = new ClassDeclExtends(id,id2,vList,mList);
		else cd = new ClassDeclSimple(id,vList,mList);
		
		return cd;
	}
	
	public Object visitVarDeclaration(ajgan_bvclParser.VarDeclarationContext ctx) {		
		Type t = (Type)this.visit(ctx.type());
		Identifier id = new Identifier(ctx.identifier().getText());
		VarDecl var = new VarDecl(t,id);
		return var;
	}
	
	public Object visitMethodDeclaration(ajgan_bvclParser.MethodDeclarationContext ctx) {		
		MethodDecl md = null;
		int c =0;
		Type t = (Type)this.visit(ctx.type(c));
		Identifier id = new Identifier(ctx.identifier(c).getText());
		c++;
		int count = 4;
		FormalList fList = new FormalList();
	
		while(!ctx.getChild(count).toString().equals(")")){
			Type t2 = (Type)this.visit(ctx.type(c));
			Identifier id2 = new Identifier(ctx.identifier(c).getText());
			Formal f = new Formal(t2,id2);
			fList.addElement(f);			
			c++;
			if(ctx.getChild(count+2).toString().equals(",")){
				count+=3;
			}
			else break;
		}
		
		VarDeclList vList = new VarDeclList();
		for (ajgan_bvclParser.VarDeclarationContext var : ctx.varDeclaration()) {
			vList.addElement((VarDecl) this.visit(var));
		}
		StatementList sList = new StatementList();
		for (ajgan_bvclParser.StatementContext st : ctx.statement()) {
			sList.addElement((Statement) this.visit(st));
		}
		Exp e = (Exp) this.visit(ctx.expression());
		
		md =new MethodDecl(t,id,fList,vList,sList,e);
		return md;
		
	}

	public Object visitType(ajgan_bvclParser.TypeContext ctx) {
		Type type = null;
		if (ctx.getChild(0).toString().equals("int")) {
			if(ctx.getChildCount() > 1){
				type = new IntArrayType();
			}
			else{
				type = new IntegerType();
			}
		} 
		else if (ctx.getChild(0).toString().equals("boolean")) {
			type = new BooleanType();
		} 
		else {
			type = new IdentifierType(ctx.getText());
		}
		return type;
	}
	
	public Object visitExpression(ajgan_bvclParser.ExpressionContext ctx) {
		System.out.println("Expression");
		Exp exp = null;
		if(ctx.getChild(0).toString().equals("true")){
			System.out.println("true");
			exp = new True();
		}
		else if(ctx.getChild(0).toString().equals("false")){
			System.out.println("false");
			exp = new False();
		}
		else if(ctx.getChild(0).toString().equals("this")){
			System.out.println("this");
			exp = new This();
		}
		else if(ctx.getChild(0).toString().equals("!")){
			System.out.println("! expression");
			exp = new Not((Exp) this.visit(ctx.expression(0)));	
		}
		else if(ctx.getChild(0).toString().equals("(")){
			System.out.println("(expression)");
			exp =(Exp)this.visit(ctx.expression(0));	
		}
		else if(ctx.getChild(0).toString().equals("new")){
			if(ctx.getChild(1).toString().equals("int")){
				System.out.println("new int [expression] ");
				exp = new NewArray((Exp) this.visit(ctx.expression(0)));
			}
			else{
				System.out.println("new identifier()");
				exp = new NewObject(new Identifier(ctx.identifier().getText()));
			}
		}
		else if(ctx.INTEGER_LITERAL()!=null){
			exp = new IntegerLiteral(Integer.parseInt(ctx.INTEGER_LITERAL().getText()));
		}
		else if(ctx.expression()==null){
			System.out.println("identifier");
			exp = new IdentifierExp(ctx.identifier().getText());
		}
		else{
			//casos que iniciam com expression
			if(ctx.getChild(1).toString().equals("[")){
				System.out.println("expression [expression]");
				Exp e = (Exp) this.visit(ctx.expression(0));
				Exp e2 = (Exp) this.visit(ctx.expression(1));
				exp = new ArrayLookup(e, e2);
			}
			else if(ctx.getChild(1).toString().equals(".")){
				if(ctx.getChild(2).toString().equals("length")){
					System.out.println("expression.lenght");
					exp = new ArrayLength((Exp) this.visit(ctx.expression(0)));
				}
				else{
					System.out.println("expression.identifier((expression ( ',' expression )* )?)");
					ExpList eList = new ExpList();
					
					for (ajgan_bvclParser.ExpressionContext expr : ctx.expression()) {
						eList.addElement((Exp) this.visit(expr));
					}
					Identifier id = new Identifier(ctx.identifier().getText());
				    Exp e = eList.elementAt(0);
					eList.removeElement(0);
					exp = new Call(e,id, eList);			
				}
			}
			else{
				//casos com os operadores
				Exp e = (Exp) this.visit(ctx.expression(0));
				Exp e2 = (Exp) this.visit(ctx.expression(1));
				switch (ctx.getChild(1).toString()) {
					case "&&":
						System.out.println("expression && expression");
						exp = new And(e, e2);
						break;
					case "<":
						System.out.println("expression < expression");
						exp = new LessThan(e, e2);
						break;
					case "+":
						System.out.println("expression + expression");
						exp = new Plus(e, e2);
						break;
					case "-":
						System.out.println("expression - expression");
						exp = new Minus(e, e2);
						break;
					case "*":
						System.out.println("expression * expression");
						exp = new Times(e, e2);
						break;
				}
			}
		}
		return exp;
	}
	
}
