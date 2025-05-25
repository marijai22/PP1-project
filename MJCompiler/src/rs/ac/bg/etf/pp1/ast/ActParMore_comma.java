// generated with ast extension for cup
// version 0.8
// 12/4/2025 22:8:6


package rs.ac.bg.etf.pp1.ast;

public class ActParMore_comma extends ActParMore {

    private Param Param;
    private ActParMore ActParMore;

    public ActParMore_comma (Param Param, ActParMore ActParMore) {
        this.Param=Param;
        if(Param!=null) Param.setParent(this);
        this.ActParMore=ActParMore;
        if(ActParMore!=null) ActParMore.setParent(this);
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
        if(Param!=null) Param.accept(visitor);
        if(ActParMore!=null) ActParMore.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Param!=null) Param.traverseTopDown(visitor);
        if(ActParMore!=null) ActParMore.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Param!=null) Param.traverseBottomUp(visitor);
        if(ActParMore!=null) ActParMore.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActParMore_comma(\n");

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
        buffer.append(") [ActParMore_comma]");
        return buffer.toString();
    }
}
