package baseClasses;
// Generated from ajgan_bvcl.g4 by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ajgan_bvclParser}.
 */
public interface ajgan_bvclListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ajgan_bvclParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(@NotNull ajgan_bvclParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ajgan_bvclParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(@NotNull ajgan_bvclParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ajgan_bvclParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(@NotNull ajgan_bvclParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ajgan_bvclParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(@NotNull ajgan_bvclParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ajgan_bvclParser#goal}.
	 * @param ctx the parse tree
	 */
	void enterGoal(@NotNull ajgan_bvclParser.GoalContext ctx);
	/**
	 * Exit a parse tree produced by {@link ajgan_bvclParser#goal}.
	 * @param ctx the parse tree
	 */
	void exitGoal(@NotNull ajgan_bvclParser.GoalContext ctx);
	/**
	 * Enter a parse tree produced by {@link ajgan_bvclParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull ajgan_bvclParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ajgan_bvclParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull ajgan_bvclParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ajgan_bvclParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void enterMainClass(@NotNull ajgan_bvclParser.MainClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link ajgan_bvclParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void exitMainClass(@NotNull ajgan_bvclParser.MainClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link ajgan_bvclParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull ajgan_bvclParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ajgan_bvclParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull ajgan_bvclParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ajgan_bvclParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(@NotNull ajgan_bvclParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ajgan_bvclParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(@NotNull ajgan_bvclParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ajgan_bvclParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(@NotNull ajgan_bvclParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ajgan_bvclParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(@NotNull ajgan_bvclParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ajgan_bvclParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(@NotNull ajgan_bvclParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ajgan_bvclParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(@NotNull ajgan_bvclParser.ClassDeclarationContext ctx);
}