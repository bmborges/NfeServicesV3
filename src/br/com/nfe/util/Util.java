/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfe.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author root
 */
public class Util {
    java.sql.Connection conn = null;

    public Util() throws Exception {
         conn = Database.getConnection();

         if (conn == null) {
            throw new Exception(getClass().getName() + ": null connection passed.");
         }
        this.conn = conn;        
    }
    public static void main(String[] args) throws SQLException, Exception {
        Util u = new Util();
        u.TpAmb();
    }
    
    public static String caminho(){
        
        String curDir = System.getProperty("user.home");
        String curSep = System.getProperty("file.separator");
        String caminho = curDir + curSep + "NfeServices"+ curSep;
        
        return caminho;
    }
    public int TpAmb() throws SQLException{
        int rTpAmb = 0;
        String qry = "select codigo as TpAmb from adm_param"
             + "  where nmparam ilike 'VND-AMBIENTE_NFeV3'";
        PreparedStatement stmt = conn.prepareStatement(qry);
        ResultSet rs = stmt.executeQuery();     
        if (rs.next()) {
            rTpAmb = Integer.parseInt(rs.getString("TpAmb"));
        } else {
            rTpAmb = 2;
        }
        return rTpAmb;
    }

    
    public int TpImp() throws SQLException{
        int rTpImp = 0;
        String qry = "select codigo as TpImp from adm_param"
             + " where nmparam ilike 'VND-FORMATO IMPRESSAO DANFE'";
        PreparedStatement stmt = conn.prepareStatement(qry);
        ResultSet rs = stmt.executeQuery();     
        if (rs.next()) {
            rTpImp = Integer.parseInt(rs.getString("TpImp"));
        } else {
            rTpImp = 1;
        }

        return rTpImp;
    }
    
    public void UpdateTpEmiss(int tpemiss) throws SQLException{
        String qry = "update adm_param set codigo = ? "
             + " where nmparam ilike 'VND_TIPOEMISSAO_NFE'";
        PreparedStatement stmt = conn.prepareStatement(qry);
        stmt.setInt(1, tpemiss);
                
        int rs = stmt.executeUpdate();
    }    
    
 public static String RemoveCaracteresEdicao(String xml){
     String[] naopode = {"\n","\t","\r","> <",">  <"};
     for (int i = 0; i < naopode.length; i++) {
         while (xml.contains(naopode[i])) {
             if(naopode[i]  == "> <"){
                xml = xml.replace(naopode[i],"><");
             } else if (naopode[i]  == ">  <"){
                xml = xml.replace(naopode[i],"><");
             } else {
                 xml = xml.replace(naopode[i],"");
             }
         }
     }
  return xml;
 }    
    
}
