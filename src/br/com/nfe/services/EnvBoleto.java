/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfe.services;

import br.com.nfe.dao.bco.BCO_BoletoDAO;
import java.awt.Color;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;

/**
 *
 * @author root
 */
public class EnvBoleto {
    
    static Timer t = new Timer();
    static TimerTask tt;
    static int tempo = 1000 * 30;
    BCO_BoletoDAO daoBoleto;
    HashMap pedido_hm;
    
    br.com.nfe.gui.Painel main = null;

    public EnvBoleto(br.com.nfe.gui.Painel main) throws Exception {
        this.main = main;
        daoBoleto = new BCO_BoletoDAO();
    }
    public static void main(String[] args) throws SQLException, Exception {
        HashMap pedido_hm = new HashMap();
        BCO_BoletoDAO daoBoleto = new BCO_BoletoDAO();
        pedido_hm = daoBoleto.ConsultaBoleto_pedido();
        ControleImpressao c = new ControleImpressao(null);
        // 0 no terceiro parametro envia so a nota
        // 1 no terceiro parametro envia a nota e o boleto
        if (Integer.parseInt(pedido_hm.get("cdpedido").toString()) > 0){
            c.PesquisaXML(Integer.parseInt(pedido_hm.get("cdpedido").toString()), null,1);
        }
    }
    
    public void StartTimer() throws Exception{
        main.CarregaJtxa(">>> TimerEnvBoleto...: " + tempo,Color.BLACK);
        tt = new TimerTask(){
          public void run() {
                try {
                    pedido_hm = new HashMap();
                    pedido_hm = daoBoleto.ConsultaBoleto_pedido();
                    ControleImpressao c = new ControleImpressao(main);
                    // 0 no terceiro parametro envia so a nota
                    // 1 no terceiro parametro envia a nota e o boleto
                    if (Integer.parseInt(pedido_hm.get("cdpedido").toString()) > 0){
                        c.PesquisaXML(Integer.parseInt(pedido_hm.get("cdpedido").toString()), null,1);
                    }
                } catch (Exception ex) {
                    try {
                        daoBoleto.UpdateBoleto_pedido(Integer.parseInt(pedido_hm.get("cdpedido").toString()), 9);
                    } catch (SQLException ex1) {
                        try {
                            main.CarregaJtxa(">>>TimerEnvBoleto não foi executado... " + ex1, Color.BLACK);
                        } catch (BadLocationException ex2) {
                            ex2.printStackTrace();
                        }
                    }
                    try {
                        main.CarregaJtxa(">>>TimerEnvBoleto não foi executado... " + ex, Color.BLACK);
                    } catch (BadLocationException ex3) {
                        ex3.printStackTrace();
                    }
                }
          }
       };
       t.schedule(tt, 0, tempo);
    }
    public void StopTimer() throws Exception{
        try {
            main.CarregaJtxa(">>>Stop TimerEnvBoleto...",Color.BLACK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tt.cancel();
    }
    
}
