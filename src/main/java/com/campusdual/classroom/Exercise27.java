package com.campusdual.classroom;


import com.google.gson.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

public class Exercise27 {
    public static void main(String[] args) {

        Exercise27 exercise27 = new Exercise27();
        exercise27.createJson();
        exercise27.createXML();


    }

    private void createXML(){
        try {
            // Crear un documento XML vacío
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            // Crear el elemento raíz
            Element root = document.createElement("shoppinglist");
            document.appendChild(root);

            Element items = document.createElement("items");
            root.appendChild(items);


            agregarProducto(document, items, 2, "Manzana");
            agregarProducto(document, items, 1, "Leche");
            agregarProducto(document, items, 3, "Pan");
            agregarProducto(document, items, 2, "Huevo");
            agregarProducto(document, items, 1, "Queso");
            agregarProducto(document, items, 1, "Cereal");
            agregarProducto(document, items, 4, "Agua");
            agregarProducto(document, items, 6, "Yogur");
            agregarProducto(document, items, 2, "Arroz");


            // Guardar el contenido XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("src/main/resources/shoppingList.xml"));

            transformer.transform(source, result);

            System.out.println("Archivo XML creado exitosamente.");

        } catch (TransformerException | ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    // Método para agregar un producto al XML
    private static void agregarProducto(Document document, Element root, int cantidad, String nombre) {
        Element item = document.createElement("item");

        item.setAttribute("quantity", String.valueOf(cantidad)); // Cantidad como atributo
        item.appendChild(document.createTextNode(nombre)); // Texto del item

        root.appendChild(item);
    }

    private JsonObject createItem(String text, int quantity) {
        JsonObject item = new JsonObject();
        item.addProperty("text", text);
        item.addProperty("quantity", quantity);
        return item;
    }

    private void createJson(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Set<JsonObject> items = new HashSet<>(Arrays.asList(
                createItem("Manzana", 2),
                createItem("Leche", 1),
                createItem("Pan", 3),
                createItem("Huevo", 2),
                createItem("Queso", 1),
                createItem("Cereal", 1),
                createItem("Agua", 4),
                createItem("Yogur", 6),
                createItem("Arroz", 2)
        ));

        JsonObject shoppingList = new JsonObject();
        shoppingList.add("items", gson.toJsonTree(items));

        // Guardar el JSON en un archivo
        try (FileWriter writer = new FileWriter("src/main/resources/shoppingList.json")) {
            writer.write(gson.toJson(shoppingList));
            System.out.println("Archivo JSON creado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo JSON: " + e.getMessage());
        }

    }


}
