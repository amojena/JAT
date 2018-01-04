import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;


import org.w3c.dom.*;
import java.io.*;
import java.util.Vector;



public class XMLparser {

    void write(Vector<jobApp> applications) {
        try {
            Document doc = retrieveFile();
            if (doc == null) {
                doc = createFile();
            }

            Element listNode, rootNode, compNameNode, jobIDNode, typeNode, appliedNode, urlNode, usernameNode, passwordNode, heardBackNode;
            jobApp temp;

            listNode = doc.createElement("List");
            doc.appendChild(listNode);

            for (int i = 0; i < applications.size(); i++) {
                temp = applications.elementAt(i);

                rootNode = doc.createElement("Application");
                listNode.appendChild(rootNode);

                compNameNode = doc.createElement("CompanyName");
                compNameNode.appendChild(doc.createTextNode(temp.companyName));
                rootNode.appendChild(compNameNode);

                jobIDNode = doc.createElement("JobID");
                jobIDNode.appendChild(doc.createTextNode(temp.jobID));
                rootNode.appendChild(jobIDNode);

                typeNode = doc.createElement("Type");
                typeNode.appendChild(doc.createTextNode(temp.type));
                rootNode.appendChild(typeNode);

                appliedNode = doc.createElement("Applied");
                appliedNode.appendChild(doc.createTextNode(String.valueOf(temp.applied)));
                rootNode.appendChild(appliedNode);

                urlNode = doc.createElement("URL");
                urlNode.appendChild(doc.createTextNode(temp.url));
                rootNode.appendChild(urlNode);

                usernameNode = doc.createElement("Username");
                usernameNode.appendChild(doc.createTextNode(temp.username));
                rootNode.appendChild(usernameNode);

                passwordNode = doc.createElement("Password");
                passwordNode.appendChild(doc.createTextNode(temp.password));
                rootNode.appendChild(passwordNode);

                heardBackNode = doc.createElement("HeardBack");
                heardBackNode.appendChild(doc.createTextNode(String.valueOf(temp.heardBack)));
                rootNode.appendChild(heardBackNode);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("test.xml"));

            transformer.transform(source, result);
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    private Document createFile() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.newDocument();
            return doc;
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        return null;
    }


    private Document retrieveFile() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse("test.xml");
            return doc;
        } catch (ParserConfigurationException pce) {
            return null;
        } catch (IOException ioe) {
            return null;
        } catch (SAXException sae) {
            return null;
        }

    }
}


