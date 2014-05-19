package ru.nsu.fit.vasilieva.computer_graphics.filters;

import ru.nsu.fit.vasilieva.computer_graphics.filters.AbstractFormulaFilter;

public class GrayscaleFormulaFilter extends AbstractFormulaFilter
{
    private static double Kr = 0.3;
    private static double Kg = 0.59;
    private static double Kb = 0.11;

    @Override
    protected int useFormula(int r, int g, int b)
    {
        int comp = (int) Math.round(r * Kr + g * Kb + b * Kb);
        return (comp * 256 * 256 + comp * 256 + comp);
    }
}
