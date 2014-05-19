package ru.nsu.fit.vasilieva.computer_graphics.filters;

import ru.nsu.fit.vasilieva.computer_graphics.filters.AbstractFormulaFilter;

public class BlackAndWhiteFormulaFilter extends AbstractFormulaFilter
{
    private int k;

    private static int BLACK = 0;
    private static int WHITE = 16777215;

    public BlackAndWhiteFormulaFilter(int[] parameters)
    {
        k = parameters[0];
    }

    protected int useFormula(int r, int g, int b)
    {
        int coeff = (int)(0.299 * r + 0.587 * g + 0.114 * b);
        if (coeff < k)
        {
            return BLACK;
        }
        return WHITE;
    }
}
