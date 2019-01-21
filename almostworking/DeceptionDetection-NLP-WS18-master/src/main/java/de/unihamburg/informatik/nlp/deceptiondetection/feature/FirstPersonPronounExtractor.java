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

public class FirstPersonPronounExtractor<T extends Annotation> implements NamedFeatureExtractor1<T> {
	
	public FirstPersonPronounExtractor() {

	}
	
    @Override
    public String getFeatureName() {
        return "FirstPersonPronounExtractor";
    }
	
	@Override
	public List<Feature> extract(JCas view, T focusAnnotation) throws CleartkExtractorException {
		List<Feature> result = new LinkedList<Feature>();
		boolean isFirstPerson = false;
		
		List<Token> tokens = JCasUtil.selectCovered(view, Token.class, focusAnnotation);
		for (Token t: tokens) {
			String word = t.getCoveredText().toLowerCase();
			isFirstPerson = word == "i";
		}
		
		result.add(new Feature("isFirstPerson", isFirstPerson));
		
		return result;
	}
}
