/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authors: Stefan Irimescu, Can Berker Cikis
 *
 */

package sparksoniq.semantics.visitor;

import org.rumbledb.expressions.CommaExpression;
import org.rumbledb.expressions.ExpressionOrClause;
import org.rumbledb.expressions.control.IfExpression;
import org.rumbledb.expressions.control.SwitchCaseExpression;
import org.rumbledb.expressions.control.SwitchExpression;
import org.rumbledb.expressions.control.TypeSwitchCaseExpression;
import org.rumbledb.expressions.control.TypeSwitchExpression;
import org.rumbledb.expressions.flowr.CountClause;
import org.rumbledb.expressions.flowr.FlworExpression;
import org.rumbledb.expressions.flowr.ForClause;
import org.rumbledb.expressions.flowr.ForClauseVar;
import org.rumbledb.expressions.flowr.GroupByClause;
import org.rumbledb.expressions.flowr.GroupByClauseVar;
import org.rumbledb.expressions.flowr.LetClause;
import org.rumbledb.expressions.flowr.LetClauseVar;
import org.rumbledb.expressions.flowr.OrderByClause;
import org.rumbledb.expressions.flowr.OrderByClauseExpr;
import org.rumbledb.expressions.flowr.ReturnClause;
import org.rumbledb.expressions.flowr.WhereClause;
import org.rumbledb.expressions.module.MainModule;
import org.rumbledb.expressions.module.Prolog;
import org.rumbledb.expressions.operational.AdditiveExpression;
import org.rumbledb.expressions.operational.AndExpression;
import org.rumbledb.expressions.operational.CastExpression;
import org.rumbledb.expressions.operational.CastableExpression;
import org.rumbledb.expressions.operational.ComparisonExpression;
import org.rumbledb.expressions.operational.InstanceOfExpression;
import org.rumbledb.expressions.operational.MultiplicativeExpression;
import org.rumbledb.expressions.operational.NotExpression;
import org.rumbledb.expressions.operational.OrExpression;
import org.rumbledb.expressions.operational.RangeExpression;
import org.rumbledb.expressions.operational.StringConcatExpression;
import org.rumbledb.expressions.operational.TreatExpression;
import org.rumbledb.expressions.operational.UnaryExpression;
import org.rumbledb.expressions.postfix.PostFixExpression;
import org.rumbledb.expressions.primary.ArgumentPlaceholder;
import org.rumbledb.expressions.primary.ArrayConstructor;
import org.rumbledb.expressions.primary.BooleanLiteral;
import org.rumbledb.expressions.primary.ContextExpression;
import org.rumbledb.expressions.primary.DecimalLiteral;
import org.rumbledb.expressions.primary.DoubleLiteral;
import org.rumbledb.expressions.primary.FunctionCall;
import org.rumbledb.expressions.primary.FunctionDeclaration;
import org.rumbledb.expressions.primary.IntegerLiteral;
import org.rumbledb.expressions.primary.NamedFunctionRef;
import org.rumbledb.expressions.primary.NullLiteral;
import org.rumbledb.expressions.primary.ObjectConstructor;
import org.rumbledb.expressions.primary.ParenthesizedExpression;
import org.rumbledb.expressions.primary.StringLiteral;
import org.rumbledb.expressions.primary.VariableReference;
import org.rumbledb.expressions.quantifiers.QuantifiedExpression;
import org.rumbledb.expressions.quantifiers.QuantifiedExpressionVar;

public abstract class AbstractExpressionOrClauseVisitor<T> {

    public T visit(ExpressionOrClause expression, T argument) {
        return expression.accept(this, argument);
    }

    public T visitDescendants(ExpressionOrClause expression, T argument) {
        T result = argument;
        for (ExpressionOrClause child : expression.getDescendants()) {
            result = visit(child, result);
        }
        return result;
    }

    protected T defaultAction(ExpressionOrClause expression, T argument) {
        return visitDescendants(expression, argument);
    }

    public T visitCommaExpression(CommaExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    // region module
    public T visitMainModule(MainModule expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitProlog(Prolog expression, T argument) {
        return defaultAction(expression, argument);
    }

    // endregion

    // region flwor
    public T visitFlowrExpression(FlworExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitVariableReference(VariableReference expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitForClause(ForClause expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitLetClause(LetClause expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitGroupByClause(GroupByClause expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitOrderByClause(OrderByClause expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitForClauseVar(ForClauseVar expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitLetClauseVar(LetClauseVar expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitGroupByClauseVar(GroupByClauseVar expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitOrderByClauseExpr(OrderByClauseExpr expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitWhereClause(WhereClause expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitCountClause(CountClause expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitReturnClause(ReturnClause expression, T argument) {
        return defaultAction(expression, argument);
    }
    // endregion

    // region primary
    public T visitPostfixExpression(PostFixExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitArrayConstructor(ArrayConstructor expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitObjectConstructor(ObjectConstructor expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitContextExpr(ContextExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitFunctionCall(FunctionCall expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitParenthesizedExpression(ParenthesizedExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitFunctionDeclaration(FunctionDeclaration expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitNamedFunctionRef(NamedFunctionRef expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitArgumentPlaceholder(ArgumentPlaceholder expression, T argument) {
        return defaultAction(expression, argument);
    }
    // endregion

    // region literal
    public T visitInteger(IntegerLiteral expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitString(StringLiteral expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitDouble(DoubleLiteral expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitDecimal(DecimalLiteral expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitNull(NullLiteral expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitBoolean(BooleanLiteral expression, T argument) {
        return defaultAction(expression, argument);
    }
    // endregion

    // region operational
    public T visitAdditiveExpr(AdditiveExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitMultiplicativeExpr(MultiplicativeExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitAndExpr(AndExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitOrExpr(OrExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitNotExpr(NotExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitUnaryExpr(UnaryExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitRangeExpr(RangeExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitStringConcatExpr(StringConcatExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitComparisonExpr(ComparisonExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitInstanceOfExpression(InstanceOfExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitTreatExpression(TreatExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitCastableExpression(CastableExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitCastExpression(CastExpression expression, T argument) {
        return defaultAction(expression, argument);
    }
    // endregion

    // region quantifiers
    public T visitQuantifiedExpression(QuantifiedExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitQuantifiedExpressionVar(QuantifiedExpressionVar expression, T argument) {
        return defaultAction(expression, argument);
    }
    // endregion

    // region control
    public T visitIfExpression(IfExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitSwitchExpression(SwitchExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitSwitchCaseExpression(SwitchCaseExpression expression, T argument) {
        return defaultAction(expression, argument);
    }
    // endregion

    public T visitTypeSwitchExpression(TypeSwitchExpression expression, T argument) {
        return defaultAction(expression, argument);
    }

    public T visitTypeSwitchCaseExpression(TypeSwitchCaseExpression expression, T argument) {
        return defaultAction(expression, argument);
    }
}
