package main;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import ast.*;
import visitor.*;
import symboltable.*;
import baseClasses.*;
public class Main {

	public static void main(String[] args)throws IOException {
		InputStream stream = new FileInputStream("src/test.txt");
		ANTLRInputStream input = new ANTLRInputStream(stream);
		ajgan_bvclLexer lexer = new ajgan_bvclLexer(input);
		CommonTokenStream token = new CommonTokenStream(lexer);
		ajgan_bvclParser parser = new ajgan_bvclParser(token);
		ParseTree tree = parser.goal();
		System.out.println(tree.toStringTree(parser));
		AjganBvclVisitor visitor = new AjganBvclVisitor();
		Program program = (Program) visitor.visit(tree);
		program.accept(new PrettyPrintVisitor());

	}

}
