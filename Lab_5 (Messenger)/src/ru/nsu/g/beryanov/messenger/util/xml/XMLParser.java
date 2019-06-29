package ru.nsu.g.beryanov.messenger.util.xml;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.nsu.g.beryanov.messenger.util.Letter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

class XMLParser {

    public static Letter parseIn(String xml) {
        Document document;
        if (xml.contains("<?xml") && xml.contains("?>")) {
            try {
                document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
                String letterName = document.getDocumentElement().getAttribute("name");
                Element root = document.getDocumentElement();
                Letter letter = new Letter(letterName);
                if (root.getTagName().equals("Error")) {
                    letter = new Letter("Error");
                    letter.getBody().put("message", "Error: " + root.getTextContent());
                    return letter;
                } else if (root.getTagName().equals("success")) {
                    NodeList nodeList = root.getElementsByTagName("listusers");
                    if (nodeList.getLength() > 0) {
                        letter = new Letter("listusers");
                        ArrayList<String> userNames = new ArrayList<>();
                        NodeList users = nodeList.item(0).getChildNodes();
                        for (int i = 0; i < users.getLength(); i++)
                            userNames.add(((Element) users.item(i)).getElementsByTagName("name").item(0).getTextContent());
                        letter.getBody().put("listusers", String.join(",", userNames));
                        return letter;
                    }
                } else {
                    switch (letterName) {
                        case "login":
                            letter.getBody().put("name", document.getDocumentElement().getElementsByTagName("name").item(0).getTextContent());
                            break;
                        case "logout":
                            letter.getBody().put("name", document.getDocumentElement().getElementsByTagName("name").item(0).getTextContent());
                            break;
                        case "message":
                            letter.getBody().put("name", document.getDocumentElement().getElementsByTagName("name").item(0).getTextContent());
                            letter.getBody().put("message", document.getDocumentElement().getElementsByTagName("message").item(0).getTextContent());
                            break;
                        case "listusers":
                            break;
                    }
                    return letter;
                }
            } catch (SAXException | IOException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String parseOut(Letter letter) {
        switch (letter.getName()) {
            case "logout":
                return parseLogout(letter);
            case "login":
                return parseLogin(letter);
            case "message":
                return parseMessage(letter);
            case "listusers":
                return parseList(letter);
        }
        return null;
    }

    private static String parseLogout(Letter letter) {
        Document document;
        String xml = "";
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = document.createElement("event");
            document.appendChild(root);
            Attr attr = document.createAttribute("name");
            attr.setValue("logout");
            root.setAttributeNode(attr);
            //name element
            Element elementName = document.createElement("name");
            elementName.setTextContent(letter.getBody().get("name"));
            root.appendChild(elementName);
            //transform
            xml = docToString(document);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return xml;
    }

    private static String parseLogin(Letter letter) {
        Document document;
        String xml = "";
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = document.createElement("event");
            document.appendChild(root);
            Attr attr = document.createAttribute("name");
            attr.setValue("login");
            root.setAttributeNode(attr);
            //name element
            Element elementName = document.createElement("name");
            elementName.setTextContent(letter.getBody().get("name"));
            root.appendChild(elementName);
            //transform
            xml = docToString(document);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return xml;
    }

    private static String parseMessage(Letter letter) {
        Document document;
        String xml = "";
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            //event element
            Element root = document.createElement("event");
            document.appendChild(root);
            Attr attr = document.createAttribute("name");
            attr.setValue("message");
            root.setAttributeNode(attr);
            //message element
            Element elementMessage = document.createElement("message");
            elementMessage.setTextContent(letter.getBody().get("message"));
            root.appendChild(elementMessage);
            //name element
            Element elementName = document.createElement("name");
            elementName.setTextContent(letter.getBody().get("name"));
            root.appendChild(elementName);
            //transform
            xml = docToString(document);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return xml;
    }

    private static String parseList(Letter letter) {
        Document document;
        String xml = "";
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            if (letter.getBody().get("listusers") != null) {
                //success element
                Element root = document.createElement("success");
                document.appendChild(root);
                //listusers element
                Element listUsers = document.createElement("listusers");
                root.appendChild(listUsers);
                for (String username : letter.getBody().get("listusers").split(",")) {
                    Element user = document.createElement("user");
                    Element name = document.createElement("name");
                    Element type = document.createElement("type");
                    name.setTextContent(username);
                    type.setTextContent("");
                    user.appendChild(name);
                    user.appendChild(type);
                    listUsers.appendChild(user);
                }
                //transform
            } else {
                Element root = document.createElement("event");
                document.appendChild(root);
                Attr attr = document.createAttribute("name");
                attr.setValue("listusers");
                root.setAttributeNode(attr);
            }
            xml = docToString(document);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public static String makeSuccess() {
        Document document;
        String xml = "";
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            //success element
            Element root = document.createElement("success");
            document.appendChild(root);
            xml = docToString(document);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public static String makeError(String message) {
        Document document;
        String xml = "";
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            //success element
            Element root = document.createElement("error");
            root.setTextContent(message);
            document.appendChild(root);
            xml = docToString(document);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return xml;
    }

    private static String docToString(Document document) {
        String xml = "";
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource domSource = new DOMSource(document);
            StringWriter stringWriter = new StringWriter();
            StreamResult streamResult = new StreamResult(stringWriter);
            transformer.transform(domSource, streamResult);
            xml = stringWriter.getBuffer().toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return xml;
    }
}
