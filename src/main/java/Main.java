package src.main.java;

import src.main.java.GameProgress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {

        File pathToZip = new File("Games/savegames/zip.zip");
        File pathToSave = new File("Games/savegames/");
        openZip(pathToZip.getAbsolutePath(), pathToSave.getAbsolutePath());

        File file1 = new File("Games/savegames/save1.dat");
        File file2 = new File("Games/savegames/save2.dat");
        File file3 = new File("Games/savegames/save3.dat");

        GameProgress openGame1 = openProgress(file1.getAbsolutePath());
        GameProgress openGame2 = openProgress(file2.getAbsolutePath());
        GameProgress openGame3 = openProgress(file3.getAbsolutePath());

        System.out.println(openGame1);
        System.out.println(openGame2);
        System.out.println(openGame3);
    }

    public static void openZip(String pathToZip, String pathToSave) {
        try(ZipInputStream zip = new ZipInputStream(new FileInputStream(pathToZip))) {

            while (true) {
                ZipEntry zipEntry = zip.getNextEntry();
                if (zipEntry == null) return;

                String fileName = zipEntry.getName();
                FileOutputStream fileOutputStream = new FileOutputStream(pathToSave + "/" + fileName);

                for (int i = zip.read(); i != -1; i = zip.read()) {
                    fileOutputStream.write(i);
                }

                fileOutputStream.flush();
                zip.closeEntry();
                fileOutputStream.close();
            }
        } catch (Exception e) { }
    }

    public static GameProgress openProgress(String pathToSaves) {
        GameProgress game = null;
        try(FileInputStream fileInputStream = new FileInputStream(pathToSaves)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            game = (GameProgress) objectInputStream.readObject();
        } catch (Exception e) { }

        return game;
    }

}
