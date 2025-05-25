// generated with ast extension for cup
// version 0.8
// 12/4/2025 22:8:6


package rs.ac.bg.etf.pp1.ast;

public class ParamsAct_e extends ParamsAct {

    private ActParNewList ActParNewList;

    public ParamsAct_e (ActParNewList ActParNewList) {
        this.ActParNewList=ActParNewList;
        if(ActParNewList!=null) ActParNewList.setParent(this);
    }

    public ActParNewList getActParNewList() {
        return ActParNewList;
    }

    public void setActParNewList(ActParNewList ActParNewList) {
        this.ActParNewList=ActParNewList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActParNewList!=null) ActParNewList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParNewList!=null) ActParNewList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParNewList!=null) ActParNewList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ParamsAct_e(\n");

        if(ActParNewList!=null)
            buffer.append(ActParNewList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ParamsAct_e]");
        return buffer.toString();
    }
}
