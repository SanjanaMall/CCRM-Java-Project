package edu.ccrm.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class recursion {
    
    /**
     * Recursively calculate total size of backup directory
     */
    public static long calculateDirectorySize(File directory) {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            return 0;
        }
        
        long totalSize = 0;
        File[] files = directory.listFiles();
        
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    totalSize += file.length();
                } else if (file.isDirectory()) {
                    // Recursive call for subdirectories
                    totalSize += calculateDirectorySize(file);
                }
            }
        }
        
        return totalSize;
    }
    
    /**
     * Recursively list all files in directory
     */
    public static List<File> listAllFiles(File directory) {
        List<File> fileList = new ArrayList<>();
        
        if (directory != null && directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileList.add(file);
                    } else if (file.isDirectory()) {
                        // Recursive call
                        fileList.addAll(listAllFiles(file));
                    }
                }
            }
        }
        
        return fileList;
    }
    
    /**
     * Print directory tree structure recursively
     */
    public static void printDirectoryTree(File directory, String prefix) {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            return;
        }
        
        System.out.println(prefix + "├── " + directory.getName() + "/");
        
        File[] files = directory.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                boolean isLast = (i == files.length - 1);
                
                if (file.isDirectory()) {
                    printDirectoryTree(file, prefix + (isLast ? "    " : "│   "));
                } else {
                    System.out.println(prefix + (isLast ? "└── " : "├── ") + file.getName());
                }
            }
        }
    }
}