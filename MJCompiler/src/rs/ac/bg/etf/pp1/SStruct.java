package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class SStruct extends Struct {
	
	public static final int Set = 8;
	private Struct myElemType = null;

	public SStruct(int kind) {
		super(kind);
	}

	public SStruct(int kind, Struct elemType) {
		super(kind, elemType); 
		
		if (kind == Set) {
            myElemType = elemType;
        }
		//super(kind, (kind == Set) ? Tab.intType : elemType);
		//super(kind == Set ? Set : kind, kind == Set ? Tab.intType : elemType);
	}

	public SStruct(int kind, SymbolDataStructure members) {
		super(kind, members);
	}

	// potrebno je predefinisati funkcije equals, compatibleWith, assignableTo
	
	@Override
	public boolean equals(Struct other) {
		// Ako su oba objekta tipa Set
	    if (this.getKind() == Set && other.getKind() == Set) {
	        return  this.getElemType().equals(other.getElemType());
	    }

	    // Ako je jedan objekat tipa Set, a drugi tipa Array
	    if (this.getKind() == Set && other.getKind() == Array) {
	        return this.getElemType().equals(other.getElemType());
	    }

	    // Ako je jedan objekat tipa Array, a drugi tipa Set
	    if (this.getKind() == Array && other.getKind() == Set) {
	        return this.getElemType().equals(other.getElemType());
	    }

	    // Pozivamo roditeljsku implementaciju ako nijedna od gore navedenih provera ne prolazi
	    return super.equals(other);
		
		/*if (this.getKind() == Set) return other.getKind() == Array && this.myElemType.equals(other.getElemType()) 
				|| other.getKind() == Set && this.getElemType().equals(other.getElemType());
		
		if (this.getKind() == Array && other.getKind() == Set) return this.getElemType() != null && this.getElemType().equals(other.getElemType()); */
		
	}
	
	
	
	
	@Override
	public boolean assignableTo(Struct dest) {
	    // Ako su oba objekta isti tip
	    if (this.equals(dest)) {
	        return true;
	    }

	    // Ako je levo Set, a desno Array (proveri da li su isti tipovi elemenata)
	    if (this.getKind() == Set && dest.getKind() == Array) {
	        return this.getElemType().equals(dest.getElemType());
	    }

	    // Ako je levo Array, a desno Set (proveri da li su isti tipovi elemenata)
	    if (this.getKind() == Array && dest.getKind() == Set) {
	        return this.getElemType().equals(dest.getElemType());
	    }

	    // Ako su oba objekta tipa Set (proveri da li su isti tipovi elemenata)
	    if (this.getKind() == Set && dest.getKind() == Set) {
	        return this.getElemType().equals(dest.getElemType());
	    }

	    // Pozivanje originalne funkcije ako ništa od gore nije odgovaralo
	    return super.assignableTo(dest);
	}
	
	
	@Override
    public Struct getElemType() {
        if (this.getKind() == Set) {
            return myElemType;
        }
        return super.getElemType();  // Za ostale vrste pozivamo getElemType iz Struct
    }
	
	@Override
	public boolean isRefType() {
	    // Proveri da li je Set (ili neka druga logika specifična za SStruct)
	    return this.getKind() == Set || super.isRefType();
	}
	
}
