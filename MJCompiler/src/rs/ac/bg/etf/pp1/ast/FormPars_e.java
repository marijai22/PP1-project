// generated with ast extension for cup
// version 0.8
// 12/4/2025 22:8:6


package rs.ac.bg.etf.pp1.ast;

public class FormPars_e extends FormPars {

    public FormPars_e () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormPars_e(\n");

        buffer.append(tab);
        buffer.append(") [FormPars_e]");
        return buffer.toString();
    }
}
