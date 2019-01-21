package de.unihamburg.informatik.nlp.deceptiondetection.writer;

import org.apache.commons.io.IOUtils;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation;
import gui.GUI;

import java.io.*;

import static org.apache.uima.fit.util.JCasUtil.select;

public class DeceptionEval extends JCasAnnotator_ImplBase {
    Logger logger = UIMAFramework.getLogger(DeceptionEval.class);

    public static final String PARAM_OUTPUT_DIR = "InputFile";

    @ConfigurationParameter(name = PARAM_OUTPUT_DIR, mandatory = true)
    private String outputDir;
    

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        int[][] confusionMatrix = new int[3][3];
        try {
            FileOutputStream result = new FileOutputStream(outputDir);
            IOUtils.write("TITLE\tGOLD\tPREDICTED\n", result,"UTF-8");
            for (StatementAnnotation statement : select(jCas, StatementAnnotation.class)) {
                
                String predicted = statement.getPredictedLabel();
                String actual = statement.getActualLabel();
                int row = -1;
                int col = -1;
                switch(predicted) {
	            	case "lie": row = 0; confusionMatrix[0][2]++; break;
	            	case "truth": row = 1; confusionMatrix[1][2]++; break;
	            }
                switch(actual) {
                	case "lie": col = 0; confusionMatrix[2][0]++; break;
                	case "truth": col = 1; confusionMatrix[2][1]++; break;
                }
                confusionMatrix[row][col]++;
                
                IOUtils.write(statement.getStatement() +"\t"+ actual +"\t"+predicted+"\n", result,"UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 

        double precision = ((double) confusionMatrix[0][0]) / ((double) confusionMatrix[0][2]);
        double recall = ((double) confusionMatrix[0][0]) / ((double) confusionMatrix[2][0]);
        double f1 = 2 * ((precision * recall) / (precision + recall));
        double totalCorrect = (((double) confusionMatrix[0][0]) + ((double) confusionMatrix[1][1]));
        double total = ((double) confusionMatrix[0][2]) + ((double) confusionMatrix[1][2]);
        double accuracy = totalCorrect / total;
        
        System.out.println("Precision: " + precision);
        System.out.println("Recall: " + recall);
        System.out.println("F1: " + f1);
        System.out.println("Accuracy: " + accuracy);
		
        try { 
        		
		      FileOutputStream evalFile = new FileOutputStream(new File(GUI.class.getClassLoader().getResource("results/eval.txt").getFile()));
		      IOUtils.write("Precision: " + precision + "\n", evalFile, "UTF-8");
		      IOUtils.write("Recall: " + recall + "\n", evalFile, "UTF-8");
		      IOUtils.write("F1: " + f1 + "\n", evalFile, "UTF-8");
		      IOUtils.write("Accuracy: " + accuracy + "\nEOF", evalFile, "UTF-8");
		} catch (IOException e) {
		    e.printStackTrace();
		} 
    }

}
