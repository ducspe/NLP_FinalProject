package de.unihamburg.informatik.nlp.deceptiondetection.feature;

import java.util.LinkedList;
import java.util.List;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.cleartk.ml.Feature;
import org.cleartk.ml.feature.extractor.CleartkExtractorException;
import org.cleartk.ml.feature.extractor.NamedFeatureExtractor1;
import org.cleartk.token.type.Sentence;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class ExaggerationExtractor<T extends Annotation> implements NamedFeatureExtractor1<T> {
	
	public ExaggerationExtractor() {

	}
	
    @Override
    public String getFeatureName() {
        return "ExaggerationExtractor";
    }
	
	@Override
	public List<Feature> extract(JCas view, T focusAnnotation) throws CleartkExtractorException {
		List<Feature> result = new LinkedList<Feature>();
		boolean hasPositive = false;
		boolean hasNegative = false;
		boolean hasNone = true;
		
		List<Token> tokens = JCasUtil.selectCovered(view, Token.class, focusAnnotation);
		for (Token t: tokens) {
			String word = t.getCoveredText().toLowerCase();
			switch (word) {
				case "love": hasPositive = true; hasNone = false; break;
				case "hate": hasNegative = true; hasNone = false; break;
			}
		}
		
		result.add(new Feature("hasPositive", hasNegative));
		result.add(new Feature("hasNegative", hasPositive));
		result.add(new Feature("hasNone", hasNone));
		
		return result;
	}
}

