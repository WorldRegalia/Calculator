/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlproject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author User
 */
public class NewClass {
    public static void main(String[] args) throws IOException {
        String targetDir = "C:\\Users\\User\\Documents\\Mvn Projects";
        String targetFolder = "ParentProject";
        String targetLocation = targetDir + "\\" + targetFolder;
        
        generateMavenProject(targetLocation, "JavaApplication1");
        
    }
    
    public static void generateMavenProject(String targetLocation, String projectName) throws IOException {
        String projectLocation = targetLocation + "\\" + projectName;
        File targetsrcFile = new File(projectLocation + "\\src\\main\\java");
        File testFile = new File(projectLocation + "\\src\\main\\java");
        Files.createDirectories(Paths.get(projectLocation + "\\src\\main\\java"));
        Files.createDirectories(Paths.get(projectLocation + "\\src\\test\\java"));
        
        String pom = "pom file";
        Files.write(Paths.get(projectLocation + "\\pom.xml"), pom.getBytes());
        
        //String sourceDir = "F:\\NetBeans-workspace\\" + projectName + "\\src";
        File sourceDir = new File("F:\\NetBeans-workspace\\" + projectName + "\\src");
        DirectoryUtils.printNonSrcDirectories(sourceDir.getAbsolutePath());
        DirectoryUtils.copyFolder(sourceDir, targetsrcFile);
        //DirectoryUtils.findAllFiles(new File("F:\\NetBeans-workspace"), ("nbproject\\project.properties"));
        System.out.println(DirectoryUtils.getProjectsWithExtnalDependencies(new File("C:\\Users\\User\\Documents\\NetBeansProjects"), "nbproject\\project.properties").toString());
    }
}
