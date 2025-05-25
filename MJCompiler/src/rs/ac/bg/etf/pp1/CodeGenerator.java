package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;
	
	public int getmainPc() {
		return this.mainPc;
	}
	
	
	private void initializePredeclaredMethods() {
        // 'ord' and 'chr' are the same code.
        Obj ordMethod = Tab.find("ord");
        Obj chrMethod = Tab.find("chr");
        ordMethod.setAdr(Code.pc);
        chrMethod.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.exit);
        Code.put(Code.return_);
 
        Obj lenMethod = Tab.find("len");
        lenMethod.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.arraylength);
        Code.put(Code.exit);
        Code.put(Code.return_);
 
    }
	
	CodeGenerator() {
		this.initializePredeclaredMethods();
	}
	
	
	
	/*METHOD DECLARATIONS*/
	
	@Override
	public void visit(ReturnType_t returnType_t) {
		returnType_t.obj.setAdr(Code.pc);
		
		Code.put(Code.enter);
		Code.put(returnType_t.obj.getLevel()); //b1 - broj formalnih parametara
		Code.put(returnType_t.obj.getLocalSymbols().size()); // b2 - locals (broj formalnih i lokalnih parametara)
	}
	
	@Override
	public void visit(ReturnType_v returnType_v) {
		returnType_v.obj.setAdr(Code.pc);
		if(returnType_v.getI1().equalsIgnoreCase("main")) {
			this.mainPc = Code.pc;
		}
		
		Code.put(Code.enter);
		Code.put(returnType_v.obj.getLevel());
		Code.put(returnType_v.obj.getLocalSymbols().size());
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	
	/*STATEMENTS*/
	
	@Override
	public void visit(Statement_pre statement_pre) {
		Code.loadConst(0);
		if(statement_pre.getExpr().struct.equals(Tab.charType)) {
			Code.put(Code.bprint);
		}
		else {
			Code.put(Code.print);
		}
	}
	
	@Override
	public void visit(Statement_prc statement_prc) {
		Code.loadConst(statement_prc.getN2());
		if(statement_prc.getExpr().struct.equals(Tab.charType)) {
			Code.put(Code.bprint);
		}
		else {
			Code.put(Code.print);
		}
	}
	
	@Override
	public void visit(Statement_rs statement_rs) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	@Override
	public void visit(Statement_res statement_res) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	@Override
	public void visit(Statement_rd statement_rd) {
		if(statement_rd.getDesignator().obj.getType().equals(Tab.charType)) {
			Code.put(Code.bread);
		}
		else {
			Code.put(Code.read);
		}
		Code.store(statement_rd.getDesignator().obj);
	}
	
	
	//Designator Statements
	@Override
	public void visit(DesignatorStatement_ass designatorStatement_ass) {
		Code.store(designatorStatement_ass.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorStatement_dp designatorStatement_dp) {
		int offset = designatorStatement_dp.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		
		if(!designatorStatement_dp.getDesignator().obj.getType().equals(Tab.noType)) {
			Code.put(Code.pop);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_inc designatorStatement_inc) {
		if(designatorStatement_inc.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2); // za dupliranje poslednje 2 vrednosti
		}
		Code.load(designatorStatement_inc.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorStatement_inc.getDesignator().obj);
	}
	
	@Override
	public void visit(DesignatorStatement_dec designatorStatement_dec) {
		if(designatorStatement_dec.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2); // za dupliranje poslednje 2 vrednosti
		}
		Code.load(designatorStatement_dec.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designatorStatement_dec.getDesignator().obj);
	}
	
	
	/*FACTOR*/
	
	
	@Override
	public void visit(FactorList_n factorList_n) {
		Code.loadConst(factorList_n.getN1());
	}
	
	@Override
	public void visit(FactorList_c factorList_c) {
		Code.loadConst(factorList_c.getC1());
	}
	
	@Override
	public void visit(FactorList_b factorList_b) {
		Code.loadConst(factorList_b.getB1());
	}
	
	@Override
	public void visit(FactorList_var factorList_var) {
		Code.load(factorList_var.getDesignator().obj);
	}
	
	
	@Override
	public void visit(FactorList_new_coll factorList_new_coll) {
		Code.put(Code.newarray);
		if(factorList_new_coll.getType().struct.equals(Tab.intType)) {
			Code.put(1);
		}
		else {
			Code.put(0);
		}
	}
	
	
	@Override
	public void visit(FactorList_meth factorList_meth) {
		int offset = factorList_meth.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	
	/*EXPR*/
	@Override
	public void visit(AddopTermList_at addopTermList_at) {
		if(addopTermList_at.getAddop() instanceof Addop_plus) {
			Code.put(Code.add);
		}
		else {
			Code.put(Code.sub);
		}
	}
	
	@Override
	public void visit(MulOpFact_mf mulOpFact_mf) {
		if(mulOpFact_mf.getMulop() instanceof Mulop_mul) {
			Code.put(Code.mul);
		}
		else if (mulOpFact_mf.getMulop() instanceof Mulop_div){
			Code.put(Code.div);
		}
		else {
			Code.put(Code.rem);
		}
	}
	
	@Override
	public void visit(Factor_m factor_m) {
		Code.put(Code.neg);
	}
	
	/*DESIGNATOR*/
	
	@Override
	public void visit(DesignatorName designatorName) {
		Code.load(designatorName.obj);
	}
	
	
	//Condition
	
	private Stack<Integer> skipCondFact = new Stack<>();
	private Stack<Integer> skipCondition = new Stack<>();
	private Stack<Integer> skipThen = new Stack<>();
	private Stack<Integer> skipElse = new Stack<>();
	
	private int relOpCode(Relop relop) {
		if(relop instanceof Relop_eq)
			return Code.eq;
		else if(relop instanceof Relop_neq)
			return Code.ne;
		else if(relop instanceof Relop_gt)
			return Code.gt;
		else if(relop instanceof Relop_ge)
			return Code.ge;
		else if(relop instanceof Relop_lt)
			return Code.lt;
		else
			return Code.le;
	}
	
	@Override
	public void visit(CondFact_e condFact_e) {
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0); // netacna
		skipCondFact.push(Code.pc - 2); // fixup-ujemo kada dodje sledeci OR, jer ako je nit bila netacna, ona treba da skoci na sledeci OR
		// tacna
	}
	
	@Override
	public void visit(CondFact_ere condFact_ere) {
		Code.putFalseJump(relOpCode(condFact_ere.getRelop()), 0); // netacna; iskocicemo ako uslov iz relOpCode-a nije ispunjen
		skipCondFact.push(Code.pc - 2);
		// tacna
	}

	@Override
	public void visit(CondTerm condTerm) {
		// tacna
		Code.putJump(0); // tacne bacamo na THEN
		skipCondition.push(Code.pc - 2);
		// ovde vracamo netacne
		while(!skipCondFact.empty()) {
			Code.fixup(skipCondFact.pop());
		}
		// netacne
	}
	
	
	@Override
	public void visit(Condition_c condition_c) {
		// netacni
		Code.putJump(0); // netacne bacamo na ELSE
		skipThen.push(Code.pc - 2);
		// THEN
		while(!skipCondition.empty()) {
			Code.fixup(skipCondition.pop());
		}
		// tacne
		
	}
	
	
	@Override
	public void visit(ElseStatement_e elseStatement_e) { // else grane nije bilo
		// tacne
		Code.fixup(skipThen.pop());
		// tacne i netacne
	}
	
	@Override
	public void visit(ElseNonTerm elseNonTerm) { 
		// tacne
		Code.putJump(0); // tacne bacamo nakon ELSE-a
		skipElse.push(Code.pc - 2);
		Code.fixup(skipThen.pop());
		// netacne
	}

	@Override
	public void visit(ElseStatement_els elseStatement_els) { 
		// netacne
		Code.fixup(skipElse.pop()); // vracamo tacne koje su preskocile ELSE
		// netacne + tacne
	}
	
	
	
	// Do-While
	private Stack<Integer> doBegin = new Stack<>();
	
	@Override
	public void visit(DoNonterminal doNonterminal) {
		doBegin.push(Code.pc);
		breakJumps.push(new ArrayList<Integer>());
		continueJumps.push(new ArrayList<Integer>());
	}
	
	
	@Override
	public void visit(Statement_dwc statement_dwc) {
		Code.putJump(doBegin.pop()); // tacne bacamo na pocetak DO-a
		Code.fixup(skipThen.pop()); // netacne bacamo na kraj petlje
		
		while(!breakJumps.peek().isEmpty()) {
			Code.fixup(breakJumps.peek().remove(0));
		}
		breakJumps.pop();
	}
	
	
	@Override
	public void visit(Statement_dwe statement_dwe) {
		Code.putJump(doBegin.pop()); // sve uvek bacamo na novi pocetak DO-a
		
		while(!breakJumps.peek().isEmpty()) {
			Code.fixup(breakJumps.peek().remove(0));
		}
		breakJumps.pop();
	}
	
	
	@Override
	public void visit(Statement_dwcd statement_dwcd) {
		Code.putJump(doBegin.pop()); // tacne bacamo na pocetak DO-a
		Code.fixup(skipThen.pop()); // netacne bacamo na kraj petlje
		
		while(!breakJumps.peek().isEmpty()) {
			Code.fixup(breakJumps.peek().remove(0));
		}
		breakJumps.pop();
	}
	
	
	// Break, Continue
	
	private Stack<List<Integer>> breakJumps = new Stack<>();
	private Stack<List<Integer>> continueJumps = new Stack<>();
	
	@Override
	public void visit(Statement_br statement_br) {
		Code.putJump(0);
		breakJumps.peek().add(Code.pc - 2);
	}
	
	@Override
	public void visit(Statement_cn statement_cn) {
		Code.putJump(0);
		continueJumps.peek().add(Code.pc - 2);
	}
	
	@Override
	public void visit(WhileNonterminal whileNonterminal) {
		while(!continueJumps.peek().isEmpty()) {
			Code.fixup(continueJumps.peek().remove(0));
		}
		continueJumps.pop();
	}
	
}
