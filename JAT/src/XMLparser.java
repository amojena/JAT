import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;


import org.w3c.dom.*;
import java.io.*;
import java.util.Vector;



public class XMLparser {

    private String path = "test.xml";

    void write(Vector<jobApp> applications) {
        try {

            File check = new File(path);
            Element listNode, rootNode, compNameNode, jobIDNode, typeNode, appliedNode, urlNode, usernameNode, passwordNode, heardBackNode;
            jobApp temp;
            Document doc;
            if (check.isFile()) {
                doc = retrieveFile();
                listNode = (Element) doc.getElementsByTagName("List").item(0);
            }
            else{
                doc = createFile();
                listNode = doc.createElement("List");
                doc.appendChild(listNode);
            }

                temp = applications.elementAt(applications.size()-1);

                rootNode = doc.createElement("Application");
                listNode.appendChild(rootNode);

                compNameNode = doc.createElement("CompanyName");
                compNameNode.appendChild(doc.createTextNode(temp.companyName));
                rootNode.appendChild(compNameNode);

                compNameNode = doc.createElement("JobTitle");
                compNameNode.appendChild(doc.createTextNode(temp.jobTitle));
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

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            System.out.println("Created file");
            System.out.println("Working Directory = " +
                    System.getProperty("user.dir"));

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
            Document doc = docBuilder.parse(path);
            if (doc == null) {return null;}
            return doc;
        } catch (ParserConfigurationException pce) {
            return null;
        } catch (IOException ioe) {
            return null;
        } catch (SAXException sae) {
            return null;
        }
    }

    Vector<jobApp> getExistingApplications()
    {
        Vector<jobApp> existingApps = new Vector<>(3, 1);
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(path);

            NodeList list = doc.getElementsByTagName("Application");

            Node application;
            Element app;
            String compName, jobTitle, jobID, type, applied, url, username, password, heardBack;
            Boolean appliedBool = false, heardBackBool = false;

            for(int i = 0; i < list.getLength(); i++)
            {
                application = list.item(i);

                if (application.getNodeType() == Node.ELEMENT_NODE)
                {
                    app = (Element) application;

                    compName = app.getElementsByTagName("CompanyName").item(0).getTextContent();
                    jobTitle = app.getElementsByTagName("JobTitle").item(0).getTextContent();
                    jobID = app.getElementsByTagName("JobID").item(0).getTextContent();
                    type = app.getElementsByTagName("Type").item(0).getTextContent();
                    applied = app.getElementsByTagName("Applied").item(0).getTextContent();
                    url = app.getElementsByTagName("URL").item(0).getTextContent();
                    username = app.getElementsByTagName("Username").item(0).getTextContent();
                    password = app.getElementsByTagName("Password").item(0).getTextContent();
                    heardBack = app.getElementsByTagName("HeardBack").item(0).getTextContent();

                    if(applied.equals("true")) {appliedBool = true;}
                    if(heardBack.equals("true")) {heardBackBool = true;}

                    existingApps.addElement(new jobApp(compName, jobTitle, jobID, type, appliedBool, url, username, password, heardBackBool));


                }
            }
            return existingApps;

        } catch (ParserConfigurationException pce) {
            return existingApps;
        } catch (IOException ioe) {
            return existingApps;
        } catch (SAXException sae) {
            return existingApps;
        }

    }

    void delete(int index)
    {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(path);
            Element app = (Element) doc.getElementsByTagName("Application").item(index);
            app.getParentNode().removeChild(app);
            /*Node parent = app.getParentNode();
            parent.removeChild(app);
            parent.normalize();*/

            return;
        }
        catch (ParserConfigurationException pce) {
            return;
        } catch (IOException ioe) {
            return;
        } catch (SAXException sae) {
            return;
        }
    }

    void overwrite(Vector<jobApp> applications) {
        try {
            Element listNode, rootNode, compNameNode, jobIDNode, typeNode, appliedNode, urlNode, usernameNode, passwordNode, heardBackNode;
            jobApp temp;
            Document doc;

            doc = createFile();
            listNode = doc.createElement("List");
            doc.appendChild(listNode);

            for(int i = 0; i < applications.size(); i++) {
                temp = applications.elementAt(i);

                rootNode = doc.createElement("Application");
                listNode.appendChild(rootNode);

                compNameNode = doc.createElement("CompanyName");
                compNameNode.appendChild(doc.createTextNode(temp.companyName));
                rootNode.appendChild(compNameNode);

                compNameNode = doc.createElement("JobTitle");
                compNameNode.appendChild(doc.createTextNode(temp.jobTitle));
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
            StreamResult result = new StreamResult(new File(path));

            transformer.transform(source, result);
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}


