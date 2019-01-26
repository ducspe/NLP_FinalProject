package de.unihamburg.informatik.nlp.deceptiondetection.writer;

import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Logger;

import de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation;

public class EvaluateUserInput extends JCasAnnotator_ImplBase {
    
	Logger logger = UIMAFramework.getLogger(DeceptionEval.class);

    public static final String PARAM_OUTPUT_DIR = "InputFile";

    @ConfigurationParameter(name = PARAM_OUTPUT_DIR, mandatory = true)
    private String outputDir;
    

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        try {
        	FileOutputStream evalFile = new FileOutputStream(outputDir);
        	//IOUtils.write("TITLE\tGROUND\tPREDICTED\n", evalFile,"UTF-8");
            for (StatementAnnotation statement : select(jCas, StatementAnnotation.class)) {      
                String predicted = statement.getPredictedLabel();
                String actual = statement.getActualLabel();
                IOUtils.write(statement.getStatement() +"\t"+ actual +"\t"+predicted+"\n", evalFile,"UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
		
    }

}
