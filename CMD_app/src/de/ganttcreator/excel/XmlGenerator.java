package de.ganttcreator.excel;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlGenerator {

    Converter c;
    Document doc;

    public XmlGenerator(Converter c) {
        this.c = c;
    }

    public Element createElementWithTextNode(String element, String text) {
        Element e = doc.createElement(element);
        e.setTextContent(text);
        return e;
    }

    public void generateXml() {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser;
            parser = factory.newDocumentBuilder();
            doc = parser.newDocument();
            doc.setXmlStandalone(true);
            doc.setXmlVersion("1.0");

            // root "Project"
            Element root = doc.createElementNS("http://schemas.microsoft.com/project", "Project");
            root.setAttribute("xmlns", "http://schemas.microsoft.com/project");
            doc.appendChild(root);

            Element e;

            root.appendChild(this.createElementWithTextNode("ProjectExternallyEdited", "0"));
            root.appendChild(this.createElementWithTextNode("DurationFormat", "5"));

            int count=0;
            
            Element tasks = doc.createElement("Tasks");
            
            Element task;

            for(Sprint s : c.getSprints()){
                count++;
                task = doc.createElement("Task");
                task.appendChild(this.createElementWithTextNode("UID", count + ""));
                task.appendChild(this.createElementWithTextNode("ID", count + ""));
                task.appendChild(this.createElementWithTextNode("Name", s.getName()));
                task.appendChild(this.createElementWithTextNode("Manual", "0"));
                task.appendChild(this.createElementWithTextNode("OutlineLevel", s.getOutlineLvl() + ""));


                tasks.appendChild(task);

                for (Phase p : s.getPhases()) {
                    count++;
                    task = doc.createElement("Task");
                    task.appendChild(this.createElementWithTextNode("UID", count + ""));
                    task.appendChild(this.createElementWithTextNode("ID", count + ""));
                    task.appendChild(this.createElementWithTextNode("Name", p.getName()));
                    task.appendChild(this.createElementWithTextNode("Manual", "0"));
                    task.appendChild(this.createElementWithTextNode("OutlineLevel", p.getOutlineLvl() + ""));

                    tasks.appendChild(task);

                    for (Task t : p.getTasks()) {
                        count++;
                        task = doc.createElement("Task");
                        task.appendChild(this.createElementWithTextNode("UID", count + ""));
                        task.appendChild(this.createElementWithTextNode("ID", count + ""));
                        task.appendChild(this.createElementWithTextNode("Name", t.getName()));
                        task.appendChild(this.createElementWithTextNode("Manual", "0"));
                        task.appendChild(this.createElementWithTextNode("OutlineLevel", t.getOutlineLvl() + ""));
                        task.appendChild(this.createElementWithTextNode("Duration", t.getDurationString()));
                        task.appendChild(this.createElementWithTextNode("Estimated", "0"));

                        tasks.appendChild(task);
                    }
                }
            }
            
            
            
            
            
            root.appendChild(tasks);
            
            // XML-Output
            TransformerFactory tFactory = TransformerFactory.newInstance();

            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

            Source source = new DOMSource(doc);

            File output = new File(c.getMyFile().getParent(), "output.xml");
            if (output.exists()) {
                output.createNewFile();
            }
            Result result = new StreamResult(output);

            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
