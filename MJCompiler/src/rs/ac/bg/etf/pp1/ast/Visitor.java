// generated with ast extension for cup
// version 0.8
// 12/4/2025 22:8:6


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(ReturnType ReturnType);
    public void visit(Mulop Mulop);
    public void visit(Relop Relop);
    public void visit(CondTermList CondTermList);
    public void visit(StatementList StatementList);
    public void visit(FactorList FactorList);
    public void visit(ConstVarDeclList ConstVarDeclList);
    public void visit(Addop Addop);
    public void visit(MulOpFact MulOpFact);
    public void visit(ActParMore ActParMore);
    public void visit(ParamsAct ParamsAct);
    public void visit(Factor Factor);
    public void visit(CondFactList CondFactList);
    public void visit(Designator Designator);
    public void visit(Condition Condition);
    public void visit(DesignExpMore DesignExpMore);
    public void visit(WhileParams WhileParams);
    public void visit(ElseStatement ElseStatement);
    public void visit(ExpOrAct ExpOrAct);
    public void visit(FormParsMore FormParsMore);
    public void visit(VarDeclList VarDeclList);
    public void visit(Expr Expr);
    public void visit(ConstDeclMore ConstDeclMore);
    public void visit(NeedVarDeclList NeedVarDeclList);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(Const Const);
    public void visit(CondDesign CondDesign);
    public void visit(Statement Statement);
    public void visit(VarDecl VarDecl);
    public void visit(VarDeclMore VarDeclMore);
    public void visit(CondFact CondFact);
    public void visit(FormPar FormPar);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(FormPars FormPars);
    public void visit(AddopTermList AddopTermList);
    public void visit(Setop Setop);
    public void visit(Relop_le Relop_le);
    public void visit(Relop_lt Relop_lt);
    public void visit(Relop_ge Relop_ge);
    public void visit(Relop_gt Relop_gt);
    public void visit(Relop_neq Relop_neq);
    public void visit(Relop_eq Relop_eq);
    public void visit(Mulop_rem Mulop_rem);
    public void visit(Mulop_div Mulop_div);
    public void visit(Mulop_mul Mulop_mul);
    public void visit(Addop_minus Addop_minus);
    public void visit(Addop_plus Addop_plus);
    public void visit(Assignop Assignop);
    public void visit(DesignatorName DesignatorName);
    public void visit(Designator_arr Designator_arr);
    public void visit(Designator_v Designator_v);
    public void visit(FactorList_expr FactorList_expr);
    public void visit(FactorList_new_coll FactorList_new_coll);
    public void visit(FactorList_b FactorList_b);
    public void visit(FactorList_c FactorList_c);
    public void visit(FactorList_n FactorList_n);
    public void visit(FactorList_meth FactorList_meth);
    public void visit(FactorList_var FactorList_var);
    public void visit(Factor_f Factor_f);
    public void visit(Factor_m Factor_m);
    public void visit(MulOpFact_mf MulOpFact_mf);
    public void visit(MulOpFact_f MulOpFact_f);
    public void visit(Term Term);
    public void visit(AddopTermList_at AddopTermList_at);
    public void visit(AddopTermList_t AddopTermList_t);
    public void visit(Expr_d Expr_d);
    public void visit(Expr_a Expr_a);
    public void visit(Param Param);
    public void visit(ActParMore_e ActParMore_e);
    public void visit(ActParMore_comma ActParMore_comma);
    public void visit(ActParNewList ActParNewList);
    public void visit(ParamsAct_e ParamsAct_e);
    public void visit(ParamsAct_rec ParamsAct_rec);
    public void visit(DesignatorStatement_dadsd DesignatorStatement_dadsd);
    public void visit(DesignatorStatement_dec DesignatorStatement_dec);
    public void visit(DesignatorStatement_inc DesignatorStatement_inc);
    public void visit(DesignatorStatement_dp DesignatorStatement_dp);
    public void visit(DesignatorStatement_err DesignatorStatement_err);
    public void visit(DesignatorStatement_ass DesignatorStatement_ass);
    public void visit(CondFact_ere CondFact_ere);
    public void visit(CondFact_e CondFact_e);
    public void visit(CondFactList_andc CondFactList_andc);
    public void visit(CondFactList_c CondFactList_c);
    public void visit(CondTerm CondTerm);
    public void visit(CondTermList_orc CondTermList_orc);
    public void visit(CondTermList_c CondTermList_c);
    public void visit(Condition_err Condition_err);
    public void visit(Condition_c Condition_c);
    public void visit(ElseNonTerm ElseNonTerm);
    public void visit(ElseStatement_e ElseStatement_e);
    public void visit(ElseStatement_els ElseStatement_els);
    public void visit(WhileNonterminal WhileNonterminal);
    public void visit(DoNonterminal DoNonterminal);
    public void visit(Statement_st Statement_st);
    public void visit(Statement_dwcd Statement_dwcd);
    public void visit(Statement_dwc Statement_dwc);
    public void visit(Statement_dwe Statement_dwe);
    public void visit(Statement_prc Statement_prc);
    public void visit(Statement_pre Statement_pre);
    public void visit(Statement_rd Statement_rd);
    public void visit(Statement_res Statement_res);
    public void visit(Statement_rs Statement_rs);
    public void visit(Statement_cn Statement_cn);
    public void visit(Statement_br Statement_br);
    public void visit(Statement_if Statement_if);
    public void visit(Statement_ds Statement_ds);
    public void visit(Label Label);
    public void visit(StatementList_e StatementList_e);
    public void visit(StatementList_s StatementList_s);
    public void visit(FormParsMore_e FormParsMore_e);
    public void visit(FormParsMore_c FormParsMore_c);
    public void visit(FormPar_var FormPar_var);
    public void visit(FormPar_arr FormPar_arr);
    public void visit(FormPars_e FormPars_e);
    public void visit(FormPars_errc FormPars_errc);
    public void visit(FormPars_errr FormPars_errr);
    public void visit(FormPars_fmore FormPars_fmore);
    public void visit(NeedVarDeclList_e NeedVarDeclList_e);
    public void visit(NeedVarDeclList_v NeedVarDeclList_v);
    public void visit(ReturnType_v ReturnType_v);
    public void visit(ReturnType_t ReturnType_t);
    public void visit(MethodSignature MethodSignature);
    public void visit(MethodDecl MethodDecl);
    public void visit(MethodDeclList_e MethodDeclList_e);
    public void visit(MethodDeclList_m MethodDeclList_m);
    public void visit(Type Type);
    public void visit(VarDeclMore_e VarDeclMore_e);
    public void visit(VarDeclMore_v VarDeclMore_v);
    public void visit(VarDecl_arr VarDecl_arr);
    public void visit(VarDecl_var VarDecl_var);
    public void visit(VarDeclList_errs VarDeclList_errs);
    public void visit(VarDeclList_errc VarDeclList_errc);
    public void visit(VarDeclList_v VarDeclList_v);
    public void visit(Const_b Const_b);
    public void visit(Const_c Const_c);
    public void visit(Const_n Const_n);
    public void visit(ConstDeclMore_e ConstDeclMore_e);
    public void visit(ConstDeclMore_comma ConstDeclMore_comma);
    public void visit(ConstDecl ConstDecl);
    public void visit(ConstDeclList ConstDeclList);
    public void visit(ConVarDecList_e ConVarDecList_e);
    public void visit(ConVarDecList_var ConVarDecList_var);
    public void visit(ConVarDecList_con ConVarDecList_con);
    public void visit(ProgramName ProgramName);
    public void visit(Program Program);

}
