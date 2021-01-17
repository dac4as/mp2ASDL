package it.unicam.cs.asdl2021.mp2.Task4;

/**
 * 
 * Class that solves an instance of the the El Mamun's Caravan problem using
 * dynamic programming.
 * 
 * Template: Daniele Marchei and Luca Tesei, Implementation: Niccolò Cuartas - niccolo.cuartas@studenti.unicam.it
 *
 */
public class ElMamunCaravanSolver {

    // the expression to analyse
    private final Expression expression;

    // table to collect the optimal solution for each sub-problem,
    // protected just for Junit Testing purposes
    protected Integer[][] table; //TABELLA M

    // table to record the chosen optimal solution among the optimal solution of
    // the sub-problems, protected just for JUnit Testing purposes
    protected Integer[][] tracebackTable; //TABELLA S

    // flag indicating that the problem has been solved at least once
    private boolean solved;

    /**
     * Create a solver for a specific expression.
     * 
     * @param expression
     *                       The expression to work on
     * @throws NullPointerException
     *                                  if the expression is null
     */
    public ElMamunCaravanSolver(Expression expression) {
        if (expression == null)
            throw new NullPointerException(
                    "Creazione di solver con expression null");
        this.expression = expression;
        this.table = new Integer[expression.size()][expression.size()];
        this.tracebackTable = new Integer[expression.size()][expression.size()];
        this.solved = false;
    }

    /**
     * Returns the expression that this solver analyse.
     * 
     * @return the expression of this solver
     */
    public Expression getExpression() {
        return this.expression;
    }

    /**
     * Solve the problem on the expression of this solver by using a given
     * objective function.
     * 
     * @param function
     *                     The objective function to be used when deciding which
     *                     candidate to choose
     * @throws NullPointerException
     *                                  if the objective function is null
     */
    public void solve(ObjectiveFunction function) {//può essere MinimumFuncion o MaximumFunction, deve contenere il risulato massimo/minimo ottenibile in base a cosa richiesto
        if(function==null) throw new NullPointerException();
        if(isSolved()) return;

        for (int i=0; i<expression.size()-1;i++)
        {
            table[i][i] = 0;
            tracebackTable[i][i] =-1;

        }
        //struttura della matrice table
        for(int h = 1; h<expression.size()-1; h++)
        {
            for(int i = 0; i<expression.size()-h; i++)
            {
                int j = i + h;
                table[i][j] = Integer.MAX_VALUE;
                tracebackTable[i][j]=-1;
                for(int k=i;k<j;k++)
                {
                    int cost =(int) expression.get(i).getValue() * (int) expression.get(k+1).getValue() * (int) expression.get(j+1).getValue();
                    if(table[i][j] > (table[i][k] + table[k+1][j] + cost))
                    {
                        table[i][j] = table[i][k] + table[k+1][j]+ cost;
                        table[i][j] = k;
                    }
                }
            }
        }
this.solved=true;
    }

    /**
     * Returns the current optimal value for the expression of this solver. The
     * value corresponds to the one obtained after the last solving (which used
     * a particular objective function).
     * 
     * @return the current optimal value
     * @throws IllegalStateException
     *                                   if the problem has never been solved
     */
    public int getOptimalSolution() {
        if(!isSolved()) throw new IllegalArgumentException("Il problema non è stato risolto.");
        return table[0][expression.size()-2];
    }

    /**
     * Returns an optimal parenthesization corresponding to an optimal solution
     * of the expression of this solver. The parenthesization corresponds to the
     * optimal value obtained after the last solving (which used a particular
     * objective function).
     * 
     * If the expression is just a digit then the parenthesization is the
     * expression itself. If the expression is not just a digit then the
     * parethesization is of the form "(<parenthesization>)". Examples: "1",
     * "(1+2)", "(1*(2+(3*4)))"
     * 
     * @return the current optimal parenthesization for the expression of this
     *         solver
     * @throws IllegalStateException
     *                                   if the problem has never been solved
     */
    public String getOptimalParenthesization() {
        if(!isSolved()) throw new IllegalStateException("Il problema non è stato risolto.");
        return null;
    }

    /**
     * Determines if the problem has been solved at least once.
     * 
     * @return true if the problem has been solved at least once, false
     *         otherwise.
     */
    public boolean isSolved() {
        return this.solved;
    }

    @Override
    public String toString() {
        return "ElMamunCaravanSolver for " + expression;
    }

    // TODO implementare: inserire eventuali metodi privati per rendere
    // l'implementazione più modulare
}
