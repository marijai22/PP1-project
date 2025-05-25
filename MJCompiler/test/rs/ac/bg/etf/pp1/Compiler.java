package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(Compiler.class);
		
		Reader br = null;
		try {
			File sourceCode = new File("test/program.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			// Formiranje AST
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //formiranje AST
	        
	        Program prog = (Program)(s.value); 
	        
			// Ispis AST
			log.info(prog.toString(""));
			log.info("=====================================================================");

			// Inicijalizacija tabele simbola
			Tab.init(); // nalazimo se idalje u universe opsegu
			Struct boolType = new Struct(Struct.Bool);
			Struct setType = new SStruct(SStruct.Set, Tab.intType); 
			
			Obj boolObj = Tab.insert(Obj.Type, "bool", boolType);
			Obj setObj = Tab.insert(Obj.Type, "set", setType);
			
			boolObj.setAdr(-1);
			boolObj.setLevel(-1);
			
			setObj.setAdr(-1);
			setObj.setLevel(-1);
			
			
			List<String> uni_methods = new ArrayList<>();
			uni_methods.add("chr");
			uni_methods.add("ord");
			uni_methods.add("len");
			
			for(String m: uni_methods) {
				for(Obj fp: Tab.find(m).getLocalSymbols()) {
					fp.setFpPos(1);
				}
			}
			
			Obj addObj;
			Obj addaAllObj;
			
			Tab.currentScope().addToLocals(addObj = new Obj(Obj.Meth, "add", Tab.noType, 0, 2));
			{
				Tab.openScope();
				Tab.currentScope.addToLocals(new Obj(Obj.Var, "a", new SStruct(SStruct.Set, Tab.intType), 0, 1)); // dodato da se zada i tip elemenata
				Tab.currentScope.addToLocals(new Obj(Obj.Var, "b", Tab.intType, 1, 1));
				addObj.setLocals(Tab.currentScope.getLocals());
				Tab.closeScope();
			}
			
			Tab.currentScope().addToLocals(addaAllObj = new Obj(Obj.Meth, "addAll", Tab.noType, 0, 2));
			{
				Tab.openScope();
				Tab.currentScope.addToLocals(new Obj(Obj.Var, "a", new SStruct(SStruct.Set, Tab.intType), 0, 1));
				Tab.currentScope.addToLocals(new Obj(Obj.Var, "b", new Struct(Struct.Array, Tab.intType), 1, 1));
				addaAllObj.setLocals(Tab.currentScope.getLocals());
				Tab.closeScope();
			}
			
			
			// Semanticka analiza
			SemAnalyser sa = new SemAnalyser();
			prog.traverseBottomUp(sa);
			
			
			// Ispis tabele simbola
			log.info("=====================================================================");
			Tab.dump();
			
			if(!p.errorDetected && sa.passed()){
				/*Generisanje koda*/
				File objFile = new File("test/program.obj");
				if(objFile.exists()) objFile.delete();
				
				CodeGenerator codeGenerator = new CodeGenerator();
				prog.traverseBottomUp(codeGenerator);
				Code.dataSize = sa.nVars;
				Code.mainPc = codeGenerator.getmainPc();
				Code.write(new FileOutputStream(objFile));
				
				
				log.info("Generisanje uspesno zavrseno!");
			}else{
				log.error("Parsiranje NIJE uspesno zavrseno!");
			}
			
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	
}
