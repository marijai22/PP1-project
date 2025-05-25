package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class ActParsCounter extends VisitorAdaptor {

	List<Struct> finalActParList;
	
	Stack<List<Struct>> actParLists = new Stack<>();
	
	@Override
	public void visit(ActParNewList actParNewList) {
		actParLists.push(new ArrayList<>());
	}
	
	@Override
	public void visit(Param param) {
		actParLists.peek().add(param.getExpr().struct);
	}
	
	@Override
	public void visit(ParamsAct_rec paramsAct_rec) {
		finalActParList = actParLists.pop(); // svaki put ubacujemo poslednje napravljenu; poslednji put ce se ubaciti prva lista
	}
	
	@Override
	public void visit(ParamsAct_e paramsAct_e) {
		finalActParList = actParLists.pop(); 
	}
}
