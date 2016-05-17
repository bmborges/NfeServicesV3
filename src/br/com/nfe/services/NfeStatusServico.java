/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfe.services;

import br.com.nfe.bean.adm.ADM_ParamBean;
import br.com.nfe.dao.adm.ADM_ParamDAO;
import br.com.nfe.gui.Painel;
import br.com.nfe.util.DtSystem;
import br.com.nfe.util.FormataValores;
import br.com.nfe.util.KStore;
import br.com.nfe.util.Util;
import br.inf.portalfiscal.nfe.schema.conssitnfe.TConsSitNFe;
import br.inf.portalfiscal.nfe.schema.consstatserv.ObjectFactory;
import br.inf.portalfiscal.nfe.schema.consstatserv.TConsStatServ;
import br.inf.portalfiscal.nfe.schema.consstatserv.TRetConsStatServ;
import br.inf.portalfiscal.www.nfe.wsdl.statusservico.NfeStatusServico2Stub;
import java.awt.Color;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.Security;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axis2.AxisFault;

/**
 *
 * @author supervisor
 */
public class NfeStatusServico{
    
    TConsStatServ statserv = null;
    String xml = "";
    private Unmarshaller unmarshaller;

    static Timer t = new Timer();
    static TimerTask tt;
    static int tempo = 1000 * 60 * 3;
    Util u = null;

    br.com.nfe.gui.Painel main = null;

    public NfeStatusServico(br.com.nfe.gui.Painel main) throws Exception {
        this.main = main;
        u = new Util();
    }
    public static void main(String[] args) throws Exception {
        NfeStatusServico s = new NfeStatusServico(null);
        s.Status();
    }
    
    /**
     * @param args the command line arguments
     */

public String Envio(String Xml, URL url, String CUF, String VersaoDados) throws XMLStreamException, AxisFault, RemoteException, BadLocationException{
    
    String Line = "";
    NfeStatusServico2Stub.NfeStatusServicoNF2Result result = null; 
    OMElement ome = AXIOMUtil.stringToOM(Xml);  
            
    NfeStatusServico2Stub.NfeDadosMsg dadosMsg = new NfeStatusServico2Stub.NfeDadosMsg();  
    dadosMsg.setExtraElement(ome);  
    NfeStatusServico2Stub.NfeCabecMsg nfeCabecMsg = new NfeStatusServico2Stub.NfeCabecMsg();  
    nfeCabecMsg.setCUF(CUF);  
    nfeCabecMsg.setVersaoDados(VersaoDados);  
    NfeStatusServico2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeStatusServico2Stub.NfeCabecMsgE();  
    nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);  
    NfeStatusServico2Stub stub = new NfeStatusServico2Stub(url.toString());

    try {            
        result = stub.nfeStatusServicoNF2(dadosMsg, nfeCabecMsgE);  
        Line = result.getExtraElement().toString(); 
    } catch (Exception e) {
        main.CarregaJtxa("Erro NfeStatusServico : " + e,Color.RED);
        try {
            u.UpdateTpEmiss(4);
        } catch (SQLException ex) {
            Logger.getLogger(NfeStatusServico.class.getName()).log(Level.SEVERE, null, ex);
        }
     } 
    return Line;
}
    public void StartTimer() throws Exception{
        main.CarregaJtxa(">>> TimerNfeStatusServico...: " + tempo,Color.BLACK);
        tt = new TimerTask(){
          public void run() {
                try {
                   Status();
                } catch (Exception ex) {
                    try {
                        main.CarregaJtxa(">>>TimerNfeStatusServico não foi executado...", Color.BLACK);
                    } catch (BadLocationException ex1) {
                        Logger.getLogger(NfeStatusServico.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
          }
       };
       t.schedule(tt, 0, tempo);
    }
    public void StopTimer() throws Exception{
        main.CarregaJtxa(">>>Stop TimerNfeStatusServico...",Color.BLACK);
        tt.cancel();
    }

    public void Status() throws Exception{
    // TODO code application logic here
    String resultado = "";
    
        ADM_ParamDAO dao = new ADM_ParamDAO();
        ADM_ParamBean bean = new ADM_ParamBean();
            
        String[] endereco = null;
        endereco = dao.pesquisa_webservice("WebserverNfeStatusServicoV3","52");

        if (endereco[0] == null || endereco[0].length() <= 0) {
            main.CarregaJtxa("Endereço Status Serviço não Localizado",Color.MAGENTA);
            return;
        }
        try {
           
            URL url = new URL(endereco[0]);

            KStore k = new KStore();
            k.KeyStore(); 
           
            try {
                MontaXML();
            } catch (Exception e) {
                main.CarregaJtxa("Erro MontaXml NfeStatusServico " + e,Color.RED);
            }
            
            JAXBContext context = null;

            try {
                StringWriter out = new StringWriter();

                //context = JAXBContext.newInstance("br.inf.portalfiscal.nfe.consserv");
                context = JAXBContext.newInstance(TConsStatServ.class);
                Marshaller marshaller = context.createMarshaller();  
                JAXBElement<TConsStatServ> element = new ObjectFactory().createConsStatServ(statserv);  
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);  
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
                
                marshaller.marshal(element,new StreamResult(out));

                xml = out.toString();
               
            } catch (JAXBException e) {
                try {
                    main.CarregaJtxa(e.toString(),Color.RED);
                } catch (Exception e1) {
                    System.out.println(e.toString());
                }
            }
            
            String retorno = Envio(xml, url, endereco[1], endereco[2]);

            if (retorno.length() > 0){
                context = JAXBContext.newInstance(TRetConsStatServ.class);  
                unmarshaller = context.createUnmarshaller();
                TRetConsStatServ retStatServ  = unmarshaller.unmarshal(new StreamSource(new StringReader(retorno)), TRetConsStatServ.class).getValue();
                
                if(Integer.valueOf(retStatServ.getCStat()) == 107){
                    u.UpdateTpEmiss(1);
                } else {
                    u.UpdateTpEmiss(4);
                }
                
                try {
                    main.CarregaJtxa(">>> NfeStatusServico....: " + retStatServ.getCStat(),Color.BLUE);
                } catch (Exception e) {
                    System.out.println(">>> NfeStatusServico....: " + retStatServ.getCStat());
                }
            }
        } catch (Exception e) {

            if(main != null){
                main.CarregaJtxa(e.toString(),Color.RED);
            } else {
                System.out.println(e);
            }

        }
        
    }
private void MontaXML() throws Exception{
    
    /** 
     * Estrutura XML Consulta Status Servico. 
     */  
    statserv = new TConsStatServ();  
    /* 
     * Versão do leiaute. 
     */  
    statserv.setVersao("3.10");  
    statserv.setTpAmb(String.valueOf(u.TpAmb()));
    statserv.setCUF("52");
    statserv.setXServ("STATUS");
  
     
 }


}
