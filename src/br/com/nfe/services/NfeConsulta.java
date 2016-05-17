/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 package br.com.nfe.services;

import br.com.nfe.dao.vnd.VND_pedvendaDAO;
import br.com.nfe.dao.lfs.LFS_atualiza_status_nfeDAO;
import br.com.nfe.bean.lfs.LFS_atualiza_status_nfeBean;
import br.com.nfe.bean.vnd.VND_NfpedidoBean;
import br.com.nfe.dao.adm.ADM_ParamDAO;
import br.com.nfe.dao.vnd.VND_nfpedidoDAO;
import static br.com.nfe.services.NfeRecepcao.infnfe;
import br.com.nfe.util.AssinarXMLsCertfificadoA1;
import br.com.nfe.util.KStore;
import br.com.nfe.util.Util;
import br.inf.portalfiscal.nfe.schema.conssitnfe.ObjectFactory;
import br.inf.portalfiscal.nfe.schema.conssitnfe.TConsSitNFe;
import br.inf.portalfiscal.nfe.schema.envinfe.TEnviNFe;
import br.inf.portalfiscal.nfe.schema.envinfe.TNFe;
import br.inf.portalfiscal.nfe.schema.retconssitnfe.TProtNFe;
import br.inf.portalfiscal.nfe.schema.retconssitnfe.TRetConsSitNFe;
import br.inf.portalfiscal.www.nfe.wsdl.consulta.NfeConsulta2Stub;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
 
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import java.net.Authenticator;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.axis2.AxisFault;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

 
/**
 *
 * @author supervisor
 */
public class NfeConsulta {
    
    private String ret[] = new String[3];
    String xml = "";
    String cStat = "";
    private Unmarshaller unmarshaller;
    public VND_nfpedidoDAO nfpedido = null;

    br.com.nfe.gui.Painel main = null;
    static Timer t = new Timer();
    static TimerTask tt;
    static int tempo = 1000 * 30;
    Util u = null;

    public NfeConsulta(br.com.nfe.gui.Painel main) throws Exception {
        this.main = main;
        u = new Util();
    }
    public static void main(String[] args) throws Exception {
        NfeConsulta c = new NfeConsulta(null);
        VND_nfpedidoDAO idnfe = new VND_nfpedidoDAO();
        String retorno[] = idnfe.pesquisa_nfpedido(7321287);
        c.consultanfe(retorno);
    }
    
public void StartTimer() throws Exception{
        main.CarregaJtxa(">>> TimerNfeConsulta...: " + tempo,Color.BLACK);
        tt = new TimerTask(){
          public void run() {
                try {
                    VND_nfpedidoDAO idnfe = new VND_nfpedidoDAO();
                    String retorno[] = idnfe.pesquisa_nfpedido();
                     if (retorno[0].length() > 0){
                       consultanfe(retorno);
                     }
                } catch (Exception ex) {
                    try {
                        main.CarregaJtxa(">>>TimerNfeConsulta não foi executado...", Color.RED);
                    } catch (BadLocationException ex1) {
                        Logger.getLogger(NfeConsulta.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
          }
       };
       t.schedule(tt, 0, tempo);
    }
    public void StopTimer() throws Exception{
        main.CarregaJtxa(">>>Stop TimerNfeConsulta...",Color.BLACK);
        tt.cancel();
    }
 public String Envio(String Xml, URL url, String CUF, String VersaoDados) throws XMLStreamException, AxisFault, RemoteException, BadLocationException{
    String Line = ""; 
    NfeConsulta2Stub.NfeConsultaNF2Result result = null;
        
    OMElement ome = AXIOMUtil.stringToOM(Xml);  

    NfeConsulta2Stub.NfeDadosMsg dadosMsg = new NfeConsulta2Stub.NfeDadosMsg();
        
        dadosMsg.setExtraElement(ome);  
        NfeConsulta2Stub.NfeCabecMsg nfeCabecMsg = new NfeConsulta2Stub.NfeCabecMsg();
        
        nfeCabecMsg.setCUF(CUF);  
        nfeCabecMsg.setVersaoDados(VersaoDados);  
        
        NfeConsulta2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeConsulta2Stub.NfeCabecMsgE();

        nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);  
        
        NfeConsulta2Stub stub = new NfeConsulta2Stub(url.toString());
                
        try {            
            result = stub.nfeConsultaNF2(dadosMsg, nfeCabecMsgE);  
            Line = result.getExtraElement().toString(); 
            try {
                main.CarregaJtxa(Line,Color.red);
            } catch (Exception e) {
                System.out.println(Line);
            }
        } catch (Exception e) {
            try {
                main.CarregaJtxa("Erro NfeConsulta : " + e,Color.RED);
            } catch (Exception e1) {
                System.out.println(e.getMessage());
            }
        }          
        
        return Line;
}   
    
    
 public void consultanfe(String ret[]) throws Exception {
     
     
        String idnfe = ret[0];
        String cdpedido = ret[1];
        String status_nfe = ret[2];
        String xml_nfe = ret[3];
                
         try {
             String codigoDoEstado = idnfe.substring(0, 2);;
             
             /**
              * Endereco WebService
              */

             ADM_ParamDAO dao = new ADM_ParamDAO();
             String[] endereco = dao.pesquisa_webservice("WebserverNfeConsultaV3",codigoDoEstado);
            
             
             URL url = new URL(endereco[0]);
             /**
              * Colocar a Chave de Acesso da NF-e Aqui.
             */

             String chaveDaNFe = idnfe;
              
             KStore k = new KStore();
             k.KeyStore(); 
             
             /**
              * Xml de Consulta.
              */

             
             TConsSitNFe cons = new TConsSitNFe();
             cons.setVersao("3.10");
             cons.setTpAmb(String.valueOf(u.TpAmb()));
             cons.setXServ("CONSULTAR");
             cons.setChNFe(chaveDaNFe);
             
             /* Declaração variaveis de retorno */

            JAXBContext context = null;

            try {
                StringWriter out = new StringWriter();

                context = JAXBContext.newInstance(TConsSitNFe.class);
                Marshaller marshaller = context.createMarshaller();
                JAXBElement<TConsSitNFe> element = new ObjectFactory().createConsSitNFe(cons);
                marshaller.setProperty(
                    javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE
                );
                
                marshaller.marshal(element,new StreamResult(out));

                xml = out.toString();
                
                xml = xml.replace("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
                       
//                AssinarXMLsCertfificadoA1 AssinarXml = new AssinarXMLsCertfificadoA1();    
//                xml = AssinarXml.assinaXml(xml, "assinaInutNFe") ;
               
            } catch (JAXBException e) {
                main.CarregaJtxa(e.toString(),Color.RED);
            }             
             
             
             String retorno = "";
             String separador = ";";
             String obs_nfe = "";
             String nfe = "NFe00";
             String tpAmb = "";
             String verAplic = "";
             String chNFe = "";
             String dhRecbto = "";
             String nProt = "";
             String cStat = "";
             String xMotivo = "";
             String nmArq = "";

             retorno = Envio(xml.toString(), url, codigoDoEstado, endereco[2]);
             
             TRetConsSitNFe retsit = null;
             if (retorno.length() > 0) {
                    context = JAXBContext.newInstance(TRetConsSitNFe.class);  
                    unmarshaller = context.createUnmarshaller();
                    try {
                        retsit = unmarshaller.unmarshal(new StreamSource(new StringReader(retorno)), TRetConsSitNFe.class).getValue();
                    } catch (Exception e) {
                    }
                    
//                    main.CarregaJtxa(retsit.getCStat(),Color.red);



             cStat = retorno.substring(retorno.indexOf("<cStat>") + 7, retorno.indexOf("</cStat>"));
             
             if (Integer.parseInt(cStat) == 100){
                nmArq = "env_" + cdpedido;
             } else if (Integer.parseInt(cStat) == 101){
                nmArq = "can2_" + cdpedido;
             } else if (Integer.parseInt(cStat) == 102){
                nmArq = "inu_" + cdpedido;
             }
                VND_pedvendaDAO atu_pedvenda = new VND_pedvendaDAO();
                
             if (Integer.parseInt(cStat) == 100 || Integer.parseInt(cStat) == 101){
                tpAmb = retorno.substring(retorno.indexOf("<tpAmb>") + 7, retorno.indexOf("</tpAmb>"));
                verAplic = retorno.substring(retorno.indexOf("<verAplic>") + 10, retorno.indexOf("</verAplic>"));
                chNFe = retorno.substring(retorno.indexOf("<chNFe>") + 7, retorno.indexOf("</chNFe>"));
                dhRecbto = retorno.substring(retorno.indexOf("<dhRecbto>") + 10, retorno.indexOf("</dhRecbto>"));
                nProt = retorno.substring(retorno.indexOf("<nProt>") + 7, retorno.indexOf("</nProt>"));
                xMotivo = retorno.substring(retorno.indexOf("<xMotivo>") + 9, retorno.indexOf("</xMotivo>"));

                obs_nfe = nfe + separador + tpAmb + separador + verAplic + separador + chNFe + separador + dhRecbto;
                obs_nfe +=  separador + nProt + separador + separador + cStat + separador + xMotivo + separador + "1" + separador + separador;
                
                if (Integer.parseInt(status_nfe) == 539){
                    VND_nfpedidoDAO pedido_dao = new VND_nfpedidoDAO();
                    VND_NfpedidoBean pedido_bean = new VND_NfpedidoBean();                                     
                    TProtNFe protnfe = new TProtNFe();

                    int cstat = Integer.parseInt(retsit.getCStat());
                    pedido_bean.setObs_nfe(null);
                    pedido_bean.setStatus_nfe(cstat);
                    pedido_bean.setCdpedido(Integer.parseInt(cdpedido));
                    pedido_dao.Update_nfpedido_env(pedido_bean);

                    HashMap pedido_hm = new HashMap();
                    pedido_hm = pedido_dao.pesquisa_nfpedido_cdpedido(Integer.parseInt(cdpedido));

                    try {
                       MontaXML(pedido_hm);
                    } catch (Exception e) {
                       main.CarregaJtxa(e.toString(),Color.RED);
                    }

                       TEnviNFe envnfe = new TEnviNFe();
                       envnfe.setIdLote("1");

                       TNFe Tnfe = new TNFe();

                       Tnfe.setInfNFe(infnfe);

                       envnfe.getNFe().add(Tnfe);             
                       envnfe.setVersao("3.10");
                       envnfe.setIndSinc("0");

                       context = null;

                      try {
                          StringWriter out = new StringWriter();

                          context = JAXBContext.newInstance(TEnviNFe.class);
                          Marshaller marshaller = context.createMarshaller();
                          JAXBElement<TEnviNFe> element = new br.inf.portalfiscal.nfe.schema.envinfe.ObjectFactory().createEnviNFe(envnfe);

                          marshaller.setProperty(
                              javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT,
                              Boolean.TRUE
                          );

                          marshaller.marshal(element,new StreamResult(out));

                          xml = out.toString();

                          xml = pedido_dao.remove_acento(xml);

                          xml = xml.replace("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");

              //            System.out.println(xml);

                          AssinarXMLsCertfificadoA1 AssinarXml = new AssinarXMLsCertfificadoA1();    
                          xml = AssinarXml.assinaXml(xml, "assinaEnvNFe") ;

                      } catch (JAXBException e) {
                          System.out.println(e);
                      }                    
                    
                    protnfe.setInfProt(retsit.getProtNFe().getInfProt());

                    cstat = Integer.parseInt(protnfe.getInfProt().getCStat());
                    String protocolo = protnfe.getInfProt().getNProt();

                    Document document = documentFactory(xml);
                    NodeList nodeListNfe = document.getDocumentElement().getElementsByTagName("NFe");
                    NodeList nodeListInfNfe = document.getElementsByTagName("infNFe");

                    Element el = (Element) nodeListInfNfe.item(0);
                    String chaveNFe = el.getAttribute("Id");

                    String xmlNFe = outputXML(nodeListNfe.item(0));
                    String xmlProtNFe = getProtNFe(retorno, chaveNFe);

                    pedido_bean.setXml_nfe(buildNFeProc(xmlNFe, xmlProtNFe));
                    pedido_bean.setProtocolo(protocolo);
                    pedido_bean.setStatus_nfe(cstat);
                    pedido_bean.setSituacaonf("N");
                    
                    try {
                        main.CarregaJtxa(">>> Update Consulta RecepcaoNfe....:" + cdpedido,Color.BLACK);
                    } catch (Exception e) {
                    }
                    
                    pedido_dao.Update_nfpedido_env(pedido_bean);

                }
                
             } else if (Integer.parseInt(cStat) == 562){
                String retChNFe = "<retConsSitNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" versao=\"3.10\"><tpAmb>1</tpAmb><verAplic>GO3.0</verAplic><cStat>562</cStat><xMotivo>Rejeição: Código numérico informado na Chave de Acesso difere do Código Númerico da NF-e[chNFe:52150202233732000157550000001184571072418203]</xMotivo><cUF>52</cUF><dhRecbto>2015-02-25T08:57:58-03:00</dhRecbto><chNFe>52150202233732000157550000001184574072418208</chNFe></retConsSitNFe>";
                retChNFe = retorno.substring(retorno.indexOf("NF-e[chNFe:") + "NF-e[chNFe:".length(), retorno.indexOf("]</xMotivo>"));
                
                boolean ok = atu_pedvenda.update_idnfe(Integer.parseInt(cdpedido), retChNFe);
                
             } else {
                tpAmb = retorno.substring(retorno.indexOf("<tpAmb>") + 7, retorno.indexOf("</tpAmb>"));
                verAplic = retorno.substring(retorno.indexOf("<verAplic>") + 10, retorno.indexOf("</verAplic>"));
                chNFe = retorno.substring(retorno.indexOf("<chNFe>") + 7, retorno.indexOf("</chNFe>"));
                xMotivo = retorno.substring(retorno.indexOf("<xMotivo>") + 9, retorno.indexOf("</xMotivo>"));

                obs_nfe = nfe + separador + tpAmb + separador + verAplic + separador + chNFe + separador + dhRecbto;
                obs_nfe +=  separador + nProt + separador + separador + cStat + separador + xMotivo + separador + "1" + separador + separador;
             }

             //System.out.println(">>>Retorno Sefaz...: " + resultado);
             //System.out.println(">>>Obs_nfe Sefaz...: " + obs_nfe);

             if (obs_nfe.length() > 0) {
                  LFS_atualiza_status_nfeBean bean = new LFS_atualiza_status_nfeBean();
                  LFS_atualiza_status_nfeDAO bk = new LFS_atualiza_status_nfeDAO();

                  
                  int numpedido = 0 ;
                  Boolean atupedido = false;
                  
                  if (nmArq.length() > 0){
                    numpedido = bk.set_atualiza_status_nfe(nmArq, obs_nfe);
                    if (numpedido > 0) {
                        //System.out.println("Gravou pedido: " + numpedido);
                        if (Integer.parseInt(retsit.getCStat()) == 101){
                            try {
                                atupedido = atu_pedvenda.atualiza_pedvenda(numpedido, 7);
                                main.CarregaJtxa("Atualizou cancelamento Pedido",Color.orange);
                            } catch (Exception e) {
                                   main.CarregaJtxa(e.toString(),Color.RED);
                            }
                        }
                    }
                 }
             }
          }
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
 private void MontaXML(HashMap pedido_hm) throws Exception{
    
    infnfe = new TNFe.InfNFe();
    
    int cdpedido = Integer.parseInt(pedido_hm.get("cdpedido").toString());
     
    //A - Dados da Nota Fiscal eletrônica

    infnfe.setVersao("3.10");      
    infnfe.setId("NFe"+ pedido_hm.get("idnfe")); // chave na nfe
    
    //B - Identificação da Nota Fiscal eletrônica
    nfpedido.pesquisa_nfpedido_env_ide(cdpedido);

    //C - Identificação do Emitente da Nota Fiscal eletrônica 
    nfpedido.pesquisa_nfpedido_env_emit(cdpedido);

    //D - Identificação do Fisco Emitente da NF-e
    
    //E - Identificação do Destinatário da Nota Fiscal eletrônica
    nfpedido.pesquisa_nfpedido_env_dest(cdpedido);    

    //F - Identificação do Local de Retirada
    //G - Identificação do Local de Entrega                 
    
    //H - Detalhamento de Produtos e Serviços da NF-e
    nfpedido.pesquisa_nfpedido_env_det(cdpedido);
    
    //W - Valores Totais da NF-e
    nfpedido.pesquisa_nfpedido_env_total(cdpedido);

    //X - Informações do Transporte da NF-e
    nfpedido.pesquisa_nfpedido_env_transp(cdpedido);

    //Y – Dados da Cobrança
    nfpedido.pesquisa_nfpedido_env_cob(cdpedido);
    
    //Z - Informações Adicionais da NF-e
    nfpedido.pesquisa_nfpedido_env_infadic(cdpedido);
    
     
 }
    private static Document documentFactory(String xml) throws SAXException,  
            IOException, ParserConfigurationException {  
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        factory.setNamespaceAware(true);  
        Document document = factory.newDocumentBuilder().parse(  
                new ByteArrayInputStream(xml.getBytes()));  
        return document;  
    }
    private static String buildNFeProc(String xmlNFe, String xmlProtNFe) {  
        StringBuilder nfeProc = new StringBuilder();  
        nfeProc.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")  
            .append("<nfeProc versao=\"2.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")  
            .append(xmlNFe)  
            .append(xmlProtNFe)  
            .append("</nfeProc>");  
        return nfeProc.toString();  
    }
    private static String outputXML(Node node) throws TransformerException {  
        ByteArrayOutputStream os = new ByteArrayOutputStream();  
        TransformerFactory tf = TransformerFactory.newInstance();  
        Transformer trans = tf.newTransformer();  
        trans.transform(new DOMSource(node), new StreamResult(os));  
        String xml = os.toString();  
        if ((xml != null) && (!"".equals(xml))) {  
            xml = xml.replaceAll("<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>", "");  
        }  
        return xml;  
    }
    private static String getProtNFe(String xml, String chaveNFe) throws SAXException,   
        IOException, ParserConfigurationException, TransformerException {  
        Document document = documentFactory(xml);  
        NodeList nodeListProtNFe = document.getDocumentElement().getElementsByTagName("protNFe");  
        NodeList nodeListChNFe = document.getElementsByTagName("chNFe");  
        for (int i = 0; i < nodeListProtNFe.getLength(); i++) {  
            Element el = (Element) nodeListChNFe.item(i);  
            String chaveProtNFe = el.getTextContent();  
            if (chaveNFe.contains(chaveProtNFe)) {  
                return outputXML(nodeListProtNFe.item(i));  
            }  
        }         
        return "";  
    }    
}

