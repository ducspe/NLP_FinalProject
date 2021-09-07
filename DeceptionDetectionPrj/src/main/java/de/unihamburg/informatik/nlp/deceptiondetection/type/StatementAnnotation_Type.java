
/* First created by JCasGen Thu Jan 10 20:21:47 CET 2019 */
package de.unihamburg.informatik.nlp.deceptiondetection.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Thu Jan 10 20:21:47 CET 2019
 * @generated */
public class StatementAnnotation_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = StatementAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation");
 
  /** @generated */
  final Feature casFeat_actualLabel;
  /** @generated */
  final int     casFeatCode_actualLabel;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getActualLabel(int addr) {
        if (featOkTst && casFeat_actualLabel == null)
      jcas.throwFeatMissing("actualLabel", "de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_actualLabel);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setActualLabel(int addr, String v) {
        if (featOkTst && casFeat_actualLabel == null)
      jcas.throwFeatMissing("actualLabel", "de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_actualLabel, v);}
    
  
 
  /** @generated */
  final Feature casFeat_predictedLabel;
  /** @generated */
  final int     casFeatCode_predictedLabel;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getPredictedLabel(int addr) {
        if (featOkTst && casFeat_predictedLabel == null)
      jcas.throwFeatMissing("predictedLabel", "de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_predictedLabel);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPredictedLabel(int addr, String v) {
        if (featOkTst && casFeat_predictedLabel == null)
      jcas.throwFeatMissing("predictedLabel", "de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_predictedLabel, v);}
    
  
 
  /** @generated */
  final Feature casFeat_statement;
  /** @generated */
  final int     casFeatCode_statement;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getStatement(int addr) {
        if (featOkTst && casFeat_statement == null)
      jcas.throwFeatMissing("statement", "de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_statement);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setStatement(int addr, String v) {
        if (featOkTst && casFeat_statement == null)
      jcas.throwFeatMissing("statement", "de.unihamburg.informatik.nlp.deceptiondetection.type.StatementAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_statement, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public StatementAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_actualLabel = jcas.getRequiredFeatureDE(casType, "actualLabel", "uima.cas.String", featOkTst);
    casFeatCode_actualLabel  = (null == casFeat_actualLabel) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_actualLabel).getCode();

 
    casFeat_predictedLabel = jcas.getRequiredFeatureDE(casType, "predictedLabel", "uima.cas.String", featOkTst);
    casFeatCode_predictedLabel  = (null == casFeat_predictedLabel) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_predictedLabel).getCode();

 
    casFeat_statement = jcas.getRequiredFeatureDE(casType, "statement", "uima.cas.String", featOkTst);
    casFeatCode_statement  = (null == casFeat_statement) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_statement).getCode();

  }
}



    