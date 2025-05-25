package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.ac.bg.etf.pp1.SStruct;

public class SemAnalyser extends VisitorAdaptor {
	
	private boolean errorDetected = false;
	Logger log = Logger.getLogger(getClass());
	private Obj currentProgam;
	private Struct currentType;
	private int constant;
	private Struct constantType;
	private Struct boolType = Tab.find("bool").getType();
	private Obj mainMethod;
	private Integer mainCounter = 0;
	private Obj currentMethod;
	private boolean returnHappend;
	private Struct returnType;
	private int loopCounter = 0;
	int nVars;
	
	//private Struct setType = Tab.find("set").getType();  // za Array smo uvek pomocu kind-a proveravali tip


	/* LOG MESSAGES */
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected  = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
	
	/* SEMANTIC PASS CODE */

	@Override
	public void visit(ProgramName programName) {
		currentProgam = Tab.insert(Obj.Prog, programName.getI1(), Tab.noType);
		Tab.openScope();
	}
	
	@Override
	public void visit(Program program) {
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(currentProgam);
		Tab.closeScope();
		currentProgam = null;
		
		if(mainMethod == null || mainMethod.getLevel() > 0 || mainCounter > 1) {
			report_error("Program nema adekvatnu main metodu(dvostruka definicija, povratna vrednost nije int ili postoje argumenti).", program);
		}
		
		mainCounter = 0;
	}
	
	/* CONST DECLARATIONS */
	
	@Override
	public void visit(ConstDecl conDecl) {
		Obj conObj = Tab.find(conDecl.getI1());
		if(conObj != Tab.noObj) {
			report_error("Dvostruka definicija konstante: " + conDecl.getI1(), conDecl);
		}
		else {
			if(constantType.assignableTo(currentType)) {
				conObj = Tab.insert(Obj.Con, conDecl.getI1(), currentType);
				conObj.setAdr(constant);
			}
			else {
				report_error("Neadekvatna dodela konstanti: " + conDecl.getI1(), conDecl);
			}
		}
	}
	
	@Override
	public void visit(Const_n const_n) {
		constant = const_n.getN1();
		constantType = Tab.intType;
	}
	
	@Override
	public void visit(Const_c const_c) {
		constant = const_c.getC1();
		constantType = Tab.charType;
	}
	
	@Override
	public void visit(Const_b const_b) {
		constant = const_b.getB1();
		constantType = boolType;
	}
	
	
	
	/* VAR DECLARATIONS */
	
	@Override
	public void visit(VarDecl_var varDecl_var) {
		Obj varObj = null;
		if(currentMethod == null)
			varObj = Tab.find(varDecl_var.getI1());
		else
			varObj = Tab.currentScope().findSymbol(varDecl_var.getI1());
		
		if(varObj == null || varObj == Tab.noObj) {
			/*if(currentType.getKind() == SStruct.Set) {
				varObj = Tab.insert(Obj.Var, varDecl_var.getI1(), new SStruct(SStruct.Set, Tab.intType));
			}
			else{
				varObj = Tab.insert(Obj.Var, varDecl_var.getI1(), currentType);
			}*/
			varObj = Tab.insert(Obj.Var, varDecl_var.getI1(), currentType);
		}
		else{
			report_error("Dvostruka definicija konstante: " + varDecl_var.getI1(), varDecl_var);
		}
	}
	
	@Override
	public void visit(VarDecl_arr varDecl_arr) {
		Obj varObj = null;
		if(currentMethod == null)
			varObj = Tab.find(varDecl_arr.getI1());
		else
			varObj = Tab.currentScope().findSymbol(varDecl_arr.getI1());
		
		if(varObj == null || varObj == Tab.noObj) {
			varObj = Tab.insert(Obj.Var, varDecl_arr.getI1(), new Struct(Struct.Array, currentType));
		}
		else{
			report_error("Dvostruka definicija konstante: " + varDecl_arr.getI1(), varDecl_arr);
		}
	}
	
	
	
	/*METHOD DECLARATIONS*/
	
	@Override
	public void visit(ReturnType_v returnType_v) {
		// generalno ako ima vise main-ova bice nam najbitnije da ukacimo da je dvostruka definicija
		// hvatacemo i da li je neadekvatna za slucaj da nemamo uopste main ili da nam ima vise argumenata ako je samo jedan; ako ima vise main metoda, necemo za svaku
		// pisati da je neadekvatna, jer ce nam biti dovoljno da se ispsiuje dvostruka definicija, a ako je jedna sa void i argumentima, tu nam je znacajno da kazemo da je nepravilna
		
		if(returnType_v.getI1().equalsIgnoreCase("main") && mainMethod != null ) { // ako je u pitanju main, ali mi vec imamo main metodu
			returnType_v.obj = currentMethod = Tab.insert(Obj.Meth, returnType_v.getI1() + mainCounter.toString(), Tab.noType); // zbog potencijalnih parametara svakako dodajemo u cvoe, ali sa izmenjenim nazivom da se ne bi poklopili
			Tab.openScope();
			mainCounter++; // povecavamo brojac
		}
		else {
			returnType_v.obj = currentMethod = Tab.insert(Obj.Meth, returnType_v.getI1(), Tab.noType); // ako je prva main metoda ili neka druga void metoda samo je ubacujemo
			Tab.openScope();
		}
		
		if(returnType_v.getI1().equalsIgnoreCase("main") && mainMethod == null) { // ako je prva main metoda, na nju kacimo "pokazivac" i uvecavamo mainMethod
			mainMethod  = currentMethod;
			mainCounter++;
		}
			
	}
	
	@Override
	public void visit(ReturnType_t returnType_t) { 
		if(returnType_t.getI2().equalsIgnoreCase("main")) { // ako je main metoda sa int-om, dovoljno je da zabelezimo da je postojala, ali ne dodeljujemo mainMethod pokazivac
			returnType_t.obj = currentMethod = Tab.insert(Obj.Meth, returnType_t.getI2() + mainCounter.toString(), currentType);
			Tab.openScope();
			mainCounter++; 
		}
		else {
			returnType_t.obj = currentMethod = Tab.insert(Obj.Meth, returnType_t.getI2(), currentType); 
			Tab.openScope();
		}
		
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		
		if(currentMethod.getType() != Tab.noType && !returnHappend) { // ako je type method() i nije se desio return
			report_error("Ne postoji return naredba unutar ne-void metode " + currentMethod.getName(), methodDecl);
		}
		else if(returnHappend && !returnType.equals(currentMethod.getType())) { // za bilo koju metodu da li se poklapa tip return-a sa tipom metode
			report_error("Return naredba ne vraca adekvatan tip u metodi " + currentMethod.getName(), methodDecl); // ako je main bez return-a i to je skroz okej(nece uci ni u jednu granu)
		}
		currentMethod = null;
		returnHappend = false;
	}
	
	
	/* FORMPAR DECLARATIONS */
	
	@Override
	public void visit(FormPar_var formPar_var) {
		Obj varObj = null;
		if(currentMethod == null)
			report_error("Semanticka greska. [FormPar_var]", formPar_var);
		else
			varObj = Tab.currentScope().findSymbol(formPar_var.getI2());
		
		if(varObj == null || varObj == Tab.noObj) {
			varObj = Tab.insert(Obj.Var, formPar_var.getI2(), currentType);
			varObj.setFpPos(1);
			currentMethod.setLevel(currentMethod.getLevel() + 1);
		}
		else{
			report_error("Dvostruka definicija formalnog parametra: " + formPar_var.getI2(), formPar_var);
		}
	}
	
	@Override
	public void visit(FormPar_arr formPar_arr) {
		Obj varObj = null;
		if(currentMethod == null)
			report_error("Semanticka greska. [FormPar_var]", formPar_arr);
		else
			varObj = Tab.currentScope().findSymbol(formPar_arr.getI2());
		
		if(varObj == null || varObj == Tab.noObj) {
			varObj = Tab.insert(Obj.Var, formPar_arr.getI2(), new Struct(Struct.Array, currentType));
			varObj.setFpPos(1);
			currentMethod.setLevel(currentMethod.getLevel() + 1);
		}
		else{
			report_error("Dvostruka definicija formalnog parametra: " + formPar_arr.getI2(), formPar_arr);
		}
	}
	
	
	
	
	@Override
	public void visit(Type type) {
		Obj typeObj = Tab.find(type.getI1());
		if(typeObj == Tab.noObj) {
			report_error("Nepostojeci tip podatka: " + type.getI1(), type);
			type.struct = currentType = Tab.noType;
		}
		else if(typeObj.getKind() != Obj.Type) {
			report_error("Neadekvatan tip podatka: " + type.getI1(), type);
			type.struct = currentType = Tab.noType;
		}
		else
			type.struct = currentType = typeObj.getType();
	}
	
	
	/*CONTECST CONDITIONS*/
	
	// Designator
	@Override
	public void visit(Designator_v designator_v) {
		Obj varObj = Tab.find(designator_v.getI1());
		if(varObj == Tab.noObj) {
			report_error("Pristup nedefinisanoj promenljivi: " + designator_v.getI1(), designator_v);
			designator_v.obj = Tab.noObj;
		}
		else if(varObj.getKind() != Obj.Var && varObj.getKind() != Obj.Con && varObj.getKind() != Obj.Meth) {
			report_error("Neadekvatna promenljiva: " + designator_v.getI1(), designator_v);
			designator_v.obj = Tab.noObj;
		}
		else {
			designator_v.obj = varObj;
		}
	}
	
	@Override
	public void visit(DesignatorName designatorName) {
		Obj arrObj = Tab.find(designatorName.getI1());
		if(arrObj == Tab.noObj) {
			report_error("Pristup nedefinisanoj promenljivi niza : " + designatorName.getI1(), designatorName);
			designatorName.obj = Tab.noObj;
		}
		else if(arrObj.getKind() != Obj.Var || arrObj.getType().getKind() != Struct.Array) {
			report_error("Neadekvatna promenljiva niza : " + designatorName.getI1(), designatorName);
			designatorName.obj = Tab.noObj;
		}
		else {
			designatorName.obj = arrObj;
		}
	}
	
	@Override
	public void visit(Designator_arr designator_arr) {
		Obj arrObj = designator_arr.getDesignatorName().obj;
		if(arrObj == Tab.noObj) {
			designator_arr.obj = Tab.noObj;
		}
		else if(!designator_arr.getExpr().struct.equals(Tab.intType)) {
			report_error("Indeksiranje sa ne int vrednoscu. [Designator_arr] ", designator_arr);
			designator_arr.obj = Tab.noObj;
		}
		else {
			designator_arr.obj = new Obj(Obj.Elem, arrObj.getName() + "[$]", arrObj.getType().getElemType());
			/*report_info("Pristup elementu niza:" + arrObj.getName() +  "[Kind: " + designator_arr.obj.getKind() + "]" 
					+ "[Type: " + designator_arr.obj.getType().getKind() + "]" + "[Adr: " + designator_arr.obj.getAdr() + "]"  
					+ "[Level: " + designator_arr.obj.getLevel() + "]" + "[FpPos: " + designator_arr.obj.getFpPos() + "]", designator_arr);*/
		}
	}
	
	// FactorList
	@Override
	public void visit(FactorList_n factorList_n) {
		factorList_n.struct = Tab.intType;
	}
	
	@Override
	public void visit(FactorList_c factorList_c) {
		factorList_c.struct = Tab.charType;
	}
	
	@Override
	public void visit(FactorList_b factorList_b) {
		factorList_b.struct = boolType;
	}
	
	
	@Override
	public void visit(FactorList_var factorList_var) {
		factorList_var.struct = factorList_var.getDesignator().obj.getType(); 
	}
	
	@Override
	public void visit(FactorList_meth factorList_meth) {
		if(factorList_meth.getDesignator().obj.getKind() != Obj.Meth) { 
			report_error("Poziv neadekvatne metode " + factorList_meth.getDesignator().obj.getName(), factorList_meth);
			factorList_meth.struct = Tab.noType;
		}
		else {
			factorList_meth.struct = factorList_meth.getDesignator().obj.getType();
		
			List<Struct> fpList = new ArrayList<>();
			for(Obj local: factorList_meth.getDesignator().obj.getLocalSymbols()) {
				if(local.getKind() == Obj.Var && local.getLevel() == 1 && local.getFpPos() == 1) {
					fpList.add(local.getType());
				}
			}
			
			ActParsCounter apc = new ActParsCounter();
			factorList_meth.getParamsAct().traverseBottomUp(apc);
			
			List<Struct> apList = apc.finalActParList;
			
			try {
				if(fpList.size() != apList.size()) {
					throw new Exception("Greska: broj parametara");
				}
				
				for(int i = 0; i < fpList.size(); i++) {
					Struct fp = fpList.get(i);
					Struct ap = apList.get(i);
					if(!ap.assignableTo(fp)) {
						throw new Exception("Greska: tipovi parametara");
					}
				}
				
			}catch (Exception e) {
				report_error("[" + e.getMessage() + "] Nekompatibilnost stvarnih argumenata i formalnih parametara, pri pozivu metode: " + factorList_meth.getDesignator().obj.getName(), factorList_meth);
			}
		}
	}
	
	@Override
	public void visit(FactorList_new_coll factorList_new_coll) {
		if(!factorList_new_coll.getExpr().struct.equals(Tab.intType)) {
			report_error("Velicina niza nije int tipa. ", factorList_new_coll);
			factorList_new_coll.struct = Tab.noType;
		}
		else {
			factorList_new_coll.struct = new Struct(Struct.Array, currentType);
		}
	}
	
	
	@Override
	public void visit(FactorList_expr factorList_expr) {
		factorList_expr.struct = factorList_expr.getExpr().struct;
	}
	
	// Factor
	@Override
	public void visit(Factor_m factor_m) {
		if(factor_m.getFactorList().struct.equals(Tab.intType))
			factor_m.struct = Tab.intType;
		else {
			report_error("Negacija ne int vrednosti", factor_m);
			factor_m.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(Factor_f factor_f) {
		factor_f.struct = factor_f.getFactorList().struct;
	}
	
	
	//Expr
	@Override
	public void visit(MulOpFact_f mulOpFact_f) {
		mulOpFact_f.struct = mulOpFact_f.getFactor().struct;
	}
	
	@Override
	public void visit(MulOpFact_mf mulOpFact_mf) {
		Struct leftFactor = mulOpFact_mf.getMulOpFact().struct;
		Struct rightFactor = mulOpFact_mf.getFactor().struct;
		if(leftFactor.equals(Tab.intType) && rightFactor.equals(Tab.intType)) {
			mulOpFact_mf.struct = Tab.intType;
		}
		else {
			report_error("Mulop operacija sa ne int vrednostima. ", mulOpFact_mf);
			mulOpFact_mf.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(Term term) {
		term.struct = term.getMulOpFact().struct;
	}
	
	
	@Override
	public void visit(AddopTermList_t addopTermList_t) {
		addopTermList_t.struct = addopTermList_t.getTerm().struct;
	}
	
	@Override
	public void visit(AddopTermList_at addopTermList_at) {
		Struct leftFactor = addopTermList_at.getAddopTermList().struct;
		Struct rightFactor = addopTermList_at.getTerm().struct;
		if(leftFactor.equals(Tab.intType) && rightFactor.equals(Tab.intType)) {
			addopTermList_at.struct = Tab.intType;
		}
		else {
			report_error("Addop operacija sa ne int vrednostima. ", addopTermList_at);
			addopTermList_at.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(Expr_a expr_a) {
		expr_a.struct = expr_a.getAddopTermList().struct;
	}
	
	@Override
	public void visit(Expr_d expr_d) {
		Obj leftDesign = expr_d.getDesignator().obj;
		Obj rightDesign = expr_d.getDesignator1().obj;
		
		if(leftDesign.getKind() == Obj.Meth && leftDesign.getType() == Tab.intType && leftDesign.getLevel() == 1) {
			Obj param = null;
			
			for(Obj node: leftDesign.getLocalSymbols()) {
				if(node.getFpPos() == 1) {
					param = node;
					break;
				}
			}
			
			if(param != null && param.getType() == Tab.intType) {
				if(rightDesign.getType().getKind() == Struct.Array && rightDesign.getType().getElemType() == Tab.intType) {
					expr_d.struct = Tab.intType;
					return;
				}
				else {
					report_error("Desna strana map operacije mora biti niz celobrojnih vrednosti.", expr_d);
				}
			} 
			else {
				report_error("Levi designator u map operaciji mora biti funkcija sa jednim parametrom tipa int.", expr_d);
			}
		}
		else {
			report_error("Levi designator u map operaciji mora biti funkcija tipa int sa jednim parametrom.", expr_d);
		}
		
		expr_d.struct = Tab.noType;
	}
	
	
	
	// Designator Statements
	@Override
	public void visit(DesignatorStatement_ass designatorStatement_ass) {
		int kind = designatorStatement_ass.getDesignator().obj.getKind();
		if(kind != Obj.Var && kind != Obj.Elem) {
			report_error("Dodela u neadekvatnu promenljivu " + designatorStatement_ass.getDesignator().obj.getName(), designatorStatement_ass);
		}
		/*else if(designatorStatement_ass.getExpr().struct.equals(designatorStatement_ass.getDesignator().obj.getType())) {
			report_info("Ove dve promenljive su dodeljive", designatorStatement_ass); // NISU EQUALS ocigledno
		}*/
		else if(!designatorStatement_ass.getExpr().struct.assignableTo(designatorStatement_ass.getDesignator().obj.getType())) {
			//report_info("Tip desne dodele je " + designatorStatement_ass.getExpr().struct.getKind() + designatorStatement_ass.getExpr().struct.getElemType().getKind(), designatorStatement_ass);
			//report_info("Tip leve promenljive je " + designatorStatement_ass.getDesignator().obj.getType().getKind() + designatorStatement_ass.getDesignator().obj.getType().getElemType().getKind(), designatorStatement_ass);
			report_error("Neadekvatna dodela vrednosti u promenljivu " + designatorStatement_ass.getDesignator().obj.getName(), designatorStatement_ass);
		}
	}
	
	
	@Override
	public void visit(DesignatorStatement_inc designatorStatement_inc) {
		int kind = designatorStatement_inc.getDesignator().obj.getKind();
		if(kind != Obj.Var && kind != Obj.Elem) {
			report_error("Inkrement neadekvatne promenljive " + designatorStatement_inc.getDesignator().obj.getName(), designatorStatement_inc);
		}
		else if(!designatorStatement_inc.getDesignator().obj.getType().equals(Tab.intType)) {
			report_error("Inkrement ne int vrednosti " + designatorStatement_inc.getDesignator().obj.getName(), designatorStatement_inc);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_dec designatorStatement_dec) {
		int kind = designatorStatement_dec.getDesignator().obj.getKind();
		if(kind != Obj.Var && kind != Obj.Elem) {
			report_error("Dekrement neadekvatne promenljive " + designatorStatement_dec.getDesignator().obj.getName(), designatorStatement_dec);
		}
		else if(!designatorStatement_dec.getDesignator().obj.getType().equals(Tab.intType)) {
			report_error("Dekrement ne int vrednosti " + designatorStatement_dec.getDesignator().obj.getName(), designatorStatement_dec);
		}
	}
	
	
	@Override
	public void visit(DesignatorStatement_dp designatorStatement_dp) {
		if(designatorStatement_dp.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("Poziv neadekvatne metode " + designatorStatement_dp.getDesignator().obj.getName() , designatorStatement_dp);
		}
		else {
			List<Struct> fpList = new ArrayList<>();
			for(Obj local: designatorStatement_dp.getDesignator().obj.getLocalSymbols()) {
				if(local.getKind() == Obj.Var && local.getLevel() == 1 && local.getFpPos() == 1) {
					fpList.add(local.getType());
				}
			}
			
			ActParsCounter apc = new ActParsCounter();
			designatorStatement_dp.getParamsAct().traverseBottomUp(apc);
			
			List<Struct> apList = apc.finalActParList;
			
			try {
				if(fpList.size() != apList.size()) {
					throw new Exception("Greska: broj parametara");
				}
				
				for(int i = 0; i < fpList.size(); i++) {
					Struct fp = fpList.get(i);
					Struct ap = apList.get(i);
					if(!ap.assignableTo(fp)) {
						throw new Exception("Greska: tipovi parametara");
					}
				}
				
			}catch (Exception e) {
				report_error("[" + e.getMessage() + "] Nekompatibilnost stvarnih argumenata i formalnih parametara, pri pozivu metode: " + designatorStatement_dp.getDesignator().obj.getName(), designatorStatement_dp);
			}
		}
	}
	
	
	@Override
	public void visit(DesignatorStatement_dadsd designatorStatement_dadsd) {
		Struct designOne = designatorStatement_dadsd.getDesignator().obj.getType();
		Struct designTwo = designatorStatement_dadsd.getDesignator1().obj.getType();
		Struct designThree = designatorStatement_dadsd.getDesignator2().obj.getType();
		
		if(designOne.getKind() != SStruct.Set || designTwo.getKind() != SStruct.Set || designThree.getKind() != SStruct.Set) {
			report_error("Union operacija sa neadekvatnim promenljivama(nisu tipa set).", designatorStatement_dadsd);
		}
	}
	
	// Statements
	@Override
	public void visit(Statement_rd statement_rd) {
		int kind = statement_rd.getDesignator().obj.getKind();
		Struct type = statement_rd.getDesignator().obj.getType();
		
		if(kind != Obj.Var && kind != Obj.Elem) {
			report_error("Read operacija neadekvatne promenljive " + statement_rd.getDesignator().obj.getName(), statement_rd);
		}
		else if(!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType)) {
			report_error("Read operacija ne int/char/bool vrednosti " + statement_rd.getDesignator().obj.getName(), statement_rd);
		}
	}
	
	
	@Override
	public void visit(Statement_pre statement_pre) {
		Struct type = statement_pre.getExpr().struct;
		if(!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType) && type.getKind() != SStruct.Set) { // da li treba praviti new SStruct ili kao bool ga negde inicijalizovati
			report_error("Print operacija ne int/char/bool/set vrednosti ", statement_pre);										 // nisam sigurna sta je pametnije: kao za Array samo preko kind-a proveravati ili raditi ne SStruct
		}
	}
	
	@Override
	public void visit(Statement_prc statement_prc) {
		Struct type = statement_prc.getExpr().struct;
		if(!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType) && !type.equals(new SStruct(SStruct.Set))) {
			report_error("Print operacija ne int/char/bool/set vrednosti ", statement_prc);
		}
	}
	
	
	@Override
	public void visit(Statement_rs statement_rs) {
		returnType = Tab.noType;
		returnHappend = true;
		
	}
	
	
	@Override
	public void visit(Statement_res statement_res) {
		returnType = statement_res.getExpr().struct;
		returnHappend = true;
		
	}
	
	
	@Override
	public void visit(DoNonterminal doNonterminal) {
		loopCounter++;
	}
	
	
	@Override
	public void visit(Statement_dwe statement_dwe) {
		loopCounter--;
	}
	
	@Override
	public void visit(Statement_dwc statement_dwc) {
		loopCounter--;
	}
	
	@Override
	public void visit(Statement_dwcd statement_dwcd) {
		loopCounter--;
	}
	
	@Override
	public void visit(Statement_br statement_br) {
		if(loopCounter == 0) {
			report_error("Break naredba se nalazi van tela do-while petlje.", statement_br);
		}
	}
	
	@Override
	public void visit(Statement_cn statement_cn) {
		if(loopCounter == 0) {
			report_error("Continue naredba se nalazi van tela do-while petlje.", statement_cn);
		}
	}
	
	
	// Condition
	@Override
	public void visit(CondFact_e condFact_e) {
		if(!condFact_e.getExpr().struct.equals(boolType)) {
			report_error("Logicki operand nije bool tipa.", condFact_e);
			condFact_e.struct = Tab.noType;
		}
		else {
			condFact_e.struct = boolType;
		}
	}
	
	
	@Override
	public void visit(CondFact_ere condFact_ere) {
		Struct expr1 = condFact_ere.getExpr().struct;
		Struct expr2 = condFact_ere.getExpr1().struct;
		if(expr1.compatibleWith(expr2)) {
			if(expr1.isRefType() || expr2.isRefType()) {
				if(condFact_ere.getRelop() instanceof Relop_eq || condFact_ere.getRelop() instanceof Relop_neq) {
					condFact_ere.struct = boolType;
				}
				else {
					report_error("Poredjenje nizovskog tipa neadekvatnim relacionim operatorima", condFact_ere);
					condFact_ere.struct = Tab.noType;
				}
			}
			else {
				condFact_ere.struct = boolType;
			}
		}
		else {
			report_error("Logicki operandi nisu kompatibilni.", condFact_ere);
			condFact_ere.struct = Tab.noType;
		}
	}
	
	
	@Override
	public void visit(CondFactList_c condFactList_c) {
		condFactList_c.struct = condFactList_c.getCondFact().struct;
	}
	
	
	@Override
	public void visit(CondFactList_andc condFactList_andc) {
		Struct leftFactor = condFactList_andc.getCondFactList().struct;
		Struct rightFactor = condFactList_andc.getCondFact().struct;
		if(leftFactor.equals(boolType) && rightFactor.equals(boolType)) {
			condFactList_andc.struct = boolType;
		}
		else {
			report_error("And operacija sa ne bool vrednostima. ", condFactList_andc);
			condFactList_andc.struct = Tab.noType;
		}
	}
	
	
	@Override
	public void visit(CondTerm condTerm) {
		condTerm.struct = condTerm.getCondFactList().struct;
	}
	
	
	@Override
	public void visit(CondTermList_c condTermList_c) {
		condTermList_c.struct = condTermList_c.getCondTerm().struct;
	}
	
	
	@Override
	public void visit(CondTermList_orc condTermList_orc) {
		Struct leftFactor = condTermList_orc.getCondTermList().struct;
		Struct rightFactor = condTermList_orc.getCondTerm().struct;
		if(leftFactor.equals(boolType) && rightFactor.equals(boolType)) {
			condTermList_orc.struct = boolType;
		}
		else {
			report_error("Or operacija sa ne bool vrednostima. ", condTermList_orc);
			condTermList_orc.struct = Tab.noType;
		}
	}
	
	
	@Override
	public void visit(Condition_c condition_c) {
		condition_c.struct = condition_c.getCondTermList().struct;
		if(!condition_c.struct.equals(boolType)) {
			report_error("Uslov nije tipa bool.", condition_c);
		}
	}
	
}
