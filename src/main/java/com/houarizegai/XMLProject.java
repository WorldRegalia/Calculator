/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlproject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author User
 */
public class XMLProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        StaxWriter configFile = new StaxWriter();
        configFile.setFile("config2.xml");
        
        try {
            generatePOM();
            configFile.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void generatePOM() throws TransformerException {
        try {
         StringWriter stringWriter = new StringWriter();

         XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
         XMLStreamWriter xMLStreamWriter =
         xMLOutputFactory.createXMLStreamWriter(stringWriter);
         
         xMLStreamWriter.writeStartDocument();
         xMLStreamWriter.writeStartElement("project");
         xMLStreamWriter.writeAttribute("xmlns", "http://maven.apache.org/POM/4.0.0");
         xMLStreamWriter.writeAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
         xMLStreamWriter.writeAttribute("xsi:schemaLocation", "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd");
   
         xMLStreamWriter.writeStartElement("modelVersion");	
         xMLStreamWriter.writeCharacters("4.0.0");
         xMLStreamWriter.writeEndElement();
      
         xMLStreamWriter.writeStartElement("groupId");
         xMLStreamWriter.writeCharacters("com.interactive.ivaap"); //Pass in groupdId?
         xMLStreamWriter.writeEndElement();

         xMLStreamWriter.writeStartElement("artifactId");			
         xMLStreamWriter.writeCharacters("IVAAPData"); // Pass in project name
         xMLStreamWriter.writeEndElement();
         
         xMLStreamWriter.writeStartElement("version");			
         xMLStreamWriter.writeCharacters("1.0-SNAPSHOT");
         xMLStreamWriter.writeEndElement();
         
         xMLStreamWriter.writeStartElement("packaging");			
         xMLStreamWriter.writeCharacters("jar"); //jar, pom, war, etc.
         xMLStreamWriter.writeEndElement();
         
         xMLStreamWriter.writeStartElement("dependencies");
         
         
         // Close dependencies
         xMLStreamWriter.writeEndElement();

         xMLStreamWriter.writeEndElement();
         xMLStreamWriter.writeEndDocument();

         xMLStreamWriter.flush();
         xMLStreamWriter.close();

         String xmlString = stringWriter.getBuffer().toString();

         stringWriter.close();
         
         Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        t.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
        Writer out = new StringWriter();
        OutputStream os = Files.newOutputStream(Paths.get("C:\\Users\\User\\Documents\\pom.xml"));
        t.transform(new StreamSource(new StringReader(xmlString)), new StreamResult(os));
        os.close();

         System.out.println(out.toString());
         System.out.println(xmlString);

      } catch (XMLStreamException e) {
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XMLProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
