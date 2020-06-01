package oneiros.engine3D.math;

/**
 *
 * @author oneiros
 */
public class Matrix {
    protected Double[][] matrix;
    private int rows;
    private int columns;
    
    public Matrix(int r, int c){
        this.matrix = new Double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                this.matrix[i][j] = 0d;
            }
        }
        this.rows = r;
        this.columns = c;
    }

    public Matrix(Double[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.columns = matrix[0].length;
    }

    public Matrix(Matrix matrix) {
        this(matrix.matrix);
    }
    
    public Double get(int i, int j){
        return this.matrix[i][j];
    }

    public Matrix sum(Matrix m) {
        if (this.rows != m.rows || this.columns != m.columns) {
            throw new IllegalArgumentException("Matrix dimensions must agree");
        }

        Matrix addMatrix = new Matrix(this.matrix);

        for (int i = 0; i < addMatrix.rows; i++) {
            for (int j = 0; j < addMatrix.columns; j++) {
                addMatrix.add(i, j, m.matrix[i][j]);
            }
        }

        return addMatrix;
    }

    public Matrix times(Matrix m) {
        if (this.rows != m.rows || this.columns != m.columns) {
            throw new IllegalArgumentException("Matrix dimensions must agree");
        }

        Matrix mulMatrix = new Matrix(this.matrix);

        for (int i = 0; i < mulMatrix.rows; i++) {
            for (int j = 0; j < mulMatrix.columns; j++) {
                mulMatrix.mul(i, j, m.matrix[i][j]);
            }
        }

        return mulMatrix;
    }
    
    public void add(int i, int j, Double d){
        this.matrix[i][j] += d;
    }
    
    public void mul(int i, int j, Double d){
        this.matrix[i][j] *= d;
    }
    
    public Matrix mul(Matrix m){
        if (this.columns != m.rows) {
            throw new IllegalArgumentException("A:Rows: " + this.rows + " did not match B:Columns " + m.columns + ".");
        }

        Matrix mulMatrix = new Matrix(this.rows, m.columns);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < m.columns; j++) {
                for (int k = 0; k < this.columns; k++) {
                    mulMatrix.add(i, j, this.matrix[i][k] * m.matrix[k][j]);
                }
            }
        }

        return mulMatrix;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                s.append(matrix[i][j]);
                s.append("\t");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
