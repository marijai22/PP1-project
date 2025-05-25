// generated with ast extension for cup
// version 0.8
// 12/4/2025 22:8:6


package rs.ac.bg.etf.pp1.ast;

public class ParamsAct_rec extends ParamsAct {

    private ActParNewList ActParNewList;
    private Param Param;
    private ActParMore ActParMore;

    public ParamsAct_rec (ActParNewList ActParNewList, Param Param, ActParMore ActParMore) {
        this.ActParNewList=ActParNewList;
        if(ActParNewList!=null) ActParNewList.setParent(this);
        this.Param=Param;
        if(Param!=null) Param.setParent(this);
        this.ActParMore=ActParMore;
        if(ActParMore!=null) ActParMore.setParent(this);
    }

    public ActParNewList getActParNewList() {
        return ActParNewList;
    }

    public void setActParNewList(ActParNewList ActParNewList) {
        this.ActParNewList=ActParNewList;
    }

    public Param getParam() {
        return Param;
    }

    public void setParam(Param Param) {
        this.Param=Param;
    }

    public ActParMore getActParMore() {
        return ActParMore;
    }

    public void setActParMore(ActParMore ActParMore) {
        this.ActParMore=ActParMore;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActParNewList!=null) ActParNewList.accept(visitor);
        if(Param!=null) Param.accept(visitor);
        if(ActParMore!=null) ActParMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParNewList!=null) ActParNewList.traverseTopDown(visitor);
        if(Param!=null) Param.traverseTopDown(visitor);
        if(ActParMore!=null) ActParMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParNewList!=null) ActParNewList.traverseBottomUp(visitor);
        if(Param!=null) Param.traverseBottomUp(visitor);
        if(ActParMore!=null) ActParMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ParamsAct_rec(\n");

        if(ActParNewList!=null)
            buffer.append(ActParNewList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Param!=null)
            buffer.append(Param.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParMore!=null)
            buffer.append(ActParMore.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ParamsAct_rec]");
        return buffer.toString();
    }
}
