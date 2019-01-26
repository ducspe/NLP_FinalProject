package de.unihamburg.informatik.nlp.deceptiondetection.annotator;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.ml.CleartkAnnotator;
import org.cleartk.ml.CleartkProcessingException;
import org.cleartk.ml.Instance;
import org.cleartk.ml.feature.extractor.*;
import org.cleartk.ml.feature.transform.InstanceStream;
import org.cleartk.ml.feature.transform.extractor.CentroidTfidfSimilarityExtractor;
import org.cleartk.ml.feature.transform.extractor.MinMaxNormalizationExtractor;
import org.cleartk.ml.feature.transform.extractor.TfidfExtractor;
import org.cleartk.ml.libsvm.LibSvmStringOutcomeDataWriter;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unihamburg.informatik.nlp.deceptiondetection.feature.CountAnnotationExtractor;
import de.unihamburg.informatik.nlp.deceptiondetection.feature.ExaggerationExtractor;
import de.unihamburg.informatik.nlp.deceptiondetection.feature.FirstPersonPronounExtractor;
import de.unihamburg.informatik.nlp.deceptiondetection.feature.NegationExtractor;
import de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.apache.uima.fit.util.JCasUtil.select;

public class Annotator extends CleartkAnnotator<String> {
    public static final String PARAM_TF_IDF_URI = "tfIdfUri";
    public static final String PARAM_TF_IDF_CENTROID_SIMILARITY_URI = "tfIdfCentroidSimilarityUri";
    public static final String PARAM_ZMUS_URI = "zmusUri";
    public static final String PARAM_MINMAX_URI = "minmaxUri";
    public static final String PARAM_DIRECTORY_NAME = "modelOutputDir";
    public static final String TFIDF_EXTRACTOR_KEY = "Token";
    public static final String CENTROID_TFIDF_SIM_EXTRACTOR_KEY = "CentroidTfIdfSimilarity";
    public static final String MINMAX_EXTRACTOR_KEY = "MINMAXFeatures";
    @ConfigurationParameter(
            name = PARAM_TF_IDF_URI,
            mandatory = false,
            description = "provides a URI where the tf*idf map will be written")
    protected URI tfIdfUri;
    @ConfigurationParameter(
            name = PARAM_TF_IDF_CENTROID_SIMILARITY_URI,
            mandatory = false,
            description = "provides a URI where the tf*idf centroid data will be written")
    protected URI tfIdfCentroidSimilarityUri;

    @ConfigurationParameter(
            name = PARAM_MINMAX_URI,
            mandatory = false,
            description = "provides a URI where the min-max feature normalizaation data will be written")
    protected URI minmaxUri;

    @ConfigurationParameter(name = PARAM_DIRECTORY_NAME,
            mandatory = false)
    private File modelOutputDir;
    private CombinedExtractor1<StatementAnnotation> extractor;
    
    public static URI createTokenTfIdfDataURI(File outputDirectoryName) {
        File f = new File(outputDirectoryName, TFIDF_EXTRACTOR_KEY + "_tfidf_extractor.dat");
        return f.toURI();
    }

    public static URI createIdfCentroidSimilarityDataURI(File outputDirectoryName) {
        File f = new File(outputDirectoryName, CENTROID_TFIDF_SIM_EXTRACTOR_KEY);
        return f.toURI();
    }

    public static URI createMinMaxDataURI(File outputDirectoryName) {
        File f = new File(outputDirectoryName, MINMAX_EXTRACTOR_KEY + "_minmax_extractor.dat");
        return f.toURI();
    }

    List<FeatureExtractor1<StatementAnnotation>> features = new ArrayList<>();



    List<FeatureExtractor1<Token>> tokenFeatures = new ArrayList<>();


    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        super.initialize(context);
        try {

            TfidfExtractor<String, StatementAnnotation> tfIdfExtractor = initTfIdfExtractor();

            CentroidTfidfSimilarityExtractor<String, StatementAnnotation> simExtractor = initCentroidTfIdfSimilarityExtractor();
            MinMaxNormalizationExtractor<String, StatementAnnotation> minmaxExtractor = initMinMaxExtractor();
            NegationExtractor<StatementAnnotation> negationExtractor = new NegationExtractor<StatementAnnotation>();
            FirstPersonPronounExtractor<StatementAnnotation> firstPersonPronounExtractor = new FirstPersonPronounExtractor<StatementAnnotation>();
            ExaggerationExtractor<StatementAnnotation> exaggerationExtractor = new ExaggerationExtractor<StatementAnnotation>();
            //Instantiate more feature extractors

            this.extractor = new CombinedExtractor1<StatementAnnotation>(
                    tfIdfExtractor,
                    simExtractor,
                    minmaxExtractor,
                    negationExtractor,
                    firstPersonPronounExtractor,
                    exaggerationExtractor
                    //Add more feature extractors here
                    );
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }

    }

    private TfidfExtractor<String, StatementAnnotation> initTfIdfExtractor() throws IOException {
        CleartkExtractor<StatementAnnotation, Token> countsExtractor = new CleartkExtractor<StatementAnnotation, Token>(
                Token.class,
                new CoveredTextExtractor<Token>(),
                new CleartkExtractor.Count(new CleartkExtractor.Covered()));

        TfidfExtractor<String, StatementAnnotation> tfIdfExtractor = new TfidfExtractor<String, StatementAnnotation>(
                Annotator.TFIDF_EXTRACTOR_KEY,
                countsExtractor);

        if (this.tfIdfUri != null) {
            tfIdfExtractor.load(this.tfIdfUri);
        }
        return tfIdfExtractor;
    }


    private CentroidTfidfSimilarityExtractor<String, StatementAnnotation> initCentroidTfIdfSimilarityExtractor()
            throws IOException {
        CleartkExtractor<StatementAnnotation, Token> countsExtractor = new CleartkExtractor<StatementAnnotation, Token>(
                Token.class,
                new CoveredTextExtractor<Token>(),
                new CleartkExtractor.Count(new CleartkExtractor.Covered()));

        CentroidTfidfSimilarityExtractor<String, StatementAnnotation> simExtractor = new CentroidTfidfSimilarityExtractor<String, StatementAnnotation>(
                Annotator.CENTROID_TFIDF_SIM_EXTRACTOR_KEY,
                countsExtractor);

        if (this.tfIdfCentroidSimilarityUri != null) {
            simExtractor.load(this.tfIdfCentroidSimilarityUri);
        }
        return simExtractor;
    }


    private MinMaxNormalizationExtractor<String, StatementAnnotation> initMinMaxExtractor()
            throws IOException {
        CombinedExtractor1<StatementAnnotation> featuresToNormalizeExtractor = new CombinedExtractor1<StatementAnnotation>(
                new CountAnnotationExtractor<StatementAnnotation>(Sentence.class),
                new CountAnnotationExtractor<StatementAnnotation>(Token.class));

        MinMaxNormalizationExtractor<String, StatementAnnotation> minmaxExtractor = new MinMaxNormalizationExtractor<String, StatementAnnotation>(
                MINMAX_EXTRACTOR_KEY,
                featuresToNormalizeExtractor);

        if (this.minmaxUri != null) {
            minmaxExtractor.load(this.minmaxUri);
        }

        return minmaxExtractor;
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        System.err.println("extracting");
        for (StatementAnnotation statement : select(jCas, StatementAnnotation.class)) {
            Instance<String> instance = new Instance<String>();

            instance.addAll(this.extractor.extract(jCas, statement));
            
            if (this.isTraining()) {
                instance.setOutcome(statement.getActualLabel());
                this.dataWriter.write(instance);

            } else {
                String result = this.classifier.classify(instance.getFeatures());
                statement.setPredictedLabel(result);
                statement.addToIndexes();

            }
        }
        if (this.isTraining()) {

            try {
                this.collectFeatures(this.modelOutputDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void collectFeatures(File outputDirectory) throws IOException, CleartkProcessingException {

        Iterable<Instance<String>> instances = InstanceStream.loadFromDirectory(outputDirectory);

        System.err.println("Collection feature normalization statistics");
        System.err.println("tfIDF ...");
        
        URI tfIdfDataURI = Annotator.createTokenTfIdfDataURI(outputDirectory);
        TfidfExtractor<String, StatementAnnotation> extractor = new TfidfExtractor<String, StatementAnnotation>(
                Annotator.TFIDF_EXTRACTOR_KEY);
        extractor.train(instances);
        extractor.save(tfIdfDataURI);

         System.err.println("tfIDF Done");
         System.err.println("similarity to corpus centroid ...");
        
        URI tfIdfCentroidSimDataURI = Annotator.createIdfCentroidSimilarityDataURI(outputDirectory);
        CentroidTfidfSimilarityExtractor<String, StatementAnnotation> simExtractor = new CentroidTfidfSimilarityExtractor<String, StatementAnnotation>(
                Annotator.CENTROID_TFIDF_SIM_EXTRACTOR_KEY);
        simExtractor.train(instances);
        simExtractor.save(tfIdfCentroidSimDataURI);

        System.err.println("similarity to corpus centroid Done");
        
        System.err.println("MinMax stats for feature normalization ...");
        
        URI minmaxDataURI = Annotator.createMinMaxDataURI(outputDirectory);
        MinMaxNormalizationExtractor<String, StatementAnnotation> minmaxExtractor = new MinMaxNormalizationExtractor<String, StatementAnnotation>(
                Annotator.MINMAX_EXTRACTOR_KEY);
        minmaxExtractor.train(instances);
        minmaxExtractor.save(minmaxDataURI);
        System.err.println("MinMax stats for feature normalization Done");

        System.err.println("Write out model training data");
        LibSvmStringOutcomeDataWriter dataWriter = new LibSvmStringOutcomeDataWriter(outputDirectory);
        for (Instance<String> instance : instances) {
            instance = extractor.transform(instance);
            instance = simExtractor.transform(instance);
            instance = minmaxExtractor.transform(instance);
            dataWriter.write(instance);
        }
        dataWriter.finish();
    }
}


