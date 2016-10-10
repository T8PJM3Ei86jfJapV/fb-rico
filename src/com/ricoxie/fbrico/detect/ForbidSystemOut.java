package com.ricoxie.fbrico.detect;

import org.apache.bcel.classfile.Code;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

public class ForbidSystemOut extends OpcodeStackDetector {

    String BUG_PATTERN = "RICO_FORBID_SYSTEM_OUT";

    BugReporter bugReporter;

    public ForbidSystemOut(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
    }

    /*
     * visit方法，在每次进入字节码方法的时候调用 在每次进入新方法的时候清空标志位
     */
    @Override
    public void visit(Code obj) {
        super.visit(obj);
    }

    /**
     * 每扫描一条字节码就会进入sawOpcode方法
     *
     * @param seen
     *            字节码的枚举值
     */
    @Override
    public void sawOpcode(int seen) {
        if (seen == GETSTATIC) {
            if (getClassConstantOperand().equals("java/lang/System")) {
                if(getNameConstantOperand().equals("out")){
                    BugInstance bug = new BugInstance(this, BUG_PATTERN,
                            NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
                                    this, getPC());
                    bugReporter.reportBug(bug);
                }
            }
        }
    }

}
