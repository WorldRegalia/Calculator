/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlproject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author User
 */
public class DirectoryUtils {

    public static void copyFolder(File sourceFolder, File destinationFolder) throws IOException {
        //Check if sourceFolder is a directory or file
        //If sourceFolder is file; then copy the file directly to new location
        if (sourceFolder.isDirectory()) {
            //Verify if destinationFolder is already present; If not then create it
            if (!destinationFolder.exists()) {
                destinationFolder.mkdir();
                //System.out.println("Directory created :: " + destinationFolder);
            }

            //Get all files from source directory
            String files[] = sourceFolder.list();

            //Iterate over all files and copy them to destinationFolder one by one
            for (String file : files) {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);

                //Recursive function call
                copyFolder(srcFile, destFile);
            }
        } else {
            //Copy the file content from one place to another 
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
            //System.out.println("File copied :: " + destinationFolder);
        }
    }

    public static void printNonSrcDirectories(String srcDir) {
        File folder = new File(srcDir);
        File[] files = folder.listFiles();

        for (File file : files) {
            if (!file.getName().startsWith("com")) {
                System.out.println("Directory: " + file.getName());
            }
        }
    }

    public static List<String> getProjectsWithExtnalDependencies(File fileDir, String targetFiles) throws IOException {
        List<File> files = findAllFiles(fileDir, targetFiles);
        List<String> projects = new ArrayList<>();
        for (File file : files) {
            Scanner scanner = new Scanner(file);
            String projectName = "";
            boolean hasExternalDep = false;
            System.out.println("NEW FILE");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //System.out.println(line);
                if (line.startsWith("application.title")) {
                    projectName = line.substring(line.lastIndexOf("="), line.length());
                    System.out.println(projectName);
                }
                if (line.startsWith("file.reference") && line.contains("=lib/")) {
                    projects.add(projectName);
                    System.out.println("xmlproject.DirectoryUtils.getProjectsWithExtnalDependencies()");
                    System.err.println(projectName);
                    scanner.close();
                    break;
                }
            }
            scanner.close();
        }
        return projects;
    }

    public static List<File> findAllFiles(File fileDir, String targetFiles) throws IOException {
        try (Stream<Path> walk = Files.walk(fileDir.toPath())) {
            System.out.println(targetFiles);
            List<String> result = walk.map(x -> x.toString())
                    .filter(f -> f.contains(targetFiles))
                    .collect(Collectors.toList());

            List<File> files = new ArrayList<>();
            result.forEach(i -> {
                files.add(new File(i));
                System.out.println(i);
            });
            return files;
        }
    }

    public static void writeRepositories(XMLStreamWriter xmlStreamWriter) throws XMLStreamException {
        /*
            1. search local repository
            2. search maven central and download if found
            3. if dependency not found in maven central and not other repos is configured then fail
            4. search dependency in repository/repositories configured and download if found or else fail
         */
        xmlStreamWriter.writeStartElement("reposistories");

        xmlStreamWriter.writeStartElement("repository");

        xmlStreamWriter.writeStartElement("id");
        xmlStreamWriter.writeCharacters("useId");
        xmlStreamWriter.writeEndElement();

        xmlStreamWriter.writeStartElement("url");
        xmlStreamWriter.writeCharacters("URL");
        xmlStreamWriter.writeEndElement();

        xmlStreamWriter.writeStartElement("snapshots");

        xmlStreamWriter.writeStartElement("snaptho");
        xmlStreamWriter.writeCharacters("false");
        xmlStreamWriter.writeEndElement();

        xmlStreamWriter.writeEndElement();

        // Close repositories element
        xmlStreamWriter.writeEndElement();
    }

}
