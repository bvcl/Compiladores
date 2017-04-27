// Generated from ajgan_bvcl.g4 by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ajgan_bvclParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ajgan_bvclVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ajgan_bvclParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(@NotNull ajgan_bvclParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link ajgan_bvclParser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(@NotNull ajgan_bvclParser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ajgan_bvclParser#goal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGoal(@NotNull ajgan_bvclParser.GoalContext ctx);
	/**
	 * Visit a parse tree produced by {@link ajgan_bvclParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(@NotNull ajgan_bvclParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ajgan_bvclParser#mainClass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainClass(@NotNull ajgan_bvclParser.MainClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link ajgan_bvclParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull ajgan_bvclParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ajgan_bvclParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(@NotNull ajgan_bvclParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ajgan_bvclParser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclaration(@NotNull ajgan_bvclParser.VarDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ajgan_bvclParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(@NotNull ajgan_bvclParser.ClassDeclarationContext ctx);
}