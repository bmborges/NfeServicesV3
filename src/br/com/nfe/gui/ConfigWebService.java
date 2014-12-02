/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConfigWebService.java
 *
 * Created on 03/07/2013, 15:56:48
 */

package br.com.nfe.gui;

import br.com.nfe.bean.adm.ADM_ParamBean;
import br.com.nfe.dao.adm.ADM_ParamDAO;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author supervisor
 */
public class ConfigWebService extends javax.swing.JInternalFrame {
    DefaultTableModel model = null;
    ADM_ParamBean bean = null;
    ADM_ParamDAO dao = null;

    /** Creates new form ConfigWebService */
    public ConfigWebService() {
        initComponents();

        setClosable(true);
        setTitle("Configura WebService");

        jtidparam.setEditable(false);
        jbgravar.setText("Inserir");
        jbdeletar.setEnabled(false);
        try {
            preparaTable();
            TablegetDados();
        } catch (Exception e) {
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jtidparam = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jtvalorparam = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtcodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtvalor = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jbgravar = new javax.swing.JButton();
        jbdeletar = new javax.swing.JButton();
        jblimpar = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();

        jLabel4.setText("Id.:");

        jLabel1.setText("Endereço:");

        jLabel2.setText("Cod. Estado:");

        jLabel3.setText("Versão:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jbgravar.setText("Gravar");
        jbgravar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbgravarMouseClicked(evt);
            }
        });

        jbdeletar.setText("Deletar");
        jbdeletar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbdeletarMouseClicked(evt);
            }
        });

        jblimpar.setText("Limpar");
        jblimpar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jblimparMouseClicked(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "WebserverNfeRecepcao", "WebserverNfeInutilizacao", "WebserverNfeCancelamento", "WebserverNfeEvento", "WebserverNfeRetRecepcao", "WebserverNfeConsulta", "WebserverNfeStatusServico", "WebserverNfeDpec", "WebserverNfeInutilizacaoCont", "WebserverNfeManifesto" }));

        jLabel5.setText("Serviço:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addComponent(jbgravar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbdeletar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jblimpar)
                .addContainerGap(212, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtidparam, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(270, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtvalor, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtvalorparam, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))
                        .addGap(205, 205, 205))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtidparam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtvalorparam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtvalor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbgravar)
                    .addComponent(jbdeletar)
                    .addComponent(jblimpar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
private void preparaTable(){
        String[] colunas = new String[]{"Id.","Serviço","Endereço","Cod. Estado","Versão"};

        String[][] dados = new String[][]{};

    model = new DefaultTableModel(dados , colunas );
    jTable1.setModel(model);
    jTable1.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                setTableValue();
            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }
        });

}
private void setTableValue(){

    jbgravar.setText("Gravar");
    jbdeletar.setEnabled(true);

    int linha = jTable1.getSelectedRow();//pegando linha selecionada

    String idparam = jTable1.getValueAt(linha , 0) != null ? jTable1.getValueAt(linha , 0).toString() : "";
    String nmparam = jTable1.getValueAt(linha , 1) != null ? jTable1.getValueAt(linha , 1).toString() : "";
    String valorparam = jTable1.getValueAt(linha , 2) != null ? jTable1.getValueAt(linha , 2).toString() : "";
    String codigo = jTable1.getValueAt(linha , 3) != null ? jTable1.getValueAt(linha , 3).toString() : "";
    String valor = jTable1.getValueAt(linha , 4) != null ? jTable1.getValueAt(linha , 4).toString() : "";

    jtidparam.setText(idparam);
    jtvalorparam.setText(valorparam);
    jtcodigo.setText(codigo);
    jtvalor.setText(valor);
    jComboBox1.setSelectedItem(nmparam);

}
private void TablegetDados() throws Exception{
    limpar_Grid();
    List lista = null;
    dao = new ADM_ParamDAO();
    lista = dao.getDados_Nmparam("WebserverNfe%");
    for (int i = 0; i < lista.size(); i++) {
        bean = (ADM_ParamBean) lista.get(i);
        model.addRow(new Object[]{
            bean.getIdparam(),
            bean.getNmparam(),
            bean.getValorparam(),
            bean.getCodigo(),
            bean.getValor()

        });

    }

}
 private void limpar_Grid() {
    while (model.getRowCount() - 1 != -1) {    //-- limpar grid para novo pedido --//
        model.removeRow(model.getRowCount() - 1);
    }
}
private void limpa_form(){
    jbgravar.setText("Inserir");
    jbdeletar.setEnabled(false);
    jtvalorparam.setText("");
    jtcodigo.setText("");
    jtvalor.setText("");
    jtidparam.setText("");
    try {
        TablegetDados();
    } catch (Exception ex) {
        Logger.getLogger(ConfigImp.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    private void jbgravarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbgravarMouseClicked
        // TODO add your handling code here:
        bean = new ADM_ParamBean();
        try {
            dao = new ADM_ParamDAO();
        } catch (Exception ex) {
            Logger.getLogger(ConfigImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (jtidparam.getText().length() <= 0){
            /*inserir*/
            bean.setNmparam(jComboBox1.getSelectedItem().toString());
            bean.setValorparam(jtvalorparam.getText().length() > 0 ? jtvalorparam.getText() : null);
            bean.setCodigo(jtcodigo.getText().length() > 0 ? Integer.parseInt(jtcodigo.getText()) : null);
            bean.setValor(jtvalor.getText().length() > 0 ? Double.parseDouble(jtvalor.getText()) : null);
        } else {
            /*alterar*/
            bean.setNmparam(jComboBox1.getSelectedItem().toString());
            bean.setIdparam(Integer.parseInt(jtidparam.getText()));
            bean.setValorparam(jtvalorparam.getText().length() > 0 ? jtvalorparam.getText() : null);
            bean.setCodigo(jtcodigo.getText().length() > 0 ? Integer.parseInt(jtcodigo.getText()) : null);
            bean.setValor(jtvalor.getText().length() > 0 ? Double.parseDouble(jtvalor.getText()) : null);
        }

//        try {
//            dao.gravaDados_Impressora(bean);
//        } catch (SQLException ex) {
//            Logger.getLogger(ConfigImp.class.getName()).log(Level.SEVERE, null, ex);
//        }

        limpa_form();
    }//GEN-LAST:event_jbgravarMouseClicked

    private void jbdeletarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbdeletarMouseClicked
        // TODO add your handling code here:
        bean = new ADM_ParamBean();
        try {
            dao = new ADM_ParamDAO();
        } catch (Exception ex) {
            Logger.getLogger(ConfigImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        bean.setIdparam(Integer.parseInt(jtidparam.getText()));
//        try {
//            dao.deletaDados_Impressora(bean);
//        } catch (SQLException ex) {
//            Logger.getLogger(ConfigImp.class.getName()).log(Level.SEVERE, null, ex);
//        }

        limpa_form();
    }//GEN-LAST:event_jbdeletarMouseClicked

    private void jblimparMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jblimparMouseClicked
        // TODO add your handling code here:
        limpa_form();
}//GEN-LAST:event_jblimparMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton jbdeletar;
    private javax.swing.JButton jbgravar;
    private javax.swing.JButton jblimpar;
    private javax.swing.JTextField jtcodigo;
    private javax.swing.JTextField jtidparam;
    private javax.swing.JTextField jtvalor;
    private javax.swing.JTextField jtvalorparam;
    // End of variables declaration//GEN-END:variables

}