/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coreferenceresolver.gui;

import coreferenceresolver.element.CorefChain;
import coreferenceresolver.element.NounPhrase;
import coreferenceresolver.element.Review;
import coreferenceresolver.util.StanfordUtil;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.StyledDocument;

/**
 *
 * @author TRONGNGHIA
 */
public class ClassifiedResultGUI extends javax.swing.JFrame {
    
    private Color[] COLORS = new Color[]{Color.YELLOW, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN,
        Color.LIGHT_GRAY, Color.ORANGE, Color.PINK, Color.RED};
    
    private List<DefaultHighlighter.DefaultHighlightPainter> highlightPainters;
    
    private String demoText = "";

    /**
     * Creates new form ClassifiedResultGUI
     */
    public ClassifiedResultGUI() throws BadLocationException {
        initComponents();
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        highlightPainters = new ArrayList<>();
        
        for (int i = 0; i < COLORS.length; ++i) {
            DefaultHighlighter.DefaultHighlightPainter highlightPainter
                    = new DefaultHighlighter.DefaultHighlightPainter(COLORS[i]);
            highlightPainters.add(highlightPainter);
        }
        
        resultJTxtPane.setText(demoText);                
        
        for (int i = 0; i < StanfordUtil.reviews.size(); ++i){
            highlightReview(StanfordUtil.reviews.get(i));
        }
    }
    
    private void highlightReview(Review review) throws BadLocationException{
        StyledDocument doc = resultJTxtPane.getStyledDocument();
        int curLen = doc.getLength();
        doc.insertString(curLen, "\n" + review.getRawContent(), null);
        for (int i = 0; i < review.getCorefChains().size(); ++i){
            CorefChain cc = review.getCorefChains().get(i);
            for (int j = 0; j < cc.getChain().size(); ++j){
                NounPhrase np = review.getNounPhrases().get(cc.getChain().get(j));
                int npOffsetBegin = np.getOffsetBegin();
                int npOffsetEnd = np.getOffsetEnd();
                resultJTxtPane.getHighlighter().addHighlight(curLen + 1 + npOffsetBegin, curLen + 1 + npOffsetEnd,
                highlightPainters.get(i));
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        resultJTxtSPane = new javax.swing.JScrollPane();
        resultJTxtPane = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        resultJTxtSPane.setViewportView(resultJTxtPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resultJTxtSPane, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(resultJTxtSPane, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClassifiedResultGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClassifiedResultGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClassifiedResultGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClassifiedResultGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ClassifiedResultGUI()                            
                            .setVisible(true);
                } catch (BadLocationException ex) {
                    Logger.getLogger(ClassifiedResultGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane resultJTxtPane;
    private javax.swing.JScrollPane resultJTxtSPane;
    // End of variables declaration//GEN-END:variables
}
