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

            root.appendChild(this.createElementWithTextNode("ProjectExternallyEdited", "0"));
            root.appendChild(this.createElementWithTextNode("DurationFormat", "5"));
            root.appendChild(this.createElementWithTextNode("StartDate", c.getSprints().get(0).getIndex().getStart()));

            
            Element tasks = doc.createElement("Tasks");
            Element task;
            
            Element assignments = doc.createElement("Assignments");
            Element assignment;

            Element predecessor;
            

            int countTask = 0;
            int countAssignment = 0;
            int predecessorId = -1;

            for(Sprint s : c.getSprints()){
                countTask++;
                task = doc.createElement("Task");
                task.appendChild(this.createElementWithTextNode("UID", countTask + ""));
                task.appendChild(this.createElementWithTextNode("ID", countTask + ""));
                task.appendChild(this.createElementWithTextNode("Name", s.getName()));
                task.appendChild(this.createElementWithTextNode("Manual", "1"));
                task.appendChild(this.createElementWithTextNode("OutlineLevel", s.getOutlineLvl() + ""));
                task.appendChild(this.createElementWithTextNode("ManualStart", s.index.getStart()));
                task.appendChild(this.createElementWithTextNode("ManualFinish", s.index.getEnd()));

                // 2. Sprint and above
                if (predecessorId > -1) {
                    predecessor = doc.createElement("PredecessorLink");
                    predecessor.appendChild(this.createElementWithTextNode("PredecessorUID", predecessorId + ""));

                    task.appendChild(predecessor);

                }
                predecessorId = countTask;

                tasks.appendChild(task);

                for (Phase p : s.getPhases()) {
                    countTask++;
                    task = doc.createElement("Task");
                    task.appendChild(this.createElementWithTextNode("UID", countTask + ""));
                    task.appendChild(this.createElementWithTextNode("ID", countTask + ""));
                    task.appendChild(this.createElementWithTextNode("Name", p.getName()));
                    task.appendChild(this.createElementWithTextNode("Manual", "0"));
                    task.appendChild(this.createElementWithTextNode("OutlineLevel", p.getOutlineLvl() + ""));

                    tasks.appendChild(task);

                    for (Task t : p.getTasks()) {
                        countTask++;
                        
                        task = doc.createElement("Task");
                        task.setAttribute("key", t.getKey());
                        task.appendChild(this.createElementWithTextNode("UID", countTask + ""));
                        task.appendChild(this.createElementWithTextNode("ID", countTask + ""));
                        task.appendChild(this.createElementWithTextNode("Name", t.getName()));
                        task.appendChild(this.createElementWithTextNode("Manual", "0"));
                        task.appendChild(this.createElementWithTextNode("OutlineLevel", t.getOutlineLvl() + ""));
                        task.appendChild(this.createElementWithTextNode("Duration", t.getDurationString()));
                        task.appendChild(this.createElementWithTextNode("Estimated", "0"));

                        tasks.appendChild(task);
                        
                        // If exists define predecessors

                        // This is useless, because you cant find out how it is
                        // linked:
                        // related, blocked, ect...

                        // for (String lt : t.getLinkedTasks()) {
                        // predecessor = doc.createElement("PredecessorLink");
                        // predecessor.appendChild(
                        // this.createElementWithTextNode("PredecessorUID",
                        // c.getTaskToId().get(lt) + ""));
                        //
                        // task.appendChild(predecessor);
                        // }
                        
                        if (t.getAssignee() > 1) {
                            countAssignment++;
                            assignment = doc.createElement("Assignment");
                            assignment.appendChild(this.createElementWithTextNode("UID", countAssignment + ""));
                            assignment.appendChild(this.createElementWithTextNode("TaskUID", countTask + ""));
                            assignment.appendChild(this.createElementWithTextNode("ResourceUID", t.getAssignee() + ""));
                            assignment.appendChild(
                                    this.createElementWithTextNode("RemainingWork", t.getDurationString()));
                            assignment.appendChild(this.createElementWithTextNode("Units", "1"));
                            assignment.appendChild(this.createElementWithTextNode("Work", t.getDurationString()));

                            assignments.appendChild(assignment);
                        } else {
                            for (int i = 1; i < c.getAssignees().size(); i++) {
                                countAssignment++;
                                assignment = doc.createElement("Assignment");
                                assignment.appendChild(this.createElementWithTextNode("UID", countAssignment + ""));
                                assignment.appendChild(this.createElementWithTextNode("TaskUID", countTask + ""));
                                assignment.appendChild(this.createElementWithTextNode("ResourceUID", (i + 1) + ""));
                                assignment.appendChild(
                                        this.createElementWithTextNode("RemainingWork", t.getDurationString()));
                                assignment.appendChild(this.createElementWithTextNode("Units", "1"));
                                assignment.appendChild(this.createElementWithTextNode("Work",
                                        t.splitDuration(c.getAssignees().size() - 1)));

                                assignments.appendChild(assignment);
                            }
                        }
                        
                    }
                }
            }
            
            Element resources = doc.createElement("Resources");
            Element resource;
            for (String r : c.getAssignees()){
                resource = doc.createElement("Resource");
                int id = c.getAssignees().indexOf(r) + 1;
                resource.appendChild(this.createElementWithTextNode("UID", id + ""));
                resource.appendChild(this.createElementWithTextNode("ID", id + ""));
                resource.appendChild(this.createElementWithTextNode("Name", r));
                resources.appendChild(resource);
            }

            root.appendChild(tasks);
            root.appendChild(resources);
            root.appendChild(assignments);
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
