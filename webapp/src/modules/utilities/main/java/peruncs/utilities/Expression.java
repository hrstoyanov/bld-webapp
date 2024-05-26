package peruncs.utilities;

import peruncs.utilities.Expression.Numerical.ConstantExpression;


import java.util.function.Function;
sealed public interface Expression {

    sealed interface Numerical{
        record SumExpression(Expression left, Expression right) implements Numerical {}
        record ProductExpression(Expression left, Expression right) implements Numerical {}

        record MinExpression(Expression left, Expression right) implements Numerical {}
        record MaxExpression(Expression left, Expression right) implements Expression {}
        record ConstantExpression<T>( T value) implements Numerical{}

        @FunctionalInterface
        non-sealed interface ValueSupplier<Object, Numerical> extends  Function<Object, Numerical>, Expression.Numerical {}
    }

    sealed  interface Logical {
        record AndExpression(Logical left, Logical right) implements Logical {}
        record OrExpression(Logical left, Logical right) implements Logical {}
        record NotExpression(Logical expression) implements Logical {}
        record LessThan(Numerical lesser, Numerical greater) implements Logical {}
        record LessThanOrEqual(Numerical lesser, Numerical greater) implements Logical {}
        record GreaterThan(Numerical lesser, Numerical greater) implements Logical {}
        record GreaterThanOrEqual(Numerical lesser, Numerical greater) implements Logical {}
        record BetweenInclusiveExpression(Numerical lesser, Numerical greater) implements Logical {}
        record BetweenExclusiveExpression(Numerical lesser, Numerical greater) implements Logical {}

        static boolean test(Logical expression){
            return switch(expression){
                case AndExpression(var left, var right ) -> test(left) && test(right);
                case OrExpression(var left, var right ) -> test(left) || test(right);
                case NotExpression(var e) -> !test(e);
                case GreaterThan(var lesser, var greater) -> true;//todo
                case LessThan(var lesser, var greater) -> true;//todo
                case LessThanOrEqual(var lesser, var greater) -> true;//todo
                case GreaterThanOrEqual(var lesser, var greater) -> true;//todo
                case BetweenInclusiveExpression(var left, var right ) -> true;//todo
                case BetweenExclusiveExpression(var left, var right ) -> true;//todo
            };
        }



    }

    static  int compare(Numerical left, Numerical right){
        return switch(left) {
            case ConstantExpression(var v)-> 1;
            default -> 0;//todo
        };
    }


}
