package fr.univ.nantes.processor;

import fr.univ.nantes.Spoon;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtTry;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;

/**
 * @author Alexis Giraudet
 * @author Thomas Minier
 */
public class MethodCallTreeProcessor extends AbstractProcessor<CtExecutable<?>> {
    @Override
    public boolean isToBeProcessed(CtExecutable<?> executable) {
        return executable instanceof CtMethod || executable instanceof CtConstructor;
    }

    @Override
    public void process(CtExecutable<?> executable) {
        String enterCode = "fr.univ.nantes.logger.LogWriter.enterMethod(\"" + Spoon.getExecutableKey(executable) + "\")";
        String leaveCode = "fr.univ.nantes.logger.LogWriter.leaveMethod()";
        CtStatement enterCtStatement = getFactory().Code().createCodeSnippetStatement(enterCode);
        CtStatement leaveCtStatement = getFactory().Code().createCodeSnippetStatement(leaveCode);
        CtTry ctTry = getFactory().Core().createTry();
        CtBlock finallyCtBlock = getFactory().Core().createBlock();
        CtBlock bodyCtBlock = getFactory().Core().createBlock();

        finallyCtBlock.addStatement(leaveCtStatement);
        ctTry.setFinalizer(finallyCtBlock);
        ctTry.setBody(executable.getBody());
        bodyCtBlock.addStatement(enterCtStatement);
        bodyCtBlock.addStatement(ctTry);
        executable.setBody(bodyCtBlock);
    }
}
