/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfe.dao.bco;

import br.com.nfe.util.Database;
import br.com.nfe.util.DtSystem;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author root
 */
public class BCO_BoletoDAO {
    
    java.sql.Connection conn = null;

    public BCO_BoletoDAO() throws Exception {
        
        conn = Database.getConnection();

        if (conn == null) {
          throw new Exception(getClass().getName() + ": null connection passed.");
        }
          this.conn = conn;
    }
    public void UpdateBoleto_pedido(int cdpedido, int status) throws SQLException{
        String qry = "update bco_boleto set env_email = ? where idboleto in (select idboleto from bco_view_boleto where cdpedido = ?)";
        PreparedStatement stmt = conn.prepareStatement(qry);
        stmt.setInt(1, status);
        stmt.setInt(2, cdpedido);
        stmt.executeUpdate();
    }
    public HashMap ConsultaBoleto_pedido() throws SQLException{
        String qry = "select cdpedido, idboleto from bco_view_boleto where id_codremessa = 1 " +
                    "and idremessa is not null " +
                    "and dt_sistema <= current_date - interval '2 day' " +
                    "and idparceiro is not null " +
                    "and env_email = 0 " +
                    "and cdpedido is not null " +
                    "order by dt_sistema desc, cdpedido " +
                    "limit 1";
        
        PreparedStatement stmt = conn.prepareStatement(qry);

        ResultSet rs = stmt.executeQuery();
        HashMap map = new HashMap();

    if (rs.next()) {
        map.put("cdpedido", rs.getInt(1));
        UpdateBoleto_pedido(rs.getInt(1), 1);
    } else {
        map.put("cdpedido", 0);
    }
    rs.close();
    stmt.close();
    
    return map;
        
    }
        
}
