

/* First created by JCasGen Thu Jan 10 20:21:47 CET 2019 */
package de.unihamburg.informatik.nlp.deceptiondetection.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Jan 10 20:21:47 CET 2019
 * XML source: C:/Users/Seb/Desktop/NLP/Exercise/final_project/DeceptionDetection-NLP-WS18/src/main/resources/desc/type/StatementAnnotation.xml
 * @generated */
public class StatementAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(StatementAnnotation.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected StatementAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public StatementAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public StatementAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public StatementAnnotation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: actualLabel

  /** getter for actualLabel - gets 
   * @generated
   * @return value of the feature 
   */
  public String getActualLabel() {
    if (StatementAnnotation_Type.featOkTst && ((StatementAnnotation_Type)jcasType).casFeat_actualLabel == null)
      jcasType.jcas.throwFeatMissing("actualLabel", "de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((StatementAnnotation_Type)jcasType).casFeatCode_actualLabel);}
    
  /** setter for actualLabel - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setActualLabel(String v) {
    if (StatementAnnotation_Type.featOkTst && ((StatementAnnotation_Type)jcasType).casFeat_actualLabel == null)
      jcasType.jcas.throwFeatMissing("actualLabel", "de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((StatementAnnotation_Type)jcasType).casFeatCode_actualLabel, v);}    
   
    
  //*--------------*
  //* Feature: predictedLabel

  /** getter for predictedLabel - gets 
   * @generated
   * @return value of the feature 
   */
  public String getPredictedLabel() {
    if (StatementAnnotation_Type.featOkTst && ((StatementAnnotation_Type)jcasType).casFeat_predictedLabel == null)
      jcasType.jcas.throwFeatMissing("predictedLabel", "de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((StatementAnnotation_Type)jcasType).casFeatCode_predictedLabel);}
    
  /** setter for predictedLabel - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPredictedLabel(String v) {
    if (StatementAnnotation_Type.featOkTst && ((StatementAnnotation_Type)jcasType).casFeat_predictedLabel == null)
      jcasType.jcas.throwFeatMissing("predictedLabel", "de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((StatementAnnotation_Type)jcasType).casFeatCode_predictedLabel, v);}    
   
    
  //*--------------*
  //* Feature: statement

  /** getter for statement - gets 
   * @generated
   * @return value of the feature 
   */
  public String getStatement() {
    if (StatementAnnotation_Type.featOkTst && ((StatementAnnotation_Type)jcasType).casFeat_statement == null)
      jcasType.jcas.throwFeatMissing("statement", "de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((StatementAnnotation_Type)jcasType).casFeatCode_statement);}
    
  /** setter for statement - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setStatement(String v) {
    if (StatementAnnotation_Type.featOkTst && ((StatementAnnotation_Type)jcasType).casFeat_statement == null)
      jcasType.jcas.throwFeatMissing("statement", "de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((StatementAnnotation_Type)jcasType).casFeatCode_statement, v);}    
  }

    