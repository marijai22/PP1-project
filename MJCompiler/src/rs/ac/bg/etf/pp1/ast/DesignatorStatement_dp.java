// generated with ast extension for cup
// version 0.8
// 12/4/2025 22:8:6


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatement_dp extends DesignatorStatement {

    private Designator Designator;
    private ParamsAct ParamsAct;

    public DesignatorStatement_dp (Designator Designator, ParamsAct ParamsAct) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.ParamsAct=ParamsAct;
        if(ParamsAct!=null) ParamsAct.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public ParamsAct getParamsAct() {
        return ParamsAct;
    }

    public void setParamsAct(ParamsAct ParamsAct) {
        this.ParamsAct=ParamsAct;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(ParamsAct!=null) ParamsAct.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(ParamsAct!=null) ParamsAct.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(ParamsAct!=null) ParamsAct.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatement_dp(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ParamsAct!=null)
            buffer.append(ParamsAct.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatement_dp]");
        return buffer.toString();
    }
}
