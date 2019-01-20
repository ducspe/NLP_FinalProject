# DeceptionDetection-NLP-WS18
Deception detection on open domain dataset with UIMA framework.

To add a feature extractor, follow the examples given by NegationExtractor.java etc. in the feature package. Then add the extractor to the combined extractor in the Annotator.java class.

At the moment, the data contains no meta information. To include it, first add it to the csv dataset (best is tab seperated). Then StatementAnnotationDescriptor.xml in src/main/resources/desc/type needs to be modified. Right click on it, select open with "Component Descriptor Editor", select the "Type System" tab and add the fields (e.g. age, gender, etc.). Then press JCasGen (Might need to remove the 2 java classes in the type package first. JCasGen generates them). Finally, modify the reader to read the metadata from the file and assign it to the corresponding fields.
