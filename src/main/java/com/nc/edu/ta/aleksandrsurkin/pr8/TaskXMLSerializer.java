package com.nc.edu.ta.aleksandrsurkin.pr8;

import com.nc.edu.ta.aleksandrsurkin.pr6.AbstractTaskList;
import com.nc.edu.ta.aleksandrsurkin.pr6.ArrayTaskList;
import com.nc.edu.ta.aleksandrsurkin.pr6.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class TaskXMLSerializer {
    public static void main(String[] args) {
        AbstractTaskList massive = new ArrayTaskList();
        Task task1 = new Task("Проснись и иди на работу", 28800, 35000, 0);
        task1.setActive(true);
        massive.add(task1);
        Task task2 = new Task("Не забудь пообедать", 48800);
        task2.setActive(true);
        massive.add(task2);
        Task task3 = new Task("Не забудь сходить домой", 30000, 30000, 64800);
        task3.setActive(true);
        massive.add(task3);
        TaskXMLSerializer.save(massive, "my_tasks_list");
        ArrayTaskList myTasksList = (ArrayTaskList) load("my_tasks_list");
        myTasksList.add(new Task("Не забудь поспать", 35000, 45000, 38000));
        TaskXMLSerializer.save(myTasksList, "do_not_forget_to_sleep");

    }

    /**
     * This method is create XML file of tasks list.
     * @param object input tasks list.
     * @param file name of saving XML file.
     */
    public static void save(AbstractTaskList object, String file) {
        String savePath = "src/main/resources/" + file + ".xml";
        try {
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            Element root = doc.createElement("tasks");                                              //создаёт root - элемент
            doc.appendChild(root);

            for (Task elem : object) {
                Element task = doc.createElement("task");
                String elemIsActive = elem.isActive() ?  "true" :  "false";
                String elemIsRepeated = elem.isRepeated() ? "true" : "false";
                task.setAttribute("active", elemIsActive);
                task.setAttribute("time", Integer.toString(elem.getTime()));
                task.setAttribute("start", Integer.toString(elem.getStartTime()));
                task.setAttribute("end", Integer.toString(elem.getEndTime()));
                task.setAttribute("repeat", Integer.toString(elem.getRepeatInterval()));
                task.setAttribute("repeated", elemIsRepeated);
                task.appendChild(doc.createTextNode(String.valueOf((elem.getTitle()))));
                root.appendChild(task);
            }

            //сохранение в файл xml
            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();
            aTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");                              //задаёт кодировку в XML
            aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");    //создаёт 4 пробела перед дочерним элементом тэга
            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");                                  //создает перенос строки

            DOMSource source = new DOMSource(doc);
            try {
                FileWriter fos = new FileWriter(savePath);
                StreamResult result = new StreamResult(fos);
                aTransformer.transform(source, result);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is creating tasks list object from loaded XML file.
     * @param file name of XML file.
     * @return tasks list.
     */
    public static AbstractTaskList load(String file) {

        boolean activeBooleanValue;

        String loadPath = "src/main/resources/" + file + ".xml";

        File loadedXmlFile = new File(loadPath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = null;

        try {
             doc = dbf.newDocumentBuilder().parse(loadedXmlFile);
        } catch (Exception e) {
            System.out.println("Open parsing error " + e.toString());
        }

        Node rootNode = doc.getFirstChild();

        AbstractTaskList tasks = new ArrayTaskList();

        NodeList rootChilds = rootNode.getChildNodes();
        for (int i = 0; i < rootChilds.getLength(); i++) {
            if (rootChilds.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
                String anyTitle = rootChilds.item(i).getTextContent();
                int anyTimeInt = Integer.parseInt(rootChilds.item(i).getAttributes().getNamedItem("start").getNodeValue());
                int anyEndInt = Integer.parseInt(rootChilds.item(i).getAttributes().getNamedItem("end").getNodeValue());
                int anyRepeatInt = Integer.parseInt(rootChilds.item(i).getAttributes().getNamedItem("repeat").getNodeValue());
                String anyActiveString = rootChilds.item(i).getAttributes().getNamedItem("active").getNodeValue();
                if (anyActiveString.equals("true")) {
                    activeBooleanValue = true;
                } else {
                    activeBooleanValue = false;
                }

                Task task = new Task(anyTitle, anyTimeInt, anyEndInt, anyRepeatInt);
                task.setActive(activeBooleanValue);
                tasks.add(task);

            }
            return tasks;
        }
    }

