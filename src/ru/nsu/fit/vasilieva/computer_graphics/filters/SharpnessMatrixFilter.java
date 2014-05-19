package ru.nsu.fit.vasilieva.computer_graphics.filters;

import ru.nsu.fit.vasilieva.computer_graphics.filters.AbstractMatrixFilter;

public class SharpnessMatrixFilter extends AbstractMatrixFilter
{
    @Override
    protected void generateMatrix()
    {
        N = 3;
        matrix = new double[N][N];

        matrix[0][0] = 0;
        matrix[0][1] = -1;
        matrix[0][2] = 0;
        matrix[1][0] = -1;
        matrix[1][1] = 5;
        matrix[1][2] = -1;
        matrix[2][0] = 0;
        matrix[2][1] = -1;
        matrix[2][2] = 0;
    }
}
