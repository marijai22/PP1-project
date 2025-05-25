// generated with ast extension for cup
// version 0.8
// 12/4/2025 22:8:6


package rs.ac.bg.etf.pp1.ast;

public class NeedVarDeclList_v extends NeedVarDeclList {

    private NeedVarDeclList NeedVarDeclList;
    private VarDeclList VarDeclList;

    public NeedVarDeclList_v (NeedVarDeclList NeedVarDeclList, VarDeclList VarDeclList) {
        this.NeedVarDeclList=NeedVarDeclList;
        if(NeedVarDeclList!=null) NeedVarDeclList.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
    }

    public NeedVarDeclList getNeedVarDeclList() {
        return NeedVarDeclList;
    }

    public void setNeedVarDeclList(NeedVarDeclList NeedVarDeclList) {
        this.NeedVarDeclList=NeedVarDeclList;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NeedVarDeclList!=null) NeedVarDeclList.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NeedVarDeclList!=null) NeedVarDeclList.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NeedVarDeclList!=null) NeedVarDeclList.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NeedVarDeclList_v(\n");

        if(NeedVarDeclList!=null)
            buffer.append(NeedVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NeedVarDeclList_v]");
        return buffer.toString();
    }
}
