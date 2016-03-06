package main;

import base.CaseConfig;
import java.io.*;
import java.security.NoSuchAlgorithmException;

/**
 * @author v.chibrikov
 */
public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        CaseConfig cfg = new CaseConfig("./cfg/test.properties", args);
        CaseProcessor caseProcessor = new CaseProcessor(cfg);
        boolean result = caseProcessor.process();
        //result = false; //заглушка
        if (!result) {
            System.out.println("Test failed with output:");
            System.out.print(caseProcessor.getServerOut());

            try(FileWriter writer = new FileWriter("log.txt", false))
            {
                String text = "Test failed with output:"+ "\n" + caseProcessor.getServerOut();//придумать перенос каждой ошибки на новую строку
                writer.write(text);
                writer.flush();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
            System.exit(1);
        } else {
            System.out.println("Test passed.");
            String key = KeyPassCreator.create(cfg.getCaseClass());
            System.out.println("Task pass-key: " + key);

            try(FileWriter writer = new FileWriter("key.txt", false))
            {
                String text = "Task pass-key: " + key;
                writer.write(text);
                writer.flush();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}
