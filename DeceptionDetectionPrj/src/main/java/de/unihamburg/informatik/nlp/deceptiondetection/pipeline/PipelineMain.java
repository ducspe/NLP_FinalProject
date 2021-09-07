package de.unihamburg.informatik.nlp.deceptiondetection.pipeline;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.UIMAFramework;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.testing.util.HideOutput;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.cleartk.ml.feature.transform.InstanceDataWriter;
import org.cleartk.ml.jar.DefaultDataWriterFactory;
import org.cleartk.ml.jar.DirectoryDataWriterFactory;
import org.cleartk.ml.jar.GenericJarClassifierFactory;
import org.cleartk.ml.jar.JarClassifierBuilder;
import org.cleartk.util.cr.FilesCollectionReader;

import de.unihamburg.informatik.nlp.deceptiondetection.annotator.Annotator;
import de.unihamburg.informatik.nlp.deceptiondetection.reader.Reader;
import de.unihamburg.informatik.nlp.deceptiondetection.writer.DeceptionEval;
import de.unihamburg.informatik.nlp.deceptiondetection.writer.EvaluateUserInput;
import gui.GUI;

public class PipelineMain {

    public static void writeModel(File saTrain, File modelDirectory, File stopwordsFile)
            throws ResourceInitializationException, UIMAException, IOException {
        System.err.println("Step 1: Extracting features and writing raw instances data");

        runPipeline(
                FilesCollectionReader.getCollectionReaderWithSuffixes(saTrain.getAbsolutePath(),
                        Reader.NEWS_CSV_VIEW, saTrain.getName()),
                createEngine(Reader.class, Reader.PARAM_STOP_WORDS, stopwordsFile),
                createEngine(AnalysisEngineFactory.createEngineDescription(
                        Annotator.class,
                        Annotator.PARAM_IS_TRAINING, true,
                        Annotator.PARAM_DIRECTORY_NAME, modelDirectory,
                        DefaultDataWriterFactory.PARAM_DATA_WRITER_CLASS_NAME,
                        InstanceDataWriter.class.getName(),
                        DirectoryDataWriterFactory.PARAM_OUTPUT_DIRECTORY,
                        modelDirectory))

        );
    }

    public static void trainModel(File modelDirectory, String[] arguments) throws Exception {
        System.err.println("Train model and write model.jar file.");
        HideOutput hider = new HideOutput();
        JarClassifierBuilder.trainAndPackage(modelDirectory, arguments);
        hider.restoreOutput();
    }

    public static void classifyTestFile(File modelDirectory, File saTest, File result, File stopwordsFile)
            throws ResourceInitializationException, UIMAException, IOException {

        runPipeline(
                FilesCollectionReader.getCollectionReaderWithSuffixes(saTest.getAbsolutePath(),
                        Reader.NEWS_CSV_VIEW, saTest.getName()),
                createEngine(Reader.class, Reader.PARAM_STOP_WORDS, stopwordsFile),
                createEngine(AnalysisEngineFactory.createEngineDescription(
                        Annotator.class,
                        Annotator.PARAM_IS_TRAINING, false,
                        Annotator.PARAM_DIRECTORY_NAME, modelDirectory,
                        Annotator.PARAM_TF_IDF_URI,
                        Annotator.createTokenTfIdfDataURI(modelDirectory),
                        Annotator.PARAM_TF_IDF_CENTROID_SIMILARITY_URI,
                        Annotator.createIdfCentroidSimilarityDataURI(modelDirectory),
                        Annotator.PARAM_MINMAX_URI,
                        Annotator.createMinMaxDataURI(modelDirectory),
                        GenericJarClassifierFactory.PARAM_CLASSIFIER_JAR_PATH, modelDirectory + "/model.jar")
                ),
                createEngine(DeceptionEval.class, DeceptionEval.PARAM_OUTPUT_DIR, result.getAbsolutePath()));

    }
    
    public static void classifyUserInput(File modelDirectory, File saTest, File result, File stopwordsFile)
            throws ResourceInitializationException, UIMAException, IOException {

        runPipeline(
                FilesCollectionReader.getCollectionReaderWithSuffixes(saTest.getAbsolutePath(),
                        Reader.NEWS_CSV_VIEW, saTest.getName()),
                createEngine(Reader.class, Reader.PARAM_STOP_WORDS, stopwordsFile),
                createEngine(AnalysisEngineFactory.createEngineDescription(
                        Annotator.class,
                        Annotator.PARAM_IS_TRAINING, false,
                        Annotator.PARAM_DIRECTORY_NAME, modelDirectory,
                        Annotator.PARAM_TF_IDF_URI,
                        Annotator.createTokenTfIdfDataURI(modelDirectory),
                        Annotator.PARAM_TF_IDF_CENTROID_SIMILARITY_URI,
                        Annotator.createIdfCentroidSimilarityDataURI(modelDirectory),
                        Annotator.PARAM_MINMAX_URI,
                        Annotator.createMinMaxDataURI(modelDirectory),
                        GenericJarClassifierFactory.PARAM_CLASSIFIER_JAR_PATH, modelDirectory + "/model.jar")
                ),
                createEngine(EvaluateUserInput.class, EvaluateUserInput.PARAM_OUTPUT_DIR, result.getAbsolutePath()));
        

    }
    

    public static void main(String[] args) throws Exception {
    	
        String[] trainingArguments = new String[]{"-t", "0"};
        long start = System.currentTimeMillis();

        String modelPath = "WebContent/WEB-INF/classes/model/";
        new File(modelPath).mkdirs();
        File modelDir = new File(modelPath);
        
        String resltPath = "WebContent/WEB-INF/classes/results/";
        new File(resltPath).mkdirs();
        File resultDir = new File(resltPath+"news.csv");

        File newsTrain = new File("WebContent/WEB-INF/classes/dataset/train.csv");
        File newsTest = new File("WebContent/WEB-INF/classes/dataset/test.csv");
        
        File stopWordsFile = new File("WebContent/WEB-INF/classes/stopwords.txt");
       
        writeModel(newsTrain, modelDir, stopWordsFile);
        trainModel(modelDir, trainingArguments);
        classifyTestFile(modelDir, newsTest, resultDir, stopWordsFile);
        long now = System.currentTimeMillis();
        UIMAFramework.getLogger().log(Level.INFO, "Time: " + (now - start) + "ms");
    }
   /*
   public static void main(String[] args) throws Exception {
    	
    	
        String[] trainingArguments = new String[]{"-t", "0"};
        long start = System.currentTimeMillis();

        String modelPath = "src/test/resources/model/";
        new File(modelPath).mkdirs();
        File modelDir = new File(modelPath);
        
        String resltPath = "src/test/resources/results/";
        new File(resltPath).mkdirs();
        File resultDir = new File(resltPath+"news.csv");

        File newsTrain = new File("src/main/resources/dataset/train.csv");
        File newsTest = new File("src/main/resources/dataset/test.csv");
        
        File stopWordsFile = new File("src/main/resources/stopwords.txt");
       
        writeModel(newsTrain, modelDir, stopWordsFile);
        trainModel(modelDir, trainingArguments);
        classifyTestFile(modelDir, newsTest, resultDir, stopWordsFile);
        long now = System.currentTimeMillis();
        UIMAFramework.getLogger().log(Level.INFO, "Time: " + (now - start) + "ms");
    }*/

}
