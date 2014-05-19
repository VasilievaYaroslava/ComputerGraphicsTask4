package ru.nsu.fit.vasilieva.computer_graphics.filters;

public class GaussianMatrixFilter extends AbstractMatrixFilter
{
    @Override
    protected void generateMatrix()
    {
        N = 3;
        matrix = new double[N][N];
        matrix[0][0] = 0.5;
        matrix[0][1] = 0.75;
        matrix[0][2] = 0.5;
        matrix[1][0] = 0.75;
        matrix[1][1] = 1;
        matrix[1][2] = 0.75;
        matrix[2][0] = 0.5;
        matrix[2][1] = 0.75;
        matrix[2][2] = 0.5;
    }
}
