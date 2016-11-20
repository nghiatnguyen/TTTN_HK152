/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coreferenceresolver.process;

import coreferenceresolver.element.CorefChain;
import coreferenceresolver.element.NounPhrase;
import coreferenceresolver.element.Review;
import coreferenceresolver.util.CrfChunkerUtil;
import coreferenceresolver.util.StanfordUtil;
import coreferenceresolver.util.Util;
import coreferenceresolver.weka.Weka;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import weka.classifiers.Evaluation;

/**
 *
 * @author TRONGNGHIA
 */
public class WekaMain {

    public static void run(String inputFilePath, String testFilePath, String resultFilePath) throws Exception {
        File inputFile = new File(inputFilePath);
        StanfordUtil su = new StanfordUtil(inputFile);

        try {
            //Init every info
            su.simpleInit();

            //Call CRFChunker, result is in input.txt.pos.chk file
            CrfChunkerUtil.runChunk();

            //Read from input.txt.pos.chk file. Get all NPs
            List<NounPhrase> nounPhrases = CrfChunkerUtil.readCrfChunker();

            Util.assignNounPhrases(nounPhrases, StanfordUtil.reviews);

            //Begin markup
            for (Review review : StanfordUtil.reviews) {
                //Discard all NPs that is Personal Pronoun
                Util.discardUnneccessaryNPs(review);
            }

            Weka.j48Classify(testFilePath, resultFilePath);
            
            //Evaluation MUC score
//            for (Review review : StanfordUtil.reviews) {
//            	for (CorefChain cf : review.getCorefChainsPredict()){
//            		System.out.println("*****" + cf.getChain().toString());
//            	}
//                for (CorefChain cf : review.getCorefChainsActual()){
//                	System.out.println("Chain: " +  cf.getChain().toString() + 
//                			"Size: " + coreferenceresolver.test.Evaluation.findSize(cf)
//                			+ "Numpartitions: " + coreferenceresolver.test.Evaluation.findNumPartitions(cf, review.getCorefChainsPredict()));
//                }
//            }
            coreferenceresolver.test.Evaluation.scoreMUC(StanfordUtil.reviews);

        } catch (IOException ex) {
            Logger.getLogger(MarkupMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
