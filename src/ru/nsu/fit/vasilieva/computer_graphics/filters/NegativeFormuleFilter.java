package ru.nsu.fit.vasilieva.computer_graphics.filters;

public class NegativeFormuleFilter extends AbstractFormulaFilter
{
    @Override
    protected int useFormula(int r, int g, int b)
    {
        return ((255 - r) * 256 * 256 + (255 - g) * 256 + (255 - b));
    }
}
