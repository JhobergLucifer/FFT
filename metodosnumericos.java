#devdopor Engieering Jhoberg Quevedo Ruiz book Metodos numericos mcgwawhill

import java.awt.Event;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.awt.Choice;
import java.awt.Label;
import java.awt.Button;
import java.awt.TextArea;
import java.awt.TextField;
import java.applet.Applet;

// 
// Decompiled by Procyon v0.6.0
// 

public class mt extends Applet
{
    TextField textField;
    TextArea textArea;
    TextArea rta;
    Button solucion;
    Button limpiar;
    Button adicionar;
    Label label;
    Label des;
    Label respuesta;
    Choice seleccion;
    int top;
    String[][] mat;
    float[][] mat1;
    float[][] sol;
    int col;
    int fila;
    
    public void init() {
        this.solucion = new Button("Solucionar");
        this.limpiar = new Button("Limpiar");
        this.adicionar = new Button("Adicionar");
        (this.label = new Label()).setText("Metodos");
        (this.des = new Label()).setText("Vector de ingreso :");
        (this.respuesta = new Label()).setText("Resultados :");
        (this.seleccion = new Choice()).addItem("Gauss");
        this.seleccion.addItem("Gauss Jordan");
        this.seleccion.addItem("Jacob");
        this.seleccion.addItem("Seidel");
        this.textField = new TextField(40);
        this.textArea = new TextArea(5, 20);
        (this.rta = new TextArea(5, 20)).setEditable(false);
        this.textArea.setEditable(false);
        final GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        layout.setConstraints(this.label, gridBagConstraints);
        this.add(this.label);
        gridBagConstraints.gridwidth = 0;
        layout.setConstraints(this.seleccion, gridBagConstraints);
        this.add(this.seleccion);
        gridBagConstraints.weightx = 0.0;
        layout.setConstraints(this.solucion, gridBagConstraints);
        this.add(this.solucion);
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        layout.setConstraints(this.des, gridBagConstraints);
        this.add(this.des);
        layout.setConstraints(this.textField, gridBagConstraints);
        this.add(this.textField);
        gridBagConstraints.gridwidth = 1;
        layout.setConstraints(this.limpiar, gridBagConstraints);
        this.add(this.limpiar);
        gridBagConstraints.gridwidth = 0;
        layout.setConstraints(this.adicionar, gridBagConstraints);
        this.add(this.adicionar);
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        layout.setConstraints(this.textArea, gridBagConstraints);
        this.add(this.textArea);
        layout.setConstraints(this.respuesta, gridBagConstraints);
        this.add(this.respuesta);
        layout.setConstraints(this.rta, gridBagConstraints);
        this.add(this.rta);
        this.validate();
    }
    
    public boolean action(final Event event, final Object o) {
        if (event.target == this.limpiar) {
            this.col = 0;
            this.fila = 0;
            this.rta.setEditable(true);
            this.textArea.setEditable(true);
            this.textArea.replaceText(" ", 0, 500);
            this.rta.replaceText(" ", 0, 500);
            this.rta.setEditable(false);
            this.textArea.setEditable(false);
        }
        if (event.target == this.solucion) {
            final int selectedIndex = this.seleccion.getSelectedIndex();
            if (selectedIndex == 0) {
                this.gauss();
            }
            if (selectedIndex == 1) {
                this.gaussj();
            }
            if (selectedIndex == 2) {
                this.jacob();
            }
            if (selectedIndex == 3) {
                this.seidel();
            }
            if (selectedIndex == 1 || selectedIndex == 0) {
                for (int i = 0; i < this.fila; ++i) {
                    this.rta.appendText("[");
                    for (int j = 0; j <= this.col; ++j) {
                        if (j == this.col - 1) {
                            this.rta.appendText(String.valueOf(this.sol[i][j]) + "   ||   ");
                        }
                        else if (j == this.col) {
                            this.rta.appendText(String.valueOf(this.sol[i][j]) + " ");
                        }
                        else {
                            this.rta.appendText(String.valueOf(this.sol[i][j]) + "    ,");
                        }
                    }
                    this.rta.appendText("]\n");
                }
            }
            else {
                this.rta.appendText("Vectores de aproximaciones");
                this.rta.appendText("\n");
                this.rta.appendText("\n");
                this.rta.appendText("Iteracion               Valores");
                this.rta.appendText("\n");
                for (int k = 0; k <= this.top; ++k) {
                    this.rta.appendText(String.valueOf(k) + "                  ");
                    this.rta.appendText("[");
                    for (int l = 0; l < this.col; ++l) {
                        if (l == this.col - 1) {
                            this.rta.appendText(String.valueOf(this.sol[k][l]) + " ");
                        }
                        else {
                            this.rta.appendText(String.valueOf(this.sol[k][l]) + "    ,");
                        }
                    }
                    this.rta.appendText("]\n");
                }
            }
        }
        if (event.target == this.adicionar) {
            if (this.validar(this.textField.getText())) {
                this.textArea.appendText("[ ");
                for (int n = 0; n <= this.col; ++n) {
                    if (n == this.col - 1) {
                        this.textArea.appendText(String.valueOf(this.mat1[this.fila - 1][n]) + "  ||   ");
                    }
                    else if (n == this.col) {
                        this.textArea.appendText(String.valueOf(this.mat1[this.fila - 1][n]) + " ");
                    }
                    else {
                        this.textArea.appendText(String.valueOf(this.mat1[this.fila - 1][n]) + ",         ");
                    }
                }
                this.textArea.appendText("]\n");
            }
            this.rta.selectAll();
        }
        this.textField.setText("  ");
        return true;
    }
    
    public boolean validar(final String s) {
        int col = 0;
        int n = 0;
        String string = new String();
        if (s.length() > 0) {
            for (int i = 0; i < s.length(); ++i) {
                if (s.charAt(i) == ',') {
                    final Float n2 = new Float(string);
                    if (n == 1) {
                        this.mat1[this.fila][col] = n2 * -1.0f;
                    }
                    else {
                        this.mat1[this.fila][col] = n2;
                    }
                    string = new String();
                    ++col;
                    this.col = col;
                    n = 0;
                }
                else if (s.charAt(i) == '-') {
                    n = 1;
                }
                else {
                    string = String.valueOf(string) + s.charAt(i);
                }
            }
            final Float n3 = new Float(string);
            if (n == 1) {
                this.mat1[this.fila][col] = n3 * -1.0f;
            }
            else {
                this.mat1[this.fila][col] = n3;
            }
            final String s2 = new String();
            ++this.fila;
            return true;
        }
        return false;
    }
    
    public void gauss() {
        this.sol = this.mat1;
        for (int i = 0; i <= this.fila; ++i) {
            final float n = this.sol[i][i];
            for (int j = 0; j <= this.col; ++j) {
                if (n != 0.0f) {
                    this.sol[i][j] /= n;
                }
            }
            for (int k = i + 1; k <= this.fila; ++k) {
                final float n2 = this.sol[k][i] * -1.0f;
                for (int l = 0; l <= this.col; ++l) {
                    this.sol[k][l] += this.sol[i][l] * n2;
                }
            }
        }
    }
    
    public void seidel() {
        for (int i = 0; i <= this.col; ++i) {
            this.sol[0][i] = 0.0f;
        }
        for (int j = 1; j <= 6; ++j) {
            for (int k = 0; k < this.col; ++k) {
                float n = 0.0f;
                for (int l = 0; l < this.col; ++l) {
                    if (k != l) {
                        n += this.mat1[k][l] * this.sol[j - 1][l] * -1.0f;
                    }
                }
                this.sol[j - 1][k] = (n + this.mat1[k][this.col]) / this.mat1[k][k];
                this.sol[j][k] = this.sol[j - 1][k];
            }
            this.top = j;
        }
    }
    
    public void jacob() {
        for (int i = 0; i <= this.col; ++i) {
            this.sol[0][i] = 0.0f;
        }
        for (int j = 1; j <= 6; ++j) {
            for (int k = 0; k < this.col; ++k) {
                float n = 0.0f;
                for (int l = 0; l < this.col; ++l) {
                    if (k != l) {
                        n += this.mat1[k][l] * this.sol[j - 1][l] * -1.0f;
                    }
                }
                this.sol[j][k] = (n + this.mat1[k][this.col]) / this.mat1[k][k];
            }
            this.top = j;
        }
    }
    
    public void gaussj() {
        this.sol = this.mat1;
        for (int i = 0; i <= this.fila; ++i) {
            final float n = this.sol[i][i];
            for (int j = 0; j <= this.col; ++j) {
                if (n != 0.0f) {
                    this.sol[i][j] /= n;
                }
            }
            for (int k = 0; k <= this.fila; ++k) {
                if (k != i) {
                    final float n2 = this.sol[k][i] * -1.0f;
                    for (int l = 0; l <= this.col; ++l) {
                        this.sol[k][l] += this.sol[i][l] * n2;
                    }
                }
            }
        }
    }
    
    public mt() {
        this.mat1 = new float[50][50];
        this.sol = new float[50][50];
    }
}
