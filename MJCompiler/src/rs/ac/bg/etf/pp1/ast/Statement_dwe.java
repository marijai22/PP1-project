// generated with ast extension for cup
// version 0.8
// 12/4/2025 22:8:6


package rs.ac.bg.etf.pp1.ast;

public class Statement_dwe extends Statement {

    private DoNonterminal DoNonterminal;
    private Statement Statement;
    private WhileNonterminal WhileNonterminal;

    public Statement_dwe (DoNonterminal DoNonterminal, Statement Statement, WhileNonterminal WhileNonterminal) {
        this.DoNonterminal=DoNonterminal;
        if(DoNonterminal!=null) DoNonterminal.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.WhileNonterminal=WhileNonterminal;
        if(WhileNonterminal!=null) WhileNonterminal.setParent(this);
    }

    public DoNonterminal getDoNonterminal() {
        return DoNonterminal;
    }

    public void setDoNonterminal(DoNonterminal DoNonterminal) {
        this.DoNonterminal=DoNonterminal;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public WhileNonterminal getWhileNonterminal() {
        return WhileNonterminal;
    }

    public void setWhileNonterminal(WhileNonterminal WhileNonterminal) {
        this.WhileNonterminal=WhileNonterminal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DoNonterminal!=null) DoNonterminal.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(WhileNonterminal!=null) WhileNonterminal.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoNonterminal!=null) DoNonterminal.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(WhileNonterminal!=null) WhileNonterminal.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoNonterminal!=null) DoNonterminal.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(WhileNonterminal!=null) WhileNonterminal.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_dwe(\n");

        if(DoNonterminal!=null)
            buffer.append(DoNonterminal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(WhileNonterminal!=null)
            buffer.append(WhileNonterminal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_dwe]");
        return buffer.toString();
    }
}
