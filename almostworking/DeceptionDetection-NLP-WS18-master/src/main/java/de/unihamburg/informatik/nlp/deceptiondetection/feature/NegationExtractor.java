package de.unihamburg.informatik.nlp.deceptiondetection.feature;

import java.util.LinkedList;
import java.util.List;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.cleartk.ml.Feature;
import org.cleartk.ml.feature.extractor.CleartkExtractorException;
import org.cleartk.ml.feature.extractor.NamedFeatureExtractor1;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class NegationExtractor<T extends Annotation> implements NamedFeatureExtractor1<T> {
	
	public NegationExtractor() {

	}
	
    @Override
    public String getFeatureName() {
        return "NegationExtractor";
    }
	
	@Override
	public List<Feature> extract(JCas view, T focusAnnotation) throws CleartkExtractorException {
		List<Feature> result = new LinkedList<Feature>();
		boolean hasNegation = false;
		
		List<Token> tokens = JCasUtil.selectCovered(view, Token.class, focusAnnotation);
		for (Token t: tokens) {
			String word = t.getCoveredText().toLowerCase();
			switch (word) {
				case "not":
				case "can't":
				case "cannot":
				case "aren't":
				case "haven't":
				case "hasn't":
				case "mustn't":
				case "needn't":
				case "shouldn't":
				case "don't":
				case "cant":
				case "arent":
				case "havent":
				case "hasnt":
				case "musnt":
				case "couldn't":
				case "couldnt":
				case "neednt":
				case "shouldnt":
				case "never":
				case "dont": hasNegation = true;
					
			}
		}
		
		result.add(new Feature("hasNegation", hasNegation));
		
		return result;
	}
}
