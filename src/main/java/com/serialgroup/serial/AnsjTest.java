package com.serialgroup.serial;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.DicAnalysis;

public class AnsjTest {
    public static void main(String[] args) {
        Result result = DicAnalysis.parse("陌生人110是阿斗");
        for (Term term : result.getTerms()) {
            System.out.println(term.getName());
        }
    }
}
